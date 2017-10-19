package com.haolinbang.modules.sns.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.ServletUtil;
import com.haolinbang.modules.act.service.ActTaskService;
import com.haolinbang.modules.sns.dao.WyComplainDao;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyApprove;
import com.haolinbang.modules.sns.entity.WyApproveDetail;
import com.haolinbang.modules.sns.entity.WyComplain;
import com.haolinbang.modules.sns.entity.WyReBizAct;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sns.util.RoomUtils;

/**
 * 物业投诉表Service
 * 
 * @author wxc
 * @version 2017-07-04
 */
@Service
@Transactional(readOnly = true)
public class WyComplainService extends CrudService<WyComplainDao, WyComplain> {

	@Autowired
	private WyComplainDao wyComplainDao;

	// 业务key
	private String bizKey = "complaint_process";

	// 表名称
	private String talbeName = "wy_complain";

	@Autowired
	private WyReBizActService wyReBizActService;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private WyApproveService wyApproveService;

	@Autowired
	private WyApproveDetailService wyApproveDetailService;

	public WyComplain get(String id) {
		return super.get(id);
	}

	public List<WyComplain> findList(WyComplain wyComplain) {
		return super.findList(wyComplain);
	}

	public Page<WyComplain> findPage(Page<WyComplain> page, WyComplain wyComplain) {
		return super.findPage(page, wyComplain);
	}

	@Transactional(readOnly = false)
	public void save(WyComplain wyComplain) {
		super.save(wyComplain);
	}

	@Transactional(readOnly = false)
	public void delete(WyComplain wyComplain) {
		super.delete(wyComplain);
	}

	/**
	 * 保存投诉信息，开启流程
	 * 
	 * @param wyComplain
	 * @return
	 */
	@Transactional(readOnly = false)
	public WeJson applySave(WyComplain wyComplain) {

		Room room = RoomUtils.getCurrentRoom();

		Member member = MemberUtils.getCurrentMember();
		// 从数据库中查询对应的流程
		WyReBizAct wyReBizAct = wyReBizActService.getBizActByBizKey(bizKey);
		// 启动流程
		String procDefId = wyReBizAct.getWyActDef().getProcDefId();
		if (StringUtils.isBlank(procDefId)) {
			return WeJson.fail("系统配置有误，请联系管理员");
		}
		wyComplain.setSource(room.getSource());
		wyComplain.setGroupId(room.getGroupId());
		wyComplain.setWyid(room.getWYID());
		this.save(wyComplain);

		// 开始时间
		Date date = new Date();

		String complainId = wyComplain.getId();
		// 启动流程实例
		Map<String, Object> vars = new HashMap<String, Object>();
		
		//设置流程中的全局变量
		String source = (String) ServletUtil.getSession().getAttribute("app_source");
		String groupid = (String) ServletUtil.getSession().getAttribute("app_groupid");
		vars.put("app_source", source);
		vars.put("app_groupid", groupid);
		
		String procInstId = actTaskService.startProcess(procDefId, talbeName, complainId, vars);

		// 保存节点详情
		WyApproveDetail wyApproveDetail = new WyApproveDetail();
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
		wyApprove.setBizId(complainId);
		wyApprove.setBizTable(talbeName);
		wyApprove.setCode(bizKey);
		wyApprove.setCurrTaskKey("start");
		wyApprove.setCurrTaskName("开始节点");
		wyApprove.setName(wyComplain.getApplyname());
		wyApprove.setProcDefId(procDefId);
		wyApprove.setProcInstId(procInstId);
		wyApprove.setSponsor(member.getMemberId().toString());
		wyApprove.setStartTime(date);
		wyApprove.setType(bizKey);
		wyApprove.setContent(wyComplain.getContent() + ";" + wyComplain.getContentdetail());
		wyApproveService.save(wyApprove);

		return null;
	}

	public List<WyComplain> getWyComplainByMemberId(String mid) {
		return wyComplainDao.getWyComplainByMemberId(mid);
	}

	public List<WyComplain> getWyComplainsByProcInsIds(List<String> procInsIdList) {
		return wyComplainDao.getWyComplainsByProcInsIds(procInsIdList);
	}

	@Transactional(readOnly = false)
	public Integer updateComplainstatus(WyComplain wyComplain) {
		return wyComplainDao.updateComplainstatus(wyComplain);
	}
}