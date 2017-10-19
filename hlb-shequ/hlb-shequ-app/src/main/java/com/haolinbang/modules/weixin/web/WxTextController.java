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
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.weixin.entity.WxText;
import com.haolinbang.modules.weixin.service.impl.WxTextService;

/**
 * 微信文本Controller
 * 
 * @author nlp
 * @version 2016-02-13
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxText")
public class WxTextController extends BaseController {

	@Autowired
	private WxTextService wxTextService;

	@ModelAttribute
	public WxText get(@RequestParam(required = false) String id) {
		WxText entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxTextService.get(id);
		}
		if (entity == null) {
			entity = new WxText();
		}
		return entity;
	}

	@RequiresPermissions("weixin:wxText:view")
	@RequestMapping(value = { "list", "" })
	public String list(WxText wxText, HttpServletRequest request, HttpServletResponse response, Model model) {
		wxText.setAccountIds(UserUtils.buildSqlInWxAccountids());
		Page<WxText> page = wxTextService.findPage(new Page<WxText>(request, response), wxText);
		model.addAttribute("page", page);
		return "modules/weixin/wxTextList";
	}

	@RequiresPermissions("weixin:wxText:view")
	@RequestMapping(value = "form")
	public String form(WxText wxText, Model model) {
		model.addAttribute("wxText", wxText);
		return "modules/weixin/wxTextForm";
	}

	@RequiresPermissions("weixin:wxText:edit")
	@RequestMapping(value = "save")
	public String save(WxText wxText, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxText)) {
			return form(wxText, model);
		}
		wxTextService.save(wxText);
		addMessage(redirectAttributes, "保存微信文本成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxText/?repage";
	}

	@RequiresPermissions("weixin:wxText:edit")
	@RequestMapping(value = "delete")
	public String delete(WxText wxText, RedirectAttributes redirectAttributes) {
		wxTextService.delete(wxText);
		addMessage(redirectAttributes, "删除微信文本成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxText/?repage";
	}

}