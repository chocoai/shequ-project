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
import com.haolinbang.modules.sns.entity.WyRepairBudgetLabor;
import com.haolinbang.modules.sns.service.WyRepairBudgetLaborService;

/**
 * 物业维修预算工时明细Controller
 * @author nlp
 * @version 2017-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyRepairBudgetLabor")
public class WyRepairBudgetLaborController extends BaseController {

	@Autowired
	private WyRepairBudgetLaborService wyRepairBudgetLaborService;
	
	@ModelAttribute
	public WyRepairBudgetLabor get(@RequestParam(required=false) String id) {
		WyRepairBudgetLabor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyRepairBudgetLaborService.get(id);
		}
		if (entity == null){
			entity = new WyRepairBudgetLabor();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyRepairBudgetLabor:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyRepairBudgetLabor wyRepairBudgetLabor, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyRepairBudgetLabor> page = wyRepairBudgetLaborService.findPage(new Page<WyRepairBudgetLabor>(request, response), wyRepairBudgetLabor); 
		model.addAttribute("page", page);
		return "modules/sns/wyRepairBudgetLaborList";
	}

	@RequiresPermissions("sns:wyRepairBudgetLabor:view")
	@RequestMapping(value = "form")
	public String form(WyRepairBudgetLabor wyRepairBudgetLabor, Model model) {
		model.addAttribute("wyRepairBudgetLabor", wyRepairBudgetLabor);
		return "modules/sns/wyRepairBudgetLaborForm";
	}

	@RequiresPermissions("sns:wyRepairBudgetLabor:edit")
	@RequestMapping(value = "save")
	public String save(WyRepairBudgetLabor wyRepairBudgetLabor, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyRepairBudgetLabor)){
			return form(wyRepairBudgetLabor, model);
		}
		wyRepairBudgetLaborService.save(wyRepairBudgetLabor);
		addMessage(redirectAttributes, "保存物业维修预算工时明细成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairBudgetLabor/?repage";
	}
	
	@RequiresPermissions("sns:wyRepairBudgetLabor:edit")
	@RequestMapping(value = "delete")
	public String delete(WyRepairBudgetLabor wyRepairBudgetLabor, RedirectAttributes redirectAttributes) {
		wyRepairBudgetLaborService.delete(wyRepairBudgetLabor);
		addMessage(redirectAttributes, "删除物业维修预算工时明细成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairBudgetLabor/?repage";
	}

}