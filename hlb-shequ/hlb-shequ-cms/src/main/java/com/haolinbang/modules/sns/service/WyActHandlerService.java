package com.haolinbang.modules.sns.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sns.entity.WyActHandler;
import com.haolinbang.modules.sns.entity.WyActJob;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.dao.WyActHandlerDao;
import com.haolinbang.modules.sns.dto.WyActHandlerDto;

/**
 * 流程处理后调用方法Service
 * 
 * @author nlp
 * @version 2017-05-26
 */
@Service
@Transactional(readOnly = true)
public class WyActHandlerService extends CrudService<WyActHandlerDao, WyActHandler> {

	@Autowired
	private WyActHandlerDao wyActHandlerDao;

	@Autowired
	private WyActJobService wyActJobService;

	public WyActHandler get(String id) {
		return super.get(id);
	}

	public List<WyActHandler> findList(WyActHandler wyActHandler) {
		return super.findList(wyActHandler);
	}

	public Page<WyActHandler> findPage(Page<WyActHandler> page, WyActHandler wyActHandler) {
		return super.findPage(page, wyActHandler);
	}

	@Transactional(readOnly = false)
	public void save(WyActHandler wyActHandler) {
		super.save(wyActHandler);
	}

	@Transactional(readOnly = false)
	public void delete(WyActHandler wyActHandler) {
		super.delete(wyActHandler);
	}

	@Transactional(readOnly = false)
	public boolean saveList(List<WyActHandler> wyActHandlerList) {
		return wyActHandlerDao.saveList(wyActHandlerList);
	}

	public List<WyActHandler> getWyActHandlerListByProcDefIdAndTaskId(String procDefinitionId, String activitiId) {
		return wyActHandlerDao.getWyActHandlerListByProcDefIdAndTaskId(procDefinitionId, activitiId);
	}

	@Transactional(readOnly = false)
	public boolean deleteListByProcdefidAndTaskid(String procDefId, String taskId) {
		return wyActHandlerDao.deleteListByProcdefidAndTaskid(procDefId, taskId);
	}

	@Transactional(readOnly = false)
	public WeJson saveHandler(WyActHandlerDto wyActHandlerDto) {
		String procDefId = wyActHandlerDto.getProcDefinitionId();
		String taskId = wyActHandlerDto.getActivitiId();

		if (StringUtils.isBlank(procDefId)) {
			return WeJson.fail("流程定义id为空");
		}

		if (StringUtils.isBlank(taskId)) {
			return WeJson.fail("任务节点id为空");
		}

		List<WyActHandler> wyActHandlerList = new ArrayList<WyActHandler>();
		// 创建处理事件
		String[] createHandlers = wyActHandlerDto.getCreateHandler();
		if (createHandlers != null && createHandlers.length > 0) {
			for (String handlerInstance : createHandlers) {
				WyActHandler wyActHandler = new WyActHandler();
				wyActHandler.setProcDefId(procDefId);
				wyActHandler.setTaskId(taskId);
				wyActHandler.setHandlerInstance(handlerInstance);
				wyActHandler.setEventName(WyConstants.TASK_EVENT_CREATE);
				wyActHandler.setType(WyConstants.TASK_EVENT_HANDLER_NORMAL);// 常规处理类型
				wyActHandler.preInsert();
				wyActHandlerList.add(wyActHandler);
			}
		}

		// 分配人员处理事件
		String[] assignHandler = wyActHandlerDto.getAssignHandler();
		if (assignHandler != null && assignHandler.length > 0) {
			for (String handlerInstance : assignHandler) {
				WyActHandler wyActHandler = new WyActHandler();
				wyActHandler.setProcDefId(procDefId);
				wyActHandler.setTaskId(taskId);
				wyActHandler.setHandlerInstance(handlerInstance);
				wyActHandler.setEventName(WyConstants.TASK_EVENT_ASSIGN);
				wyActHandler.setType(WyConstants.TASK_EVENT_HANDLER_NORMAL);
				wyActHandler.preInsert();
				wyActHandlerList.add(wyActHandler);
			}
		}

		// 完成事件
		String[] completeHandler = wyActHandlerDto.getCompleteHandler();
		if (completeHandler != null && completeHandler.length > 0) {
			for (String handlerInstance : completeHandler) {
				WyActHandler wyActHandler = new WyActHandler();
				wyActHandler.setProcDefId(procDefId);
				wyActHandler.setTaskId(taskId);
				wyActHandler.setHandlerInstance(handlerInstance);
				wyActHandler.setEventName(WyConstants.TASK_EVENT_COMPLETE);
				wyActHandler.setType(WyConstants.TASK_EVENT_HANDLER_NORMAL);
				wyActHandler.preInsert();
				wyActHandlerList.add(wyActHandler);
			}
		}

		// 定时事件
		String[] jobHandler = wyActHandlerDto.getJobHandler();
		if (jobHandler != null && jobHandler.length > 0) {
			for (String jobstr : jobHandler) {
				WyActHandler wyActHandler = new WyActHandler();
				wyActHandler.setProcDefId(procDefId);
				wyActHandler.setTaskId(taskId);
				wyActHandler.setHandlerInstance(jobstr);
				wyActHandler.setEventName(WyConstants.TASK_EVENT_CREATE);
				wyActHandler.setType(WyConstants.TASK_EVENT_HANDLER_JOB);
				wyActHandler.preInsert();
				wyActHandlerList.add(wyActHandler);
			}
		}

		// 保存定时时间
		// 先查询出来,然后后面进行更新或者删除
		WyActJob wyActJob = wyActJobService.getWyActJobByProcdefidAndTaskidAndType(procDefId, taskId, WyConstants.TASK_JOB_ACTIVITI_CREATE);
		if (wyActJob == null) {
			// 数据库不存在重新新建
			wyActJob = new WyActJob();
			wyActJob.setProcDefId(procDefId);
			wyActJob.setTaskId(taskId);
			wyActJob.setType(WyConstants.TASK_JOB_ACTIVITI_CREATE);
		}
		if (wyActHandlerDto.getJobDay() != null) {
			wyActJob.setJobDay(wyActHandlerDto.getJobDay());
		}
		if (wyActHandlerDto.getJobHour() != null) {
			wyActJob.setJobHour(wyActHandlerDto.getJobHour());
		}
		if (wyActHandlerDto.getJobMinute() != null) {
			wyActJob.setJobMinute(wyActHandlerDto.getJobMinute());
		}
		wyActJobService.save(wyActJob);

		// 先删除,然后保存
		this.deleteListByProcdefidAndTaskid(procDefId, taskId);
		boolean b = this.saveList(wyActHandlerList);
		if (b) {
			return WeJson.success("保存成功");
		}
		return WeJson.fail("保存失败");
	}

}