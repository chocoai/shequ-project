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
import com.haolinbang.modules.weixin.entity.WxMessage;
import com.haolinbang.modules.weixin.service.impl.WxMessageService;

/**
 * 微信消息Controller
 * @author nlp
 * @version 2016-02-06
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMessage")
public class WxMessageController extends BaseController {

	@Autowired
	private WxMessageService wxMessageService;
	
	@ModelAttribute
	public WxMessage get(@RequestParam(required=false) String id) {
		WxMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMessageService.get(id);
		}
		if (entity == null){
			entity = new WxMessage();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:wxMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMessage wxMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMessage> page = wxMessageService.findPage(new Page<WxMessage>(request, response), wxMessage); 
		model.addAttribute("page", page);
		return "modules/weixin/wxMessageList";
	}

	@RequiresPermissions("weixin:wxMessage:view")
	@RequestMapping(value = "form")
	public String form(WxMessage wxMessage, Model model) {
		model.addAttribute("wxMessage", wxMessage);
		return "modules/weixin/wxMessageForm";
	}

	@RequiresPermissions("weixin:wxMessage:edit")
	@RequestMapping(value = "save")
	public String save(WxMessage wxMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMessage)){
			return form(wxMessage, model);
		}
		wxMessageService.save(wxMessage);
		addMessage(redirectAttributes, "保存微信消息成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxMessage/?repage";
	}
	
	@RequiresPermissions("weixin:wxMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMessage wxMessage, RedirectAttributes redirectAttributes) {
		wxMessageService.delete(wxMessage);
		addMessage(redirectAttributes, "删除微信消息成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxMessage/?repage";
	}

}