package com.haolinbang.modules.weixin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.weixin.entity.WxMaterialImages;
import com.haolinbang.modules.weixin.dao.WxMaterialImagesDao;

/**
 * 图片素材对应关系表Service
 * 
 * @author nlp
 * @version 2017-07-14
 */
@Service
@Transactional(readOnly = true)
public class WxMaterialImagesService extends CrudService<WxMaterialImagesDao, WxMaterialImages> {

	@Autowired
	private WxMaterialImagesDao wxMaterialImagesDao;

	public WxMaterialImages get(String id) {
		return super.get(id);
	}

	public List<WxMaterialImages> findList(WxMaterialImages wxMaterialImages) {
		return super.findList(wxMaterialImages);
	}

	public Page<WxMaterialImages> findPage(Page<WxMaterialImages> page, WxMaterialImages wxMaterialImages) {
		return super.findPage(page, wxMaterialImages);
	}

	@Transactional(readOnly = false)
	public void save(WxMaterialImages wxMaterialImages) {
		super.save(wxMaterialImages);
	}

	@Transactional(readOnly = false)
	public void delete(WxMaterialImages wxMaterialImages) {
		super.delete(wxMaterialImages);
	}

	public String getWxImgUrlByLocalUrl(String localUrl) {
		return wxMaterialImagesDao.getWxImgUrlByLocalUrl(localUrl);
	}

}