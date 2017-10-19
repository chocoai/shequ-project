package com.haolinbang.modules.sns.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/mytest")
public class TestController {
	 /**
     * 微信消息接收和token验证
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/test")
    public void test(Model model, HttpServletRequest request,HttpServletResponse response) throws IOException {
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter print;
        if (isGet) {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature != null/* && CheckoutUtil.checkSignature(signature, timestamp, nonce)*/) {
                try {
                    print = response.getWriter();
                    print.write(echostr);
                    print.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("/myd")
	public String myd(){

    	return "modules/sns/myDeliveryAddress";
	}
    
    @RequestMapping("/addd")
	public String addd(){

    	return "modules/sns/addDeliveryAddress";
	}
    
    @RequestMapping("/editd")
	public String editd(){

    	return "modules/sns/editDeliveryAddress";
	}
    
    @RequestMapping("/test1")
	public String test1(){

    	return "modules/sns/wyConvenienceServiceSearch";
	}
}
