package com.haolinbang.modules.sns.entity;

import java.util.Date;

import com.haolinbang.common.persistence.DataEntity;

public class PropertyCompany extends DataEntity<PropertyCompany> {//物业公司

	private static final long serialVersionUID = 5711943766062136833L;
	
	private Integer groupid;//主键
	private String groupname;//公司名称
	private String source;//公司代码
	private Date createtime;//创建时间
	
	public Integer getGroupid() {
		return groupid;
	}
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	
}
