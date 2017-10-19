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
import com.haolinbang.modules.sns.entity.WyActRelation;
import com.haolinbang.modules.sns.service.WyActRelationService;

/**
 * 流程实例对应的关系表Controller
 * @author nlp
 * @version 2017-05-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyActRelation")
public class WyActRelationController extends BaseController {

	@Autowired
	private WyActRelationService wyActRelationService;
	
	@ModelAttribute
	public WyActRelation get(@RequestParam(required=false) String id) {
		WyActRelation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyActRelationService.get(id);
		}
		if (entity == null){
			entity = new WyActRelation();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyActRelation:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyActRelation wyActRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyActRelation> page = wyActRelationService.findPage(new Page<WyActRelation>(request, response), wyActRelation); 
		model.addAttribute("page", page);
		return "modules/sns/wyActRelationList";
	}

	@RequiresPermissions("sns:wyActRelation:view")
	@RequestMapping(value = "form")
	public String form(WyActRelation wyActRelation, Model model) {
		model.addAttribute("wyActRelation", wyActRelation);
		return "modules/sns/wyActRelationForm";
	}

	@RequiresPermissions("sns:wyActRelation:edit")
	@RequestMapping(value = "save")
	public String save(WyActRelation wyActRelation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyActRelation)){
			return form(wyActRelation, model);
		}
		wyActRelationService.save(wyActRelation);
		addMessage(redirectAttributes, "保存流程实例对应的关系表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActRelation/?repage";
	}
	
	@RequiresPermissions("sns:wyActRelation:edit")
	@RequestMapping(value = "delete")
	public String delete(WyActRelation wyActRelation, RedirectAttributes redirectAttributes) {
		wyActRelationService.delete(wyActRelation);
		addMessage(redirectAttributes, "删除流程实例对应的关系表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActRelation/?repage";
	}

}