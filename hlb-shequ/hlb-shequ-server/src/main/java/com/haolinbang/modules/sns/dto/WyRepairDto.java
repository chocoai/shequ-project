package com.haolinbang.modules.sns.dto;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyRepair;

public class WyRepairDto {

	private Member member;// 报修会员

	private Room room;// 报修的房屋

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	private String id;
	private String repairtype; // 报修类型
	private String applyname; // 报修姓名
	private String phone; // 联系电话
	private String content; // 报修内容简要说明
	private String contentdetail; // 报修详细说明
	private String imgurl; // 用户上传图片地址,多张图片用逗号隔开
	private Date appointmenttime; // appointmenttime
	private String procInsId; // 流程实例id
	private Integer memberid; // 会员id
	private String repairstatus; // 报修状态
	private Date createtime; // 创建时间
	private Date updatetime; // 更新时间

	private List<String> imgList;// 临时变量
	private int currStep;// 当前步骤
	private String currStepName;// 步骤名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Length(min = 1, max = 5, message = "报修类型长度必须介于 1 和 5 之间")
	public String getRepairtype() {
		return repairtype;
	}

	public void setRepairtype(String repairtype) {
		this.repairtype = repairtype;
	}

	@Length(min = 1, max = 100, message = "报修姓名长度必须介于 1 和 100 之间")
	public String getApplyname() {
		return applyname;
	}

	public void setApplyname(String applyname) {
		this.applyname = applyname;
	}

	@Length(min = 1, max = 20, message = "联系电话长度必须介于 1 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 1, max = 200, message = "报修内容简要说明长度必须介于 1 和 200 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Length(min = 1, max = 1000, message = "报修详细说明长度必须介于 1 和 1000 之间")
	public String getContentdetail() {
		return contentdetail;
	}

	public void setContentdetail(String contentdetail) {
		this.contentdetail = contentdetail;
	}

	@Length(min = 1, max = 4000, message = "用户上传图片地址,多张图片用逗号隔开长度必须介于 1 和 4000 之间")
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAppointmenttime() {
		return appointmenttime;
	}

	public void setAppointmenttime(Date appointmenttime) {
		this.appointmenttime = appointmenttime;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	@Length(min = 1, max = 32, message = "会员id长度必须介于 1 和 32 之间")
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	@Length(min = 1, max = 10, message = "报修状态长度必须介于 1 和 10 之间")
	public String getRepairstatus() {
		return repairstatus;
	}

	public void setRepairstatus(String repairstatus) {
		this.repairstatus = repairstatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "创建时间不能为空")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "更新时间不能为空")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public int getCurrStep() {
		return currStep;
	}

	public void setCurrStep(int currStep) {
		this.currStep = currStep;
	}

	public String getCurrStepName() {
		return currStepName;
	}

	public void setCurrStepName(String currStepName) {
		this.currStepName = currStepName;
	}

}
