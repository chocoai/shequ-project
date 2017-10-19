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
import com.haolinbang.modules.sns.entity.WyActForm;
import com.haolinbang.modules.sns.service.WyActFormService;

/**
 * 流程表单管理Controller
 * @author nlp
 * @version 2017-06-01
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyActForm")
public class WyActFormController extends BaseController {

	@Autowired
	private WyActFormService wyActFormService;
	
	@ModelAttribute
	public WyActForm get(@RequestParam(required=false) String id) {
		WyActForm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyActFormService.get(id);
		}
		if (entity == null){
			entity = new WyActForm();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyActForm:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyActForm wyActForm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyActForm> page = wyActFormService.findPage(new Page<WyActForm>(request, response), wyActForm); 
		model.addAttribute("page", page);
		return "modules/sns/wyActFormList";
	}

	@RequiresPermissions("sns:wyActForm:view")
	@RequestMapping(value = "form")
	public String form(WyActForm wyActForm, Model model) {
		model.addAttribute("wyActForm", wyActForm);
		return "modules/sns/wyActFormForm";
	}

	@RequiresPermissions("sns:wyActForm:edit")
	@RequestMapping(value = "save")
	public String save(WyActForm wyActForm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyActForm)){
			return form(wyActForm, model);
		}
		wyActFormService.save(wyActForm);
		addMessage(redirectAttributes, "保存流程表单管理成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActForm/?repage";
	}
	
	@RequiresPermissions("sns:wyActForm:edit")
	@RequestMapping(value = "delete")
	public String delete(WyActForm wyActForm, RedirectAttributes redirectAttributes) {
		wyActFormService.delete(wyActForm);
		addMessage(redirectAttributes, "删除流程表单管理成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActForm/?repage";
	}

}