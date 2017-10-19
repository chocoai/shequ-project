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
import com.haolinbang.modules.sns.entity.WyRepairSettlementLabor;
import com.haolinbang.modules.sns.service.WyRepairSettlementLaborService;

/**
 * 物业维修核算工时明细Controller
 * 
 * @author nlp
 * @version 2017-05-18
 */
@Controller
@RequestMapping(value = "/sns/wyRepairSettlementLabor")
public class WyRepairSettlementLaborController extends BaseController {

	@Autowired
	private WyRepairSettlementLaborService wyRepairSettlementLaborService;

	@ModelAttribute
	public WyRepairSettlementLabor get(@RequestParam(required = false) String id) {
		WyRepairSettlementLabor entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyRepairSettlementLaborService.get(id);
		}
		if (entity == null) {
			entity = new WyRepairSettlementLabor();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(WyRepairSettlementLabor wyRepairSettlementLabor, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyRepairSettlementLabor> page = wyRepairSettlementLaborService.findPage(new Page<WyRepairSettlementLabor>(request, response), wyRepairSettlementLabor);
		model.addAttribute("page", page);
		return "modules/sns/wyRepairSettlementLaborList";
	}

	@RequestMapping(value = "form")
	public String form(WyRepairSettlementLabor wyRepairSettlementLabor, Model model) {
		model.addAttribute("wyRepairSettlementLabor", wyRepairSettlementLabor);
		return "modules/sns/wyRepairSettlementLaborForm";
	}

	@RequestMapping(value = "save")
	public String save(WyRepairSettlementLabor wyRepairSettlementLabor, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyRepairSettlementLabor)) {
			return form(wyRepairSettlementLabor, model);
		}
		wyRepairSettlementLaborService.save(wyRepairSettlementLabor);
		addMessage(redirectAttributes, "保存物业维修核算工时明细成功");

		return "redirect:/wyPrivateRepair/settlement?bizId=" + wyRepairSettlementLabor.getRepairId();
	}

	@RequestMapping(value = "delete")
	public String delete(WyRepairSettlementLabor wyRepairSettlementLabor, RedirectAttributes redirectAttributes) {
		wyRepairSettlementLaborService.delete(wyRepairSettlementLabor);
		addMessage(redirectAttributes, "删除物业维修核算工时明细成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyRepairSettlementLabor/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "deleteById")
	public WeJson deleteById(String id) {
		boolean b = wyRepairSettlementLaborService.deleteById(id);
		if (b) {
			return WeJson.success(b);
		}
		return WeJson.fail("删除失败");
	}

	@RequestMapping(value = "view")
	public String view(String id, Model model) {
		WyRepairSettlementLabor wyRepairSettlementLabor = wyRepairSettlementLaborService.get(id);
		model.addAttribute("wyRepairSettlementLabor", wyRepairSettlementLabor);
		return "modules/sns/wyRepairSettlementLaborFormView";
	}

}