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
import com.haolinbang.modules.sns.entity.WyRepairSettlementDetail;
import com.haolinbang.modules.sns.service.WyRepairSettlementDetailService;

/**
 * 物业维修核算明细Controller
 * @author nlp
 * @version 2017-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyRepairSettlementDetail")
public class WyRepairSettlementDetailController extends BaseController {

	@Autowired
	private WyRepairSettlementDetailService wyRepairSettlementDetailService;
	
	@ModelAttribute
	public WyRepairSettlementDetail get(@RequestParam(required=false) String id) {
		WyRepairSettlementDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyRepairSettlementDetailService.get(id);
		}
		if (entity == null){
			entity = new WyRepairSettlementDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyRepairSettlementDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyRepairSettlementDetail wyRepairSettlementDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyRepairSettlementDetail> page = wyRepairSettlementDetailService.findPage(new Page<WyRepairSettlementDetail>(request, response), wyRepairSettlementDetail); 
		model.addAttribute("page", page);
		return "modules/sns/wyRepairSettlementDetailList";
	}

	@RequiresPermissions("sns:wyRepairSettlementDetail:view")
	@RequestMapping(value = "form")
	public String form(WyRepairSettlementDetail wyRepairSettlementDetail, Model model) {
		model.addAttribute("wyRepairSettlementDetail", wyRepairSettlementDetail);
		return "modules/sns/wyRepairSettlementDetailForm";
	}

	@RequiresPermissions("sns:wyRepairSettlementDetail:edit")
	@RequestMapping(value = "save")
	public String save(WyRepairSettlementDetail wyRepairSettlementDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyRepairSettlementDetail)){
			return form(wyRepairSettlementDetail, model);
		}
		wyRepairSettlementDetailService.save(wyRepairSettlementDetail);
		addMessage(redirectAttributes, "保存物业维修核算明细成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairSettlementDetail/?repage";
	}
	
	@RequiresPermissions("sns:wyRepairSettlementDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(WyRepairSettlementDetail wyRepairSettlementDetail, RedirectAttributes redirectAttributes) {
		wyRepairSettlementDetailService.delete(wyRepairSettlementDetail);
		addMessage(redirectAttributes, "删除物业维修核算明细成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairSettlementDetail/?repage";
	}

}