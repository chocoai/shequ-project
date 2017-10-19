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
import com.haolinbang.modules.sns.entity.WyApproveDetail;
import com.haolinbang.modules.sns.service.WyApproveDetailService;

/**
 * 审批明细表Controller
 * @author nlp
 * @version 2017-06-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyApproveDetail")
public class WyApproveDetailController extends BaseController {

	@Autowired
	private WyApproveDetailService wyApproveDetailService;
	
	@ModelAttribute
	public WyApproveDetail get(@RequestParam(required=false) String id) {
		WyApproveDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyApproveDetailService.get(id);
		}
		if (entity == null){
			entity = new WyApproveDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyApproveDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyApproveDetail wyApproveDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyApproveDetail> page = wyApproveDetailService.findPage(new Page<WyApproveDetail>(request, response), wyApproveDetail); 
		model.addAttribute("page", page);
		return "modules/sns/wyApproveDetailList";
	}

	@RequiresPermissions("sns:wyApproveDetail:view")
	@RequestMapping(value = "form")
	public String form(WyApproveDetail wyApproveDetail, Model model) {
		model.addAttribute("wyApproveDetail", wyApproveDetail);
		return "modules/sns/wyApproveDetailForm";
	}

	@RequiresPermissions("sns:wyApproveDetail:edit")
	@RequestMapping(value = "save")
	public String save(WyApproveDetail wyApproveDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyApproveDetail)){
			return form(wyApproveDetail, model);
		}
		wyApproveDetailService.save(wyApproveDetail);
		addMessage(redirectAttributes, "保存审批明细表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyApproveDetail/?repage";
	}
	
	@RequiresPermissions("sns:wyApproveDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(WyApproveDetail wyApproveDetail, RedirectAttributes redirectAttributes) {
		wyApproveDetailService.delete(wyApproveDetail);
		addMessage(redirectAttributes, "删除审批明细表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyApproveDetail/?repage";
	}

}