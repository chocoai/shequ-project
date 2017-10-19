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

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.utils.ArithmeticUtil;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.entity.WyRepairBudgetMateriel;
import com.haolinbang.modules.sns.service.WyRepairBudgetMaterielService;

/**
 * 物业维修预算物料明细Controller
 * 
 * @author nlp
 * @version 2017-05-18
 */
@Controller
@RequestMapping(value = "/sns/wyRepairBudgetMateriel")
public class WyRepairBudgetMaterielController extends BaseController {

	@Autowired
	private WyRepairBudgetMaterielService wyRepairBudgetMaterielService;

	@ModelAttribute
	public WyRepairBudgetMateriel get(@RequestParam(required = false) String id) {
		WyRepairBudgetMateriel entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyRepairBudgetMaterielService.get(id);
		}
		if (entity == null) {
			entity = new WyRepairBudgetMateriel();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(WyRepairBudgetMateriel wyRepairBudgetMateriel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyRepairBudgetMateriel> page = wyRepairBudgetMaterielService.findPage(new Page<WyRepairBudgetMateriel>(request, response), wyRepairBudgetMateriel);
		model.addAttribute("page", page);
		return "modules/sns/wyRepairBudgetMaterielList";
	}

	@RequestMapping(value = "form")
	public String form(WyRepairBudgetMateriel wyRepairBudgetMateriel, Model model) {
		if (StringUtils.isNotBlank(wyRepairBudgetMateriel.getId())) {
			wyRepairBudgetMateriel = wyRepairBudgetMaterielService.get(wyRepairBudgetMateriel.getId());
		}
		model.addAttribute("wyRepairBudgetMateriel", wyRepairBudgetMateriel);
		return "modules/sns/wyRepairBudgetMaterielForm";
	}

	@RequestMapping(value = "view")
	public String view(String id, Model model) {
		WyRepairBudgetMateriel wyRepairBudgetMateriel = wyRepairBudgetMaterielService.get(id);
		model.addAttribute("wyRepairBudgetMateriel", wyRepairBudgetMateriel);
		return "modules/sns/wyRepairBudgetMaterielFormView";
	}

	@RequestMapping(value = "save")
	public String save(WyRepairBudgetMateriel wyRepairBudgetMateriel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyRepairBudgetMateriel)) {
			return form(wyRepairBudgetMateriel, model);
		}
		wyRepairBudgetMateriel.setCount(ArithmeticUtil.mul2(wyRepairBudgetMateriel.getPrice(), wyRepairBudgetMateriel.getNum(), ArithmeticUtil.ROUND_SCALE_2));
		wyRepairBudgetMaterielService.save(wyRepairBudgetMateriel);
		addMessage(redirectAttributes, "保存物业维修预算物料明细成功");
		return "redirect:/wyPrivateRepair/budget?bizId=" + wyRepairBudgetMateriel.getRepairId();
	}

	@ResponseBody
	@RequestMapping(value = "deleteById")
	public WeJson deleteById(String id) {
		boolean b = wyRepairBudgetMaterielService.deleteById(id);
		if (b) {
			return WeJson.success(b);
		}
		return WeJson.fail("删除失败");
	}

}