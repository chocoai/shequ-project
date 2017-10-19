package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 维修物料明细表单Entity
 * @author wxc
 * @version 2017-05-05
 */
public class WyRepairForm extends DataEntity<WyRepairForm> {
	
	private static final long serialVersionUID = 1L;
	private String repairId;		// 维修id
	private String materialNo;		// 物料编号no
	private String materialName;		// 物料名称
	private String specificationModel;		// 规格型号
	private String price;		// 单价
	private Integer num;		// 数量
	private String count;		// 合价
	
	public WyRepairForm() {
		super();
	}

	public WyRepairForm(String id){
		super(id);
	}

	@Length(min=0, max=32, message="维修id长度必须介于 0 和 32 之间")
	public String getRepairId() {
		return repairId;
	}

	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}
	
	@Length(min=0, max=32, message="物料编号no长度必须介于 0 和 32 之间")
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	
	@Length(min=0, max=32, message="物料名称长度必须介于 0 和 32 之间")
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	@Length(min=0, max=32, message="规格型号长度必须介于 0 和 32 之间")
	public String getSpecificationModel() {
		return specificationModel;
	}

	public void setSpecificationModel(String specificationModel) {
		this.specificationModel = specificationModel;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
}