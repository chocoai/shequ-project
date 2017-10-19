package com.haolinbang.modules.sns.web;

import java.util.Date;

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
import com.haolinbang.modules.sns.entity.WyOption;
import com.haolinbang.modules.sns.entity.WySubject;
import com.haolinbang.modules.sns.service.WyOptionService;
import com.haolinbang.modules.sns.service.WySubjectService;

/**
 * 选项表Controller
 * 
 * @author wxc
 * @version 2017-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyOption")
public class WyOptionController extends BaseController {

	@Autowired
	private WyOptionService wyOptionService;

	@Autowired
	private WySubjectService wySubjectService;

	@ModelAttribute
	public WyOption get(@RequestParam(required = false) String optionid) {
		WyOption entity = null;
		if (StringUtils.isNotEmpty(optionid) && !optionid.equals("0")) {
			entity = wyOptionService.get(optionid);
		}
		if (entity == null) {
			entity = new WyOption();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(WyOption wyOption, HttpServletRequest request, HttpServletResponse response, Model model) {
		Integer subjectid = (Integer) request.getSession().getAttribute("subjectid");
		WyOption entity = null;
		if (subjectid != null && subjectid != 0) {
			entity = wyOptionService.get("" + subjectid);
		}

		String title = entity != null ? entity.getTitle() : "";
		wyOption.setSubjectid(subjectid != null ? subjectid : 0);
		wyOption.setTitle(title);

		Page<WyOption> page = wyOptionService.findPage(new Page<WyOption>(request, response), wyOption);
		model.addAttribute("page", page);
		return "modules/sns/wyOptionList";
	}

	@RequestMapping(value = "form")
	public String form(WyOption wyOption, HttpServletRequest request, Model model, String type) {
		Integer subjectid = (Integer) request.getSession().getAttribute("subjectid");
		WySubject entity = null;
		if (subjectid != null && subjectid != 0) {
			entity = wySubjectService.get("" + subjectid);
		}
		String title = entity != null ? entity.getTitle() : "";
		wyOption.setSubjectid(subjectid != null ? subjectid : 0);
		wyOption.setTitle(title);
		model.addAttribute("wyOption", wyOption);
		model.addAttribute("type", type);
		return "modules/sns/wyOptionForm";
	}

	@RequestMapping(value = "save")
	public String save(WyOption wyOption, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes, String type) {
		Integer classificationid = (Integer) request.getSession().getAttribute("classificationid");
		Integer questionnaireid = (Integer) request.getSession().getAttribute("questionnaireid");
		wyOption.setClassificationid(classificationid != null ? classificationid : 0);
		wyOption.setQuestionnaireid(questionnaireid != null ? questionnaireid : 0);

		if (type != null && type.equals("edit")) {
			wyOption.setId(wyOption.getSubjectid().toString());
			wyOption.setUpdatetime(new Date());
		} else {
			wyOption.setCreatetime(new Date());
			wyOption.setUpdatetime(new Date());
		}
		if (!beanValidator(model, wyOption)) {
			return form(wyOption, request, model, type);
		}
		wyOptionService.save(wyOption);
		addMessage(redirectAttributes, "保存选项表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyOption/?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(WyOption wyOption, RedirectAttributes redirectAttributes) {
		wyOptionService.delete(wyOption);
		addMessage(redirectAttributes, "删除选项表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyOption/?repage";
	}

}