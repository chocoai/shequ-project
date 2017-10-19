package com.haolinbang.modules.sns.entity;

import com.haolinbang.common.persistence.DataEntity;

public class Account extends DataEntity<Account> {

	private static final long serialVersionUID = 1L;
	private String appid; // 平台分配的应用编号
	private String appuserid; // 用户在商户应用的唯一标识，建议为用户帐号
	private String WYID;// 物业编号
	private String status; // 1-有效，0-无效
	private String appvKey;// 应用key(服务商秘钥)
	private String appSecret;// 公众号秘钥
	private String source;// 来源
	private String platpKey;// platp_key(微信appid)
	private String platsystem;// 新增, 支付平台 0-爱贝 1-好邻邦微信 2-好邻邦支付宝
	private Boolean offline;// 新增, false=线上支付 true=线下支付
	private String WXID;//维修id
	private String LTID;//临停id

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppuserid() {
		return appuserid;
	}

	public void setAppuserid(String appuserid) {
		this.appuserid = appuserid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWYID() {
		return WYID;
	}

	public void setWYID(String wYID) {
		WYID = wYID;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPlatsystem() {
		return platsystem;
	}

	public void setPlatsystem(String platsystem) {
		this.platsystem = platsystem;
	}

	public Boolean getOffline() {
		return offline == null ? false : offline;
	}

	public void setOffline(Boolean offline) {
		this.offline = offline;
	}

	public String getAppvKey() {
		return appvKey;
	}

	public void setAppvKey(String appvKey) {
		this.appvKey = appvKey;
	}

	public String getPlatpKey() {
		return platpKey;
	}

	public void setPlatpKey(String platpKey) {
		this.platpKey = platpKey;
	}

	public String getWXID() {
		return WXID;
	}

	public void setWXID(String wXID) {
		WXID = wXID;
	}

	public String getLTID() {
		return LTID;
	}

	public void setLTID(String lTID) {
		LTID = lTID;
	}

}
