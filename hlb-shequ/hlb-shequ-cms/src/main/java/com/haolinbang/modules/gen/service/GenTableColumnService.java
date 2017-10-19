package com.haolinbang.modules.gen.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.gen.dao.GenTableColumnDao;
import com.haolinbang.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段Service
 * 
 * @author nlp
 * @version 2016-01-31
 */
@Service
@Transactional(readOnly = true)
public class GenTableColumnService extends CrudService<GenTableColumnDao, GenTableColumn> {

	public GenTableColumn get(String id) {
		return super.get(id);
	}

	public List<GenTableColumn> findList(GenTableColumn genTableColumn) {
		return super.findList(genTableColumn);
	}

	public Page<GenTableColumn> findPage(Page<GenTableColumn> page, GenTableColumn genTableColumn) {
		return super.findPage(page, genTableColumn);
	}

	@Transactional(readOnly = false)
	public void save(GenTableColumn genTableColumn) {
		super.save(genTableColumn);
	}

	@Transactional(readOnly = false)
	public void delete(GenTableColumn genTableColumn) {
		super.delete(genTableColumn);
	}

}