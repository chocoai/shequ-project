package com.haolinbang.modules.weixin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxMsgTpl;

/**
 * 消息模板DAO接口
 * 
 * @author nlp
 * @version 2017-08-22
 */
@MyBatisDao
public interface WxMsgTplDao extends CrudDao<WxMsgTpl> {

	void saveList(@Param("list") List<WxMsgTpl> list);

}