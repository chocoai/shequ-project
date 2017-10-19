package com.haolinbang.modules.sys.entity;

import javax.validation.constraints.NotNull;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 角色菜单表Entity
 * @author nlp
 * @version 2017-08-02
 */
public class AppRoleMenu extends DataEntity<AppRoleMenu> {
	
	private static final long serialVersionUID = 1L;
	private Integer roleId;		// 角色id
	private Integer menuId;		// 菜单id
	
	public AppRoleMenu() {
		super();
	}

	public AppRoleMenu(String id){
		super(id);
	}

	@NotNull(message="角色id不能为空")
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	@NotNull(message="菜单id不能为空")
	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
}