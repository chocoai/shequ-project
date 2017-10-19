package com.haolinbang.modules.sns.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haolinbang.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/sns/wyBizAct")
public class WyBizActRelationController extends BaseController {

	/**
	 * 配置流程和业务功能
	 * 
	 * @return
	 */
	@RequestMapping(value = "/confBizAct")
	public String confBizAct() {
		
		
		
		return "";
	}
	
	

}
