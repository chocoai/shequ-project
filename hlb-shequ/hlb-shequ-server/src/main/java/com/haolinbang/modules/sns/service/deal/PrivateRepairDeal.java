package com.haolinbang.modules.sns.service.deal;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.modules.sns.entity.WyRepair;
import com.haolinbang.modules.sns.service.WyRepairService;

/**
 * 个人维修处理类 主要处理传递的变量值
 * 
 * @author Administrator
 * 
 */
@Service
public class PrivateRepairDeal {

	/**
	 * 处理是否有效
	 */
	public void commonDeal(HttpServletRequest request, String bizId, Map<String, Object> var) {
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

		// 如果不为空,处理是否有效
		String isvalid = request.getParameter("isvalid");
		if (StringUtils.isNoneBlank(isvalid)) {
			if ("1".equals(isvalid)) {
				var.put("commonFlow", true);
			} else if ("0".equals(isvalid)) {
				var.put("commonFlow", false);
			}
		}

		// 如果是否同意的值不为空,设置该值
		String agree = request.getParameter("agree");
		if (StringUtils.isNotBlank(agree)) {
			if ("1".equals(agree)) {
				var.put("commonFlow", true);
			} else if ("0".equals(agree)) {
				var.put("commonFlow", false);
			}
		}

	}

}
