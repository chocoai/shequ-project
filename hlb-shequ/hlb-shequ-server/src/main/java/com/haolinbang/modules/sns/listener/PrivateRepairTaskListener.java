package com.haolinbang.modules.sns.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.entity.WyActCandidate;
import com.haolinbang.modules.sns.entity.WyActRelation;
import com.haolinbang.modules.sns.service.WyActRelationService;

public class PrivateRepairTaskListener implements TaskListener {

	private static final long serialVersionUID = 5048754944965537823L;

	private WyActRelationService wyActRelationService = SpringContextHolder.getBean(WyActRelationService.class);

	@Override
	public void notify(DelegateTask delegateTask) {

		// 根据不同的流程进行处理流程
		String defKey = delegateTask.getTaskDefinitionKey();
		String defid = delegateTask.getProcessDefinitionId();

		// 将原来工作流的数据源切换回来
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		// 查询数据
		WyActRelation wyActRelation = wyActRelationService.getRelationCandidate(defid, defKey);
		// 有设置成原来的数据库
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS3);

		if (wyActRelation != null) {
			List<WyActCandidate> wyActCandidates = wyActRelation.getWyActCandidates();
			if (wyActCandidates != null && wyActCandidates.size() > 0)
				for (WyActCandidate wyActCandidate : wyActCandidates) {
					// 候選人
					if (WyConstants.CANDIDATE_TYPE_SELECT_STAFF.equals(wyActCandidate.getCandidateType())) {
						delegateTask.addCandidateUser(wyActCandidate.getCandidate());
						// 分組
					} else if (WyConstants.CANDIDATE_TYPE_GROUP.equals(wyActCandidate.getCandidateType())) {
						delegateTask.addCandidateGroup(wyActCandidate.getCandidate());
					}
				}
		}

	}

}
