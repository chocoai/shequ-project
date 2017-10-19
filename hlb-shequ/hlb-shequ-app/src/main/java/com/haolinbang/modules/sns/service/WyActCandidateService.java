package com.haolinbang.modules.sns.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.entity.WyActCandidate;
import com.haolinbang.modules.sns.entity.WyActCandidateDetail;
import com.haolinbang.modules.sns.entity.WyActDef;
import com.haolinbang.modules.sns.entity.WyReBizAct;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.dao.WyActCandidateDao;
import com.haolinbang.modules.sns.dto.WyRelationCandidate;

/**
 * 候选人表或者群组表Service
 * 
 * @author NLP
 * @version 2017-05-03
 */
@Service
@Transactional(readOnly = true)
public class WyActCandidateService extends CrudService<WyActCandidateDao, WyActCandidate> {

	@Autowired
	private WyActCandidateDao wyActCandidateDao;

	@Autowired
	private WyActCandidateDetailService wyActCandidateDetailService;

	@Autowired
	private WyActDefService wyActDefService;

	@Autowired
	private WyReBizActService wyReBizActService;

	public WyActCandidate get(String id) {
		return super.get(id);
	}

	public List<WyActCandidate> findList(WyActCandidate wyActCandidate) {
		return super.findList(wyActCandidate);
	}

	public Page<WyActCandidate> findPage(Page<WyActCandidate> page, WyActCandidate wyActCandidate) {
		return super.findPage(page, wyActCandidate);
	}

	@Transactional(readOnly = false)
	public void save(WyActCandidate wyActCandidate) {

		// 通过用户的组织机构查询该流程的关联关系
		WyActDef actDef = wyActDefService.getBizActByDefId(wyActCandidate.getProcDefId());
		WyReBizAct wyReBizAct = wyReBizActService.getBizActByBizKey(actDef.getCategory());
		wyActCandidate.setRelationId(Integer.valueOf(wyReBizAct.getId()));
		super.save(wyActCandidate);
	}

	@Transactional(readOnly = false)
	public void delete(WyActCandidate wyActCandidate) {
		super.delete(wyActCandidate);
	}

	public WyActCandidate getWyActCandidateByProcDefIdAndTaskIdAndCandidate(String procDefId, String taskId, String candidate) {
		return wyActCandidateDao.getWyActCandidateByProcDefIdAndTaskIdAndCandidate(procDefId, taskId, candidate);
	}

	public List<WyActCandidate> getWyActCandidateByProcDefIdAndTaskIdAndType(String procDefId, String taskId, String type) {
		return wyActCandidateDao.getWyActCandidateByProcDefIdAndTaskIdAndType(procDefId, taskId, type);
	}

	public List<WyActCandidate> getWyActCandidateByProcDefIdAndTaskId(String procDefId, String taskId) {
		return wyActCandidateDao.getWyActCandidateByProcDefIdAndTaskId(procDefId, taskId);
	}

	public WyActCandidate getWyActCandidateByPidAndTaskidAndType(String procDefId, String taskId, String type) {
		return wyActCandidateDao.getWyActCandidateByPidAndTaskidAndType(procDefId, taskId, type);
	}

	public WyActCandidate getWyActCandidateByPidAndTaskidAndTypeAndSource(String procDefId, String taskId, String type, String source) {
		return wyActCandidateDao.getWyActCandidateByPidAndTaskidAndTypeAndSource(procDefId, taskId, type, source);
	}

	public WyActCandidate getWyActCandidateByPidAndTaskidAndSource(String procDefId, String taskId, String source) {
		// 通过用户的组织机构查询该流程的关联关系
		WyActDef actDef = wyActDefService.getBizActByDefId(procDefId);
		WyReBizAct wyReBizAct = wyReBizActService.getBizActByBizKey(actDef.getCategory());

		return wyActCandidateDao.getWyActCandidateByPidAndTaskidAndSource(procDefId, taskId, source, wyReBizAct.getId());
	}

