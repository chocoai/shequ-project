package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 跳蚤市场Entity
 * 
 * @author nlp
 * @version 2017-07-07
 */
public class WyFleaMarket extends DataEntity<WyFleaMarket> {

	private static final long serialVersionUID = 1L;
	private Integer memberId; // 发布会员
	private String goodsName; // 物品名称
	private String goodsDesc; // 物品描述
	private Double price; // 价格
	private Boolean giftGiving; // 是否爱心赠送
	private String imgs; // 图片l列表，用逗号隔开
	private String locationAddress; // 定位地址
	private Integer commentNum; // 评论数
	private Integer dianzanNum; // 点赞数
	private Double longitude; // 百度地图经度
	private Double latitude; // 百度地图维度

	private WyMember member;// 发布人
	private Boolean audit;// 是否已审核

	public Boolean getAudit() {
		return audit;
	}

	public void setAudit(Boolean audit) {
		this.audit = audit;
	}

	public WyMember getMember() {
		return member;
	}

	public void setMember(WyMember member) {
		this.member = member;
	}

	public WyFleaMarket() {
		super();
	}

	public WyFleaMarket(String id) {
		super(id);
	}

	@NotNull(message = "发布会员不能为空")
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Length(min = 1, max = 100, message = "物品名称长度必须介于 1 和 100 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Length(min = 0, max = 255, message = "物品描述长度必须介于 0 和 255 之间")
	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@NotNull(message = "是否爱心赠送不能为空")
	public Boolean getGiftGiving() {
		return giftGiving;
	}

	public void setGiftGiving(Boolean giftGiving) {
		this.giftGiving = giftGiving;
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

}