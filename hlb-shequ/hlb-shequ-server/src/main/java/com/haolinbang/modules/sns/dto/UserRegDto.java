package com.haolinbang.modules.sns.dto;

import java.io.Serializable;

/**
 * 用户注册dto
 * 
 * @author Administrator
 * 
 */
public class UserRegDto implements Serializable {

	private static final long serialVersionUID = 6391086700641247527L;

	private String mobile;// 手机号码

	private String mainMemberPhone;// 关联会员手机号码
	
	private String parentMemberId;// 关联会员ID

	private String memberName;// 会员姓名

	private String idNo;// 身份证号码

	private String isAssociate;// 是否是关联会员
	
	private String wymc;
	
	private String buildingId;
	
	private Integer admintype;
	
	private String ygID;
	
	private String regType;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMainMemberPhone() {
		return mainMemberPhone;
	}

	public void setMainMemberPhone(String mainMemberPhone) {
		this.mainMemberPhone = mainMemberPhone;
	}

	public String getParentMemberId() {
		return parentMemberId;
	}

	public void setParentMemberId(String parentMemberId) {
		this.parentMemberId = parentMemberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIsAssociate() {
		return isAssociate;
	}

	public void setIsAssociate(String isAssociate) {
		this.isAssociate = isAssociate;
	}

	public String getWymc() {
		return wymc;
	}

	public void setWymc(String wymc) {
		this.wymc = wymc;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
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

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}
	
	
}
