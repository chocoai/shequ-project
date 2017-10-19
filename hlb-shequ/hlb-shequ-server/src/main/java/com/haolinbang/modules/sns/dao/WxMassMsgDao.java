package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WxMassMsg;

/**
 * 楼盘表DAO接口
 * @author wxc
 * @version 2017-07-15
 */
@MyBatisDao
public interface WxMassMsgDao extends CrudDao<WxMassMsg> {
	List<WxMassMsg> getWxMassMsgList(@Param("source")String source, @Param("groupId")String groupId, @Param("wyId")String wyId, @Param("lyId")String lyId,
			@Param("memberId")String memberId);
	
	List<WxMassMsg> getWxMassMsgList2(@Param("source")String source, @Param("groupId")String groupId,
			@Param("memberId")String memberId);
}