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
import com.haolinbang.modules.weixin.entity.WxMsgTplCommon;
import com.haolinbang.modules.weixin.service.WxMsgTplCommonService;

/**
 * 模板消息通用模板定义Controller
 * 
 * @author nlp
 * @version 2017-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMsgTplCommon")
public class WxMsgTplCommonController extends BaseController {

	@Autowired
	private WxMsgTplCommonService wxMsgTplCommonService;

	@ModelAttribute
	public WxMsgTplCommon get(@RequestParam(required = false) String id) {
		WxMsgTplCommon entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxMsgTplCommonService.get(id);
		}
		if (entity == null) {
			entity = new WxMsgTplCommon();
		}
		return entity;
	}

	@RequiresPermissions("weixin:wxMsgTplCommon:view")
	@RequestMapping(value = { "list", "" })
	public String list(WxMsgTplCommon wxMsgTplCommon, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMsgTplCommon> page = wxMsgTplCommonService.findPage(new Page<WxMsgTplCommon>(request, response), wxMsgTplCommon);
		model.addAttribute("page", page);
		return "modules/weixin/wxMsgTplCommonList";
	}

	@RequiresPermissions("weixin:wxMsgTplCommon:view")
	@RequestMapping(value = "form")
	public String form(WxMsgTplCommon wxMsgTplCommon, Model model) {
		model.addAttribute("wxMsgTplCommon", wxMsgTplCommon);
		return "modules/weixin/wxMsgTplCommonForm";
	}

	@RequiresPermissions("weixin:wxMsgTplCommon:edit")
	@RequestMapping(value = "save")
	public String save(WxMsgTplCommon wxMsgTplCommon, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMsgTplCommon)) {
			return form(wxMsgTplCommon, model);
		}
		// 字段1校验字段
		if (StringUtils.isBlank(wxMsgTplCommon.getKeyword1Name()) || StringUtils.isBlank(wxMsgTplCommon.getKeyword1Field())) {
			wxMsgTplCommon.setKeyword1Color(null);
		}
		// 字段2校验字段
		if (StringUtils.isBlank(wxMsgTplCommon.getKeyword2Name()) || StringUtils.isBlank(wxMsgTplCommon.getKeyword2Field())) {
			wxMsgTplCommon.setKeyword2Color(null);
		}
		// 字段3校验字段
		if (StringUtils.isBlank(wxMsgTplCommon.getKeyword3Name()) || StringUtils.isBlank(wxMsgTplCommon.getKeyword3Field())) {
			wxMsgTplCommon.setKeyword3Color(null);
		}
		// 字段4校验字段
		if (StringUtils.isBlank(wxMsgTplCommon.getKeyword4Name()) || StringUtils.isBlank(wxMsgTplCommon.getKeyword4Field())) {
			wxMsgTplCommon.setKeyword4Color(null);
		}
		// 字段5校验字段
		if (StringUtils.isBlank(wxMsgTplCommon.getKeyword5Name()) || StringUtils.isBlank(wxMsgTplCommon.getKeyword5Field())) {
			wxMsgTplCommon.setKeyword5Color(null);
		}

		wxMsgTplCommonService.save(wxMsgTplCommon);
		addMessage(redirectAttributes, "保存模板消息通用模板定义成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMsgTplCommon/?repage";
	}

	@RequiresPermissions("weixin:wxMsgTplCommon:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMsgTplCommon wxMsgTplCommon, RedirectAttributes redirectAttributes) {
		wxMsgTplCommonService.delete(wxMsgTplCommon);
		addMessage(redirectAttributes, "删除模板消息通用模板定义成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMsgTplCommon/?repage";
	}

}