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
import com.haolinbang.modules.sys.entity.AppMenu;
import com.haolinbang.modules.sys.service.AppMenuService;

/**
 * 应用平台菜单表Controller
 * @author nlp
 * @version 2017-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/appMenu")
public class AppMenuController extends BaseController {

	@Autowired
	private AppMenuService appMenuService;
	
	@ModelAttribute
	public AppMenu get(@RequestParam(required=false) String id) {
		AppMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = appMenuService.get(id);
		}
		if (entity == null){
			entity = new AppMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:appMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(AppMenu appMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AppMenu> page = appMenuService.findPage(new Page<AppMenu>(request, response), appMenu); 
		model.addAttribute("page", page);
		return "modules/sys/appMenuList";
	}

	@RequiresPermissions("sys:appMenu:view")
	@RequestMapping(value = "form")
	public String form(AppMenu appMenu, Model model) {
		model.addAttribute("appMenu", appMenu);
		return "modules/sys/appMenuForm";
	}

	@RequiresPermissions("sys:appMenu:edit")
	@RequestMapping(value = "save")
	public String save(AppMenu appMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appMenu)){
			return form(appMenu, model);
		}
		appMenuService.save(appMenu);
		addMessage(redirectAttributes, "保存应用平台菜单表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/appMenu/?repage";
	}
	
	@RequiresPermissions("sys:appMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(AppMenu appMenu, RedirectAttributes redirectAttributes) {
		appMenuService.delete(appMenu);
		addMessage(redirectAttributes, "删除应用平台菜单表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/appMenu/?repage";
	}

}