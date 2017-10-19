package com.haolinbang.modules.weixin.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 自动回复Entity
 * 
 * @author nlp
 * @version 2016-02-08
 */
public class WxAreply extends DataEntity<WxAreply> {

	private static final long serialVersionUID = 1L;
	private String keyword; // 关键字
	private String content; // 内容
	private WxAccount account; // 关联公众号id
	private Boolean isDefault; // 是否默认

	public WxAreply() {
		super();
	}

	public WxAreply(String id) {
		super(id);
	}

	@Length(min = 1, max = 100, message = "关键字长度必须介于 1 和 100 之间")
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@NotNull(message = "关联公众号id不能为空")
	public WxAccount getAccount() {
		return account;
	}

	public void setAccount(WxAccount account) {
		this.account = account;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public String toString() {
		return "WxAreply [keyword=" + keyword + ", content=" + content + ", account=" + account + ", isDefault=" + isDefault + "]";
	}

}