package com.haolinbang.modules.weixin.dao;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxMaterialImages;

/**
 * 图片素材对应关系表DAO接口
 * 
 * @author nlp
 * @version 2017-07-14
 */
@MyBatisDao
public interface WxMaterialImagesDao extends CrudDao<WxMaterialImages> {

	String getWxImgUrlByLocalUrl(@Param("localUrl") String localUrl);

}