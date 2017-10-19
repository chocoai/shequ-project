package com.haolinbang.modules.sns.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.dto.WyActFormDto;
import com.haolinbang.modules.sns.entity.WyActDef;
import com.haolinbang.modules.sns.entity.WyActForm;
import com.haolinbang.modules.sns.entity.WyBizForm;
import com.haolinbang.modules.sns.service.WyActDefService;
import com.haolinbang.modules.sns.service.WyActFormService;
import com.haolinbang.modules.sns.service.WyBizFormService;

/**
 * 流程表单管理Controller
 * 
 * @author nlp
 * @version 2017-06-01
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyActForm")
public class WyActFormController extends BaseController {

	@Autowired
	private WyActFormService wyActFormService;

	@Autowired
	private WyBizFormService wyBizFormService;

	@Autowired
	private WyActDefService wyActDefService;

	@ModelAttribute
	public WyActForm get(@RequestParam(required = false) String id) {
		WyActForm entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyActFormService.get(id);
		}
		if (entity == null) {
			entity = new WyActForm();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyActForm:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyActForm wyActForm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyActForm> page = wyActFormService.findPage(new Page<WyActForm>(request, response), wyActForm);
		model.addAttribute("page", page);
		return "modules/sns/wyActFormList";
	}

	@RequiresPermissions("sns:wyActForm:view")
	@RequestMapping(value = "form")
	public String form(WyActForm wyActForm, Model model) {
		model.addAttribute("wyActForm", wyActForm);
		return "modules/sns/wyActFormForm";
	}

	@RequiresPermissions("sns:wyActForm:edit")
	@RequestMapping(value = "save")
	public String save(WyActForm wyActForm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyActForm)) {
			return form(wyActForm, model);
		}
		wyActFormService.save(wyActForm);
		addMessage(redirectAttributes, "保存流程表单管理成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyActForm/?repage";
	}

	@RequiresPermissions("sns:wyActForm:edit")
	@RequestMapping(value = "delete")
	public String delete(WyActForm wyActForm, RedirectAttributes redirectAttributes) {
		wyActFormService.delete(wyActForm);
		addMessage(redirectAttributes, "删除流程表单管理成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyActForm/?repage";
	}

	/**
	 * 显示表单页面配置
	 * 
	 * @param wyActForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showForm")
	public String showForm(WyActFormDto wyActFormDto, Model model) {
		// 通过流程定义id获取流程信息
		WyActDef actDef = wyActDefService.getWyActDefByProcDefId(wyActFormDto.getProcDefinitionId());
		if (actDef == null) {
			throw new RuntimeException("获取工作流程配置信息出现异常");
		}

		String type = actDef.getCategory();
		List<WyBizForm> wyBizFormList = wyBizFormService.getWyBizFormListByType(type);

		List<WyActForm> wyActFormList = wyActFormService.getWyActFormListByprocDefIdAndTaskid(wyActFormDto.getProcDefinitionId(), wyActFormDto.getActivitiId());
		if (wyActFormList != null && !wyActFormList.isEmpty()) {
			WyBizForm wyBizForm = wyBizFormList.get(0);
			wyActFormDto.setFormId(wyBizForm.getFormId());
		}

		WyActForm wyActForm = wyActFormService.getWyActFormByProcDefIdAndTaskId(wyActFormDto.getProcDefinitionId(), wyActFormDto.getActivitiId());
		if (wyActForm != null) {
			wyActFormDto.setFormId(wyActForm.getFormId());
		}

		model.addAttribute("wyBizFormList", wyBizFormList);
		model.addAttribute("wyActFormDto", wyActFormDto);

		return "modules/sns/wyActShowForm";
	}

	/**
	 * 
	 * @param wyActForm
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveForm")
	public WeJson saveForm(WyActFormDto wyActFormDto, Model model) {
		try {
			String procDefId = wyActFormDto.getProcDefinitionId();
			String activitiId = wyActFormDto.getActivitiId();

			WyActForm wyActForm = wyActFormService.getWyActFormByProcDefIdAndTaskId(procDefId, activitiId);
			if (wyActForm == null) {
				wyActForm = new WyActForm();
				wyActForm.setProcDefId(procDefId);
				wyActForm.setTaskId(activitiId);
			}

			// 查询表单信息设置表单信息
			WyBizForm wyBizForm = wyBizFormService.get(wyActFormDto.getFormId());
			wyActForm.setFormUrl(wyBizForm.getFormUrl());
			wyActForm.setFormName(wyBizForm.getFormName());
			wyActForm.setFormId(wyActFormDto.getFormId());

			wyActFormService.save(wyActForm);

			return WeJson.success("保存成功");
		} catch (Exception e) {
			logger.error("保存出错={}", e);
			return WeJson.fail("保存失败");
		}
	}

	/**
	 * 显示表单页面配置
	 * 
	 * @param wyActForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showForm2")
	public String showForm2(WyActFormDto wyActFormDto, Model model) {
		// 从数据库中查询对应的流程
		WyActDef actDef = wyActDefService.getWyActDefByProcDefId(wyActFormDto.getProcDefinitionId());
		if (actDef == null) {
			throw new RuntimeException("获取工作流程配置信息出现异常");
		}
		String type = actDef.getCategory();
		List<WyBizForm> wyBizFormList = wyBizFormService.getWyBizFormListByType(type);

		List<WyActForm> wyActFormList = wyActFormService.getWyActFormListByprocDefIdAndTaskid(wyActFormDto.getProcDefinitionId(), wyActFormDto.getActivitiId());
		if (wyActFormList != null && !wyActFormList.isEmpty()) {
			WyBizForm wyBizForm = wyBizFormList.get(0);
			wyActFormDto.setFormId(wyBizForm.getFormId());
		}

		WyActForm wyActForm = wyActFormService.getWyActFormByProcDefIdAndTaskId(wyActFormDto.getProcDefinitionId(), wyActFormDto.getActivitiId());
		if (wyActForm != null) {
			wyActFormDto.setFormId(wyActForm.getFormId());
		}

		model.addAttribute("wyBizFormList", wyBizFormList);
		model.addAttribute("wyActFormDto", wyActFormDto);

		return "modules/sns/wyActShowForm2";
	}

	/**
	 * 
	 * @param wyActForm
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveForm2")
	public WeJson saveForm2(WyActFormDto wyActFormDto, Model model) {
		try {
			String procDefId = wyActFormDto.getProcDefinitionId();
			String activitiId = wyActFormDto.getActivitiId();

			if (StringUtils.isNotBlank(wyActFormDto.getStartUrlFormid())) {
				WyActForm wyActForm = wyActFormService.getWyActFormByProcDefIdAndTaskIdAndFormType(procDefId, activitiId, WyConstants.TASK_FORM_URL_START);
				if (wyActForm == null) {
					wyActForm = new WyActForm();
				}
				wyActForm.setProcDefId(procDefId);
				wyActForm.setTaskId(activitiId);
				wyActForm.setFormType(WyConstants.TASK_FORM_URL_START);

				WyBizForm wyBizForm = wyBizFormService.get(wyActFormDto.getStartUrlFormid());
				if (wyBizForm != null) {
					wyActForm.setFormUrl(wyBizForm.getFormUrl());
					wyActForm.setFormName(wyBizForm.getFormName());
					wyActForm.setFormId(wyActFormDto.getStartUrlFormid());
				}

				wyActFormService.save(wyActForm);
			}

			if (StringUtils.isNotBlank(wyActFormDto.getEndUrlFormid())) {
				WyActForm wyActForm = wyActFormService.getWyActFormByProcDefIdAndTaskIdAndFormType(procDefId, activitiId, WyConstants.TASK_FORM_URL_END);
				if (wyActForm == null) {
					wyActForm = new WyActForm();
				}
				wyActForm.setProcDefId(procDefId);
				wyActForm.setTaskId(activitiId);
				wyActForm.setFormType(WyConstants.TASK_FORM_URL_END);

				WyBizForm wyBizForm = wyBizFormService.get(wyActFormDto.getEndUrlFormid());
				if (wyBizForm != null) {
					wyActForm.setFormUrl(wyBizForm.getFormUrl());
					wyActForm.setFormName(wyBizForm.getFormName());
					wyActForm.setFormId(wyActFormDto.getEndUrlFormid());
				}

				wyActFormService.save(wyActForm);
			}

			return WeJson.success("保存成功");
		} catch (Exception e) {
			logger.error("保存出错={}", e);
			return WeJson.fail("保存失败");
		}
	}

	/**
	 * 显示轨迹图
	 * 
	 * @param processDefinitionId
	 * @param processInstanceId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getGraphTrace")
	public String getGraphTrace(String processDefinitionId, String processInstanceId, Model model) {
		model.addAttribute("processDefinitionId", processDefinitionId);
		model.addAttribute("processInstanceId", processInstanceId);

		return "modules/sns/wyActFormGraphTrace";
	}

}