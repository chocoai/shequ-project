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
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sys.entity.AppUserRole;
import com.haolinbang.modules.sys.service.AppUserRoleService;

/**
 * 用户角色表Controller
 * 
 * @author nlp
 * @version 2017-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/appUserRole")
public class AppUserRoleController extends BaseController {

	@Autowired
	private AppUserRoleService appUserRoleService;

	@ModelAttribute
	public AppUserRole get(@RequestParam(required = false) String id) {
		AppUserRole entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = appUserRoleService.get(id);
		}
		if (entity == null) {
			entity = new AppUserRole();
		}
		return entity;
	}

	@RequiresPermissions("sys:appUserRole:view")
	@RequestMapping(value = { "list", "" })
	public String list(AppUserRole appUserRole, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AppUserRole> page = appUserRoleService.findPage(new Page<AppUserRole>(request, response), appUserRole);
		model.addAttribute("page", page);
		return "modules/sys/appUserRoleList";
	}

	@RequiresPermissions("sys:appUserRole:view")
	@RequestMapping(value = "form")
	public String form(AppUserRole appUserRole, Model model) {

		model.addAttribute("appUserRole", appUserRole);
		return "modules/sys/appUserRoleForm";
	}

	@RequiresPermissions("sys:appUserRole:edit")
	@RequestMapping(value = "save")
	public String save(AppUserRole appUserRole, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appUserRole)) {
			return form(appUserRole, model);
		}
		appUserRoleService.save(appUserRole);
		addMessage(redirectAttributes, "保存用户角色表成功");
		return "redirect:" + Global.getAdminPath() + "/sys/appUserRole/?repage";
	}

	@RequiresPermissions("sys:appUserRole:edit")
	@RequestMapping(value = "delete")
	public String delete(AppUserRole appUserRole, RedirectAttributes redirectAttributes) {
		appUserRoleService.delete(appUserRole);
		addMessage(redirectAttributes, "删除用户角色表成功");
		return "redirect:" + Global.getAdminPath() + "/sys/appUserRole/?repage";
	}

}