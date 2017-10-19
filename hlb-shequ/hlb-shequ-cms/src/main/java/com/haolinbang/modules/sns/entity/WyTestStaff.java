package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import com.haolinbang.modules.weixin.entity.WxMemberGroup;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 管理人员表Entity
 * @author nlp
 * @version 2017-06-23
 */
public class WyTestStaff extends DataEntity<WyTestStaff> {
	
	private static final long serialVersionUID = 1L;
	private Integer staffId;		// 员工id
	private WxMemberGroup group;		// 所属组织机构
	private String staffNo;		// 员工编号
	private String staffName;		// 员工姓名
	private String job;		// 职位
	private String leave;		// 是否离职
	private Date leaveDate;		// 离职时间
	
	public WyTestStaff() {
		super();
	}

	public WyTestStaff(String id){
		super(id);
	}

	@NotNull(message="员工id不能为空")
	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	
	@NotNull(message="所属组织机构不能为空")
	public WxMemberGroup getGroup() {
		return group;
	}

	public void setGroup(WxMemberGroup group) {
		this.group = group;
	}
	
	@Length(min=0, max=30, message="员工编号长度必须介于 0 和 30 之间")
	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	
	@Length(min=0, max=20, message="员工姓名长度必须介于 0 和 20 之间")
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	@Length(min=0, max=50, message="职位长度必须介于 0 和 50 之间")
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	@Length(min=1, max=1, message="是否离职长度必须介于 1 和 1 之间")
	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	
}