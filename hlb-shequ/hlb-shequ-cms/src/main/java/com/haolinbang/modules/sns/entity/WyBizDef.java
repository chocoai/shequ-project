package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 业务功能定义Entity
 * 
 * @author nlp
 * @version 2017-09-11
 */
public class WyBizDef extends DataEntity<WyBizDef> {

	private static final long serialVersionUID = 1L;
	private String key; // 公司编码
	private String name; // 不同机构使用权限
	private String groupId;// 组织机构

	private String showName;// 显示名称

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WyBizDef() {
		super();
	}

	public WyBizDef(String id) {
		super(id);
	}

	@Length(min = 1, max = 1000, message = "不同机构使用权限长度必须介于 1 和 1000 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}