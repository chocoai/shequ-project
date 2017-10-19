package com.haolinbang.modules.sys.web;

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
import com.haolinbang.modules.sys.entity.Hlbregister;
import com.haolinbang.modules.sys.service.HlbregisterService;

/**
 * 注册表Controller
 * @author nlp
 * @version 2017-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/hlbregister")
public class HlbregisterController extends BaseController {

	@Autowired
	private HlbregisterService hlbregisterService;
	
	@ModelAttribute
	public Hlbregister get(@RequestParam(required=false) String id) {
		Hlbregister entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hlbregisterService.get(id);
		}
		if (entity == null){
			entity = new Hlbregister();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:hlbregister:view")
	@RequestMapping(value = {"list", ""})
	public String list(Hlbregister hlbregister, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Hlbregister> page = hlbregisterService.findPage(new Page<Hlbregister>(request, response), hlbregister); 
		model.addAttribute("page", page);
		return "modules/sys/hlbregisterList";
	}

	@RequiresPermissions("sys:hlbregister:view")
	@RequestMapping(value = "form")
	public String form(Hlbregister hlbregister, Model model) {
		model.addAttribute("hlbregister", hlbregister);
		return "modules/sys/hlbregisterForm";
	}

	@RequiresPermissions("sys:hlbregister:edit")
	@RequestMapping(value = "save")
	public String save(Hlbregister hlbregister, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hlbregister)){
			return form(hlbregister, model);
		}
		hlbregisterService.save(hlbregister);
		addMessage(redirectAttributes, "保存注册表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/hlbregister/?repage";
	}
	
	@RequiresPermissions("sys:hlbregister:edit")
	@RequestMapping(value = "delete")
	public String delete(Hlbregister hlbregister, RedirectAttributes redirectAttributes) {
		hlbregisterService.delete(hlbregister);
		addMessage(redirectAttributes, "删除注册表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/hlbregister/?repage";
	}

}