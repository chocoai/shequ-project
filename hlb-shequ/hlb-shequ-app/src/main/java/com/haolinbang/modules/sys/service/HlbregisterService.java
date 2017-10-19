package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sys.entity.Hlbregister;
import com.haolinbang.modules.sys.dao.HlbregisterDao;

/**
 * 注册表Service
 * @author nlp
 * @version 2017-09-07
 */
@Service
@Transactional(readOnly = true)
public class HlbregisterService extends CrudService<HlbregisterDao, Hlbregister> {

	public Hlbregister get(String id) {
		return super.get(id);
	}
	
	public List<Hlbregister> findList(Hlbregister hlbregister) {
		return super.findList(hlbregister);
	}
	
	public Page<Hlbregister> findPage(Page<Hlbregister> page, Hlbregister hlbregister) {
		return super.findPage(page, hlbregister);
	}
	
	@Transactional(readOnly = false)
	public void save(Hlbregister hlbregister) {
		super.save(hlbregister);
	}
	
	@Transactional(readOnly = false)
	public void delete(Hlbregister hlbregister) {
		super.delete(hlbregister);
	}
	
}