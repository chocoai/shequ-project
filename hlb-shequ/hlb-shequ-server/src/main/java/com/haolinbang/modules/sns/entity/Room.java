package com.haolinbang.modules.sns.entity;

import java.util.Date;

import com.haolinbang.common.persistence.DataEntity;

public class Room extends DataEntity<Room> {//房屋信息

	private static final long serialVersionUID = 5711943766062136833L;
	
	private Integer roomId;
	private String source;//公司代码
	private Integer WYID;//物业id
	private Integer groupId;
	private Integer HTID;//合同id
	private Integer KHID;//客户id
	private String WYNo;//物业编号
	private String WYName;//物业名称
	private Integer LYID;//楼盘id
	private String LYNo;
	private String LYName;
	private String roomNo;//房号
	private Boolean terminationStatus;//合同终止状态,0=正常，1=已终止
	private Date terminationDate;//合同终止日期
	private Integer memberId;//会员id
	private Date createtime;//创建时间
	private Date updatetime;
	
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getWYID() {
		return WYID;
	}
	public void setWYID(Integer wYID) {
		WYID = wYID;
	}
	public Integer getHTID() {
		return HTID;
	}
	public void setHTID(Integer hTID) {
		HTID = hTID;
	}
	public Integer getKHID() {
		return KHID;
	}
	public void setKHID(Integer kHID) {
		KHID = kHID;
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
	public Integer getLYID() {
		return LYID;
	}
	public void setLYID(Integer lYID) {
		LYID = lYID;
	}
	public String getLYNo() {
		return LYNo;
	}
	public void setLYNo(String lYNo) {
		LYNo = lYNo;
	}
	public String getLYName() {
		return LYName;
	}
	public void setLYName(String lYName) {
		LYName = lYName;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public Boolean getTerminationStatus() {
		return terminationStatus;
	}
	public void setTerminationStatus(Boolean terminationStatus) {
		this.terminationStatus = terminationStatus;
	}
	public Date getTerminationDate() {
		return terminationDate;
	}
	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	

}
