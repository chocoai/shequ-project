package com.haolinbang.modules.sys.entity;

import com.haolinbang.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 用户使用数据范围Entity
 * 
 * @author nlp
 * @version 2017-08-02
 */
public class AppUserScope extends DataEntity<AppUserScope> {

	private static final long serialVersionUID = 1L;
	private String userId; // 用户id
	private String type; // 数据范围类型:1：所有数据（集团）；2：所在分公司及以下数据；3：所在管理处及以下数据；8：仅本人数据；9：按明细设置
	private String groupIds; // 当数据方位类型为自定义类型时，查找该字段的值，用户指定数据作用范围
	private String groupId;// 组织机构id
	private String parentGroupId;// 父id

	public String getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public AppUserScope() {
		super();
	}

	public AppUserScope(String id) {
		super(id);
	}

	@NotNull(message = "用户id不能为空")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Length(min = 1, max = 1, message = "数据范围类型:1：所有数据（集团）；2：所在分公司及以下数据；3：所在管理处及以下数据；8：仅本人数据；9：按明细设置长度必须介于 1 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min = 0, max = 1000, message = "当数据方位类型为自定义类型时，查找该字段的值，用户指定数据作用范围长度必须介于 0 和 1000 之间")
	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

}