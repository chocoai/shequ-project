package com.haolinbang.modules.sns.constant;

public class WyConstants {
	/**
	 * 候选人--员工
	 */
	public final static String CANDIDATE_TYPE_ASSIGNEE_MANAGER = "M_";

	/**
	 * 候选人,组
	 */
	public final static String CANDIDATE_TYPE_GROUP = "G_";

	/**
	 * 候选人--业主
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
	 * 节点开始
	 */
	public final static String ACTIVITY_EVENT_STARTED = "start";

	/**
	 * 节点结束
	 */
	public final static String ACTIVITY_EVENT_END = "end";

	/**
	 * 任务事件处理,一般任务
	 */
	public final static String TASK_EVENT_HANDLER_NORMAL = "1";
	/**
	 * 任务事件处理,定时执行任务
	 */
	public final static String TASK_EVENT_HANDLER_JOB = "2";

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
	 * 任务删除
	 */
	public final static String TASK_EVENT_DELETE = "delete";

	/**
	 * 任务删除,定时任务
	 */
	public final static String TASK_EVENT_HANDLER_DELETE_JOB = "delete";

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
	 * 组织机构--集团
	 */
	public final static String CANDIDATE_GROUP_GRUOP = "0";

	/**
	 * 组织机构--公司
	 */
	public final static String CANDIDATE_GROUP_COMPANY = "1";

	/**
	 * 组织机构--管理处
	 */
	public final static String CANDIDATE_GROUP_MANAGEMENT = "2";

	/**
	 * 组织机构--部门
	 */
	public final static String CANDIDATE_GROUP_DEPARTMENT = "3";

	/**
	 * 点赞类型--对主题点赞
	 */
	public final static String DIANZHAN_THEME = "1";

	/**
	 * 点赞类型--对留言点赞
	 */
	public final static String DIANZHAN_LEAVE_MESSAGE = "2";

	/**
	 * 点赞类型--对回复点赞
	 */
	public final static String DIANZHAN_REPLY = "3";

	/**
	 * 缓存key--微信授权后回调地址
	 */
	public final static String CACHE_KEY_REDIRECT_URL = "_redirect_url_";

	/**
	 * 缓存key--当前用户对应的appid，即微信公众号
	 */
	public final static String CACHE_KEY_APPID = "_appid_";
	
	/**
	 * 缓存key--当前用户对应的appsecret，即微信公众号
	 */
	public final static String CACHE_KEY_APPSECRET = "_appsecret_";
	
	/**
	 * 缓存key--当前appid对应的source
	 */
	public final static String CACHE_KEY_SOURCE = "_source_";
	
	/**
	 * 缓存key--当前appid对应的groupid
	 */
	public final static String CACHE_KEY_GROUPID = "_groupid_";
	
	/**
	 * 缓存key--当前appid对应的wxaccountid
	 */
	public final static String CACHE_KEY_WXACCOUNTID = "_wxAccountId_";

	/*
	 * 
	 */

	public static void main(String[] args) {
		System.out.println("TASK_BIZ_HANDLER_Allocation".toUpperCase());
	}

}
