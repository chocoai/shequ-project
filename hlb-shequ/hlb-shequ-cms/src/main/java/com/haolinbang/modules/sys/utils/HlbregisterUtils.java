package com.haolinbang.modules.sys.utils;

import org.apache.commons.lang3.StringUtils;

import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.common.web.Servlets;
import com.haolinbang.modules.sys.dao.HlbregisterDao;
import com.haolinbang.modules.sys.entity.Hlbregister;
import com.haolinbang.modules.sys.entity.User;

/**
 * 读写注册表信息
 * 
 * @author Administrator
 * 
 */
public class HlbregisterUtils {
	private static HlbregisterDao hlbregisterDao = SpringContextHolder.getBean(HlbregisterDao.class);

	/**
	 * 保存分页大小
	 * 
	 * @param pageSize
	 */
	public static void savePageSize(int pageSize) {
		String value = (String) Servlets.getRequest().getSession().getAttribute("pageSize");
		if (StringUtils.isNotBlank(value)) {
			if (value.equals(String.valueOf(pageSize))) {
				return;
			}
		}
		saveValue("pageSize", String.valueOf(pageSize));
	}

	/**
	 * 获取分页大小
	 * 
	 * @return
	 */
	public static int getPageSize() {
		String value = (String) Servlets.getRequest().getSession().getAttribute("pageSize");
		if (StringUtils.isBlank(value)) {
			value = getValue("pageSize");
			if (StringUtils.isNotBlank(value)) {
				return Integer.valueOf(value);
			}
		}
		return 20;
	}

	/**
	 * 获取注册表的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		String value = null;
		// 现获取用户的值,如果用户的值为空,则获取默认值
		User user = UserUtils.getUser();
		Hlbregister userHlbregister = new Hlbregister();
		userHlbregister.setSource("system");
		userHlbregister.setSubsystem("hlb_system");
		userHlbregister.setRootkey(user.getId());
		userHlbregister.setKey(key);
		userHlbregister = hlbregisterDao.getHlbregister(userHlbregister);
		if (userHlbregister != null) {
			value = userHlbregister.getValue();
		}
		if (StringUtils.isBlank(value)) {
			Hlbregister sysHlbregister = new Hlbregister();
			sysHlbregister.setSubsystem("hlb_system");
			sysHlbregister.setKey(key);
			sysHlbregister = hlbregisterDao.getHlbregister(sysHlbregister);
			if (sysHlbregister != null) {
				value = sysHlbregister.getValue();
			}
		}
		return value;
	}

	/**
	 * 设置key、value的值
	 * 
	 * @param key
	 * @param value
	 */
	public static void saveValue(String key, String value) {
		User user = UserUtils.getUser();
		Hlbregister hlbregister = new Hlbregister();
		hlbregister.setSource("system");
		hlbregister.setSubsystem("hlb_system");
		hlbregister.setRootkey(user.getId());
		hlbregister.setKey(key);
		// 先查询
		Hlbregister hlbregister2 = hlbregisterDao.getHlbregister(hlbregister);
		if (hlbregister2 != null) {
			hlbregister2.setValue(value);
			hlbregisterDao.update(hlbregister2);
		} else {
			hlbregister.setValue(value);
			hlbregisterDao.insert(hlbregister);
		}
	}
}
