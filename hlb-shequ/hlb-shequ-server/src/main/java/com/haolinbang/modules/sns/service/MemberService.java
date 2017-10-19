package com.haolinbang.modules.sns.service;

import java.util.List;

import com.haolinbang.modules.sns.entity.Member;

/**
 * 
 * 
 * @author Administrator
 * 
 */
public interface MemberService {

	Member getMember(Integer memberID);

	List<Member> findList(Member member);
	
	void updateMember(Member member);

	String getMemberByYgid(Integer ygid);

	List<Member> getMemberBygroupId(Integer valueOf);
}
