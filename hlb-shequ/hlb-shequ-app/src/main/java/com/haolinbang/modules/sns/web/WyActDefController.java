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
import com.haolinbang.modules.sns.entity.WyActDef;
import com.haolinbang.modules.sns.service.WyActDefService;

/**
 * 工作流程定义表Controller
 * @author nlp
 * @version 2017-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyActDef")
public class WyActDefController extends BaseController {

	@Autowired
	private WyActDefService wyActDefService;
	
	@ModelAttribute
	public WyActDef get(@RequestParam(required=false) String id) {
		WyActDef entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyActDefService.get(id);
		}
		if (entity == null){
			entity = new WyActDef();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyActDef:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyActDef wyActDef, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyActDef> page = wyActDefService.findPage(new Page<WyActDef>(request, response), wyActDef); 
		model.addAttribute("page", page);
		return "modules/sns/wyActDefList";
	}

	@RequiresPermissions("sns:wyActDef:view")
	@RequestMapping(value = "form")
	public String form(WyActDef wyActDef, Model model) {
		model.addAttribute("wyActDef", wyActDef);
		return "modules/sns/wyActDefForm";
	}

	@RequiresPermissions("sns:wyActDef:edit")
	@RequestMapping(value = "save")
	public String save(WyActDef wyActDef, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyActDef)){
			return form(wyActDef, model);
		}
		wyActDefService.save(wyActDef);
		addMessage(redirectAttributes, "保存工作流程定义表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActDef/?repage";
	}
	
	@RequiresPermissions("sns:wyActDef:edit")
	@RequestMapping(value = "delete")
	public String delete(WyActDef wyActDef, RedirectAttributes redirectAttributes) {
		wyActDefService.delete(wyActDef);
		addMessage(redirectAttributes, "删除工作流程定义表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActDef/?repage";
	}

}