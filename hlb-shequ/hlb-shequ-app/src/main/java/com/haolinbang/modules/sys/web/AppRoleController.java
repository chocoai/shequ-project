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
import com.haolinbang.modules.sys.entity.AppRole;
import com.haolinbang.modules.sys.service.AppRoleService;

/**
 * 应用平台角色Controller
 * @author nlp
 * @version 2017-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/appRole")
public class AppRoleController extends BaseController {

	@Autowired
	private AppRoleService appRoleService;
	
	@ModelAttribute
	public AppRole get(@RequestParam(required=false) String id) {
		AppRole entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = appRoleService.get(id);
		}
		if (entity == null){
			entity = new AppRole();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:appRole:view")
	@RequestMapping(value = {"list", ""})
	public String list(AppRole appRole, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AppRole> page = appRoleService.findPage(new Page<AppRole>(request, response), appRole); 
		model.addAttribute("page", page);
		return "modules/sys/appRoleList";
	}

	@RequiresPermissions("sys:appRole:view")
	@RequestMapping(value = "form")
	public String form(AppRole appRole, Model model) {
		model.addAttribute("appRole", appRole);
		return "modules/sys/appRoleForm";
	}

	@RequiresPermissions("sys:appRole:edit")
	@RequestMapping(value = "save")
	public String save(AppRole appRole, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appRole)){
			return form(appRole, model);
		}
		appRoleService.save(appRole);
		addMessage(redirectAttributes, "保存应用平台角色成功");
		return "redirect:"+Global.getAdminPath()+"/sys/appRole/?repage";
	}
	
	@RequiresPermissions("sys:appRole:edit")
	@RequestMapping(value = "delete")
	public String delete(AppRole appRole, RedirectAttributes redirectAttributes) {
		appRoleService.delete(appRole);
		addMessage(redirectAttributes, "删除应用平台角色成功");
		return "redirect:"+Global.getAdminPath()+"/sys/appRole/?repage";
	}

}