package com.haolinbang.modules.sys.entity;

import javax.validation.constraints.NotNull;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 用户角色表Entity
 * 
 * @author nlp
 * @version 2017-08-02
 */
public class AppUserRole extends DataEntity<AppUserRole> {

	private static final long serialVersionUID = 1L;
	private Integer roleId; // 角色id
	private long userId;// yinghu
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AppUserRole() {
		super();
	}

	public AppUserRole(String id) {
		super(id);
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@NotNull(message = "角色id不能为空")
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}