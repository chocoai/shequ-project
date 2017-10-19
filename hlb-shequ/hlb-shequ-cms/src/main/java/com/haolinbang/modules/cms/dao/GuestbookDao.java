package com.haolinbang.modules.cms.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.cms.entity.Guestbook;

/**
 * 留言DAO接口
 * 
 */
@MyBatisDao
public interface GuestbookDao extends CrudDao<Guestbook> {

}
