package com.haolinbang.modules.sns.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.haolinbang.common.persistence.DataEntity;

/**
 * 物业维修预算工时明细Entity
 * 
 * @author nlp
 * @version 2017-05-18
 */
public class WyRepairBudgetLabor extends DataEntity<WyRepairBudgetLabor> {

	private static final long serialVersionUID = 1L;
	private String repairId; // 维修id
	private String name; // 姓名
	private Double price; // 每小时单价
	private Double spentHour; // 花费小时数
	private Double count; // 合价

	public WyRepairBudgetLabor() {
		super();
	}

	public WyRepairBudgetLabor(String id) {
		super(id);
	}

	@Length(min = 1, max = 32, message = "维修id长度必须介于 1 和 32 之间")
	public String getRepairId() {
		return repairId;
	}

	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}

	@Length(min = 0, max = 32, message = "姓名长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSpentHour() {
		return spentHour;
	}

	public void setSpentHour(Double spentHour) {
		this.spentHour = spentHour;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

}