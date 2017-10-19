package com.haolinbang.modules.sns.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.utils.DateUtils;
import com.haolinbang.common.utils.Exceptions;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.common.web.ServletUtil;
import com.haolinbang.modules.act.entity.Act;
import com.haolinbang.modules.act.utils.ProcessDefCache;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.dao.WyRepairDao;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyActForm;
import com.haolinbang.modules.sns.entity.WyApprove;
import com.haolinbang.modules.sns.entity.WyApproveDetail;
import com.haolinbang.modules.sns.entity.WyBizDef;
import com.haolinbang.modules.sns.entity.WyComplain;
import com.haolinbang.modules.sns.entity.WyReBizAct;
import com.haolinbang.modules.sns.entity.WyRepair;
import com.haolinbang.modules.sns.service.WuyeService;
import com.haolinbang.modules.sns.service.WyActFormService;
import com.haolinbang.modules.sns.service.WyApproveDetailService;
import com.haolinbang.modules.sns.service.WyApproveService;
import com.haolinbang.modules.sns.service.WyBizDefService;
import com.haolinbang.modules.sns.service.WyComplainService;
import com.haolinbang.modules.sns.service.WyReBizActService;
import com.haolinbang.modules.sns.service.WyRepairService;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sns.util.RoomUtils;

@Controller
@RequestMapping("/wuye")
public class WuyeController extends BaseController {

	@Autowired
	private WyRepairDao wyRepairDao;

	@Autowired
	private WyRepairService wyRepairService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private WyReBizActService wyReBizActService;

	// 私有区域key
	private String bizKey = "biz_private_repair";

	@Autowired
	private WyActFormService wyActFormService;

	@Autowired
	private WuyeService wuyeService;

	@Autowired
	private WyApproveService wyApproveService;

	@Autowired
	private WyApproveDetailService wyApproveDetailService;

	@Autowired
	private WyBizDefService wyBizDefService;
	
	@Autowired
	private WyComplainService wyComplainService;

	@RequestMapping("/repairs")
	public String repairs(Model model) {
		try {
			Member member = MemberUtils.getCurrentMember();
			// Member member = memberService.getMember(memberId);
			ServletUtil.getSession().setAttribute("memberid", member.getMemberId());
			Room room = RoomUtils.getCurrentRoom();

			model.addAttribute("member", member);
			model.addAttribute("room", room);

			model.addAttribute("bizKey", bizKey);

			return "modules/sns/repairs";
		} catch (Exception e) {
			logger.error("报修提交");
			return Exceptions.deal(e);
		}
	}

	// 报修内容存储
	@ResponseBody
	@RequestMapping("/toRepairs")
	public WeJson toRepairs(HttpServletRequest request, Model model) {
		try {
			Member member = MemberUtils.getCurrentMember();

			String pri = request.getParameter("pri");
			// String pub = request.getParameter("pub");
			String rname = request.getParameter("rname");
			String rphone = request.getParameter("rphone");
			String rcontent = request.getParameter("rcontent");
			String rdetail = request.getParameter("rdetail");
			String beginTime = request.getParameter("beginTime");
			String picture1 = request.getParameter("picture1");
			String picture2 = request.getParameter("picture2");
			String picture3 = request.getParameter("picture3");

			WyRepair wyRepairs = new WyRepair();
			wyRepairs.setId(StringUtils.getUUID());
			wyRepairs.setMemberid(member.getMemberId());
			wyRepairs.setRepairtype(pri == null ? "公共区域" : "私人区域");
			wyRepairs.setApplyname(rname);
			wyRepairs.setPhone(rphone);
			wyRepairs.setContent(rcontent);
			wyRepairs.setContentdetail(rdetail);
			wyRepairs.setAppointmenttime(DateUtils.parseDate(beginTime));
			wyRepairs.setImgurl((picture1 != null ? picture1 + "," : "") + (picture2 != null ? picture2 + "," : "") + (picture3 != null ? picture3 : ""));
			wyRepairs.setCreatetime(new Date());
			wyRepairs.setUpdatetime(new Date());
			wyRepairs.setRepairstatus("0");

			// 从数据库中查询对应的流程
			WyReBizAct wyReBizAct = wyReBizActService.getBizActByBizKey(bizKey);
			// 启动流程
			String procDefId = wyReBizAct.getWyActDef().getProcDefId();
			if (StringUtils.isBlank(procDefId)) {
				return WeJson.fail("系统出错，请联系管理员");
			}

			model.addAttribute("bizKey", bizKey);
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(member.getMemberId().toString());

			Map<String, Object> vars = new HashMap<String, Object>();

			// actTaskService.startProcess(act.getProcDefKey(),
			// act.getBusinessId(),
			// act.getBusinessTable(), act.getTitle());
			ProcessInstance pi = runtimeService.startProcessInstanceById(procDefId, vars);
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

			// 更新流程id
			wyRepairs.setProcInsId(pi.getId());
			wyRepairDao.insert(wyRepairs);

			return WeJson.success("add");
		} catch (Exception e) {
			logger.error("保存信息失败:{}", e);
			return WeJson.fail("add");
		}
	}

