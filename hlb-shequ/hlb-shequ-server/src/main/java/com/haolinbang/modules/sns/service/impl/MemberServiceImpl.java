package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.modules.sns.dao.MemberDao;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.service.MemberService;

/**
 * 注册实现逻辑
 * 
 * @author Administrator
 * 
 */
@Service
public class MemberServiceImpl implements MemberService {

	private static Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Autowired
	private MemberDao memberDao;

	@Override
	public Member getMember(Integer memberID) {

		return memberDao.get("" + memberID);
	}

	@Override
	public List<Member> findList(Member member) {

		return memberDao.findList(member);
	}

	@Override
	public void updateMember(Member member) {

		memberDao.update(member);
	}

	@Override
	public String getMemberByYgid(Integer ygid) {
		return memberDao.getMemberByYgid(ygid);
	}

	@Override
	public List<Member> getMemberBygroupId(Integer groupId) {
		// TODO Auto-generated method stub
		return memberDao.getMemberBygroupId(groupId);
	}

}
