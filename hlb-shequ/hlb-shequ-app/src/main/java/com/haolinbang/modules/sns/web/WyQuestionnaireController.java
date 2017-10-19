package com.haolinbang.modules.sns.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.dao.WyQuestionnaireReleaseDao;
import com.haolinbang.modules.sns.entity.WyClassification;
import com.haolinbang.modules.sns.entity.WyConvenienceService;
import com.haolinbang.modules.sns.entity.WyOption;
import com.haolinbang.modules.sns.entity.WyQuestionnaire;
import com.haolinbang.modules.sns.entity.WyQuestionnaireRelease;
import com.haolinbang.modules.sns.entity.WySubject;
import com.haolinbang.modules.sns.service.WyClassificationService;
import com.haolinbang.modules.sns.service.WyOptionService;
import com.haolinbang.modules.sns.service.WyQuestionnaireReleaseService;
import com.haolinbang.modules.sns.service.WyQuestionnaireService;
import com.haolinbang.modules.sns.service.WySubjectService;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.sys.web.OfficeController;

/**
 * 问卷调查表Controller
 * @author wxc
 * @version 2017-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyQuestionnaire")
public class WyQuestionnaireController extends BaseController {

	@Autowired
	private WyQuestionnaireService wyQuestionnaireService;
	
	@Autowired
	private WyClassificationService wyClassificationService;
	
	@Autowired
	private WyQuestionnaireReleaseService wyQuestionnaireReleaseService;
	
	@Autowired
	private WySubjectService wySubjectService;
	
	@Autowired
	private WyOptionService wyOptionService;
	
	@RequiresPermissions("sns:wyQuestionnaire:view")
	@RequestMapping("")
	public String init(WyConvenienceService wyConvenienceService, HttpServletRequest request, HttpServletResponse response, Model model) {
		request.getSession().setAttribute("wqgid", "");
		request.getSession().setAttribute("wqname", "");
		return "modules/sns/wyQuestionnaireIndex";
	}
	
	@ModelAttribute
	public WyQuestionnaire get(@RequestParam(required=false) String questionnaireid) {
		WyQuestionnaire entity = null;
		if (StringUtils.isNotEmpty(questionnaireid) && !questionnaireid.equals("0")){
			entity = wyQuestionnaireService.get(questionnaireid);
		}
		if (entity == null){
			entity = new WyQuestionnaire();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyQuestionnaire:view")
	@RequestMapping(value = {"list"})
	public String list(WyQuestionnaire wyQuestionnaire, HttpServletRequest request, HttpServletResponse response, Model model) {
		String gid = request.getParameter("gid");
		String name = request.getParameter("name");
		String refresh = request.getParameter("refresh");
		if(StringUtils.isBlank(gid)){
			gid = (String) request.getSession().getAttribute("wqgid");
		}
		if(StringUtils.isBlank(name)){
			name = (String) request.getSession().getAttribute("wqname");
		}
		if(StringUtils.isBlank(gid)){
			String urlkey = UserUtils.getUser().getSource();
			List<GroupInfo> list = UserUtils.getUserGroupInfo(urlkey);
			GroupInfo groupInfo = list.get(list.size()-1);
			List<String> groupids = UserUtils.getEmployeeOfGroups();
			boolean flag = false;
			for(String ss : groupids){
				if(groupInfo.getGroupId().equals(ss)){
					flag = true;
				}
			}
			if(flag){
				wyQuestionnaire.setSource(urlkey);
				wyQuestionnaire.setGroupid(StringUtils.toInteger(groupInfo.getGroupId()));
				request.getSession().setAttribute("wqsource", urlkey);
				request.getSession().setAttribute("wqgroupid", groupInfo.getGroupId());
			}else{
				return "modules/sns/wyCompanyProfileNoForm";
			}
		}else if(StringUtils.isBlank(name)){
			return "modules/sns/wyCompanyProfileNoForm";
		}else{
			if(name.contains("可操作")){
				String[] gids = gid.split("___");
				wyQuestionnaire.setSource(gids[1]);
				wyQuestionnaire.setGroupid(StringUtils.toInteger(gids[2]));
				request.getSession().setAttribute("wqsource", gids[1]);
				request.getSession().setAttribute("wqgroupid", gids[2]);
			}else{
				return "modules/sns/wyCompanyProfileNoForm";
			}
		}
		Page<WyQuestionnaire> page = wyQuestionnaireService.findPage(new Page<WyQuestionnaire>(request, response), wyQuestionnaire); 
		model.addAttribute("page", page);
		if(StringUtils.isNotBlank(refresh) && refresh.equals("refresh")){
			request.getSession().setAttribute("questionnaireid", 0);
		}
		Integer questionnaireid = (Integer) request.getSession().getAttribute("questionnaireid");
		if(questionnaireid==null || questionnaireid==0){
			questionnaireid = page.getList().size()>0 ? page.getList().get(0).getQuestionnaireid() : 0;
			request.getSession().setAttribute("questionnaireid", questionnaireid);
			if(questionnaireid != 0){
				List<WyClassification> wyClassificationList = wyClassificationService.getbyquestionnaireid(questionnaireid);
				if(wyClassificationList!=null && wyClassificationList.size()>0){
					Integer classificationid = wyClassificationList.get(0).getClassificationid();
					request.getSession().setAttribute("classificationid", classificationid);
					List<WySubject> wySubjects = wySubjectService.getbyclassificationid(classificationid);
					if(wySubjects!=null && wySubjects.size()>0){
						Integer subjectid = wySubjects.get(0).getSubjectid();
						request.getSession().setAttribute("subjectid", subjectid);
					}
				}else{
					request.getSession().setAttribute("subjectid", 0);
				}
			}
			
		}
		model.addAttribute("questionnaireid", questionnaireid);
		request.getSession().setAttribute("wqgid", gid);
		request.getSession().setAttribute("wqname", name);
		return "modules/sns/wyQuestionnaireList";
	}

	@RequiresPermissions("sns:wyQuestionnaire:view")
	@RequestMapping(value = "form")
	public String form(WyQuestionnaire wyQuestionnaire, Model model, String type) {
		model.addAttribute("wyQuestionnaire", wyQuestionnaire);
		model.addAttribute("type", type);
		return "modules/sns/wyQuestionnaireForm";
	}

	@RequiresPermissions("sns:wyQuestionnaire:edit")
	@RequestMapping(value = "save")
	public String save(WyQuestionnaire wyQuestionnaire, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes, String type) {
		if(type!=null && type.equals("edit")){
			wyQuestionnaire.setId(wyQuestionnaire.getQuestionnaireid().toString());
			wyQuestionnaire.setUpdatetime(new Date());
		}else{
			wyQuestionnaire.setCreatetime(new Date());
			wyQuestionnaire.setUpdatetime(new Date());
			User user = UserUtils.getUser();
			if(user!=null){
				wyQuestionnaire.setCreater(user.getName());
				String source = (String) request.getSession().getAttribute("wqsource");
				String groupid = (String) request.getSession().getAttribute("wqgroupid");
				wyQuestionnaire.setSource(source);
				
				wyQuestionnaire.setGroupid(StringUtils.toInteger(groupid));
			}
		}
		
		if (!beanValidator(model, wyQuestionnaire)){
			return form(wyQuestionnaire, model, type);
		}
		
		wyQuestionnaireService.save(wyQuestionnaire);
		
		changeSession(wyQuestionnaire.getQuestionnaireid(), request);
		
		addMessage(redirectAttributes, "保存问卷调查表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyQuestionnaire/list?repage";
	}
	
	@RequiresPermissions("sns:wyQuestionnaire:edit")
	@RequestMapping(value = "delete")
	public String delete(WyQuestionnaire wyQuestionnaire, RedirectAttributes redirectAttributes) {
		WyQuestionnaireRelease wyQuestionnaireRelease = new WyQuestionnaireRelease();
		wyQuestionnaireRelease.setQuestionnaireid(wyQuestionnaire.getQuestionnaireid());
		List<WyQuestionnaireRelease> wyQuestionnaireReleasesList = wyQuestionnaireReleaseService.findList(wyQuestionnaireRelease);
		if(wyQuestionnaireReleasesList!=null && wyQuestionnaireReleasesList.size()>0){
			addMessage(redirectAttributes, "该问卷已发布，不可删除");
		}else{
			wyQuestionnaireService.delete(wyQuestionnaire);
			addMessage(redirectAttributes, "删除问卷调查表成功");
		}

		return "redirect:"+Global.getAdminPath()+"/sns/wyQuestionnaire/list?repage";
	}
	
	@RequiresPermissions("sns:wyQuestionnaire:edit")
	@RequestMapping(value = "release")
	public String release(WyQuestionnaire wyQuestionnaire, RedirectAttributes redirectAttributes) {
		WyQuestionnaireRelease wyQuestionnaireRelease = new WyQuestionnaireRelease();
		wyQuestionnaireRelease.setQuestionnaireid(wyQuestionnaire.getQuestionnaireid());
		List<WyQuestionnaireRelease> wyQuestionnaireReleases = wyQuestionnaireReleaseService.findList(wyQuestionnaireRelease);
		
		if(wyQuestionnaireReleases!=null && wyQuestionnaireReleases.size()>0) {
			addMessage(redirectAttributes, "该问卷已发布,请勿重复发布");
		}else {
			wyQuestionnaireRelease = new WyQuestionnaireRelease();
			wyQuestionnaireRelease.setQuestionnaireid(wyQuestionnaire.getQuestionnaireid());
			wyQuestionnaireRelease.setNum(0);
			wyQuestionnaireRelease.setRunstatus(1);
			wyQuestionnaireRelease.setCreatetime(new Date());
			wyQuestionnaireReleaseService.save(wyQuestionnaireRelease);
			addMessage(redirectAttributes, "问卷调查表发布成功");
		}

		return "redirect:"+Global.getAdminPath()+"/sns/wyQuestionnaire/list?repage";
	}
	
	@RequiresPermissions("sns:wyQuestionnaire:edit")
	@RequestMapping(value = "copy")
	public String copy(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Integer questionnaireid = (Integer) request.getSession().getAttribute("questionnaireid");
		WyQuestionnaire wyQuestionnaire = wyQuestionnaireService.get(""+questionnaireid);
		wyQuestionnaire.setCreatetime(new Date());
		wyQuestionnaire.setUpdatetime(new Date());
		wyQuestionnaireService.save(wyQuestionnaire);
		
		List<WyClassification> wyClassifications = wyClassificationService.getbyquestionnaireid(StringUtils.toInteger(questionnaireid));
		for(WyClassification wcf : wyClassifications){
			Integer classificationid = wcf.getClassificationid();
			wcf.setQuestionnaireid(wyQuestionnaire.getQuestionnaireid());
			wcf.setCreatetime(new Date());
			wcf.setUpdatetime(new Date());
			wyClassificationService.save(wcf);
			
			List<WySubject> wySubjects = wySubjectService.getbyclassificationid(classificationid);
			for(WySubject ws : wySubjects){
				Integer subjectid = ws.getSubjectid();
				ws.setQuestionnaireid(wyQuestionnaire.getQuestionnaireid());
				ws.setClassificationid(wcf.getClassificationid());
				ws.setCreatetime(new Date());
				ws.setUpdatetime(new Date());
				wySubjectService.save(ws);
				
				List<WyOption> wyOptions = wyOptionService.getbysubjectid(subjectid);
				for(WyOption wo : wyOptions){
					wo.setQuestionnaireid(wyQuestionnaire.getQuestionnaireid());
					wo.setClassificationid(wcf.getClassificationid());
					wo.setSubjectid(ws.getSubjectid());
					wo.setCreatetime(new Date());
					wo.setUpdatetime(new Date());
					wyOptionService.save(wo);
				}
			}
			
		}
		addMessage(redirectAttributes, "问卷调查表复制成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyQuestionnaire/list?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "changeid")
	public WeJson changeid(Integer questionnaireid, HttpServletRequest request) {
		changeSession(questionnaireid, request);
		return WeJson.success("success");
	}
	
	public void changeSession(Integer questionnaireid, HttpServletRequest request){
		if(questionnaireid!=null && questionnaireid!=0){
			request.getSession().setAttribute("questionnaireid", questionnaireid);
			List<WyClassification> wyClassificationList = wyClassificationService.getbyquestionnaireid(questionnaireid);
			if(wyClassificationList!=null && wyClassificationList.size()>0){
				Integer classificationid = wyClassificationList.get(0).getClassificationid();
				request.getSession().setAttribute("classificationid", classificationid);
				List<WySubject> wySubjects = wySubjectService.getbyclassificationid(classificationid);
				if(wySubjects!=null && wySubjects.size()>0){
					Integer subjectid = wySubjects.get(0).getSubjectid();
					request.getSession().setAttribute("subjectid", subjectid);
				}else{
					request.getSession().setAttribute("subjectid", 0);
				}
			}else{
				request.getSession().setAttribute("classificationid", 0);
				request.getSession().setAttribute("subjectid", 0);
			}
		}
	}
}