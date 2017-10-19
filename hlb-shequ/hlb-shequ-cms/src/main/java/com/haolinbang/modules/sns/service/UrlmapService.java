package com.haolinbang.modules.sns.service;

import java.util.List;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;

/**
 * 
 * 
 * @author Administrator
 * 
 */
public interface UrlmapService {

	Urlmap getUrlmap(Urlmap urlmap);

	List<Urlmap> getUrlmap1(Urlmap urlmap);

	List<Urlmap> getUrlmapBySource(String source);

	Page<Urlmap> findPage(Page<Urlmap> page, Urlmap urlmap);

	List<Urlmap> findList(Urlmap urlmap);
}
