package com.haolinbang.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 日志表Entity
 * @author nlp
 * @version 2017-08-02
 */
public class AppLog extends DataEntity<AppLog> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 日志类型
	private String title;		// 操作菜单
	private String remoteAddr;		// 操作者IP
	private String userAgent;		// 用户代理
	private String requestUri;		// 请求URI
	private String method;		// 提交方式
	private String params;		// 操作提交的数据
	private String exception;		// 异常信息
	
	public AppLog() {
		super();
	}

	public AppLog(String id){
		super(id);
	}

	@Length(min=0, max=1, message="日志类型长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="操作菜单长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="操作者IP长度必须介于 0 和 255 之间")
	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	
	@Length(min=0, max=255, message="用户代理长度必须介于 0 和 255 之间")
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	@Length(min=0, max=255, message="请求URI长度必须介于 0 和 255 之间")
	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	
	@Length(min=0, max=5, message="提交方式长度必须介于 0 和 5 之间")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
	
}