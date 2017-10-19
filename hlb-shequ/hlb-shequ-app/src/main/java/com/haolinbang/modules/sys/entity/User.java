package com.haolinbang.modules.sys.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.haolinbang.common.persistence.DataEntity;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.modules.sys.security.sso.vo.Auth;

/**
 * 用户Entity
 * 
 */
public class User extends DataEntity<User> {

	private static final long serialVersionUID = 1L;

	private String name; // 用户名,姓名
	private String loginName; // 登录名
	private Integer staffId; // 对应单点登录平台的员工id
	private Integer groupId; // 对应的组织机构
	private String source; // 公司编码
	private String oldLoginIp; // 上次登陆IP
	private Date oldLoginDate; // 上次登陆日期
	private String loginIp; // 最后登陆IP
	private Date loginDate; // 最后登陆日期
	private String loginFlag;// 是否允许登录标识
	private String no;// 员工编号
	private String sex;// 性别
	private String email;// 电子邮件
	private String phone;// 手机
	private boolean leaveStatus;// 离职状态
	private Date leaveDate;// 离职日期

	private Office company; // 归属公司
	private Office office; // 归属部门
	private String password;// 密码

	private Role role; // 根据角色查询用户条件
	private List<Role> roleList = new ArrayList<Role>(); // 拥有角色列表
	private List<AppUserScope> appUserScopeList;// 拥有的数据范围

	private Integer parentGroupId;// 临时变量，登录时查询并保存
	private GroupInfo groupInfo;// 当前部门信息

	private Auth auth;// 授权信息

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(boolean leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public List<AppUserScope> getAppUserScopeList() {
		return appUserScopeList;
	}

	public void setAppUserScopeList(List<AppUserScope> appUserScopeList) {
		this.appUserScopeList = appUserScopeList;
	}

	public GroupInfo getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(GroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}

	public User() {
		super();
	}

	public User(String id) {
		super(id);
	}

	public User(String id, String loginName) {
		super(id);
		this.loginName = loginName;
	}

	public User(String source, String id, String loginName) {
		super(id);
		this.loginName = loginName;
		this.source = source;
	}

	public User(Role role) {
		super();
		this.role = role;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public boolean isAdmin() {
		return isAdmin(this.id);
	}

	public static boolean isAdmin(String id) {
		return id != null && "1".equals(id);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOldLoginIp() {
		return oldLoginIp;
	}

	public void setOldLoginIp(String oldLoginIp) {
		this.oldLoginIp = oldLoginIp;
	}

	public Date getOldLoginDate() {
		return oldLoginDate;
	}

	public void setOldLoginDate(Date oldLoginDate) {
		this.oldLoginDate = oldLoginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<String> getRoleIdList() {
		List<String> roleIdList = new ArrayList<String>();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public Integer getParentGroupId() {
		return parentGroupId != null ? parentGroupId : (groupInfo != null && StringUtils.isNotBlank(groupInfo.getParentId()) ? Integer.valueOf(groupInfo.getParentId()) : null);
	}

	public void setParentGroupId(Integer parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

}