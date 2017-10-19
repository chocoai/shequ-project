package com.haolinbang.modules.weixin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.weixin.entity.WxAreply;
import com.haolinbang.modules.weixin.service.impl.WxAreplyService;
import com.haolinbang.modules.weixin.web.front.WeixinController;

/**
 * 自动回复Controller
 * 
 * @author nlp
 * @version 2016-02-08
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxAreply")
public class WxAreplyController extends BaseController {

	private Logger log = LoggerFactory.getLogger(WeixinController.class);

	@Autowired
	private WxAreplyService wxAreplyService;

	@ModelAttribute
	public WxAreply get(@RequestParam(required = false) String id) {
		WxAreply entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxAreplyService.get(id);
		}
		if (entity == null) {
			entity = new WxAreply();
		}
		return entity;
	}

	@RequiresPermissions("weixin:wxAreply:view")
	@RequestMapping(value = { "list", "" })
	public String list(WxAreply wxAreply, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 查询所有公众号,查询一个默认的公众号
		wxAreply.setAccountIds(UserUtils.buildSqlInWxAccountids());

		Page<WxAreply> page = wxAreplyService.findPage(new Page<WxAreply>(request, response), wxAreply);
		model.addAttribute("page", page);
		return "modules/weixin/wxAreplyList";
	}

	@RequiresPermissions("weixin:wxAreply:view")
	@RequestMapping(value = "form")
	public String form(WxAreply wxAreply, Model model) {
		model.addAttribute("wxAreply", wxAreply);
		return "modules/weixin/wxAreplyForm";
	}

	@RequiresPermissions("weixin:wxAreply:edit")
	@RequestMapping(value = "save")
	public String save(WxAreply wxAreply, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxAreply)) {
			return form(wxAreply, model);
		}
		wxAreplyService.save(wxAreply);
		addMessage(redirectAttributes, "保存自动回复成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxAreply/?repage";
	}

	@RequiresPermissions("weixin:wxAreply:edit")
	@RequestMapping(value = "delete")
	public String delete(WxAreply wxAreply, RedirectAttributes redirectAttributes) {
		wxAreplyService.delete(wxAreply);
		addMessage(redirectAttributes, "删除自动回复成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxAreply/?repage";
	}

	/**
	 * 设置该微信公众号默认回复
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("weixin:wxAreply:edit")
	@RequestMapping(value = "setDefaultAreply")
	@ResponseBody
	public String setDefaultAreply(String id, RedirectAttributes redirectAttributes) {
		log.debug("id={}", id);
		wxAreplyService.setDefaultAreply(id);
		addMessage(redirectAttributes, "设置默认回复成功");
		return "true";
	}

}