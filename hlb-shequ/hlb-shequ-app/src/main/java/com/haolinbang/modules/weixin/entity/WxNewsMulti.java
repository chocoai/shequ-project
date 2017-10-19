package com.haolinbang.modules.weixin.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 微信多图文Entity
 * 
 * @author nlp
 * @version 2016-02-13
 */
public class WxNewsMulti extends DataEntity<WxNewsMulti> {

	private static final long serialVersionUID = 1L;
	private WxAccount account; // 关联的微信公众号id
	private String keywords; // 关键字
	private String news; // 图文标题

	private String accountIds;// 可以查询的公众号ids

	public String getAccountIds() {
		return accountIds;
	}

	public void setAccountIds(String accountIds) {
		this.accountIds = accountIds;
	}

	public WxNewsMulti() {
		super();
	}

	public WxNewsMulti(String id) {
		super(id);
	}

	@NotNull(message = "关联的微信公众号id不能为空")
	public WxAccount getAccount() {
		return account;
	}

	public void setAccount(WxAccount account) {
		this.account = account;
	}

	@Length(min = 1, max = 255, message = "关键字长度必须介于 1 和 255 之间")
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Length(min = 1, max = 60, message = "图文标题长度必须介于 1 和 60 之间")
	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}

}