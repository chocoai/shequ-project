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
import com.haolinbang.common.web.BaseController;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.WxAccountService;

/**
 * 微信公共帐号Controller
 * @author nlp
 * @version 2017-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxAccount")
public class WxAccountController extends BaseController {

	@Autowired
	private WxAccountService wxAccountService;
	
	@ModelAttribute
	public WxAccount get(@RequestParam(required=false) String id) {
		WxAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxAccountService.get(id);
		}
		if (entity == null){
			entity = new WxAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:wxAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxAccount wxAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxAccount> page = wxAccountService.findPage(new Page<WxAccount>(request, response), wxAccount); 
		model.addAttribute("page", page);
		return "modules/weixin/wxAccountList";
	}

	@RequiresPermissions("weixin:wxAccount:view")
	@RequestMapping(value = "form")
	public String form(WxAccount wxAccount, Model model) {
		model.addAttribute("wxAccount", wxAccount);
		return "modules/weixin/wxAccountForm";
	}

	@RequiresPermissions("weixin:wxAccount:edit")
	@RequestMapping(value = "save")
	public String save(WxAccount wxAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxAccount)){
			return form(wxAccount, model);
		}
		wxAccountService.save(wxAccount);
		addMessage(redirectAttributes, "保存微信公共帐号成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxAccount/?repage";
	}
	
	@RequiresPermissions("weixin:wxAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(WxAccount wxAccount, RedirectAttributes redirectAttributes) {
		wxAccountService.delete(wxAccount);
		addMessage(redirectAttributes, "删除微信公共帐号成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxAccount/?repage";
	}

}