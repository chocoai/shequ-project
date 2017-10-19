package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.service.CrudService;
import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.modules.sys.dao.DictDao;
import com.haolinbang.modules.sys.entity.Dict;
import com.haolinbang.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * 
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {

	@Autowired
	private DictDao dictDao;

	/**
	 * 查询字段类型列表
	 * 
	 * @return
	 */
	public List<Dict> findTypeList() {
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	public List<String> findDescriptionList() {
		return dao.findDescriptionList(new Dict());
	}

	/**
	 * 保存类型
	 * 
	 * @param dict
	 */
	public void typeSave(Dict dict) {
		dictDao.typeSave(dict);
	}

	@Transactional(readOnly = false)
	public List<Dict> getByType(Dict dict) {
		
		return dictDao.getByType(dict);
	}

	@Transactional(readOnly = false)
	public void updateType(Dict dict) {
		dictDao.updateType(dict);	
	}

	public List<Dict> findDictList(Dict dict) {
		return dictDao.findDictList(dict);
	}

}
