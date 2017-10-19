package com.haolinbang.modules.sns.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.entity.WyRepair;
import com.haolinbang.modules.sns.service.WyRepairService;

/**
 * 物业报修Controller
 * 
 * @author nlp
 * @version 2017-04-17
 */
@Controller
@RequestMapping(value = "/sns/wyRepair")
public class WyRepairController extends BaseController {

	@Autowired
	private WyRepairService wyRepairService;

	@ModelAttribute
	public WyRepair get(@RequestParam(required = false) String id) {
		WyRepair entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyRepairService.get(id);
		}
		if (entity == null) {
			entity = new WyRepair();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(WyRepair wyRepair, HttpServletRequest request, HttpServletResponse response, Model model) {

		// Page<WyRepair> page = wyRepairService.findPage(new
		// Page<WyRepair>(request, response), wyRepair);

		List<WyRepair> wyRepairs = wyRepairService.findList(wyRepair);
		
		model.addAttribute("wyRepairs", wyRepairs);

		return "modules/sns/wyRepairList";
	}

	@RequestMapping(value = "form")
	public String form(WyRepair wyRepair, Model model) {
		model.addAttribute("wyRepair", wyRepair);
		return "modules/sns/wyRepairForm";
	}

	@RequestMapping(value = "save")
	public String save(WyRepair wyRepair, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyRepair)) {
			return form(wyRepair, model);
		}
		wyRepairService.save(wyRepair);
		addMessage(redirectAttributes, "保存物业报修成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyRepair/?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(WyRepair wyRepair, RedirectAttributes redirectAttributes) {
		wyRepairService.delete(wyRepair);
		addMessage(redirectAttributes, "删除物业报修成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyRepair/?repage";
	}

}