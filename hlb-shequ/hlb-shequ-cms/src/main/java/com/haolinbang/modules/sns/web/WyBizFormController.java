package com.haolinbang.modules.sns.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
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
import com.haolinbang.modules.sns.entity.WyBizDef;
import com.haolinbang.modules.sns.entity.WyBizForm;
import com.haolinbang.modules.sns.service.WyBizDefService;
import com.haolinbang.modules.sns.service.WyBizFormService;

/**
 * 业务表单定义Controller
 * 
 * @author nlp
 * @version 2017-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyBizForm")
public class WyBizFormController extends BaseController {

	@Autowired
	private WyBizFormService wyBizFormService;

	@Autowired
	private WyBizDefService wyBizDefService;

	@ModelAttribute
	public WyBizForm get(@RequestParam(required = false) String id) {
		WyBizForm entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyBizFormService.get(id);
		}
		if (entity == null) {
			entity = new WyBizForm();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyBizForm:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyBizForm wyBizForm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyBizForm> page = wyBizFormService.findPage(new Page<WyBizForm>(request, response), wyBizForm);
		// 查询业务定义类型
		WyBizDef wyBizDef = new WyBizDef();
		List<WyBizDef> wyBizDefList = wyBizDefService.findList(wyBizDef);

		
		model.addAttribute("page", page);
		model.addAttribute("wyBizDefList", wyBizDefList);
		return "modules/sns/wyBizFormList";
	}

	@RequiresPermissions("sns:wyBizForm:view")
	@RequestMapping(value = "form")
	public String form(WyBizForm wyBizForm, Model model) {

		// 查询业务类型
		WyBizDef wyBizDef = new WyBizDef();
		List<WyBizDef> wyBizDefList = wyBizDefService.findList(wyBizDef);
		
		model.addAttribute("wyBizDefList", wyBizDefList);

		model.addAttribute("wyBizForm", wyBizForm);
		return "modules/sns/wyBizFormForm";
	}

	@RequiresPermissions("sns:wyBizForm:edit")
	@RequestMapping(value = "save")
	public String save(WyBizForm wyBizForm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyBizForm)) {
			return form(wyBizForm, model);
		}
		// 将html代码转码
		String formHtml = StringEscapeUtils.unescapeHtml4(wyBizForm.getFormHtml());
		wyBizForm.setFormHtml(formHtml);

		wyBizFormService.save(wyBizForm);
		addMessage(redirectAttributes, "保存业务表单定义成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyBizForm/?repage";
	}

	@RequiresPermissions("sns:wyBizForm:edit")
	@RequestMapping(value = "delete")
	public String delete(WyBizForm wyBizForm, RedirectAttributes redirectAttributes) {
		wyBizFormService.delete(wyBizForm);
		addMessage(redirectAttributes, "删除业务表单定义成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyBizForm/?repage";
	}

	/**
	 * 显示表单编辑器
	 * 
	 * @param wyBizForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/formDesigner")
	public String formDesigner(String id, Model model) {
		WyBizForm wyBizForm = wyBizFormService.get(id);

		model.addAttribute("wyBizForm", wyBizForm);

		return "modules/sns/formDesigner2";
	}

}