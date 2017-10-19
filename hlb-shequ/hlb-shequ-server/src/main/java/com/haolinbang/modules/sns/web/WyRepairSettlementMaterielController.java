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
import com.haolinbang.modules.sns.entity.WyRepairSettlementMateriel;
import com.haolinbang.modules.sns.service.WyRepairSettlementMaterielService;

/**
 * 物业维修核算物料明细Controller
 * 
 * @author nlp
 * @version 2017-05-18
 */
@Controller
@RequestMapping(value = "/sns/wyRepairSettlementMateriel")
public class WyRepairSettlementMaterielController extends BaseController {

	@Autowired
	private WyRepairSettlementMaterielService wyRepairSettlementMaterielService;

	@ModelAttribute
	public WyRepairSettlementMateriel get(@RequestParam(required = false) String id) {
		WyRepairSettlementMateriel entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyRepairSettlementMaterielService.get(id);
		}
		if (entity == null) {
			entity = new WyRepairSettlementMateriel();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(WyRepairSettlementMateriel wyRepairSettlementMateriel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyRepairSettlementMateriel> page = wyRepairSettlementMaterielService.findPage(new Page<WyRepairSettlementMateriel>(request, response), wyRepairSettlementMateriel);
		model.addAttribute("page", page);
		return "modules/sns/wyRepairSettlementMaterielList";
	}

	@RequestMapping(value = "form")
	public String form(WyRepairSettlementMateriel wyRepairSettlementMateriel, Model model) {
		model.addAttribute("wyRepairSettlementMateriel", wyRepairSettlementMateriel);
		return "modules/sns/wyRepairSettlementMaterielForm";
	}

	@RequestMapping(value = "save")
	public String save(WyRepairSettlementMateriel wyRepairSettlementMateriel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyRepairSettlementMateriel)) {
			return form(wyRepairSettlementMateriel, model);
		}
		wyRepairSettlementMateriel.setCount(ArithmeticUtil.mul2(wyRepairSettlementMateriel.getPrice(), wyRepairSettlementMateriel.getNum(), ArithmeticUtil.ROUND_SCALE_2));
		wyRepairSettlementMaterielService.save(wyRepairSettlementMateriel);
		addMessage(redirectAttributes, "保存物业维修核算物料明细成功");

		return "redirect:/wyPrivateRepair/settlement?bizId=" + wyRepairSettlementMateriel.getRepairId();
	}

	@ResponseBody
	@RequestMapping(value = "deleteById")
	public WeJson deleteById(String id) {
		boolean b = wyRepairSettlementMaterielService.deleteById(id);
		if (b) {
			return WeJson.success(b);
		}
		return WeJson.fail("删除失败");
	}

	@RequestMapping(value = "view")
	public String view(String id, Model model) {
		WyRepairSettlementMateriel wyRepairSettlementMateriel = wyRepairSettlementMaterielService.get(id);
		model.addAttribute("wyRepairSettlementMateriel", wyRepairSettlementMateriel);
		return "modules/sns/wyRepairSettlementMaterielFormView";
	}

}