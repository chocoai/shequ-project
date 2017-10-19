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
import com.haolinbang.modules.sys.entity.UserRole;
import com.haolinbang.modules.sys.service.UserRoleService;

/**
 * 用户-角色Controller
 * @author nlp
 * @version 2017-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/userRole")
public class UserRoleController extends BaseController {

	@Autowired
	private UserRoleService userRoleService;
	
	@ModelAttribute
	public UserRole get(@RequestParam(required=false) String id) {
		UserRole entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userRoleService.get(id);
		}
		if (entity == null){
			entity = new UserRole();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:userRole:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserRole userRole, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserRole> page = userRoleService.findPage(new Page<UserRole>(request, response), userRole); 
		model.addAttribute("page", page);
		return "modules/sys/userRoleList";
	}

	@RequiresPermissions("sys:userRole:view")
	@RequestMapping(value = "form")
	public String form(UserRole userRole, Model model) {
		model.addAttribute("userRole", userRole);
		return "modules/sys/userRoleForm";
	}

	@RequiresPermissions("sys:userRole:edit")
	@RequestMapping(value = "save")
	public String save(UserRole userRole, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userRole)){
			return form(userRole, model);
		}
		userRoleService.save(userRole);
		addMessage(redirectAttributes, "保存用户-角色成功");
		return "redirect:"+Global.getAdminPath()+"/sys/userRole/?repage";
	}
	
	@RequiresPermissions("sys:userRole:edit")
	@RequestMapping(value = "delete")
	public String delete(UserRole userRole, RedirectAttributes redirectAttributes) {
		userRoleService.delete(userRole);
		addMessage(redirectAttributes, "删除用户-角色成功");
		return "redirect:"+Global.getAdminPath()+"/sys/userRole/?repage";
	}

}