package com.haolinbang.modules.sns.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.WyMemberRefQuestionnaire;
import com.haolinbang.modules.sns.entity.WyOption;
import com.haolinbang.modules.sns.entity.WyQuestionnaire;
import com.haolinbang.modules.sns.entity.WyQuestionnaireRelease;
import com.haolinbang.modules.sns.entity.WySubject;
import com.haolinbang.modules.sns.service.MemberRefQuestionnaireService;
import com.haolinbang.modules.sns.service.OptionService;
import com.haolinbang.modules.sns.service.QuestionaryReleaseService;
import com.haolinbang.modules.sns.service.QuestionaryService;
import com.haolinbang.modules.sns.service.RoomService;
import com.haolinbang.modules.sns.service.SubjectService;
import com.haolinbang.modules.sns.util.MemberUtils;


@Controller
@RequestMapping("/questionary")
public class QuestionaryController extends BaseController {
	
	@Autowired
	private QuestionaryService questionaryService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private OptionService optionService;
	
	@Autowired
	private MemberRefQuestionnaireService memberRefQuestionnaireService;
	
	@Autowired
	private QuestionaryReleaseService questionaryReleaseService;
	
	@Autowired
	private RoomService roomService;
	
	/**
	 * 显示问卷页面
	 * 
	 * @return
	 */
	@RequestMapping("/showQuestionary")
	public String showQuestionary(Model model,  HttpServletRequest request) {
		
		/*List<WyQuestionnaire> questionnaireList = questionaryService.getQuestionnaireList();
		model.addAttribute("questionnaireList", questionnaireList);*/
		/*
		 * 获取用户所有合同所有的source和groupid
		 * 根据source和groupid获取对应的已发布的问卷
		 * 获取发布的问卷列表,按创建时间排序
		 * 遍历列表,为每一个问卷赋状态值,临时变量status,
		 * 如果截止日期小于今天,status=0,可查看不可操作;
		 * 如果截止日期大于今天,status=1,可填写;
		 */
		String source = (String) request.getSession().getAttribute("usersource"); 
		String groupid = ((Integer) request.getSession().getAttribute("usergroupid"))+""; 
		/*DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		Member member = MemberUtils.getCurrentMember();	
		List<String> list = roomService.getSourceListByMemberId(member.getMemberId().toString());*/
		//List<String> list = roomService.getSourceListByMemberId("6");
		List<WyQuestionnaireRelease> wyQuestionnaireReleasesList = new ArrayList<WyQuestionnaireRelease>();
		wyQuestionnaireReleasesList = questionaryReleaseService.getBySourceAndGroupId(source, groupid);
		/*for(String s : list){
			List<WyQuestionnaireRelease> wyQuestionnaireReleasesList1 = questionaryReleaseService.getQuestionnaireReleaseList(s);
			wyQuestionnaireReleasesList.addAll(wyQuestionnaireReleasesList1);
		}*/

		for(WyQuestionnaireRelease wyQuestionnaireRelease : wyQuestionnaireReleasesList){
			Date date = new Date();
			Date endtime = wyQuestionnaireRelease.getEndtime()==null?null:wyQuestionnaireRelease.getEndtime();
			if(endtime==null || wyQuestionnaireRelease.getEndtime().getTime() > date.getTime()){
				wyQuestionnaireRelease.setStatus(1);
			}else{
				wyQuestionnaireRelease.setStatus(0);
			}
		}
		model.addAttribute("wyQuestionnaireReleasesList", wyQuestionnaireReleasesList);
		
		if(wyQuestionnaireReleasesList==null || wyQuestionnaireReleasesList.size()<=0){
			model.addAttribute("flag", 0);
		}else{
			model.addAttribute("flag", 1);
		}
		
		return "modules/sns/showQuestionary";
	}
	
