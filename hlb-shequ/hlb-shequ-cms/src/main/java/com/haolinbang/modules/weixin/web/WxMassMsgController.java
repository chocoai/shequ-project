package com.haolinbang.modules.weixin.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem;

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
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.entity.WxMassMsg;
import com.haolinbang.modules.weixin.entity.WxNewsArticle;
import com.haolinbang.modules.weixin.service.WxMassMsgService;
import com.haolinbang.modules.weixin.service.WxNewsArticleService;
import com.haolinbang.modules.weixin.service.inter.WeixinService;
import com.haolinbang.modules.weixin.service.inter.WxAccountService;

/**
 * 群发消息记录表Controller
 * 
 * @author nlp
 * @version 2017-07-11
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMassMsg")
public class WxMassMsgController extends BaseController {

	@Autowired
	private WxMassMsgService wxMassMsgService;

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private WxNewsArticleService wxNewsArticleService;

	@Autowired
	private WxAccountService wxAccountService;

	@ModelAttribute
	public WxMassMsg get(@RequestParam(required = false) String id) {
		WxMassMsg entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxMassMsgService.get(id);
		}
		if (entity == null) {
			entity = new WxMassMsg();
		}
		return entity;
	}

	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping(value = { "list", "" })
	public String list(WxMassMsg wxMassMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMassMsg> page = wxMassMsgService.findPage(new Page<WxMassMsg>(request, response), wxMassMsg);
		model.addAttribute("page", page);
		return "modules/weixin/wxMassMsgList";
	}

	/**
	 * 群发图文消息
	 * 
	 * @param wxMassMsg
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping(value = "form")
	public String form(WxMassMsg wxMassMsg, Model model) {
		// 获取图文消息

		model.addAttribute("wxMassMsg", wxMassMsg);
		return "modules/weixin/wxMassMsgForm";
	}

	/**
	 * 发送群发消息并且保存发送记录
	 * 
	 * @param wxMassMsg
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:edit")
	@RequestMapping(value = "save")
	public String save(WxMassMsg wxMassMsg, Model model, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(wxMassMsg.getNewsId())) {
			wxMassMsgService.save(wxMassMsg);
			addMessage(redirectAttributes, "保存群发消息记录成功");			
		} else {
			addMessage(redirectAttributes, "保存群发消息记录失败");
		}
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMassMsg/?repage";
	}

	@RequiresPermissions("weixin:wxMassMsg:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMassMsg wxMassMsg, RedirectAttributes redirectAttributes) {
		wxMassMsgService.delete(wxMassMsg);
		addMessage(redirectAttributes, "删除群发消息记录表成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMassMsg/?repage";
	}

	/**
	 * 群发图文消息
	 * 
	 * @param wxMassMsg
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:edit")
	@RequestMapping("/showSelectNews")
	public String showSelectNews(WxMassMsg wxMassMsg, Model model) {
		// 获取图文消息
		try {
			List<WxMaterialNewsBatchGetNewsItem> newsList = weixinService.getNewsList(0, 20);

			model.addAttribute("newsList", newsList);
		} catch (WxErrorException e) {
			logger.error("群发图文消息出错:{}", e);
		}
		model.addAttribute("wxMassMsg", wxMassMsg);
		return "modules/weixin/showSelectNews";
	}

	/**
	 * 群发图文消息
	 * 
	 * @param wxMassMsg
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:edit")
	@RequestMapping("/showSelectNews2")
	public String showSelectNews2(WxMassMsg wxMassMsg, Model model) {
		try {

			WxAccount account = wxAccountService.getDefaultWxAccount();
			// 获取保存的图文消息
			WxNewsArticle wxNewsArticle = new WxNewsArticle();
			wxNewsArticle.setAccountId(account.getId());

			List<WxNewsArticle> wxNewsArticleList = wxNewsArticleService.findList(wxNewsArticle);

			for (WxNewsArticle article : wxNewsArticleList) {
				// article.setUrl(weixinService.getBase64StrByMediaId(article.getThumbMediaId()));
			}

			model.addAttribute("wxNewsArticleList", wxNewsArticleList);
			model.addAttribute("wxMassMsg", wxMassMsg);
			return "modules/weixin/showSelectNews2";
		} catch (Exception e) {

		}
		return "";
	}
}