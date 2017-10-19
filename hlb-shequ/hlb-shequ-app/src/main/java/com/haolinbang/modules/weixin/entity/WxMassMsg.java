package com.haolinbang.modules.weixin.entity;

import java.util.List;

import com.haolinbang.common.persistence.DataEntity;
import com.haolinbang.modules.sns.entity.WyMember;

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

	private String wyIds;// 物业id
	private String memberIds;// 会员id
	private String ldids;// 楼栋id

	private Integer needSendNum;// 需要发送的条数
	private Integer currSendNum;// 当前发送条数

	private List<WyMember> memberList;// 会员列表

	private String memberName;// 会员名称

	private WxMassMsgCommon wxMassMsgCommon;// 通用消息模板
	private WxAccount wxAccount;// 微信公众号

	public WxAccount getWxAccount() {
		return wxAccount;
	}

	public void setWxAccount(WxAccount wxAccount) {
		this.wxAccount = wxAccount;
	}

	public WxMassMsgCommon getWxMassMsgCommon() {
		return wxMassMsgCommon;
	}

	public void setWxMassMsgCommon(WxMassMsgCommon wxMassMsgCommon) {
		this.wxMassMsgCommon = wxMassMsgCommon;
	}

	public String getLdids() {
		return ldids;
	}

	public void setLdids(String ldids) {
		this.ldids = ldids;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(String memberIds) {
		this.memberIds = memberIds;
	}

	public List<WyMember> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<WyMember> memberList) {
		this.memberList = memberList;
	}

	public Integer getNeedSendNum() {
		return needSendNum;
	}

	public void setNeedSendNum(Integer needSendNum) {
		this.needSendNum = needSendNum;
	}

	public Integer getCurrSendNum() {
		return currSendNum;
	}

	public void setCurrSendNum(Integer currSendNum) {
		this.currSendNum = currSendNum;
	}

	public String getWyIds() {
		return wyIds;
	}

	public void setWyIds(String wyIds) {
		this.wyIds = wyIds;
	}

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

}