package com.haolinbang.modules.weixin.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 通用实例消息Entity
 * 
 * @author nlp
 * @version 2017-08-29
 */
public class WxMassMsgCommon extends DataEntity<WxMassMsgCommon> {

	private static final long serialVersionUID = 1L;
	private Integer tplId; // 模板id
	private String name; // 消息名称
	private String firstValue; // 标题值
	private String keyword1Value; // 字段1的值
	private String keyword2Value; // 字段2的值
	private String keyword3Value; // 字段3的值
	private String keyword4Value; // 字段4的值
	private String keyword5Value; // 字段5的值
	private String remarkValue; // 备注字段的值
	private String url; // 点击消息后打开的url地址

	private String msgDetailId;// 关联内容id
	private String step;// 当前的步骤

	private String content;// 消息内容

	public String getMsgDetailId() {
		return msgDetailId;
	}

	public void setMsgDetailId(String msgDetailId) {
		this.msgDetailId = msgDetailId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public WxMassMsgCommon() {
		super();
	}

	public WxMassMsgCommon(String id) {
		super(id);
	}

	@NotNull(message = "模板id不能为空")
	public Integer getTplId() {
		return tplId;
	}

	public void setTplId(Integer tplId) {
		this.tplId = tplId;
	}

	@Length(min = 0, max = 100, message = "消息名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 255, message = "标题值长度必须介于 0 和 255 之间")
	public String getFirstValue() {
		return firstValue;
	}

	public void setFirstValue(String firstValue) {
		this.firstValue = firstValue;
	}

	@Length(min = 0, max = 255, message = "字段1的值长度必须介于 0 和 255 之间")
	public String getKeyword1Value() {
		return keyword1Value;
	}

	public void setKeyword1Value(String keyword1Value) {
		this.keyword1Value = keyword1Value;
	}

	@Length(min = 0, max = 255, message = "字段2的值长度必须介于 0 和 255 之间")
	public String getKeyword2Value() {
		return keyword2Value;
	}

	public void setKeyword2Value(String keyword2Value) {
		this.keyword2Value = keyword2Value;
	}

	@Length(min = 0, max = 255, message = "字段3的值长度必须介于 0 和 255 之间")
	public String getKeyword3Value() {
		return keyword3Value;
	}

	public void setKeyword3Value(String keyword3Value) {
		this.keyword3Value = keyword3Value;
	}

	@Length(min = 0, max = 255, message = "字段4的值长度必须介于 0 和 255 之间")
	public String getKeyword4Value() {
		return keyword4Value;
	}

	public void setKeyword4Value(String keyword4Value) {
		this.keyword4Value = keyword4Value;
	}

	@Length(min = 0, max = 255, message = "字段5的值长度必须介于 0 和 255 之间")
	public String getKeyword5Value() {
		return keyword5Value;
	}

	public void setKeyword5Value(String keyword5Value) {
		this.keyword5Value = keyword5Value;
	}

	@Length(min = 0, max = 255, message = "备注字段的值长度必须介于 0 和 255 之间")
	public String getRemarkValue() {
		return remarkValue;
	}

	public void setRemarkValue(String remarkValue) {
		this.remarkValue = remarkValue;
	}

	@Length(min = 0, max = 255, message = "点击消息后打开的url地址长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}