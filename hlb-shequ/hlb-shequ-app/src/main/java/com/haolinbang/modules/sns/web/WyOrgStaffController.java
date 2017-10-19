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
import com.haolinbang.modules.sns.entity.WyOrgStaff;
import com.haolinbang.modules.sns.service.WyOrgStaffService;

/**
 * 豪龙内部员工表Controller
 * @author nlp
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyOrgStaff")
public class WyOrgStaffController extends BaseController {

	@Autowired
	private WyOrgStaffService wyOrgStaffService;
	
	@ModelAttribute
	public WyOrgStaff get(@RequestParam(required=false) String id) {
		WyOrgStaff entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyOrgStaffService.get(id);
		}
		if (entity == null){
			entity = new WyOrgStaff();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyOrgStaff:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyOrgStaff wyOrgStaff, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyOrgStaff> page = wyOrgStaffService.findPage(new Page<WyOrgStaff>(request, response), wyOrgStaff); 
		model.addAttribute("page", page);
		return "modules/sns/wyOrgStaffList";
	}

	@RequiresPermissions("sns:wyOrgStaff:view")
	@RequestMapping(value = "form")
	public String form(WyOrgStaff wyOrgStaff, Model model) {
		model.addAttribute("wyOrgStaff", wyOrgStaff);
		return "modules/sns/wyOrgStaffForm";
	}

	@RequiresPermissions("sns:wyOrgStaff:edit")
	@RequestMapping(value = "save")
	public String save(WyOrgStaff wyOrgStaff, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyOrgStaff)){
			return form(wyOrgStaff, model);
		}
		wyOrgStaffService.save(wyOrgStaff);
		addMessage(redirectAttributes, "保存豪龙内部员工表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyOrgStaff/?repage";
	}
	
	@RequiresPermissions("sns:wyOrgStaff:edit")
	@RequestMapping(value = "delete")
	public String delete(WyOrgStaff wyOrgStaff, RedirectAttributes redirectAttributes) {
		wyOrgStaffService.delete(wyOrgStaff);
		addMessage(redirectAttributes, "删除豪龙内部员工表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyOrgStaff/?repage";
	}

}