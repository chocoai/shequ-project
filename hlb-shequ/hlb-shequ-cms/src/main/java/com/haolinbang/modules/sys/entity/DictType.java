package com.haolinbang.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 字典分类Entity
 * 
 * @author nlp
 * @version 2017-08-15
 */
public class DictType extends DataEntity<DictType> {

	private static final long serialVersionUID = 1L;
	private String code; // 分类编码
	private String name; // 分类名称
	private String value; // 分类键值

	private String valueOld;// 原来的值

	public String getValueOld() {
		return valueOld;
	}

	public void setValueOld(String valueOld) {
		this.valueOld = valueOld;
	}

	public DictType() {
		super();
	}

	public DictType(String id) {
		super(id);
	}

	@Length(min = 0, max = 50, message = "分类编码长度必须介于 0 和 50 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min = 0, max = 100, message = "分类名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 50, message = "分类键值长度必须介于 0 和 50 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}