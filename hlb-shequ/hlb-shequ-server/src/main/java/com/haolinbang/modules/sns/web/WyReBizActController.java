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
import com.haolinbang.modules.sns.entity.WyReBizAct;
import com.haolinbang.modules.sns.service.WyReBizActService;

/**
 * 工作流和实际的业务对应关系表Controller
 * @author nlp
 * @version 2017-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyReBizAct")
public class WyReBizActController extends BaseController {

	@Autowired
	private WyReBizActService wyReBizActService;
	
	@ModelAttribute
	public WyReBizAct get(@RequestParam(required=false) String id) {
		WyReBizAct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyReBizActService.get(id);
		}
		if (entity == null){
			entity = new WyReBizAct();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyReBizAct:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyReBizAct wyReBizAct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyReBizAct> page = wyReBizActService.findPage(new Page<WyReBizAct>(request, response), wyReBizAct); 
		model.addAttribute("page", page);
		return "modules/sns/wyReBizActList";
	}

	@RequiresPermissions("sns:wyReBizAct:view")
	@RequestMapping(value = "form")
	public String form(WyReBizAct wyReBizAct, Model model) {
		model.addAttribute("wyReBizAct", wyReBizAct);
		return "modules/sns/wyReBizActForm";
	}

	@RequiresPermissions("sns:wyReBizAct:edit")
	@RequestMapping(value = "save")
	public String save(WyReBizAct wyReBizAct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyReBizAct)){
			return form(wyReBizAct, model);
		}
		wyReBizActService.save(wyReBizAct);
		addMessage(redirectAttributes, "保存工作流和实际的业务对应关系表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyReBizAct/?repage";
	}
	
	@RequiresPermissions("sns:wyReBizAct:edit")
	@RequestMapping(value = "delete")
	public String delete(WyReBizAct wyReBizAct, RedirectAttributes redirectAttributes) {
		wyReBizActService.delete(wyReBizAct);
		addMessage(redirectAttributes, "删除工作流和实际的业务对应关系表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyReBizAct/?repage";
	}

}