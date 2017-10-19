package com.haolinbang.modules.sns.dto;

import java.io.Serializable;

/**
 * 树形结构
 * 
 * @author Administrator
 * 
 */
public class ZtreeDataDto implements Serializable {

	private static final long serialVersionUID = 8128101654557542552L;

	private String id;

	private String pid;

	private String name;

	private String group;// 组织机构

	private String type;// 类型

	private boolean chkDisabled;// 是否禁用复选框

	private boolean checked;// 是否已勾选

	private boolean open;// 是否展开

	private boolean nocheck;// 是否没有复选框

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
