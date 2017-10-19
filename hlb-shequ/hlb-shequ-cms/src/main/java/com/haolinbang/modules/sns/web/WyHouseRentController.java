package com.haolinbang.modules.sns.web;

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
import com.haolinbang.modules.sns.entity.WyHouseRent;
import com.haolinbang.modules.sns.service.WyHouseRentService;

/**
 * 房屋租售Controller
 * 
 * @author nlp
 * @version 2017-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyHouseRent")
public class WyHouseRentController extends BaseController {

	@Autowired
	private WyHouseRentService wyHouseRentService;

	@ModelAttribute
	public WyHouseRent get(@RequestParam(required = false) String id) {
		WyHouseRent entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyHouseRentService.get(id);
		}
		if (entity == null) {
			entity = new WyHouseRent();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyHouseRent:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyHouseRent wyHouseRent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyHouseRent> page = wyHouseRentService.findPage(new Page<WyHouseRent>(request, response), wyHouseRent);
		model.addAttribute("page", page);
		return "modules/sns/wyHouseRentList";
	}

	@RequiresPermissions("sns:wyHouseRent:view")
	@RequestMapping(value = "form")
	public String form(WyHouseRent wyHouseRent, Model model) {
		model.addAttribute("wyHouseRent", wyHouseRent);
		return "modules/sns/wyHouseRentForm";
	}

	@RequiresPermissions("sns:wyHouseRent:edit")
	@RequestMapping(value = "save")
	public String save(WyHouseRent wyHouseRent, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyHouseRent)) {
			return form(wyHouseRent, model);
		}
		wyHouseRentService.save(wyHouseRent);
		addMessage(redirectAttributes, "保存房屋租售成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyHouseRent/?repage";
	}

	@RequiresPermissions("sns:wyHouseRent:edit")
	@RequestMapping(value = "delete")
	public String delete(WyHouseRent wyHouseRent, RedirectAttributes redirectAttributes) {
		wyHouseRentService.delete(wyHouseRent);
		addMessage(redirectAttributes, "删除房屋租售成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyHouseRent/?repage";
	}

	@RequiresPermissions("sns:wyHouseRent:audit")
	@RequestMapping(value = "audit")
	public String audit(String id, RedirectAttributes redirectAttributes) {
		boolean b = wyHouseRentService.audit(id);
		if (b) {
			addMessage(redirectAttributes, "审核房屋租售信息成功");
		} else {
			addMessage(redirectAttributes, "审核房屋租售信息失败");
		}
		return "redirect:" + Global.getAdminPath() + "/sns/wyHouseRent/?repage";
	}
	
	
	

}