package com.haolinbang.modules.weixin.web.front;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.weixin.service.inter.WeixinService;

/**
 * 微信Controller
 * 
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController extends BaseController {

	private Logger log = LoggerFactory.getLogger(WeixinController.class);

	@Autowired
	private WeixinService weixinService;

	/**
	 * 微信请求接口
	 * 
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/deal/{token}")
	public String deal(@PathVariable String token, HttpServletRequest request) {
		if (StringUtils.isBlank(token)) {
			log.error("config is error..");
			return "config is error..";
		}
		return weixinService.deal(request, token);
	}

	@ResponseBody
	@RequestMapping("/mass")
	public WeJson mass(HttpServletRequest request) {
		

		return weixinService.mass(request);		
	}

}
