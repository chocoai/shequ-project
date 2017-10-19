package com.haolinbang.modules.sns.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
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
import com.haolinbang.common.thridwy.haolong.bean.bean.UserInfo;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.act.entity.Act;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.dto.PvmTransitionDto;
import com.haolinbang.modules.sns.dto.WyRelationCandidate;
import com.haolinbang.modules.sns.dto.ZtreeDataDto;
import com.haolinbang.modules.sns.entity.WyActCandidate;
import com.haolinbang.modules.sns.entity.WyActCandidateDetail;
import com.haolinbang.modules.sns.entity.WyMember;
import com.haolinbang.modules.sns.entity.WyOrgGroup;
import com.haolinbang.modules.sns.entity.WyOrgStaff;
import com.haolinbang.modules.sns.service.WyActCandidateDetailService;
import com.haolinbang.modules.sns.service.WyActCandidateService;
import com.haolinbang.modules.sns.service.WyMemberService;
import com.haolinbang.modules.sns.service.WyOrgGroupService;
import com.haolinbang.modules.sns.service.WyOrgStaffService;
import com.haolinbang.modules.sns.util.ActUtil;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 候选人表或者群组表Controller
 * 
 * @author NLP
 * @version 2017-05-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyActCandidate")
public class WyActCandidateController extends BaseController {

	@Autowired
	private WyActCandidateService wyActCandidateService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private WyMemberService wyMemberService;

	@Autowired
	private WyActCandidateDetailService wyActCandidateDetailService;

	@Autowired
	private WyOrgGroupService wyOrgGroupService;

	@Autowired
	private WyOrgStaffService wyOrgStaffService;

	@ModelAttribute
	public WyActCandidate get(@RequestParam(required = false) String id) {
		WyActCandidate entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyActCandidateService.get(id);
		}
		if (entity == null) {
			entity = new WyActCandidate();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyActCandidate:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyActCandidate wyActCandidate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyActCandidate> page = wyActCandidateService.findPage(new Page<WyActCandidate>(request, response), wyActCandidate);
		model.addAttribute("page", page);
		return "modules/sns/wyActCandidateList";
	}

	@RequiresPermissions("sns:wyActCandidate:view")
	@RequestMapping(value = "form")
	public String form(WyActCandidate wyActCandidate, Model model) {
		model.addAttribute("wyActCandidate", wyActCandidate);
		return "modules/sns/wyActCandidateForm";
	}

