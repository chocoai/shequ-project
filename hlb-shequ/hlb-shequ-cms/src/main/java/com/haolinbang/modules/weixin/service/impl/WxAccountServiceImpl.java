package com.haolinbang.modules.weixin.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.constant.Constants;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.common.utils.IdGen;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.weixin.dao.WxAccountDao;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.inter.WxAccountService;
import com.haolinbang.modules.weixin.utils.AccountUtil;

/**
 * 微信管理Service
 * 
 * @author nlp
 * @version 2016-01-09
 */
@Service
@Transactional(readOnly = true)
public class WxAccountServiceImpl extends CrudService<WxAccountDao, WxAccount> implements WxAccountService {

	@Autowired
	private WxAccountDao wxAccountDao;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public WxAccount get(String id) {
		WxAccount wxAccount = super.get(id);
		return wxAccount;
	}

	public List<WxAccount> findList(WxAccount wxAccount) {
		wxAccount.setUser(UserUtils.getUser());
		return super.findList(wxAccount);
	}

	public Page<WxAccount> findPage(Page<WxAccount> page, WxAccount wxAccount) {
		wxAccount.setUser(UserUtils.getUser());
		return super.findPage(page, wxAccount);
	}

	@Transactional(readOnly = false)
	public void save(WxAccount wxAccount) {
		wxAccount.setUser(UserUtils.getUser());
		// 设置token
		if (StringUtils.isBlank(wxAccount.getToken())) {
			wxAccount.setToken(IdGen.uuid());
		}
		// 设置加密串
		if (StringUtils.isBlank(wxAccount.getEncodingAESKey())) {
			wxAccount.setEncodingAESKey(StringUtils.getRandomString(Constants.WEIXIN_ENCODING_AES_KEYS_LENGTH));
		}
		
		super.save(wxAccount);
	}

	@Transactional(readOnly = false)
	public void delete(WxAccount wxAccount) {
		super.delete(wxAccount);
	}

	/**
	 * 更新微信默认账号
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void setDefaultAccount(String id) {
		List<WxAccount> wxAccounts = findList(new WxAccount());
		log.debug("wxAccounts={}", wxAccounts);
		for (WxAccount account : wxAccounts) {
			if (id.equals(account.getId())) {
				// 设置默认账号的缓存
				AccountUtil.putAccount(account);
				account.setIsDefault(true);
			} else {
				account.setIsDefault(false);
			}
		}
		dao.setDefaultAccount(wxAccounts);
	}

	/**
	 * 通过token查找微信账号
	 * 
	 * @param token
	 * @return
	 */
	public WxAccount findWxAccountByToken(String token) {
		WxAccount wxAccount = dao.findWxAccountByToken(token);
		return wxAccount;
	}

	@Override
	public WxAccount getDefaultWxAccount() {
		return wxAccountDao.getDefaultWxAccount();
	}

}