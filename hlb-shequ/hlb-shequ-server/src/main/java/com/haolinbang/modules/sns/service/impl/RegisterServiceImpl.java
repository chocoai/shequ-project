package com.haolinbang.modules.sns.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.servlet.ValidateCodeServlet;
import com.haolinbang.common.thridwy.haolong.bean.SendSMS;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.AES;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.ServletUtil;
import com.haolinbang.modules.sns.dao.MemberDao;
import com.haolinbang.modules.sns.dto.UserRegDto;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.service.MemberService;
import com.haolinbang.modules.sns.service.RegisterService;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 注册实现逻辑
 * 
 * @author Administrator
 * 
 */
@Service
public class RegisterServiceImpl implements RegisterService {

	private static Logger log = LoggerFactory.getLogger(RegisterServiceImpl.class);

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private MemberService memberService;

	/**
	 * 验证码发送
	 */
	@Override
	public boolean sendsms(String mobile, String type) {
		String smscode = StringUtils.randomNumStr(6);
		ServletUtil.getSession().setAttribute("smscode", smscode);
		log.info("发送的验证码，smscode={}", smscode);

		String smsStr = "验证码:" + smscode;
		String source = (String) ServletUtil.getSession().getAttribute("app_source");
		Urlmap urlmap = UserUtils.getUrlmap(source);

		String secretkey = urlmap.getSecretkey();
		SendSMS sms = new SendSMS();
		sms.setSecretkey(secretkey);
		sms.setSoapActionString(urlmap.getSoapactionstring());
		sms.setUrlString(urlmap.getUrlstring());
		try {
			sms.setSource(AES.getAesPwd(source, secretkey));
			sms.setWYID(AES.getAesPwd("0", secretkey));
			sms.setMobile(AES.getAesPwd(mobile, secretkey));
		} catch (Exception e1) {
			log.error("aes加密出现异常:{}", e1);
		}
		sms.setSMSStr(smsStr);

		List<Map<String, String>> elist;
		try {
			elist = HaolongUtils.sendSMS(sms);
			log.info("发送短信验证码返回:{}", elist);
		} catch (IOException e) {
			log.error("发送短信异常:{}", e);
		}
		return true;
	}

	/**
	 * 验证手机验证码
	 */
	@Override
	public boolean checkSmsCode(String smsCode) {
		String smsCode2 = (String) ServletUtil.getSession().getAttribute("smscode");
		log.info("服务器验证码为={}，用户输入验证码={}", smsCode2, smsCode);

		if (smsCode2 != null && smsCode2.equalsIgnoreCase(smsCode)) {
			return true;
		}

		return false;
	}

	/**
	 * 验证图形验证码
	 */
	@Override
	public boolean checkPiccode(String piccode) {
		String piccode2 = (String) ServletUtil.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		log.info("服务器验证码为={}，用户输入验证码={}", piccode2, piccode);

		if (piccode2.equalsIgnoreCase(piccode)) {
			return true;
		}

		return false;
	}

	@Override
	public Member saveMember(UserRegDto user) {
		// 将用户的信息保存到数据库
		Member member = new Member();
		member.setMobile(user.getMobile());
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		member = memberService.findList(member).size() > 0 ? memberService.findList(member).get(0) : null;
		// 如果数据库中存在该手机对应的用户信息，则修改openid即可；
		if (member != null) {
			/*if(user.getAdmintype()==1){
				member.setAdmintype(user.getAdmintype());
				member.setYgID(user.getYgID());
			}*/
			member.setOpenid(String.valueOf(ServletUtil.getSession().getAttribute("openid")));
			member.setAppid(String.valueOf(ServletUtil.getSession().getAttribute("appid")));
			memberDao.update(member);
			/*
			 * Room room = new Room(); room.setMemberId(member.getMemberId());
			 * roomDao.delete(room);
			 */
			
			return member;
		}/*
		 * else{ member = new Member();
		 * member.setOpenid(String.valueOf(ServletUtil
		 * .getSession().getAttribute("openid"))); member =
		 * memberService.findList
		 * (member).size()>0?memberService.findList(member).get(0):null;
		 * if(member!=null){
		 * 
		 * }
		 * 
		 * }
		 */
		member = new Member();
		member.setMobile(user.getMobile());
		member.setMemberType('0');// 游客
		member.setMemberName(user.getMemberName());
		member.setNickname(user.getMemberName());
		member.setStatus("1");
		member.setAdmintype(user.getAdmintype());
		/*if(member.getAdmintype()==1){
			member.setYgID(user.getYgID());
		}*/

		if (user.getIsAssociate().equals("true")) {// 关联会员，需要写入主会员id和修改会员身份
			member.setMemberType('2');
			Member mainMember = new Member();
			mainMember.setMobile(user.getMainMemberPhone());
			mainMember = memberDao.findAllList(mainMember).get(0);
			member.setRoomId(mainMember.getRoomId());
			member.setParentMemberId(mainMember.getMemberId());
		}

		member.setCreatetime(new Date());
		member.setOpenid(String.valueOf(ServletUtil.getSession().getAttribute("openid")));
		member.setAppid(String.valueOf(ServletUtil.getSession().getAttribute("appid")));
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		memberDao.insert(member);

		return member;
	}
}
