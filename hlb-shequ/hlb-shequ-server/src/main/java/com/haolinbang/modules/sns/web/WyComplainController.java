package com.haolinbang.modules.sns.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.utils.Exceptions;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.ServletUtil;
import com.haolinbang.modules.act.entity.Act;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.dto.WyRelationCandidate;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyActCandidate;
import com.haolinbang.modules.sns.entity.WyComplain;
import com.haolinbang.modules.sns.entity.WyEvaluate;
import com.haolinbang.modules.sns.service.MemberService;
import com.haolinbang.modules.sns.service.RoomService;
import com.haolinbang.modules.sns.service.WyActCandidateService;
import com.haolinbang.modules.sns.service.WyComplainService;
import com.haolinbang.modules.sns.service.WyEvaluateService;
import com.haolinbang.modules.sns.util.ActUtil;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sns.util.RoomUtils;

@Controller
@RequestMapping("/wyComplain")
public class WyComplainController extends WuyeController {

	@Autowired
	private WyComplainService wyComplainService;

	@Autowired
	private TaskService taskService;

	// 业务key
	private String bizKey = "complaint_process";

	@Autowired
	private WyEvaluateService wyEvaluateService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private WyActCandidateService wyActCandidateService;

	/* 投诉选择页面 */
	@RequestMapping("/myComplain")
	public String myComplain(String flag, Model model) {
		try {
			flag = StringUtils.isBlank(flag) ? "0" : flag;// 0:处理中 1：未处理 2：已处理
			model.addAttribute("flag", flag);

			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
			Member member = MemberUtils.getCurrentMember();
			ServletUtil.getSession().setAttribute("memberid", member.getMemberId());

			// 获取用户可以抢单的任务,通过定义的processDefinitionId进行过滤数据
			List<Act> todoActList = getTodoActList(member.getMemberId().toString(), bizKey);
			List<String> procInsIdList = new ArrayList<String>();
			for (Act act : todoActList) {
				procInsIdList.add(act.getTask().getProcessInstanceId());
			}
			List<WyComplain> grabtasks = null;
			if (procInsIdList != null && procInsIdList.size() > 0) {
				grabtasks = wyComplainService.getWyComplainsByProcInsIds(procInsIdList);
			}

			// 获取用户待处理任务
			List<Act> toClaimActList = getToClaimActList(member.getMemberId().toString(), bizKey);
			List<String> procInsIdList2 = new ArrayList<String>();
			for (Act act : toClaimActList) {
				procInsIdList2.add(act.getTask().getProcessInstanceId());
			}
			List<WyComplain> daibanTasks = null;
			if (procInsIdList2 != null && procInsIdList2.size() > 0) {
				daibanTasks = wyComplainService.getWyComplainsByProcInsIds(procInsIdList2);
			}

			// 办理任务和待办任务合并起来
			List<WyComplain> wyComplainList = new ArrayList<WyComplain>();
			if (grabtasks != null && grabtasks.size() > 0) {
				wyComplainList.addAll(grabtasks);
			}
			if (daibanTasks != null && daibanTasks.size() > 0) {
				wyComplainList.addAll(daibanTasks);
			}

			// 多张图片展示
			for (WyComplain wc : wyComplainList) {
				if (wc.getImgurl() != null) {
					wc.setImgList(Arrays.asList(wc.getImgurl().split(",")));
				}
			}

			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

			List<WyComplain> doneWyComplainList = new ArrayList<>();
			for (WyComplain wyComplain : doneWyComplainList) {
				if (!wyComplainList.contains(wyComplain)) {
					doneWyComplainList.add(wyComplain);
				}
			}

			// 我申请的报修
			List<WyComplain> applyWyComplainList = wyComplainService.getWyComplainByMemberId(member.getMemberId().toString());

			// 获取节点流程集合
			List<String> flowNames = getFlowNames(bizKey);
			// 获取每个流程的进度
			for (WyComplain complain : applyWyComplainList) {
				if (StringUtils.isNotBlank(complain.getProcInsId())) {
					List<Task> tasks = taskService.createTaskQuery().processInstanceId(complain.getProcInsId()).active().orderByTaskCreateTime().desc().list();
					if (tasks != null && !tasks.isEmpty()) {
						for (Task task : tasks) {
							complain.setCurrStep(getTaskCurrStep(task.getName(), flowNames));
							complain.setCurrStepName(task.getName());
						}
					} else {
						complain.setCurrStep(9999999);
						complain.setCurrStepName("已完成");
					}
				}
			}

			for (WyComplain complain : wyComplainList) {
				if (StringUtils.isNotBlank(complain.getProcInsId())) {
					List<Task> tasks = taskService.createTaskQuery().processInstanceId(complain.getProcInsId()).active().orderByTaskCreateTime().desc().list();
					if (tasks != null && !tasks.isEmpty()) {
						for (Task task : tasks) {
							complain.setCurrStep(getTaskCurrStep(task.getName(), flowNames));
							complain.setCurrStepName(task.getName());
						}
					} else {
						complain.setCurrStep(9999999);
						complain.setCurrStepName("已完成");
					}
				}
			}

			model.addAttribute("doneWyComplainList", doneWyComplainList);
			model.addAttribute("wyComplainList", wyComplainList);
			model.addAttribute("applyWyComplainList", applyWyComplainList);
			model.addAttribute("flowNames", flowNames);

			return "modules/sns/complain/myComplain";
		} catch (Exception e) {
			logger.error("投诉页面出错：{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 投诉选择页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/apply")
	public String apply(Model model) {
		try {
			Member member = MemberUtils.getCurrentMember();
			ServletUtil.getSession().setAttribute("memberid", member.getMemberId());

			Room room = RoomUtils.getCurrentRoom();

			model.addAttribute("member", member);
			model.addAttribute("room", room);
			model.addAttribute("node", "start");

			return "modules/sns/complain/complain";
		} catch (Exception e) {
			logger.error("投诉页面");
			return Exceptions.deal(e);
		}
	}

	/**
	 * 投诉受理页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/accept")
	public String accept(String bizId, HttpServletRequest request, Model model) {
		try {
			// 设置常用参数,用于传递参数
			setCommonParams(request, model);

			WyComplain wyComplain = wyComplainService.get(bizId);
			// 该节点是否可以回退和委托他人办理
			setTaskInfo(wyComplain.getProcInsId(), model);
			
			Member member = MemberUtils.getCurrentMember();
			ServletUtil.getSession().setAttribute("memberid", member.getMemberId());
			setComplainMember(wyComplain, model);
			
			model.addAttribute("member", member);
			model.addAttribute("wyComplain", wyComplain);
			model.addAttribute("node", "accept");

			//return "modules/sns/complain/accept";
			return "modules/sns/complain/complain";
		} catch (Exception e) {
			logger.error("投诉受理页面出错");
			return Exceptions.deal(e);
		}
	}

	/**
	 * 投诉处理页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/deal")
	public String deal(String bizId, HttpServletRequest request, Model model) {
		try {
			// 设置常用参数,用于传递参数
			setCommonParams(request, model);

			WyComplain wyComplain = wyComplainService.get(bizId);
			// 该节点是否可以回退和委托他人办理
			setTaskInfo(wyComplain.getProcInsId(), model);
			Member member = MemberUtils.getCurrentMember();
			ServletUtil.getSession().setAttribute("memberid", member.getMemberId());
			setComplainMember(wyComplain, model);

			model.addAttribute("member", member);
			model.addAttribute("wyComplain", wyComplain);
			model.addAttribute("node", "deal");

			/*return "modules/sns/complain/deal";*/
			return "modules/sns/complain/complain";
		} catch (Exception e) {
			logger.error("投诉处理页面出错");
			return Exceptions.deal(e);
		}
	}

