package com.haolinbang.modules.weixin.service.inter;

import java.util.List;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.modules.weixin.entity.WxAccount;

public interface WxAccountService {
	public WxAccount get(String id);

	public List<WxAccount> findList(WxAccount wxAccount);

	public Page<WxAccount> findPage(Page<WxAccount> page, WxAccount wxAccount);

	public void save(WxAccount wxAccount);

	public void delete(WxAccount wxAccount);

	public void setDefaultAccount(String id);

	public WxAccount findWxAccountByToken(String token);

	public WxAccount getDefaultWxAccount();

}
