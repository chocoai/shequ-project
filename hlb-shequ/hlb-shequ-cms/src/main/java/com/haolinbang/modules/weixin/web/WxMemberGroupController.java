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
import com.haolinbang.modules.weixin.entity.WxMemberGroup;
import com.haolinbang.modules.weixin.service.impl.WxMemberGroupService;

/**
 * 微信公众号粉丝分组Controller
 * @author nlp
 * @version 2016-01-31
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMemberGroup")
public class WxMemberGroupController extends BaseController {

	@Autowired
	private WxMemberGroupService wxMemberGroupService;
	
	@ModelAttribute
	public WxMemberGroup get(@RequestParam(required=false) String id) {
		WxMemberGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMemberGroupService.get(id);
		}
		if (entity == null){
			entity = new WxMemberGroup();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:wxMemberGroup:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMemberGroup wxMemberGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMemberGroup> page = wxMemberGroupService.findPage(new Page<WxMemberGroup>(request, response), wxMemberGroup); 
		model.addAttribute("page", page);
		return "modules/weixin/wxMemberGroupList";
	}

	@RequiresPermissions("weixin:wxMemberGroup:view")
	@RequestMapping(value = "form")
	public String form(WxMemberGroup wxMemberGroup, Model model) {
		model.addAttribute("wxMemberGroup", wxMemberGroup);
		return "modules/weixin/wxMemberGroupForm";
	}

	@RequiresPermissions("weixin:wxMemberGroup:edit")
	@RequestMapping(value = "save")
	public String save(WxMemberGroup wxMemberGroup, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMemberGroup)){
			return form(wxMemberGroup, model);
		}
		wxMemberGroupService.save(wxMemberGroup);
		addMessage(redirectAttributes, "保存微信公众号粉丝分组成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxMemberGroup/?repage";
	}
	
	@RequiresPermissions("weixin:wxMemberGroup:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMemberGroup wxMemberGroup, RedirectAttributes redirectAttributes) {
		wxMemberGroupService.delete(wxMemberGroup);
		addMessage(redirectAttributes, "删除微信公众号粉丝分组成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxMemberGroup/?repage";
	}

}