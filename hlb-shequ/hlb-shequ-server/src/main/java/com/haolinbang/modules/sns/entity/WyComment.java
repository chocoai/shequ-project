package com.haolinbang.modules.sns.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 评论和回复表Entity
 * 
 * @author nlp
 * @version 2017-07-18
 */
public class WyComment extends DataEntity<WyComment> {

	private static final long serialVersionUID = 1L;
	private String bizType; // 业务类型
	private Integer relationId; // 对应的业务表id
	private Integer publisher; // 发表言论的人
	private String publisherRelation; // 回复谁
	private Boolean beReply; // 是否为回复
	private String comment; // 评论内容
	private Boolean audit;// 是否已审核
	private String commentId;// 评论id,当用户发表回复时,该字段必须有值

	private List<WyComment> comments;//回复评论List
	private String publisherName;//发表人姓名
	private String publisherAvatar;//发表人头像
	private String publisherRelationName;//回复谁
	private String createTime;
	
	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public Boolean getAudit() {
		return audit;
	}

	public void setAudit(Boolean audit) {
		this.audit = audit;
	}

	public WyComment() {
		super();
	}

	public WyComment(String id) {
		super(id);
	}

	@Length(min = 1, max = 50, message = "业务类型长度必须介于 1 和 50 之间")
	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	@NotNull(message = "对应的业务表id不能为空")
	public Integer getRelationId() {
		return relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	@NotNull(message = "发表言论的人不能为空")
	public Integer getPublisher() {
		return publisher;
	}

	public void setPublisher(Integer publisher) {
		this.publisher = publisher;
	}

	public String getPublisherRelation() {
		return publisherRelation;
	}

	public void setPublisherRelation(String publisherRelation) {
		this.publisherRelation = publisherRelation;
	}

	@NotNull(message = "是否为回复不能为空")
	public Boolean getBeReply() {
		return beReply;
	}

	public void setBeReply(Boolean beReply) {
		this.beReply = beReply;
	}

	@Length(min = 0, max = 1000, message = "评论内容长度必须介于 0 和 1000 之间")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<WyComment> getComments() {
		return comments;
	}

	public void setComments(List<WyComment> comments) {
		this.comments = comments;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublisherAvatar() {
		return publisherAvatar;
	}

	public void setPublisherAvatar(String publisherAvatar) {
		this.publisherAvatar = publisherAvatar;
	}

	public String getPublisherRelationName() {
		return publisherRelationName;
	}

	public void setPublisherRelationName(String publisherRelationName) {
		this.publisherRelationName = publisherRelationName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}