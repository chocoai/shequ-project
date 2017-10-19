package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 业务功能定义Entity
 * 
 * @author nlp
 * @version 2017-05-05
 */
public class WyBizDef extends DataEntity<WyBizDef> {

	private static final long serialVersionUID = 1L;
	private String key; // 业务key
	private String name; // 业务名称
	private String groupId; // 机构权限

	private String showName;// 前端显示的名称

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public WyBizDef() {
		super();
	}

	public WyBizDef(String id) {
		super(id);
	}

	@Length(min = 1, max = 100, message = "业务key长度必须介于 1 和 100 之间")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Length(min = 0, max = 200, message = "业务名称长度必须介于 0 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}