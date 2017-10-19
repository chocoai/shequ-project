package com.haolinbang.modules.sys.web;

import java.util.ArrayList;
import java.util.List;

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
import com.haolinbang.common.web.Servlets;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sys.entity.AppMenu;
import com.haolinbang.modules.sys.entity.AppRole;
import com.haolinbang.modules.sys.entity.AppRoleMenu;
import com.haolinbang.modules.sys.entity.Dict;
import com.haolinbang.modules.sys.service.AppRoleMenuService;
import com.haolinbang.modules.sys.service.AppRoleService;
import com.haolinbang.modules.sys.service.AppUserService;
import com.haolinbang.modules.sys.utils.DictUtils;
import com.haolinbang.modules.sys.vo.AppMenuVo;

/**
 * 应用平台角色Controller
 * 
 * @author nlp
 * @version 2017-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/appRole")
public class AppRoleController extends BaseController {

	@Autowired
	private AppRoleService appRoleService;

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private AppRoleMenuService appRoleMenuService;

	@ModelAttribute
	public AppRole get(@RequestParam(required = false) String id) {
		AppRole entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = appRoleService.get(id);
		}
		if (entity == null) {
			entity = new AppRole();
		}
		return entity;
	}

	@RequiresPermissions("sys:appRole:view")
	@RequestMapping(value = { "list", "" })
	public String list(AppRole appRole, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 如果系统类型为空，则选择默认的系统类型,这个参数必传
		if (StringUtils.isBlank(appRole.getSysType())) {
			List<Dict> dictList = DictUtils.getDictList("sys_type");
			appRole.setSysType(dictList.get(0).getValue());
		}

		Page<AppRole> page = appRoleService.findPage(new Page<AppRole>(request, response), appRole);
		model.addAttribute("page", page);
		return "modules/sys/appRoleList";
	}

	@RequiresPermissions("sys:appRole:view")
	@RequestMapping(value = "form")
	public String form(AppRole appRole, Model model) {

		// 查询已经勾选哪些选项
		List<Integer> selectedMenuList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(appRole.getId())) {
			AppRoleMenu appRoleMenu = new AppRoleMenu();
			appRoleMenu.setRoleId(Integer.valueOf(appRole.getId()));
			List<AppRoleMenu> list = appRoleMenuService.findList(appRoleMenu);
			for (AppRoleMenu rm : list) {
				selectedMenuList.add(rm.getMenuId());
			}
		}

		// 设置系统类型
		AppMenu appMenu = new AppMenu();
		appMenu.setSysType(appRole.getSysType());
		List<AppMenuVo> volist = appUserService.getAuthList(selectedMenuList, appMenu);

		model.addAttribute("volist", volist);
		model.addAttribute("appRole", appRole);

		//判断是否显示详情
		if ("true".equals(Servlets.getRequest().getParameter("isView"))) {
			return "modules/sys/appRoleView";
		}

		return "modules/sys/appRoleForm";
	}

	@RequiresPermissions("sys:appRole:edit")
	@RequestMapping(value = "save")
	public String save(AppRole appRole, Model model, RedirectAttributes redirectAttributes) {
		String msg = "保存应用平台角色成功";
		try {
			if (!beanValidator(model, appRole)) {
				return form(appRole, model);
			}
			appRoleService.save(appRole);
		} catch (Exception e) {
			logger.error("保存角色出错：{}", e);
			String errMsg = e.getMessage();
			if (errMsg != null && errMsg.contains("Duplicate")) {
				msg = "重复添加,请校验后重新添加";
			}
		}
		addMessage(redirectAttributes, msg);
		return "redirect:" + Global.getAdminPath() + "/sys/appRole/?repage";
	}

	@RequiresPermissions("sys:appRole:edit")
	@RequestMapping(value = "delete")
	public String delete(AppRole appRole, RedirectAttributes redirectAttributes) {
		appRoleService.delete(appRole);
		addMessage(redirectAttributes, "删除应用平台角色成功");
		return "redirect:" + Global.getAdminPath() + "/sys/appRole/?repage";
	}
}