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
import com.haolinbang.modules.sns.entity.WyMenus;
import com.haolinbang.modules.sns.service.WyMenusService;

/**
 * 首页菜单Controller
 * @author wxc
 * @version 2017-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyMenus")
public class WyMenusController extends BaseController {

	@Autowired
	private WyMenusService wyMenusService;
	
	@ModelAttribute
	public WyMenus get(@RequestParam(required=false) String id) {
		WyMenus entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyMenusService.get(id);
		}
		if (entity == null){
			entity = new WyMenus();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyMenus:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyMenus wyMenus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyMenus> page = wyMenusService.findPage(new Page<WyMenus>(request, response), wyMenus); 
		model.addAttribute("page", page);
		return "modules/sns/wyMenusList";
	}

	@RequiresPermissions("sns:wyMenus:view")
	@RequestMapping(value = "form")
	public String form(WyMenus wyMenus, Model model) {
		model.addAttribute("wyMenus", wyMenus);
		return "modules/sns/wyMenusForm";
	}

	@RequiresPermissions("sns:wyMenus:edit")
	@RequestMapping(value = "save")
	public String save(WyMenus wyMenus, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyMenus)){
			return form(wyMenus, model);
		}
		wyMenusService.save(wyMenus);
		addMessage(redirectAttributes, "保存首页菜单成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyMenus/?repage";
	}
	
	@RequiresPermissions("sns:wyMenus:edit")
	@RequestMapping(value = "delete")
	public String delete(WyMenus wyMenus, RedirectAttributes redirectAttributes) {
		wyMenusService.delete(wyMenus);
		addMessage(redirectAttributes, "删除首页菜单成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyMenus/?repage";
	}

}