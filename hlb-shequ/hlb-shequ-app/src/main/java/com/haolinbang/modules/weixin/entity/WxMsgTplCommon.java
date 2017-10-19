package com.haolinbang.modules.weixin.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 模板消息通用模板定义Entity
 * @author nlp
 * @version 2017-08-28
 */
public class WxMsgTplCommon extends DataEntity<WxMsgTplCommon> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 模板编号
	private String name;		// 模板名称
	private String firstName;		// 标题字段名
	private String firstField;		// 标题字段
	private String firstColor;		// 标题颜色
	private String keyword1Name;		// 字段一名称
	private String keyword1Field;		// 字段一替换字符串
	private String keyword1Color;		// 字段一颜色
	private String keyword2Name;		// 字段2名称
	private String keyword2Field;		// 字段2替换字符串
	private String keyword2Color;		// 字段2颜色
	private String keyword3Name;		// 字段3名称
	private String keyword3Field;		// 字段3替换字符串
	private String keyword3Color;		// 字段3颜色
	private String keyword4Name;		// 字段4名称
	private String keyword4Field;		// 字段4替换字符串
	private String keyword4Color;		// 字段4颜色
	private String keyword5Name;		// 字段5名称
	private String keyword5Field;		// 字段5替换字符串
	private String keyword5Color;		// 字段5颜色
	private String remarkName;		// 备注名称
	private String remarkField;		// 备注替换字符串
	private String remarkColor;		// 备注颜色
	
	public WxMsgTplCommon() {
		super();
	}

	public WxMsgTplCommon(String id){
		super(id);
	}

	@Length(min=1, max=20, message="模板编号长度必须介于 1 和 20 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=1, max=100, message="模板名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="标题字段名长度必须介于 0 和 100 之间")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Length(min=0, max=20, message="标题字段长度必须介于 0 和 20 之间")
	public String getFirstField() {
		return firstField;
	}

	public void setFirstField(String firstField) {
		this.firstField = firstField;
	}
	
	@Length(min=0, max=20, message="标题颜色长度必须介于 0 和 20 之间")
	public String getFirstColor() {
		return firstColor;
	}

	public void setFirstColor(String firstColor) {
		this.firstColor = firstColor;
	}
	
	@Length(min=0, max=100, message="字段一名称长度必须介于 0 和 100 之间")
	public String getKeyword1Name() {
		return keyword1Name;
	}

	public void setKeyword1Name(String keyword1Name) {
		this.keyword1Name = keyword1Name;
	}
	
	@Length(min=0, max=20, message="字段一替换字符串长度必须介于 0 和 20 之间")
	public String getKeyword1Field() {
		return keyword1Field;
	}

	public void setKeyword1Field(String keyword1Field) {
		this.keyword1Field = keyword1Field;
	}
	
	@Length(min=0, max=20, message="字段一颜色长度必须介于 0 和 20 之间")
	public String getKeyword1Color() {
		return keyword1Color;
	}

	public void setKeyword1Color(String keyword1Color) {
		this.keyword1Color = keyword1Color;
	}
	
	@Length(min=0, max=100, message="字段2名称长度必须介于 0 和 100 之间")
	public String getKeyword2Name() {
		return keyword2Name;
	}

	public void setKeyword2Name(String keyword2Name) {
		this.keyword2Name = keyword2Name;
	}
	
	@Length(min=0, max=20, message="字段2替换字符串长度必须介于 0 和 20 之间")
	public String getKeyword2Field() {
		return keyword2Field;
	}

	public void setKeyword2Field(String keyword2Field) {
		this.keyword2Field = keyword2Field;
	}
	
	@Length(min=0, max=20, message="字段2颜色长度必须介于 0 和 20 之间")
	public String getKeyword2Color() {
		return keyword2Color;
	}

	public void setKeyword2Color(String keyword2Color) {
		this.keyword2Color = keyword2Color;
	}
	
	@Length(min=0, max=100, message="字段3名称长度必须介于 0 和 100 之间")
	public String getKeyword3Name() {
		return keyword3Name;
	}

	public void setKeyword3Name(String keyword3Name) {
		this.keyword3Name = keyword3Name;
	}
	
	@Length(min=0, max=20, message="字段3替换字符串长度必须介于 0 和 20 之间")
	public String getKeyword3Field() {
		return keyword3Field;
	}

	public void setKeyword3Field(String keyword3Field) {
		this.keyword3Field = keyword3Field;
	}
	
	@Length(min=0, max=20, message="字段3颜色长度必须介于 0 和 20 之间")
	public String getKeyword3Color() {
		return keyword3Color;
	}

	public void setKeyword3Color(String keyword3Color) {
		this.keyword3Color = keyword3Color;
	}
	
	@Length(min=0, max=100, message="字段4名称长度必须介于 0 和 100 之间")
	public String getKeyword4Name() {
		return keyword4Name;
	}

	public void setKeyword4Name(String keyword4Name) {
		this.keyword4Name = keyword4Name;
	}
	
	@Length(min=0, max=20, message="字段4替换字符串长度必须介于 0 和 20 之间")
	public String getKeyword4Field() {
		return keyword4Field;
	}

	public void setKeyword4Field(String keyword4Field) {
		this.keyword4Field = keyword4Field;
	}
	
	@Length(min=0, max=20, message="字段4颜色长度必须介于 0 和 20 之间")
	public String getKeyword4Color() {
		return keyword4Color;
	}

	public void setKeyword4Color(String keyword4Color) {
		this.keyword4Color = keyword4Color;
	}
	
	@Length(min=0, max=100, message="字段5名称长度必须介于 0 和 100 之间")
	public String getKeyword5Name() {
		return keyword5Name;
	}

	public void setKeyword5Name(String keyword5Name) {
		this.keyword5Name = keyword5Name;
	}
	
	@Length(min=0, max=20, message="字段5替换字符串长度必须介于 0 和 20 之间")
	public String getKeyword5Field() {
		return keyword5Field;
	}

	public void setKeyword5Field(String keyword5Field) {
		this.keyword5Field = keyword5Field;
	}
	
	@Length(min=0, max=20, message="字段5颜色长度必须介于 0 和 20 之间")
	public String getKeyword5Color() {
		return keyword5Color;
	}

	public void setKeyword5Color(String keyword5Color) {
		this.keyword5Color = keyword5Color;
	}
	
	@Length(min=0, max=100, message="备注名称长度必须介于 0 和 100 之间")
	public String getRemarkName() {
		return remarkName;
	}

	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}
	
	@Length(min=0, max=20, message="备注替换字符串长度必须介于 0 和 20 之间")
	public String getRemarkField() {
		return remarkField;
	}

	public void setRemarkField(String remarkField) {
		this.remarkField = remarkField;
	}
	
	@Length(min=0, max=20, message="备注颜色长度必须介于 0 和 20 之间")
	public String getRemarkColor() {
		return remarkColor;
	}

	public void setRemarkColor(String remarkColor) {
		this.remarkColor = remarkColor;
	}
	
}