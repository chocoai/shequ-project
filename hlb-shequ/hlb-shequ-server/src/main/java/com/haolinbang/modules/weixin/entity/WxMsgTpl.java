package com.haolinbang.modules.weixin.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 具体消息模板Entity
 * 
 * @author nlp
 * @version 2017-09-19
 */
public class WxMsgTpl extends DataEntity<WxMsgTpl> {

	private static final long serialVersionUID = 1L;
	private String source; // 公司source
	private String accountId; // 对应的微信账号id
	private String name; // 模板名称
	private Integer commonTplId; // 通用消息模板id
	private String tplId; // 微信后台的模板id
	private String content; // 模板样式

	private WxMsgTplCommon commonTpl;// 模板

	

	

	public WxMsgTpl() {
		super();
	}

	public WxMsgTplCommon getCommonTpl() {
		return commonTpl;
	}

	public void setCommonTpl(WxMsgTplCommon commonTpl) {
		this.commonTpl = commonTpl;
	}

	public WxMsgTpl(String id) {
		super(id);
	}

	@Length(min = 0, max = 50, message = "公司source长度必须介于 0 和 50 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Length(min = 1, max = 64, message = "对应的微信账号id长度必须介于 1 和 64 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Length(min = 1, max = 50, message = "模板名称长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull(message = "通用消息模板id不能为空")
	public Integer getCommonTplId() {
		return commonTplId;
	}

	public void setCommonTplId(Integer commonTplId) {
		this.commonTplId = commonTplId;
	}

	@Length(min = 0, max = 100, message = "微信后台的模板id长度必须介于 0 和 100 之间")
	public String getTplId() {
		return tplId;
	}

	public void setTplId(String tplId) {
		this.tplId = tplId;
	}

	@Length(min = 0, max = 3000, message = "模板样式长度必须介于 0 和 3000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
