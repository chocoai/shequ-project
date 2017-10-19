package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyNodeView;
import com.haolinbang.modules.sns.dao.WyNodeViewDao;

/**
 * 节点对应的页面显示Service
 * @author nlp
 * @version 2017-05-09
 */
@Service
@Transactional(readOnly = true)
public class WyNodeViewService extends CrudService<WyNodeViewDao, WyNodeView> {

	public WyNodeView get(String id) {
		return super.get(id);
	}
	
	public List<WyNodeView> findList(WyNodeView wyNodeView) {
		return super.findList(wyNodeView);
	}
	
	public Page<WyNodeView> findPage(Page<WyNodeView> page, WyNodeView wyNodeView) {
		return super.findPage(page, wyNodeView);
	}
	
	@Transactional(readOnly = false)
	public void save(WyNodeView wyNodeView) {
		super.save(wyNodeView);
	}
	
	@Transactional(readOnly = false)
	public void delete(WyNodeView wyNodeView) {
		super.delete(wyNodeView);
	}
	
}