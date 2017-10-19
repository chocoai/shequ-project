package com.haolinbang.modules.weixin.utils;

import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.weixin.dao.WxAccountDao;
import com.haolinbang.modules.weixin.entity.WxAccount;

public class AccountUtil {

	public static final String WX_ACCOUNT_CACHE = "wxAccountCache";

	public static final String WX_ACCOUNT_CACHE_ID = "wxAccountCache_id_";

	public static final String WX_ACCOUNT_CACHE_NAME = "wxAccountCache_name_";

	private static WxAccountDao wxAccountDao = SpringContextHolder.getBean(WxAccountDao.class);

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
	public static WxAccount get(String id) {
		WxAccount account = (WxAccount) CacheUtils.get(WX_ACCOUNT_CACHE, WX_ACCOUNT_CACHE_ID + id);
		if (account == null) {
			account = wxAccountDao.get(id);
			if (account == null) {
				return null;
			}
			CacheUtils.put(WX_ACCOUNT_CACHE, WX_ACCOUNT_CACHE_ID + account.getId(), account);
			CacheUtils.put(WX_ACCOUNT_CACHE, WX_ACCOUNT_CACHE_NAME + account.getWxname(), account);
		}
		return account;
	}

	/**
	 * 将默认账号缓存到内存中
	 * 
	 * @param id
	 * @param account
	 */
	public static void put(String id, WxAccount account) {
		if (account != null) {
			CacheUtils.put(WX_ACCOUNT_CACHE, WX_ACCOUNT_CACHE_ID + id, account);
		}
	}

	/**
	 * 将默认account放入缓存中
	 * 
	 * @param id
	 * @param account
	 */
	public static void putAccount(WxAccount account) {
		put(UserUtils.getUser().getId(), account);
	}

	/**
	 * 获取默认微信Account
	 * 
	 * @param userId
	 * @return
	 */
	public static WxAccount getAccount() {
		return (WxAccount) CacheUtils.get(WX_ACCOUNT_CACHE, WX_ACCOUNT_CACHE_ID + UserUtils.getUser().getId());
	}
}
