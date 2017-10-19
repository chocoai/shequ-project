package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 分类表Entity
 * @author wxc
 * @version 2017-06-09
 */
public class WyClassification extends DataEntity<WyClassification> {
	
	private static final long serialVersionUID = 1L;
	private Integer classificationid;		// classificationid
	private String classificationname;
	private Integer questionnaireid;		// questionnaireid
	private Integer weight;		// weight
	private Integer sortval;		// sortval
	private Integer status;		// 0-无效 1-有效
	private Date createtime;		// createtime
	private Date updatetime;		// updatetime
	
	private String title;//临时变量，不存数据库，用于后台显示
	
	public WyClassification() {
		super();
	}

	public WyClassification(String id){
		super(id);
	}
	
	public String getClassificationname() {
		return classificationname;
	}

	public void setClassificationname(String classificationname) {
		this.classificationname = classificationname;
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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
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

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	
	
}