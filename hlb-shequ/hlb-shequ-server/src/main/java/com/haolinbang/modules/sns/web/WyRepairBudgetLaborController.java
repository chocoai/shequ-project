package com.haolinbang.modules.sns.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.haolinbang.modules.sns.entity.WyRepairBudgetLabor;
import com.haolinbang.modules.sns.service.WyRepairBudgetLaborService;

/**
 * 物业维修预算工时明细Controller
 * 
 * @author nlp
 * @version 2017-05-18
 */
@Controller
@RequestMapping(value = "/sns/wyRepairBudgetLabor")
public class WyRepairBudgetLaborController extends BaseController {

	@Autowired
	private WyRepairBudgetLaborService wyRepairBudgetLaborService;

	@ModelAttribute
	public WyRepairBudgetLabor get(@RequestParam(required = false) String id) {
		WyRepairBudgetLabor entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyRepairBudgetLaborService.get(id);
		}
		if (entity == null) {
			entity = new WyRepairBudgetLabor();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(WyRepairBudgetLabor wyRepairBudgetLabor, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyRepairBudgetLabor> page = wyRepairBudgetLaborService.findPage(new Page<WyRepairBudgetLabor>(request, response), wyRepairBudgetLabor);
		model.addAttribute("page", page);
		return "modules/sns/wyRepairBudgetLaborList";
	}

	@RequestMapping(value = "view")
	public String view(String id, Model model) {
		WyRepairBudgetLabor wyRepairBudgetLabor = wyRepairBudgetLaborService.get(id);
		model.addAttribute("wyRepairBudgetLabor", wyRepairBudgetLabor);
		return "modules/sns/wyRepairBudgetLaborFormView";
	}

	@RequestMapping(value = "form")
	public String form(WyRepairBudgetLabor wyRepairBudgetLabor, Model model) {
		model.addAttribute("wyRepairBudgetLabor", wyRepairBudgetLabor);
		return "modules/sns/wyRepairBudgetLaborForm";
	}

	@RequestMapping(value = "save")
	public String save(WyRepairBudgetLabor wyRepairBudgetLabor, Model model) {
		if (!beanValidator(model, wyRepairBudgetLabor)) {
			return form(wyRepairBudgetLabor, model);
		}
		
		wyRepairBudgetLaborService.save(wyRepairBudgetLabor);
		return "redirect:/wyPrivateRepair/budget?bizId=" + wyRepairBudgetLabor.getRepairId();
	}

	@ResponseBody
	@RequestMapping(value = "deleteById")
	public WeJson deleteById(String id) {
		boolean b = wyRepairBudgetLaborService.deleteById(id);
		if(b){
			return WeJson.success(b);
		}
		return WeJson.fail("删除失败");
	}

}