package com.haolinbang.modules.sns.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.dao.WyActCandidateDao;
import com.haolinbang.modules.sns.dao.WyActDefDao;
import com.haolinbang.modules.sns.dto.WyRelationCandidate;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyActCandidate;
import com.haolinbang.modules.sns.entity.WyActDef;
import com.haolinbang.modules.sns.entity.WyInstCandidate;
import com.haolinbang.modules.sns.entity.WyReBizAct;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sns.util.RoomUtils;

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
	private WyInstCandidateService wyInstCandidateService;

	@Autowired
	private WyActDefDao wyActDefDao;

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
		super.save(wyActCandidate);
	}

	@Transactional(readOnly = false)
	public void delete(WyActCandidate wyActCandidate) {
		super.delete(wyActCandidate);
	}

	public List<WyActCandidate> getWyActCandidateByDefidAndDefkey(String defid, String defKey) {
		return wyActCandidateDao.getWyActCandidateByDefidAndDefkey(defid, defKey);
	}

	public WyActCandidate getWyActCandidateByDefidAndDefkeyAndType(String procDefId, String taskKey, String type) {
		return wyActCandidateDao.getWyActCandidateByDefidAndDefkeyAndType(procDefId, taskKey, type);
	}

	public WyActCandidate getWyActCandidateByDefidAndDefkeyAndSource(String defid, String defKey, String source) {
		return wyActCandidateDao.getWyActCandidateByDefidAndDefkeyAndSource(defid, defKey, source);
	}

	/**
	 * 保存数据
	 * 
	 * @param wyRelationCandidate
	 * @return
	 */
	@Transactional(readOnly = false)
	public WeJson selectOrgSave(WyRelationCandidate wyRelationCandidate) {
		String procDefId = wyRelationCandidate.getProcDefId();
		String taskId = wyRelationCandidate.getTaskId();
		String source = wyRelationCandidate.getSource();
		String procInsId = wyRelationCandidate.getProcInsId();

		// 保存主表
		WyActCandidate wyActCandidate = wyActCandidateDao.getWyActCandidateByDefidAndDefkeyAndSource(procDefId, taskId, source);
		if (wyActCandidate == null || !WyConstants.CANDIDATE_TYPE_PREVIOUS_SPECIFY.equals(wyActCandidate.getType())) {
			return WeJson.fail("保存失败");
		}

		// 保存实例运行的值
		wyInstCandidateService.deleteWyActCandidateByDefIdAndTaskId(procInsId, taskId);

		List<WyInstCandidate> list = new ArrayList<WyInstCandidate>();
		// 员工信息,保存人员
		String[] candidateIds = wyRelationCandidate.getCandidateIds();
		List<WyInstCandidate> candidateList = new ArrayList<WyInstCandidate>();
		if (candidateIds != null && candidateIds.length > 0) {
			for (String id : candidateIds) {
				WyInstCandidate wyInstCandidate = new WyInstCandidate();
				wyInstCandidate.setProcInstId(procInsId);
				wyInstCandidate.setTaskId(taskId);
				wyInstCandidate.setCandidate(id);
				// 只能保存人员
				if (id.startsWith(WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER)) {
					wyInstCandidate.setCandidateType(WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER);
				}
				wyInstCandidate.preInsert();
				candidateList.add(wyInstCandidate);
			}
			if (candidateList != null && !candidateList.isEmpty()) {
				list.addAll(candidateList);
			}
		}

		// 保存部门信息
		String[] orgIds = wyRelationCandidate.getOrgIds();
		List<WyInstCandidate> orglist = new ArrayList<WyInstCandidate>();
		if (orgIds != null && orgIds.length > 0) {
			for (String id : orgIds) {
				WyInstCandidate wyInstCandidate = new WyInstCandidate();
				wyInstCandidate.setProcInstId(procInsId);
				wyInstCandidate.setTaskId(taskId);
				wyInstCandidate.setCandidate(id);
				// 只能保存部门
				if (id.startsWith(WyConstants.CANDIDATE_TYPE_GROUP)) {
					wyInstCandidate.setCandidateType(WyConstants.CANDIDATE_TYPE_GROUP);
				}
				wyInstCandidate.preInsert();
				orglist.add(wyInstCandidate);
			}
			if (orglist != null && !orglist.isEmpty()) {
				list.addAll(orglist);
			}
		}

		// 将集合保存起来
		if (list != null && !list.isEmpty()) {
			wyInstCandidateService.saveList(list);
		}

		return WeJson.success("保存成功");
	}

	public WyActCandidate getWyActCandidateByDefidAndSpecifyIdAndSource(String defid, String specifyId, String source) {
		return wyActCandidateDao.getWyActCandidateByDefidAndSpecifyIdAndSource(defid, specifyId, source);
	}

	public WyActCandidate getWyActCandidateByDefidAndDefkey2(String defid, String defKey) {
		// 查詢关联id
		Member member = MemberUtils.getCurrentMember();
		Room room = RoomUtils.getCurrentRoom();
		String source = room.getSource();
		String groupId = member.getGroupID();

		WyActDef actdef = wyActDefDao.getWyActDefByProcDefId(defid);
		String category = actdef.getCategory();
		WyReBizAct wyReBizAct = wyReBizActService.getBizActByBizKey(category);

		return wyActCandidateDao.getWyActCandidateByDefidAndDefkey2(defid, defKey, wyReBizAct.getId());
	}
}