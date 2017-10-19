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
import com.haolinbang.modules.sns.entity.WyRepairSettlement;
import com.haolinbang.modules.sns.service.WyRepairSettlementService;

/**
 * 物业维修核算汇总Controller
 * @author nlp
 * @version 2017-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyRepairSettlement")
public class WyRepairSettlementController extends BaseController {

	@Autowired
	private WyRepairSettlementService wyRepairSettlementService;
	
	@ModelAttribute
	public WyRepairSettlement get(@RequestParam(required=false) String id) {
		WyRepairSettlement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyRepairSettlementService.get(id);
		}
		if (entity == null){
			entity = new WyRepairSettlement();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyRepairSettlement:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyRepairSettlement wyRepairSettlement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyRepairSettlement> page = wyRepairSettlementService.findPage(new Page<WyRepairSettlement>(request, response), wyRepairSettlement); 
		model.addAttribute("page", page);
		return "modules/sns/wyRepairSettlementList";
	}

	@RequiresPermissions("sns:wyRepairSettlement:view")
	@RequestMapping(value = "form")
	public String form(WyRepairSettlement wyRepairSettlement, Model model) {
		model.addAttribute("wyRepairSettlement", wyRepairSettlement);
		return "modules/sns/wyRepairSettlementForm";
	}

	@RequiresPermissions("sns:wyRepairSettlement:edit")
	@RequestMapping(value = "save")
	public String save(WyRepairSettlement wyRepairSettlement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyRepairSettlement)){
			return form(wyRepairSettlement, model);
		}
		wyRepairSettlementService.save(wyRepairSettlement);
		addMessage(redirectAttributes, "保存物业维修核算汇总成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairSettlement/?repage";
	}
	
	@RequiresPermissions("sns:wyRepairSettlement:edit")
	@RequestMapping(value = "delete")
	public String delete(WyRepairSettlement wyRepairSettlement, RedirectAttributes redirectAttributes) {
		wyRepairSettlementService.delete(wyRepairSettlement);
		addMessage(redirectAttributes, "删除物业维修核算汇总成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairSettlement/?repage";
	}

}