package com.haolinbang.modules.sns.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.thridwy.haolong.bean.GetLastGroupInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sns.service.UrlmapService;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.inter.WxAccountService;

public class GroupUtil {
	
	protected static Logger logger = LoggerFactory.getLogger(GroupUtil.class);
	
	@Autowired
	private static UrlmapService urlmapService = SpringContextHolder.getBean(UrlmapService.class);
	
	@Autowired
	private static WxAccountService wxAccountService = SpringContextHolder.getBean(WxAccountService.class);
	
	/**
	 * 获取当前组织机构所属的公众号
	 */
	public static String getBelongAppid(String source, Integer groupId){
		Urlmap urlmap = new Urlmap();
		urlmap.setStatus("1");
		urlmap.setUrlkey(source);
		
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		urlmap = urlmapService.getUrlmap(urlmap);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

		GetLastGroupInfo getLastGroupInfo = new GetLastGroupInfo();
		getLastGroupInfo.setSecretkey(urlmap.getSecretkey());
		getLastGroupInfo.setSoapActionString(urlmap.getSoapactionstring());
		getLastGroupInfo.setUrlString(urlmap.getUrlstring());
		getLastGroupInfo.setGroupId(groupId.toString());
		
		List<Map<String, String>> getLastGroupInfos = null;
		String appid = "";
		try {
			getLastGroupInfos = HaolongUtils.GetLastGroupInfo(getLastGroupInfo);
		} catch (IOException e) {
			logger.error("调用GetLastGroupInfo接口出错： "+e.getMessage());
		}
		
		int length = getLastGroupInfos.size();
		if (getLastGroupInfos != null && length > 1) {
			while(StringUtils.isBlank(appid) && length-->=0){
				WxAccount account = new WxAccount();
				account.setSource(source);
				account.setGroupId(StringUtils.toInteger(getLastGroupInfos.get(length).get("GroupID")));
				List<WxAccount> accounts = wxAccountService.findList(account);
				if(accounts!=null && accounts.size()>0){
					appid = accounts.get(0).getAppid();
				}
			}
		}
		
		return appid;
	}
	
	/*
	 * 获取当前组织机构上面机构的信息
	 */
	public static List<Map<String, String>> getLastGroupInfo(String source, Integer groupId) throws IOException{
		Urlmap urlmap = new Urlmap();
		urlmap.setStatus("1");
		urlmap.setUrlkey(source);
		
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		urlmap = urlmapService.getUrlmap(urlmap);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

		GetLastGroupInfo getLastGroupInfo = new GetLastGroupInfo();
		getLastGroupInfo.setSecretkey(urlmap.getSecretkey());
		getLastGroupInfo.setSoapActionString(urlmap.getSoapactionstring());
		getLastGroupInfo.setUrlString(urlmap.getUrlstring());
		getLastGroupInfo.setGroupId(groupId.toString());
		
		List<Map<String, String>> elist = null;
		try {
			elist = HaolongUtils.GetLastGroupInfo(getLastGroupInfo);
		} catch (IOException e) {
			logger.error("调用GetLastGroupInfo接口出错： "+e.getMessage());
		}
		
		return elist;
	}
}
