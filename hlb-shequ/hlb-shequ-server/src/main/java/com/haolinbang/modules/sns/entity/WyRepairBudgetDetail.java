package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 物业维修预算明细Entity
 * @author nlp
 * @version 2017-05-18
 */
public class WyRepairBudgetDetail extends DataEntity<WyRepairBudgetDetail> {
	
	private static final long serialVersionUID = 1L;
	private String repairId;		// 维修id
	private String materialNo;		// 物料编号no
	private String materialName;		// 物料名称
	private String specificationModel;		// 规格型号
	private Double price;		// 单价
	private Integer num;		// 数量
	private Double count;		// 合价
	private String type;		// 装修类型
	
	public WyRepairBudgetDetail() {
		super();
	}

	public WyRepairBudgetDetail(String id){
		super(id);
	}

	@Length(min=1, max=32, message="维修id长度必须介于 1 和 32 之间")
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
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}
	
	@Length(min=0, max=2, message="装修类型长度必须介于 0 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}