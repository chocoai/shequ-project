package com.haolinbang.modules.sns.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.haolinbang.common.config.Global;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.common.utils.Exceptions;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.utils.WeixinUtil;
import com.haolinbang.common.web.ServletUtil;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.dao.MemberDao;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.service.WeixinService;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.WxAccountService;

@Service
@Scope("prototype")
public class WeixinServiceImpl extends WxMpServiceImpl implements WeixinService {

	private final Logger log = LoggerFactory.getLogger(WeixinServiceImpl.class);

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private WxAccountService wxAccountService;

	// 微信配置信息
	private WxMpInMemoryConfigStorage config;

	public WxAccount getAccount(String appid) {
		WxAccount wxAccount = null;
		String appsecret = null;
		// 如果appid为空,使用默认账号,否则从数据库中查询出账号
		if (StringUtils.isBlank(appid)) {
			String source = (String) ServletUtil.getSession().getAttribute("app_source");
			String groupid = (String) ServletUtil.getSession().getAttribute("app_groupid");
			if (StringUtils.isNotBlank(source) && StringUtils.isNotBlank(groupid)) {
				wxAccount = new WxAccount();
				wxAccount.setGroupId(StringUtils.toInteger(groupid));
				wxAccount.setSource(source);
				List<WxAccount> wxAccounts = wxAccountService.findList(wxAccount);
				if (wxAccounts.size() > 0) {
					wxAccount = wxAccounts.get(0);
					appid = wxAccounts.get(0).getAppid();
					appsecret = wxAccounts.get(0).getAppsecret();
				} else {
					appid = Global.getConfig("weixin.appid");
					appsecret = Global.getConfig("weixin.secret");
				}
			} else {
				appid = Global.getConfig("weixin.appid");
				appsecret = Global.getConfig("weixin.secret");
			}
		} else {
			appid = (String) ServletUtil.getSession().getAttribute("appid");
			appsecret = (String) ServletUtil.getSession().getAttribute("appsecret");

		}
		if (wxAccount == null) {
			wxAccount = new WxAccount();
		}
		wxAccount.setAppid(appid);
		wxAccount.setAppsecret(appsecret);
		return wxAccount;
	}

	/**
	 * 二维码支付配置初始化信息
	 * 
	 * @param appid2
	 * 
	 * @param state
	 * 
	 * @throws Exception
	 */
	private void init(String appid) throws RuntimeException {
		String appsecret = null;
		if (StringUtils.isBlank(appid)) {
			String source = (String) ServletUtil.getSession().getAttribute("app_source");
			String groupid = ServletUtil.getSession().getAttribute("app_groupid") + "";
			if(StringUtils.isNotBlank(source) && StringUtils.isNotBlank(groupid)){
				WxAccount wxAccount = new WxAccount();
				wxAccount.setGroupId(StringUtils.toInteger(groupid));
				wxAccount.setSource(source);
				List<WxAccount> wxAccounts = wxAccountService.findList(wxAccount);
				if(wxAccounts.size() > 0){
					appid = wxAccounts.get(0).getAppid();
					appsecret = wxAccounts.get(0).getAppsecret();
				}else{
					appid = Global.getConfig("weixin.appid");
					appsecret = Global.getConfig("weixin.secret");
				}
			}else{
				appid = Global.getConfig("weixin.appid");
				appsecret = Global.getConfig("weixin.secret");
			}
		} else {
			WxAccount account = wxAccountService.getWxAccountByAppid(appid).get(0);
			/*String wxAccountId = (String) ServletUtil.getSession().getAttribute("wxAccountId");
			WxAccount account = wxAccountService.get(wxAccountId);*/
			appid = account.getAppid();
			appsecret = account.getAppsecret();
		}
		
		config = new WxMpInMemoryConfigStorage();
		// 获取微信支付账号
		if (StringUtils.isNotBlank(appid)) {
			config.setAppId(appid.trim());// 微信号
		} else {
			throw new RuntimeException("获取微信号出错，数据库没有配置Platp_key字段");
		}

		// 设置微信秘钥
		if (StringUtils.isNotBlank(appsecret)) {
			config.setSecret(appsecret.trim());
		} else {
			throw new RuntimeException("获取微信秘钥出错，数据库没有配置AppSecret字段");
		}

		log.info("config={}", JSON.toJSONString(config));

		config.setOauth2redirectUri(ServletUtil.getOuterUrl() + "/weixin/authCallback");
		setWxMpConfigStorage(config);
	}

