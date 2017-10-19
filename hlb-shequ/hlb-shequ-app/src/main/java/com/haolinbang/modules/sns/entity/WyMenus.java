package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 首页菜单Entity
 * @author wxc
 * @version 2017-06-02
 */
public class WyMenus extends DataEntity<WyMenus> {
	
	private static final long serialVersionUID = 1L;
	private String parentids;		// parentids
	private String name;		// name
	private String href;		// href
	private String icon;		// icon
	private Integer sort;		// sort
	private String isshow;		// isshow
	private Integer type;		//0.首页菜单 1.个人中心菜单
	private String backgroundColor;
	
 	public WyMenus() {
		super();
	}

	public WyMenus(String id){
		super(id);
	}

	@Length(min=0, max=255, message="parentids长度必须介于 0 和 255 之间")
	public String getParentids() {
		return parentids;
	}

	public void setParentids(String parentids) {
		this.parentids = parentids;
	}
	
	@Length(min=0, max=255, message="name长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="href长度必须介于 0 和 255 之间")
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
	@Length(min=0, max=1023, message="icon长度必须介于 0 和 1023 之间")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@NotNull(message="sort不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="isshow长度必须介于 0 和 255 之间")
	public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
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
	
	
	
}