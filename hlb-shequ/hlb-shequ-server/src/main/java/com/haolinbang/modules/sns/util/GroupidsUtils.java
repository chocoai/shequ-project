package com.haolinbang.modules.sns.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haolinbang.common.thridwy.haolong.bean.GetLastGroupInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.modules.sys.utils.UserUtils;

public class GroupidsUtils {

	protected static Logger logger = LoggerFactory.getLogger(GroupidsUtils.class);

	/*
	 * 获取当前组织机构的上层组织机构
	 */
	public static List<String> getLastGroupIDs(String source, Integer groupId) throws Exception {
		Urlmap urlmap = UserUtils.getUrlmap(source);
		GetLastGroupInfo getLastGroupInfo = new GetLastGroupInfo();
		getLastGroupInfo.setGroupId(groupId.toString());
		getLastGroupInfo.setSecretkey(urlmap.getSecretkey());
		getLastGroupInfo.setSoapActionString(urlmap.getSoapactionstring());
		getLastGroupInfo.setUrlString(urlmap.getUrlstring());

		List<Map<String, String>> getLastGroupInfos = null;
		try {
			getLastGroupInfos = HaolongUtils.GetLastGroupInfo(getLastGroupInfo);
		} catch (IOException e) {
			logger.error("调用GetLastGroupInfo接口出错，错误信息：{}", e);
			return null;
		}

		List<String> groupids = new ArrayList<String>();
		if (getLastGroupInfos != null && getLastGroupInfos.size() > 1) {
			for (Map<String, String> maps : getLastGroupInfos) {
				groupids.add(maps.get("GroupID"));
			}
		}
		return groupids;
	}
}
