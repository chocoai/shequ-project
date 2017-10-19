package com.haolinbang.modules.sns.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.common.utils.Exceptions;
import com.haolinbang.common.utils.HttpUtils;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.service.WeixinService;

@Controller
@RequestMapping("/weixin")
public class WeixinController extends BaseController {

	@Resource
	private WeixinService weixinService;

	/**
	 * 发起授权url配置
	 * 
	 * @return
	 */
	@RequestMapping("/oAuth2")
	public String oAuth2(String sessionid, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(sessionid)) {
				sessionid = request.getSession().getId();
			}
			String url = weixinService.oAuth2(sessionid);
			logger.info("oAuth|发起授权url,获取openid;url={}", url);
			return "redirect:" + url;
		} catch (Exception e) {
			logger.error("{}", e);
			return null;
		}
	}

	/**
	 * 授权后回调
	 * 
	 * @return
	 */
	@RequestMapping("/authCallback")
	public String authCallback(HttpServletRequest request, HttpServletResponse response) {
		try {
			String code = request.getParameter("code");
			String sessionid = request.getParameter("state");
			logger.info("authCallbac|授权后回调;入参;code={},state(sessionid)={}", code, sessionid);

			// 将openid放入到session中
			String openid = weixinService.authCallback(request);
			// request.getSession().setAttribute("openid", openid);
			logger.info("authCallbac|授权后回调;出参;从授权接口中获取的openid={}", openid);

			// 转到之前调用的正常页面
			String url = CacheUtils.get(sessionid, WyConstants.CACHE_KEY_REDIRECT_URL);
			logger.info("认证成功后回调url={}", url);

			// 转发到实际支付地址,带有jsessionid,保存原来状态
			return "redirect:" + url;
		} catch (Exception e) {
			logger.error("授权后回调,{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 下载多媒体文件
	 * 
	 * @return
	 * @throws WxErrorException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@ResponseBody
	@RequestMapping("/uploadphoto")
	public WeJson uploadphoto(HttpServletRequest request, String media_id, String type) throws WxErrorException, ClientProtocolException, IOException {
		// Member member = MemberUtils.getCurrentMember();
		String getMediaUrl = weixinService.dealPhoto(media_id);
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		SimpleDateFormat time = new SimpleDateFormat("HHmmss");
		Date date = new Date();
		String slash = File.separator;
		String filename = "unknow";
		if (type != null && !type.equals("")) {
			if (type.equals("repair")) {
				filename = "bxImage";
			} else if (type.equals("houseRent")) {
				filename = "houseRent";
			}
		}
		String imageName = filename + time.format(date) + ((int) Math.random() * 100) + ".jpg";
		String urlPath = filename + slash + year.format(date) + slash + month.format(date) + slash + day.format(date) + slash;
		String amrPath = Global.getUserfilesBaseDir() + urlPath;

		boolean b = HttpUtils.executeDownloadFile(getMediaUrl, amrPath, imageName);
		if (b) {
			String imgUrl = (Global.getConfig("server.project.images") + slash + urlPath).replaceAll("\\\\", "/") + imageName;
			return WeJson.success(imgUrl);
		} else {
			return WeJson.fail("下载图片失败");
		}
	}

	/**
	 * 根据url获取jssdk
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/wxjssdk")
	public WeJson wxjssdk(Model model, HttpServletRequest request) throws Exception {

		String url = StringUtils.obj2String(request.getParameter("url"));
		WxJsapiSignature jsapi = weixinService.getWxJsapiSignature(url);
		// model.addAttribute("jsapi", jsapi);

		return WeJson.success(jsapi);
	}

	public static void main(String[] args) {
		String str = "PROCESS_INSTANCE_ID";
		System.out.println(str.toLowerCase());
	}

}
