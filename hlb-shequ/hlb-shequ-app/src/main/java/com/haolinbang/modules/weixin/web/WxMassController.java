package com.haolinbang.modules.weixin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haolinbang.common.web.BaseController;

/**
 * 群发管理
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMass")
public class WxMassController extends BaseController {

	@RequestMapping("/massShow")
	public String massShow(Model model) {
		
		
		
		
		
		return "";
	}

}
