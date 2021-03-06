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
import com.haolinbang.modules.weixin.entity.WxMsgTpl;
import com.haolinbang.modules.weixin.service.WxMsgTplService;

/**
 * 具体消息模板Controller
 * @author nlp
 * @version 2017-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMsgTpl")
public class WxMsgTplController extends BaseController {

	@Autowired
	private WxMsgTplService wxMsgTplService;
	
	@ModelAttribute
	public WxMsgTpl get(@RequestParam(required=false) String id) {
		WxMsgTpl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMsgTplService.get(id);
		}
		if (entity == null){
			entity = new WxMsgTpl();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:wxMsgTpl:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMsgTpl wxMsgTpl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMsgTpl> page = wxMsgTplService.findPage(new Page<WxMsgTpl>(request, response), wxMsgTpl); 
		model.addAttribute("page", page);
		return "modules/weixin/wxMsgTplList";
	}

	@RequiresPermissions("weixin:wxMsgTpl:view")
	@RequestMapping(value = "form")
	public String form(WxMsgTpl wxMsgTpl, Model model) {
		model.addAttribute("wxMsgTpl", wxMsgTpl);
		return "modules/weixin/wxMsgTplForm";
	}

	@RequiresPermissions("weixin:wxMsgTpl:edit")
	@RequestMapping(value = "save")
	public String save(WxMsgTpl wxMsgTpl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMsgTpl)){
			return form(wxMsgTpl, model);
		}
		wxMsgTplService.save(wxMsgTpl);
		addMessage(redirectAttributes, "保存具体消息模板成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxMsgTpl/?repage";
	}
	
	@RequiresPermissions("weixin:wxMsgTpl:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMsgTpl wxMsgTpl, RedirectAttributes redirectAttributes) {
		wxMsgTplService.delete(wxMsgTpl);
		addMessage(redirectAttributes, "删除具体消息模板成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxMsgTpl/?repage";
	}

}