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
import com.haolinbang.modules.sns.entity.WyBizHandler;
import com.haolinbang.modules.sns.service.WyBizHandlerService;

/**
 * 业务处理器Controller
 * @author nlp
 * @version 2017-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyBizHandler")
public class WyBizHandlerController extends BaseController {

	@Autowired
	private WyBizHandlerService wyBizHandlerService;
	
	@ModelAttribute
	public WyBizHandler get(@RequestParam(required=false) String id) {
		WyBizHandler entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyBizHandlerService.get(id);
		}
		if (entity == null){
			entity = new WyBizHandler();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyBizHandler:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyBizHandler wyBizHandler, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyBizHandler> page = wyBizHandlerService.findPage(new Page<WyBizHandler>(request, response), wyBizHandler); 
		model.addAttribute("page", page);
		return "modules/sns/wyBizHandlerList";
	}

	@RequiresPermissions("sns:wyBizHandler:view")
	@RequestMapping(value = "form")
	public String form(WyBizHandler wyBizHandler, Model model) {
		model.addAttribute("wyBizHandler", wyBizHandler);
		return "modules/sns/wyBizHandlerForm";
	}

	@RequiresPermissions("sns:wyBizHandler:edit")
	@RequestMapping(value = "save")
	public String save(WyBizHandler wyBizHandler, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyBizHandler)){
			return form(wyBizHandler, model);
		}
		wyBizHandlerService.save(wyBizHandler);
		addMessage(redirectAttributes, "保存业务处理器成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyBizHandler/?repage";
	}
	
	@RequiresPermissions("sns:wyBizHandler:edit")
	@RequestMapping(value = "delete")
	public String delete(WyBizHandler wyBizHandler, RedirectAttributes redirectAttributes) {
		wyBizHandlerService.delete(wyBizHandler);
		addMessage(redirectAttributes, "删除业务处理器成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyBizHandler/?repage";
	}

}