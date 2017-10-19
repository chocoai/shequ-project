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
import com.haolinbang.modules.sns.entity.WyActJob;
import com.haolinbang.modules.sns.service.WyActJobService;

/**
 * 工作流定时任务时间表Controller
 * @author nlp
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyActJob")
public class WyActJobController extends BaseController {

	@Autowired
	private WyActJobService wyActJobService;
	
	@ModelAttribute
	public WyActJob get(@RequestParam(required=false) String id) {
		WyActJob entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyActJobService.get(id);
		}
		if (entity == null){
			entity = new WyActJob();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyActJob:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyActJob wyActJob, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyActJob> page = wyActJobService.findPage(new Page<WyActJob>(request, response), wyActJob); 
		model.addAttribute("page", page);
		return "modules/sns/wyActJobList";
	}

	@RequiresPermissions("sns:wyActJob:view")
	@RequestMapping(value = "form")
	public String form(WyActJob wyActJob, Model model) {
		model.addAttribute("wyActJob", wyActJob);
		return "modules/sns/wyActJobForm";
	}

	@RequiresPermissions("sns:wyActJob:edit")
	@RequestMapping(value = "save")
	public String save(WyActJob wyActJob, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyActJob)){
			return form(wyActJob, model);
		}
		wyActJobService.save(wyActJob);
		addMessage(redirectAttributes, "保存工作流定时任务时间表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActJob/?repage";
	}
	
	@RequiresPermissions("sns:wyActJob:edit")
	@RequestMapping(value = "delete")
	public String delete(WyActJob wyActJob, RedirectAttributes redirectAttributes) {
		wyActJobService.delete(wyActJob);
		addMessage(redirectAttributes, "删除工作流定时任务时间表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyActJob/?repage";
	}

}