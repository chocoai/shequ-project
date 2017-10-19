package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 点赞记录Entity
 * @author nlp
 * @version 2017-07-20
 */
public class WyDianzanRecord extends DataEntity<WyDianzanRecord> {
	
	private static final long serialVersionUID = 1L;
	private Integer relationId;		// 关联的业务表id
	private Integer memberId;		// 点赞的人
	private String dianzanType;		// 点赞类型，1=对主题点赞，2=对留言点赞，3=对回复点赞
	private String bizType;		// 业务类型
	
	public WyDianzanRecord() {
		super();
	}

	public WyDianzanRecord(String id){
		super(id);
	}

	@NotNull(message="关联的业务表id不能为空")
	public Integer getRelationId() {
		return relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}
	
	@NotNull(message="点赞的人不能为空")
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	@Length(min=0, max=255, message="点赞类型，1=对主题点赞，2=对留言点赞，3=对回复点赞长度必须介于 0 和 255 之间")
	public String getDianzanType() {
		return dianzanType;
	}

	public void setDianzanType(String dianzanType) {
		this.dianzanType = dianzanType;
	}
	
	@Length(min=1, max=50, message="业务类型长度必须介于 1 和 50 之间")
	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
}