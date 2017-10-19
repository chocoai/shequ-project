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
import com.haolinbang.modules.sns.entity.WyApprove;
import com.haolinbang.modules.sns.service.WyApproveService;

/**
 * 工作流审批记录表Controller
 * @author nlp
 * @version 2017-06-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyApprove")
public class WyApproveController extends BaseController {

	@Autowired
	private WyApproveService wyApproveService;
	
	@ModelAttribute
	public WyApprove get(@RequestParam(required=false) String id) {
		WyApprove entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyApproveService.get(id);
		}
		if (entity == null){
			entity = new WyApprove();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyApprove:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyApprove wyApprove, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyApprove> page = wyApproveService.findPage(new Page<WyApprove>(request, response), wyApprove); 
		model.addAttribute("page", page);
		return "modules/sns/wyApproveList";
	}

	@RequiresPermissions("sns:wyApprove:view")
	@RequestMapping(value = "form")
	public String form(WyApprove wyApprove, Model model) {
		model.addAttribute("wyApprove", wyApprove);
		return "modules/sns/wyApproveForm";
	}

	@RequiresPermissions("sns:wyApprove:edit")
	@RequestMapping(value = "save")
	public String save(WyApprove wyApprove, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyApprove)){
			return form(wyApprove, model);
		}
		wyApproveService.save(wyApprove);
		addMessage(redirectAttributes, "保存工作流审批记录表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyApprove/?repage";
	}
	
	@RequiresPermissions("sns:wyApprove:edit")
	@RequestMapping(value = "delete")
	public String delete(WyApprove wyApprove, RedirectAttributes redirectAttributes) {
		wyApproveService.delete(wyApprove);
		addMessage(redirectAttributes, "删除工作流审批记录表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyApprove/?repage";
	}

}