	@RequiresPermissions("sns:wyActCandidate:edit")
	@RequestMapping(value = "save")
	public String save(WyActCandidate wyActCandidate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyActCandidate)) {
			return form(wyActCandidate, model);
		}
		wyActCandidateService.save(wyActCandidate);
		addMessage(redirectAttributes, "保存候选人表或者群组表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyActCandidate/?repage";
	}

	@RequiresPermissions("sns:wyActCandidate:edit")
	@RequestMapping(value = "delete")
	public String delete(WyActCandidate wyActCandidate, RedirectAttributes redirectAttributes) {
		wyActCandidateService.delete(wyActCandidate);
		addMessage(redirectAttributes, "删除候选人表或者群组表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyActCandidate/?repage";
	}

	/**
	 * 显示选择待办人员
	 * 
	 * @param wyActCandidate
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sns:wyActCandidate:view")
	@RequestMapping(value = "/selectHandler")
	public String selectHandler(WyActCandidate wyActCandidate, String more, Model model, HttpServletRequest request) {
		String procDefId = wyActCandidate.getProcDefId();
		String taskId = wyActCandidate.getTaskId();

		String source = getPreActivitiSource(procDefId, taskId, "1");

		WyActCandidate wyActCandidate2 = wyActCandidateService.getWyActCandidateByPidAndTaskidAndSource(procDefId, taskId, source);
		if (wyActCandidate2 != null) {
			if (WyConstants.CANDIDATE_TYPE_SAME_OTHER_TASK.equals(wyActCandidate2.getType())) {
				// 查询历史节点
				String sameTask = wyActCandidate2.getSameTaskId();
				String sameTaskName = getActivitiName(wyActCandidate.getProcDefId(), sameTask);
				model.addAttribute("sameTaskName", sameTaskName);
			} else if (WyConstants.CANDIDATE_TYPE_SELECT_STAFF.equals(wyActCandidate2.getType())) {
				// 组织机构
				List<WyActCandidateDetail> orgList = wyActCandidateDetailService.getWyActCandidateDetailByRelationIdAndType(wyActCandidate2.getId(),
						WyConstants.CANDIDATE_TYPE_GROUP);
				List<String> orgNameList = new ArrayList<String>();
				for (WyActCandidateDetail detail : orgList) {
					String name2 = getOrgName(detail.getCandidate());
					orgNameList.add(name2);
				}

				// 待办人员
				List<WyActCandidateDetail> candidateList = wyActCandidateDetailService.getWyActCandidateDetailByRelationIdAndType(wyActCandidate2.getId(),
						WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER);
				List<String> candidateNameList = new ArrayList<String>();
				for (WyActCandidateDetail detail : candidateList) {
					UserInfo userinfo = UserUtils.getEmployeeInfo(UserUtils.getUser().getSource(), UserUtils.getUser().getParentGroupId().toString(),
							StringUtils.getUnderlineId(detail.getCandidate()));
					if (userinfo != null) {
						candidateNameList.add(userinfo.getStaffName());
					}
				}

				model.addAttribute("orgNameList", StringUtils.join(orgNameList.toArray(), ","));
				model.addAttribute("candidateNameList", StringUtils.join(candidateNameList.toArray(), ","));
			}
		}
		if (wyActCandidate2 != null) {
			wyActCandidate = wyActCandidate2;
		}
		wyActCandidate.setSource(source);

		model.addAttribute("source", wyActCandidate.getSource());
		model.addAttribute("wyActCandidate", wyActCandidate);
		WyMember wyMember = new WyMember();
		// 查询会员信息
		List<WyMember> wyMemberList = wyMemberService.findList(wyMember);
		model.addAttribute("wyMemberList", wyMemberList);

		List<Act> actList = getActList(procDefId, taskId);
		model.addAttribute("actList", actList);

		String view = "modules/sns/wyActCandidateSelectHandler";

		return view;
	}

	/**
	 * 获取组织机构名称
	 * 
	 * @param id
	 * @return
	 */
	private String getOrgName(String id) {
		List<ZtreeDataDto> list = getOrgTreeList();
		for (ZtreeDataDto dto : list) {
			if (dto.getId().equals(id)) {
				return dto.getName();
			}
		}
		return null;
	}

	/**
	 * 获取节点名称
	 * 
	 * @param procDefId
	 * @param sameTask
	 * @return
	 */
	private String getActivitiName(String procDefId, String sameTask) {
		ProcessDefinitionEntity def2 = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(procDefId);
		List<ActivityImpl> activitiList2 = def2.getActivities();
		for (ActivityImpl activityImpl : activitiList2) {
			if (activityImpl.getId().equals(sameTask)) {
				return String.valueOf(activityImpl.getProperty("name"));
			}
		}
		return null;
	}

	/**
	 * 获取上一节点
	 * 
	 * @param procDefId
	 * @param taskId
	 * @param type
	 *            1=获取id;2=获取name
	 * @return
	 */
	private String getPreActivitiSource(String procDefId, String taskId, String type) {
		// 显示流程节点,查找来源节点
		List<PvmTransition> pvmTransitionList = ActUtil.getIncomingTransitions(procDefId, taskId);
		List<PvmTransitionDto> pvmTransitionDtoList = new ArrayList<PvmTransitionDto>();
		for (PvmTransition pvm : pvmTransitionList) {
			// 组装数据来源
			PvmTransitionDto pvmDto = new PvmTransitionDto();
			Object desName = pvm.getDestination().getProperty("name") == null ? pvm.getDestination().getProperty("type") : pvm.getDestination().getProperty("name");
			String desName2 = String.valueOf(desName);
			if ("startEvent".equals(desName2)) {
				desName2 = "开始节点";
			} else if ("exclusiveGateway".equals(desName2)) {
				desName2 = "网关节点";
			}
			pvmDto.setDestination(desName2);

			Object sourceName = pvm.getSource().getProperty("name") == null ? pvm.getSource().getProperty("type") : pvm.getSource().getProperty("name");
			String sourceName2 = String.valueOf(sourceName);
			if ("startEvent".equals(sourceName2)) {
				sourceName2 = "开始节点";
			} else if ("exclusiveGateway".equals(sourceName2)) {
				sourceName2 = "网关节点";
			}
			pvmDto.setSource(sourceName2);
			pvmDto.setDestinationId(pvm.getDestination().getId());
			pvmDto.setSourceId(pvm.getSource().getId());
			pvmTransitionDtoList.add(pvmDto);
		}

		// 当只有一个节点来源时
		if (pvmTransitionDtoList != null && pvmTransitionDtoList.size() == 1) {
			if ("1".equals(type)) {
				// 获取id
				return pvmTransitionDtoList.get(0).getSourceId();
			} else if ("2".equals(type)) {
				// 获取名称
				return pvmTransitionDtoList.get(0).getSource();
			}
		}
		return null;
	}

	/**
	 * 从流程中获取节点顺序
	 * 
	 * @param procDefId
	 * @param taskId
	 * @return
	 */
	private List<Act> getActList(String procDefId, String taskId) {
		// 获取流程定义名称
		ProcessDefinitionEntity def2 = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(procDefId);
		List<ActivityImpl> activitiList2 = def2.getActivities();
		Collections.sort(activitiList2, new Comparator<ActivityImpl>() {
			@Override
			public int compare(ActivityImpl o1, ActivityImpl o2) {
				return o1.getX() - o2.getX();
			}
		});
		List<Act> actList = new ArrayList<Act>();
		for (ActivityImpl act : activitiList2) {
			if ("userTask".equals(act.getProperty("type"))) {
				// 只保存之前的节点
				if (act.getId().equals(taskId)) {
					break;
				}
				Act act2 = new Act();
				act2.setTaskName(String.valueOf(act.getProperty("name")));
				act2.setTaskDefKey(act.getId());
				actList.add(act2);
			}
		}
		return actList;
	}

	/**
	 * 保存处理人员
	 * 
	 * @param wyActCandidate
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sns:wyActCandidate:edit")
	@RequestMapping(value = "saveWyActCandidate")
	public WeJson saveWyActCandidate(WyActCandidate wyActCandidate) {
		try {
			String procDefId = wyActCandidate.getProcDefId();
			String taskId = wyActCandidate.getTaskId();
			String source = wyActCandidate.getSource();

			WyActCandidate wyActCandidate2 = wyActCandidateService.getWyActCandidateByPidAndTaskidAndSource(procDefId, taskId, source);
			// 保存发起人和有上一节点指定人员
			if (WyConstants.CANDIDATE_TYPE_PREVIOUS_SPECIFY.equals(wyActCandidate.getType()) || WyConstants.CANDIDATE_TYPE_SAME_STARTER.equals(wyActCandidate.getType())
					|| WyConstants.CANDIDATE_TYPE_PRE_ACT_HANDLER.equals(wyActCandidate.getType())) {
				// 由上一节点指定和发起人为处理人

				if (wyActCandidate2 == null) {
					wyActCandidate2 = new WyActCandidate();
					wyActCandidate2.setProcDefId(procDefId);
					wyActCandidate2.setTaskId(taskId);
					wyActCandidate2.setSource(source);
				}
				// 指定用户类型
				if (WyConstants.CANDIDATE_TYPE_PREVIOUS_SPECIFY.equals(wyActCandidate.getType())) {
					String preTaskId = getPreTaskId(procDefId, taskId);

					wyActCandidate2.setSpecifyTaskId(preTaskId);
					wyActCandidate2.setType(WyConstants.CANDIDATE_TYPE_PREVIOUS_SPECIFY);
				} else if (WyConstants.CANDIDATE_TYPE_SAME_STARTER.equals(wyActCandidate.getType())) {
					wyActCandidate2.setType(WyConstants.CANDIDATE_TYPE_SAME_STARTER);
				} else if (WyConstants.CANDIDATE_TYPE_PRE_ACT_HANDLER.equals(wyActCandidate.getType())) {
					wyActCandidate2.setType(WyConstants.CANDIDATE_TYPE_PRE_ACT_HANDLER);
				}
			}
			wyActCandidate2.setAllowDelegateTask(wyActCandidate.isAllowDelegateTask());
			wyActCandidate2.setAllowBack(wyActCandidate.isAllowBack());
			wyActCandidateService.save(wyActCandidate2);
			return WeJson.success("保存成功");
		} catch (Exception e) {
			logger.error("保存联系人出错：{}", e);
			return WeJson.fail("保存出错");
		}
	}

	/**
	 * 获取前一个任务节点的key
	 * 
	 * @param procDefId
	 * @param taskId
	 * @return
	 */
	private String getPreTaskId(String procDefId, String taskId) {
		List<Act> actList = getActList(procDefId, null);
		int i = 0;
		for (Act act : actList) {
			if (act.getTaskDefKey().equals(taskId)) {
				break;
			}
			i++;
		}
		if (actList.size() > i && i >= 0) {
			return actList.get(i - 1).getTaskDefKey();
		}
		return null;
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

		return "modules/sns/wyActCandidateGraphTrace";
	}

	/**
	 * 选择组织结构人员
	 * 
	 * @param pid
	 * @param groupId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/selectOrgUser")
	public String selectOrgUser(WyRelationCandidate wyRelationCandidate, Model model) {

		String procDefId = wyRelationCandidate.getProcDefId();
		String taskId = wyRelationCandidate.getTaskId();
		String source = wyRelationCandidate.getSource();

		User user = UserUtils.getUser();
		List<ZtreeDataDto> orgTreeList = UserUtils.getOrgTreeList(user.getSource(), user.getGroupInfo().getGroupId());
		model.addAttribute("wyRelationCandidate", wyRelationCandidate);
		model.addAttribute("orgTreeList", orgTreeList);

		List<ZtreeDataDto> candidateTreeList = UserUtils.getOrgTreeList(user.getSource(), user.getGroupInfo().getGroupId());
		model.addAttribute("candidateTreeList", candidateTreeList);

		WyActCandidate wyActCandidate = wyActCandidateService.getWyActCandidateByPidAndTaskidAndSource(procDefId, taskId, source);
		if (wyActCandidate != null) {
			// 查询已经勾选的人员
			List<WyActCandidateDetail> orgList = wyActCandidateDetailService.getWyActCandidateDetailByRelationIdAndType(wyActCandidate.getId(), WyConstants.CANDIDATE_TYPE_GROUP);
			model.addAttribute("orgList", orgList);

			List<WyActCandidateDetail> candidateList = wyActCandidateDetailService.getWyActCandidateDetailByRelationIdAndType(wyActCandidate.getId(),
					WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER);
			model.addAttribute("candidateList", candidateList);
		}

		return "modules/sns/selectOrgUser";
	}

	/**
	 * 选择组织结构人员,选择后传值到父页面
	 * 
	 * @param pid
	 * @param groupId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/selectOrgUser2")
	public String selectOrgUser2(WyRelationCandidate wyRelationCandidate, Model model) {

		String procDefId = wyRelationCandidate.getProcDefId();
		String taskId = wyRelationCandidate.getTaskId();
		String source = wyRelationCandidate.getSource();

		User user = UserUtils.getUser();
		List<ZtreeDataDto> orgTreeList = UserUtils.getOrgTreeList(user.getSource(), user.getGroupInfo().getGroupId());
		model.addAttribute("wyRelationCandidate", wyRelationCandidate);
		model.addAttribute("orgTreeList", orgTreeList);

		List<ZtreeDataDto> candidateTreeList = UserUtils.getOrgTreeList(user.getSource(), user.getGroupInfo().getGroupId());
		model.addAttribute("candidateTreeList", candidateTreeList);

		WyActCandidate wyActCandidate = wyActCandidateService.getWyActCandidateByPidAndTaskidAndSource(procDefId, taskId, source);
		if (wyActCandidate != null) {
			// 查询已经勾选的人员
			List<WyActCandidateDetail> orgList = wyActCandidateDetailService.getWyActCandidateDetailByRelationIdAndType(wyActCandidate.getId(), WyConstants.CANDIDATE_TYPE_GROUP);
			model.addAttribute("orgList", orgList);

			List<WyActCandidateDetail> candidateList = wyActCandidateDetailService.getWyActCandidateDetailByRelationIdAndType(wyActCandidate.getId(),
					WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER);
			model.addAttribute("candidateList", candidateList);
		}

		return "modules/sns/selectOrgUser2";
	}

	/**
	 * 获取组织机构
	 * 
	 * @return
	 */
	private List<ZtreeDataDto> getOrgTreeList() {
		WyOrgGroup wyOrgGroup = new WyOrgGroup();
		List<ZtreeDataDto> orgTreeList = new ArrayList<ZtreeDataDto>();
		List<WyOrgGroup> groupList = wyOrgGroupService.findList(wyOrgGroup);
		for (WyOrgGroup group : groupList) {
			ZtreeDataDto dto = new ZtreeDataDto();
			dto.setId("G_" + group.getGroupId().toString());
			dto.setPid("G_" + group.getParentId().toString());
			dto.setName(group.getGroupName());
			dto.setType(group.getGroupType().toString());
			orgTreeList.add(dto);
			if (group.getGroupType() == 3) {
				List<WyOrgStaff> wyOrgStaffList = wyOrgStaffService.getWyOrgStaffByGroupId(group.getGroupId());
				for (WyOrgStaff staff : wyOrgStaffList) {
					ZtreeDataDto dto2 = new ZtreeDataDto();
					dto2.setId("M_" + staff.getStaffId().toString());
					dto2.setName(staff.getStaffName());
					dto2.setPid("G_" + group.getGroupId());
					dto2.setNocheck(true);
					orgTreeList.add(dto2);
				}
			}
		}
		return orgTreeList;
	}

	/**
	 * 通过父id获取用户信息
	 * 
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGroupStaff")
	public WeJson getGroupStaff(String pid) {

		logger.info("通过父id获取用户信息");

		return WeJson.success(pid);
	}

	/**
	 * 保存选择的用户信息
	 * 
	 * @param wyRelationCandidate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectOrgUserSave")
	public WeJson selectOrgUserSave(WyRelationCandidate wyRelationCandidate) {

		WeJson weJson = wyActCandidateService.selectOrgUserSave(wyRelationCandidate);
		return weJson;
	}

	/**
	 * 选择历史处理人员处理节点
	 * 
	 * @param pid
	 * @param groupId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/selectHistoryTaskHandlePerson")
	public String selectHistoryTaskHandlePerson(WyActCandidate wyActCandidate, Model model) {

		String procDefId = wyActCandidate.getProcDefId();
		String taskId = wyActCandidate.getTaskId();
		List<Act> actList = getActList(procDefId, taskId);
		model.addAttribute("actList", actList);

		String source = wyActCandidate.getSource();
		WyActCandidate wyActCandidate2 = wyActCandidateService.getWyActCandidateByPidAndTaskidAndSource(procDefId, taskId, source);
		if (wyActCandidate2 == null) {
			wyActCandidate2 = wyActCandidate;
		}
		model.addAttribute("wyActCandidate", wyActCandidate2);

		return "modules/sns/selectHistoryTaskHandlePerson";
	}

	/**
	 * 保存历史处理人节点
	 * 
	 * @param wyRelationCandidate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectHistoryTaskHandlePersonSave")
	public WeJson selectHistoryTaskHandlePersonSave(WyRelationCandidate wyRelationCandidate) {
		try {
			String procDefId = wyRelationCandidate.getProcDefId();
			String taskId = wyRelationCandidate.getTaskId();
			String historyNodeid = wyRelationCandidate.getHistoryActivitiId();
			WyActCandidate wyActCandidate = wyActCandidateService.getWyActCandidateByPidAndTaskidAndSource(procDefId, taskId, wyRelationCandidate.getSource());
			if (wyActCandidate == null) {
				wyActCandidate = new WyActCandidate();
				wyActCandidate.setProcDefId(procDefId);
				wyActCandidate.setTaskId(taskId);
				wyActCandidate.setSource(wyRelationCandidate.getSource());

			}
			wyActCandidate.setType(WyConstants.CANDIDATE_TYPE_SAME_OTHER_TASK);
			// 切换不同的节点
			wyActCandidate.setSameTaskId(historyNodeid);
			wyActCandidateService.save(wyActCandidate);
			return WeJson.success("处理成功");
		} catch (Exception e) {
			logger.error("处理出现错误:{}", e);
			return WeJson.fail("处理出现错误");
		}
	}
}