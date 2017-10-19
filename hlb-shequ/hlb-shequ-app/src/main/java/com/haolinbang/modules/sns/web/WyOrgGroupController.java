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
import com.haolinbang.modules.sns.entity.WyOrgGroup;
import com.haolinbang.modules.sns.service.WyOrgGroupService;

/**
 * 豪龙组织机构Controller
 * @author nlp
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyOrgGroup")
public class WyOrgGroupController extends BaseController {

	@Autowired
	private WyOrgGroupService wyOrgGroupService;
	
	@ModelAttribute
	public WyOrgGroup get(@RequestParam(required=false) String id) {
		WyOrgGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyOrgGroupService.get(id);
		}
		if (entity == null){
			entity = new WyOrgGroup();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyOrgGroup:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyOrgGroup wyOrgGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyOrgGroup> page = wyOrgGroupService.findPage(new Page<WyOrgGroup>(request, response), wyOrgGroup); 
		model.addAttribute("page", page);
		return "modules/sns/wyOrgGroupList";
	}

	@RequiresPermissions("sns:wyOrgGroup:view")
	@RequestMapping(value = "form")
	public String form(WyOrgGroup wyOrgGroup, Model model) {
		model.addAttribute("wyOrgGroup", wyOrgGroup);
		return "modules/sns/wyOrgGroupForm";
	}

	@RequiresPermissions("sns:wyOrgGroup:edit")
	@RequestMapping(value = "save")
	public String save(WyOrgGroup wyOrgGroup, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyOrgGroup)){
			return form(wyOrgGroup, model);
		}
		wyOrgGroupService.save(wyOrgGroup);
		addMessage(redirectAttributes, "保存豪龙组织机构成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyOrgGroup/?repage";
	}
	
	@RequiresPermissions("sns:wyOrgGroup:edit")
	@RequestMapping(value = "delete")
	public String delete(WyOrgGroup wyOrgGroup, RedirectAttributes redirectAttributes) {
		wyOrgGroupService.delete(wyOrgGroup);
		addMessage(redirectAttributes, "删除豪龙组织机构成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyOrgGroup/?repage";
	}

}