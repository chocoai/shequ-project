package com.haolinbang.modules.sys.security.sso.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * 
 * @author Administrator
 * 
 */
public class UserInfo implements Serializable {

	private static final long serialVersionUID = -7180153663562219797L;
	private Integer staffId; // 员工id
	private Integer group; // 所属组织机构
	private String staffNo; // 员工编号
	private String staffName; // 员工姓名
	private String job; // 职位
	private Integer leave; // 是否离职
	private Date leaveDate; // 离职时间

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getLeave() {
		return leave;
	}

	public void setLeave(Integer leave) {
		this.leave = leave;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

}
