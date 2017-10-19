package com.haolinbang.modules.sns.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sns.entity.WyMemberRefQuestionnaire;
import com.haolinbang.modules.sns.service.WyMemberRefQuestionnaireService;

/**
 * 会员调查表Controller
 * @author wxc
 * @version 2017-06-19
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyMemberRefQuestionnaire")
public class WyMemberRefQuestionnaireController extends BaseController {

	@Autowired
	private WyMemberRefQuestionnaireService wyMemberRefQuestionnaireService;
	
	@ModelAttribute
	public WyMemberRefQuestionnaire get(@RequestParam(required=false) String id) {
		WyMemberRefQuestionnaire entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyMemberRefQuestionnaireService.get(id);
		}
		if (entity == null){
			entity = new WyMemberRefQuestionnaire();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyMemberRefQuestionnaire:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyMemberRefQuestionnaire wyMemberRefQuestionnaire, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyMemberRefQuestionnaire> page = wyMemberRefQuestionnaireService.findPage(new Page<WyMemberRefQuestionnaire>(request, response), wyMemberRefQuestionnaire); 
		model.addAttribute("page", page);
		return "modules/sns/wyMemberRefQuestionnaireList";
	}

	@RequiresPermissions("sns:wyMemberRefQuestionnaire:view")
	@RequestMapping(value = "form")
	public String form(WyMemberRefQuestionnaire wyMemberRefQuestionnaire, Model model) {
		model.addAttribute("wyMemberRefQuestionnaire", wyMemberRefQuestionnaire);
		return "modules/sns/wyMemberRefQuestionnaireForm";
	}

	@RequiresPermissions("sns:wyMemberRefQuestionnaire:edit")
	@RequestMapping(value = "save")
	public String save(WyMemberRefQuestionnaire wyMemberRefQuestionnaire, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyMemberRefQuestionnaire)){
			return form(wyMemberRefQuestionnaire, model);
		}
		wyMemberRefQuestionnaireService.save(wyMemberRefQuestionnaire);
		addMessage(redirectAttributes, "保存会员调查表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyMemberRefQuestionnaire/?repage";
	}
	
	@RequiresPermissions("sns:wyMemberRefQuestionnaire:edit")
	@RequestMapping(value = "delete")
	public String delete(WyMemberRefQuestionnaire wyMemberRefQuestionnaire, RedirectAttributes redirectAttributes) {
		wyMemberRefQuestionnaireService.delete(wyMemberRefQuestionnaire);
		addMessage(redirectAttributes, "删除会员调查表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyMemberRefQuestionnaire/?repage";
	}

}