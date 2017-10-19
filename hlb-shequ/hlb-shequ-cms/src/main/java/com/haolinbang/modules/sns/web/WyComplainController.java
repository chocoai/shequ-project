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
import com.haolinbang.modules.sns.entity.WyComplain;
import com.haolinbang.modules.sns.service.WyComplainService;

/**
 * 物业投诉表Controller
 * @author wxc
 * @version 2017-07-04
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyComplain")
public class WyComplainController extends BaseController {

	@Autowired
	private WyComplainService wyComplainService;
	
	@ModelAttribute
	public WyComplain get(@RequestParam(required=false) String id) {
		WyComplain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyComplainService.get(id);
		}
		if (entity == null){
			entity = new WyComplain();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyComplain:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyComplain wyComplain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyComplain> page = wyComplainService.findPage(new Page<WyComplain>(request, response), wyComplain); 
		model.addAttribute("page", page);
		return "modules/sns/wyComplainList";
	}

	@RequiresPermissions("sns:wyComplain:view")
	@RequestMapping(value = "form")
	public String form(WyComplain wyComplain, Model model) {
		model.addAttribute("wyComplain", wyComplain);
		return "modules/sns/wyComplainForm";
	}

	@RequiresPermissions("sns:wyComplain:edit")
	@RequestMapping(value = "save")
	public String save(WyComplain wyComplain, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyComplain)){
			return form(wyComplain, model);
		}
		wyComplainService.save(wyComplain);
		addMessage(redirectAttributes, "保存物业投诉表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyComplain/?repage";
	}
	
	@RequiresPermissions("sns:wyComplain:edit")
	@RequestMapping(value = "delete")
	public String delete(WyComplain wyComplain, RedirectAttributes redirectAttributes) {
		wyComplainService.delete(wyComplain);
		addMessage(redirectAttributes, "删除物业投诉表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyComplain/?repage";
	}

}