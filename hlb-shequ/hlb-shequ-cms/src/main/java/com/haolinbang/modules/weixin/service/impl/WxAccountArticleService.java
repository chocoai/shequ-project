package com.haolinbang.modules.weixin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.dao.WxAccountArticleDao;
import com.haolinbang.modules.weixin.entity.WxAccountArticle;

/**
 * 账号和文章对应Service
 * @author nlp
 * @version 2016-02-07
 */
@Service
@Transactional(readOnly = true)
public class WxAccountArticleService extends CrudService<WxAccountArticleDao, WxAccountArticle> {

	public WxAccountArticle get(String id) {
		return super.get(id);
	}
	
	public List<WxAccountArticle> findList(WxAccountArticle wxAccountArticle) {
		return super.findList(wxAccountArticle);
	}
	
	public Page<WxAccountArticle> findPage(Page<WxAccountArticle> page, WxAccountArticle wxAccountArticle) {
		return super.findPage(page, wxAccountArticle);
	}
	
	@Transactional(readOnly = false)
	public void save(WxAccountArticle wxAccountArticle) {
		super.save(wxAccountArticle);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxAccountArticle wxAccountArticle) {
		super.delete(wxAccountArticle);
	}
	
}