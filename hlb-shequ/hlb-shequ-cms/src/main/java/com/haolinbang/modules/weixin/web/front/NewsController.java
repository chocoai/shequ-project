package com.haolinbang.modules.weixin.web.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.weixin.entity.WxNews;
import com.haolinbang.modules.weixin.service.impl.WxNewsService;

/**
 * 微信Controller
 * 
 */
@Controller
@RequestMapping("${frontPath}/weixin/news")
public class NewsController extends BaseController {

	private Logger log = LoggerFactory.getLogger(NewsController.class);

	@Autowired
	private WxNewsService wxNewsService;

	@RequestMapping(value = "/view-{contentId}${urlSuffix}")
	public String view(@PathVariable String contentId, Model model) {
		log.debug("contentId={}", contentId);
		WxNews wxNews = wxNewsService.get(contentId);

		log.debug("wxNews={}", wxNews);

		model.addAttribute("wxNews", wxNews);
		model.addAttribute("wxNews", wxNews);
		return "modules/weixin/front/frontViewArticle";
	}
	
	
	
}
