package com.haolinbang.modules.sns.util;

import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.common.utils.Exceptions;
import com.haolinbang.common.utils.SpringContextHolder;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.ServletUtil;
import com.haolinbang.modules.sns.dao.MemberDao;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.service.UrlmapService;
import com.haolinbang.modules.sys.dao.UserDao;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;

public class MemberUtils {

	public static final String BF_ADS_CONF = "BfAdConf";

	private static MemberDao wxMemberDao = SpringContextHolder.getBean(MemberDao.class);
	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static UrlmapService urlmapService = SpringContextHolder.getBean(UrlmapService.class);
	
	public static final String CACHE_SOURCE_KEY = "sourceKey_";

	/**
	 * 获取登录session中的会员信息
	 * 
	 * @param request
	 * @return
	 */
	public static Member getCurrentMember() {
		//Integer memberid = 37; 
		Integer memberid = (Integer) ServletUtil.getSession().getAttribute("memberid");
		if (memberid == null || memberid == 0) {
			memberid = StringUtils.toInteger(ServletUtil.getRequest().getParameter("memberid"));
		}
		if (memberid == null || memberid == 0) {
			throw Exceptions.memberIsNull(new Exception("没有登录，请先登录"));
		}
		if (memberid != 0) {
			ServletUtil.getSession().setAttribute("memberid", memberid);
		}

		return getWxMember(String.valueOf(memberid));
	}

	/**
	 * 获取会员信息
	 * 
	 * @param mid
	 * @return
	 */
	public static Member getWxMember(String mid) {
		Member member = null /* CacheUtils.get(mid, "member") */;
		if (member == null) {
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
			member = wxMemberDao.get(mid);
			if (member == null) {
				throw Exceptions.memberNeedReg(new Exception("你获取的会员信息不存在,您需要先进行注册"));
			}
			if (CacheUtils.get(mid, "member") != null) {
				CacheUtils.remove(mid, "member");
			}
			if (StringUtils.isNotBlank(member.getSource()) && StringUtils.isNotBlank(member.getYgID())) {
				//设置会员的用户信息
				User staff = userDao.getUserByStaffId(member.getSource(), member.getYgID());
				// 设置组织机构信息
				UserUtils.setUserGroup(staff.getGroupId().toString(), member.getSource(), staff);
				member.setStaff(staff);
			}

			CacheUtils.put(mid, "member", member);
		}

		return member;
	}
	
	public static Urlmap getUrlmap(String source) {
		Urlmap urlmap = CacheUtils.get(CACHE_SOURCE_KEY + source);
		if (urlmap == null) {
			urlmap = new Urlmap();
			urlmap.setStatus("1");
			urlmap.setUrlkey(source);
			// 设置数据源
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
			urlmap = urlmapService.getUrlmap(urlmap);
			// 还原数据源
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
			CacheUtils.put(CACHE_SOURCE_KEY + source, urlmap);
		}
		return urlmap;
	}

}
