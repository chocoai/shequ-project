package com.haolinbang.modules.sys.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haolinbang.common.config.Global;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sys.entity.AppMenu;
import com.haolinbang.modules.sys.entity.Dict;
import com.haolinbang.modules.sys.entity.Menu;
import com.haolinbang.modules.sys.service.AppMenuService;
import com.haolinbang.modules.sys.utils.DictUtils;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 应用平台菜单表Controller
 * 
 * @author nlp
 * @version 2017-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/appMenu")
public class AppMenuController extends BaseController {

	@Autowired
	private AppMenuService appMenuService;

	@ModelAttribute
	public AppMenu get(@RequestParam(required = false) String id) {
		AppMenu entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = appMenuService.get(id);
		}
		if (entity == null) {
			entity = new AppMenu();
		}
		return entity;
	}

	@RequiresPermissions("sys:appMenu:view")
	@RequestMapping(value = { "list", "" })
	public String list(AppMenu appMenu, Model model) {

		// 如果系统类型为空，则选择默认的系统类型
		if (StringUtils.isBlank(appMenu.getSysType())) {
			List<Dict> dictList = DictUtils.getDictList("sys_type");
			appMenu.setSysType(dictList.get(0).getValue());
		}

		List<AppMenu> sourcelist = UserUtils.findAllAppMenu(appMenu);
		List<AppMenu> list = Lists.newArrayList();
		AppMenu.sortList(list, sourcelist, Menu.getRootId(), true);

		// 查找系统类型

		model.addAttribute("list", list);
		return "modules/sys/appMenuList";
	}

	@RequiresPermissions("sys:appMenu:view")
	@RequestMapping(value = "form")
	public String form(AppMenu menu, Model model) {

		if (menu.getParent() == null || menu.getParent().getId() == null) {
			menu.setParent(new AppMenu(AppMenu.getRootId()));
		}
		menu.setParent(appMenuService.get(menu.getParent().getId()));
		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(menu.getId())) {
			List<AppMenu> list = new ArrayList<AppMenu>();
			List<AppMenu> sourcelist = UserUtils.findAllAppMenu(menu);
			AppMenu.sortList(list, sourcelist, menu.getParentId(), false);
			if (list.size() > 0) {
				menu.setSort(list.get(list.size() - 1).getSort() + 30);
			}
		}

		model.addAttribute("menu", menu);
		return "modules/sys/appMenuForm";
	}

	@RequiresPermissions("sys:appMenu:edit")
	@RequestMapping(value = "save")
	public String save(AppMenu appMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appMenu)) {
			return form(appMenu, model);
		}
		appMenuService.save(appMenu);
		addMessage(redirectAttributes, "保存应用平台菜单表成功");
		return "redirect:" + Global.getAdminPath() + "/sys/appMenu/?repage";
	}

	@RequiresPermissions("sys:appMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(AppMenu appMenu, RedirectAttributes redirectAttributes) {
		appMenuService.delete(appMenu);
		addMessage(redirectAttributes, "删除应用平台菜单表成功");
		return "redirect:" + Global.getAdminPath() + "/sys/appMenu/?repage";
	}

	/**
	 * isShowHide是否显示隐藏菜单
	 * 
	 * @param extId
	 * @param isShowHidden
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/treeData")
	public List<Map<String, Object>> treeData(AppMenu menu, @RequestParam(required = false) String extId, @RequestParam(required = false) String isShowHide,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();

		List<AppMenu> list = UserUtils.findAllAppMenu(menu);
		for (int i = 0; i < list.size(); i++) {
			AppMenu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
				if (isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")) {
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

	/**
	 * 批量修改菜单排序
	 */
	@RequiresPermissions("sys:appMenu:edit")
	@RequestMapping(value = "/updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/appMenu/?repage";
		}
		for (int i = 0; i < ids.length; i++) {
			AppMenu menu = new AppMenu(ids[i]);
			menu.setSort(sorts[i]);
			appMenuService.updateMenuSort(menu);
		}
		addMessage(redirectAttributes, "保存菜单排序成功!");
		return "redirect:" + adminPath + "/sys/appMenu/?repage";
	}

	/**
	 * 权限添加
	 * 
	 * @param menu
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:appMenu:view")
	@RequestMapping("/permissionForm")
	public String permissionForm(AppMenu menu, Model model) {

		if (menu.getParent() == null || StringUtils.isBlank(menu.getParent().getId())) {
			menu.setParent(new AppMenu(AppMenu.getRootId()));
		}
		menu.setParent(appMenuService.get(menu.getParent().getId()));
		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(menu.getId())) {
			List<AppMenu> list = new ArrayList<AppMenu>();
			List<AppMenu> sourcelist = UserUtils.findAllAppMenu(menu);
			AppMenu.sortList(list, sourcelist, menu.getParentId(), false);
			if (list.size() > 0) {
				menu.setSort(list.get(list.size() - 1).getSort() + 30);
			}
		}

		model.addAttribute("menu", menu);
		return "modules/sys/appPermissionForm";
	}

}