package com.haolinbang.modules.weixin.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 微信图文Entity
 * 
 * @author nlp
 * @version 2016-02-13
 */
public class WxNews extends DataEntity<WxNews> {

	private static final long serialVersionUID = 1L;
	private WxAccount account; // 关联的微信公众号id
	private String keyword; // keyword
	private String precisions; // precisions
	private String title; // 图文标题
	private String description; // 文章简介
	private String content; // 内容
	private String picurl; // 封面图片
	private Boolean isShowpic; // 图片是否显示封面
	private String url; // 图文外链地址
	private Integer click; // 点击数量
	private String writer; // 作者

	private String accountIds;// 可以查询的公众号ids

	public String getAccountIds() {
		return accountIds;
	}

	public void setAccountIds(String accountIds) {
		this.accountIds = accountIds;
	}

	public WxNews() {
		super();
	}

	public WxNews(String id) {
		super(id);
	}

	@NotNull(message = "微信公众号id不能为空")
	public WxAccount getAccount() {
		return account;
	}

	public void setAccount(WxAccount account) {
		this.account = account;
	}

	@Length(min = 1, max = 255, message = "keyword不能为空，且长度必须介于 1 和 255 之间")
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPrecisions() {
		return precisions;
	}

	public void setPrecisions(String precisions) {
		this.precisions = precisions;
	}

	@Length(min = 1, max = 60, message = "图文标题不能为空，且长度必须介于 1 和 60 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotNull(message = "文章简介不能为空")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull(message = "文章内容不能为空")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Length(min = 1, max = 255, message = "封面图片不能为空，且长度必须介于 1 和 255 之间")
	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public Boolean getIsShowpic() {
		return isShowpic;
	}

	public void setIsShowpic(Boolean isShowpic) {
		this.isShowpic = isShowpic;
	}

	@Length(min = 1, max = 255, message = "图文外链地址不能为空，且长度必须介于 1 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getClick() {
		return click;
	}

	public void setClick(Integer click) {
		this.click = click;
	}

	@Length(min = 0, max = 200, message = "作者不能为空，且长度必须介于 0 和 200 之间")
	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	@Override
	public String toString() {
		return "WxNews [account=" + account + ", keyword=" + keyword + ", precisions=" + precisions + ", title=" + title + ", description=" + description + ", content=" + content
				+ ", picurl=" + picurl + ", isShowpic=" + isShowpic + ", url=" + url + ", click=" + click + ", writer=" + writer + "]";
	}

}