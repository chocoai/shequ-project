package com.haolinbang.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 注册表Entity
 * @author nlp
 * @version 2017-09-07
 */
public class Hlbregister extends DataEntity<Hlbregister> {
	
	private static final long serialVersionUID = 1L;
	private String source;		// 公司编码
	private String subsystem;		// 子系统编码，hlb_pms,hlb_pay,hlb_community,hlb_system,hlb_app
	private String rootkey;		// 根键system,machine,user
	private String mainkey;		// 主键
	private String subkey;		// 子健，可以没有内容
	private String key;		// 键
	private String value;		// 值
	private Integer regid;		// 注册id
	
	public Hlbregister() {
		super();
	}

	public Hlbregister(String id){
		super(id);
	}

	@Length(min=1, max=50, message="公司编码长度必须介于 1 和 50 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Length(min=1, max=50, message="子系统编码，hlb_pms,hlb_pay,hlb_community,hlb_system,hlb_app长度必须介于 1 和 50 之间")
	public String getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}
	
	@Length(min=1, max=50, message="根键system,machine,user长度必须介于 1 和 50 之间")
	public String getRootkey() {
		return rootkey;
	}

	public void setRootkey(String rootkey) {
		this.rootkey = rootkey;
	}
	
	@Length(min=1, max=50, message="主键长度必须介于 1 和 50 之间")
	public String getMainkey() {
		return mainkey;
	}

	public void setMainkey(String mainkey) {
		this.mainkey = mainkey;
	}
	
	@Length(min=1, max=50, message="子健，可以没有内容长度必须介于 1 和 50 之间")
	public String getSubkey() {
		return subkey;
	}

	public void setSubkey(String subkey) {
		this.subkey = subkey;
	}
	
	@Length(min=1, max=50, message="键长度必须介于 1 和 50 之间")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@Length(min=1, max=255, message="值长度必须介于 1 和 255 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@NotNull(message="注册id不能为空")
	public Integer getRegid() {
		return regid;
	}

	public void setRegid(Integer regid) {
		this.regid = regid;
	}
	
}