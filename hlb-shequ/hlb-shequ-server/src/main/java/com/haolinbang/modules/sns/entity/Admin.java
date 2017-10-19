package com.haolinbang.modules.sns.entity;

import java.util.Date;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 管理员表
 * 
 * @author Administrator
 * 
 */
public class Admin extends DataEntity<Account> {

	private static final long serialVersionUID = -2754021626470826223L;

	private String memberId;

	private String openid;

	private String loginName;

	private String memberType;

	private String source;

	private String mobile;

	private Boolean syncStatus;

	private String syncDesc;

	private Date createtime;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
