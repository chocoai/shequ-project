package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 豪龙内部员工表Entity
 * 
 * @author nlp
 * @version 2017-06-26
 */
public class WyOrgStaff extends DataEntity<WyOrgStaff> {

	private static final long serialVersionUID = 1L;
	private Integer staffId; // 员工id
	private Integer groupId; // 所属组织机构
	private String staffNo; // 员工编号
	private String staffName; // 员工姓名
	private String job; // 职位
	private Integer leave; // 是否离职
	private Date leaveDate; // 离职时间

	public WyOrgStaff() {
		super();
	}

	public WyOrgStaff(String id) {
		super(id);
	}

	@NotNull(message = "员工id不能为空")
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

	@Length(min = 0, max = 30, message = "员工编号长度必须介于 0 和 30 之间")
	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	@Length(min = 0, max = 20, message = "员工姓名长度必须介于 0 和 20 之间")
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	@Length(min = 0, max = 50, message = "职位长度必须介于 0 和 50 之间")
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@NotNull(message = "是否离职不能为空")
	public Integer getLeave() {
		return leave;
	}

	public void setLeave(Integer leave) {
		this.leave = leave;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

}