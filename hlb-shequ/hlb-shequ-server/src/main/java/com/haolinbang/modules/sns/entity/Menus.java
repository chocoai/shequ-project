package com.haolinbang.modules.sns.entity;

import com.haolinbang.common.persistence.DataEntity;

public class Menus extends DataEntity<Menus>{
	
	private static final long serialVersionUID = 1L;
	private String parentIds; // 所有父级编号
	private String name; // 名称
	private String href; // 链接
	private String icon; // 图标
	private Integer sort; // 排序
	private String isShow; // 是否在菜单中显示（1：显示；0：不显示）
	private Integer type; //菜单类型（0：首页菜单；1：个人中心菜单）
	private String backgroundColor;
	private Integer backgroundType;
	private String screenCode;//屏蔽码
	
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public Integer getBackgroundType() {
		return backgroundType;
	}
	public void setBackgroundType(Integer backgroundType) {
		this.backgroundType = backgroundType;
	}
	public String getScreenCode() {
		return screenCode;
	}
	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}
}
