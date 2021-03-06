package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.WyComment;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sns.dao.WyCommentDao;

/**
 * 评论和回复表Service
 * 
 * @author nlp
 * @version 2017-07-18
 */
@Service
@Transactional(readOnly = true)
public class WyCommentService extends CrudService<WyCommentDao, WyComment> {

	@Autowired
	private WyCommentDao wyCommentDao;
	
	public WyComment get(String id) {
		return super.get(id);
	}

	public List<WyComment> findList(WyComment wyComment) {
		return super.findList(wyComment);
	}

	public Page<WyComment> findPage(Page<WyComment> page, WyComment wyComment) {
		return super.findPage(page, wyComment);
	}

	@Transactional(readOnly = false)
	public void save(WyComment wyComment) {
		super.save(wyComment);
	}

	@Transactional(readOnly = false)
	public void delete(WyComment wyComment) {
		super.delete(wyComment);
	}

	/**
	 * 发表评论
	 * 
	 * @param wyComment
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean publish(WyComment wyComment) {
		Member member = MemberUtils.getCurrentMember();
		// 设置发布人员
		wyComment.setPublisher(member.getMemberId());
		
		
		
		
		this.save(wyComment);
		return true;
	}


	public List<WyComment> getByTypeAndId(String bizType, String relationId) {
		return wyCommentDao.getByTypeAndId(bizType, relationId);
	}
	

	public List<WyComment> getByCommentId(String commentId) {
		return wyCommentDao.getByCommentId(commentId);
	}

	public List<WyComment> getAllByTypeAndId(String bizType, String relationId) {
		return wyCommentDao.getAllByTypeAndId(bizType, relationId);
	}
	
}