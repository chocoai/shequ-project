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
import com.haolinbang.modules.sns.entity.WyActCandidateDetail;
import com.haolinbang.modules.sns.service.WyActCandidateDetailService;

/**
 * 具体人员明细表Controller
 * @author nlp
 * @version 2017-06-20
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyActCandidateDetail")
public class WyActCandidateDetailController extends BaseController {

	@Autowired
	private WyActCandidateDetailService wyActCandidateDetailService;
	
	@ModelAttribute
	public WyActCandidateDetail get(@RequestParam(required=false) String id) {
		WyActCandidateDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyActCandidateDetailService.get(id);
		}
		if (entity == null){
			entity = new WyActCandidateDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyActCandidateDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyActCandidateDetail wyActCandidateDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyActCandidateDetail> page = wyActCandidateDetailService.findPage(new Page<WyActCandidateDetail>(request, response), wyActCandidateDetail); 
		model.addAttribute("page", page);
		return "modules/sns/wyActCandidateDetailList";
	}

	@RequiresPermissions("sns:wyActCandidateDetail:view")
	@RequestMapping(value = "form")
	public String form(WyActCandidateDetail wyActCandidateDetail, Model model) {
		model.addAttribute("wyActCandidateDetail", wyActCandidateDetail);
		return "modules/sns/wyActCandidateDetailForm";
	}

	@RequiresPermissions("sns:wyActCandidateDetail:edit")
	@RequestMapping(value = "save")
	public String save(WyActCandidateDetail wyActCandidateDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyActCandidateDetail)){
			return form(wyActCandidateDetail, model);
		}
		wyActCandidateDetailService.save(wyActCandidateDetail);
		addMessage(redirectAttributes, "保存具体人员明细表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActCandidateDetail/?repage";
	}
	
	@RequiresPermissions("sns:wyActCandidateDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(WyActCandidateDetail wyActCandidateDetail, RedirectAttributes redirectAttributes) {
		wyActCandidateDetailService.delete(wyActCandidateDetail);
		addMessage(redirectAttributes, "删除具体人员明细表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActCandidateDetail/?repage";
	}

}