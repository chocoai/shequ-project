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
import com.haolinbang.modules.sns.entity.WyRepairForm;
import com.haolinbang.modules.sns.service.WyRepairFormService;

/**
 * 维修物料明细表单Controller
 * @author wxc
 * @version 2017-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyRepairForm")
public class WyRepairFormController extends BaseController {

	@Autowired
	private WyRepairFormService wyRepairFormService;
	
	@ModelAttribute
	public WyRepairForm get(@RequestParam(required=false) String id) {
		WyRepairForm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyRepairFormService.get(id);
		}
		if (entity == null){
			entity = new WyRepairForm();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyRepairForm:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyRepairForm wyRepairForm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyRepairForm> page = wyRepairFormService.findPage(new Page<WyRepairForm>(request, response), wyRepairForm); 
		model.addAttribute("page", page);
		return "modules/sns/wyRepairFormList";
	}

	@RequiresPermissions("sns:wyRepairForm:view")
	@RequestMapping(value = "form")
	public String form(WyRepairForm wyRepairForm, Model model) {
		model.addAttribute("wyRepairForm", wyRepairForm);
		return "modules/sns/wyRepairFormForm";
	}

	@RequiresPermissions("sns:wyRepairForm:edit")
	@RequestMapping(value = "save")
	public String save(WyRepairForm wyRepairForm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyRepairForm)){
			return form(wyRepairForm, model);
		}
		wyRepairFormService.save(wyRepairForm);
		addMessage(redirectAttributes, "保存维修物料明细表单成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairForm/?repage";
	}
	
	@RequiresPermissions("sns:wyRepairForm:edit")
	@RequestMapping(value = "delete")
	public String delete(WyRepairForm wyRepairForm, RedirectAttributes redirectAttributes) {
		wyRepairFormService.delete(wyRepairForm);
		addMessage(redirectAttributes, "删除维修物料明细表单成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairForm/?repage";
	}

}