package com.haolinbang.modules.weixin.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 群发消息用户表Entity
 * @author nlp
 * @version 2017-08-22
 */
public class WxMassUser extends DataEntity<WxMassUser> {
	
	private static final long serialVersionUID = 1L;
	private String openid;		// 发送给用户的openid
	private Integer massMsgId;		// 发送的消息关联表
	
	public WxMassUser() {
		super();
	}

	public WxMassUser(String id){
		super(id);
	}

	@Length(min=1, max=50, message="发送给用户的openid长度必须介于 1 和 50 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@NotNull(message="发送的消息关联表不能为空")
	public Integer getMassMsgId() {
		return massMsgId;
	}

	public void setMassMsgId(Integer massMsgId) {
		this.massMsgId = massMsgId;
	}
	
}