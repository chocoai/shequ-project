package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 豪龙组织机构Entity
 * 
 * @author nlp
 * @version 2017-06-26
 */
public class WyOrgGroup extends DataEntity<WyOrgGroup> {

	private static final long serialVersionUID = 1L;
	private Integer groupId; // group_id
	private Integer parentId; // parent_id
	private Integer groupType; // group_type
	private String groupNo; // group_no
	private String groupName; // group_name

	public WyOrgGroup() {
		super();
	}

	public WyOrgGroup(String id) {
		super(id);
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@NotNull(message = "group_type不能为空")
	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	@Length(min = 0, max = 50, message = "group_no长度必须介于 0 和 50 之间")
	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	@Length(min = 0, max = 60, message = "group_name长度必须介于 0 和 60 之间")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}