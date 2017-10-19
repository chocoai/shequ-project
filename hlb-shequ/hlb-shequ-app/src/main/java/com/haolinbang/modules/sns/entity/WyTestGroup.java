package com.haolinbang.modules.sns.entity;

import com.haolinbang.modules.weixin.entity.WxMemberGroup;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 组织机构Entity
 * @author nlp
 * @version 2017-06-23
 */
public class WyTestGroup extends DataEntity<WyTestGroup> {
	
	private static final long serialVersionUID = 1L;
	private WxMemberGroup group;		// group_id
	private WyTestGroup parent;		// parent_id
	private String groupType;		// group_type
	private String groupNo;		// group_no
	private String groupName;		// group_name
	
	public WyTestGroup() {
		super();
	}

	public WyTestGroup(String id){
		super(id);
	}

	@NotNull(message="group_id不能为空")
	public WxMemberGroup getGroup() {
		return group;
	}

	public void setGroup(WxMemberGroup group) {
		this.group = group;
	}
	
	@JsonBackReference
	@NotNull(message="parent_id不能为空")
	public WyTestGroup getParent() {
		return parent;
	}

	public void setParent(WyTestGroup parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=1, message="group_type长度必须介于 1 和 1 之间")
	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	
	@Length(min=0, max=50, message="group_no长度必须介于 0 和 50 之间")
	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	
	@Length(min=0, max=60, message="group_name长度必须介于 0 和 60 之间")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}