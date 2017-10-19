package com.haolinbang.modules.weixin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.dao.WxNewsDao;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.entity.WxNews;

/**
 * 微信图文Service
 * 
 * @author nlp
 * @version 2016-02-13
 */
@Service
@Transactional(readOnly = true)
public class WxNewsService extends CrudService<WxNewsDao, WxNews> {

	public WxNews get(String id) {
		return super.get(id);
	}

	public List<WxNews> findList(WxNews wxNews) {
		return super.findList(wxNews);
	}

	public Page<WxNews> findPage(Page<WxNews> page, WxNews wxNews) {
		return super.findPage(page, wxNews);
	}

	@Transactional(readOnly = false)
	public void save(WxNews wxNews) {
		// 对html进行转码转义
		if (StringUtils.isNotBlank(wxNews.getContent())) {
			wxNews.setContent(StringEscapeUtils.unescapeHtml4(wxNews.getContent()));
		}
		super.save(wxNews);
		// 更新url地址
		wxNews.setUrl(wxNews.getUrl() + wxNews.getId() + ".html");
		super.save(wxNews);
	}

	@Transactional(readOnly = false)
	public void delete(WxNews wxNews) {
		super.delete(wxNews);
	}

	/**
	 * 模糊查找内容
	 * 
	 * @param keyword
	 *            关键字
	 * @param wxAccount
	 * @return
	 */
	public WxNews query(String keyword, WxAccount wxAccount) {
		WxNews wxNews = new WxNews();
		wxNews.setAccount(wxAccount);
		wxNews.setKeyword(keyword);
		List<WxNews> newsList = findList(wxNews);
		if (newsList != null && newsList.size() > 0) {
			return newsList.get(0);
		}
		return null;
	}

	/**
	 * 多图文查询
	 * 
	 * @param keyword
	 * @param wxAccount
	 * @return
	 */
	public List<WxNews> queryMutilNews(String news, WxAccount wxAccount) {
		WxNews wxNews = new WxNews();
		wxNews.setAccount(wxAccount);
		wxNews.setKeyword(news);
		return dao.queryMutilNews(wxNews);
	}
}