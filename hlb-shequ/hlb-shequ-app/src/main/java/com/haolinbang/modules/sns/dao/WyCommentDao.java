package com.haolinbang.modules.sns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyComment;

/**
 * 评论和回复表DAO接口
 * 
 * @author nlp
 * @version 2017-07-18
 */
@MyBatisDao
public interface WyCommentDao extends CrudDao<WyComment> {

	boolean audit(@Param("id") String id);
	
	boolean audit1(@Param("id") String id);

	List<WyComment> getDetailByCommentId(WyComment wyComment);

}