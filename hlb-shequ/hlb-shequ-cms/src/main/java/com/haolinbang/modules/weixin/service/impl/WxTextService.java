package com.haolinbang.modules.weixin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.dao.WxTextDao;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.entity.WxText;
import com.haolinbang.modules.weixin.utils.AccountUtil;

/**
 * 微信文本Service
 * 
 * @author nlp
 * @version 2016-02-13
 */
@Service
@Transactional(readOnly = true)
public class WxTextService extends CrudService<WxTextDao, WxText> {

	public WxText get(String id) {
		return super.get(id);
	}

	public List<WxText> findList(WxText wxText) {
		return super.findList(wxText);
	}

	public Page<WxText> findPage(Page<WxText> page, WxText wxText) {
		return super.findPage(page, wxText);
	}

	@Transactional(readOnly = false)
	public void save(WxText wxText) {
		wxText.setAccount(AccountUtil.getAccount());
		super.save(wxText);
	}

	@Transactional(readOnly = false)
	public void delete(WxText wxText) {
		super.delete(wxText);
	}

	/**
	 * 模糊查找内容
	 * 
	 * @param keyword
	 * @param wxAccount
	 * @return
	 */
	public String query(String keyword, WxAccount wxAccount) {
		WxText wxText = new WxText();
		wxText.setAccount(wxAccount);
		wxText.setKeyword(keyword);
		List<WxText> wxTexts = findList(wxText);
		if (wxTexts != null && wxTexts.size() > 0) {
			return wxTexts.get(0).getContent();
		}
		return null;
	}
}