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
import com.haolinbang.modules.sns.entity.WyActHandler;
import com.haolinbang.modules.sns.service.WyActHandlerService;

/**
 * 流程处理后调用方法Controller
 * @author nlp
 * @version 2017-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyActHandler")
public class WyActHandlerController extends BaseController {

	@Autowired
	private WyActHandlerService wyActHandlerService;
	
	@ModelAttribute
	public WyActHandler get(@RequestParam(required=false) String id) {
		WyActHandler entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyActHandlerService.get(id);
		}
		if (entity == null){
			entity = new WyActHandler();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyActHandler:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyActHandler wyActHandler, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyActHandler> page = wyActHandlerService.findPage(new Page<WyActHandler>(request, response), wyActHandler); 
		model.addAttribute("page", page);
		return "modules/sns/wyActHandlerList";
	}

	@RequiresPermissions("sns:wyActHandler:view")
	@RequestMapping(value = "form")
	public String form(WyActHandler wyActHandler, Model model) {
		model.addAttribute("wyActHandler", wyActHandler);
		return "modules/sns/wyActHandlerForm";
	}

	@RequiresPermissions("sns:wyActHandler:edit")
	@RequestMapping(value = "save")
	public String save(WyActHandler wyActHandler, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyActHandler)){
			return form(wyActHandler, model);
		}
		wyActHandlerService.save(wyActHandler);
		addMessage(redirectAttributes, "保存流程处理后调用方法成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActHandler/?repage";
	}
	
	@RequiresPermissions("sns:wyActHandler:edit")
	@RequestMapping(value = "delete")
	public String delete(WyActHandler wyActHandler, RedirectAttributes redirectAttributes) {
		wyActHandlerService.delete(wyActHandler);
		addMessage(redirectAttributes, "删除流程处理后调用方法成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActHandler/?repage";
	}

}