package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 流程处理后调用方法Entity
 * 
 * @author nlp
 * @version 2017-05-26
 */
public class WyActHandler extends DataEntity<WyActHandler> {

	private static final long serialVersionUID = 1L;
	private String procDefId; // 关联id
	private String taskId; // 用户节点任务id
	private String handlerInstance; // 实例处理类
	private String handlerClass; // 处理类的包名路径
	private String handlerMethod; // 处理方法
	private String nodeType; // 节点类型
	private String nodeKey; // 节点key
	private Integer sort; // 处理顺序
	private String eventName;// 节点处理事件名称,如果是用户任务节点，则有create,assignment,complete,delete事件,其他接点有start，end，take事件
	private String type;// 任务节点类型

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public WyActHandler() {
		super();
	}

	public WyActHandler(String id) {
		super(id);
	}

	@Length(min = 1, max = 32, message = "关联id长度必须介于 1 和 32 之间")
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	@Length(min = 0, max = 64, message = "用户节点任务id长度必须介于 0 和 64 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Length(min = 0, max = 255, message = "实例处理类长度必须介于 0 和 255 之间")
	public String getHandlerInstance() {
		return handlerInstance;
	}

	public void setHandlerInstance(String handlerInstance) {
		this.handlerInstance = handlerInstance;
	}

	@Length(min = 1, max = 255, message = "处理类的包名路径长度必须介于 1 和 255 之间")
	public String getHandlerClass() {
		return handlerClass;
	}

	public void setHandlerClass(String handlerClass) {
		this.handlerClass = handlerClass;
	}

	@Length(min = 1, max = 255, message = "处理方法长度必须介于 1 和 255 之间")
	public String getHandlerMethod() {
		return handlerMethod;
	}

	public void setHandlerMethod(String handlerMethod) {
		this.handlerMethod = handlerMethod;
	}

	@Length(min = 1, max = 100, message = "节点类型长度必须介于 1 和 100 之间")
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	@Length(min = 1, max = 64, message = "节点key长度必须介于 1 和 64 之间")
	public String getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	@NotNull(message = "处理顺序不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@NotNull(message = "节点事件名称不能为空")
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

}