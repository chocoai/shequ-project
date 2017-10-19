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
import com.haolinbang.modules.sns.entity.WyNoticeRelation;
import com.haolinbang.modules.sns.service.WyNoticeRelationService;

/**
 * 不同通知处理方法Controller
 * @author nlp
 * @version 2017-07-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyNoticeRelation")
public class WyNoticeRelationController extends BaseController {

	@Autowired
	private WyNoticeRelationService wyNoticeRelationService;
	
	@ModelAttribute
	public WyNoticeRelation get(@RequestParam(required=false) String id) {
		WyNoticeRelation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyNoticeRelationService.get(id);
		}
		if (entity == null){
			entity = new WyNoticeRelation();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyNoticeRelation:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyNoticeRelation wyNoticeRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyNoticeRelation> page = wyNoticeRelationService.findPage(new Page<WyNoticeRelation>(request, response), wyNoticeRelation); 
		model.addAttribute("page", page);
		return "modules/sns/wyNoticeRelationList";
	}

	@RequiresPermissions("sns:wyNoticeRelation:view")
	@RequestMapping(value = "form")
	public String form(WyNoticeRelation wyNoticeRelation, Model model) {
		model.addAttribute("wyNoticeRelation", wyNoticeRelation);
		return "modules/sns/wyNoticeRelationForm";
	}

	@RequiresPermissions("sns:wyNoticeRelation:edit")
	@RequestMapping(value = "save")
	public String save(WyNoticeRelation wyNoticeRelation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyNoticeRelation)){
			return form(wyNoticeRelation, model);
		}
		wyNoticeRelationService.save(wyNoticeRelation);
		addMessage(redirectAttributes, "保存不同通知处理方法成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyNoticeRelation/?repage";
	}
	
	@RequiresPermissions("sns:wyNoticeRelation:edit")
	@RequestMapping(value = "delete")
	public String delete(WyNoticeRelation wyNoticeRelation, RedirectAttributes redirectAttributes) {
		wyNoticeRelationService.delete(wyNoticeRelation);
		addMessage(redirectAttributes, "删除不同通知处理方法成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyNoticeRelation/?repage";
	}

}