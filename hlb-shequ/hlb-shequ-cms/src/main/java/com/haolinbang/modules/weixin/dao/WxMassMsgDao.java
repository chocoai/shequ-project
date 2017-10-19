package com.haolinbang.modules.weixin.dao;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxMassMsg;

/**
 * 群发消息记录表DAO接口
 * 
 * @author nlp
 * @version 2017-07-11
 */
@MyBatisDao
public interface WxMassMsgDao extends CrudDao<WxMassMsg> {

	WxMassMsg getWxMassMsgByMsgid(@Param("msgId") Long msgId);

}