package com.haolinbang.modules.sns.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haolinbang.common.persistence.DataEntity;

/**
 * 问卷调查表Entity
 * @author wxc
 * @version 2017-06-08
 */
public class WyQuestionnaire extends DataEntity<WyQuestionnaire> {
	
	private static final long serialVersionUID = 1L;
	private Integer questionnaireid;		// questionnaireid
	private String title;		// title
	private String explain;		// explain
	private Integer sortval;		// sortval
	private Integer status;		// status
	private Date createtime;		// createtime
	private Date updatetime;		// updatetime
	private String source;
	private Integer groupid;
	private String creater;
	
	public WyQuestionnaire() {
		super();
	}

	public WyQuestionnaire(String id){
		super(id);
	}

	
	
	public Integer getQuestionnaireid() {
		return questionnaireid;
	}

	public void setQuestionnaireid(Integer questionnaireid) {
		this.questionnaireid = questionnaireid;
	}

	@Length(min=1, max=255, message="title长度必须介于 1 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="explain长度必须介于 0 和 255 之间")
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	public Integer getSortval() {
		return sortval;
	}

	public void setSortval(Integer sortval) {
		this.sortval = sortval;
	}
	
	@NotNull(message="status不能为空")
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
}