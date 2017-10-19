package com.haolinbang.modules.sns.entity;

import java.util.Date;

import com.haolinbang.common.persistence.DataEntity;
import com.haolinbang.modules.sys.entity.User;

public class Member extends DataEntity<Member> {// 会员信息

	private static final long serialVersionUID = 5711943766062136833L;

	private Integer memberId;// 会员id(GUID
	private String appid;
	private String memberName;// 会员姓名
	private String openid;// 微信openid
	private String roomId;// 默认房号
	private Character memberType;// 会员类型，0-游客 1-会员 2-关联
	private Integer parentMemberId;// 主会员id
	private Boolean syncStatus;// 同步状态
	private String syncDesc;// 信息描述
	private String mobile;// 手机
	private Date createtime;// 创建时间
	private String status;// 状态：0-无效 1-有效
	private String blacklist;
	private Integer admintype;
	private String ygID;
	private String czID;
	private String groupID;
	private String avatarurl;// 微信头像
	private String nickname;// 昵称
	private Integer sex;
	private String source;

	private User staff;// 员工信息

	public User getStaff() {
		return staff;
	}

	public void setStaff(User staff) {
		this.staff = staff;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Character getMemberType() {
		return memberType;
	}

	public void setMemberType(Character memberType) {
		this.memberType = memberType;
	}

	public Integer getParentMemberId() {
		return parentMemberId;
	}

	public void setParentMemberId(Integer parentMemberId) {
		this.parentMemberId = parentMemberId;
	}

	public Boolean getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Boolean syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getSyncDesc() {
		return syncDesc;
	}

	public void setSyncDesc(String syncDesc) {
		this.syncDesc = syncDesc;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	public Integer getAdmintype() {
		return admintype;
	}

	public void setAdmintype(Integer admintype) {
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

	public String getAvatarurl() {
		return avatarurl;
	}

	public void setAvatarurl(String avatarurl) {
		this.avatarurl = avatarurl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
}
