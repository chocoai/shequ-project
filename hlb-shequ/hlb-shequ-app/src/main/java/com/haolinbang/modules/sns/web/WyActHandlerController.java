package com.haolinbang.modules.sns.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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
import com.haolinbang.modules.sns.dto.WyActHandlerDto;
import com.haolinbang.modules.sns.dto.WyBizHandlerDto;
import com.haolinbang.modules.sns.dto.WyRelationCandidate;
import com.haolinbang.modules.sns.entity.WyActHandler;
import com.haolinbang.modules.sns.entity.WyActJob;
import com.haolinbang.modules.sns.entity.WyBizHandler;
import com.haolinbang.modules.sns.service.WyActHandlerService;
import com.haolinbang.modules.sns.service.WyActJobService;
import com.haolinbang.modules.sns.service.WyBizHandlerService;

/**
 * 流程处理后调用方法Controller
 * 
 * @author nlp
 * @version 2017-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyActHandler")
public class WyActHandlerController extends BaseController {

	@Autowired
	private WyActHandlerService wyActHandlerService;

	@Autowired
	private WyBizHandlerService wyBizHandlerService;

	@Autowired
	private WyActJobService wyActJobService;

	@ModelAttribute
	public WyActHandler get(@RequestParam(required = false) String id) {
		WyActHandler entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyActHandlerService.get(id);
		}
		if (entity == null) {
			entity = new WyActHandler();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyActHandler:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyActHandler wyActHandler, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyActHandler> page = wyActHandlerService.findPage(new Page<WyActHandler>(request, response), wyActHandler);
		model.addAttribute("page", page);
		return "modules/sns/wyActHandlerList";
	}

	@RequiresPermissions("sns:wyActHandler:view")
	@RequestMapping(value = "form")
	public String form(WyActHandler wyActHandler, Model model) {
		model.addAttribute("wyActHandler", wyActHandler);
		return "modules/sns/wyActHandlerForm";
	}

	@RequiresPermissions("sns:wyActHandler:edit")
	@RequestMapping(value = "save")
	public String save(WyActHandler wyActHandler, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyActHandler)) {
			return form(wyActHandler, model);
		}
		wyActHandlerService.save(wyActHandler);
		addMessage(redirectAttributes, "保存流程处理后调用方法成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyActHandler/?repage";
	}

	@RequiresPermissions("sns:wyActHandler:edit")
	@RequestMapping(value = "delete")
	public String delete(WyActHandler wyActHandler, RedirectAttributes redirectAttributes) {
		wyActHandlerService.delete(wyActHandler);
		addMessage(redirectAttributes, "删除流程处理后调用方法成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyActHandler/?repage";
	}

	/**
	 * 显示处理任务页面
	 * 
	 * @param activitiId
	 * @param procDefinitionId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sns:wyActHandler:edit")
	@RequestMapping(value = "showHandler")
	public String showHandler(String activitiId, String procDefinitionId, Model model) {
		WyBizHandler wyBizHandler = new WyBizHandler();
		List<WyBizHandler> wyBizHandlerList = wyBizHandlerService.findList(wyBizHandler);

		List<String> wyBizHandlerGroup = wyBizHandlerService.getWyBizHandlerGroupByType();
		model.addAttribute("wyBizHandlerGroup", wyBizHandlerGroup);

		// 查询已经保存的信息
		List<WyActHandler> wyActHandlerList = wyActHandlerService.getWyActHandlerListByProcDefIdAndTaskId(procDefinitionId, activitiId);

		// 查询是否需要勾选
		// 创建
		List<WyBizHandlerDto> createlist = new ArrayList<WyBizHandlerDto>();
		for (WyBizHandler wyBizHandler2 : wyBizHandlerList) {
			WyBizHandlerDto wyBizHandlerDto = new WyBizHandlerDto();
			BeanUtils.copyProperties(wyBizHandler2, wyBizHandlerDto);
			for (WyActHandler wyActHandler : wyActHandlerList) {
				if (wyBizHandler2.getHandlerInstance().equals(wyActHandler.getHandlerInstance())) {
					if (WyConstants.TASK_EVENT_CREATE.equals(wyActHandler.getEventName()) && WyConstants.TASK_EVENT_HANDLER_NORMAL.equals(wyActHandler.getType())) {
						wyBizHandlerDto.setChecked(true);
					}
				}
			}
			createlist.add(wyBizHandlerDto);
		}

		List<WyBizHandlerDto> createJoblist = new ArrayList<WyBizHandlerDto>();
		for (WyBizHandler wyBizHandler2 : wyBizHandlerList) {
			WyBizHandlerDto wyBizHandlerDto = new WyBizHandlerDto();
			BeanUtils.copyProperties(wyBizHandler2, wyBizHandlerDto);
			for (WyActHandler wyActHandler : wyActHandlerList) {
				if (wyBizHandler2.getHandlerInstance().equals(wyActHandler.getHandlerInstance())) {
					if (WyConstants.TASK_EVENT_CREATE.equals(wyActHandler.getEventName()) && WyConstants.TASK_EVENT_HANDLER_JOB.equals(wyActHandler.getType())) {
						wyBizHandlerDto.setChecked(true);
					}
				}
			}
			createJoblist.add(wyBizHandlerDto);
		}

		List<WyBizHandlerDto> assignlist = new ArrayList<WyBizHandlerDto>();
		for (WyBizHandler wyBizHandler2 : wyBizHandlerList) {
			WyBizHandlerDto wyBizHandlerDto = new WyBizHandlerDto();
			BeanUtils.copyProperties(wyBizHandler2, wyBizHandlerDto);
			for (WyActHandler wyActHandler : wyActHandlerList) {
				if (wyBizHandler2.getHandlerInstance().equals(wyActHandler.getHandlerInstance())) {
					if (WyConstants.TASK_EVENT_ASSIGN.equals(wyActHandler.getEventName())) {
						wyBizHandlerDto.setChecked(true);
					}
				}
			}
			assignlist.add(wyBizHandlerDto);
		}

		List<WyBizHandlerDto> completelist = new ArrayList<WyBizHandlerDto>();
		for (WyBizHandler wyBizHandler2 : wyBizHandlerList) {
			WyBizHandlerDto wyBizHandlerDto = new WyBizHandlerDto();
			BeanUtils.copyProperties(wyBizHandler2, wyBizHandlerDto);
			for (WyActHandler wyActHandler : wyActHandlerList) {
				if (wyBizHandler2.getHandlerInstance().equals(wyActHandler.getHandlerInstance())) {
					if (WyConstants.TASK_EVENT_COMPLETE.equals(wyActHandler.getEventName())) {
						wyBizHandlerDto.setChecked(true);
					}
				}
			}
			completelist.add(wyBizHandlerDto);
		}

		// 查询定时任务
		WyActJob wyActJob = wyActJobService.getWyActJobByProcdefidAndTaskidAndType(procDefinitionId, activitiId, WyConstants.TASK_JOB_ACTIVITI_CREATE);

		model.addAttribute("createJoblist", createJoblist);
		model.addAttribute("wyActJob", wyActJob);
		model.addAttribute("activitiId", activitiId);
		model.addAttribute("procDefinitionId", procDefinitionId);
		model.addAttribute("wyActHandlerList", wyActHandlerList);
		model.addAttribute("createlist", createlist);
		model.addAttribute("assignlist", assignlist);
		model.addAttribute("completelist", completelist);

		return "modules/sns/wyActHandlerShow";
	}

	/**
	 * 通过分类选择处理方法
	 */
	@RequestMapping("/showHandlerByType")
	public String showHandlerByType(String type, Model model) {
		WyRelationCandidate wyRelationCandidate = new WyRelationCandidate();
		model.addAttribute("wyRelationCandidate", wyRelationCandidate);
		
		List<WyBizHandler> wyBizHandlerList = wyBizHandlerService.getWyRelationCandidateByType(type);
		model.addAttribute("wyBizHandlerList", wyBizHandlerList);

		return "modules/sns/selectHandlerShow";
	}

	/**
	 * 
	 * @param activitiId
	 * @param procDefinitionId
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sns:wyActHandler:edit")
	@RequestMapping(value = "saveHandler")
	public WeJson saveHandler(WyActHandlerDto wyActHandlerDto) {
		try {

			WeJson json = wyActHandlerService.saveHandler(wyActHandlerDto);
			return json;
		} catch (Exception e) {
			logger.info("出现异常={}", e);
			return WeJson.fail("保存失败，出现异常");
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

		return "modules/sns/wyActHandlerGraphTrace";
	}
}