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
import com.haolinbang.modules.sns.entity.WyRepairBudgetMateriel;
import com.haolinbang.modules.sns.service.WyRepairBudgetMaterielService;

/**
 * 物业维修预算物料明细Controller
 * @author nlp
 * @version 2017-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyRepairBudgetMateriel")
public class WyRepairBudgetMaterielController extends BaseController {

	@Autowired
	private WyRepairBudgetMaterielService wyRepairBudgetMaterielService;
	
	@ModelAttribute
	public WyRepairBudgetMateriel get(@RequestParam(required=false) String id) {
		WyRepairBudgetMateriel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyRepairBudgetMaterielService.get(id);
		}
		if (entity == null){
			entity = new WyRepairBudgetMateriel();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyRepairBudgetMateriel:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyRepairBudgetMateriel wyRepairBudgetMateriel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyRepairBudgetMateriel> page = wyRepairBudgetMaterielService.findPage(new Page<WyRepairBudgetMateriel>(request, response), wyRepairBudgetMateriel); 
		model.addAttribute("page", page);
		return "modules/sns/wyRepairBudgetMaterielList";
	}

	@RequiresPermissions("sns:wyRepairBudgetMateriel:view")
	@RequestMapping(value = "form")
	public String form(WyRepairBudgetMateriel wyRepairBudgetMateriel, Model model) {
		model.addAttribute("wyRepairBudgetMateriel", wyRepairBudgetMateriel);
		return "modules/sns/wyRepairBudgetMaterielForm";
	}

	@RequiresPermissions("sns:wyRepairBudgetMateriel:edit")
	@RequestMapping(value = "save")
	public String save(WyRepairBudgetMateriel wyRepairBudgetMateriel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyRepairBudgetMateriel)){
			return form(wyRepairBudgetMateriel, model);
		}
		wyRepairBudgetMaterielService.save(wyRepairBudgetMateriel);
		addMessage(redirectAttributes, "保存物业维修预算物料明细成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairBudgetMateriel/?repage";
	}
	
	@RequiresPermissions("sns:wyRepairBudgetMateriel:edit")
	@RequestMapping(value = "delete")
	public String delete(WyRepairBudgetMateriel wyRepairBudgetMateriel, RedirectAttributes redirectAttributes) {
		wyRepairBudgetMaterielService.delete(wyRepairBudgetMateriel);
		addMessage(redirectAttributes, "删除物业维修预算物料明细成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairBudgetMateriel/?repage";
	}

}