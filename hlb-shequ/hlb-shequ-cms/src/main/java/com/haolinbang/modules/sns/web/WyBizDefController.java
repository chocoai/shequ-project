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
import com.haolinbang.modules.sns.entity.WyBizDef;
import com.haolinbang.modules.sns.service.WyBizDefService;

/**
 * 业务功能定义Controller
 * @author nlp
 * @version 2017-09-11
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyBizDef")
public class WyBizDefController extends BaseController {

	@Autowired
	private WyBizDefService wyBizDefService;
	
	@ModelAttribute
	public WyBizDef get(@RequestParam(required=false) String id) {
		WyBizDef entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyBizDefService.get(id);
		}
		if (entity == null){
			entity = new WyBizDef();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyBizDef:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyBizDef wyBizDef, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyBizDef> page = wyBizDefService.findPage(new Page<WyBizDef>(request, response), wyBizDef); 
		model.addAttribute("page", page);
		return "modules/sns/wyBizDefList";
	}

	@RequiresPermissions("sns:wyBizDef:view")
	@RequestMapping(value = "form")
	public String form(WyBizDef wyBizDef, Model model) {
		model.addAttribute("wyBizDef", wyBizDef);
		return "modules/sns/wyBizDefForm";
	}

	@RequiresPermissions("sns:wyBizDef:edit")
	@RequestMapping(value = "save")
	public String save(WyBizDef wyBizDef, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyBizDef)){
			return form(wyBizDef, model);
		}
		wyBizDefService.save(wyBizDef);
		addMessage(redirectAttributes, "保存业务功能定义成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyBizDef/?repage";
	}
	
	@RequiresPermissions("sns:wyBizDef:edit")
	@RequestMapping(value = "delete")
	public String delete(WyBizDef wyBizDef, RedirectAttributes redirectAttributes) {
		wyBizDefService.delete(wyBizDef);
		addMessage(redirectAttributes, "删除业务功能定义成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyBizDef/?repage";
	}

}