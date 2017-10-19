package com.haolinbang.modules.sns.web;

import java.io.File;
import java.net.URLEncoder;
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

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.thridwy.haolong.utils.EncodesUtils;
import com.haolinbang.common.utils.ArithmeticUtil;
import com.haolinbang.common.utils.DateUtils;
import com.haolinbang.common.utils.Exceptions;
import com.haolinbang.common.utils.FreeMarkers;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.ServletUtil;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.dto.WyRelationCandidate;
import com.haolinbang.modules.sns.dto.WyRepairDto;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyActCandidate;
import com.haolinbang.modules.sns.entity.WyActForm;
import com.haolinbang.modules.sns.entity.WyBizForm;
import com.haolinbang.modules.sns.entity.WyEvaluate;
import com.haolinbang.modules.sns.entity.WyRepair;
import com.haolinbang.modules.sns.entity.WyRepairBudget;
import com.haolinbang.modules.sns.entity.WyRepairBudgetDetail;
import com.haolinbang.modules.sns.entity.WyRepairBudgetLabor;
import com.haolinbang.modules.sns.entity.WyRepairBudgetMateriel;
import com.haolinbang.modules.sns.entity.WyRepairSettlementLabor;
import com.haolinbang.modules.sns.entity.WyRepairSettlementMateriel;
import com.haolinbang.modules.sns.service.MemberService;
import com.haolinbang.modules.sns.service.WyActCandidateService;
import com.haolinbang.modules.sns.service.WyActFormService;
import com.haolinbang.modules.sns.service.WyBizFormService;
import com.haolinbang.modules.sns.service.WyEvaluateService;
import com.haolinbang.modules.sns.service.WyPrivateRepairService;
import com.haolinbang.modules.sns.service.WyRepairBudgetDetailService;
import com.haolinbang.modules.sns.service.WyRepairBudgetLaborService;
import com.haolinbang.modules.sns.service.WyRepairBudgetMaterielService;
import com.haolinbang.modules.sns.service.WyRepairBudgetService;
import com.haolinbang.modules.sns.service.WyRepairService;
import com.haolinbang.modules.sns.service.WyRepairSettlementLaborService;
import com.haolinbang.modules.sns.service.WyRepairSettlementMaterielService;
import com.haolinbang.modules.sns.util.ActUtil;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sns.util.RoomUtils;

@Controller
@RequestMapping("/wyPrivateRepair")
public class WyPrivateRepairController extends WuyeController {

	@Autowired
	private WyPrivateRepairService wyPrivateRepairService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private WyRepairService wyRepairService;

	@Autowired
	private WyEvaluateService wyEvaluateService;

	@Autowired
	private WyActCandidateService wyActCandidateService;

	@Autowired
	private WyRepairBudgetMaterielService wyRepairBudgetMaterielService;

	@Autowired
	private WyRepairBudgetService wyRepairBudgetService;

	@Autowired
	private WyRepairBudgetDetailService wyRepairBudgetDetailService;

	@Autowired
	private WyRepairBudgetLaborService wyRepairBudgetLaborService;

	@Autowired
	private WyActFormService wyActFormService;

	@Autowired
	private WyBizFormService wyBizFormService;

	@Autowired
	private MemberService memberService;

	private String bizKey = "biz_private_repair";// 业务处理key

	@Autowired
	private WyRepairSettlementLaborService wyRepairSettlementLaborService;

	@Autowired
	private WyRepairSettlementMaterielService wyRepairSettlementMaterielService;

