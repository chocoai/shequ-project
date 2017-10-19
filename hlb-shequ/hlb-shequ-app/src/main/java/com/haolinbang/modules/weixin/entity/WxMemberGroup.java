package com.haolinbang.modules.weixin.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 微信公众号粉丝分组Entity
 * @author nlp
 * @version 2016-01-31
 */
public class WxMemberGroup extends DataEntity<WxMemberGroup> {
	
	private static final long serialVersionUID = 1L;
	private WxAccount account;		// 关联微信公众号
	private String groupName;		// 分组名称
	
	public WxMemberGroup() {
		super();
	}

	public WxMemberGroup(String id){
		super(id);
	}

	@NotNull(message="关联微信公众号不能为空")
	public WxAccount getAccount() {
		return account;
	}

	public void setAccount(WxAccount account) {
		this.account = account;
	}
	
	@Length(min=1, max=80, message="分组名称长度必须介于 1 和 80 之间")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}