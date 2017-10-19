package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 选项表Entity
 * @author wxc
 * @version 2017-06-12
 */
public class WyOption extends DataEntity<WyOption> {
	
	private static final long serialVersionUID = 1L;
	private Integer optionid;		// optionid
	private Integer subjectid;		// subjectid
	private Integer classificationid;
	private Integer questionnaireid;
	private String content;		// content
	private Integer weight;		// weight
	private Integer sortval;		// sortval
	private Date createtime;		// createtime
	private Date updatetime;		// updatetime
	
	private String title;//临时变量,不存数据库
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public WyOption() {
		super();
	}

	public WyOption(String id){
		super(id);
	}

	public Integer getOptionid() {
		return optionid;
	}

	public void setOptionid(Integer optionid) {
		this.optionid = optionid;
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

	@Length(min=1, max=255, message="content长度必须介于 1 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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