	/**
	 * 我的报修列表
	 * 
	 * @param flag
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myRepair")
	public String myRepair(String flag, Model model) {
		try {
			flag = StringUtils.isBlank(flag) ? "0" : flag;// 0:处理中 1：未处理 2：已处理
			model.addAttribute("flag", flag);

			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
			Member member = MemberUtils.getCurrentMember();
			ServletUtil.getSession().setAttribute("memberid", member.getMemberId());

			// 查询该用户的所有报修任务
			List<WyRepair> allWyRepairList = wyRepairService.getWyRepairByMemberId(member.getMemberId().toString());

			// 获取用户可以抢单的任务,通过定义的processDefinitionId进行过滤数据
			List<Act> todoActList = getTodoActList(member.getMemberId().toString(), bizKey);
			List<String> procInsIdList = new ArrayList<String>();
			for (Act act : todoActList) {
				procInsIdList.add(act.getTask().getProcessInstanceId());
			}
			List<WyRepair> grabtasks = null;
			if (procInsIdList != null && procInsIdList.size() > 0) {
				grabtasks = wyRepairService.getRepairsByProcInsIds2(procInsIdList);
			}

			// 获取用户待处理任务
			List<Act> toClaimActList = getToClaimActList(member.getMemberId().toString(), bizKey);
			List<String> procInsIdList2 = new ArrayList<String>();
			for (Act act : toClaimActList) {
				procInsIdList2.add(act.getTask().getProcessInstanceId());
			}
			List<WyRepair> daibanTasks = null;
			if (procInsIdList2 != null && procInsIdList2.size() > 0) {
				daibanTasks = wyRepairService.getRepairsByProcInsIds2(procInsIdList2);
			}

			// 办理任务和待办任务合并起来
			List<WyRepair> wyRepairList = new ArrayList<WyRepair>();
			if (grabtasks != null && grabtasks.size() > 0) {
				wyRepairList.addAll(grabtasks);
			}
			if (daibanTasks != null && daibanTasks.size() > 0) {
				wyRepairList.addAll(daibanTasks);
			}

			// 多张图片展示
			for (WyRepair wp : wyRepairList) {
				if (wp.getImgurl() != null) {
					wp.setImgList(Arrays.asList(wp.getImgurl().split(",")));
				}
			}

			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

			List<WyRepair> doneWyRepairList = new ArrayList<>();
			for (WyRepair repair : allWyRepairList) {
				if (!wyRepairList.contains(repair)) {
					doneWyRepairList.add(repair);
				}
			}

			// 我申请的报修
			List<WyRepair> applyWyRepairList = wyRepairService.getWyRepairByMemberId(member.getMemberId().toString());

			// 获取节点流程集合
			List<String> flowNames = getFlowNames(bizKey);
			// 获取每个流程的进度
			for (WyRepair wp : applyWyRepairList) {
				if (StringUtils.isNotBlank(wp.getProcInsId())) {
					List<Task> tasks = taskService.createTaskQuery().processInstanceId(wp.getProcInsId()).active().orderByTaskCreateTime().desc().list();
					if (tasks != null && !tasks.isEmpty()) {
						for (Task task : tasks) {
							wp.setCurrStep(getTaskCurrStep(task.getName(), flowNames));
							wp.setCurrStepName(task.getName());
						}
					} else {
						wp.setCurrStep(9999999);
						wp.setCurrStepName("已完成");
					}
				}
			}

			for (WyRepair wp : wyRepairList) {
				if (StringUtils.isNotBlank(wp.getProcInsId())) {
					List<Task> tasks = taskService.createTaskQuery().processInstanceId(wp.getProcInsId()).active().orderByTaskCreateTime().desc().list();
					if (tasks != null && !tasks.isEmpty()) {
						for (Task task : tasks) {
							wp.setCurrStep(getTaskCurrStep(task.getName(), flowNames));
							wp.setCurrStepName(task.getName());
						}
					} else {
						wp.setCurrStep(9999999);
						wp.setCurrStepName("已完成");
					}
				}
			}

			model.addAttribute("doneWyRepairList", doneWyRepairList);
			model.addAttribute("wyRepairList", wyRepairList);
			model.addAttribute("applyWyRepairList", applyWyRepairList);
			model.addAttribute("flowNames", flowNames);

			return "modules/sns/myRepair";
		} catch (Exception e) {
			logger.error("获取报修列表失败：{}", e);
			return Exceptions.deal(e);
		}

	}

	/**
	 * 流程处理详情
	 * 
	 * @return
	 */
	@RequestMapping("/processDetail")
	public String processDetail(String procInstId, Model model) {
		WyApprove wyApprove = wyApproveService.getWyApproveByProcInstId(procInstId);
		List<WyApproveDetail> wyApproveDetailList = null;
		if (wyApprove != null) {
			wyApproveDetailList = wyApproveDetailService.getWyApproveDetailListByProcInstId(procInstId);
		}
		model.addAttribute("wyApprove", wyApprove);
		model.addAttribute("wyApproveDetailList", wyApproveDetailList);

		return "modules/sns/processDetail";
	}

