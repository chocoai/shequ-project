package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 物业评论表Entity
 * 
 * @author nlp
 * @version 2017-05-11
 */
public class WyEvaluate extends DataEntity<WyEvaluate> {

	private static final long serialVersionUID = 1L;
	private Integer relationId; // 关联id，可以为物业id，投诉id等评论内容
	private String memberId; // 评论会员id
	private String title; // 评价标题
	private String content; // 评论内容
	private Integer star1; // 评论星的个数
	private Integer star2; // 评论星的个数
	private Integer star3; // 评论星的个数
	private Integer star4; // 评论星的个数
	private Integer star5; // 评论星的个数

	public WyEvaluate() {
		super();
	}

	public WyEvaluate(String id) {
		super(id);
	}

	public Integer getRelationId() {
		return relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	@Length(min = 1, max = 32, message = "评论会员id长度必须介于 1 和 32 之间")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Length(min = 1, max = 200, message = "评价标题长度必须介于 1 和 200 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min = 0, max = 1000, message = "评论内容长度必须介于 0 和 1000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStar1() {
		return star1;
	}

	public void setStar1(Integer star1) {
		this.star1 = star1;
	}

	public Integer getStar2() {
		return star2;
	}

	public void setStar2(Integer star2) {
		this.star2 = star2;
	}

	public Integer getStar3() {
		return star3;
	}

	public void setStar3(Integer star3) {
		this.star3 = star3;
	}

	public Integer getStar4() {
		return star4;
	}

	public void setStar4(Integer star4) {
		this.star4 = star4;
	}

	public Integer getStar5() {
		return star5;
	}

	public void setStar5(Integer star5) {
		this.star5 = star5;
	}

}