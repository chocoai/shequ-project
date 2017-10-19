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
import com.haolinbang.modules.sys.entity.AppRoleMenu;
import com.haolinbang.modules.sys.service.AppRoleMenuService;

/**
 * 角色菜单表Controller
 * @author nlp
 * @version 2017-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/appRoleMenu")
public class AppRoleMenuController extends BaseController {

	@Autowired
	private AppRoleMenuService appRoleMenuService;
	
	@ModelAttribute
	public AppRoleMenu get(@RequestParam(required=false) String id) {
		AppRoleMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = appRoleMenuService.get(id);
		}
		if (entity == null){
			entity = new AppRoleMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:appRoleMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(AppRoleMenu appRoleMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AppRoleMenu> page = appRoleMenuService.findPage(new Page<AppRoleMenu>(request, response), appRoleMenu); 
		model.addAttribute("page", page);
		return "modules/sys/appRoleMenuList";
	}

	@RequiresPermissions("sys:appRoleMenu:view")
	@RequestMapping(value = "form")
	public String form(AppRoleMenu appRoleMenu, Model model) {
		model.addAttribute("appRoleMenu", appRoleMenu);
		return "modules/sys/appRoleMenuForm";
	}

	@RequiresPermissions("sys:appRoleMenu:edit")
	@RequestMapping(value = "save")
	public String save(AppRoleMenu appRoleMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appRoleMenu)){
			return form(appRoleMenu, model);
		}
		appRoleMenuService.save(appRoleMenu);
		addMessage(redirectAttributes, "保存角色菜单表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/appRoleMenu/?repage";
	}
	
	@RequiresPermissions("sys:appRoleMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(AppRoleMenu appRoleMenu, RedirectAttributes redirectAttributes) {
		appRoleMenuService.delete(appRoleMenu);
		addMessage(redirectAttributes, "删除角色菜单表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/appRoleMenu/?repage";
	}

}