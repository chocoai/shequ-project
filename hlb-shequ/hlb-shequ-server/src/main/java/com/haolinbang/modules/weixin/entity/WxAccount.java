package com.haolinbang.modules.weixin.entity;

import com.haolinbang.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.haolinbang.modules.sys.entity.Area;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 微信公共帐号Entity
 * 
 * @author nlp
 * @version 2017-09-07
 */
public class WxAccount extends DataEntity<WxAccount> {

	private static final long serialVersionUID = 1L;
	private User user; // 用户id
	private String wxname; // 公众号名称
	private String appid; // appid
	private String appsecret; // appsecret
	private String originId; // 公众号原始ID
	private String wxnum; // 微信号
	private String logo; // 公司logo
	private String token; // token
	private Area area; // 区域id
	private String qq; // 公众号邮箱
	private Integer wxfans; // 微信粉丝
	private Integer typeId; // 分类ID
	private Boolean isDefault; // 是否为默认微信账号
	private String encodingAesKey; // 消息体加解密密钥
	private String source; // 公司对应的编码
	private String wyid; // 物业id
	private Integer groupId; // 组织机构id

	private String grabOrderTplId;// 抢单模板id
	private String cscTplId;// 客服中心模板id

	public String getGrabOrderTplId() {
		return grabOrderTplId;
	}

	public void setGrabOrderTplId(String grabOrderTplId) {
		this.grabOrderTplId = grabOrderTplId;
	}

	public String getCscTplId() {
		return cscTplId;
	}

	public void setCscTplId(String cscTplId) {
		this.cscTplId = cscTplId;
	}

	public WxAccount() {
		super();
	}

	public WxAccount(String id) {
		super(id);
	}

	@NotNull(message = "用户id不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Length(min = 1, max = 60, message = "公众号名称长度必须介于 1 和 60 之间")
	public String getWxname() {
		return wxname;
	}

	public void setWxname(String wxname) {
		this.wxname = wxname;
	}

	@Length(min = 1, max = 50, message = "appid长度必须介于 1 和 50 之间")
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	@Length(min = 1, max = 50, message = "appsecret长度必须介于 1 和 50 之间")
	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	@Length(min = 1, max = 20, message = "公众号原始ID长度必须介于 1 和 20 之间")
	public String getOriginId() {
		return originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	@Length(min = 1, max = 20, message = "微信号长度必须介于 1 和 20 之间")
	public String getWxnum() {
		return wxnum;
	}

	public void setWxnum(String wxnum) {
		this.wxnum = wxnum;
	}

	@Length(min = 0, max = 255, message = "公司logo长度必须介于 0 和 255 之间")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Length(min = 1, max = 32, message = "token长度必须介于 1 和 32 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Length(min = 0, max = 25, message = "公众号邮箱长度必须介于 0 和 25 之间")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Integer getWxfans() {
		return wxfans;
	}

	public void setWxfans(Integer wxfans) {
		this.wxfans = wxfans;
	}

	@NotNull(message = "分类ID不能为空")
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@NotNull(message = "是否为默认微信账号不能为空")
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Length(min = 0, max = 43, message = "消息体加解密密钥长度必须介于 0 和 43 之间")
	public String getEncodingAesKey() {
		return encodingAesKey;
	}

	public void setEncodingAesKey(String encodingAesKey) {
		this.encodingAesKey = encodingAesKey;
	}

	@Length(min = 0, max = 50, message = "公司对应的编码长度必须介于 0 和 50 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Length(min = 0, max = 100, message = "物业id长度必须介于 0 和 100 之间")
	public String getWyid() {
		return wyid;
	}

	public void setWyid(String wyid) {
		this.wyid = wyid;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

}