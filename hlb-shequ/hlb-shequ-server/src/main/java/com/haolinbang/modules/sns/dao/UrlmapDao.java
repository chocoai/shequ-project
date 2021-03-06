package com.haolinbang.modules.sns.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;

@MyBatisDao
public interface UrlmapDao extends CrudDao<Urlmap> {

	public Urlmap getUrlmap(Urlmap urlmap);

	public List<Urlmap> getUrlmap1(Urlmap urlmap);

}
