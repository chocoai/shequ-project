package com.haolinbang.modules.sns.web;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.act.entity.Act;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 用于处理任务的流转状态控制类
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/task")
public class WyTaskController extends BaseController {

	@Autowired
	private TaskService taskService;

	/**
	 * 签收任务
	 * 
	 * @param taskId
	 *            任务ID
	 */
	@ResponseBody
	@RequestMapping(value = "claim")
	public WeJson claim(String taskId) {
		try {
			Member member = MemberUtils.getCurrentMember();
			taskService.claim(taskId, member.getMemberId().toString());
			return WeJson.success("签收任务成功");
		} catch (Exception e) {
			logger.error("签收任务出错={}", e);
			return WeJson.fail("签收任务出错,请联系管理员");
		}
	}

	/**
	 * 完成任务
	 * 
	 * @param taskId
	 *            任务ID
	 * @param procInsId
	 *            流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment
	 *            任务提交意见的内容
	 * @param vars
	 *            任务流程变量，如下 vars.keys=flag,pass vars.values=1,true
	 *            vars.types=S,B @see
	 *            com.thinkgem.haolinbang.modules.act.utils.PropertyType
	 */
	@ResponseBody
	@RequestMapping(value = "complete")
	public String complete(Act act) {
		Member member = MemberUtils.getCurrentMember();
		taskService.complete(act.getTaskId());
		return "true";

	}

}
