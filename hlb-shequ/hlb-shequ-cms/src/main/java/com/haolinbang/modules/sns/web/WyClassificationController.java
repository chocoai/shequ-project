package com.haolinbang.modules.sns.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

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
import com.haolinbang.modules.sns.entity.WyQuestionnaire;
import com.haolinbang.modules.sns.entity.WySubject;
import com.haolinbang.modules.sns.service.WyClassificationService;
import com.haolinbang.modules.sns.service.WyQuestionnaireService;
import com.haolinbang.modules.sns.service.WySubjectService;

/**
 * 分类表Controller
 * 
 * @author wxc
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyClassification")
public class WyClassificationController extends BaseController {

	@Autowired
	private WyQuestionnaireService wyQuestionnaireService;

	@Autowired
	private WyClassificationService wyClassificationService;

	@Autowired
	private WySubjectService wySubjectService;

	@ModelAttribute
	public WyClassification get(@RequestParam(required = false) String classificationid) {
		WyClassification entity = null;
		if (StringUtils.isNotEmpty(classificationid) && !classificationid.equals("0")) {
			entity = wyClassificationService.get(classificationid);
		}
		if (entity == null) {
			entity = new WyClassification();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(WyClassification wyClassification, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		Integer questionnaireid = (Integer) request.getSession().getAttribute("questionnaireid");
		WyQuestionnaire entity = null;
		if (questionnaireid != null && questionnaireid != 0) {
			entity = wyQuestionnaireService.get("" + questionnaireid);
		}

		String title = entity != null ? entity.getTitle() : "";
		wyClassification.setQuestionnaireid(questionnaireid != null ? questionnaireid : 0);
		wyClassification.setTitle(title);

		Page<WyClassification> page = wyClassificationService.findPage(new Page<WyClassification>(request, response), wyClassification);
		model.addAttribute("page", page);
		Integer classificationid = (Integer) request.getSession().getAttribute("classificationid");
		if (classificationid == null || classificationid == 0) {
			classificationid = page.getList().size() > 0 ? page.getList().get(0).getClassificationid() : 0;
			request.getSession().setAttribute("classificationid", classificationid);
		}
		model.addAttribute("classificationid", classificationid);
		return "modules/sns/wyClassificationList";
	}

	@RequestMapping(value = "form")
	public String form(WyClassification wyClassification, HttpServletRequest request, Model model, String type) {
		Integer questionnaireid = (Integer) request.getSession().getAttribute("questionnaireid");
		WyQuestionnaire entity = null;
		if (questionnaireid != null && questionnaireid != 0) {
			entity = wyQuestionnaireService.get("" + questionnaireid);
		}

		String title = entity != null ? entity.getTitle() : "";
		wyClassification.setQuestionnaireid(questionnaireid != null ? questionnaireid : 0);
		wyClassification.setTitle(title);
		model.addAttribute("wyClassification", wyClassification);
		model.addAttribute("type", type);
		return "modules/sns/wyClassificationForm";
	}

	@RequestMapping(value = "save")
	public String save(WyClassification wyClassification, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes, String type)
			throws UnsupportedEncodingException {
		if (type != null && type.equals("edit")) {
			wyClassification.setId(wyClassification.getClassificationid().toString());
			wyClassification.setUpdatetime(new Date());
		} else {
			wyClassification.setCreatetime(new Date());
			wyClassification.setUpdatetime(new Date());
		}

		if (!beanValidator(model, wyClassification)) {
			return form(wyClassification, request, model, type);
		}
		wyClassificationService.save(wyClassification);

		changeSession(wyClassification.getClassificationid(), request);

		addMessage(redirectAttributes, "保存分类表成功");
		// System.out.println("redirect:"+Global.getAdminPath()+"/sns/wyClassification?questionnaireid="+wyClassification.getQuestionnaireid()+"&title="+wyClassification.getTitle());
		// return
		// "redirect:"+Global.getAdminPath()+"/sns/wyClassification/?repage";
		return "redirect:" + Global.getAdminPath() + "/sns/wyClassification";
	}

	@RequestMapping(value = "delete")
	public String delete(WyClassification wyClassification, HttpServletRequest request, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		wyClassificationService.delete(wyClassification);
		addMessage(redirectAttributes, "删除分类表成功");

		return "redirect:" + Global.getAdminPath() + "/sns/wyClassification";
	}

	@ResponseBody
	@RequestMapping(value = "changeid")
	public WeJson changeid(Integer classificationid, HttpServletRequest request) {
		changeSession(classificationid, request);
		return WeJson.success("success");
	}

	public void changeSession(Integer classificationid, HttpServletRequest request) {
		if (classificationid != null && classificationid != 0) {
			request.getSession().setAttribute("classificationid", classificationid);
			List<WySubject> wySubjects = wySubjectService.getbyclassificationid(classificationid);
			if (wySubjects != null && wySubjects.size() > 0) {
				Integer subjectid = wySubjects.get(0).getSubjectid();
				request.getSession().setAttribute("subjectid", subjectid);
			} else {
				request.getSession().setAttribute("subjectid", 0);
			}
		} else {
			request.getSession().setAttribute("classificationid", 0);
		}
	}

}