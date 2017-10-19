package com.haolinbang.modules.sys.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 应用平台菜单表Entity
 * 
 * @author nlp
 * @version 2017-08-02
 */
public class AppMenu extends DataEntity<AppMenu> {

	private static final long serialVersionUID = 1L;
	private AppMenu parent; // 父级编号
	private String parentIds; // 所有父级编号
	private String name; // 名称
	private String sort; // 排序
	private String href; // 链接
	private String target; // 目标
	private String icon; // 图标
	private String color; // 文字显示颜色
	private String isShow; // 是否在菜单中显示
	private String permission; // 权限标识
	private String sysType; // 子系统系统类别编码
	private String screenCode;//屏蔽码

	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	public AppMenu() {
		super();
	}

	public AppMenu(String id) {
		super(id);
	}

	@JsonBackReference
	@NotNull(message = "父级编号不能为空")
	public AppMenu getParent() {
		return parent;
	}

	public void setParent(AppMenu parent) {
		this.parent = parent;
	}

	@Length(min = 1, max = 2000, message = "所有父级编号长度必须介于 1 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Length(min = 1, max = 100, message = "名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Length(min = 0, max = 2000, message = "链接长度必须介于 0 和 2000 之间")
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Length(min = 0, max = 20, message = "目标长度必须介于 0 和 20 之间")
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Length(min = 0, max = 100, message = "图标长度必须介于 0 和 100 之间")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Length(min = 0, max = 100, message = "文字显示颜色长度必须介于 0 和 100 之间")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Length(min = 1, max = 1, message = "是否在菜单中显示长度必须介于 1 和 1 之间")
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	@Length(min = 0, max = 200, message = "权限标识长度必须介于 0 和 200 之间")
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Length(min = 0, max = 20, message = "子系统系统类别编码长度必须介于 0 和 20 之间")
	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}
	
	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	@JsonIgnore
	public static void sortList(List<AppMenu> list, List<AppMenu> sourcelist, String parentId, boolean cascade) {
		for (int i = 0; i < sourcelist.size(); i++) {
			AppMenu e = sourcelist.get(i);
			if (e.getParent() != null && e.getParent().getId() != null && e.getParent().getId().equals(parentId)) {
				list.add(e);
				if (cascade) {
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j = 0; j < sourcelist.size(); j++) {
						AppMenu child = sourcelist.get(j);
						if (child.getParent() != null && child.getParent().getId() != null && child.getParent().getId().equals(e.getId())) {
							sortList(list, sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}

}