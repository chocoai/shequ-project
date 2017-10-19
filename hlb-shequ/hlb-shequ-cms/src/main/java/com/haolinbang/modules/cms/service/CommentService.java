package com.haolinbang.modules.cms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.cms.dao.CommentDao;
import com.haolinbang.modules.cms.entity.Comment;

/**
 * 评论Service
 * 
 */
@Service
@Transactional(readOnly = true)
public class CommentService extends CrudService<CommentDao, Comment> {

	public Page<Comment> findPage(Page<Comment> page, Comment comment) {
		// DetachedCriteria dc = commentDao.createDetachedCriteria();
		// if (StringUtils.isNotBlank(comment.getContentId())){
		// dc.add(Restrictions.eq("contentId", comment.getContentId()));
		// }
		// if (StringUtils.isNotEmpty(comment.getTitle())){
		// dc.add(Restrictions.like("title", "%"+comment.getTitle()+"%"));
		// }
		// dc.add(Restrictions.eq(Comment.FIELD_DEL_FLAG,
		// comment.getDelFlag()));
		// dc.addOrder(Order.desc("id"));
		// return commentDao.find(page, dc);
		comment.getSqlMap().put("dsf", dataScopeFilter(comment.getCurrentUser(), "o", "u"));

		return super.findPage(page, comment);
	}

	public void delete(Comment entity, Boolean isRe) {
		super.delete(entity);
	}
}
