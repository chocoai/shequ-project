package com.haolinbang.modules.weixin.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 微信文本Entity
 * 
 * @author nlp
 * @version 2016-02-13
 */
public class WxText extends DataEntity<WxText> {

	private static final long serialVersionUID = 1L;
	private String keyword; // 关键字
	private String content; // 内容
	private WxAccount account; // 关联公众号id
	private Integer click; // 点击数量

	private String accountIds;// 可以查询的公众号ids

	public String getAccountIds() {
		return accountIds;
	}

	public void setAccountIds(String accountIds) {
		this.accountIds = accountIds;
	}

	public WxText() {
		super();
	}

	public WxText(String id) {
		super(id);
	}

	@Length(min = 1, max = 100, message = "关键字长度必须介于 1 和 100 之间")
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@NotNull(message = "回复内容不能为空")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public WxAccount getAccount() {
		return account;
	}

	public void setAccount(WxAccount account) {
		this.account = account;
	}

	public Integer getClick() {
		return click;
	}

	public void setClick(Integer click) {
		this.click = click;
	}

}