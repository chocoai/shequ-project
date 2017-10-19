package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 会员信息Entity
 * 
 * @author nlp
 * @version 2017-06-02
 */
public class WyMember extends DataEntity<WyMember> {

	private static final long serialVersionUID = 1L;
	private Integer memberId; // 会员id(GUID)
	private String appid;
	private String openid; // 微信openid
	private String roomid; // 默认房号
	private String memberType; // 会员类型，0-游客 1-会员 2-关联
	private Integer parentMemberId; // 父id
	private String mobile; // 手机号码
	private String syncStatus; // 是否同步成功，1=成功,0=失败
	private String syncDesc; // 同步失败原因说明
	private Date createtime; // 创建时间
	private String memberName; // membername
	private String status; // 状态，1=有效,0=无效
	private String blacklist; // blacklist
	private String admintype;
	private String ygID;
	private String czID;
	private String groupID;
	
	private String WYName;
	private String LYName;
	private String roomNo;
	private String parentMemberName;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public Integer getParentMemberId() {
		return parentMemberId;
	}

	public void setParentMemberId(Integer parentMemberId) {
		this.parentMemberId = parentMemberId;
	}

	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getSyncDesc() {
		return syncDesc;
	}

	public void setSyncDesc(String syncDesc) {
		this.syncDesc = syncDesc;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public WyMember() {
		super();
	}

	public WyMember(String id) {
		super(id);
	}

	@Length(min = 1, max = 32, message = "微信openid长度必须介于 1 和 32 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Length(min = 0, max = 10, message = "默认房号长度必须介于 0 和 10 之间")
	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	@Length(min = 0, max = 20, message = "手机号码长度必须介于 0 和 20 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Length(min = 0, max = 32, message = "状态长度必须介于 0 和 32 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Length(min = 0, max = 32, message = "blacklist长度必须介于 0 和 32 之间")
	public String getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	public String getAdmintype() {
		return admintype;
	}

	public void setAdmintype(String admintype) {
		this.admintype = admintype;
	}

	public String getYgID() {
		return ygID;
	}

	public void setYgID(String ygID) {
		this.ygID = ygID;
	}

	public String getCzID() {
		return czID;
	}

	public void setCzID(String czID) {
		this.czID = czID;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getWYName() {
		return WYName;
	}

	public void setWYName(String wYName) {
		WYName = wYName;
	}

	public String getLYName() {
		return LYName;
	}

	public void setLYName(String lYName) {
		LYName = lYName;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getParentMemberName() {
		return parentMemberName;
	}

	public void setParentMemberName(String parentMemberName) {
		this.parentMemberName = parentMemberName;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
}