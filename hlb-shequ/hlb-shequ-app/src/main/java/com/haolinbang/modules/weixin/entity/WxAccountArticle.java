package com.haolinbang.modules.weixin.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 账号和文章对应Entity
 * @author nlp
 * @version 2016-02-07
 */
public class WxAccountArticle extends DataEntity<WxAccountArticle> {
	
	private static final long serialVersionUID = 1L;
	private String articleId;		// 对应文章id
	private WxAccount account;		// 微信公众号id
	
	public WxAccountArticle() {
		super();
	}

	public WxAccountArticle(String id){
		super(id);
	}

	@Length(min=1, max=64, message="对应文章id长度必须介于 1 和 64 之间")
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	@NotNull(message="微信公众号id不能为空")
	public WxAccount getAccount() {
		return account;
	}

	public void setAccount(WxAccount account) {
		this.account = account;
	}
	
}