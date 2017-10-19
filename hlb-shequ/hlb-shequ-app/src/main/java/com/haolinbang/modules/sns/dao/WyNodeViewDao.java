package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyNodeView;

/**
 * 节点对应的页面显示DAO接口
 * @author nlp
 * @version 2017-05-09
 */
@MyBatisDao
public interface WyNodeViewDao extends CrudDao<WyNodeView> {
	
}