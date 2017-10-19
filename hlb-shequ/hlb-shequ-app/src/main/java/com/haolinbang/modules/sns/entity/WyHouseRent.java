package com.haolinbang.modules.sns.entity;

import java.util.List;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 房屋租售Entity
 * 
 * @author nlp
 * @version 2017-07-07
 */
public class WyHouseRent extends DataEntity<WyHouseRent> {

	private static final long serialVersionUID = 1L;
	private Integer memberId; // 发布会员
	private String rentalType; // 租售类型
	private String title; // 发布标题
	private String houseDesc; // 房屋描述
	private Double monthlyRent; // 月租
	private String imgs; // 图片l列表，用逗号隔开
	private String locationAddress; // 定位地址
	private Integer commentNum; // 评论数
	private Integer dianzanNum; // 点赞数
	private Double longitude; // 百度地图经度
	private Double latitude; // 百度地图维度
	private String source;
	private Integer groupId;
	

	private Boolean audit;// 是否已审核
	private String wymc;
	private String wyid;
	private String wyids;
	
	private List<String> imgList;// 临时变量

	public Boolean getAudit() {
		return audit;
	}

	public void setAudit(Boolean audit) {
		this.audit = audit;
	}

	public WyHouseRent() {
		super();
	}

	public WyHouseRent(String id) {
		super(id);
	}

	@NotNull(message = "发布会员不能为空")
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Length(min = 1, max = 255, message = "租售类型长度必须介于 1 和 255 之间")
	public String getRentalType() {
		return rentalType;
	}

	public void setRentalType(String rentalType) {
		this.rentalType = rentalType;
	}

	@Length(min = 1, max = 100, message = "发布标题长度必须介于 1 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min = 0, max = 255, message = "房屋描述长度必须介于 0 和 255 之间")
	public String getHouseDesc() {
		return houseDesc;
	}

	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}

	public Double getMonthlyRent() {
		return monthlyRent;
	}

	public void setMonthlyRent(Double monthlyRent) {
		this.monthlyRent = monthlyRent;
	}

	@Length(min = 0, max = 255, message = "图片l列表，用逗号隔开长度必须介于 0 和 255 之间")
	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	@Length(min = 0, max = 255, message = "定位地址长度必须介于 0 和 255 之间")
	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getDianzanNum() {
		return dianzanNum;
	}

	public void setDianzanNum(Integer dianzanNum) {
		this.dianzanNum = dianzanNum;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public String getWymc() {
		return wymc;
	}

	public void setWymc(String wymc) {
		this.wymc = wymc;
	}

	public String getWyid() {
		return wyid;
	}

	public void setWyid(String wyid) {
		this.wyid = wyid;
	}

	public String getWyids() {
		return wyids;
	}

	public void setWyids(String wyids) {
		this.wyids = wyids;
	}
	
	
}