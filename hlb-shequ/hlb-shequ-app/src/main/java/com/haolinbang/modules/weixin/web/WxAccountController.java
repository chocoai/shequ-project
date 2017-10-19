package com.haolinbang.modules.weixin.web;

import java.util.List;

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
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.entity.WyBuilding;
import com.haolinbang.modules.sns.service.WyBuildingService;
import com.haolinbang.modules.sys.entity.Area;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.service.AreaService;
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
	private AreaService areaService;

	@Autowired
	private WyBuildingService wyBuildingService;

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

	@RequiresPermissions("weixin:wxAccount:view")
	@RequestMapping(value = { "list", "" })
	public String list(WxAccount wxAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		GroupInfo group = user.getGroupInfo();
		wxAccount.setSource(user.getSource());
		wxAccount.setGroupId(Integer.valueOf(group.getGroupId()));
		Page<WxAccount> page = wxAccountService.findPage(new Page<WxAccount>(request, response), wxAccount);

		model.addAttribute("page", page);
		return "modules/weixin/wxAccountList";
	}

	@RequiresPermissions("weixin:wxAccount:view")
	@RequestMapping(value = "form")
	public String form(WxAccount wxAccount, Model model) {
		wxAccount.setArea(new Area());
		model.addAttribute("wxAccount", wxAccount);
		List<Area> areas = areaService.findPovinces();
		log.debug("areas={}", areas);
		model.addAttribute("areas", areas);

		User user = UserUtils.getUser();
		wxAccount.setSource(user.getSource());

		String wyids = wxAccount.getWyid();
		if (StringUtils.isNotBlank(wyids)) {
			String[] wyidArr = wyids.split(",");
			String wymc = "";
			if (wyids != null) {
				for (String id : wyidArr) {
					WyBuilding wy = wyBuildingService.get(id);
					if (wy != null) {
						wymc += "," + wy.getWymc();
					}
				}
				if (wymc.startsWith(",")) {
					wymc = wymc.substring(1);
					wxAccount.setWymc(wymc);
				}
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
		User user = UserUtils.getUser();
		wxAccount.setSource(user.getSource());

		wxAccountService.save(wxAccount);
		addMessage(redirectAttributes, "保存微信管理成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxAccount/?repage";
	}

	@RequiresPermissions("weixin:wxAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(WxAccount wxAccount, RedirectAttributes redirectAttributes) {
		wxAccountService.delete(wxAccount);
		addMessage(redirectAttributes, "删除微信管理成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxAccount/?repage";
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

	/**
	 * 用于选择账号页面
	 * 
	 * @param wxAccount
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxAccount:view")
	@RequestMapping("/select")
	public String select(WxAccount wxAccount, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<WxAccount> page = wxAccountService.findPage(new Page<WxAccount>(request, response), wxAccount);

		model.addAttribute("page", page);
		return "modules/weixin/wxAccountSelect";
	}
}