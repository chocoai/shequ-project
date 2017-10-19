package com.haolinbang.modules.weixin.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 群发消息记录表Entity
 * 
 * @author nlp
 * @version 2017-07-11
 */
public class WxMassMsg extends DataEntity<WxMassMsg> {

	private static final long serialVersionUID = 1L;
	private String name; // 群发名称
	private String toUsers; // 群发名称
	private String newsArticleId; // 图文消息id
	private String newsId;// 图文消息id集合
	private String account_id;
	private String msgId;// 群发消息后的id,回调的时候可以查询
	private Boolean sendStatus;// 发送状态

	public Boolean getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Boolean sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public WxMassMsg() {
		super();
	}

	public WxMassMsg(String id) {
		super(id);
	}

	@Length(min = 1, max = 20, message = "群发名称长度必须介于 1 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 1, max = 1000, message = "群发名称长度必须介于 1 和 1000 之间")
	public String getToUsers() {
		return toUsers;
	}

	public void setToUsers(String toUsers) {
		this.toUsers = toUsers;
	}

	public String getNewsArticleId() {
		return newsArticleId;
	}

	public void setNewsArticleId(String newsArticleId) {
		this.newsArticleId = newsArticleId;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

}