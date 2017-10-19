package com.haolinbang.modules.weixin.web;

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
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.common.utils.Encodes;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sns.entity.WyConvenienceService;
import com.haolinbang.modules.sns.service.WyConvenienceServiceService;
import com.haolinbang.modules.weixin.entity.WxMassMsgCommon;
import com.haolinbang.modules.weixin.entity.WxMsgTplCommon;
import com.haolinbang.modules.weixin.service.WxMassMsgCommonService;
import com.haolinbang.modules.weixin.service.WxMsgTplCommonService;

/**
 * 通用实例消息Controller
 * 
 * @author nlp
 * @version 2017-08-29
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMassMsgCommon")
public class WxMassMsgCommonController extends BaseController {

	@Autowired
	private WxMassMsgCommonService wxMassMsgCommonService;

	@Autowired
	private WxMsgTplCommonService wxMsgTplCommonService;

	@Autowired
	private WyConvenienceServiceService wyConvenienceServiceService;

	@ModelAttribute
	public WxMassMsgCommon get(@RequestParam(required = false) String id) {
		WxMassMsgCommon entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxMassMsgCommonService.get(id);
		}
		if (entity == null) {
			entity = new WxMassMsgCommon();
		}
		return entity;
	}

	@RequiresPermissions("weixin:wxMassMsgCommon:view")
	@RequestMapping(value = { "list", "" })
	public String list(WxMassMsgCommon wxMassMsgCommon, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMassMsgCommon> page = wxMassMsgCommonService.findPage(new Page<WxMassMsgCommon>(request, response), wxMassMsgCommon);
		model.addAttribute("page", page);
		return "modules/weixin/wxMassMsgCommonList";
	}

	/**
	 * 分两步进行创建模板消息
	 * 
	 * @param step
	 *            当前进行的步骤
	 * @param wxMassMsgCommon
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsgCommon:view")
	@RequestMapping(value = "form")
	public String form(WxMassMsgCommon wxMassMsgCommon, Model model) {
		model.addAttribute("wxMassMsgCommon", wxMassMsgCommon);
		String msgDetailId = wxMassMsgCommon.getMsgDetailId();
		if (StringUtils.isNotBlank(msgDetailId)) {
			WyConvenienceService wyConvenienceService = wyConvenienceServiceService.get(msgDetailId);
			if (wyConvenienceService != null && StringUtils.isNotBlank(wyConvenienceService.getContent())) {
				String content = wyConvenienceService.getContent();
				if (StringUtils.isNotBlank(content)) {
					content = Encodes.unescapeHtml(wyConvenienceService.getContent());					
				}
				wxMassMsgCommon.setContent(content);
			}
		}

		String step = wxMassMsgCommon.getStep();
		if (StringUtils.isBlank(step)) {
			step = "1";
		}
		// 第一步,展示模板供选择
		if ("1".equals(step)) {
			// 查询已有的通用模板消息
			WxMsgTplCommon wxMsgTplCommon = new WxMsgTplCommon();
			List<WxMsgTplCommon> wxMsgTplCommonList = wxMsgTplCommonService.findList(wxMsgTplCommon);
			model.addAttribute("wxMsgTplCommonList", wxMsgTplCommonList);
			return "modules/weixin/wxMassMsgCommonForm1";
		}
		// 第二步，根据选择的 模板进行填写模板消息
		else {
			// 将模板加载出来
			WxMsgTplCommon wxMsgTplCommon = wxMsgTplCommonService.get(wxMassMsgCommon.getTplId().toString());
			model.addAttribute("wxMsgTplCommon", wxMsgTplCommon);
			return "modules/weixin/wxMassMsgCommonForm2";
		}
	}

	@RequiresPermissions("weixin:wxMassMsgCommon:edit")
	@RequestMapping(value = "save")
	public String save(WxMassMsgCommon wxMassMsgCommon, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMassMsgCommon)) {
			return form(wxMassMsgCommon, model);
		}

		wxMassMsgCommonService.save(wxMassMsgCommon);
		addMessage(redirectAttributes, "保存通用实例消息成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMassMsgCommon/?repage";
	}

	@RequiresPermissions("weixin:wxMassMsgCommon:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMassMsgCommon wxMassMsgCommon, RedirectAttributes redirectAttributes) {
		wxMassMsgCommonService.delete(wxMassMsgCommon);
		addMessage(redirectAttributes, "删除通用实例消息成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMassMsgCommon/?repage";
	}
	
	/**
	 * 选择展示
	 * @param wxMassMsgCommon
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsgCommon:view")
	@RequestMapping("/select")
	public String select(WxMassMsgCommon wxMassMsgCommon, HttpServletRequest request, HttpServletResponse response, Model model) {
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		Page<WxMassMsgCommon> page = wxMassMsgCommonService.findPage(new Page<WxMassMsgCommon>(request, response), wxMassMsgCommon);
		model.addAttribute("page", page);
		return "modules/weixin/wxMassMsgCommonSelect";
	}

}