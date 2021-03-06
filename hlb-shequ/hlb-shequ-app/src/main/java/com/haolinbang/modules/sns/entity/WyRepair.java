package com.haolinbang.modules.sns.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haolinbang.common.persistence.ActEntity;

/**
 * 物业报修Entity
 * 
 * @author nlp
 * @version 2017-04-17
 */
public class WyRepair extends ActEntity<WyRepair> {

	private static final long serialVersionUID = 1L;
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
	private Integer roomId;
	private String sponsor;// 流程发起人
	private String serviceman;// 维修处理人
	private String room;// 房屋信息
	private String groupId;// 分组id

	private List<String> imgList;// 临时变量
	private String memberName;// 临时变量
	private int currStep;// 当前步骤
	private String currStepName;// 步骤名称
	private String wymc;
	private String source;
	private String wyid;
	private String wyids;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public WyRepair() {
		super();
	}

	public WyRepair(String id) {
		super(id);
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
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

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getServiceman() {
		return serviceman;
	}

	public void setServiceman(String serviceman) {
		this.serviceman = serviceman;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getWymc() {
		return wymc;
	}

	public void setWymc(String wymc) {
		this.wymc = wymc;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getWyid() {
		return wyid;
	}

	public void setWyid(String wyid) {
		this.wyid = wyid;
	}

	public String getWyids() {
		return wyids;
	}

	public void setWyids(String wyids) {
		this.wyids = wyids;
	}
}