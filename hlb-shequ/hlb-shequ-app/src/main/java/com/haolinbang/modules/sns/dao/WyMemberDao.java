package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyMember;

/**
 * 会员信息DAO接口
 * 
 * @author nlp
 * @version 2017-06-02
 */
@MyBatisDao
public interface WyMemberDao extends CrudDao<WyMember> {

	List<WyMember> getMemberListByWyids(@Param("source") String source, @Param("wyIds") String wyIds);

	List<WyMember> getMemberListByMids(@Param("memberIds") String memberIds);

	List<WyMember> getMemberListByWyidsName(@Param("source") String source, @Param("wyIds") String wyIds, @Param("memberName") String memberName);

	List<WyMember> getMemberListByLyid(@Param("source") String source, @Param("lyid") String lyid, @Param("memberName") String memberName);

}