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
import com.haolinbang.modules.sys.entity.AppUserPermission;
import com.haolinbang.modules.sys.service.AppUserPermissionService;

/**
 * 用户权限表Controller
 * @author nlp
 * @version 2017-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/appUserPermission")
public class AppUserPermissionController extends BaseController {

	@Autowired
	private AppUserPermissionService appUserPermissionService;
	
	@ModelAttribute
	public AppUserPermission get(@RequestParam(required=false) String id) {
		AppUserPermission entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = appUserPermissionService.get(id);
		}
		if (entity == null){
			entity = new AppUserPermission();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:appUserPermission:view")
	@RequestMapping(value = {"list", ""})
	public String list(AppUserPermission appUserPermission, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AppUserPermission> page = appUserPermissionService.findPage(new Page<AppUserPermission>(request, response), appUserPermission); 
		model.addAttribute("page", page);
		return "modules/sys/appUserPermissionList";
	}

	@RequiresPermissions("sys:appUserPermission:view")
	@RequestMapping(value = "form")
	public String form(AppUserPermission appUserPermission, Model model) {
		model.addAttribute("appUserPermission", appUserPermission);
		return "modules/sys/appUserPermissionForm";
	}

	@RequiresPermissions("sys:appUserPermission:edit")
	@RequestMapping(value = "save")
	public String save(AppUserPermission appUserPermission, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appUserPermission)){
			return form(appUserPermission, model);
		}
		appUserPermissionService.save(appUserPermission);
		addMessage(redirectAttributes, "保存用户权限表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/appUserPermission/?repage";
	}
	
	@RequiresPermissions("sys:appUserPermission:edit")
	@RequestMapping(value = "delete")
	public String delete(AppUserPermission appUserPermission, RedirectAttributes redirectAttributes) {
		appUserPermissionService.delete(appUserPermission);
		addMessage(redirectAttributes, "删除用户权限表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/appUserPermission/?repage";
	}

}