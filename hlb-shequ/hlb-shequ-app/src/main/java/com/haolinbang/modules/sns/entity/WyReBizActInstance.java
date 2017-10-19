package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 具体流程引用Entity
 * @author nlp
 * @version 2017-09-11
 */
public class WyReBizActInstance extends DataEntity<WyReBizActInstance> {
	
	private static final long serialVersionUID = 1L;
	private Integer relationId;		// 流程关联表id
	private String source;		// 公司编码
	private Integer groupId;		// 组织id
	private Integer typeId;		// 分类
	
	public WyReBizActInstance() {
		super();
	}

	public WyReBizActInstance(String id){
		super(id);
	}

	@NotNull(message="流程关联表id不能为空")
	public Integer getRelationId() {
		return relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}
	
	@Length(min=1, max=20, message="公司编码长度必须介于 1 和 20 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@NotNull(message="组织id不能为空")
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	@NotNull(message="分类不能为空")
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
}