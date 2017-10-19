package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 具体人员明细表Entity
 * @author nlp
 * @version 2017-06-20
 */
public class WyActCandidateDetail extends DataEntity<WyActCandidateDetail> {
	
	private static final long serialVersionUID = 1L;
	private Integer actCandidateId;		// 人员类型id
	private String candidate;		// 候选人
	private String candidateType;		// 候选人类型，1=候选人，2=部门候选人类型
	
	public WyActCandidateDetail() {
		super();
	}

	public WyActCandidateDetail(String id){
		super(id);
	}

	@NotNull(message="人员类型id不能为空")
	public Integer getActCandidateId() {
		return actCandidateId;
	}

	public void setActCandidateId(Integer actCandidateId) {
		this.actCandidateId = actCandidateId;
	}
	
	@Length(min=0, max=64, message="候选人长度必须介于 0 和 64 之间")
	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}
	
	@Length(min=0, max=5, message="候选人类型，1=候选人，2=部门候选人类型长度必须介于 0 和 5 之间")
	public String getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(String candidateType) {
		this.candidateType = candidateType;
	}
	
}