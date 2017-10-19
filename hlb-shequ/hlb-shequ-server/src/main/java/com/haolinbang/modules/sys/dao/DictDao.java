package com.haolinbang.modules.sys.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.DataSource;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * 
 */
@MyBatisDao
@DataSource("ds1")
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);

}
