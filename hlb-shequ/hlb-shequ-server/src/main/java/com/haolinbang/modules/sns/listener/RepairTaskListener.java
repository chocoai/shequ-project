package com.haolinbang.modules.sns.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class RepairTaskListener implements TaskListener {

	private static final long serialVersionUID = -2894555779164887441L;

	@Override
	public void notify(DelegateTask delegateTask) {

		// delegateTask.setAssignee("");
		// delegateTask.addCandidateGroup("");

		delegateTask.addCandidateUser("admin");

		String name = delegateTask.getName();
		System.out.println("name=" + name);

	}

}
