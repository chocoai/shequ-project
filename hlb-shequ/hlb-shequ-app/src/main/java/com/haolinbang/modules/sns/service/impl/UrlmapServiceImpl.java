package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.modules.sns.dao.UrlmapDao;
import com.haolinbang.modules.sns.service.UrlmapService;

/**
 * 注册实现逻辑
 * 
 * @author Administrator
 * 
 */
@Service
public class UrlmapServiceImpl implements UrlmapService {

	private static Logger log = LoggerFactory.getLogger(UrlmapServiceImpl.class);

	@Autowired
	private UrlmapDao urlmapDao;

	@Override
	public Urlmap getUrlmap(Urlmap urlmap) {
		try {
			return urlmapDao.getUrlmap(urlmap);
		} catch (Exception e) {
			log.info("调用urlmapDao出错，错误信息：" + e);
			return null;
		}
	}

	@Override
	public List<Urlmap> getUrlmap1(Urlmap urlmap) {
		try {
			return urlmapDao.getUrlmap1(urlmap);
		} catch (Exception e) {
			log.info("调用urlmapDao出错，错误信息：" + e);
			return null;
		}
	}

	@Override
	public List<Urlmap> getUrlmapBySource(String source) {
		return urlmapDao.getUrlmapBySource(source);
	}

}
