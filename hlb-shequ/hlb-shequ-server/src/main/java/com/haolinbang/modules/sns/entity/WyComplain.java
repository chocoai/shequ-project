package com.haolinbang.modules.sns.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haolinbang.common.persistence.DataEntity;

/**
 * 物业投诉表Entity
 * 
 * @author wxc
 * @version 2017-07-04
 */
public class WyComplain extends DataEntity<WyComplain> {

	private static final long serialVersionUID = 1L;
	private Integer complainArea; // 0.私有区域 1.公共区域
	private String phone; // phone
	private String content; // content
	private String contentdetail; // contentdetail
	private String imgurl; // imgurl
	private String memberid; // memberid
	private Date createtime; // createtime
	private Date updatetime; // updatetime
	private String procInsId; // procinsid
	private Integer roomid; // roomid
	private String applyname; // applyname
	private Integer status;

	private List<String> imgList;// 临时变量
	private int currStep;// 当前步骤
	private String currStepName;// 步骤名称

	private String source;// 公司编码
	private Integer wyid;// 物业id
	private Integer groupId;// 组织机构id

	public WyComplain() {
		super();
	}

	public WyComplain(String id) {
		super(id);
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getWyid() {
		return wyid;
	}

	public void setWyid(Integer wyid) {
		this.wyid = wyid;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getComplainArea() {
		return complainArea;
	}

	public void setComplainArea(Integer complainArea) {
		this.complainArea = complainArea;
	}

	@Length(min = 0, max = 255, message = "phone长度必须介于 0 和 255 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 0, max = 255, message = "content长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Length(min = 0, max = 255, message = "contentdetail长度必须介于 0 和 255 之间")
	public String getContentdetail() {
		return contentdetail;
	}

	public void setContentdetail(String contentdetail) {
		this.contentdetail = contentdetail;
	}

	@Length(min = 0, max = 255, message = "imgurl长度必须介于 0 和 255 之间")
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	@Length(min = 0, max = 255, message = "memberid长度必须介于 0 和 255 之间")
	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public Integer getRoomid() {
		return roomid;
	}

	public void setRoomid(Integer roomid) {
		this.roomid = roomid;
	}

	@Length(min = 0, max = 255, message = "applyname长度必须介于 0 和 255 之间")
	public String getApplyname() {
		return applyname;
	}

	public void setApplyname(String applyname) {
		this.applyname = applyname;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}