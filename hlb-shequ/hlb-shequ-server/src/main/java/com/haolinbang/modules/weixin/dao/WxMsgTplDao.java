package com.haolinbang.modules.weixin.dao;


import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.weixin.entity.WxMsgTpl;

/**
 * 具体消息模板DAO接口
 * 
 * @author nlp
 * @version 2017-09-19
 */
@MyBatisDao
public interface WxMsgTplDao extends CrudDao<WxMsgTpl> {

	String getMsgTplByAccountidAndTplCode(@Param("accountId") String accountId, @Param("tplCode") String tplCode);

	WxMsgTpl getWxMsgTpl(WxMsgTpl wxMsgTpl);

}
