package com.haolinbang.modules.sns.dto;

import java.io.Serializable;

/**
 * 业务处理dto
 * 
 * @author Administrator
 * 
 */
public class WyBizHandlerDto implements Serializable {

	private static final long serialVersionUID = 4875439440172801645L;
	private Integer code;// 编码
	private String handlerName; // 处理器名称
	private String handlerInstance; // 实例名
	private String handlerClass; // 处理类的包名路径
	private String handlerMethod; // 处理方法
	private String handlerType;// 处理类的类型

	private boolean checked;// 是否勾选

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public String getHandlerInstance() {
		return handlerInstance;
	}

	public void setHandlerInstance(String handlerInstance) {
		this.handlerInstance = handlerInstance;
	}

	public String getHandlerClass() {
		return handlerClass;
	}

	public void setHandlerClass(String handlerClass) {
		this.handlerClass = handlerClass;
	}

	public String getHandlerMethod() {
		return handlerMethod;
	}

	public void setHandlerMethod(String handlerMethod) {
		this.handlerMethod = handlerMethod;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getHandlerType() {
		return handlerType;
	}

	public void setHandlerType(String handlerType) {
		this.handlerType = handlerType;
	}
	
}
