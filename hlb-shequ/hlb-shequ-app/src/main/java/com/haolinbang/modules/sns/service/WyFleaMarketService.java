package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyFleaMarket;
import com.haolinbang.modules.sns.dao.WyFleaMarketDao;

/**
 * 跳蚤市场Service
 * 
 * @author nlp
 * @version 2017-07-07
 */
@Service
@Transactional(readOnly = true)
public class WyFleaMarketService extends CrudService<WyFleaMarketDao, WyFleaMarket> {

	@Autowired
	private WyFleaMarketDao wyFleaMarketDao;

	public WyFleaMarket get(String id) {
		return super.get(id);
	}

	public List<WyFleaMarket> findList(WyFleaMarket wyFleaMarket) {
		return super.findList(wyFleaMarket);
	}

	public Page<WyFleaMarket> findPage(Page<WyFleaMarket> page, WyFleaMarket wyFleaMarket) {
		return super.findPage(page, wyFleaMarket);
	}

	@Transactional(readOnly = false)
	public void save(WyFleaMarket wyFleaMarket) {
		super.save(wyFleaMarket);
	}

	@Transactional(readOnly = false)
	public void delete(WyFleaMarket wyFleaMarket) {
		super.delete(wyFleaMarket);
	}

	/**
	 * 跳蚤市场审核
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean audit(String id) {
		return wyFleaMarketDao.audit(id);
	}

}