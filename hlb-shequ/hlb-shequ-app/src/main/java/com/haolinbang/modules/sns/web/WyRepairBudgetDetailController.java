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
import com.haolinbang.modules.sns.entity.WyRepairBudgetDetail;
import com.haolinbang.modules.sns.service.WyRepairBudgetDetailService;

/**
 * 物业维修预算明细Controller
 * @author nlp
 * @version 2017-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyRepairBudgetDetail")
public class WyRepairBudgetDetailController extends BaseController {

	@Autowired
	private WyRepairBudgetDetailService wyRepairBudgetDetailService;
	
	@ModelAttribute
	public WyRepairBudgetDetail get(@RequestParam(required=false) String id) {
		WyRepairBudgetDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyRepairBudgetDetailService.get(id);
		}
		if (entity == null){
			entity = new WyRepairBudgetDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyRepairBudgetDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyRepairBudgetDetail wyRepairBudgetDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyRepairBudgetDetail> page = wyRepairBudgetDetailService.findPage(new Page<WyRepairBudgetDetail>(request, response), wyRepairBudgetDetail); 
		model.addAttribute("page", page);
		return "modules/sns/wyRepairBudgetDetailList";
	}

	@RequiresPermissions("sns:wyRepairBudgetDetail:view")
	@RequestMapping(value = "form")
	public String form(WyRepairBudgetDetail wyRepairBudgetDetail, Model model) {
		model.addAttribute("wyRepairBudgetDetail", wyRepairBudgetDetail);
		return "modules/sns/wyRepairBudgetDetailForm";
	}

	@RequiresPermissions("sns:wyRepairBudgetDetail:edit")
	@RequestMapping(value = "save")
	public String save(WyRepairBudgetDetail wyRepairBudgetDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyRepairBudgetDetail)){
			return form(wyRepairBudgetDetail, model);
		}
		wyRepairBudgetDetailService.save(wyRepairBudgetDetail);
		addMessage(redirectAttributes, "保存物业维修预算明细成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairBudgetDetail/?repage";
	}
	
	@RequiresPermissions("sns:wyRepairBudgetDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(WyRepairBudgetDetail wyRepairBudgetDetail, RedirectAttributes redirectAttributes) {
		wyRepairBudgetDetailService.delete(wyRepairBudgetDetail);
		addMessage(redirectAttributes, "删除物业维修预算明细成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyRepairBudgetDetail/?repage";
	}

}