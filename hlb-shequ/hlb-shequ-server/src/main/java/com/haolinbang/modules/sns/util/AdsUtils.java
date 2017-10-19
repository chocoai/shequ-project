package com.haolinbang.modules.sns.util;

import java.util.List;

import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.modules.sns.dao.AdDao;
import com.haolinbang.modules.sns.entity.Ad;

public class AdsUtils {
	
	private static AdDao adDao = SpringContextHolder.getBean(AdDao.class);
	
	public static List<Ad> getAds(){
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		Ad ad = new Ad();
		ad.setSource(null);
		ad.setWYID(null);
		ad.setStatus("1"); // 有效状态
		List<Ad> ads = adDao.findList(ad);
		CacheUtils.put("ads", ads);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

		return ads;
	}
}
