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
import com.haolinbang.modules.sns.entity.WyInstCandidate;
import com.haolinbang.modules.sns.service.WyInstCandidateService;

/**
 * 运行期间待办人Controller
 * @author nlp
 * @version 2017-06-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyInstCandidate")
public class WyInstCandidateController extends BaseController {

	@Autowired
	private WyInstCandidateService wyInstCandidateService;
	
	@ModelAttribute
	public WyInstCandidate get(@RequestParam(required=false) String id) {
		WyInstCandidate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyInstCandidateService.get(id);
		}
		if (entity == null){
			entity = new WyInstCandidate();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyInstCandidate:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyInstCandidate wyInstCandidate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyInstCandidate> page = wyInstCandidateService.findPage(new Page<WyInstCandidate>(request, response), wyInstCandidate); 
		model.addAttribute("page", page);
		return "modules/sns/wyInstCandidateList";
	}

	@RequiresPermissions("sns:wyInstCandidate:view")
	@RequestMapping(value = "form")
	public String form(WyInstCandidate wyInstCandidate, Model model) {
		model.addAttribute("wyInstCandidate", wyInstCandidate);
		return "modules/sns/wyInstCandidateForm";
	}

	@RequiresPermissions("sns:wyInstCandidate:edit")
	@RequestMapping(value = "save")
	public String save(WyInstCandidate wyInstCandidate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyInstCandidate)){
			return form(wyInstCandidate, model);
		}
		wyInstCandidateService.save(wyInstCandidate);
		addMessage(redirectAttributes, "保存运行期间待办人成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyInstCandidate/?repage";
	}
	
	@RequiresPermissions("sns:wyInstCandidate:edit")
	@RequestMapping(value = "delete")
	public String delete(WyInstCandidate wyInstCandidate, RedirectAttributes redirectAttributes) {
		wyInstCandidateService.delete(wyInstCandidate);
		addMessage(redirectAttributes, "删除运行期间待办人成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyInstCandidate/?repage";
	}

}