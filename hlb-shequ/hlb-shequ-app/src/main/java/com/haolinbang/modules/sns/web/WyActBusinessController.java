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
import com.haolinbang.modules.sns.entity.WyActBusiness;
import com.haolinbang.modules.sns.service.WyActBusinessService;

/**
 * 工作流程和业务关联配置表Controller
 * @author nlp
 * @version 2017-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyActBusiness")
public class WyActBusinessController extends BaseController {

	@Autowired
	private WyActBusinessService wyActBusinessService;
	
	@ModelAttribute
	public WyActBusiness get(@RequestParam(required=false) String id) {
		WyActBusiness entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyActBusinessService.get(id);
		}
		if (entity == null){
			entity = new WyActBusiness();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyActBusiness:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyActBusiness wyActBusiness, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyActBusiness> page = wyActBusinessService.findPage(new Page<WyActBusiness>(request, response), wyActBusiness); 
		model.addAttribute("page", page);
		return "modules/sns/wyActBusinessList";
	}

	@RequiresPermissions("sns:wyActBusiness:view")
	@RequestMapping(value = "form")
	public String form(WyActBusiness wyActBusiness, Model model) {
		model.addAttribute("wyActBusiness", wyActBusiness);
		return "modules/sns/wyActBusinessForm";
	}

	@RequiresPermissions("sns:wyActBusiness:edit")
	@RequestMapping(value = "save")
	public String save(WyActBusiness wyActBusiness, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyActBusiness)){
			return form(wyActBusiness, model);
		}
		wyActBusinessService.save(wyActBusiness);
		addMessage(redirectAttributes, "保存工作流程和业务关联配置表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActBusiness/?repage";
	}
	
	@RequiresPermissions("sns:wyActBusiness:edit")
	@RequestMapping(value = "delete")
	public String delete(WyActBusiness wyActBusiness, RedirectAttributes redirectAttributes) {
		wyActBusinessService.delete(wyActBusiness);
		addMessage(redirectAttributes, "删除工作流程和业务关联配置表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActBusiness/?repage";
	}

}