package com.haolinbang.modules.sns.web;

import java.util.Date;

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
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.entity.WyClassification;
import com.haolinbang.modules.sns.entity.WySubject;
import com.haolinbang.modules.sns.service.WyClassificationService;
import com.haolinbang.modules.sns.service.WySubjectService;

/**
 * 题目表Controller
 * @author wxc
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wySubject")
public class WySubjectController extends BaseController {

	@Autowired
	private WySubjectService wySubjectService;
	
	@Autowired
	private WyClassificationService wyClassificationService;
	
	@ModelAttribute
	public WySubject get(@RequestParam(required=false) String subjectid) {
		WySubject entity = null;
		if (StringUtils.isNotEmpty(subjectid) && !subjectid.equals("0")){
			entity = wySubjectService.get(subjectid);
		}
		if (entity == null){
			entity = new WySubject();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(WySubject wySubject, HttpServletRequest request, HttpServletResponse response, Model model) {
		Integer classificationid = (Integer) request.getSession().getAttribute("classificationid");
		WySubject entity = null;
		if (classificationid!=null && classificationid!=0){
			entity = wySubjectService.get(""+classificationid);
		}
		
		String classificationname = entity!=null?entity.getClassificationname():"";
		wySubject.setClassificationid(classificationid!=null?classificationid:0);
		wySubject.setClassificationname(classificationname);
		
		Page<WySubject> page = wySubjectService.findPage(new Page<WySubject>(request, response), wySubject); 
		model.addAttribute("page", page);
		Integer subjectid = (Integer) request.getSession().getAttribute("subjectid");
		if(subjectid==null || subjectid==0){
			subjectid = page.getList().size()>0 ? page.getList().get(0).getSubjectid() : 0;
			request.getSession().setAttribute("subjectid", subjectid);
		}
		model.addAttribute("subjectid", subjectid);
		return "modules/sns/wySubjectList";
	}

	@RequestMapping(value = "form")
	public String form(WySubject wySubject, HttpServletRequest request, Model model, String type1) {
		Integer classificationid = (Integer) request.getSession().getAttribute("classificationid");
		WyClassification entity = null;
		if (classificationid!=null && classificationid!=0){
			entity = wyClassificationService.get(""+classificationid);
		}

		String classificationname = entity!=null?entity.getClassificationname():"";
		wySubject.setClassificationid(classificationid!=null?classificationid:0);
		wySubject.setClassificationname(classificationname);
		model.addAttribute("wySubject", wySubject);
		model.addAttribute("type", type1);
		return "modules/sns/wySubjectForm";
	}

	@RequestMapping(value = "save")
	public String save(WySubject wySubject, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes, String type1) {
		Integer questionnaireid = (Integer) request.getSession().getAttribute("questionnaireid");
		wySubject.setQuestionnaireid(questionnaireid!=null?questionnaireid:0);
		if(type1!=null && type1.equals("edit")){
			wySubject.setId(wySubject.getClassificationid().toString());
			wySubject.setUpdatetime(new Date());
		}else{
			wySubject.setCreatetime(new Date());
			wySubject.setUpdatetime(new Date());
		}
		
		if (!beanValidator(model, wySubject)){
			return form(wySubject, request, model, type1);
		}
		wySubjectService.save(wySubject);
		
		changeSession(wySubject.getSubjectid(), request);
		
		addMessage(redirectAttributes, "保存题目表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wySubject/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(WySubject wySubject, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		wySubjectService.delete(wySubject);
		addMessage(redirectAttributes, "删除题目表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wySubject/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "changeid")
	public WeJson changeid(Integer subjectid, HttpServletRequest request) {
		changeSession(subjectid, request);
		return WeJson.success("success");
	}
	
	public void changeSession(Integer subjectid, HttpServletRequest request){
		if(subjectid != 0){
			request.getSession().setAttribute("subjectid", subjectid);
		}else{
			request.getSession().setAttribute("subjectid", 0);
		}
	}

}