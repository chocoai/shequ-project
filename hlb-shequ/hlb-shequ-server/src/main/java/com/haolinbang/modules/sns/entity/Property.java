package com.haolinbang.modules.sns.entity;

import java.util.Date;

import com.haolinbang.common.persistence.DataEntity;

public class Property extends DataEntity<Property> {//物业项目

	private static final long serialVersionUID = 5711943766062136833L;
	
	private Integer WYID;//物业id
	private String WYNo;//物业编号
	private String WYName;//物业名称
	private String source;//公司代码
	private Integer districtId;//地区id
	private Date createtime;//创建时间
	
	public Integer getWYID() {
		return WYID;
	}
	public void setWYID(Integer wYID) {
		WYID = wYID;
	}
	public String getWYNo() {
		return WYNo;
	}
	public void setWYNo(String wYNo) {
		WYNo = wYNo;
	}
	public String getWYName() {
		return WYName;
	}
	public void setWYName(String wYName) {
		WYName = wYName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	

}
