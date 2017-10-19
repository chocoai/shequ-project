package com.haolinbang.modules.weixin.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.service.UrlmapService;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.inter.WxAccountService;
import com.haolinbang.modules.weixin.web.front.WeixinController;

/**
 * 微信管理Controller
 * 
 * @author nlp
 * @version 2016-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxAccount")
public class WxAccountController extends BaseController {
	private Logger log = LoggerFactory.getLogger(WeixinController.class);

	@Autowired
	private WxAccountService wxAccountService;

	@Autowired
	private UrlmapService urlmapService;

	@ModelAttribute
	public WxAccount get(@RequestParam(required = false) String id) {
		WxAccount entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxAccountService.get(id);
		}
		if (entity == null) {
			entity = new WxAccount();
		}
		return entity;
	}

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequiresPermissions("weixin:wxAccount:view")
	@RequestMapping("")
	public String index() {
		return "modules/weixin/wxAccountIndex";
	}

	@RequiresPermissions("weixin:wxAccount:view")
	@RequestMapping("/list")
	public String list(String showType, String gid, WxAccount wxAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		String type = null;
		String gid2 = null;
		String source = null;
		if (StringUtils.isNotBlank(gid)) {
			wxAccount.setGid(gid);
			Map<String, String> map = UserUtils.parseGroupids(gid);
			source = map.get("source");
			type = map.get("type");
			gid2 = map.get("gid");
		}

		if (StringUtils.isBlank(source)) {
			// 默认source
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
			List<Urlmap> urlmapList = urlmapService.getUrlmapBySource(null);
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

			if (urlmapList != null && urlmapList.size() > 0) {
				source = urlmapList.get(0).getUrlkey();
			}
			wxAccount.setSource(source);
		} else {
			if (StringUtils.isNotBlank(type) && "s".equals(type)) {
				wxAccount.setSource(source);
			} else if (StringUtils.isNotBlank(type) && "g".equals(type)) {
				// 获取底下的组织机构
				String groupIds = UserUtils.getSubGroupIds(gid2, source);
				wxAccount.setSource(source);
				wxAccount.setGroupIds(groupIds);
			}
		}

		Page<WxAccount> page = wxAccountService.findPage(new Page<WxAccount>(request, response), wxAccount);
		// 获取组织机构
		for (WxAccount account : page.getList()) {
			GroupInfo group = UserUtils.getGroupInfo(String.valueOf(account.getGroupId()), source);
			account.setGroup(group);
		}

		model.addAttribute("page", page);
		model.addAttribute("type", type);

		if ("2".equals(showType)) {
			return "modules/weixin/wxAccountSelect";
		}
		return "modules/weixin/wxAccountList";
	}

	@RequiresPermissions("weixin:wxAccount:view")
	@RequestMapping("/listSelect")
	public String listSelect(WxAccount wxAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		String source = wxAccount.getSource();
		if (StringUtils.isBlank(source)) {
			// 默认source
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
			List<Urlmap> urlmapList = urlmapService.getUrlmapBySource(null);
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

			if (urlmapList != null && urlmapList.size() > 0) {
				source = urlmapList.get(0).getUrlkey();
			}
			wxAccount.setSource(source);
		}

		Page<WxAccount> page = wxAccountService.findPage(new Page<WxAccount>(request, response), wxAccount);
		// 获取组织机构
		for (WxAccount account : page.getList()) {
			GroupInfo group = UserUtils.getGroupInfo(String.valueOf(account.getGroupId()), source);
			account.setGroup(group);
		}

		model.addAttribute("page", page);
		return "modules/weixin/wxAccountSelect";
	}

	@RequiresPermissions("weixin:wxAccount:view")
	@RequestMapping(value = "form")
	public String form(WxAccount wxAccount, Model model) {
		Map<String, String> map = UserUtils.parseGroupids(wxAccount.getGid());
		if (map != null) {
			String source = map.get("source");
			String type = map.get("type");
			String groupId = map.get("gid");

			wxAccount.setSource(source);
			if ("g".equals(type)) {
				wxAccount.setGroupId(Integer.valueOf(groupId));
			}
		}
		return "modules/weixin/wxAccountForm";
	}

	@RequiresPermissions("weixin:wxAccount:edit")
	@RequestMapping(value = "save")
	public String save(WxAccount wxAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxAccount)) {
			return form(wxAccount, model);
		}

		wxAccountService.save(wxAccount);
		addMessage(redirectAttributes, "保存微信管理成功");

		return "redirect:" + Global.getAdminPath() + "/weixin/wxAccount/list?gid=" + wxAccount.getGid();
	}

	@RequiresPermissions("weixin:wxAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(WxAccount wxAccount, RedirectAttributes redirectAttributes) {
		wxAccountService.delete(wxAccount);
		addMessage(redirectAttributes, "删除微信管理成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxAccount/list?repage";
	}

	/**
	 * 设置默认微信公众号
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("weixin:wxAccount:edit")
	@RequestMapping(value = "setDefaultAccount")
	@ResponseBody
	public String setDefaultAccount(String id, RedirectAttributes redirectAttributes) {
		log.debug("id={}", id);
		wxAccountService.setDefaultAccount(id);
		addMessage(redirectAttributes, "默认微信号设置成功");
		return "true";
	}

	/**
	 * 查询api
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxAccount:view")
	@RequestMapping(value = "wxApiView")
	public String wxApiView(@RequestParam(required = true) String id, Model model, HttpServletRequest request) {
		WxAccount wxAccount = wxAccountService.get(id);
		// 组装回调的地址,用于后台通讯
		String url = Global.getConfig("server.project.url") + request.getContextPath() + "/weixin/deal/" + wxAccount.getToken();
		model.addAttribute("wxAccount", wxAccount);
		model.addAttribute("url", url);
		return "modules/weixin/wxApiView";
	}
}