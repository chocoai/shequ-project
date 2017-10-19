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
import com.haolinbang.modules.sys.entity.AppUserScope;
import com.haolinbang.modules.sys.service.AppUserScopeService;

/**
 * 用户使用数据范围Controller
 * @author nlp
 * @version 2017-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/appUserScope")
public class AppUserScopeController extends BaseController {

	@Autowired
	private AppUserScopeService appUserScopeService;
	
	@ModelAttribute
	public AppUserScope get(@RequestParam(required=false) String id) {
		AppUserScope entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = appUserScopeService.get(id);
		}
		if (entity == null){
			entity = new AppUserScope();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:appUserScope:view")
	@RequestMapping(value = {"list", ""})
	public String list(AppUserScope appUserScope, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AppUserScope> page = appUserScopeService.findPage(new Page<AppUserScope>(request, response), appUserScope); 
		model.addAttribute("page", page);
		return "modules/sys/appUserScopeList";
	}

	@RequiresPermissions("sys:appUserScope:view")
	@RequestMapping(value = "form")
	public String form(AppUserScope appUserScope, Model model) {
		model.addAttribute("appUserScope", appUserScope);
		return "modules/sys/appUserScopeForm";
	}

	@RequiresPermissions("sys:appUserScope:edit")
	@RequestMapping(value = "save")
	public String save(AppUserScope appUserScope, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appUserScope)){
			return form(appUserScope, model);
		}
		appUserScopeService.save(appUserScope);
		addMessage(redirectAttributes, "保存用户使用数据范围成功");
		return "redirect:"+Global.getAdminPath()+"/sys/appUserScope/?repage";
	}
	
	@RequiresPermissions("sys:appUserScope:edit")
	@RequestMapping(value = "delete")
	public String delete(AppUserScope appUserScope, RedirectAttributes redirectAttributes) {
		appUserScopeService.delete(appUserScope);
		addMessage(redirectAttributes, "删除用户使用数据范围成功");
		return "redirect:"+Global.getAdminPath()+"/sys/appUserScope/?repage";
	}

}