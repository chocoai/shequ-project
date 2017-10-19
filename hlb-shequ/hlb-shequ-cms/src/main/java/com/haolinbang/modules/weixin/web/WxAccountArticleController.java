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
import com.haolinbang.modules.cms.entity.Article;
import com.haolinbang.modules.cms.service.ArticleDataService;
import com.haolinbang.modules.cms.service.ArticleService;
import com.haolinbang.modules.cms.service.CategoryService;
import com.haolinbang.modules.cms.service.FileTplService;
import com.haolinbang.modules.cms.service.SiteService;
import com.haolinbang.modules.weixin.entity.WxAccountArticle;
import com.haolinbang.modules.weixin.service.impl.WxAccountArticleService;

/**
 * 账号和文章对应Controller <br/>
 * 主要是微信回复文章的内容 <br/>
 * 
 * @author nlp
 * @version 2016-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxAccountArticle")
public class WxAccountArticleController extends BaseController {

	@Autowired
	private WxAccountArticleService wxAccountArticleService;

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleDataService articleDataService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private FileTplService fileTplService;
	@Autowired
	private SiteService siteService;

	@ModelAttribute
	public WxAccountArticle get(@RequestParam(required = false) String id) {
		WxAccountArticle entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxAccountArticleService.get(id);
		}
		if (entity == null) {
			entity = new WxAccountArticle();
		}
		return entity;
	}

	@RequiresPermissions("weixin:wxAccountArticle:view")
	@RequestMapping(value = { "list", "" })
	public String list(WxAccountArticle wxAccountArticle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxAccountArticle> page = wxAccountArticleService.findPage(new Page<WxAccountArticle>(request, response), wxAccountArticle);
		model.addAttribute("page", page);
		return "modules/weixin/wxAccountArticleList";
	}

	@RequiresPermissions("weixin:wxAccountArticle:view")
	@RequestMapping(value = "form")
	public String form(WxAccountArticle wxAccountArticle, Model model) {
		model.addAttribute("wxAccountArticle", wxAccountArticle);
		return "modules/weixin/wxAccountArticleForm";
	}

	@RequiresPermissions("weixin:wxAccountArticle:edit")
	@RequestMapping(value = "save")
	public String save(WxAccountArticle wxAccountArticle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxAccountArticle)) {
			return form(wxAccountArticle, model);
		}
		wxAccountArticleService.save(wxAccountArticle);
		addMessage(redirectAttributes, "保存账号和文章对应成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxAccountArticle/?repage";
	}

	@RequiresPermissions("weixin:wxAccountArticle:edit")
	@RequestMapping(value = "delete")
	public String delete(WxAccountArticle wxAccountArticle, RedirectAttributes redirectAttributes) {
		wxAccountArticleService.delete(wxAccountArticle);
		addMessage(redirectAttributes, "删除账号和文章对应成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxAccountArticle/?repage";
	}

	/**
	 * 关注后自动回复
	 * 
	 * @param wxAccountArticle
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxAccountArticle:view")
	@RequestMapping(value = { "subscribeAutoReply" })
	public String subscribeAutoReply(Article article, Model model) {
		model.addAttribute("article", article);
		return "modules/weixin/subscribeAutoReply";
	}

	/**
	 * 保存自动回复
	 * 
	 * @param article
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxAccountArticle:view")
	@RequestMapping(value = { "subscribeAutoReplySave" })
	public String subscribeAutoReplySave(Article article, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, article)) {
			return subscribeAutoReply(article, model);
		}
		article.setCategory(categoryService.get("6c34974274cf4946ac6f0819bf187c56"));

		articleService.save(article);
		addMessage(redirectAttributes, "自动回复成功");
		return "redirect:" + adminPath + "/weixin/wxAccountArticle/?repage&article.id=" + article.getId();
	}

}