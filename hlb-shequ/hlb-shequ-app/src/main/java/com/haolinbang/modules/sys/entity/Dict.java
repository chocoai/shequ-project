package com.haolinbang.modules.sys.entity;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 字典Entity
 * 
 */
public class Dict extends DataEntity<Dict> {

	private static final long serialVersionUID = 1L;
	private String value; // 数据值
	private String label; // 标签名
	private String type; // 类型
	private String description;// 描述
	private String code; // 排序
	private String parentId;// 父Id

	public Dict() {
		super();
	}

	public Dict(String id) {
		super(id);
	}

	public Dict(String value, String label) {
		this.value = value;
		this.label = label;
	}

	@XmlAttribute
	@Length(min = 1, max = 100)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlAttribute
	@Length(min = 1, max = 100)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Length(min = 1, max = 100)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute
	@Length(min = 0, max = 100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	public String getSort() {
		return code;
	}

	public void setSort(String code) {
		this.code = code;
	}

	@Length(min = 1, max = 100)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return label;
	}
}