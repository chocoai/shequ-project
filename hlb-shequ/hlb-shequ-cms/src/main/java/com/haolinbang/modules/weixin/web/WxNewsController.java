package com.haolinbang.modules.weixin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sys.utils.DictUtils;
import com.haolinbang.modules.weixin.entity.WxNews;
import com.haolinbang.modules.weixin.service.impl.WxNewsService;
import com.haolinbang.modules.weixin.utils.AccountUtil;

/**
 * 微信图文Controller
 * 
 * @author nlp
 * @version 2016-02-13
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxNews")
public class WxNewsController extends BaseController {

	@Autowired
	private WxNewsService wxNewsService;

	@ModelAttribute
	public WxNews get(@RequestParam(required = false) String id) {
		WxNews entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxNewsService.get(id);
		}
		if (entity == null) {
			entity = new WxNews();
		}
		return entity;
	}

	@RequiresPermissions("weixin:wxNews:view")
	@RequestMapping(value = { "list", "" })
	public String list(WxNews wxNews, HttpServletRequest request, HttpServletResponse response, Model model) {
		wxNews.setAccount(AccountUtil.getAccount());
		Page<WxNews> page = wxNewsService.findPage(new Page<WxNews>(request, response), wxNews);
		model.addAttribute("page", page);
		return "modules/weixin/wxNewsList";
	}

	@RequiresPermissions("weixin:wxNews:view")
	@RequestMapping(value = "form")
	public String form(WxNews wxNews, Model model) {
		model.addAttribute("wxNews", wxNews);
		return "modules/weixin/wxNewsForm";
	}

	@RequiresPermissions("weixin:wxNews:edit")
	@RequestMapping(value = "save")
	public String save(WxNews wxNews, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		wxNews.setAccount(AccountUtil.getAccount());
		// 拼接外部前台链接
		String url = "http://" + DictUtils.getDictValue("域名设置", "sys_access_domain", "") + request.getContextPath() + frontPath + "/weixin/news/view-";
		wxNews.setUrl(url);
		// 设置微信图片地址
		wxNews.setPicurl("http://" + DictUtils.getDictValue("域名设置", "sys_access_domain", "") + wxNews.getPicurl());
		if (!beanValidator(model, wxNews)) {
			return form(wxNews, model);
		}
		wxNewsService.save(wxNews);
		addMessage(redirectAttributes, "保存微信图文成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxNews/?repage";
	}

	@RequiresPermissions("weixin:wxNews:edit")
	@RequestMapping(value = "delete")
	public String delete(WxNews wxNews, RedirectAttributes redirectAttributes) {
		wxNewsService.delete(wxNews);
		addMessage(redirectAttributes, "删除微信图文成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxNews/?repage";
	}

}