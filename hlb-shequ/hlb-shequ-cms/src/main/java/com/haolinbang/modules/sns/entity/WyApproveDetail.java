package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 审批明细表Entity
 * 
 * @author nlp
 * @version 2017-06-29
 */
public class WyApproveDetail extends DataEntity<WyApproveDetail> {

	private static final long serialVersionUID = 1L;
	private Integer procInstId; // 关联的审批表
	private String approver; // 审批人
	private String approverType; // 审批人类型
	private String taskKey; // 任务key
	private String taskName; // 任务名称
	private Date startTime; // 任务开始时间
	private Date endTime; // 任务结束时间

	public WyApproveDetail() {
		super();
	}

	public WyApproveDetail(String id) {
		super(id);
	}

	public Integer getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(Integer procInstId) {
		this.procInstId = procInstId;
	}

	@Length(min = 1, max = 100, message = "审批人长度必须介于 1 和 100 之间")
	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	@Length(min = 1, max = 255, message = "审批人类型长度必须介于 1 和 255 之间")
	public String getApproverType() {
		return approverType;
	}

	public void setApproverType(String approverType) {
		this.approverType = approverType;
	}

	@Length(min = 1, max = 255, message = "任务key长度必须介于 1 和 255 之间")
	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	@Length(min = 1, max = 255, message = "任务名称长度必须介于 1 和 255 之间")
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "任务开始时间不能为空")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}