package com.haolinbang.modules.weixin.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 图片素材对应关系表Entity
 * @author nlp
 * @version 2017-07-14
 */
public class WxMaterialImages extends DataEntity<WxMaterialImages> {
	
	private static final long serialVersionUID = 1L;
	private String remoteUrl;		// 远程地址，比如微信服务器地址
	private String localUrl;		// 本地地址
	private String type;		// 图片类型
	private String accountId;		// 公众号id
	
	public WxMaterialImages() {
		super();
	}

	public WxMaterialImages(String id){
		super(id);
	}

	@Length(min=1, max=255, message="远程地址，比如微信服务器地址长度必须介于 1 和 255 之间")
	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}
	
	@Length(min=1, max=255, message="本地地址长度必须介于 1 和 255 之间")
	public String getLocalUrl() {
		return localUrl;
	}

	public void setLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}
	
	@Length(min=1, max=10, message="图片类型长度必须介于 1 和 10 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=1, max=64, message="公众号id长度必须介于 1 和 64 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
}