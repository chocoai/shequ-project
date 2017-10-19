package com.haolinbang.common.persistence.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	public java.util.logging.Logger getParentLogger() {
		return null;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		// 从自定义的位置获取数据源标识
		return DynamicDataSourceHolder.getDataSource();
	}

}
