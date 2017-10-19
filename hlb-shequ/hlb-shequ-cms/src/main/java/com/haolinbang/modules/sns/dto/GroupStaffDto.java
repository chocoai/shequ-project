package com.haolinbang.modules.sns.dto;

import java.util.List;

/**
 * 员工和组织机构
 * 
 * @author Administrator
 * 
 */
public class GroupStaffDto {

	private GroupDto group;// 组织机构

	private List<StaffDto> staffs;// 机构人员

	public GroupDto getGroup() {
		return group;
	}

	public void setGroup(GroupDto group) {
		this.group = group;
	}

	public List<StaffDto> getStaffs() {
		return staffs;
	}

	public void setStaffs(List<StaffDto> staffs) {
		this.staffs = staffs;
	}

}