	@Override
	public String oAuth2(String sessionid) {
		init(null);
		String source = (String) ServletUtil.getSession().getAttribute("app_source");
		String groupid = ServletUtil.getSession().getAttribute("app_groupid") + "";
		String dirUrl = (String) ServletUtil.getSession().getAttribute("dirType");
		CacheUtils.put(sessionid, WyConstants.CACHE_KEY_APPID, config.getAppId());
		CacheUtils.put(sessionid, WyConstants.CACHE_KEY_SOURCE, source);
		CacheUtils.put(sessionid, WyConstants.CACHE_KEY_GROUPID, groupid);
		CacheUtils.put(sessionid, WyConstants.CACHE_KEY_APPSECRET, config.getSecret());
		CacheUtils.put(sessionid, "dirUrl", dirUrl);
		return oauth2buildAuthorizationUrl(WxConsts.OAUTH2_SCOPE_BASE, sessionid);
	}

	// 处理用户的请求信息
	@Override
	public String authCallback(HttpServletRequest request) throws Exception {
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String appid = CacheUtils.get(state, WyConstants.CACHE_KEY_APPID);
		String appsecret = CacheUtils.get(state, WyConstants.CACHE_KEY_APPSECRET);
		String source = CacheUtils.get(state, WyConstants.CACHE_KEY_SOURCE);
		String groupid = CacheUtils.get(state, WyConstants.CACHE_KEY_GROUPID);
		String dirUrl = CacheUtils.get(state, "dirUrl");
		
		request.getSession().setAttribute("app_source", source);
		request.getSession().setAttribute("app_groupid", groupid);
		request.getSession().setAttribute("appid", appid);
		request.getSession().setAttribute("appsecret", appsecret);
		if(StringUtils.isNotBlank(dirUrl)){
			request.getSession().setAttribute("dirUrl", dirUrl);
		}
		String openid = null;
		init(appid);

		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
		if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(state)) {
			// 获取票据信息
			wxMpOAuth2AccessToken = oauth2getAccessToken(code);
			log.debug("wxMpOAuth2AccessToken={}", wxMpOAuth2AccessToken);

			Member member = null;
			if (wxMpOAuth2AccessToken != null) {
				openid = wxMpOAuth2AccessToken.getOpenId();

				request.getSession().setAttribute("openid", openid);

				request.getSession().setAttribute("appid", appid);
				// 查看用户是否保存
				DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
				member = memberDao.getMemberByOpenid(openid);
				if (member != null) {
					member.setAppid(appid);
					memberDao.update(member);
					// 将会员信息缓存起来
					Integer memberId = member.getMemberId();
					if (CacheUtils.get(memberId.toString(), "member") != null) {
						CacheUtils.remove(memberId.toString(), "member");
					}
					CacheUtils.put(memberId.toString(), "member", member);
					request.getSession().setAttribute("memberid", memberId);
					log.info("memberid={}", memberId);
					request.getSession().setAttribute("isexist", "true");
				} else {
					if(StringUtils.isBlank(dirUrl)){
						Exception e = Exceptions.memberNeedReg(new Exception("您还不是会员,您需要先注册"));
						throw e;
					}else{
						request.getSession().setAttribute("isexist", "false");
					}
				}
			}
		}
		return openid;
	}

	@Override
	public WxJsapiSignature getWxJsapiSignature(String curUrl) throws WxErrorException {
		init(null);
		String jsapiTicket = getJsapiTicket();

		String url = ServletUtil.getRequestUrlAndParas();
		if (curUrl != null) {
			url = curUrl;
		}
		log.info("jsapiTicket={};当前连接请求地址:url={}", jsapiTicket, url);

		WxJsapiSignature wxJsapiSignature = WeixinUtil.sign(jsapiTicket, url, config.getAppId());
		log.info("获取js验证签名wxJsapiSignature={}", JSON.toJSONString(wxJsapiSignature));

		return wxJsapiSignature;
	}

	// 获取多媒体下载图片路径
	@Override
	public String dealPhoto(String media_id) throws WxErrorException {
		init(null);
		String mediaId = media_id;
		String accessToken = getAccessToken();
		// 下载地址
		String getMediaUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id=" + mediaId;

		return getMediaUrl;
	}

	@Override
	public String tplMsgSend(WxMpTemplateMessage templateMessage) throws WxErrorException {
		init(null);
		return templateSend(templateMessage);
	}

}
