package com.haolinbang.modules.sns.dao;

import java.util.List;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.Member;

/**
 * 字典DAO接口
 * 
 */
@MyBatisDao
public interface MemberDao extends CrudDao<Member> {

	Member getMemberByOpenid(String openid);

	String getMemberByYgid(Integer ygid);

	List<Member> getMemberBygroupId(Integer groupId);

}
