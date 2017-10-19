package com.haolinbang.modules.weixin.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 微信图文消息Entity
 * 
 * @author nlp
 * @version 2017-07-11
 */
public class WxNewsArticle extends DataEntity<WxNewsArticle> {

	private static final long serialVersionUID = 1L;
	private String thumbMediaId; // (必填) 图文消息缩略图的media_id
	private String author; // 图文消息的作者
	private String title; // (必填) 图文消息的标题
	private String contentSourceUrl; // 在图文消息页面点击&ldquo;阅读原文&rdquo;后的页面链接
	private String content; // (必填) 图文消息页面的内容，支持HTML标签
	private String digest; // 图文消息的描述
	private Boolean showCoverPic; // 是否显示封面，true为显示，false为不显示
	private String url;// 临时变量，图片base64
	private String accountId;// 账户id,记录该素材属于哪个账户
	private String imgLocalUrl;// 图片地址

	public String getImgLocalUrl() {
		return imgLocalUrl;
	}

	public void setImgLocalUrl(String imgLocalUrl) {
		this.imgLocalUrl = imgLocalUrl;
	}

	public WxNewsArticle() {
		super();
	}

	public WxNewsArticle(String id) {
		super(id);
	}

	@Length(min = 1, max = 100, message = "(必填) 图文消息缩略图的media_id长度必须介于 1 和 100 之间")
	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Length(min = 0, max = 50, message = "图文消息的作者长度必须介于 0 和 50 之间")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Length(min = 1, max = 255, message = "(必填) 图文消息的标题长度必须介于 1 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min = 0, max = 255, message = "在图文消息页面点击&ldquo;阅读原文&rdquo;后的页面链接长度必须介于 0 和 255 之间")
	public String getContentSourceUrl() {
		return contentSourceUrl;
	}

	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl = contentSourceUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Length(min = 0, max = 255, message = "图文消息的描述长度必须介于 0 和 255 之间")
	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	@NotNull(message = "是否显示封面，true为显示，false为不显示不能为空")
	public Boolean getShowCoverPic() {
		return showCoverPic;
	}

	public void setShowCoverPic(Boolean showCoverPic) {
		this.showCoverPic = showCoverPic;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}