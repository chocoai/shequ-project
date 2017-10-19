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
import com.haolinbang.modules.sns.entity.WyReBizActInstance;
import com.haolinbang.modules.sns.service.WyReBizActInstanceService;

/**
 * 具体流程引用Controller
 * @author nlp
 * @version 2017-09-11
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyReBizActInstance")
public class WyReBizActInstanceController extends BaseController {

	@Autowired
	private WyReBizActInstanceService wyReBizActInstanceService;
	
	@ModelAttribute
	public WyReBizActInstance get(@RequestParam(required=false) String id) {
		WyReBizActInstance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyReBizActInstanceService.get(id);
		}
		if (entity == null){
			entity = new WyReBizActInstance();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyReBizActInstance:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyReBizActInstance wyReBizActInstance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyReBizActInstance> page = wyReBizActInstanceService.findPage(new Page<WyReBizActInstance>(request, response), wyReBizActInstance); 
		model.addAttribute("page", page);
		return "modules/sns/wyReBizActInstanceList";
	}

	@RequiresPermissions("sns:wyReBizActInstance:view")
	@RequestMapping(value = "form")
	public String form(WyReBizActInstance wyReBizActInstance, Model model) {
		model.addAttribute("wyReBizActInstance", wyReBizActInstance);
		return "modules/sns/wyReBizActInstanceForm";
	}

	@RequiresPermissions("sns:wyReBizActInstance:edit")
	@RequestMapping(value = "save")
	public String save(WyReBizActInstance wyReBizActInstance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyReBizActInstance)){
			return form(wyReBizActInstance, model);
		}
		wyReBizActInstanceService.save(wyReBizActInstance);
		addMessage(redirectAttributes, "保存具体流程引用成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyReBizActInstance/?repage";
	}
	
	@RequiresPermissions("sns:wyReBizActInstance:edit")
	@RequestMapping(value = "delete")
	public String delete(WyReBizActInstance wyReBizActInstance, RedirectAttributes redirectAttributes) {
		wyReBizActInstanceService.delete(wyReBizActInstance);
		addMessage(redirectAttributes, "删除具体流程引用成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyReBizActInstance/?repage";
	}

}