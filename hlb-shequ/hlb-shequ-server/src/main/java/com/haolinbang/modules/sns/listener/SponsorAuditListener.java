package com.haolinbang.modules.sns.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;

/**
 * 发起人审核
 * 
 * @author Administrator
 * 
 */
public class SponsorAuditListener implements TaskListener, ExecutionListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub

	}

}
