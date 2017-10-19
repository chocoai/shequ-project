package com.haolinbang.modules.sys.entity;

import com.haolinbang.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 用户-角色Entity
 * @author nlp
 * @version 2017-08-14
 */
public class UserRole extends DataEntity<UserRole> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户编号
	private String roleId;		// 角色编号
	
	public UserRole() {
		super();
	}

	public UserRole(String id){
		super(id);
	}

	@NotNull(message="用户编号不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=1, max=64, message="角色编号长度必须介于 1 和 64 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}