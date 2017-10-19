package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 工作流和实际的业务对应关系表Entity
 * 
 * @author nlp
 * @version 2017-05-08
 */
public class WyReBizAct extends DataEntity<WyReBizAct> {

	private static final long serialVersionUID = 1L;
	private String actId; // 工作流id
	private String bizId; // 实际业务流程id

	private WyActDef wyActDef;// 工作流定义
	private WyBizDef wyBizDef;// 业务定义

	private String source;// 公司编码
	private String groupId;// 组织机构编码

	private String category;// 分类

	private Boolean isDefault;// 在同一个分类中是否为默认值

	private Boolean editable;// 是否可以修改

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public WyActDef getWyActDef() {
		return wyActDef;
	}

	public void setWyActDef(WyActDef wyActDef) {
		this.wyActDef = wyActDef;
	}

	public WyBizDef getWyBizDef() {
		return wyBizDef;
	}

	public void setWyBizDef(WyBizDef wyBizDef) {
		this.wyBizDef = wyBizDef;
	}

	public WyReBizAct() {
		super();
	}

	public WyReBizAct(String id) {
		super(id);
	}

	@Length(min = 1, max = 32, message = "工作流id长度必须介于 1 和 32 之间")
	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	@Length(min = 1, max = 32, message = "实际业务流程id长度必须介于 1 和 32 之间")
	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

}