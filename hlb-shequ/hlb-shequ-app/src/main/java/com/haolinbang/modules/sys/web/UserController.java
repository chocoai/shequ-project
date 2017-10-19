package com.haolinbang.modules.sys.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haolinbang.common.beanvalidator.BeanValidators;
import com.haolinbang.common.config.Global;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.thridwy.haolong.bean.GetEmployeeInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.thridwy.haolong.bean.bean.UserInfo;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.DateUtils;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.utils.excel.ExportExcel;
import com.haolinbang.common.utils.excel.ImportExcel;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.service.UrlmapService;
import com.haolinbang.modules.sys.entity.Office;
import com.haolinbang.modules.sys.entity.Role;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.service.SystemService;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 用户Controller
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;

	@Autowired
	private UrlmapService urlmapService;

	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return systemService.getUser(id);
		} else {
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "/index" })
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "/list", "" })
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		model.addAttribute("page", page);
		return "modules/sys/userList";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping("/authList")
	public String authList(HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = new Page<User>();

		List<User> userList = new ArrayList<User>();

		String type = null;
		String source = null;

		// 从数据库中查询对应的记录
		String gid = request.getParameter("gid");
		if (StringUtils.isBlank(gid)) {
			page.setList(userList);
			model.addAttribute("page", page);
			return "modules/sys/authUserList";
		}
		if (gid.contains("___")) {
			String[] arr = gid.split("___");
			if (arr != null && arr.length == 3) {
				type = arr[0];
				source = arr[1];
				gid = arr[2];
			}
		}

		Urlmap urlmap = new Urlmap();
		urlmap.setStatus("1");
		urlmap.setUrlkey(source);

		// 设置数据源
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		urlmap = urlmapService.getUrlmap(urlmap);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

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
				// 遍历记录，组装员工信息

				User u = new User();
				u.setName(userInfo.getStaffName());
				u.setLoginName(userInfo.getStaffName());
				u.setStaffId(userInfo.getStaffId());
				u.setGroupId(userInfo.getGroupId());
				u.setSex(userInfo.getSex());
				Office company = new Office();
				company.setId(source);
				company.setName(urlmap.getUrlname());

				Office office = new Office();
				office.setId(gid);
				office.setName(UserUtils.getOfficeNameFromGroupInfo(gid, source));

				u.setCompany(company);
				u.setOffice(office);
				userList.add(u);

			}
		}

		page.setList(userList);
		model.addAttribute("page", page);
		return "modules/sys/authUserList";
	}

	/**
	 * 获取物业用户列表数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping("/selectHandlerList")
	public String selectHandlerList(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		String activitiId = request.getParameter("activitiId");
		String procDefinitionId = request.getParameter("procDefinitionId");
		model.addAttribute("activitiId", activitiId);
		model.addAttribute("procDefinitionId", procDefinitionId);
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		model.addAttribute("page", page);
		return "modules/sys/selectHandlerUserList2";
	}

	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "/listData" })
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		return page;
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "/form")
	public String form(User user, Model model) {
		if (user.getCompany() == null || user.getCompany().getId() == null) {
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice() == null || user.getOffice().getId() == null) {
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userForm";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "/save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		// if (StringUtils.isNotBlank(user.getNewPassword())) {
		// user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		// }
		// if (!beanValidator(model, user)) {
		// return form(user, model);
		// }
		// if (!"true".equals(checkLoginName(user.getOldLoginName(),
		// user.getLoginName()))) {
		// addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
		// return form(user, model);
		// }
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()) {
			if (roleIdList.contains(r.getId())) {
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
			UserUtils.clearCache();
			// UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "/delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		} else if (user.isAdmin()) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		} else {
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 导出用户数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
			new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 导入用户数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list) {
				try {
					if ("true".equals(checkLoginName("", user.getLoginName()))) {
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					} else {
						failureMsg.append("<br/>登录名 " + user.getLoginName() + " 已存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 下载导入用户数据模板
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "/import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据导入模板.xlsx";
			List<User> list = Lists.newArrayList();
			list.add(UserUtils.getUser());
			new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 验证登录名是否有效
	 * 
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "/checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName != null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName != null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 用户信息显示及保存
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "/info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		String source = currentUser.getSource();

		Urlmap urlmap = UserUtils.getUrlmap(source);

		GetEmployeeInfo getEmployeeInfo = new GetEmployeeInfo();
		getEmployeeInfo.setSecretkey(urlmap.getSecretkey());
		getEmployeeInfo.setSoapActionString(urlmap.getSoapactionstring());
		getEmployeeInfo.setUrlString(urlmap.getUrlstring());
		getEmployeeInfo.setGroupID(currentUser.getGroupId() + "");
		List<UserInfo> elist = null;

		try {
			elist = HaolongUtils.getEmployeeInfo(getEmployeeInfo);
			logger.info("获取部门员工elist:{}", elist);

		} catch (IOException e) {
			logger.error("获取部门员工出错：{}", e);
		}
		if (elist != null && !elist.isEmpty()) {
			for (UserInfo userInfo : elist) {

				Integer staffId = userInfo.getStaffId();
				if (staffId.intValue() == currentUser.getStaffId().intValue()) {

					// 更新或者保存应用用户信息
					currentUser.setNo(userInfo.getStaffNo());
					currentUser.setSex(userInfo.getSex());

					Office company = new Office();
					company.setId(source);
					company.setName(urlmap.getUrlname());

					Office office = new Office();
					office.setId(currentUser.getGroupId().toString());
					office.setName(UserUtils.getOfficeNameFromGroupInfo(currentUser.getGroupId().toString(), source));

					currentUser.setCompany(company);
					currentUser.setOffice(office);
				}

			}
		}

		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}

	/**
	 * 返回用户信息
	 * 
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/infoData")
	public User infoData() {
		return UserUtils.getUser();
	}

	/**
	 * 修改个人用户密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "/modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
			if (Global.isDemoMode()) {
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())) {
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			} else {
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i = 0; i < list.size(); i++) {
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_" + e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

}
