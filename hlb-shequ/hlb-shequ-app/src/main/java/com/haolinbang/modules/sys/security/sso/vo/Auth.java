package com.haolinbang.modules.sys.security.sso.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 认证vo
 * 
 * @author Administrator
 * 
 */
public class Auth implements Serializable {

	private static final long serialVersionUID = -1163016986994270092L;

	private int code;// 返回代码，如果成功，返回200，错误返回500,和http中的状态码一致

	private String msg;// 错误原因说明

	// --------------------只有当code为200的时候,才会返回正常的信息

	private String username;// 用户名登录名

	private String staffName;// 用户名

	private String token;// 票据信息

	private String bizCode;// 业务编码

	private boolean valid;// 是否有效

	private Date startTime;// 开始时间

	private Date expiredTime;// 过期时间

	private int staffId;// 员工id

	private int groupId;//

	private String email;// 电子邮件

	private String phone;// 手机

	private boolean leaveStatus;// 离职状态

	private Date leaveDate;// 离职日期

	private String sex;// 性别

	private String url;// 回调的跳转url地址

	private String source;// 公司编码

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStaffName() {
		return staffName;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public boolean isValid() {
		valid = false;
		Date now = new Date();
		if (now.before(expiredTime) && now.after(startTime)) {
			valid = true;
		}
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

}
