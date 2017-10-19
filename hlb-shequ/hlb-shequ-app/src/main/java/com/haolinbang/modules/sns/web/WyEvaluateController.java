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
import com.haolinbang.modules.sns.entity.WyEvaluate;
import com.haolinbang.modules.sns.service.WyEvaluateService;

/**
 * 物业评论表Controller
 * @author nlp
 * @version 2017-05-11
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyEvaluate")
public class WyEvaluateController extends BaseController {

	@Autowired
	private WyEvaluateService wyEvaluateService;
	
	@ModelAttribute
	public WyEvaluate get(@RequestParam(required=false) String id) {
		WyEvaluate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyEvaluateService.get(id);
		}
		if (entity == null){
			entity = new WyEvaluate();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyEvaluate:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyEvaluate wyEvaluate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyEvaluate> page = wyEvaluateService.findPage(new Page<WyEvaluate>(request, response), wyEvaluate); 
		model.addAttribute("page", page);
		return "modules/sns/wyEvaluateList";
	}

	@RequiresPermissions("sns:wyEvaluate:view")
	@RequestMapping(value = "form")
	public String form(WyEvaluate wyEvaluate, Model model) {
		model.addAttribute("wyEvaluate", wyEvaluate);
		return "modules/sns/wyEvaluateForm";
	}

	@RequiresPermissions("sns:wyEvaluate:edit")
	@RequestMapping(value = "save")
	public String save(WyEvaluate wyEvaluate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyEvaluate)){
			return form(wyEvaluate, model);
		}
		wyEvaluateService.save(wyEvaluate);
		addMessage(redirectAttributes, "保存物业评论表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyEvaluate/?repage";
	}
	
	@RequiresPermissions("sns:wyEvaluate:edit")
	@RequestMapping(value = "delete")
	public String delete(WyEvaluate wyEvaluate, RedirectAttributes redirectAttributes) {
		wyEvaluateService.delete(wyEvaluate);
		addMessage(redirectAttributes, "删除物业评论表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyEvaluate/?repage";
	}

}