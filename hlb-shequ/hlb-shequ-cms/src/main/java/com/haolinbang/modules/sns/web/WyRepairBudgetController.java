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
import com.haolinbang.modules.sns.entity.WyRepairBudget;
import com.haolinbang.modules.sns.service.WyRepairBudgetService;

/**
 * 物业维修预算汇总Controller
 * @author nlp
 * @version 2017-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyRepairBudget")
public class WyRepairBudgetController extends BaseController {

	@Autowired
	private WyRepairBudgetService wyRepairBudgetService;
	
	@ModelAttribute
	public WyRepairBudget get(@RequestParam(required=false) String id) {
		WyRepairBudget entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyRepairBudgetService.get(id);
		}
		if (entity == null){
			entity = new WyRepairBudget();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyRepairBudget:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyRepairBudget wyRepairBudget, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyRepairBudget> page = wyRepairBudgetService.findPage(new Page<WyRepairBudget>(request, response), wyRepairBudget); 
		model.addAttribute("page", page);
		return "modules/sns/wyRepairBudgetList";
	}

	@RequiresPermissions("sns:wyRepairBudget:view")
	@RequestMapping(value = "form")
	public String form(WyRepairBudget wyRepairBudget, Model model) {
		model.addAttribute("wyRepairBudget", wyRepairBudget);
		return "modules/sns/wyRepairBudgetForm";
	}

	@RequiresPermissions("sns:wyRepairBudget:edit")
	@RequestMapping(value = "save")
	public String save(WyRepairBudget wyRepairBudget, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyRepairBudget)){
			return form(wyRepairBudget, model);
		}
		wyRepairBudgetService.save(wyRepairBudget);
		addMessage(redirectAttributes, "保存物业维修预算汇总成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairBudget/?repage";
	}
	
	@RequiresPermissions("sns:wyRepairBudget:edit")
	@RequestMapping(value = "delete")
	public String delete(WyRepairBudget wyRepairBudget, RedirectAttributes redirectAttributes) {
		wyRepairBudgetService.delete(wyRepairBudget);
		addMessage(redirectAttributes, "删除物业维修预算汇总成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairBudget/?repage";
	}

}