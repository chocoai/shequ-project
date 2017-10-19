package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;
import com.haolinbang.common.utils.StringUtils;

/**
 * 业务处理器Entity
 * 
 * @author nlp
 * @version 2017-05-26
 */
public class WyBizHandler extends DataEntity<WyBizHandler> {

	private static final long serialVersionUID = 1L;
	private String handlerName; // 处理器名称
	private String handlerInstance; // 实例名
	private String handlerClass; // 处理类的包名路径
	private String handlerMethod; // 处理方法
	private String handlerType;// 处理类的类型

	public WyBizHandler() {
		super();
	}

	public WyBizHandler(String id) {
		super(id);
	}

	@Length(min = 0, max = 100, message = "处理器名称长度必须介于 0 和 100 之间")
	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	@Length(min = 0, max = 255, message = "实例名长度必须介于 0 和 255 之间")
	public String getHandlerInstance() {
		// 将字符串首字母转成小写
		return StringUtils.lowerFirstCase(handlerInstance);
	}

	public void setHandlerInstance(String handlerInstance) {
		// 将字符串首字母转成小写
		this.handlerInstance = StringUtils.lowerFirstCase(handlerInstance);
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

	public String getHandlerType() {
		return handlerType;
	}

	public void setHandlerType(String handlerType) {
		this.handlerType = handlerType;
	}

}