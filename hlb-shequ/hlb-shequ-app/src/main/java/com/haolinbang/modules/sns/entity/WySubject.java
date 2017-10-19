package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 题目表Entity
 * @author wxc
 * @version 2017-06-09
 */
public class WySubject extends DataEntity<WySubject> {
	
	private static final long serialVersionUID = 1L;
	private Integer subjectid;		// subjectid
	private Integer classificationid;		// classificationid
	private Integer questionnaireid;
	private String title;		// title
	private Integer type;		// 0-单选 1-多选2-填空
	private Integer notnull;		// 0-否 1-是
	private Integer sortval;		// sortval
	private Integer status;		// 0-无效 1-有效
	private Date createtime;		// createtime
	private Date updatetime;		// updatetime
	
	private String classificationname;//临时变量
	
	public String getClassificationname() {
		return classificationname;
	}

	public void setClassificationname(String classificationname) {
		this.classificationname = classificationname;
	}

	public WySubject() {
		super();
	}

	public WySubject(String id){
		super(id);
	}

	@Length(min=1, max=255, message="title长度必须介于 1 和 255 之间")
	public String getTitle() {
		return title;
	}

	public Integer getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}

	public Integer getClassificationid() {
		return classificationid;
	}

	public void setClassificationid(Integer classificationid) {
		this.classificationid = classificationid;
	}

	public Integer getQuestionnaireid() {
		return questionnaireid;
	}

	public void setQuestionnaireid(Integer questionnaireid) {
		this.questionnaireid = questionnaireid;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull(message="0-单选 1-多选2-填空不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getNotnull() {
		return notnull;
	}

	public void setNotnull(Integer notnull) {
		this.notnull = notnull;
	}

	public Integer getSortval() {
		return sortval;
	}

	public void setSortval(Integer sortval) {
		this.sortval = sortval;
	}
	
	@NotNull(message="0-无效 1-有效不能为空")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="createtime不能为空")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="updatetime不能为空")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
}