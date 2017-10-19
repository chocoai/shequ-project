package com.haolinbang.modules.sns.service;

import com.haolinbang.modules.sns.dto.UserRegDto;
import com.haolinbang.modules.sns.entity.Member;

/**
 * 注册实现类
 * 
 * @author Administrator
 * 
 */
public interface RegisterService {

	boolean sendsms(String mobile, String type);

	boolean checkSmsCode(String smsCode);

	boolean checkPiccode(String piccode);
	
	Member saveMember(UserRegDto user);

}
