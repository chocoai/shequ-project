package com.haolinbang.modules.weixin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.dao.WxNewsMultiDao;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.entity.WxNews;
import com.haolinbang.modules.weixin.entity.WxNewsMulti;

/**
 * 微信多图文Service
 * 
 * @author nlp
 * @version 2016-02-13
 */
@Service
@Transactional(readOnly = true)
public class WxNewsMultiService extends CrudService<WxNewsMultiDao, WxNewsMulti> {

	@Autowired
	private WxNewsService wxNewsService;

	public WxNewsMulti get(String id) {
		return super.get(id);
	}

	public List<WxNewsMulti> findList(WxNewsMulti wxNewsMulti) {
		return super.findList(wxNewsMulti);
	}

	public Page<WxNewsMulti> findPage(Page<WxNewsMulti> page, WxNewsMulti wxNewsMulti) {
		return super.findPage(page, wxNewsMulti);
	}

	@Transactional(readOnly = false)
	public void save(WxNewsMulti wxNewsMulti) {
		super.save(wxNewsMulti);
	}

	@Transactional(readOnly = false)
	public void delete(WxNewsMulti wxNewsMulti) {
		super.delete(wxNewsMulti);
	}

	/**
	 * 模糊查找，关键字内容
	 * @param keyword
	 * @param wxAccount
	 * @return
	 */
	public List<WxNews> query(String keyword, WxAccount wxAccount) {
		// 查询记录
		WxNewsMulti wxNewsMulti = new WxNewsMulti();
		wxNewsMulti.setAccount(wxAccount);
		wxNewsMulti.setKeywords(keyword);
		List<WxNewsMulti> wxNewsMultis = findList(wxNewsMulti);
		if (wxNewsMultis != null && wxNewsMultis.size() > 0) {
			WxNewsMulti news = wxNewsMultis.get(0);
			return wxNewsService.queryMutilNews(news.getNews(), wxAccount);
		}
		// 2.将多篇图文合并起来
		return null;
	}
}