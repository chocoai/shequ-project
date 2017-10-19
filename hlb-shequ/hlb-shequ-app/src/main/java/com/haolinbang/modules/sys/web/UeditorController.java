package com.haolinbang.modules.sys.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.ueditor.ActionEnter;
import com.haolinbang.common.web.BaseController;

/**
 * UeditorController
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/ueditor")
public class UeditorController extends BaseController {
	
	@ResponseBody
	@RequestMapping(value = "getUeditor")
	public String getUeditor(HttpServletRequest request, HttpServletResponse response){
		try {
			request.setCharacterEncoding( "utf-8" );
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}
		response.setHeader("Content-Type" , "text/html");
		String ae = new ActionEnter(request, request.getSession().getServletContext().getRealPath("") ).exec();
		
		return ae;
	}
	
}