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
import com.haolinbang.modules.weixin.entity.WxMassUser;
import com.haolinbang.modules.weixin.service.WxMassUserService;

/**
 * 群发消息用户表Controller
 * @author nlp
 * @version 2017-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMassUser")
public class WxMassUserController extends BaseController {

	@Autowired
	private WxMassUserService wxMassUserService;
	
	@ModelAttribute
	public WxMassUser get(@RequestParam(required=false) String id) {
		WxMassUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMassUserService.get(id);
		}
		if (entity == null){
			entity = new WxMassUser();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:wxMassUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMassUser wxMassUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMassUser> page = wxMassUserService.findPage(new Page<WxMassUser>(request, response), wxMassUser); 
		model.addAttribute("page", page);
		return "modules/weixin/wxMassUserList";
	}

	@RequiresPermissions("weixin:wxMassUser:view")
	@RequestMapping(value = "form")
	public String form(WxMassUser wxMassUser, Model model) {
		model.addAttribute("wxMassUser", wxMassUser);
		return "modules/weixin/wxMassUserForm";
	}

	@RequiresPermissions("weixin:wxMassUser:edit")
	@RequestMapping(value = "save")
	public String save(WxMassUser wxMassUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMassUser)){
			return form(wxMassUser, model);
		}
		wxMassUserService.save(wxMassUser);
		addMessage(redirectAttributes, "保存群发消息用户表成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxMassUser/?repage";
	}
	
	@RequiresPermissions("weixin:wxMassUser:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMassUser wxMassUser, RedirectAttributes redirectAttributes) {
		wxMassUserService.delete(wxMassUser);
		addMessage(redirectAttributes, "删除群发消息用户表成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxMassUser/?repage";
	}

}