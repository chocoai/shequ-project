package com.haolinbang.modules.act.web.workflow;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.utils.PageUtil;
import com.haolinbang.modules.act.entity.Act;

/**
 * 工作流实例查询
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/workflow/processinstance")
public class ProcessInstanceController {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private RepositoryService repositoryService;

	/**
	 * 挂起、激活流程实例
	 */
	@RequestMapping(value = "/update/{state}/{processInstanceId}")
	public String updateState(@PathVariable("state") String state, @PathVariable("processInstanceId") String processInstanceId, RedirectAttributes redirectAttributes) {
		if (state.equals("active")) {
			redirectAttributes.addFlashAttribute("message", "已激活ID为[" + processInstanceId + "]的流程实例。");
			runtimeService.activateProcessInstanceById(processInstanceId);
		} else if (state.equals("suspend")) {
			runtimeService.suspendProcessInstanceById(processInstanceId);
			redirectAttributes.addFlashAttribute("message", "已挂起ID为[" + processInstanceId + "]的流程实例。");
		}
		return "redirect:/workflow/processinstance/running";
	}

	/**
	 * 流程设置,对人员进行设置
	 * 
	 * @param model
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/running")
	public ModelAndView running(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("modules/act/running-manage");
		Page<ProcessInstance> page = new Page<ProcessInstance>(request, response);
		int[] pageParams = PageUtil.init(page, request);

		ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
		List<ProcessInstance> list = processInstanceQuery.listPage(0, pageParams[1]);
		page.setList(list);
		page.setCount(processInstanceQuery.count());
		mav.addObject("page", page);
		return mav;
	}

	/**
	 * 查看流程定义
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryProcessDefinition")
	public String queryProcessDefinition(Act act, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 获取仓库服务对象
		// 获取流程定义查询对象
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

		Page<ProcessDefinition> page = new Page<ProcessDefinition>(request, response);
		if (StringUtils.isNotBlank(act.getProcDefName())) {
			processDefinitionQuery.processDefinitionNameLike(act.getProcDefName());
		}
		if (StringUtils.isNotBlank(act.getProcDefId())) {
			processDefinitionQuery.processDefinitionId(act.getProcDefId());
		}
		if (StringUtils.isNotBlank(act.getProcDefKey())) {
			processDefinitionQuery.processDefinitionKeyLike(act.getProcDefKey());
		}

		processDefinitionQuery.listPage(page.getFirstResult(), page.getMaxResults());
		

		// 分页设置

		// processDefinitionQuery
		// 添加过滤条件
		// .processDefinitionName(processDefinitionName)
		// .processDefinitionId(processDefinitionId)
		// .processDefinitionKey(processDefinitionKey)
		// 分页条件
		// .listPage(firstResult, maxResults)
		// 排序条件

		// 配置查询对象
		processDefinitionQuery.orderByProcessDefinitionVersion().desc();

		/**
		 * 执行查询 list : 执行后返回一个集合 singelResult
		 * 执行后，首先检测结果长度是否为1，如果为一则返回第一条数据；如果不唯一，抛出异常 count： 统计符合条件的结果数量
		 */
		List<ProcessDefinition> pds = processDefinitionQuery.list();
		page.setList(pds);
		model.addAttribute("page", page);
		model.addAttribute("act", act);
		

		return "modules/act/actProcDefList";
	}
}
