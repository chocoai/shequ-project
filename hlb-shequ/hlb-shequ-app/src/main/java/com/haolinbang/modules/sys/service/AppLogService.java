package com.haolinbang.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sys.entity.AppLog;
import com.haolinbang.modules.sys.dao.AppLogDao;

/**
 * 日志表Service
 * @author nlp
 * @version 2017-08-02
 */
@Service
@Transactional(readOnly = true)
public class AppLogService extends CrudService<AppLogDao, AppLog> {

	public AppLog get(String id) {
		return super.get(id);
	}
	
	public List<AppLog> findList(AppLog appLog) {
		return super.findList(appLog);
	}
	
	public Page<AppLog> findPage(Page<AppLog> page, AppLog appLog) {
		return super.findPage(page, appLog);
	}
	
	@Transactional(readOnly = false)
	public void save(AppLog appLog) {
		super.save(appLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(AppLog appLog) {
		super.delete(appLog);
	}
	
}