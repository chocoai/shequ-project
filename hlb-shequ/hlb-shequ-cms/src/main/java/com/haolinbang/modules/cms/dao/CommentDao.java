package com.haolinbang.modules.cms.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.cms.entity.Comment;

/**
 * 评论DAO接口
 * 
 */
@MyBatisDao
public interface CommentDao extends CrudDao<Comment> {

}