	/**
	 * 显示问卷内容
	 * 
	 * @return
	 */
	@RequestMapping("/showQuestionaryDetail")
	public String showQuestionaryDetail(String questionnaireid, Model model, Integer status, String releaseId, HttpServletRequest request) {
		request.getSession().setAttribute("releaseId", releaseId);
		
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		WyQuestionnaire wyQuestionnaire = questionaryService.getByQuestionnaireid(""+questionnaireid);
		model.addAttribute("wyQuestionnaire", wyQuestionnaire);
		
		List<WySubject> wySubjectsList = subjectService.getSubjectListByQuestionnaireid(questionnaireid);
		ArrayList<Object> list = new ArrayList<Object>();
		for(WySubject ws : wySubjectsList){
			Map map = new HashMap();
			map.put("subject", ws);
			
			if(ws.getType() != 2){
				List<WyOption> optionList = optionService.getOptionListBySubjectid(ws.getSubjectid().toString());
				map.put("optionList", optionList);
			}
			list.add(map);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("status", status);
		
		return "modules/sns/showQuestionaryDetail";
	}
	
	/**
	 * 问卷提交
	 * 
	 * @return
	 */
	@RequestMapping("/questionarySubmit")
	public String questionarySubmit(Model model, HttpServletRequest request) {
		try{
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
			Member member = MemberUtils.getCurrentMember();	
			String wyQuestionnaireid = request.getParameter("wyQuestionnaireid");
			List<WySubject> subjectList = subjectService.getSubjectListByQuestionnaireid(wyQuestionnaireid);
			for(WySubject wySubject : subjectList){
				WyMemberRefQuestionnaire wyMemberRefQuestionnaire = new WyMemberRefQuestionnaire();
				wyMemberRefQuestionnaire.setMemberid(member.getMemberId());
				wyMemberRefQuestionnaire.setQuestionnaireid(StringUtils.toInteger(wyQuestionnaireid));
				wyMemberRefQuestionnaire.setSubjectid(wySubject.getSubjectid());
				wyMemberRefQuestionnaire.setCreatetime(new Date());
				if(wySubject.getType() == 0){
					String optionid = request.getParameter("subject"+wySubject.getSubjectid());
					wyMemberRefQuestionnaire.setOptionid(StringUtils.toInteger(optionid));
					memberRefQuestionnaireService.save(wyMemberRefQuestionnaire);
				}else if(wySubject.getType() == 1){
					String[] optionid = request.getParameterValues("subject"+wySubject.getSubjectid());
					if(optionid==null){
						continue;
					}
					for(String s : optionid){
						WyMemberRefQuestionnaire wyMemberRefQuestionnaire1 = wyMemberRefQuestionnaire;
						wyMemberRefQuestionnaire1.setOptionid(StringUtils.toInteger(s));
						memberRefQuestionnaireService.save(wyMemberRefQuestionnaire1);
					}
				}else if(wySubject.getType() == 2){
					String optionid = request.getParameter("subject"+wySubject.getSubjectid());
					wyMemberRefQuestionnaire.setContent(optionid);
					memberRefQuestionnaireService.save(wyMemberRefQuestionnaire);
				}			
			}
			
			String releaseId = (String) request.getSession().getAttribute("releaseId");
			WyQuestionnaireRelease wyQuestionnaireRelease = new WyQuestionnaireRelease();
			wyQuestionnaireRelease = questionaryReleaseService.get(releaseId);
			wyQuestionnaireRelease.setNum(wyQuestionnaireRelease.getNum()+1);
			questionaryReleaseService.update(wyQuestionnaireRelease);
		} catch(Exception e){
			logger.error("提交问卷出错:"+e.getMessage());
			//return WeJson.fail("提交失败");
			return "redirect:" + "/index";
		}
		
		return "redirect:" + "/index";
		//return WeJson.success("提交成功");
	}
	
	/*
	 * 问卷统计
	 */
	@RequestMapping("/countResult")
	public String countResult(Model model) {
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		Member member = MemberUtils.getCurrentMember();	
		List<String> list = roomService.getSourceListByMemberId(member.getMemberId().toString());
		List<WyQuestionnaireRelease> wyQuestionnaireReleasesList = new ArrayList<WyQuestionnaireRelease>();
		for(String s : list){
			List<WyQuestionnaireRelease> wyQuestionnaireReleasesList1 = questionaryReleaseService.getQuestionnaireReleaseList(s);
			wyQuestionnaireReleasesList.addAll(wyQuestionnaireReleasesList1);
		}

		model.addAttribute("wyQuestionnaireReleasesList", wyQuestionnaireReleasesList);
		
		if(wyQuestionnaireReleasesList==null || wyQuestionnaireReleasesList.size()<=0){
			model.addAttribute("flag", 0);
		}else{
			model.addAttribute("flag", 1);
		}
		
		return "modules/sns/countResult";
	}
	
	/*
	 * 问卷统计详情
	 
	@RequestMapping("/countResultDetail")
	public String countResultDetail(WyQuestionnaireRelease wyQuestionnaireRelease, Model model) {
		
		return "";
	}
	*/
}