	/**
	 * 获取流程顺序名称
	 * 
	 * @param bizKey
	 * @return
	 */
	protected List<String> getFlowNames(String bizKey) {
		// 从数据库中查询对应的流程
		WyReBizAct wyReBizAct = wyReBizActService.getBizActByBizKey(bizKey);
		// 获取流程定义名称
		ProcessDefinitionEntity def2 = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(wyReBizAct.getWyActDef().getProcDefId());
		List<ActivityImpl> activitiList2 = def2.getActivities();
		Collections.sort(activitiList2, new Comparator<ActivityImpl>() {
			@Override
			public int compare(ActivityImpl o1, ActivityImpl o2) {
				return o1.getX() - o2.getX();
			}
		});
		List<String> flowNames = new ArrayList<String>();
		for (ActivityImpl act : activitiList2) {
			if ("userTask".equals(act.getProperty("type"))) {
				flowNames.add(String.valueOf(act.getProperty("name")));
			}
		}
		return flowNames;
	}

	/**
	 * 查询签收任务
	 * 
	 * @return
	 */
	protected List<Act> getToClaimActList(String userId, String bizKey) {
		// 从数据库中查询对应的流程
		WyReBizAct wyReBizAct = wyReBizActService.getBizActByBizKey(bizKey);
		// =============== 等待签收的任务 ===============
		List<Act> toClaimActList = new ArrayList<Act>();
		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId).active().orderByTaskCreateTime().desc();

		// 设置查询条件
		if (wyReBizAct != null && wyReBizAct.getWyActDef() != null && StringUtils.isNotBlank(wyReBizAct.getWyActDef().getProcDefId())) {
			toClaimQuery.processDefinitionId(wyReBizAct.getWyActDef().getProcDefId());
		}

		// 查询列表
		List<Task> toClaimList = toClaimQuery.list();
		for (Task task : toClaimList) {
			Act e = new Act();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
			e.setTaskVars(task.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
			e.setStatus("claim");
			toClaimActList.add(e);
		}
		return toClaimActList;
	}

