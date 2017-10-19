package com.haolinbang.modules.sys.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.modules.sys.dao.AppMenuDao;
import com.haolinbang.modules.sys.entity.AppMenu;
import com.haolinbang.modules.sys.utils.LogUtils;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 应用平台菜单表Service
 * 
 * @author nlp
 * @version 2017-08-02
 */
@Service
@Transactional(readOnly = true)
public class AppMenuService extends CrudService<AppMenuDao, AppMenu> {

	@Autowired
	private AppMenuDao appMenuDao;

	public AppMenu get(String id) {
		return super.get(id);
	}

	public List<AppMenu> findList(AppMenu appMenu) {
		return super.findList(appMenu);
	}

	public Page<AppMenu> findPage(Page<AppMenu> page, AppMenu appMenu) {
		return super.findPage(page, appMenu);
	}

	@Transactional(readOnly = false)
	public void save(AppMenu menu) {
		String icon = menu.getIcon();
		if (StringUtils.isNotBlank(icon) && icon.contains("icon-")) {
			icon = icon.substring(icon.indexOf("icon-") + 5);
			menu.setIcon(icon);
		}

		// 获取父节点实体
		menu.setParent(this.get(menu.getParent().getId()));
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds();
		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");
		// 保存或更新实体
		super.save(menu);
		// 更新子节点 parentIds
		AppMenu m = new AppMenu();
		m.setParentIds("%," + menu.getId() + ",%");
		List<AppMenu> list = appMenuDao.findByParentIdsLike(m);
		for (AppMenu e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			appMenuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_APP_MENU_LIST);
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);

	}

	@Transactional(readOnly = false)
	public void delete(AppMenu appMenu) {
		super.delete(appMenu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_APP_MENU_LIST);
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	/**
	 * 更新菜单排序
	 * 
	 * @param menu
	 */
	@Transactional(readOnly = false)
	public void updateMenuSort(AppMenu menu) {
		appMenuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_APP_MENU_LIST);
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

}