package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

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
	private Integer publisherRelation; // 回复谁
	private Boolean beReply; // 是否为回复
	private String comment; // 评论内容
	private WyMember publisherMember;// 发布会员
	private WyMember relationMember;// 回复谁

	private Boolean audit;// 是否已审核
	private String wymc;
	private String source;
	private String wyid;
	private String wyids;

	private String title;
	private String title1;
	private String title2;
	private String title3;
	private String title4;
	private String relationName;
	private String beginDate;
	private String endDate;
	
	public Boolean getAudit() {
		return audit;
	}

	public void setAudit(Boolean audit) {
		this.audit = audit;
	}

	public WyMember getPublisherMember() {
		return publisherMember;
	}

	public void setPublisherMember(WyMember publisherMember) {
		this.publisherMember = publisherMember;
	}

	public WyMember getRelationMember() {
		return relationMember;
	}

	public void setRelationMember(WyMember relationMember) {
		this.relationMember = relationMember;
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

	public Integer getPublisherRelation() {
		return publisherRelation;
	}

	public void setPublisherRelation(Integer publisherRelation) {
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

	public String getWymc() {
		return wymc;
	}

	public void setWymc(String wymc) {
		this.wymc = wymc;
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

	public String getWyids() {
		return wyids;
	}

	public void setWyids(String wyids) {
		this.wyids = wyids;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getTitle3() {
		return title3;
	}

	public void setTitle3(String title3) {
		this.title3 = title3;
	}

	public String getTitle4() {
		return title4;
	}

	public void setTitle4(String title4) {
		this.title4 = title4;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}