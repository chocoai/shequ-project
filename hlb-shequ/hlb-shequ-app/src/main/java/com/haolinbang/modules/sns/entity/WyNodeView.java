package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 节点对应的页面显示Entity
 * 
 * @author nlp
 * @version 2017-05-09
 */
public class WyNodeView extends DataEntity<WyNodeView> {

	private static final long serialVersionUID = 1L;
	private Integer relationId; // 关联id
	private String viewUrl; // 视图地址

	public WyNodeView() {
		super();
	}

	public WyNodeView(String id) {
		super(id);
	}

	public Integer getRelationId() {
		return relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	@Length(min = 1, max = 200, message = "视图地址长度必须介于 1 和 200 之间")
	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}

}