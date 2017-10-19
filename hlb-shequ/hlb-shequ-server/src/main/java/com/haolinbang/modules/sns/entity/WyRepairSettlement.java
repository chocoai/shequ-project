package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 物业维修核算汇总Entity
 * @author nlp
 * @version 2017-05-18
 */
public class WyRepairSettlement extends DataEntity<WyRepairSettlement> {
	
	private static final long serialVersionUID = 1L;
	private String repairId;		// 维修id
	private Integer materielNum;		// 物料总数量
	private Double materielMoney;		// 物料总花费
	private Double laborCost;		// 人力花费
	private Double totalCost;		// 总金额
	
	public WyRepairSettlement() {
		super();
	}

	public WyRepairSettlement(String id){
		super(id);
	}

	@Length(min=1, max=32, message="维修id长度必须介于 1 和 32 之间")
	public String getRepairId() {
		return repairId;
	}

	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}
	
	public Integer getMaterielNum() {
		return materielNum;
	}

	public void setMaterielNum(Integer materielNum) {
		this.materielNum = materielNum;
	}
	
	public Double getMaterielMoney() {
		return materielMoney;
	}

	public void setMaterielMoney(Double materielMoney) {
		this.materielMoney = materielMoney;
	}
	
	public Double getLaborCost() {
		return laborCost;
	}

	public void setLaborCost(Double laborCost) {
		this.laborCost = laborCost;
	}
	
	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	
}