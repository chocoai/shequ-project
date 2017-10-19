package com.haolinbang.modules.weixin.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.modules.sns.entity.WyBuilding;
import com.haolinbang.modules.sys.entity.Area;
import com.haolinbang.modules.sys.entity.User;

/**
 * 微信公账号Entity
 * 
 * @author nlp
 * @version 2016-01-10
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
	private String token; // 验证token
	private Area area; // 区域id
	private String qq; // 公众号邮箱
	private Integer wxfans; // 微信粉丝
	private Integer typeId; // 分类ID
	private Boolean isDefault; // 是否为默认微信号
	private String encodingAESKey;
	private String source;// 公司编码,用于调用不同公司的接口地址
	private String wyid;// 物业id

	private String wymc;// 物业名称

	private WyBuilding wyBuilding;// 物业信息

	private GroupInfo group;// 组织机构

	private Integer groupId;// 组织结构id

	private String groupIds;// 组织机构ids

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public GroupInfo getGroup() {
		return group;
	}

	public void setGroup(GroupInfo group) {
		this.group = group;
	}

	public String getWymc() {
		return wymc;
	}

	public void setWymc(String wymc) {
		this.wymc = wymc;
	}

	public WyBuilding getWyBuilding() {
		return wyBuilding;
	}

	public void setWyBuilding(WyBuilding wyBuilding) {
		this.wyBuilding = wyBuilding;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getWyid() {
		return wyid;
	}

	public void setWyid(String wyid) {
		this.wyid = wyid;
	}

	public WxAccount() {
		super();
	}

	public WxAccount(String id) {
		super(id);
	}

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

	@Length(min = 1, max = 25, message = "公众号邮箱长度必须介于 1 和 25 之间")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	// @NotNull(message = "微信粉丝不能为空")
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

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getEncodingAESKey() {
		return encodingAESKey;
	}

	public void setEncodingAESKey(String encodingAESKey) {
		this.encodingAESKey = encodingAESKey;
	}

	@Override
	public String toString() {
		return "WxAccount [user=" + user + ", wxname=" + wxname + ", appid=" + appid + ", appsecret=" + appsecret + ", originId=" + originId + ", wxnum=" + wxnum + ", logo="
				+ logo + ", token=" + token + ", area=" + area + ", qq=" + qq + ", wxfans=" + wxfans + ", typeId=" + typeId + ", isDefault=" + isDefault + ", encodingAESKey="
				+ encodingAESKey + "]";
	}

}