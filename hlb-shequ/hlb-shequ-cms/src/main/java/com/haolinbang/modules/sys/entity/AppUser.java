package com.haolinbang.modules.sys.entity;

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

	private Office company;// 所属公司
	private Office office;// 所属部门
	private String no;// 员工编号
	private String sex;// 性别
	private boolean authStatus;// 授权状态

	private String sysType;// 系统类型

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

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