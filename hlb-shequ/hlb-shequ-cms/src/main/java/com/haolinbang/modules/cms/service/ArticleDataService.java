package com.haolinbang.modules.cms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.cms.dao.ArticleDataDao;
import com.haolinbang.modules.cms.entity.ArticleData;

/**
 * 站点Service
 * 
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}
