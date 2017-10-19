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
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.thridwy.haolong.bean.GetRights;
import com.haolinbang.common.thridwy.haolong.bean.GetTimeStamp;
import com.haolinbang.common.thridwy.haolong.utils.AESUtils;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.Collections3;
import com.haolinbang.common.utils.ServletUtil;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.common.web.Servlets;
import com.haolinbang.modules.sys.entity.Office;
import com.haolinbang.modules.sys.entity.Role;
import com.haolinbang.modules.sys.entity.RoleMenu;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.service.OfficeService;
import com.haolinbang.modules.sys.service.RoleMenuService;
import com.haolinbang.modules.sys.service.SystemService;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.sys.vo.MenuVo;

/**
 * 角色Controller
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/role")
public class RoleController extends BaseController {

	@Autowired
	private SystemService systemService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private RoleMenuService roleMenuService;

	@ModelAttribute("role")
	public Role get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return systemService.getRole(id);
		} else {
			return new Role();
		}
	}

	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = { "/list", "" })
	public String list(Role role, Model model) {
		List<Role> list = systemService.findAllRole();
		model.addAttribute("list", list);
		return "modules/sys/roleList";
	}

	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "/form")
	public String form(Role role, Model model) {
		// 查询已经勾选哪些选项
		List<String> selectedMenuList = new ArrayList<String>();
		if (StringUtils.isNotBlank(role.getId())) {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(role.getId());
			List<RoleMenu> list = roleMenuService.findList(roleMenu);
			for (RoleMenu rm : list) {
				selectedMenuList.add(rm.getMenuId());
			}
		}

		List<MenuVo> volist = systemService.getAuthList(selectedMenuList);

		String unStr = (String) ServletUtil.getRequest().getSession().getAttribute("screenCodes");

		if (StringUtils.isBlank(unStr)) {
			String secretkey = Global.getConfig("okbang.secretkey");
			String soapactionstring = Global.getConfig("okbang.soapactionstring");
			String urlstring = Global.getConfig("okbang.urlstring");

			GetTimeStamp bean = new GetTimeStamp();
			bean.setSecretkey(secretkey);
			bean.setSoapActionString(soapactionstring);
			bean.setUrlString(urlstring);

			try {
				String timestampStr = HaolongUtils.getTimeStamp(bean);
				String app_source = (String) ServletUtil.getRequest().getSession().getAttribute("app_source");

				GetRights getRights = new GetRights();
				getRights.setSecretkey(secretkey);
				getRights.setSoapActionString(soapactionstring);
				getRights.setUrlString(urlstring);
				getRights.setSource(AESUtils.encryptAes16(app_source, secretkey));
				getRights.setTimeStampT(AESUtils.encryptAes16(timestampStr, secretkey));
				String str = HaolongUtils.getRights(getRights);
				unStr = AESUtils.decryptAes16(str, secretkey);

				if (StringUtils.isNotBlank(unStr)) {
					unStr = unStr.split("\\^")[0];
				}
				ServletUtil.getRequest().getSession().setAttribute("screenCodes", unStr);
			} catch (Exception e) {
				logger.error("获取时间戳:{}", e);
			}
		}

		for (int i = 0; i < volist.size();) {
			int screenCode = StringUtils.toInteger(StringUtils.isBlank(volist.get(i).getScreenCode()) ? "-1" : volist.get(i).getScreenCode());
			if (screenCode == -1) {
				i++;
			} else if (screenCode == 0) {
				i++;
			} else if (screenCode <= unStr.length()) {
				String flag = unStr.substring(screenCode - 1, screenCode);
				if (flag.equals("Y")) {
					i++;
				} else {
					volist.remove(i);
				}
			} else {
				volist.remove(i);
			}
		}

		model.addAttribute("volist", volist);
		model.addAttribute("role", role);

		// 判断是否显示详情
		if ("true".equals(Servlets.getRequest().getParameter("isView"))) {
			return "modules/sys/roleView";
		}

		return "modules/sys/roleForm";
	}

	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "/save")
	public String save(Role role, Model model, RedirectAttributes redirectAttributes) {

		systemService.saveRole(role);
		addMessage(redirectAttributes, "保存角色'" + role.getName() + "'成功");
		return "redirect:" + adminPath + "/sys/role/?repage";
	}

	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "/delete")
	public String delete(Role role, RedirectAttributes redirectAttributes) {
		if (!UserUtils.getUser().isAdmin() && role.getSysData().equals(Global.YES)) {
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
		// if (Role.isAdmin(id)){
		// addMessage(redirectAttributes, "删除角色失败, 不允许内置角色或编号空");
		// // }else if (UserUtils.getUser().getRoleIdList().contains(id)){
		// // addMessage(redirectAttributes, "删除角色失败, 不能删除当前用户所在角色");
		// }else{
		systemService.deleteRole(role);
		addMessage(redirectAttributes, "删除角色成功");
		// }
		return "redirect:" + adminPath + "/sys/role/?repage";
	}

	/**
	 * 角色分配页面
	 * 
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "/assign")
	public String assign(Role role, Model model) {
		List<User> userList = systemService.findUser(new User(new Role(role.getId())));
		model.addAttribute("userList", userList);
		return "modules/sys/roleAssign";
	}

	/**
	 * 角色分配 -- 打开角色分配对话框
	 * 
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "/usertorole")
	public String selectUserToRole(Role role, Model model) {
		List<User> userList = systemService.findUser(new User(new Role(role.getId())));
		model.addAttribute("role", role);
		model.addAttribute("userList", userList);
		model.addAttribute("selectIds", Collections3.extractToString(userList, "name", ","));
		model.addAttribute("officeList", officeService.findAll());
		return "modules/sys/selectUserToRole";
	}

	/**
	 * 角色分配 -- 根据部门编号获取用户列表
	 * 
	 * @param officeId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@ResponseBody
	@RequestMapping(value = "/users")
	public List<Map<String, Object>> users(String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		User user = new User();
		user.setOffice(new Office(officeId));
		Page<User> page = systemService.findUser(new Page<User>(1, -1), user);
		for (User e : page.getList()) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", 0);
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 角色分配 -- 从角色中移除用户
	 * 
	 * @param userId
	 * @param roleId
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "/outrole")
	public String outrole(String userId, String roleId, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/assign?id=" + roleId;
		}
		Role role = systemService.getRole(roleId);
		User user = systemService.getUser(userId);
		if (UserUtils.getUser().getId().equals(userId)) {
			addMessage(redirectAttributes, "无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！");
		} else {
			if (user.getRoleList().size() <= 1) {
				addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！这已经是该用户的唯一角色，不能移除。");
			} else {
				Boolean flag = systemService.outUserInRole(role, user);
				if (!flag) {
					addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！");
				} else {
					addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！");
				}
			}
		}
		return "redirect:" + adminPath + "/sys/role/assign?id=" + role.getId();
	}

	/**
	 * 角色分配
	 * 
	 * @param role
	 * @param idsArr
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "/assignrole")
	public String assignRole(Role role, String[] idsArr, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/assign?id=" + role.getId();
		}
		StringBuilder msg = new StringBuilder();
		int newNum = 0;
		for (int i = 0; i < idsArr.length; i++) {
			User user = systemService.assignUserToRole(role, systemService.getUser(idsArr[i]));
			if (null != user) {
				msg.append("<br/>新增用户【" + user.getName() + "】到角色【" + role.getName() + "】！");
				newNum++;
			}
		}
		addMessage(redirectAttributes, "已成功分配 " + newNum + " 个用户" + msg);
		return "redirect:" + adminPath + "/sys/role/assign?id=" + role.getId();
	}

	/**
	 * 验证角色名是否有效
	 * 
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/checkName")
	public String checkName(String oldName, String name) {
		if (name != null && name.equals(oldName)) {
			return "true";
		} else if (name != null && systemService.getRoleByName(name) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 验证角色英文名是否有效
	 * 
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/checkEnname")
	public String checkEnname(String oldEnname, String enname) {
		if (enname != null && enname.equals(oldEnname)) {
			return "true";
		} else if (enname != null && systemService.getRoleByEnname(enname) == null) {
			return "true";
		}
		return "false";
	}

}