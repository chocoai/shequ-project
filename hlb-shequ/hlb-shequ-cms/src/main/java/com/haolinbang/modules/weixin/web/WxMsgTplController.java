package com.haolinbang.modules.weixin.web;

import java.util.ArrayList;
import java.util.List;

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
import com.haolinbang.modules.weixin.entity.WxMsgTpl;
import com.haolinbang.modules.weixin.entity.WxMsgTplCommon;
import com.haolinbang.modules.weixin.service.WxMsgTplCommonService;
import com.haolinbang.modules.weixin.service.WxMsgTplService;

/**
 * 消息模板Controller
 * 
 * @author nlp
 * @version 2017-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMsgTpl")
public class WxMsgTplController extends BaseController {

	@Autowired
	private WxMsgTplService wxMsgTplService;

	@Autowired
	private WxMsgTplCommonService wxMsgTplCommonService;

	@ModelAttribute
	public WxMsgTpl get(@RequestParam(required = false) String id) {
		WxMsgTpl entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxMsgTplService.get(id);
		}
		if (entity == null) {
			entity = new WxMsgTpl();
		}
		return entity;
	}

	@RequiresPermissions("weixin:wxMsgTpl:view")
	@RequestMapping("")
	public String index() {
		return "modules/weixin/wxMsgTplIndex";
	}

	@RequiresPermissions("weixin:wxMsgTpl:view")
	@RequestMapping("/list")
	public String list(WxMsgTpl wxMsgTpl, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<WxMsgTpl> page = wxMsgTplService.findPage(new Page<WxMsgTpl>(request, response), wxMsgTpl);
		if (StringUtils.isNotBlank(wxMsgTpl.getName())) {
			model.addAttribute("page", page);
			return "modules/weixin/wxMsgTplList";
		}

		// 已有模板实例条数
		List<WxMsgTpl> list = page.getList();
		List<WxMsgTpl> list2 = new ArrayList<WxMsgTpl>();
		// 查询通用模板条数
		WxMsgTplCommon wxMsgTplCommon = new WxMsgTplCommon();
		List<WxMsgTplCommon> wxMsgTplCommonList = wxMsgTplCommonService.findList(wxMsgTplCommon);
		if (list == null || list.isEmpty() || (wxMsgTplCommonList != null && list != null && wxMsgTplCommonList.size() > list.size())) {
			// list = new ArrayList<WxMsgTpl>();
			// 排除已经添加的模板
			List<WxMsgTplCommon> wxMsgTplCommonList2 = new ArrayList<WxMsgTplCommon>();
			if (list != null && !list.isEmpty()) {
				for (WxMsgTpl tpl : list) {
					for (WxMsgTplCommon tplCommon : wxMsgTplCommonList) {
						if (tpl.getCommonTpl() != null && tpl.getCommonTpl().getId() != null && !tplCommon.getId().equals(tpl.getCommonTpl().getId())) {
							wxMsgTplCommonList2.add(tplCommon);
						} else {
							list2.add(tpl);
						}
					}
				}
			} else {
				wxMsgTplCommonList2.addAll(wxMsgTplCommonList);
			}

			List<WxMsgTpl> list3 = new ArrayList<WxMsgTpl>();
			// 循环需要添加的新增消息模板
			String accountId = wxMsgTpl.getAccountId();
			if (StringUtils.isNotBlank(accountId)) {
				if (wxMsgTplCommonList2 != null && !wxMsgTplCommonList2.isEmpty()) {
					for (WxMsgTplCommon tpl : wxMsgTplCommonList2) {
						WxMsgTpl msgTpl = new WxMsgTpl();
						msgTpl.setCommonTpl(tpl);
						msgTpl.setName(tpl.getName());
						msgTpl.setSource(wxMsgTpl.getSource());
						msgTpl.setAccountId(accountId);
						msgTpl.preInsert();
						list3.add(msgTpl);
						list2.add(msgTpl);
					}
					wxMsgTplService.saveList(list3);
				}
			}
		}
		if (list2 != null && !list2.isEmpty()) {
			page.setList(list2);
		}
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
		if (!beanValidator(model, wxMsgTpl)) {
			return form(wxMsgTpl, model);
		}
		wxMsgTplService.save(wxMsgTpl);
		addMessage(redirectAttributes, "保存消息模板成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMsgTpl/?repage";
	}

	@RequiresPermissions("weixin:wxMsgTpl:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMsgTpl wxMsgTpl, RedirectAttributes redirectAttributes) {
		wxMsgTplService.delete(wxMsgTpl);
		addMessage(redirectAttributes, "删除消息模板成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMsgTpl/list?accountId=" + wxMsgTpl.getAccountId();
	}

	/**
	 * 更新模板id
	 * 
	 * @param wxMsgTpl
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("weixin:wxMsgTpl:edit")
	@RequestMapping("/updateTplId")
	public String updateTplId(WxMsgTpl wxMsgTpl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMsgTpl)) {
			return form(wxMsgTpl, model);
		}
		// 更新模板id
		WxMsgTpl wxMsgTpl2 = wxMsgTplService.get(wxMsgTpl.getId());
		wxMsgTpl2.setTplId(wxMsgTpl.getTplId());

		wxMsgTplService.save(wxMsgTpl2);
		addMessage(redirectAttributes, "更新消息模板ID成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMsgTpl/list?accountId=" + wxMsgTpl.getAccountId();
	}

}