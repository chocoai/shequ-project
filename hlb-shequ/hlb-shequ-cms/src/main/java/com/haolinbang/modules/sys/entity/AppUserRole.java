package com.haolinbang.modules.sys.entity;

import com.haolinbang.modules.sys.entity.User;
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
	private User user; // 用户id
	private Integer roleId; // 角色id
	private Long userId;// 用户id

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public AppUserRole() {
		super();
	}

	public AppUserRole(String id) {
		super(id);
	}

	@NotNull(message = "用户id不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@NotNull(message = "角色id不能为空")
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}