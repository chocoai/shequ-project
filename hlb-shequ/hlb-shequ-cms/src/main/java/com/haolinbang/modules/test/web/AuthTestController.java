package com.haolinbang.modules.test.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("${adminPath}/auth")
public class AuthTestController {

	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model) throws UnsupportedEncodingException {

		String str = request.getHeader("auth-data");

		Cookie[] cookies = request.getCookies();

		String type = request.getParameter("type");
		if ("1".equals(type)) {
			String url/* = Servlets.getRequestOuterNetUrlAndParas() */;
			url = "http://192.168.1.2:9090/hlb-shequ-cms/auth/login";

			url = URLEncoder.encode(url, "UTF-8");
			return "redirect:http://192.168.1.2:9090/sso-web/auth/login?biz_code=bizsys&cb_url=" + url;
		}

		return "modules/test/auth/login";
	}

	@ResponseBody
	@RequestMapping("/encode")
	public String encode() {
		String str = "你好";

		try {
			str = new String(str.getBytes("utf-8"), "gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str;
	}

}
