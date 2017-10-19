package com.haolinbang.modules.sns.dto;

import java.io.Serializable;

/**
 * 组织结构dto
 * 
 * @author Administrator
 * 
 */
public class GroupDto implements Serializable {

	private static final long serialVersionUID = 3287804064567357621L;
	private String parentId;// 父类id
	private String groupId;// 组织结构id
	private String groupType;// 组织结构类型
	private String groupNo;// 组织机构编号
	private String groupName;// 组织机构名称
	private String guid;// guid

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

}
