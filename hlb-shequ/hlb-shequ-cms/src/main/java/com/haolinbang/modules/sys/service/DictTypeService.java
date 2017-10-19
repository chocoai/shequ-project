package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sys.dao.DictTypeDao;
import com.haolinbang.modules.sys.entity.Dict;
import com.haolinbang.modules.sys.entity.DictType;

/**
 * 字典分类Service
 * 
 * @author nlp
 * @version 2017-08-15
 */
@Service
@Transactional(readOnly = true)
public class DictTypeService extends CrudService<DictTypeDao, DictType> {

	@Autowired
	private DictService dictService;

	public DictType get(String id) {
		return super.get(id);
	}

	public List<DictType> findList(DictType dictType) {
		return super.findList(dictType);
	}

	public Page<DictType> findPage(Page<DictType> page, DictType dictType) {
		return super.findPage(page, dictType);
	}

	@Transactional(readOnly = false)
	public void save(DictType dictType) {
		// 更新明细表中的对应记录
		Dict dict = new Dict();
		dict.setType(dictType.getValueOld());
		List<Dict> dictList = dictService.findDictList(dict);
		for (Dict dict2 : dictList) {
			dict2.setType(dictType.getValue());
			dictService.save(dict2);
		}
		super.save(dictType);
	}

	@Transactional(readOnly = false)
	public void delete(DictType dictType) {
		super.delete(dictType);
	}

}