	/**
	 * 获取待办任务
	 * 
	 * @param userId
	 * @param bizKey
	 * @return
	 */
	protected List<Act> getTodoActList(String userId, String bizKey) {
		// 从数据库中查询对应的流程
		WyReBizAct wyReBizAct = wyReBizActService.getBizActByBizKey(bizKey);
		List<Act> todoActList = new ArrayList<Act>();
		// =============== 已经签收的任务 ===============
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active().orderByTaskCreateTime().desc();
		// 设置查询条件,通過流程定义id查询
		if (wyReBizAct != null && wyReBizAct.getWyActDef() != null && StringUtils.isNotBlank(wyReBizAct.getWyActDef().getProcDefId())) {
			todoTaskQuery.processDefinitionId(wyReBizAct.getWyActDef().getProcDefId());
		}
		// 查询列表
		List<Task> todoList = todoTaskQuery.list();
		for (Task task : todoList) {
			Act e = new Act();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
			e.setTaskVars(task.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
			e.setStatus("todo");
			todoActList.add(e);
		}
		return todoActList;
	}

	/**
	 * 计算当前节点步骤
	 * 
	 * @param name
	 * @param flowNames
	 * @return
	 */
	protected int getTaskCurrStep(String name, List<String> flowNames) {
		int currStep = 0;
		for (String name2 : flowNames) {
			currStep++;
			if (name.equals(name2)) {
				return currStep;
			}
		}
		return 0;
	}

	// 编辑报修单
	@RequestMapping("/editRepair")
	public String editRepair(String id, Model model) {
		Member member = MemberUtils.getCurrentMember();
		// Room room = RoomUtils.getCurrentRoom();
		WyRepair wyRepair = wyRepairDao.get(id);
		if (wyRepair.getImgurl() != null) {
			wyRepair.setImgList(Arrays.asList(wyRepair.getImgurl().split(",")));
		}

		model.addAttribute("member", member);
		// model.addAttribute("room", room);
		model.addAttribute("wyRepair", wyRepair);
		model.addAttribute("imgnum", wyRepair.getImgList().size());

		return "modules/sns/repairs";
	}

	/**
	 * 委托人办理任务
	 */
	@RequestMapping("/resolveTask")
	public String resolveTask(String bizId, String procInsId) {
		try {
			String formkey = wuyeService.resolveTask(bizId, procInsId);
			return formkey;
		} catch (Exception e) {
			logger.error("委托人办理任务:{}", e);
			return Exceptions.deal(e);
		}
	}

	// 委托他人办理,用户认领任务后,才能进行委托办理
	@RequestMapping("/delegateTask")
	public String delegateTask(String bizId, String procInsId, String memberid) {
		try {
			String formKey = wuyeService.delegateTask(bizId, procInsId, memberid);
			return formKey;
		} catch (Exception e) {
			logger.error("委托他人办理出现错误:{}", e);
			return "";
		}
	}

	/**
	 * 表单入口
	 * 
	 * @param bizId
	 *            业务id
	 * @param procInsId
	 *            流程实例id
	 * @param taskId
	 *            任务id
	 * @return
	 */
	@RequestMapping("/form2")
	public String form2(String bizId, String procInsId) {
		try {
			Member member = MemberUtils.getCurrentMember();
			String taskKey = null;
			String formUrlType = null;
			String procDefId = null;
			String formKey = null;
			// 获取当前要处理的的流程任务节点
			Task currTask = taskService.createTaskQuery().processInstanceId(procInsId).taskAssignee(member.getMemberId().toString()).active().orderByTaskCreateTime().desc()
					.singleResult();
			if (currTask != null) {
				taskKey = currTask.getTaskDefinitionKey();
				formUrlType = WyConstants.TASK_FORM_COMPLETE_URL;
				procDefId = currTask.getProcessDefinitionId();
			}

			// 要认领的任务节点
			Task claimTask = taskService.createTaskQuery().processInstanceId(procInsId).taskCandidateUser(member.getMemberId().toString()).active().orderByTaskCreateTime().desc()
					.singleResult();
			if (claimTask != null) {
				taskKey = claimTask.getTaskDefinitionKey();
				formUrlType = WyConstants.TASK_FORM_CLAIM_URL;
				procDefId = claimTask.getProcessDefinitionId();
			}

			// 如果任务已完成,则没有可操作的节点,默认是从结束节点获取表单
			if (currTask == null && claimTask == null) {
				HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInsId).singleResult();
				procDefId = historicProcessInstance.getProcessDefinitionId();
				formUrlType = WyConstants.TASK_FORM_URL_END;

				// 获取最后一个节点
				ProcessDefinitionEntity def2 = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(procDefId);
				List<ActivityImpl> activitiList2 = def2.getActivities();
				for (ActivityImpl activityImpl : activitiList2) {
					if ("endEvent".equals(activityImpl.getProperty("type"))) {
						taskKey = activityImpl.getId();
						break;
					}
				}
			}

			// 从表中查询显示的页面
			WyActForm wyActForm = wyActFormService.getWyActFormByProcDefIdAndTaskKey(procDefId, taskKey);
			if (wyActForm != null) {
				// 组装调用地址
				formKey = "redirect:" + wyActForm.getFormUrl() + "?bizId=" + bizId + "&procInsId=" + procInsId;
			}
			return formKey;
		} catch (Exception e) {
			logger.error("获取表单入口出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 通用完成任务
	 * 
	 * @param bizId
	 * @param procInsId
	 * @param vars
	 *            用于传递变量临时变量
	 * @return
	 */
	@RequestMapping("/fulfilTask")
	public String fulfilTask(String bizId, String procInsId, String vars) {
		try {
			boolean b = wuyeService.fulfilTask(bizId, procInsId, vars);
			return "redirect:/wuye/myAffairs";
		} catch (Exception e) {
			logger.error("通用完成任务出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 回退任务
	 */
	@RequestMapping("/backTask")
	public String backTask(String bizId, String procInsId) {
		try {
			boolean b = wuyeService.backTask(bizId, procInsId);

			return "redirect:/wuye/myAffairs";
		} catch (Exception e) {
			logger.error("回退任务出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 表单详情入口
	 * 
	 * @param repairId
	 * @param model
	 * @return
	 */
	@RequestMapping("/todo")
	public String todo(HttpServletRequest request, String bizKey, Model model) {
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		// Member member = memberService.getMember(memberId);
		Member member = MemberUtils.getCurrentMember();
		// 获取当前用户id
		String userId = member.getMemberId().toString();

		// 从数据库中查询对应的流程
		WyReBizAct wyReBizAct = wyReBizActService.getBizActByBizKey(bizKey);
		List<Act> todoActList = new ArrayList<Act>();
		// =============== 已经签收的任务 ===============
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active().orderByTaskCreateTime().desc();
		// 设置查询条件,通過流程定义id查询
		if (wyReBizAct != null && wyReBizAct.getWyActDef() != null && StringUtils.isNotBlank(wyReBizAct.getWyActDef().getProcDefId())) {
			todoTaskQuery.processDefinitionId(wyReBizAct.getWyActDef().getProcDefId());
		}
		// 查询列表
		List<Task> todoList = todoTaskQuery.list();
		for (Task task : todoList) {
			Act e = new Act();
			e.setTask(task);
			e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
			e.setStatus("todo");
			todoActList.add(e);
		}

		// =============== 等待签收的任务 ===============
		List<Act> toClaimActList = new ArrayList<Act>();
		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId).active().orderByTaskCreateTime().desc();

		// 设置查询条件
		if (wyReBizAct != null && wyReBizAct.getWyActDef() != null && StringUtils.isNotBlank(wyReBizAct.getWyActDef().getProcDefId())) {
			toClaimQuery.processDefinitionId(wyReBizAct.getWyActDef().getProcDefId());
		}

		// 查询列表
		List<Task> toClaimList = toClaimQuery.list();
		for (Task task : toClaimList) {
			Act e = new Act();
			e.setTask(task);
			e.setStatus("claim");
			toClaimActList.add(e);
		}

		// 根据配置转发到不同的连接地址
		String redirect = "";
		switch (bizKey) {
		case "biz_private_repair":
			redirect = "redirect:/wuye/myAffairs";
			break;

		default:
			break;
		}

		return redirect;
	}

	/* 投诉选择页面 */
	@RequestMapping("/tschoose")
	public String tschoose(Model model) {
		return "modules/sns/tschoose";
	}

	/**
	 * 我的事务
	 * 
	 * @param flag
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myAffairs")
	public String myAffairs(String flag, String type, String keywords, Model model) {
		try {
			Member member = MemberUtils.getCurrentMember();
			flag = StringUtils.isBlank(flag) ? "0" : flag;
			// 将标记加上
			model.addAttribute("flag", flag);
			model.addAttribute("type", type);
			model.addAttribute("keywords", keywords);

			if ("0".equals(flag)) {
				// 待办事项
				List<String> procInstIdList = new ArrayList<String>();
				// 查询本人的任务节点
				List<Task> claimtasks = taskService.createTaskQuery().taskCandidateUser(member.getMemberId().toString()).active().orderByTaskCreateTime().desc().list();
				List<Task> tdotasks = taskService.createTaskQuery().taskAssignee(member.getMemberId().toString()).active().orderByTaskCreateTime().desc().list();
				List<Task> tasks = new ArrayList<Task>();
				if (claimtasks != null && !claimtasks.isEmpty()) {
					tasks.addAll(claimtasks);
				}
				if (tdotasks != null && !tdotasks.isEmpty()) {
					tasks.addAll(tdotasks);
				}
				for (Task task : tasks) {
					procInstIdList.add(task.getProcessInstanceId());
				}
				List<WyApprove> daibanWyApproveList = null;
				if(procInstIdList!=null && procInstIdList.size()>0){
					daibanWyApproveList = wyApproveService.getDaibanWyApproveByProcInstIds(procInstIdList, type, keywords);
					// 处理分号隔开的内容,只显示内容,详情不显示
					dealContent(daibanWyApproveList);
				}

				model.addAttribute("daibanWyApproveList", daibanWyApproveList);
			} else if ("1".equals(flag)) {
				// 已办理的事务
				List<WyApprove> doneWyApproveList = wyApproveService.getDoneWyApproveListBymid(member.getMemberId(), type, keywords);
				// 处理分号隔开的内容,只显示内容,详情不显示
				dealContent(doneWyApproveList);

				model.addAttribute("doneWyApproveList", doneWyApproveList);
			} else if ("2".equals(flag)) {
				// 查询我发起的
				List<WyApprove> myWyApproveList = wyApproveService.getWyApproveListBymid(member.getMemberId(), type, keywords);
				// 处理分号隔开的内容,只显示内容,详情不显示
				dealContent(myWyApproveList);
				model.addAttribute("myWyApproveList", myWyApproveList);
			}

			// 分类查询
			List<WyBizDef> wyBizDefList = wyBizDefService.getAllWyBizDefByGroupid(member.getGroupID());
			model.addAttribute("wyBizDefList", wyBizDefList);

			return "modules/sns/myAffairs";
		} catch (Exception e) {
			logger.error("获取我的事务出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 对内容进行分割处理
	 * 
	 * @param myWyApproveList
	 */
	private void dealContent(List<WyApprove> myWyApproveList) {
		// 对内容进行分割,
		for (WyApprove wyApprove : myWyApproveList) {
			String content = wyApprove.getContent();
			if (StringUtils.isNotBlank(content)) {
				String[] contentArr = content.split(";");
				if (contentArr != null && contentArr.length > 0) {
					wyApprove.setContent(contentArr[0]);
				}
			}
		}
	}

	protected void setCommonParams(HttpServletRequest request, Model model) {
		model.addAttribute("selectName", request.getParameter("selectName"));
	}
	
	/*
	 * 修改受理状态
	 */
	@ResponseBody
	@RequestMapping("/changestatus")
	public WeJson changestatus(HttpServletRequest request, Model model) {
		String type = request.getParameter("type");
		String procInsId = request.getParameter("procInsId");
		
		if(StringUtils.isNotBlank(procInsId)){
			if(type.equals("repair")){
				WyRepair wyRepair = new WyRepair();
				wyRepair.setProcInsId(procInsId);
				wyRepair.setRepairstatus("1");
				wyRepairService.updateRepairstatus(wyRepair);
			}else if(type.equals("complain")){
				WyComplain wyComplain = new WyComplain();
				wyComplain.setProcInsId(procInsId);
				wyComplain.setStatus(1);
				wyComplainService.updateComplainstatus(wyComplain);
			}
		}

		return WeJson.success("");
	}

}
