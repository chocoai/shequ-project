package com.haolinbang.modules.sns.constant;

public class WyConstants {
	/**
	 * 候选人
	 */
	public final static String CANDIDATE_TYPE_ASSIGNEE_MANAGER = "M_";

	/**
	 * 候选人,组
	 */
	public final static String CANDIDATE_TYPE_GROUP = "G_";

	/**
	 * 候选人
	 */
	public final static String CANDIDATE_TYPE_ASSIGNEE_CUSTOM = "C_";

	/**
	 * 设置待办人员
	 */
	public final static String CANDIDATE_TYPE_SELECT_STAFF = "0";

	/**
	 * 和之前的节点具有相同的处理人任务节点
	 */
	public final static String CANDIDATE_TYPE_SAME_OTHER_TASK = "1";

	/**
	 * 处理人和发起人一致
	 */
	public final static String CANDIDATE_TYPE_SAME_STARTER = "2";

	/**
	 * 处理人由上一个节点指定
	 */
	public final static String CANDIDATE_TYPE_PREVIOUS_SPECIFY = "3";

	/**
	 * 有多个来源的任务节点指定人员
	 */
	public final static String CANDIDATE_TYPE_MULTI_SOURCE = "4";

	/**
	 * 和上一节点处理人相同
	 */
	public final static String CANDIDATE_TYPE_PRE_ACT_HANDLER = "5";

	/**
	 * 任务创建
	 */
	public final static String TASK_EVENT_CREATE = "create";

	/**
	 * 任务分配处理人员
	 */
	public final static String TASK_EVENT_ASSIGN = "assignment";

	/**
	 * 任务完成
	 */
	public final static String TASK_EVENT_COMPLETE = "complete";

	/**
	 * 任务事件处理,一般任务
	 */
	public final static String TASK_EVENT_HANDLER_NORMAL = "1";
	/**
	 * 任务事件处理,定时执行任务
	 */
	public final static String TASK_EVENT_HANDLER_JOB = "2";

	/**
	 * 任务删除
	 */
	public final static String TASK_EVENT_DELETE = "delete";

	/**
	 * 任务删除,定时任务
	 */
	public final static String TASK_EVENT_HANDLER_DELETE_JOB = "delete";

	/**
	 * 节点开始
	 */
	public final static String ACTIVITY_EVENT_STARTED = "start";

	/**
	 * 节点结束
	 */
	public final static String ACTIVITY_EVENT_END = "end";

	/**
	 * 任务节点认领任务url
	 */
	public final static String TASK_FORM_CLAIM_URL = "claim_url";

	/**
	 * 任务节点认领任务html
	 */
	public final static String TASK_FORM_CLAIM_HTML = "claim_html";

	/**
	 * 任务节点认领任务url
	 */
	public final static String TASK_FORM_COMPLETE_URL = "complete_url";

	/**
	 * 任务节点认领任务url
	 */
	public final static String TASK_FORM_COMPLETE_HTML = "complete_html";

	/**
	 * 任务节点完成后转发
	 */
	public final static String TASK_FORM_URL_COMPLETED_REDIRECT_URL = "completed_redirect_url";

	/**
	 * 任务节点完成后转发
	 */
	public final static String TASK_FORM_URL_COMPLETED_REDIRECT_HTML = "completed_redirect_html";

	/**
	 * 任务节点中选择表单
	 */
	public final static String TASK_FORM_URL_SELECT_HANDLER_URL = "select_handler_url";

	/**
	 * 任务节点中选择表单
	 */
	public final static String TASK_FORM_URL_SELECT_HANDLER_HTML = "select_handler_html";

	/**
	 * 委托办理人html
	 */
	public final static String TASK_FORM_DELEGATE_HANDLER_HTML = "delegate_task_handler_html";

	/**
	 * 委托办理处理按钮
	 */
	public static final String TASK_FORM_DELEGATE_HANDLER_BUTTON = "delegate_task_handler_button";

	/**
	 * 发起流程表单
	 */
	public final static String TASK_FORM_URL_START = "start";

	/**
	 * 任务结束后跳转的表单
	 */
	public final static String TASK_FORM_URL_END = "end";

	/**
	 * 表单url跳转地址
	 */
	public final static String BIZ_FORM_TYPE_PRIVATE_REPAIR = "1";

	/**
	 * 自定义表单
	 */
	public final static String BIZ_FORM_TYPE_PUBLIC_REPAIR = "2";

	/**
	 * 定义的处理类，公用处理类,${commonEventListener}
	 */
	public final static String ACTIVITI_VALUE_COMMON_EVENT_LISTENER = "commonEventListener";

	/**
	 * 公用流向控制值,${commonFlow}
	 */
	public final static String ACTIVITI_VALUE_COMMON_FLOW = "commonFlow";

	/**
	 * 公用定时器，时间,${commonTime}
	 */
	public final static String ACTIVITI_VALUE_COMMON_TIME = "commonTime";

	/**
	 * 定时任务-超时任务
	 */
	public final static String TASK_JOB_TIMEOUT = "1";

	/**
	 * 定时任务-循环任务
	 */
	public final static String TASK_JOB_CIRCLE = "2";

	/**
	 * 定时任务-节点创建时的定时任务
	 */
	public final static String TASK_JOB_ACTIVITI_CREATE = "3";

	/**
	 * 任务处理类--微信通知
	 */
	public final static String TASK_BIZ_HANDLER_NOTICE_WEIXIN = "11";

	/**
	 * 任务处理类--短信通知
	 */
	public final static String TASK_BIZ_HANDLER_NOTICE_SMS = "12";

	/**
	 * 任务处理类--分配人员
	 */
	public final static String TASK_BIZ_HANDLER_ALLOCATION = "20";

	/**
	 * 微信群发类型--图文消息
	 */
	public final static String WX_MASS_MSG_TYPE_NEWS = "1";

	/**
	 * 微信群发类型--模板消息
	 */
	public final static String WX_MASS_MSG_TYPE_TPL = "2";

	/**
	 * 微信群发给--物业id下的会员
	 */
	public final static String WX_MASS_SEND_TO_TYPE_WUYE = "1";

	/**
	 * 微信群发给--发给指定人员
	 */
	public final static String WX_MASS_SEND_TO_TYPE_SPECIFIC_USER = "2";

	/**
	 * 微信群发给--发给公司所有会员
	 */
	public final static String WX_MASS_SEND_TO_TYPE_COMPANY = "3";

	/**
	 * 微信群发给--软件公司群发
	 */
	public final static String WX_MASS_SEND_TO_TYPE_SOFTWARE_COMPANY = "4";

	/**
	 * 微信群发给--指定楼栋发送
	 */
	public final static String WX_MASS_SEND_TO_TYPE_LOUYU = "5";

	/**
	 * 微信群发消息详情-便民服务
	 */
	public final static String WX_MASS_MSG_DETAIL_CONVENIENCE_SERVICES = "1";

	/**
	 * 微信群发消息详情-公告通告详情
	 */
	public final static String WX_MASS_MSG_DETAIL_NOTICE = "2";
	
	/**
	 * 公司简介
	 */
	public final static String WX_MASS_MSG_DETAIL_COMPANYPROFILE = "3";
	

	public static void main(String[] args) {
		System.out.println("WX_MASS_SEND_TO_TYPE_specific_user".toUpperCase());
	}

}
