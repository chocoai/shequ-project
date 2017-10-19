package com.haolinbang.modules.sns.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.ServletUtil;
import com.haolinbang.modules.act.service.ActTaskService;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyApprove;
import com.haolinbang.modules.sns.entity.WyApproveDetail;
import com.haolinbang.modules.sns.entity.WyReBizAct;
import com.haolinbang.modules.sns.entity.WyRepair;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sns.util.RoomUtils;

/**
 * 私人维修处理类
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional(readOnly = true)
public class WyPrivateRepairService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	// 业务表
	private String privateRepairTable = "wy_repair";

	@Autowired
	private WyReBizActService wyReBizActService;

	@Autowired
	private WyRepairService wyRepairService;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private WyApproveService wyApproveService;

	@Autowired
	private WyApproveDetailService wyApproveDetailService;

	/**
	 * 保存申請信息
	 * 
	 * @param bizKey
	 * @param wyRepairs
	 * @return
	 */
	@Transactional(readOnly = false)
	public WeJson applySave(@Param("bizKey") String bizKey, @Param("wyRepairs") WyRepair wyRepairs) {
		WyApproveDetail wyApproveDetail = null;
		String procInstId = null;
		try {
			Member member = MemberUtils.getCurrentMember();
			Room room = RoomUtils.getCurrentRoom();
			// 从数据库中查询对应的流程
			WyReBizAct wyReBizAct = wyReBizActService.getBizActByBizKey(bizKey);
			if (wyReBizAct == null) {
				throw new RuntimeException("流程关联业务为空,请先配置流程和业务的关联关系");
			}

			// 启动流程
			String procDefId = wyReBizAct.getWyActDef().getProcDefId();
			if (StringUtils.isBlank(procDefId)) {
				return WeJson.fail("系统出错，请联系管理员");
			}
			// 设置组织机构信息
			wyRepairs.setSource(room.getSource());
			wyRepairs.setGroupId(room.getGroupId());
			wyRepairs.setWyid(room.getWYID());
			wyRepairs.setRoomId(StringUtils.toInteger(member.getRoomId()));
			wyRepairService.save(wyRepairs);
			Date date = new Date();
			// 启动流程实例
			Map<String, Object> vars = new HashMap<String, Object>();
			// 设置流程中的全局变量
			String source = (String) ServletUtil.getSession().getAttribute("app_source");
			String groupid = (String) ServletUtil.getSession().getAttribute("app_groupid");
			vars.put("app_source", source);
			vars.put("app_groupid", groupid);

			procInstId = actTaskService.startProcess(procDefId, privateRepairTable, wyRepairs.getId(), vars);

			// 保存节点详情
			wyApproveDetail = new WyApproveDetail();
			wyApproveDetail.setProcInstId(procInstId);
			wyApproveDetail.setApprover(member.getMemberId().toString());
			wyApproveDetail.setTaskKey("start");
			wyApproveDetail.setTaskName("开始节点");
			wyApproveDetail.setRemarks("创建任务");
			wyApproveDetail.setStartTime(date);
			wyApproveDetail.setUpdateDate(date);
			wyApproveDetail.setCreateDate(date);
			wyApproveDetailService.save(wyApproveDetail);

			// 保存流程审批信息
			WyApprove wyApprove = new WyApprove();
			wyApprove.setAvailable(true);
			wyApprove.setBizId(wyRepairs.getId());
			wyApprove.setBizTable("wy_repair");
			wyApprove.setCode(bizKey);
			wyApprove.setCurrTaskKey("start");
			wyApprove.setCurrTaskName("开始节点");
			wyApprove.setName(wyRepairs.getApplyname());
			wyApprove.setProcDefId(procDefId);
			wyApprove.setProcInstId(procInstId);
			wyApprove.setSponsor(member.getMemberId().toString());
			wyApprove.setStartTime(date);
			wyApprove.setType(bizKey);
			wyApprove.setContent(wyRepairs.getContent() + ";" + wyRepairs.getContentdetail());
			wyApproveService.save(wyApprove);

		} catch (Exception e) {
			logger.error("{}", e);
		}

		if (StringUtils.isNotBlank(procInstId)) {
			return WeJson.success("add");
		}
		return WeJson.fail("流程启动失败");
	}

	/**
	 * 更新预约时间
	 * 
	 * @param bizId
	 * @param appointmentDate
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean upateAppointmentTime(String bizId, Date appointmentDate) {
		return wyRepairService.upateAppointmentTime(bizId, appointmentDate);
	}

}