	public List<WyActCandidate> getWyActCandidateSByPidAndTaskidAndSource(String procDefId, String taskId, String source) {
		return wyActCandidateDao.getWyActCandidateSByPidAndTaskidAndSource(procDefId, taskId, source);
	}

	public WyActCandidate getWyActCandidateByPidAndTaskidAndTypeAndSourceAndCandidate(String procDefId, String taskId, String source, String candidate) {
		return wyActCandidateDao.getWyActCandidateByPidAndTaskidAndTypeAndSourceAndCandidate(procDefId, taskId, source, candidate);
	}

	/**
	 * 保存所选择的用户信息
	 * 
	 * @param wyRelationCandidate
	 * @return
	 */
	@Transactional(readOnly = false)
	public WeJson selectOrgUserSave(WyRelationCandidate wyRelationCandidate) {
		String procDefId = wyRelationCandidate.getProcDefId();
		String taskId = wyRelationCandidate.getTaskId();
		String source = wyRelationCandidate.getSource();
		// 保存主表
		WyActCandidate wyActCandidate = this.getWyActCandidateByPidAndTaskidAndSource(procDefId, taskId, source);
		if (wyActCandidate == null) {
			wyActCandidate = new WyActCandidate();
			wyActCandidate.setProcDefId(procDefId);
			wyActCandidate.setTaskId(taskId);
			wyActCandidate.setSource(wyRelationCandidate.getSource());
		}
		wyActCandidate.setType(WyConstants.CANDIDATE_TYPE_SELECT_STAFF);
		this.save(wyActCandidate);

		// 先删除保存的人员信息,然后在进行保存
		wyActCandidateDetailService.deleteWyActCandidateDetailByRelationId(wyActCandidate.getId());

		List<WyActCandidateDetail> list = new ArrayList<WyActCandidateDetail>();
		// 员工信息,保存人员
		String[] candidateIds = wyRelationCandidate.getCandidateIds();
		List<WyActCandidateDetail> candidateList = new ArrayList<WyActCandidateDetail>();
		if (candidateIds != null && candidateIds.length > 0) {
			for (String id : candidateIds) {
				WyActCandidateDetail wyActCandidateDetail = new WyActCandidateDetail();
				wyActCandidateDetail.setActCandidateId(Integer.valueOf(wyActCandidate.getId()));
				wyActCandidateDetail.setCandidate(id);
				// 只能保存人员
				if (id.startsWith(WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER)) {
					wyActCandidateDetail.setCandidateType(WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER);
				}
				wyActCandidateDetail.preInsert();
				candidateList.add(wyActCandidateDetail);
			}
			if (candidateList != null && !candidateList.isEmpty()) {
				list.addAll(candidateList);
			}
		}

		// 保存部门信息
		String[] orgIds = wyRelationCandidate.getOrgIds();
		List<WyActCandidateDetail> orglist = new ArrayList<WyActCandidateDetail>();
		if (orgIds != null && orgIds.length > 0) {
			for (String id : orgIds) {
				WyActCandidateDetail wyActCandidateDetail = new WyActCandidateDetail();
				wyActCandidateDetail.setActCandidateId(Integer.valueOf(wyActCandidate.getId()));
				wyActCandidateDetail.setCandidate(id);
				// 只能保存部门
				if (id.startsWith(WyConstants.CANDIDATE_TYPE_GROUP)) {
					wyActCandidateDetail.setCandidateType(WyConstants.CANDIDATE_TYPE_GROUP);
				}
				wyActCandidateDetail.preInsert();
				orglist.add(wyActCandidateDetail);
			}
			if (orglist != null && !orglist.isEmpty()) {
				list.addAll(orglist);
			}
		}

		// 将集合保存起来
		if (list != null && !list.isEmpty()) {
			wyActCandidateDetailService.saveList(list);
		}

		return WeJson.success("保存成功");
	}
}