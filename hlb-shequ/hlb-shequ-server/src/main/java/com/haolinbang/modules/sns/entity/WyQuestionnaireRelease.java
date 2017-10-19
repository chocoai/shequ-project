package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 问卷发布表Entity
 * @author wxc
 * @version 2017-06-19
 */
public class WyQuestionnaireRelease extends DataEntity<WyQuestionnaireRelease> {
	
	private static final long serialVersionUID = 1L;
	private Integer releaseId;		// release_id
	private Integer questionnaireid;		// questionnaireid
	private Integer num;		// num
	private Integer runstatus;
	private Date endtime;		// endtime
	private Date createtime;		// createtime
	
	private Integer status;
	private String title;
	
	public WyQuestionnaireRelease() {
		super();
	}

	public WyQuestionnaireRelease(String id){
		super(id);
	}

	@NotNull(message="release_id不能为空")
	public Integer getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(Integer releaseId) {
		this.releaseId = releaseId;
	}
	
	@NotNull(message="questionnaireid不能为空")
	public Integer getQuestionnaireid() {
		return questionnaireid;
	}

	public void setQuestionnaireid(Integer questionnaireid) {
		this.questionnaireid = questionnaireid;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public Integer getRunstatus() {
		return runstatus;
	}
	

	public void setRunstatus(Integer runstatus) {
		this.runstatus = runstatus;
	}
	

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="createtime不能为空")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}