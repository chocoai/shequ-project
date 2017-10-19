package com.haolinbang.modules.sys.entity;

import javax.validation.constraints.NotNull;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 用户权限表Entity
 * 
 * @author nlp
 * @version 2017-08-02
 */
public class AppUserPermission extends DataEntity<AppUserPermission> {

	private static final long serialVersionUID = 1L;
	private AppUser user; // 用户
	private Integer menuId; // 菜单id

	private Integer userId;// 用户id

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public AppUserPermission() {
		super();
	}

	public AppUserPermission(String id) {
		super(id);
	}

	@NotNull(message = "用户id不能为空")
	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	@NotNull(message = "菜单id不能为空")
	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

}