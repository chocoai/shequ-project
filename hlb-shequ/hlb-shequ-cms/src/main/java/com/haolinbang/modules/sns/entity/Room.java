package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 房屋信息Entity
 * @author wxc
 * @version 2017-06-05
 */
public class Room extends DataEntity<Room> {
	
	private static final long serialVersionUID = 1L;
	private Integer roomid;		// roomid
	private String source;		// 公司代码
	private Integer wyid;		// 物业id
	private Integer htid;		// 合同id
	private Integer khid;		// 客户id
	private String wyno;		// 物业编号
	private String wyname;		// 物业名称
	private Integer lyid;		// 楼盘id
	private String lyno;		// 楼盘编号
	private String lyname;		// 楼盘名称
	private String roomno;		// 房号
	private String terminationstatus;		// 合同终止状态,0=正常，1=已终止
	private Date terminationdate;		// 合同终止日期
	private Integer memberid;		// 会员id
	private Date createtime;		// 创建时间
	private Date updatetime;		// 更新时间
	
	private String membername;//临时变量，不存数据库
	
	public Room() {
		super();
	}

	public Room(String id){
		super(id);
	}

	@NotNull(message="roomid不能为空")
	public Integer getRoomid() {
		return roomid;
	}

	public void setRoomid(Integer roomid) {
		this.roomid = roomid;
	}
	
	@Length(min=1, max=20, message="公司代码长度必须介于 1 和 20 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@NotNull(message="物业id不能为空")
	public Integer getWyid() {
		return wyid;
	}

	public void setWyid(Integer wyid) {
		this.wyid = wyid;
	}
	
	@NotNull(message="合同id不能为空")
	public Integer getHtid() {
		return htid;
	}

	public void setHtid(Integer htid) {
		this.htid = htid;
	}
	
	@NotNull(message="客户id不能为空")
	public Integer getKhid() {
		return khid;
	}

	public void setKhid(Integer khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=20, message="物业编号长度必须介于 0 和 20 之间")
	public String getWyno() {
		return wyno;
	}

	public void setWyno(String wyno) {
		this.wyno = wyno;
	}
	
	@Length(min=0, max=100, message="物业名称长度必须介于 0 和 100 之间")
	public String getWyname() {
		return wyname;
	}

	public void setWyname(String wyname) {
		this.wyname = wyname;
	}
	
	public Integer getLyid() {
		return lyid;
	}

	public void setLyid(Integer lyid) {
		this.lyid = lyid;
	}
	
	@Length(min=0, max=20, message="lyno长度必须介于 0 和 20 之间")
	public String getLyno() {
		return lyno;
	}

	public void setLyno(String lyno) {
		this.lyno = lyno;
	}
	
	@Length(min=0, max=100, message="lyname长度必须介于 0 和 100 之间")
	public String getLyname() {
		return lyname;
	}

	public void setLyname(String lyname) {
		this.lyname = lyname;
	}
	
	@Length(min=0, max=10, message="房号长度必须介于 0 和 10 之间")
	public String getRoomno() {
		return roomno;
	}

	public void setRoomno(String roomno) {
		this.roomno = roomno;
	}
	
	@Length(min=1, max=1, message="合同终止状态,0=正常，1=已终止长度必须介于 1 和 1 之间")
	public String getTerminationstatus() {
		return terminationstatus;
	}

	public void setTerminationstatus(String terminationstatus) {
		this.terminationstatus = terminationstatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTerminationdate() {
		return terminationdate;
	}

	public void setTerminationdate(Date terminationdate) {
		this.terminationdate = terminationdate;
	}
	
	@Length(min=0, max=32, message="会员id长度必须介于 0 和 32 之间")
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}
}