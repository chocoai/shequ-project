package com.haolinbang.modules.sys.vo;

import java.io.Serializable;

import com.haolinbang.modules.sys.entity.AppMenu;

/**
 * 菜单vo
 * 
 * @author Administrator
 * 
 */
public class AppMenuVo implements Serializable {

	private static final long serialVersionUID = -6437912838331280717L;
	private String id;// id
	private AppMenu parent; // 父级编号
	private String parentIds; // 所有父级编号
	private String name; // 名称
	private Integer sort; // 排序
	private String href; // 链接
	private String target; // 目标
	private String icon; // 图标
	private String color; // 文字显示颜色
	private String isShow; // 是否在菜单中显示
	private String permission; // 权限标识
	private String sysType; // 子系统系统类别编码
	private String screenCode;

	private String permissionStrs;// 该菜单所有的菜单集合

	private String view;// 查看权限
	private String add;// 添加权限
	private String edit;// 编辑权限
	private String del;// 删除权限
	private String audit;// 审核权限
	private String print;// 打印权限
	private String export;// 导出权限
	private String daoru;// 导入权限
	private String exec;// 执行权限

	private Boolean viewChecked;// 查看权限,是否已选中
	private Boolean addChecked;// 添加权限,是否已选中
	private Boolean editChecked;// 编辑权限,是否已选中
	private Boolean delChecked;// 删除权限,是否已选中
	private Boolean auditChecked;// 审核权限,是否已选中
	private Boolean printChecked;// 打印权限,是否已选中
	private Boolean exportChecked;// 导出权限,是否已选中
	private Boolean daoruChecked;// 导入权限,是否已选中
	private Boolean execChecked;// 执行权限,是否已选中

	private String uid;// 授权用户id
	private String ids;// 菜单ids
	private String gid;// 分组id
	private String role;// 角色

	private String[] groupIds;// 组织机构id

	public String[] getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}

	public String getExec() {
		return exec;
	}

	public void setExec(String exec) {
		this.exec = exec;
	}

	public Boolean getExecChecked() {
		return execChecked;
	}

	public void setExecChecked(Boolean execChecked) {
		this.execChecked = execChecked;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Boolean getViewChecked() {
		return viewChecked;
	}

	public void setViewChecked(Boolean viewChecked) {
		this.viewChecked = viewChecked;
	}

	public Boolean getAddChecked() {
		return addChecked;
	}

	public void setAddChecked(Boolean addChecked) {
		this.addChecked = addChecked;
	}

	public Boolean getEditChecked() {
		return editChecked;
	}

	public void setEditChecked(Boolean editChecked) {
		this.editChecked = editChecked;
	}

	public Boolean getDelChecked() {
		return delChecked;
	}

	public void setDelChecked(Boolean delChecked) {
		this.delChecked = delChecked;
	}

	public Boolean getAuditChecked() {
		return auditChecked;
	}

	public void setAuditChecked(Boolean auditChecked) {
		this.auditChecked = auditChecked;
	}

	public Boolean getPrintChecked() {
		return printChecked;
	}

	public void setPrintChecked(Boolean printChecked) {
		this.printChecked = printChecked;
	}

	public Boolean getExportChecked() {
		return exportChecked;
	}

	public void setExportChecked(Boolean exportChecked) {
		this.exportChecked = exportChecked;
	}

	public Boolean getDaoruChecked() {
		return daoruChecked;
	}

	public void setDaoruChecked(Boolean daoruChecked) {
		this.daoruChecked = daoruChecked;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AppMenu getParent() {
		return parent;
	}

	public void setParent(AppMenu parent) {
		this.parent = parent;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getPermissionStrs() {
		return permissionStrs;
	}

	public void setPermissionStrs(String permissionStrs) {
		this.permissionStrs = permissionStrs;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getExport() {
		return export;
	}

	public void setExport(String export) {
		this.export = export;
	}

	public String getDaoru() {
		return daoru;
	}

	public void setDaoru(String daoru) {
		this.daoru = daoru;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}
}
