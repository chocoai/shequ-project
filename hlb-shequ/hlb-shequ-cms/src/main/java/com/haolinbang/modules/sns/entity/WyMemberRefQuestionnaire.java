package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 会员调查表Entity
 * @author wxc
 * @version 2017-06-19
 */
public class WyMemberRefQuestionnaire extends DataEntity<WyMemberRefQuestionnaire> {
	
	private static final long serialVersionUID = 1L;
	private Integer memQueId;		// mem_que_id
	private Integer memberid;		// memberid
	private Integer questionnaireid;		// questionnaireid
	private Integer subjectid;		// subjectid
	private Integer optionid;		// optionid
	private String content;		// content
	private Date createtime;		// createtime
	
	public WyMemberRefQuestionnaire() {
		super();
	}

	public WyMemberRefQuestionnaire(String id){
		super(id);
	}

	@NotNull(message="mem_que_id不能为空")
	public Integer getMemQueId() {
		return memQueId;
	}

	public void setMemQueId(Integer memQueId) {
		this.memQueId = memQueId;
	}
	
	@NotNull(message="memberid不能为空")
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@NotNull(message="questionnaireid不能为空")
	public Integer getQuestionnaireid() {
		return questionnaireid;
	}

	public void setQuestionnaireid(Integer questionnaireid) {
		this.questionnaireid = questionnaireid;
	}
	
	@NotNull(message="subjectid不能为空")
	public Integer getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}
	
	public Integer getOptionid() {
		return optionid;
	}

	public void setOptionid(Integer optionid) {
		this.optionid = optionid;
	}
	
	@Length(min=0, max=255, message="content长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="createtime不能为空")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}