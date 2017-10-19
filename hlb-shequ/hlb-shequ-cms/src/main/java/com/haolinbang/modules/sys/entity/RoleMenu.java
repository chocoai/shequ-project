package com.haolinbang.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 角色-菜单Entity
 * @author nlp
 * @version 2017-08-14
 */
public class RoleMenu extends DataEntity<RoleMenu> {
	
	private static final long serialVersionUID = 1L;
	private String roleId;		// 角色编号
	private String menuId;		// 菜单编号
	
	public RoleMenu() {
		super();
	}

	public RoleMenu(String id){
		super(id);
	}

	@Length(min=1, max=64, message="角色编号长度必须介于 1 和 64 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	@Length(min=1, max=64, message="菜单编号长度必须介于 1 和 64 之间")
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
}