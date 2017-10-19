package com.haolinbang.modules.sns.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 楼盘表Entity
 * @author wxc
 * @version 2017-07-15
 */
public class WyBuilding extends DataEntity<WyBuilding> {
	
	private static final long serialVersionUID = 1L;
	private Integer buildingId;		// 楼盘id
	private String source;		// 来源
	private String wyid;		// 物业id
	private String province;		// 省份
	private String city;		// 城市
	private String area;		// 区县
	private String address;		// 详细地址
	private String wybh;		// 物业编号
	private String wymc;		// 物业名称
	
	public WyBuilding() {
		super();
	}

	public WyBuilding(String id){
		super(id);
	}

	@NotNull(message="楼盘id不能为空")
	public Integer getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}
	
	@Length(min=0, max=32, message="来源长度必须介于 0 和 32 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Length(min=0, max=32, message="物业id长度必须介于 0 和 32 之间")
	public String getWyid() {
		return wyid;
	}

	public void setWyid(String wyid) {
		this.wyid = wyid;
	}
	
	@Length(min=0, max=32, message="省份长度必须介于 0 和 32 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=32, message="城市长度必须介于 0 和 32 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=32, message="区县长度必须介于 0 和 32 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=0, max=255, message="详细地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=32, message="物业编号长度必须介于 0 和 32 之间")
	public String getWybh() {
		return wybh;
	}

	public void setWybh(String wybh) {
		this.wybh = wybh;
	}
	
	@Length(min=0, max=32, message="物业名称长度必须介于 0 和 32 之间")
	public String getWymc() {
		return wymc;
	}

	public void setWymc(String wymc) {
		this.wymc = wymc;
	}
	
}