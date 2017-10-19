package com.haolinbang.modules.sys.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 应用平台用户Entity
 * 
 * @author nlp
 * @version 2017-08-02
 */
public class AppUser extends DataEntity<AppUser> {

	private static final long serialVersionUID = 1L;
	private String name; // 用户名,姓名
	private String loginName; // 登录名
	private Integer staffId; // 对应单点登录平台的员工id
	private Integer groupId; // 对应的组织机构
	private String source; // 公司编码

	private String loginFlag;// 是否允许登录标识
	private String password;// 密码

	private String oldLoginIp; // 上次登陆IP
	private Date oldLoginDate; // 上次登陆日期
	private String loginIp; // 最后登陆IP
	private Date loginDate; // 最后登陆日期

	private Office company; // 归属公司
	private Office office; // 归属部门

	private AppRole role; // 根据角色查询用户条件
	private List<AppRole> roleList = new ArrayList<AppRole>(); // 拥有角色列表

	private String no;// 员工编号
	private String sex;// 性别

	private boolean authStatus;// 授权状态

	public boolean isAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(boolean authStatus) {
		this.authStatus = authStatus;
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

	public AppRole getRole() {
		return role;
	}

	public void setRole(AppRole role) {
		this.role = role;
	}

	public List<AppRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<AppRole> roleList) {
		this.roleList = roleList;
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

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AppUser() {
		super();
	}

	public AppUser(String id) {
		super(id);
	}

	@Length(min = 1, max = 50, message = "用户名,姓名长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 50, message = "登录名长度必须介于 0 和 50 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@NotNull(message = "对应单点登录平台的员工id不能为空")
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

	@Length(min = 0, max = 20, message = "公司编码长度必须介于 0 和 20 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}