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

	public List<Dict> findTypeList(Dict dict);

	public void typeSave(Dict dict);

	public List<String> findDescriptionList(Dict dict);

	public List<Dict> getByType(Dict dict);

	public void updateType(Dict dict);

	public List<Dict> findDictList(Dict dict);
}
