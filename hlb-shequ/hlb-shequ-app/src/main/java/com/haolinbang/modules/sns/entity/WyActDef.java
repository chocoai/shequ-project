package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 工作流程定义表Entity
 * 
 * @author nlp
 * @version 2017-05-05
 */
public class WyActDef extends DataEntity<WyActDef> {

	private static final long serialVersionUID = -8040355221017301679L;
	private String key; // 流程唯一标识的key
	private String name; // 流程名称
	private String groupId; // 可以使用该流程的机构,用于权限控制,为空则表示通用
	private String procDefId;// 流程定义id
	private int version;// 版本
	private String category;// 分类类别
	private String state;// 流程状态
	private String alias;// 别名

	private String showName;// 前端显示的名称

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public WyActDef() {
		super();
	}

	public WyActDef(String id) {
		super(id);
	}

	@Length(min = 1, max = 100, message = "流程唯一标识的key长度必须介于 1 和 100 之间")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Length(min = 0, max = 100, message = "流程名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 1000, message = "可以使用该流程的机构,用于权限控制,为空则表示通用长度必须介于 0 和 1000 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}