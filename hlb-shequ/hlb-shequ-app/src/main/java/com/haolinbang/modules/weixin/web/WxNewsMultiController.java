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
import com.haolinbang.modules.weixin.entity.WxNewsMulti;
import com.haolinbang.modules.weixin.service.impl.WxNewsMultiService;

/**
 * 微信多图文Controller
 * 
 * @author nlp
 * @version 2016-02-13
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxNewsMulti")
public class WxNewsMultiController extends BaseController {

	@Autowired
	private WxNewsMultiService wxNewsMultiService;

	@ModelAttribute
	public WxNewsMulti get(@RequestParam(required = false) String id) {
		WxNewsMulti entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxNewsMultiService.get(id);
		}
		if (entity == null) {
			entity = new WxNewsMulti();
		}
		return entity;
	}

	@RequiresPermissions("weixin:wxNewsMulti:view")
	@RequestMapping(value = { "list", "" })
	public String list(WxNewsMulti wxNewsMulti, HttpServletRequest request, HttpServletResponse response, Model model) {
		wxNewsMulti.setAccountIds(UserUtils.buildSqlInWxAccountids());
		Page<WxNewsMulti> page = wxNewsMultiService.findPage(new Page<WxNewsMulti>(request, response), wxNewsMulti);
		model.addAttribute("page", page);
		return "modules/weixin/wxNewsMultiList";
	}

	@RequiresPermissions("weixin:wxNewsMulti:view")
	@RequestMapping(value = "form")
	public String form(WxNewsMulti wxNewsMulti, Model model) {
		model.addAttribute("wxNewsMulti", wxNewsMulti);
		return "modules/weixin/wxNewsMultiForm";
	}

	@RequiresPermissions("weixin:wxNewsMulti:edit")
	@RequestMapping(value = "save")
	public String save(WxNewsMulti wxNewsMulti, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxNewsMulti)) {
			return form(wxNewsMulti, model);
		}
		wxNewsMultiService.save(wxNewsMulti);
		addMessage(redirectAttributes, "保存微信多图文成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxNewsMulti/?repage";
	}

	@RequiresPermissions("weixin:wxNewsMulti:edit")
	@RequestMapping(value = "delete")
	public String delete(WxNewsMulti wxNewsMulti, RedirectAttributes redirectAttributes) {
		wxNewsMultiService.delete(wxNewsMulti);
		addMessage(redirectAttributes, "删除微信多图文成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxNewsMulti/?repage";
	}

}