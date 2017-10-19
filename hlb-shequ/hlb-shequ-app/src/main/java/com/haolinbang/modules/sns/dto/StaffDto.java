package com.haolinbang.modules.sns.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工dto
 * 
 * @author Administrator
 * 
 */
public class StaffDto implements Serializable {

	private static final long serialVersionUID = -1465173744297698162L;

	private String staffNo;// 员工编号
	private String staffName;// 姓名
	private String positions;// 现任职务
	private String isLeave;// 是否离职
	private Date leavedate;// 离职日期
	private String groupId;// 所属组织机构

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

	public String getPositions() {
		return positions;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}

	public String getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(String isLeave) {
		this.isLeave = isLeave;
	}

	public Date getLeavedate() {
		return leavedate;
	}

	public void setLeavedate(Date leavedate) {
		this.leavedate = leavedate;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