	/**
	 * 私人维修申请表单
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apply")
	public String apply(Model model) {
		try {
			Member member = MemberUtils.getCurrentMember();
			Room room = RoomUtils.getCurrentRoom();

			model.addAttribute("member", member);
			model.addAttribute("room", room);
			model.addAttribute("bizKey", bizKey);
			model.addAttribute("node", "start");

			return "modules/sns/wyPrivateRepair/apply";
		} catch (Exception e) {
			logger.error("私人维修申请表单报错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 报修更新
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateRepair")
	public WeJson updateRepair(HttpServletRequest request) {
		try {
			Member member = MemberUtils.getCurrentMember();

			String id = request.getParameter("contentid");
			String repairtype = request.getParameter("repairtype");
			String rname = request.getParameter("rname");
			String rphone = request.getParameter("rphone");
			String rcontent = request.getParameter("rcontent");
			String rdetail = request.getParameter("rdetail");
			String beginTime = request.getParameter("beginTime");
			String picture1 = request.getParameter("picture1");
			String picture2 = request.getParameter("picture2");
			String picture3 = request.getParameter("picture3");
			String roomId = request.getParameter("roomId");

			WyRepair wyRepairs = new WyRepair();
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
			wyRepairs = wyRepairService.get(id);
			wyRepairs.setMemberid(member.getMemberId());
			wyRepairs.setRepairtype(repairtype);
			wyRepairs.setApplyname(rname);
			wyRepairs.setPhone(rphone);
			wyRepairs.setContent(rcontent);
			wyRepairs.setContentdetail(rdetail);
			wyRepairs.setAppointmenttime(DateUtils.parseDate(beginTime));
			wyRepairs.setImgurl((picture1 != null ? picture1 + "," : "") + (picture2 != null ? picture2 + "," : "") + (picture3 != null ? picture3 : ""));
			wyRepairs.setCreatetime(new Date());
			wyRepairs.setUpdatetime(new Date());
			wyRepairs.setRepairstatus("0");
			wyRepairs.setRoomId(StringUtils.toInteger(roomId));

			wyRepairService.save(wyRepairs);

			return WeJson.success("edit");
		} catch (Exception e) {
			logger.error("报修更新出错:{}", e);
			return WeJson.fail("报修更新出错");
		}
	}

	// 删除报修单
	@ResponseBody
	@RequestMapping("/delRepair")
	public WeJson delRepair(String id) {
		try {
			WyRepair wyRepair = new WyRepair();
			wyRepair.setId(id);
			wyRepairService.delete(wyRepair);
			return WeJson.success("删除成功");
		} catch (Exception e) {
			logger.error("删除报修单出错:{} ", e.getMessage());
			return WeJson.fail("删除报修单出错");
		}
	}

	/**
	 * 删除维修照片
	 * 
	 * @param url
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delphoto")
	public WeJson delphoto(String url, HttpServletRequest request) throws Exception {
		try {
			String imgurl = request.getServerName() + ":" + request.getServerPort() + url;
			// String imgurl1 =
			// "D:\\软件\\apache-tomcat-7.0.75-windows-x64\\apache-tomcat-7.0.75\\webapps\\hlb-shequ-server\\static\\image\\repair1 - 副本.png";
			File file = new File(imgurl);
			file.delete();
			return WeJson.success("success");
		} catch (Exception e) {
			logger.error("删除照片出错:{}", e);
			return WeJson.fail("删除照片出错");
		}
	}

	/**
	 * 保存报修信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/applySave")
	public WeJson applySave(HttpServletRequest request) {
		try {
			Member member = MemberUtils.getCurrentMember();
			String repairtype = request.getParameter("repairtype");
			String rname = request.getParameter("rname");
			String rphone = request.getParameter("rphone");
			String rcontent = request.getParameter("rcontent");
			String rdetail = request.getParameter("rdetail");
			String beginTime = request.getParameter("beginTime");
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

			Date date = new Date();
			WyRepair wyRepairs = new WyRepair();
			wyRepairs.setMemberid(member.getMemberId());
			wyRepairs.setRepairtype("1");
			wyRepairs.setApplyname(rname);
			wyRepairs.setPhone(rphone);
			wyRepairs.setContent(rcontent);
			wyRepairs.setContentdetail(rdetail);
			wyRepairs.setAppointmenttime(DateUtils.parseDate(beginTime));
			//wyRepairs.setImgurl((picture1 != null ? picture1 + "," : "") + (picture2 != null ? picture2 + "," : "") + (picture3 != null ? picture3 : ""));
			wyRepairs.setImgurl(imgs);
			wyRepairs.setCreatetime(date);
			wyRepairs.setUpdatetime(date);
			wyRepairs.setRepairstatus("0");
			//wyRepairs.setRoomId(StringUtils.toInteger(roomId));
			//wyRepairs.setRoomId(StringUtils.toInteger(member.getRoomId()));
			WeJson weJson = wyPrivateRepairService.applySave(bizKey, wyRepairs);
			return weJson;
		} catch (Exception e) {
 			logger.error("保存出错:{}", e);
			return WeJson.fail("保存出错，请稍后重试！");
		}
	}

	/**
	 * 客服中心
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/csc")
	public String csc(String bizId, HttpServletRequest request, Model model) {
		try {

			// 设置常用参数,用于传递参数
			setCommonParams(request, model);

			setWyRepairInfo(bizId, model);
			model.addAttribute("node", "csc");
			//return "modules/sns/wyPrivateRepair/csc";
			return "modules/sns/wyPrivateRepair/apply";
		} catch (Exception e) {
			logger.error("出现错误:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 维修人员抢单
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/grabOrder")
	public String grabOrder(String bizId, Model model) {
		try {
			setWyRepairInfo(bizId, model);
			model.addAttribute("node", "grabOrder");
			//return "modules/sns/wyPrivateRepair/grabOrder";
			return "modules/sns/wyPrivateRepair/apply";
		} catch (Exception e) {
			logger.error("维修人员抢单出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 维修人员预算
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/budget")
	public String budget(String bizId, Model model) {
		try {
			setWyRepairInfo(bizId, model);
			setWyRepairBudgetInfo(bizId, model);
			return "modules/sns/wyPrivateRepair/budget";
		} catch (Exception e) {
			logger.error("维修人员预算出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 发起人审核
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/audit")
	public String audit(String bizId, Model model) {
		try {
			setWyRepairInfo(bizId, model);
			setWyRepairBudgetInfo(bizId, model);
			return "modules/sns/wyPrivateRepair/audit";
		} catch (Exception e) {
			logger.error("维修人员预算出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 维修人员开始维修
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/startRepair")
	public String upateAppointmentTime(String bizId, Model model) {
		try {
			setWyRepairInfo(bizId, model);
			model.addAttribute("node", "startRepair");
			//return "modules/sns/wyPrivateRepair/startRepair";
			return "modules/sns/wyPrivateRepair/apply";
		} catch (Exception e) {
			logger.error("维修人员预算出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 维修更改预约时间
	 * 
	 * @param bizId
	 * @param appointmentTime
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateAppointmentTime")
	public WeJson updateAppointmentTime(String bizId, String appointmentTime, Model model) {
		try {
			Date appointmentDate = DateUtils.parseDate(appointmentTime);
			if (wyPrivateRepairService.upateAppointmentTime(bizId, appointmentDate)) {
				return WeJson.success("更新成功");
			}
			return WeJson.fail("更新失败");
		} catch (Exception e) {
			logger.error("更新维修时间出错:{}", e);
			return WeJson.fail("更新维修时间出错");
		}
	}

	/**
	 * 维修人员完成维修,结算
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/settlement")
	public String settlement(String bizId, Model model) throws Exception {
		try {
			// 查询原来的结算，展示出来
			setWyRepairInfo(bizId, model);

			// 拷贝到结算信息
			List<WyRepairSettlementMateriel> wyRepairSettlementMaterielList = wyRepairSettlementMaterielService.getWyRepairSettlementMaterielsByRepairId(bizId);
			List<WyRepairSettlementLabor> wyRepairSettlementLaborList = wyRepairSettlementLaborService.getWyRepairSettlementLaborsByRepairId(bizId);

			int materielNum = 0;
			double totalCost = 0.0;
			if (wyRepairSettlementMaterielList == null || wyRepairSettlementMaterielList.isEmpty()) {

				wyRepairSettlementMaterielList = new ArrayList<WyRepairSettlementMateriel>();
				// 查询物料
				List<WyRepairBudgetMateriel> wyRepairBudgetMaterielList = wyRepairBudgetMaterielService.getWyRepairBudgetMaterielByRepairId(bizId);
				for (WyRepairBudgetMateriel wyRepairBudgetMateriel : wyRepairBudgetMaterielList) {
					WyRepairSettlementMateriel wyRepairSettlementMateriel = new WyRepairSettlementMateriel();
					BeanUtils.copyProperties(wyRepairBudgetMateriel, wyRepairSettlementMateriel);
					wyRepairSettlementMaterielList.add(wyRepairSettlementMateriel);
				}
				if (wyRepairSettlementMaterielList.size() > 0) {
					int b = wyRepairSettlementMaterielService.saveList(wyRepairSettlementMaterielList);
				}
			}

			if (wyRepairSettlementLaborList == null || wyRepairSettlementLaborList.isEmpty()) {

				wyRepairSettlementLaborList = new ArrayList<WyRepairSettlementLabor>();
				// 查询人力成本
				List<WyRepairBudgetLabor> wyRepairBudgetLaborList = wyRepairBudgetLaborService.getWyRepairBudgetLaborByRepairId(bizId);
				for (WyRepairBudgetLabor wyRepairBudgetLabor : wyRepairBudgetLaborList) {
					WyRepairSettlementLabor wyRepairSettlementLabor = new WyRepairSettlementLabor();
					BeanUtils.copyProperties(wyRepairBudgetLabor, wyRepairSettlementLabor);
					wyRepairSettlementLaborList.add(wyRepairSettlementLabor);
				}
				if (wyRepairSettlementLaborList.size() > 0) {
					// 保存预算信息
					int a = wyRepairSettlementLaborService.saveList(wyRepairSettlementLaborList);
				}
			}

			for (WyRepairSettlementMateriel wyRepairSettlementMateriel : wyRepairSettlementMaterielList) {
				totalCost = ArithmeticUtil.round(ArithmeticUtil.add(wyRepairSettlementMateriel.getCount() == null ? 0.0 : wyRepairSettlementMateriel.getCount(), totalCost),
						ArithmeticUtil.ROUND_SCALE_2);
				materielNum += wyRepairSettlementMateriel.getNum();

			}
			for (WyRepairSettlementLabor wyRepairSettlementLabor : wyRepairSettlementLaborList) {
				totalCost = ArithmeticUtil.round(ArithmeticUtil.add(wyRepairSettlementLabor.getCount() == null ? 0.0 : wyRepairSettlementLabor.getCount(), totalCost),
						ArithmeticUtil.ROUND_SCALE_2);
			}

			model.addAttribute("materielNum", materielNum);
			model.addAttribute("totalCost", totalCost);

			model.addAttribute("wyRepairSettlementMaterielList", wyRepairSettlementMaterielList);
			model.addAttribute("wyRepairSettlementLaborList", wyRepairSettlementLaborList);

			return "modules/sns/wyPrivateRepair/settlement";
		} catch (Exception e) {
			logger.error("维修人员完成维修,结算出错:{}", e);
			return "error/500";
		}
	}

	/**
	 * 发起人付款
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/topay")
	public String topay(String bizId, Model model) throws Exception {
		try {
			Member member = MemberUtils.getCurrentMember();
			Room room = RoomUtils.getCurrentRoom();

			setWyRepairInfo(bizId, model);
			double totalCost = setWyRepairSettleInfo(bizId, model);
			// 计算用户需要支付的金额
			String url = Global.getConfig("pay.server.url") + "/index2.html";
			String x0 = "2";// 商品号，费用分类
			String x1 = room.getWYID().toString();// 物业项目ID
			String x2 = "0";// 合同ID
			String x4 = room.getSource();// 公司代码,接口代码
			String x5 = ServletUtil.getOuterUrl() + "/wyPrivateRepair/evaluate?ispaied=1&bizId=" + bizId;// 回调地址
			String from = "0";
			String bzid = "2";
			String price = String.valueOf(totalCost);

			// 对回调的链接进行编码
			x5 = URLEncoder.encode(x5, "UTF-8");

			String[] arr_data = { x0, x1, x2, x5 };
			String SIGN = EncodesUtils.encrypt2(arr_data, x4);

			StringBuilder sb = new StringBuilder(url);
			sb.append("?");
			sb.append("x0=").append(x0).append("&");
			sb.append("x1=").append(x1).append("&");
			sb.append("x2=").append(x2).append("&");
			sb.append("x4=").append(x4).append("&");
			sb.append("x5=").append(x5).append("&");
			sb.append("fromtype=").append(from).append("&");
			sb.append("bzid=").append(bzid).append("&");
			sb.append("SIGN=").append(SIGN).append("&");
			sb.append("price=").append(price);

			model.addAttribute("sb", sb.toString());
			return "modules/sns/wyPrivateRepair/topay";
		} catch (Exception e) {
			logger.error("发起人付款出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 发起人评价
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/evaluate")
	public String evaluate(String bizId, Model model) throws Exception {
		try {
			setWyRepairInfo(bizId, model, false);
			return "modules/sns/wyPrivateRepair/evaluate";
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
			WyRepair wyRepair = wyRepairService.get(wyEvaluate.getRelationId() + "");

			return "redirect:/wuye/fulfilTask?bizId=" + wyEvaluate.getRelationId() + "&procInsId=" + wyRepair.getProcInsId();
		} catch (Exception e) {
			logger.error("评价出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	// 评价
	/**
	 * 
	 * @param wyEvaluate
	 * @param model
	 * @return
	 */
	@RequestMapping("/show")
	public String show(String bizId, Model model) {
		try {
			setWyRepairInfo(bizId, model, false);
			model.addAttribute("node", "show");
			//return "modules/sns/wyPrivateRepair/show";
			return "modules/sns/wyPrivateRepair/apply";
		} catch (Exception e) {
			logger.error("发起人评价出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	private void setWyRepairInfo(String bizId, Model model) {
		setWyRepairInfo(bizId, model, true);
	}

	/**
	 * 设置物业信息
	 * 
	 * @param bizId
	 * @param model
	 */
	private void setWyRepairInfo(String bizId, Model model, boolean flag) {
		Member member = MemberUtils.getCurrentMember();
		Room room = RoomUtils.getCurrentRoom();

		WyRepair wyRepair = wyRepairService.get(bizId);
		WyRepairDto wyRepair2 = null;
		if (wyRepair != null) {
			wyRepair2 = new WyRepairDto();
			BeanUtils.copyProperties(wyRepair, wyRepair2);
			Member wyRepairMember = memberService.getMember(wyRepair.getMemberid());
			wyRepair2.setMember(wyRepairMember);
			wyRepair2.setRoom(room);
			wyRepair2.setImgurl(wyRepair.getImgurl());
			if (wyRepair2.getImgurl() != null) {
				wyRepair2.setImgList(Arrays.asList(wyRepair2.getImgurl().split(",")));
				model.addAttribute("imgnum", wyRepair2.getImgList().size());
			}
		}

		if (flag) {
			// 设置任务节点信息
			setTaskInfo(wyRepair.getProcInsId(), member.getMemberId().toString(), model);
		}

		model.addAttribute("member", member);
		model.addAttribute("room", room);
		model.addAttribute("wyRepair", wyRepair2);
	}

	/**
	 * 设置任务节点信息
	 * 
	 * @param procInsId
	 * @param mid
	 * @param model
	 */
	private void setTaskInfo(String procInsId, String mid, Model model) {
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

	/**
	 * 获取预算信息
	 * 
	 * @param bizId
	 * @param model
	 */
	private void setWyRepairBudgetInfo(String bizId, Model model) {
		// 查询物料
		List<WyRepairBudgetMateriel> wyRepairBudgetMaterielList = wyRepairBudgetMaterielService.getWyRepairBudgetMaterielByRepairId(bizId);
		// 查询人力成本
		List<WyRepairBudgetLabor> wyRepairBudgetLaborList = wyRepairBudgetLaborService.getWyRepairBudgetLaborByRepairId(bizId);
		int materielNum = 0;
		double totalCost = 0.0;
		for (WyRepairBudgetMateriel wyRepairBudgetMateriel : wyRepairBudgetMaterielList) {
			totalCost = ArithmeticUtil.round(ArithmeticUtil.add(wyRepairBudgetMateriel.getCount() == null ? 0.0 : wyRepairBudgetMateriel.getCount(), totalCost),
					ArithmeticUtil.ROUND_SCALE_2);
			materielNum += wyRepairBudgetMateriel.getNum();
		}

		for (WyRepairBudgetLabor wyRepairBudgetLabor : wyRepairBudgetLaborList) {
			totalCost = ArithmeticUtil.round(ArithmeticUtil.add(wyRepairBudgetLabor.getCount() == null ? 0.0 : wyRepairBudgetLabor.getCount(), totalCost),
					ArithmeticUtil.ROUND_SCALE_2);
		}
		model.addAttribute("materielNum", materielNum);
		model.addAttribute("totalCost", totalCost);
		model.addAttribute("wyRepairBudgetMaterielList", wyRepairBudgetMaterielList);
		model.addAttribute("wyRepairBudgetLaborList", wyRepairBudgetLaborList);
	}

	/**
	 * 获取结算信息
	 * 
	 * @param bizId
	 * @param model
	 */
	private double setWyRepairSettleInfo(String bizId, Model model) {
		// 拷贝到结算信息
		List<WyRepairSettlementMateriel> wyRepairSettlementMaterielList = wyRepairSettlementMaterielService.getWyRepairSettlementMaterielsByRepairId(bizId);
		List<WyRepairSettlementLabor> wyRepairSettlementLaborList = wyRepairSettlementLaborService.getWyRepairSettlementLaborsByRepairId(bizId);

		int materielNum = 0;
		double totalCost = 0.0;

		for (WyRepairSettlementMateriel wyRepairSettlementMateriel : wyRepairSettlementMaterielList) {
			totalCost = ArithmeticUtil.round(ArithmeticUtil.add(wyRepairSettlementMateriel.getCount() == null ? 0.0 : wyRepairSettlementMateriel.getCount(), totalCost),
					ArithmeticUtil.ROUND_SCALE_2);
			materielNum += wyRepairSettlementMateriel.getNum();

		}
		for (WyRepairSettlementLabor wyRepairSettlementLabor : wyRepairSettlementLaborList) {
			totalCost = ArithmeticUtil.round(ArithmeticUtil.add(wyRepairSettlementLabor.getCount() == null ? 0.0 : wyRepairSettlementLabor.getCount(), totalCost),
					ArithmeticUtil.ROUND_SCALE_2);
		}

		model.addAttribute("materielNum", materielNum);
		model.addAttribute("totalCost", totalCost);
		model.addAttribute("wyRepairSettlementMaterielList", wyRepairSettlementMaterielList);
		model.addAttribute("wyRepairSettlementLaborList", wyRepairSettlementLaborList);
		
		return totalCost;
	}

	// 报修清单
	@RequestMapping("/repairFormDetail")
	public String repairFormDetail(String repairId, Model model) throws Exception {
		// 传递repairId
		model.addAttribute("repairId", repairId);

		return "modules/sns/addWyRepairBudget";
	}

	// 提交报修材料
	@ResponseBody
	@RequestMapping("/sumbitRepairForm")
	public WeJson sumbitRepairForm(WyRepairBudget wyRepairBudget) throws Exception {
		wyRepairBudgetService.save(wyRepairBudget);

		return WeJson.success("success");
	}

	// 报修材料列表
	@RequestMapping("/repairFormList")
	public String repairFormList(String bizId, Model model) throws Exception {

		WyRepair wyRepair = wyRepairService.get(bizId);
		model.addAttribute("wyRepair", wyRepair);

		Member member = MemberUtils.getCurrentMember();
		// Member member = memberService.getMember(memberId);
		model.addAttribute("member", member);

		String formUrlType = null;
		String formHtmlType = null;
		String procDefId = null;
		String taskId = null;
		String taskKey = null;
		// 如果不存在,返回错误
		if (wyRepair == null) {
			return "";
		}
		// 获取当前要处理的的流程任务节点
		Task currTask = null;
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(wyRepair.getProcInsId()).taskAssignee(member.getMemberId().toString()).active()
				.orderByTaskCreateTime().desc().list();
		if (taskList != null && taskList.size() > 0) {
			currTask = taskList.get(0);
			model.addAttribute("currTask", currTask);
			taskId = currTask.getId();
			taskKey = currTask.getTaskDefinitionKey();
			procDefId = currTask.getProcessDefinitionId();
			formUrlType = WyConstants.TASK_FORM_COMPLETE_URL;
			formHtmlType = WyConstants.TASK_FORM_COMPLETE_HTML;
		}

		// 要认领的任务节点
		Task claimTask = null;
		List<Task> taskList2 = taskService.createTaskQuery().processInstanceId(wyRepair.getProcInsId()).taskCandidateUser(member.getMemberId().toString()).active()
				.orderByTaskCreateTime().desc().list();
		if (taskList2 != null && taskList2.size() > 0) {
			claimTask = taskList2.get(0);
			model.addAttribute("claimTask", claimTask);
			taskId = claimTask.getId();
			taskKey = claimTask.getTaskDefinitionKey();
			procDefId = claimTask.getProcessDefinitionId();
			formUrlType = WyConstants.TASK_FORM_CLAIM_URL;
			formHtmlType = WyConstants.TASK_FORM_CLAIM_HTML;
		}
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

		// 查询物料
		List<WyRepairBudgetMateriel> wyRepairBudgetMaterielList = wyRepairBudgetMaterielService.getWyRepairBudgetMaterielByRepairId(bizId);
		// 查询人力成本
		List<WyRepairBudgetLabor> wyRepairBudgetLaborList = wyRepairBudgetLaborService.getWyRepairBudgetLaborByRepairId(bizId);
		int materielNum = 0;
		double totalCost = 0.0;
		for (WyRepairBudgetMateriel wyRepairBudgetMateriel : wyRepairBudgetMaterielList) {
			totalCost = ArithmeticUtil.round(ArithmeticUtil.add(wyRepairBudgetMateriel.getCount() == null ? 0.0 : wyRepairBudgetMateriel.getCount(), totalCost),
					ArithmeticUtil.ROUND_SCALE_2);
			materielNum += wyRepairBudgetMateriel.getNum();

		}

		for (WyRepairBudgetLabor wyRepairBudgetLabor : wyRepairBudgetLaborList) {
			totalCost = ArithmeticUtil.round(ArithmeticUtil.add(wyRepairBudgetLabor.getCount() == null ? 0.0 : wyRepairBudgetLabor.getCount(), totalCost),
					ArithmeticUtil.ROUND_SCALE_2);
		}

		model.addAttribute("materielNum", materielNum);
		model.addAttribute("totalCost", totalCost);
		model.addAttribute("taskId", taskId);
		model.addAttribute("wyRepair", wyRepair);

		model.addAttribute("wyRepairBudgetMaterielList", wyRepairBudgetMaterielList);
		model.addAttribute("wyRepairBudgetLaborList", wyRepairBudgetLaborList);
		model.addAttribute("ctx", ServletUtil.getContextPath());

		if (StringUtils.isNotBlank(formHtmlType)) {
			// 项目路径

			// 实际的调用的form表单
			WyActForm wyActForm = wyActFormService.getWyActFormByProcinsidAndTaskkey(procDefId, taskKey, formHtmlType);
			if (wyActForm != null) {
				// 组装调用地址
				String formid = wyActForm.getFormId();
				WyBizForm wyBizForm = wyBizFormService.get(formid);

				String formHtml = wyBizForm.getFormHtml();
				formHtml = FreeMarkers.renderString(formHtml, model.asMap());
				model.addAttribute("formHtml", formHtml);
			}
		}

		return "modules/sns/repairFormList2";
	}

	/**
	 * 添加物料清单
	 * 
	 * @param repairId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addWyRepairBudgetDetail")
	public String addWyRepairBudget(String repairId, Model model) throws Exception {
		// 传递repairId
		model.addAttribute("repairId", repairId);

		return "modules/sns/addWyRepairBudgetDetail";
	}

	/**
	 * 添加物料清单
	 * 
	 * @param repairId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveWyRepairBudgetDetail")
	public String saveWyRepairBudgetDetail(WyRepairBudgetDetail wyRepairBudgetDetail, Model model) throws Exception {
		// 计算费用
		wyRepairBudgetDetail.setCount(ArithmeticUtil.round(ArithmeticUtil.mul(wyRepairBudgetDetail.getNum(), wyRepairBudgetDetail.getPrice()), ArithmeticUtil.ROUND_SCALE_2));

		wyRepairBudgetDetailService.save(wyRepairBudgetDetail);

		return "redirect:/wuye/repairFormList?repairId=" + wyRepairBudgetDetail.getRepairId();
	}

	/**
	 * 显示报修材料清单
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showRepairForm")
	public String showRepairForm(String id, Model model) throws Exception {
		WyRepairBudgetDetail wyRepairBudgetDetail = wyRepairBudgetDetailService.get(id);
		model.addAttribute("wyRepairBudgetDetail", wyRepairBudgetDetail);

		return "modules/sns/showRepairForm2";
	}

	/**
	 * 修改预算清单
	 * 
	 * @param id
	 * @param melzo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateWyRepairBudget")
	public String updateWyRepairBudget(String id, Model model) throws Exception {
		WyRepairBudgetDetail wyRepairBudgetDetail = wyRepairBudgetDetailService.get(id);
		model.addAttribute("wyRepairBudgetDetail", wyRepairBudgetDetail);

		return "modules/sns/addWyRepairBudgetDetail";
	}

	/**
	 * 删除预算清单
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delWyRepairBudgetDetail")
	public WeJson delWyRepairBudget(String id, Model model) throws Exception {
		WyRepairBudgetDetail wyRepairBudgetDetail = wyRepairBudgetDetailService.get(id);
		wyRepairBudgetDetailService.delete(wyRepairBudgetDetail);

		return WeJson.success("删除成功");
	}

}
