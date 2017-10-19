package com.haolinbang.modules.weixin.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 微信消息Entity
 * @author nlp
 * @version 2016-02-06
 */
public class WxMessage extends DataEntity<WxMessage> {
	
	private static final long serialVersionUID = 1L;
	private String toUserName;		// 发送的消息
	private String fromUserName;		// 发送的用户
	private String content;		// 消息内容
	private Long createTime;		// 创建时间
	private String msgType;		// 消息类型
	private Long msgId;		// 消息id
	private String picUrl;		// 图片地址
	private String mediaid;		// 媒体id
	private String format;		// 格式
	private String thumbMediaId;		// 缩略图
	private Double locationX;		// x坐标
	private Long locationY;		// y坐标
	private Double scale;		// 地图的放大等级
	private String label;		// 标签
	private String title;		// 标题
	private String description;		// 描述
	private String url;		// Url地址
	private String event;		// 事件
	private String eventkey;		// 事件key
	private String ticket;		// 票据信息
	private Double latitude;		// 维度
	private Double longitude;		// 经度
	private Double precision;		// 位置精度
	private String recognition;		// Recognition
	
	public WxMessage() {
		super();
	}

	public WxMessage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="发送的消息长度必须介于 0 和 64 之间")
	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	
	@Length(min=0, max=64, message="发送的用户长度必须介于 0 和 64 之间")
	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=32, message="消息类型长度必须介于 0 和 32 之间")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	
	@Length(min=0, max=255, message="图片地址长度必须介于 0 和 255 之间")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	@Length(min=0, max=32, message="媒体id长度必须介于 0 和 32 之间")
	public String getMediaid() {
		return mediaid;
	}

	public void setMediaid(String mediaid) {
		this.mediaid = mediaid;
	}
	
	@Length(min=0, max=16, message="格式长度必须介于 0 和 16 之间")
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	@Length(min=0, max=32, message="缩略图长度必须介于 0 和 32 之间")
	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
	public Double getLocationX() {
		return locationX;
	}

	public void setLocationX(Double locationX) {
		this.locationX = locationX;
	}
	
	public Long getLocationY() {
		return locationY;
	}

	public void setLocationY(Long locationY) {
		this.locationY = locationY;
	}
	
	public Double getScale() {
		return scale;
	}

	public void setScale(Double scale) {
		this.scale = scale;
	}
	
	@Length(min=0, max=64, message="标签长度必须介于 0 和 64 之间")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="描述长度必须介于 0 和 255 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=255, message="Url地址长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=64, message="事件长度必须介于 0 和 64 之间")
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	@Length(min=0, max=64, message="事件key长度必须介于 0 和 64 之间")
	public String getEventkey() {
		return eventkey;
	}

	public void setEventkey(String eventkey) {
		this.eventkey = eventkey;
	}
	
	@Length(min=0, max=255, message="票据信息长度必须介于 0 和 255 之间")
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}
	
	@Length(min=0, max=255, message="Recognition长度必须介于 0 和 255 之间")
	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	
}