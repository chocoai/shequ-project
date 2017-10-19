package com.haolinbang.modules.weixin.dao;

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

	WxMsgTpl getWxMsgTplByCommonTplIdAndAccountId(@Param("tplId") Integer tplId, @Param("accountId") String accountId);

}