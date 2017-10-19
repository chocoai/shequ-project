package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 便民服务Entity
 * @author wxc
 * @version 2017-07-14
 */
public class WyConvenienceService extends DataEntity<WyConvenienceService> {
	
	private static final long serialVersionUID = 1L;
	private Integer serviceId;		// 便民服务id
	private String content;		// 内容
	private Date createTime;		// create_time
	private String source;		// source
	private Integer groupId;		// group_id
	
	public WyConvenienceService() {
		super();
	}

	public WyConvenienceService(String id){
		super(id);
	}

	@NotNull(message="便民服务id不能为空")
	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=255, message="source长度必须介于 0 和 255 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
}