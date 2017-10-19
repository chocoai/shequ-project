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
import com.haolinbang.modules.sys.entity.RoleMenu;
import com.haolinbang.modules.sys.service.RoleMenuService;

/**
 * 角色-菜单Controller
 * @author nlp
 * @version 2017-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/roleMenu")
public class RoleMenuController extends BaseController {

	@Autowired
	private RoleMenuService roleMenuService;
	
	@ModelAttribute
	public RoleMenu get(@RequestParam(required=false) String id) {
		RoleMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = roleMenuService.get(id);
		}
		if (entity == null){
			entity = new RoleMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:roleMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(RoleMenu roleMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RoleMenu> page = roleMenuService.findPage(new Page<RoleMenu>(request, response), roleMenu); 
		model.addAttribute("page", page);
		return "modules/sys/roleMenuList";
	}

	@RequiresPermissions("sys:roleMenu:view")
	@RequestMapping(value = "form")
	public String form(RoleMenu roleMenu, Model model) {
		model.addAttribute("roleMenu", roleMenu);
		return "modules/sys/roleMenuForm";
	}

	@RequiresPermissions("sys:roleMenu:edit")
	@RequestMapping(value = "save")
	public String save(RoleMenu roleMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, roleMenu)){
			return form(roleMenu, model);
		}
		roleMenuService.save(roleMenu);
		addMessage(redirectAttributes, "保存角色-菜单成功");
		return "redirect:"+Global.getAdminPath()+"/sys/roleMenu/?repage";
	}
	
	@RequiresPermissions("sys:roleMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(RoleMenu roleMenu, RedirectAttributes redirectAttributes) {
		roleMenuService.delete(roleMenu);
		addMessage(redirectAttributes, "删除角色-菜单成功");
		return "redirect:"+Global.getAdminPath()+"/sys/roleMenu/?repage";
	}

}