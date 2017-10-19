package com.haolinbang.modules.sns.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.act.entity.Act;
import com.haolinbang.modules.act.service.ActTaskService;
import com.haolinbang.modules.act.utils.ActUtils;
import com.haolinbang.modules.sns.dao.WyRepairDao;
import com.haolinbang.modules.sns.entity.WyRepair;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 物业报修Service
 * 
 * @author nlp
 * @version 2017-04-17
 */
@Service
@Transactional(readOnly = true)
public class WyRepairService extends CrudService<WyRepairDao, WyRepair> {

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private ProcessEngineFactoryBean processEngine;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;

	public WyRepair get(String id) {
		return super.get(id);
	}

	public List<WyRepair> findList(WyRepair wyRepair) {
		return super.findList(wyRepair);
	}

	public Page<WyRepair> findPage(Page<WyRepair> page, WyRepair wyRepair) {
		return super.findPage(page, wyRepair);
	}

	@Transactional(readOnly = false)
	public void save(WyRepair wyRepair) {

		// 申请发起
		if (StringUtils.isBlank(wyRepair.getId())) {
			User user = UserUtils.getUser();
			// 启动流程
			wyRepair.setRepairstatus("1");
			wyRepair.setMemberid(StringUtils.toInteger(user.getId()));
			wyRepair.preInsert();
			wyRepair.setCreatetime(new Date());
			wyRepair.setUpdatetime(new Date());
			dao.insert(wyRepair);

			// 启动流程实例，并更新到业务数据表中
			actTaskService.startProcess(ActUtils.PD_REPAIR[0], ActUtils.PD_REPAIR[1], wyRepair.getId(), wyRepair.getContent());

		}
		// 重新编辑申请
		else {
			wyRepair.preUpdate();
			dao.update(wyRepair);
			wyRepair.getAct().setComment(("yes".equals(wyRepair.getAct().getFlag()) ? "[重申] " : "[销毁] ") + wyRepair.getAct().getComment());

			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(wyRepair.getAct().getFlag()) ? "1" : "0");
			actTaskService.complete(wyRepair.getAct().getTaskId(), wyRepair.getAct().getProcInsId(), wyRepair.getAct().getComment(), wyRepair.getContent(), vars);
		}
	}

	@Transactional(readOnly = false)
	public void delete(WyRepair wyRepair) {
		super.delete(wyRepair);
	}

	public void buildAct(WyRepair entity) {
		Act act = entity.getAct();

		Task task = taskService.createTaskQuery().processInstanceId(act.getProcInsId()).taskAssignee(UserUtils.getUser().getLoginName()).singleResult();
		act.setTask(task);
		act.setProcDefId("");

	}

}