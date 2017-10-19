package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 工作流审批记录表Entity
 * 
 * @author nlp
 * @version 2017-06-29
 */
public class WyApprove extends DataEntity<WyApprove> {

	private static final long serialVersionUID = 1L;
	private String name; // 审批名称
	private String code; // 审批编码
	private String type; // 审批类型,对应具体业务
	private Date startTime; // 审批开始时间
	private Date endTime; // 审批结束时间
	private Boolean available; // 是否有效
	private String sponsor; // 发起人
	private String sponsorType; // 发起人类型
	private Boolean finished; // 是否已完成
	private String procDefId; // 流程定义id
	private String procInstId; // 流程实例id
	private String currTaskKey; // 当前任务key
	private String currTaskName; // 当前任务名称
	private String bizTable; // 业务表,对应处理业务的表
	private String bizId; // 业务id,可以通过biz_table查找到业务数据
	private String groupId;// 部门id

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public WyApprove() {
		super();
	}

	public WyApprove(String id) {
		super(id);
	}

	@Length(min = 1, max = 100, message = "审批名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 1, max = 50, message = "审批编码长度必须介于 1 和 50 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min = 1, max = 50, message = "审批类型,对应具体业务长度必须介于 1 和 50 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "审批开始时间不能为空")
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

	@NotNull(message = "是否有效不能为空")
	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Length(min = 1, max = 100, message = "发起人长度必须介于 1 和 100 之间")
	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	@Length(min = 1, max = 10, message = "发起人类型长度必须介于 1 和 10 之间")
	public String getSponsorType() {
		return sponsorType;
	}

	public void setSponsorType(String sponsorType) {
		this.sponsorType = sponsorType;
	}

	@NotNull(message = "是否已完成不能为空")
	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	@Length(min = 1, max = 64, message = "流程定义id长度必须介于 1 和 64 之间")
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	@Length(min = 1, max = 32, message = "流程实例id长度必须介于 1 和 32 之间")
	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	@Length(min = 1, max = 100, message = "当前任务key长度必须介于 1 和 100 之间")
	public String getCurrTaskKey() {
		return currTaskKey;
	}

	public void setCurrTaskKey(String currTaskKey) {
		this.currTaskKey = currTaskKey;
	}

	@Length(min = 1, max = 255, message = "当前任务名称长度必须介于 1 和 255 之间")
	public String getCurrTaskName() {
		return currTaskName;
	}

	public void setCurrTaskName(String currTaskName) {
		this.currTaskName = currTaskName;
	}

	@Length(min = 1, max = 30, message = "业务表,对应处理业务的表长度必须介于 1 和 30 之间")
	public String getBizTable() {
		return bizTable;
	}

	public void setBizTable(String bizTable) {
		this.bizTable = bizTable;
	}

	@Length(min = 1, max = 100, message = "业务id,可以通过biz_table查找到业务数据长度必须介于 1 和 100 之间")
	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

}