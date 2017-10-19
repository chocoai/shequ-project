package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
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
	private Integer complainId; // complain_id
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

	private WyMember member;// 客户名称

	public WyMember getMember() {
		return member;
	}

	public void setMember(WyMember member) {
		this.member = member;
	}

	public WyComplain() {
		super();
	}

	public WyComplain(String id) {
		super(id);
	}

	@NotNull(message = "complain_id不能为空")
	public Integer getComplainId() {
		return complainId;
	}

	public void setComplainId(Integer complainId) {
		this.complainId = complainId;
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

}