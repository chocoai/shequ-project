package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyMember;
import com.haolinbang.modules.sns.dao.WyMemberDao;

/**
 * 会员信息Service
 * 
 * @author nlp
 * @version 2017-06-02
 */
@Service
@Transactional(readOnly = true)
public class WyMemberService extends CrudService<WyMemberDao, WyMember> {
	@Autowired
	private WyMemberDao wyMemberDao;

	public WyMember get(String id) {
		return super.get(id);
	}

	public List<WyMember> findList(WyMember wyMember) {
		return super.findList(wyMember);
	}

	public Page<WyMember> findPage(Page<WyMember> page, WyMember wyMember) {
		return super.findPage(page, wyMember);
	}

	@Transactional(readOnly = false)
	public void save(WyMember wyMember) {
		super.save(wyMember);
	}

	@Transactional(readOnly = false)
	public void delete(WyMember wyMember) {
		super.delete(wyMember);
	}

	public List<WyMember> getMemberListByWyids(String source, String wyIds) {
		return wyMemberDao.getMemberListByWyids(source, wyIds);
	}

	public List<WyMember> getMemberListByMids(String memberIds) {
		return wyMemberDao.getMemberListByMids(memberIds);
	}

	public List<WyMember> getMemberListByWyidsName(String source, String wyIds, String memberName) {
		return wyMemberDao.getMemberListByWyidsName(source, wyIds, memberName);
	}

	public List<WyMember> getMemberListByLyid(String source, String lyid, String memberName) {
		return wyMemberDao.getMemberListByLyid(source, lyid, memberName);
	}
}