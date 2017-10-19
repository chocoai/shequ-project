package com.haolinbang.modules.sns.entity;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 群发消息记录表Entity
 * 
 * @author nlp
 * @version 2017-07-11
 */
public class WxMassMsg extends DataEntity<WxMassMsg> {

	private static final long serialVersionUID = -7976701909040515929L;
	private String name; // 群发名称
	private String toUsers; // 群发名称
	private String msgId; // 发送消息后的id，回调会用到
	private String newsArticleId; // 图文消息id
	private Boolean sendStatus; // 发送状态
	private String accountId; // account_id
	private String msgType; // 消息类型，1=图文消息，2=模板消息
	private String type; // 发送给哪些用户，1=物业，2=指定用户，3=公司，4=软件公司
	private String source; // 公司source
	private String wyid; // 物业id
	private String newsId;// 图文id
	private String url;// 发送链接地址
	private String title;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToUsers() {
		return toUsers;
	}

	public void setToUsers(String toUsers) {
		this.toUsers = toUsers;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getNewsArticleId() {
		return newsArticleId;
	}

	public void setNewsArticleId(String newsArticleId) {
		this.newsArticleId = newsArticleId;
	}

	public Boolean getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Boolean sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getWyid() {
		return wyid;
	}

	public void setWyid(String wyid) {
		this.wyid = wyid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	

}