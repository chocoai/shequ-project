package com.haolinbang.modules.sys.web;

import java.io.IOException;
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
import com.haolinbang.common.thridwy.haolong.bean.GetEmployeeInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.thridwy.haolong.bean.bean.UserInfo;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.dto.ZtreeDataDto;
import com.haolinbang.modules.sys.entity.AppMenu;
import com.haolinbang.modules.sys.entity.AppRole;
import com.haolinbang.modules.sys.entity.AppUser;
import com.haolinbang.modules.sys.entity.AppUserPermission;
import com.haolinbang.modules.sys.entity.AppUserRole;
import com.haolinbang.modules.sys.entity.AppUserScope;
import com.haolinbang.modules.sys.entity.Dict;
import com.haolinbang.modules.sys.entity.Office;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.service.AppRoleService;
import com.haolinbang.modules.sys.service.AppUserPermissionService;
import com.haolinbang.modules.sys.service.AppUserRoleService;
import com.haolinbang.modules.sys.service.AppUserScopeService;
import com.haolinbang.modules.sys.service.AppUserService;
import com.haolinbang.modules.sys.utils.DictUtils;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.sys.vo.AppMenuVo;

/**
 * 应用平台用户Controller
 * 
 * @author nlp
 * @version 2017-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/appUser")
public class AppUserController extends BaseController {

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private AppUserPermissionService appUserPermissionService;

	@Autowired
	private AppRoleService appRoleService;

	@Autowired
	private AppUserRoleService appUserRoleService;

	@Autowired
	private AppUserScopeService appUserScopeService;

	@ModelAttribute
	public AppUser get(@RequestParam(required = false) String id) {
		AppUser entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = appUserService.get(id);
		}
		if (entity == null) {
			entity = new AppUser();
		}
		return entity;
	}

	@RequiresPermissions("sys:appUser:view")
	@RequestMapping(value = { "list", "" })
	public String list(AppUser appUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AppUser> page = appUserService.findPage(new Page<AppUser>(request, response), appUser);
		model.addAttribute("page", page);
		return "modules/sys/appUserList";
	}

	@RequiresPermissions("sys:appUser:view")
	@RequestMapping(value = "form")
	public String form(AppUser appUser, Model model) {
		model.addAttribute("appUser", appUser);
		return "modules/sys/appUserForm";
	}

	@RequiresPermissions("sys:appUser:edit")
	@RequestMapping(value = "save")
	public String save(AppUser appUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appUser)) {
			return form(appUser, model);
		}
		appUserService.save(appUser);
		addMessage(redirectAttributes, "保存应用平台用户成功");
		return "redirect:" + Global.getAdminPath() + "/sys/appUser/?repage";
	}

	@RequiresPermissions("sys:appUser:edit")
	@RequestMapping(value = "delete")
	public String delete(AppUser appUser, RedirectAttributes redirectAttributes) {
		appUserService.delete(appUser);
		addMessage(redirectAttributes, "删除应用平台用户成功");
		return "redirect:" + Global.getAdminPath() + "/sys/appUser/?repage";
	}

	/**
	 * 授权应用平台
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping("/authIndex")
	public String authIndex(User user, Model model) {
		return "modules/sys/authIndex";
	}

	/**
	 * 通过接口查询部门下的人员列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:appUser:view")
	@RequestMapping("/authList")
	public String authList(HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AppUser> page = new Page<AppUser>();

		List<AppUser> userList = new ArrayList<AppUser>();

		// String type = null;
		String source = null;

		// 从数据库中查询对应的记录
		String originalGid = request.getParameter("gid");
		String gid = originalGid;

		if (StringUtils.isNotBlank(gid) && gid.contains("___")) {
			String[] arr = gid.split("___");
			if (arr != null && arr.length == 3) {
				// type = arr[0];
				source = arr[1];
				gid = arr[2];
			}
		} else {
			return "modules/sys/authUserList";
		}

		Urlmap urlmap = UserUtils.getUrlmap(source);

		GetEmployeeInfo getEmployeeInfo = new GetEmployeeInfo();
		getEmployeeInfo.setSecretkey(urlmap.getSecretkey());
		getEmployeeInfo.setSoapActionString(urlmap.getSoapactionstring());
		getEmployeeInfo.setUrlString(urlmap.getUrlstring());
		getEmployeeInfo.setGroupID(gid);

		List<UserInfo> elist = null;
		try {
			elist = HaolongUtils.getEmployeeInfo(getEmployeeInfo);
			logger.info("获取部门员工elist:{}", elist);

		} catch (IOException e) {
			logger.error("获取部门员工出错：{}", e);
		}
		if (elist != null && !elist.isEmpty()) {
			for (UserInfo userInfo : elist) {
				Integer staffId = Integer.valueOf(userInfo.getStaffId());
				AppUser u = appUserService.getAppUserByStaffId(source, staffId);
				if (u == null) {
					u = new AppUser();
				}

				// 更新或者保存应用用户信息
				u.setName(userInfo.getStaffName());
				u.setLoginName(userInfo.getStaffName());
				u.setStaffId(staffId);
				u.setGroupId(userInfo.getGroupId());
				u.setNo(userInfo.getStaffNo());
				u.setSex(userInfo.getSex());
				u.setSource(source);

				Office company = new Office();
				company.setId(source);
				company.setName(urlmap.getUrlname());

				Office office = new Office();
				office.setId(gid);
				office.setName(UserUtils.getOfficeNameFromGroupInfo(gid, source));

				u.setCompany(company);
				u.setOffice(office);

				// 通过source和员工id查询该用户，如果存在，则更新数据；不存在保存数据
				appUserService.save(u);

				// 查询是否已授权
				boolean authStatus = appUserService.checkIsAuth(u.getId());
				u.setAuthStatus(authStatus);

				userList.add(u);
			}
		}

		page.setList(userList);
		model.addAttribute("page", page);
		model.addAttribute("gid", originalGid);
		return "modules/sys/authUserList";
	}

	@RequiresPermissions("sys:appUser:edit")
	@RequestMapping("/auth")
	public String auth(AppMenuVo appMenuVo, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("appMenuVo", appMenuVo);

		// 查询已具有的权限
		List<Integer> selectedMenuList = new ArrayList<Integer>();
		AppUserPermission appUserPermission = new AppUserPermission();
		appUserPermission.setUserId(Integer.valueOf(appMenuVo.getUid()));
		List<AppUserPermission> appUserPermissionList = appUserPermissionService.findList(appUserPermission);
		if (appUserPermissionList != null && !appUserPermissionList.isEmpty())
			for (AppUserPermission p : appUserPermissionList) {
				selectedMenuList.add(p.getMenuId());
			}

		AppMenu appMenu = new AppMenu();
		if (StringUtils.isBlank(appMenuVo.getSysType())) {
			List<Dict> dictList = DictUtils.getDictList("sys_type");
			appMenu.setSysType(dictList.get(0).getValue());
		} else {
			appMenu.setSysType(appMenuVo.getSysType());
		}

		List<AppMenuVo> volist = appUserService.getAuthList(selectedMenuList, appMenu);
		model.addAttribute("volist", volist);

		// 查询可以授权的角色
		AppRole appRole = new AppRole();
		appRole.setSysType(appMenu.getSysType());
		List<AppRole> appRoleList = appRoleService.findList(appRole);

		AppUserRole appUserRole = new AppUserRole();
		appUserRole.setUserId(Long.valueOf(appMenuVo.getUid()));
		// 查询该用户已经拥有的角色
		List<AppUserRole> appUserRoleList = appUserRoleService.findList(appUserRole);

		for (AppRole role : appRoleList) {
			for (AppUserRole ur : appUserRoleList) {
				if (role.getId().equals(ur.getRoleId().toString())) {
					role.setPermission(ur.getRoleId().toString());
				}
			}
		}

		String gid = appMenuVo.getGid();
		String source = null;
		if (StringUtils.isNotBlank(gid) && gid.contains("___")) {
			String[] arr = gid.split("___");
			if (arr != null && arr.length == 3) {
				source = arr[1];
				gid = arr[2];
			}
		}

		// 获取用户所有的组织机构
		List<ZtreeDataDto> orgTreeList = UserUtils.getOrgWYTreeList(source, gid);
		model.addAttribute("orgTreeList", orgTreeList);

		List<AppUserScope> appUserScopeList = appUserScopeService.getAppUserScopeByUid(appMenuVo.getUid());
		for (AppUserScope scope : appUserScopeList) {
			if ("G".equals(scope.getType())) {
				scope.setGroupId("G_" + scope.getGroupId());
			} else if ("WY".equals(scope.getType())) {
				scope.setGroupId("WY_" + scope.getGroupId());
			}
		}

		model.addAttribute("appUserScopeList", appUserScopeList);
		model.addAttribute("appRoleList", appRoleList);
		return "modules/sys/authUserForm";
	}

	@RequiresPermissions("sys:appUser:edit")
	@RequestMapping("/authSave")
	public String authSave(AppMenuVo vo, HttpServletRequest request, HttpServletResponse response, Model model) {
		appUserService.authSave(vo);

		return "redirect:" + adminPath + "/sys/appUser/authList?uid=" + vo.getUid() + "&gid=" + vo.getGid();
	}

}