	/**
	 * 投诉验收
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/check")
	public String check(String bizId, HttpServletRequest request, Model model) {
		try {
			// 设置常用参数,用于传递参数
			setCommonParams(request, model);

			WyComplain wyComplain = wyComplainService.get(bizId);
			// 该节点是否可以回退和委托他人办理
			setTaskInfo(wyComplain.getProcInsId(), model);

			Member member = MemberUtils.getCurrentMember();
			ServletUtil.getSession().setAttribute("memberid", member.getMemberId());
			setComplainMember(wyComplain, model);

			model.addAttribute("member", member);
			model.addAttribute("wyComplain", wyComplain);
			model.addAttribute("node", "check");

			//return "modules/sns/complain/check";
			return "modules/sns/complain/complain";
		} catch (Exception e) {
			logger.error("验收页面出错");
			return Exceptions.deal(e);
		}
	}

	/**
	 * 发起人评价回访
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/evaluate")
	public String evaluate(String bizId, Model model) throws Exception {
		try {
			WyComplain wyComplain = wyComplainService.get(bizId);
			// 该节点是否可以回退和委托他人办理
			setTaskInfo(wyComplain.getProcInsId(), model);
			model.addAttribute("wyComplain", wyComplain);
			return "modules/sns/complain/evaluate";
		} catch (Exception e) {
			logger.error("发起人评价出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 评价
	 * 
	 * @param wyEvaluate
	 * @param model
	 * @return
	 */
	@RequestMapping("/evaluateSave")
	public String evaluateSave(WyEvaluate wyEvaluate, Model model) {
		try {
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
			Member member = MemberUtils.getCurrentMember();
			wyEvaluate.setMemberId(member.getMemberId().toString());
			wyEvaluateService.save(wyEvaluate);
			WyComplain wyComplain = wyComplainService.get(wyEvaluate.getRelationId().toString());
			model.addAttribute("wyComplain", wyComplain);

			return "redirect:/wuye/fulfilTask?bizId=" + wyEvaluate.getRelationId() + "&procInsId=" + wyComplain.getProcInsId();
		} catch (Exception e) {
			logger.error("评价出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/*
	 * 保存投诉信息
	 * 
	 * @param request
	 * 
	 * @param model
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/applySave")
	public WeJson applySave(HttpServletRequest request) {
		try {
			Member member = MemberUtils.getCurrentMember();
			String rname = request.getParameter("rname");
			String rphone = request.getParameter("rphone");
			String rcontent = request.getParameter("rcontent");
			String rdetail = request.getParameter("rdetail");
			/*String picture1 = request.getParameter("picture1");
			String picture2 = request.getParameter("picture2");
			String picture3 = request.getParameter("picture3");
			String roomId = request.getParameter("roomId");*/
			String imgs = request.getParameter("imgs");

			// 对输入数据进行校验
			if ((StringUtils.isNotBlank(rcontent) && rcontent.contains(";")) || (StringUtils.isNotBlank(rdetail) && rdetail.contains(";"))) {
				return WeJson.fail("含有敏感字符，请重新输入");
			}
			// 对字符长度进行验证
			if ((StringUtils.isNotBlank(rcontent) && rcontent.length() > 100) || (StringUtils.isNotBlank(rdetail) && rdetail.length() > 300)) {
				return WeJson.fail("你输入的内容长度太长,请减少输入字数");
			}

			WyComplain wyComplain = new WyComplain();
			wyComplain.setMemberid(member.getMemberId().toString());
			wyComplain.setApplyname(rname);
			wyComplain.setPhone(rphone);
			wyComplain.setContent(rcontent);
			wyComplain.setContentdetail(rdetail);
			//wyComplain.setImgurl((picture1 != null ? picture1 + "," : "") + (picture2 != null ? picture2 + "," : "") + (picture3 != null ? picture3 : ""));
			wyComplain.setImgurl(imgs);
			wyComplain.setCreatetime(new Date());
			wyComplain.setUpdatetime(new Date());
			wyComplain.setRoomid(StringUtils.toInteger(member.getRoomId()));

			WeJson json = wyComplainService.applySave(wyComplain);
			if (json != null) {
				return json;
			}

			return WeJson.success("add");
		} catch (Exception e) {
			logger.error("保存投诉信息出错:{}", e);
			return WeJson.fail("投诉申请出错，请稍后重试！");
		}
	}

	/*
	 * 编辑投诉信息
	 * 
	 * @param request
	 * 
	 * @param model
	 * 
	 * @return
	 */
	@RequestMapping("/editComplain")
	public String editComplain(HttpServletRequest request, Model model) {
		String complainid = request.getParameter("complainid");
		WyComplain wyComplain = new WyComplain();
		wyComplain = wyComplainService.get(complainid);
		if (wyComplain != null && wyComplain.getImgurl() != null) {
			wyComplain.setImgList(Arrays.asList(wyComplain.getImgurl().split(",")));
		}
		model.addAttribute("wyComplain", wyComplain);
		model.addAttribute("imgnum", wyComplain.getImgList().size());

		Member member = MemberUtils.getCurrentMember();
		ServletUtil.getSession().setAttribute("memberid", member.getMemberId());

		Room room = RoomUtils.getCurrentRoom();

		model.addAttribute("member", member);
		model.addAttribute("room", room);

		return "modules/sns/complain";
	}

	/**
	 * 发起人评价回访
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/show")
	public String show(String bizId, Model model) throws Exception {
		try {
			WyComplain wyComplain = wyComplainService.get(bizId);
			// 该节点是否可以回退和委托他人办理

			Member member = memberService.getMember(Integer.valueOf(wyComplain.getMemberid()));
			Room room = roomService.getRoom(member.getRoomId());
			if (wyComplain.getImgurl() != null) {
				wyComplain.setImgList(Arrays.asList(wyComplain.getImgurl().split(",")));
				model.addAttribute("imgnum", wyComplain.getImgList().size());
			}

			model.addAttribute("member", member);
			model.addAttribute("room", room);
			model.addAttribute("wyComplain", wyComplain);
			model.addAttribute("node", "show");

			//return "modules/sns/complain/show";
			return "modules/sns/complain/complain";
		} catch (Exception e) {
			logger.error("发起人评价出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 设置任务节点信息
	 * 
	 * @param procInsId
	 * @param mid
	 * @param model
	 */
	private void setTaskInfo(String procInsId, Model model) {
		Member member = MemberUtils.getCurrentMember();
		String mid = member.getMemberId().toString();

		String taskId = null;
		String defid = null;

		// 处理人员办理
		Task currTask = taskService.createTaskQuery().processInstanceId(procInsId).taskAssignee(mid).singleResult();
		if (currTask != null) {
			defid = currTask.getProcessDefinitionId();
			taskId = currTask.getTaskDefinitionKey();
			model.addAttribute("currTask", currTask);
		}
		// 待办人员办理
		Task claimTask = taskService.createTaskQuery().processInstanceId(procInsId).taskCandidateUser(mid).singleResult();
		if (claimTask != null) {
			defid = claimTask.getProcessDefinitionId();
			taskId = claimTask.getTaskDefinitionKey();
			model.addAttribute("claimTask", claimTask);
		}

		String nextActivitiId = ActUtil.getOutgoingActivitiId(procInsId, defid, taskId);

		// 查询是否需要指定下级节点办理人,是否允许回退
		WyRelationCandidate wyRelationCandidate = new WyRelationCandidate();
		WyActCandidate wyActCandidate = wyActCandidateService.getWyActCandidateByDefidAndSpecifyIdAndSource(defid, taskId, nextActivitiId);
		if (wyActCandidate != null && WyConstants.CANDIDATE_TYPE_PREVIOUS_SPECIFY.equals(wyActCandidate.getType())) {
			BeanUtils.copyProperties(wyActCandidate, wyRelationCandidate);
			wyRelationCandidate.setProcInsId(procInsId);
		}
		String source = ActUtil.getPreActivitiSource(defid, taskId, "1");
		WyActCandidate wyActCandidate2 = wyActCandidateService.getWyActCandidateByDefidAndDefkeyAndSource(defid, taskId, source);
		if (wyActCandidate2 != null) {
			wyRelationCandidate.setAllowDelegateTask(wyActCandidate2.isAllowDelegateTask());
			wyRelationCandidate.setAllowBack(wyActCandidate2.isAllowBack());
		}
		model.addAttribute("wyRelationCandidate", wyRelationCandidate);
	}
	
	private void setComplainMember(WyComplain wyComplain, Model model) {
		Member complainMember = memberService.getMember(StringUtils.toInteger(wyComplain.getMemberid()));
		if(complainMember!=null){
			Room room = roomService.getRoom(complainMember.getRoomId());
			model.addAttribute("room", room);
		}
		model.addAttribute("complainMember", complainMember);
		if (wyComplain.getImgurl() != null) {
			wyComplain.setImgList(Arrays.asList(wyComplain.getImgurl().split(",")));
			model.addAttribute("imgnum", wyComplain.getImgList().size());
		}
	}

}