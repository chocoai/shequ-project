package com.haolinbang.modules.sys.entity;

import com.haolinbang.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 应用平台角色Entity
 * 
 * @author nlp
 * @version 2017-08-02
 */
public class AppRole extends DataEntity<AppRole> {

	private static final long serialVersionUID = 1L;
	private Office office; // 归属机构
	private String name; // 角色名称
	private String enname; // 英文名称
	private String roleType; // 角色类型
	private Boolean isSys; // 是否系统数据
	private Boolean useable; // 是否可用
	private String sysType;// 系统平台编码

	private String permission; // 权限标识

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public AppRole() {
		super();
	}

	public AppRole(String id) {
		super(id);
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@Length(min = 1, max = 100, message = "角色名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 255, message = "英文名称长度必须介于 0 和 255 之间")
	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	@Length(min = 0, max = 255, message = "角色类型长度必须介于 0 和 255 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Boolean getIsSys() {
		return isSys;
	}

	public void setIsSys(Boolean isSys) {
		this.isSys = isSys;
	}

	public Boolean getUseable() {
		return useable;
	}

	public void setUseable(Boolean useable) {
		this.useable = useable;
	}

}