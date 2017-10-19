package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyMember;

/**
 * 会员信息DAO接口
 * @author nlp
 * @version 2017-06-02
 */
@MyBatisDao
public interface WyMemberDao extends CrudDao<WyMember> {
	
}