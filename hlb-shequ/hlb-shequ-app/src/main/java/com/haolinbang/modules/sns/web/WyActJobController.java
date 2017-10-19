package com.haolinbang.modules.sns.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.pvm.PvmTransition;
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
import com.haolinbang.common.web.BaseController;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.entity.WyActJob;
import com.haolinbang.modules.sns.service.WyActJobService;
import com.haolinbang.modules.sns.util.ActUtil;

/**
 * 工作流定时任务时间表Controller
 * 
 * @author nlp
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyActJob")
public class WyActJobController extends BaseController {

	@Autowired
	private WyActJobService wyActJobService;

	@ModelAttribute
	public WyActJob get(@RequestParam(required = false) String id) {
		WyActJob entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyActJobService.get(id);
		}
		if (entity == null) {
			entity = new WyActJob();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyActJob:view")
	@RequestMapping(value = { "list", "" })
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
		if (!beanValidator(model, wyActJob)) {
			return form(wyActJob, model);
		}
		wyActJobService.save(wyActJob);
		addMessage(redirectAttributes, "保存工作流定时任务时间表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyActJob/?repage";
	}

	@RequiresPermissions("sns:wyActJob:edit")
	@RequestMapping(value = "delete")
	public String delete(WyActJob wyActJob, RedirectAttributes redirectAttributes) {
		wyActJobService.delete(wyActJob);
		addMessage(redirectAttributes, "删除工作流定时任务时间表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyActJob/?repage";
	}

	/**
	 * 定时器设置,选择显示定时设置
	 * 
	 * @param wyActJob
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sns:wyActJob:view")
	@RequestMapping(value = "show")
	public String show(WyActJob wyActJob, String activitiId, String procDefinitionId, Model model) {
		// 获取定时任务
		// 将上一个节点的数据查询出来
		String sourceId1 = null;
		List<PvmTransition> list = ActUtil.getIncomingTransitions(procDefinitionId, activitiId);
		if (list != null && list.size() == 1) {
			sourceId1 = list.get(0).getSource().getId();
		}
		String sourceId2 = null;
		// 在查找下一个节点,获取前一个节点的id
		if (StringUtils.isNotBlank(sourceId1)) {
			List<PvmTransition> list2 = ActUtil.getIncomingTransitions(procDefinitionId, sourceId2);
			if (list2 != null && list2.size() > 0) {

			}
		}

		wyActJob = wyActJobService.getWyActJobByprocDefIdAndActId(procDefinitionId, activitiId);

		model.addAttribute("activitiId", activitiId);
		model.addAttribute("procDefinitionId", procDefinitionId);
		model.addAttribute("wyActJob", wyActJob);
		return "modules/sns/wyActJobFormShow";
	}

	/**
	 * 定时器设置,选择显示定时设置
	 * 
	 * @param wyActJob
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sns:wyActJob:edit")
	@RequestMapping(value = "saveSettingTime")
	public WeJson saveSettingTime(WyActJob wyActJob, Model model) {
		try {
			WyActJob wyActJob2 = wyActJobService.getWyActJobByprocDefIdAndActId(wyActJob.getProcDefId(), wyActJob.getTaskId());
			if (wyActJob2 != null) {
				// 如果能够查询到记录,则进行更新操作
				wyActJob.setId(wyActJob2.getId());
			}
			// 超时时间类型
			wyActJob.setType(WyConstants.TASK_JOB_TIMEOUT);
			wyActJobService.save(wyActJob);
			return WeJson.success("保存成功");
		} catch (Exception e) {
			logger.error("保存错误:{}", e);
			return WeJson.fail("保存失败");
		}
	}

}