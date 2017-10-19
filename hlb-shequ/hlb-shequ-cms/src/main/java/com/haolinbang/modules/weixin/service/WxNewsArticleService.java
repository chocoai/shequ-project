package com.haolinbang.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.entity.WxNewsArticle;
import com.haolinbang.modules.weixin.dao.WxNewsArticleDao;

/**
 * 微信图文消息Service
 * @author nlp
 * @version 2017-07-11
 */
@Service
@Transactional(readOnly = true)
public class WxNewsArticleService extends CrudService<WxNewsArticleDao, WxNewsArticle> {

	public WxNewsArticle get(String id) {
		return super.get(id);
	}
	
	public List<WxNewsArticle> findList(WxNewsArticle wxNewsArticle) {
		return super.findList(wxNewsArticle);
	}
	
	public Page<WxNewsArticle> findPage(Page<WxNewsArticle> page, WxNewsArticle wxNewsArticle) {
		return super.findPage(page, wxNewsArticle);
	}
	
	@Transactional(readOnly = false)
	public void save(WxNewsArticle wxNewsArticle) {
		super.save(wxNewsArticle);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxNewsArticle wxNewsArticle) {
		super.delete(wxNewsArticle);
	}
	
}