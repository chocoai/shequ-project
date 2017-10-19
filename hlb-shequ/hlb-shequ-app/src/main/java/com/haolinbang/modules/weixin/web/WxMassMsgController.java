package com.haolinbang.modules.weixin.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.mapper.JsonMapper;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.dto.ZtreeDataDto;
import com.haolinbang.modules.sns.entity.WyMember;
import com.haolinbang.modules.sns.service.UrlmapService;
import com.haolinbang.modules.sns.service.WyMemberService;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.entity.WxMassMsg;
import com.haolinbang.modules.weixin.entity.WxMassMsgCommon;
import com.haolinbang.modules.weixin.entity.WxMsgTpl;
import com.haolinbang.modules.weixin.entity.WxMsgTplCommon;
import com.haolinbang.modules.weixin.entity.WxNewsArticle;
import com.haolinbang.modules.weixin.service.WxMassMsgCommonService;
import com.haolinbang.modules.weixin.service.WxMassMsgService;
import com.haolinbang.modules.weixin.service.WxMsgTplCommonService;
import com.haolinbang.modules.weixin.service.WxMsgTplService;
import com.haolinbang.modules.weixin.service.WxNewsArticleService;
import com.haolinbang.modules.weixin.service.inter.WeixinService;
import com.haolinbang.modules.weixin.service.inter.WxAccountService;
import com.haolinbang.modules.weixin.utils.AccountUtil;

/**
 * 群发消息记录表Controller
 * 
 * @author nlp
 * @version 2017-07-11
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMassMsg")
public class WxMassMsgController extends BaseController {

	@Autowired
	private WxMassMsgService wxMassMsgService;

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private WxNewsArticleService wxNewsArticleService;

	@Autowired
	private WxAccountService wxAccountService;

	@Autowired
	private UrlmapService urlmapService;

	@Autowired
	private WxMassMsgCommonService wxMassMsgCommonService;

	@Autowired
	private WxMsgTplCommonService wxMsgTplCommonService;

	@Autowired
	private WxMsgTplService wxMsgTplService;

	@Autowired
	private WyMemberService wyMemberService;

	@ModelAttribute
	public WxMassMsg get(@RequestParam(required = false) String id) {
		WxMassMsg entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxMassMsgService.get(id);
		}
		if (entity == null) {
			entity = new WxMassMsg();
		}
		return entity;
	}

	/**
	 * 图文消息群发
	 * 
	 * @param wxMassMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping("/news")
	public String news(WxMassMsg wxMassMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		wxMassMsg.setMsgType(WyConstants.WX_MASS_MSG_TYPE_NEWS);
		Page<WxMassMsg> page = wxMassMsgService.findPage(new Page<WxMassMsg>(request, response), wxMassMsg);
		model.addAttribute("page", page);
		return "modules/weixin/wxMassMsgList";
	}

	/**
	 * 群发图文消息
	 * 
	 * @param wxMassMsg
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping(value = "form")
	public String form(WxMassMsg wxMassMsg, Model model) {
		// 获取图文消息

		model.addAttribute("wxMassMsg", wxMassMsg);
		return "modules/weixin/wxMassMsgForm";
	}

	/**
	 * 保存群发的图文消息
	 * 
	 * @param wxMassMsg
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:edit")
	@RequestMapping(value = "save")
	public String save(WxMassMsg wxMassMsg, Model model, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(wxMassMsg.getNewsId())) {
			wxMassMsg.setAccountId(wxMassMsg.getAccountId());
			// 设置成图文消息
			// 设置图文消息
			wxMassMsg.setNewsArticleId(wxMassMsg.getNewsId());
			wxMassMsg.setMsgType(WyConstants.WX_MASS_MSG_TYPE_NEWS);
			wxMassMsg.setType("1");
			wxMassMsgService.save(wxMassMsg);
			addMessage(redirectAttributes, "保存群发消息记录成功");
		} else {
			addMessage(redirectAttributes, "保存群发消息记录失败");
		}
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMassMsg/news?repage";
	}

	/**
	 * 发送图文消息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:edit")
	@RequestMapping("/sendNewsMsg")
	public String sendNewsMsg(String id, RedirectAttributes redirectAttributes) {
		try {
			WeJson json = wxMassMsgService.sendNewsMsg(id);

			logger.info("发送返回的消息:{}", json);

			addMessage(redirectAttributes, "发送成功");
			// return WeJson.success(json);
		} catch (Exception e) {
			logger.error("发送图文消息出错:{}", e);
			// return WeJson.fail("发送图文消息出错");
			addMessage(redirectAttributes, "发送出错");
		}
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMassMsg/news?repage";
	}

	@RequiresPermissions("weixin:wxMassMsg:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMassMsg wxMassMsg, RedirectAttributes redirectAttributes) {
		wxMassMsgService.delete(wxMassMsg);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMassMsg/news?repage";
	}

	/**
	 * 群发图文消息
	 * 
	 * @param wxMassMsg
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:edit")
	@RequestMapping("/showSelectNews")
	public String showSelectNews(WxMassMsg wxMassMsg, Model model) {
		// 获取图文消息
		try {
			List<WxMaterialNewsBatchGetNewsItem> newsList = weixinService.getNewsList(0, 20);

			model.addAttribute("newsList", newsList);
		} catch (WxErrorException e) {
			logger.error("群发图文消息出错:{}", e);
		}
		model.addAttribute("wxMassMsg", wxMassMsg);
		return "modules/weixin/showSelectNews";
	}

	/**
	 * 群发图文消息
	 * 
	 * @param wxMassMsg
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:edit")
	@RequestMapping("/showSelectNews2")
	public String showSelectNews2(WxMassMsg wxMassMsg, Model model) {
		try {
			// 获取保存的图文消息
			WxNewsArticle wxNewsArticle = new WxNewsArticle();
			wxNewsArticle.setAccountId(wxMassMsg.getAccountId());
			List<WxNewsArticle> wxNewsArticleList = wxNewsArticleService.findList(wxNewsArticle);

			// for (WxNewsArticle article : wxNewsArticleList) {
			//
			// //
			// article.setUrl(weixinService.getBase64StrByMediaId(article.getThumbMediaId()));
			// }

			model.addAttribute("wxNewsArticleList", wxNewsArticleList);
			model.addAttribute("wxMassMsg", wxMassMsg);
			return "modules/weixin/showSelectNews2";
		} catch (Exception e) {

		}
		return "";
	}

	/**
	 * 群发图文消息json列表
	 * 
	 * @param wxMassMsg
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping("/showSelectNewsList")
	public WeJson showSelectNewsList(WxMassMsg wxMassMsg, Model model) {
		try {
			// 获取保存的图文消息
			WxNewsArticle wxNewsArticle = new WxNewsArticle();
			wxNewsArticle.setAccountId(wxMassMsg.getAccountId());
			wxNewsArticle.setTitle(wxMassMsg.getName());
			List<WxNewsArticle> wxNewsArticleList = wxNewsArticleService.findList(wxNewsArticle);

			return WeJson.success(wxNewsArticleList);
		} catch (Exception e) {
			logger.info("获取图文消息出错:{}", e);
			return WeJson.fail("获取图文消息出错");
		}
	}

	/**
	 * 模板消息首页
	 * 
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping("/tplIndex")
	public String tplIndex(WxMassMsgCommon wxMassMsgCommon, HttpServletRequest request, HttpServletResponse response, Model model) {
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		Page<WxMassMsgCommon> page = wxMassMsgCommonService.findPage(new Page<WxMassMsgCommon>(request, response), wxMassMsgCommon);
		model.addAttribute("page", page);

		return "modules/weixin/wxMassMsgTplIndex";
	}

	/**
	 * 模板消息群发
	 * 
	 * @param wxMassMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping("/tpl")
	public String tpl(WxMassMsg wxMassMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		wxMassMsg.setAccountId(AccountUtil.getAccount().getId());
		wxMassMsg.setMsgType(WyConstants.WX_MASS_MSG_TYPE_TPL);
		Page<WxMassMsg> page = wxMassMsgService.findPage(new Page<WxMassMsg>(request, response), wxMassMsg);
		model.addAttribute("page", page);
		return "modules/weixin/wxMassMsgListTpl";
	}

	/**
	 * 模板消息群发
	 * 
	 * @param wxMassMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping("/tplSendHistory")
	public String tplSendHistory(WxMassMsg wxMassMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 查询通用消息
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		WxMassMsgCommon wxMassMsgCommon = wxMassMsgCommonService.get(wxMassMsg.getMsgId());
		if (wxMassMsgCommon != null) {
			wxMassMsg.setMsgType(WyConstants.WX_MASS_MSG_TYPE_TPL);
			Page<WxMassMsg> page = wxMassMsgService.findPage(new Page<WxMassMsg>(request, response), wxMassMsg);
			List<WxMassMsg> wxMassMsgList = page.getList();
			String source = wxMassMsg.getSource();
			for (WxMassMsg msg : wxMassMsgList) {
				// 微信公众号
				msg.setWxAccount(wxAccountService.get(msg.getAccountId()));
				String toUsers = msg.getToUsers();
				if (StringUtils.isNotBlank(toUsers)) {
					String memberNames = "";
					String toUserIds = StringUtils.getInSqlStr(toUsers);
					List<WyMember> wyMemberList = null;
					if (WyConstants.WX_MASS_SEND_TO_TYPE_WUYE.equals(msg.getType())) {
						// 选择物业
						wyMemberList = wyMemberService.getMemberListByWyids(source, toUserIds);
					} else if (WyConstants.WX_MASS_SEND_TO_TYPE_SPECIFIC_USER.equals(msg.getType())) {
						// 发送给指定用户
						wyMemberList = wyMemberService.getMemberListByMids(toUserIds);
					} else if (WyConstants.WX_MASS_SEND_TO_TYPE_LOUYU.equals(msg.getType())) {
						// 发送给指定楼盘
						wyMemberList = wyMemberService.getMemberListByLyid(source, toUserIds, null);
					} else {
						memberNames = "所有住户";
					}

					if (wyMemberList != null && !wyMemberList.isEmpty()) {
						for (WyMember wyMember : wyMemberList) {
							memberNames += "," + wyMember.getMemberName();
						}
						if (StringUtils.isNotBlank(memberNames) && memberNames.startsWith(",")) {
							memberNames = memberNames.substring(1);
						}
					}

					// 设置用户信息到前端显示
					msg.setToUsers(memberNames);
				}
			}

			model.addAttribute("wxMassMsgCommon", wxMassMsgCommon);
			model.addAttribute("page", page);
			model.addAttribute("wxMassMsg", wxMassMsg);
		}
		return "modules/weixin/wxMassMsgListTplSendHistory";
	}

	/**
	 * 模板消息群发显示
	 * 
	 * @param wxMassMsg
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping(value = "formTpl")
	public String formTpl(WxMassMsg wxMassMsg, Model model) {
		// 获取图文消息

		model.addAttribute("wxMassMsg", wxMassMsg);
		return "modules/weixin/wxMassMsgFormTpl";
	}

	/**
	 * 模板消息保存
	 * 
	 * @param wxMassMsg
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:edit")
	@RequestMapping(value = "saveTpl")
	public String saveTpl(WxMassMsg wxMassMsg, Model model, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(wxMassMsg.getNewsId())) {
			wxMassMsg.setAccountId(AccountUtil.getAccount().getId());
			// 设置成图文消息
			wxMassMsg.setMsgType(WyConstants.WX_MASS_MSG_TYPE_TPL);
			wxMassMsgService.save(wxMassMsg);
			addMessage(redirectAttributes, "保存群发消息记录成功");
		} else {
			addMessage(redirectAttributes, "保存群发消息记录失败");
		}
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMassMsg/tpl?repage";
	}

	/**
	 * 删除模板消息
	 * 
	 * @param wxMassMsg
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:edit")
	@RequestMapping(value = "deleteTpl")
	public String deleteTpl(WxMassMsg wxMassMsg, RedirectAttributes redirectAttributes) {
		wxMassMsgService.delete(wxMassMsg);
		addMessage(redirectAttributes, "删除群发消息记录表成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxMassMsg/tpl?repage";
	}

	/**
	 * 选择公众号
	 * 
	 * @param wxMassMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping("/tplSelectAccounut")
	public String tplSelectAccounut(WxMassMsg wxMassMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 查询该用户的公众号的权限范围
		String source = wxMassMsg.getSource();
		WxAccount wxAccount = new WxAccount();
		if (StringUtils.isBlank(source)) {
			// 默认source
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
			List<Urlmap> urlmapList = urlmapService.getUrlmapBySource(null);
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

			if (urlmapList != null && urlmapList.size() > 0) {
				source = urlmapList.get(0).getUrlkey();
			}
			wxAccount.setSource(source);
		}

		Page<WxAccount> page = wxAccountService.findPage(new Page<WxAccount>(request, response), wxAccount);
		// 获取组织机构
		for (WxAccount account : page.getList()) {
			GroupInfo group = UserUtils.getGroupInfo(String.valueOf(account.getGroupId()), source);
			account.setGroup(group);
		}
		model.addAttribute("page", page);
		model.addAttribute("wxMassMsg", wxMassMsg);

		return "modules/weixin/wxMassMsgTplSelectAccounut";
	}

	/**
	 * 选择发送对象
	 * 
	 * @param wxMassMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping("/tplSelectSendObj")
	public String tplSelectSendObj(WxMassMsg wxMassMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		// 选择物业
		List<ZtreeDataDto> orgTreeList = UserUtils.getOrgWYTreeList(user.getSource(), user.getGroupInfo().getGroupId());
		model.addAttribute("orgTreeList", orgTreeList);

		// 选择人员
		List<ZtreeDataDto> memberTreeList = UserUtils.getOrgMemberTreeList(user.getSource(), user.getGroupInfo().getGroupId());
		model.addAttribute("memberTreeList", memberTreeList);

		model.addAttribute("wxMassMsg", wxMassMsg);
		return "modules/weixin/wxMassMsgTplSelectSendObj";
	}

	/**
	 * 发送消息
	 * 
	 * @param wxMassMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping("/tplSendMsg")
	public String tplSendMsg(WxMassMsg wxMassMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 获取公司编码
		String source = UserUtils.getUser().getSource();
		WxAccount wxAccount = new WxAccount();
		wxAccount.setSource(source);
		wxMassMsg.setSource(source);

		// 根据用户权限获取公众号账号
		List<WxAccount> wxAccountList = wxAccountService.findList(wxAccount);
		model.addAttribute("accountList", wxAccountList);
		model.addAttribute("wxMassMsg", wxMassMsg);

		// 获取该公众号下面的物业id和会员信息
		String accountId = wxMassMsg.getAccountId();
		if (StringUtils.isNotBlank(accountId)) {
			wxAccount = wxAccountService.get(accountId);
		} else {
			wxAccount = wxAccountList.get(0);
			accountId = wxAccount.getId();
		}
		model.addAttribute("wxAccount", wxAccount);

		String groupId = wxAccount.getGroupId().toString();
		// 选择物业
		List<ZtreeDataDto> orgTreeList = UserUtils.getOrgWYTreeListSub(source, groupId);
		model.addAttribute("orgTreeList", orgTreeList);

		model.addAttribute("wxMassMsg", wxMassMsg);
		return "modules/weixin/wxMassMsgTplSendMsg";
	}

	/**
	 * 发送消息
	 * 
	 * @param wxMassMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxMassMsg:view")
	@RequestMapping("/sendMsgSave")
	public WeJson sendMsgSave(WxMassMsg wxMassMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			// 通过accountid查询source
			WxAccount account = wxAccountService.get(wxMassMsg.getAccountId());
			String source = account.getSource();
			String toUserIds = "";
			// 组装发送会员列表
			List<WyMember> memberList = new ArrayList<WyMember>();
			if (WyConstants.WX_MASS_SEND_TO_TYPE_WUYE.equals(wxMassMsg.getType())) {
				// 发送消息给物业
				String wyIds = wxMassMsg.getWyIds();
				String wyIds2 = "";

				if (StringUtils.isNotBlank(wyIds)) {
					String[] wyidArr = wyIds.split(",");
					for (String str : wyidArr) {
						String[] strArr = str.split("_");
						if (strArr != null && strArr.length > 0) {
							wyIds2 += ",'" + strArr[1] + "'";
							toUserIds += "," + strArr[1];
						}
					}
				}
				if (wyIds2.startsWith(",")) {
					wyIds2 = wyIds2.substring(1);
					toUserIds = toUserIds.substring(1);
				}
				if (StringUtils.isNotBlank(wyIds2)) {
					memberList = wyMemberService.getMemberListByWyids(source, wyIds2);
				}
			} else if (WyConstants.WX_MASS_SEND_TO_TYPE_SPECIFIC_USER.equals(wxMassMsg.getType())) {
				// 发送给指定会员
				String memberIds = wxMassMsg.getMemberIds();
				String memberIds2 = "";
				if (StringUtils.isNotBlank(memberIds)) {
					String[] memberIdsArr = memberIds.split(",");
					for (String str : memberIdsArr) {
						if (str.startsWith("M_")) {
							String id = str.substring(2);
							memberIds2 += ",'" + id + "'";
							toUserIds += "," + id;
						} else {
							memberIds2 += ",'" + str + "'";
							toUserIds += "," + str;
						}
					}
					if (memberIds2.startsWith(",")) {
						memberIds2 = memberIds2.substring(1);
						toUserIds = toUserIds.substring(1);
					}
				}
				if (StringUtils.isNotBlank(memberIds2)) {
					memberList = wyMemberService.getMemberListByMids(memberIds2);
				}
			} else if (WyConstants.WX_MASS_SEND_TO_TYPE_LOUYU.equals(wxMassMsg.getType())) {
				// 发送消息给楼栋
				String lyid = wxMassMsg.getLdids();
				memberList = wyMemberService.getMemberListByLyid(source, lyid, null);
				toUserIds = "ly_" + lyid;

			} else {
				// 发送给该source的全部会员
				// 获取account id下面的所有物业id,通过物业id获取下面的会员信息
				String accountId = wxMassMsg.getAccountId();
				WxAccount wxAccount = wxAccountService.get(accountId);
				String wyids = UserUtils.getWyidsByGroupid(source, wxAccount.getGroupId().toString());

				memberList = wyMemberService.getMemberListByWyids(source, wyids);
				toUserIds = "all";
			}

			// 通过msgid获取通用模板消息
			WxMassMsgCommon wxMassMsgCommon = wxMassMsgCommonService.get(wxMassMsg.getMsgId());
			// 保存发送消息z状态
			final WxMassMsg wxMassMsg2 = new WxMassMsg();
			wxMassMsg2.setName(wxMassMsgCommon.getFirstValue());// 设置消息发送标题
			wxMassMsg2.setAccountId(wxMassMsg.getAccountId());
			wxMassMsg2.setMsgType(WyConstants.WX_MASS_MSG_DETAIL_NOTICE);
			wxMassMsg2.setType(wxMassMsg.getType());
			wxMassMsg2.setSource(wxMassMsg.getSource());
			wxMassMsg2.setWyid(wxMassMsg.getWyIds());
			wxMassMsg2.setUrl(wxMassMsgCommon.getUrl());// 拼接地址，在手机端可以访问
			wxMassMsg2.setNeedSendNum(memberList.size());
			wxMassMsg2.setToUsers(toUserIds);
			wxMassMsg2.setMsgId(wxMassMsg.getMsgId());

			// 保存发送的实例消息
			wxMassMsgService.save2(wxMassMsg2);

			// 设置会员列表
			wxMassMsg2.setMemberList(memberList);

			// 异步发送消息
			new Thread(new Runnable() {
				@Override
				public void run() {
					// 发送消息
					sendTplMsg(wxMassMsg2);
				}
			}).start();

			return WeJson.success("消息发送成功");
		} catch (Exception e) {
			logger.error("发送消息出现错误:{}", e);
			return WeJson.fail("发送消息出现错误");
		}
	}

	/**
	 * 发送模板消息
	 * 
	 * @param msgId
	 * @param memberList
	 */
	private void sendTplMsg(WxMassMsg wxMassMsg) {
		String msgId = wxMassMsg.getMsgId();
		List<WyMember> memberList = wxMassMsg.getMemberList();
		if (StringUtils.isBlank(msgId) || memberList == null || memberList.isEmpty()) {
			throw new RuntimeException("msgId为空,或者memberList为空:msgId=" + msgId);
		}

		// 通过msgid获取通用模板消息
		WxMassMsgCommon wxMassMsgCommon = wxMassMsgCommonService.get(msgId);
		if (wxMassMsgCommon == null) {
			throw new RuntimeException("通过msgid获取通用模板消息为空:msgId=" + msgId);
		}
		// 获取实例模板,通过accountID和通用模板id唯一确定出来一个绑定的实例模板
		WxMsgTpl wxMsgTpl = wxMsgTplService.getWxMsgTplByCommonTplIdAndAccountId(wxMassMsgCommon.getTplId(), wxMassMsg.getAccountId());
		if (wxMsgTpl == null) {
			throw new RuntimeException("实例模板为空,commonTplId=" + wxMassMsgCommon.getTplId() + ";accountId=" + wxMassMsg.getAccountId());
		}
		// 获取通用模板
		WxMsgTplCommon wxMsgTplCommon = wxMsgTplCommonService.get(wxMsgTpl.getCommonTplId().toString());
		if (wxMsgTplCommon == null) {
			throw new RuntimeException("通用模板为空,commonTplId=" + wxMsgTpl.getCommonTplId());
		}

		// 组装点击消息跳转的url地址
		String url = Global.getConfig("server.project.hlb-shequ-server") + wxMassMsgCommon.getUrl();
		// 组装需要发送的消息
		WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
		templateMessage.setTemplateId(wxMsgTpl.getTplId());// 从实例模板中获取绑定的微信模板id
		templateMessage.setUrl(url);// 点击的跳转url地址
		// 组装发送消息
		// 头部提示消息
		if (StringUtils.isNotBlank(wxMsgTplCommon.getFirstName()) && StringUtils.isNotBlank(wxMsgTplCommon.getFirstField())) {
			if (StringUtils.isNotBlank(wxMassMsgCommon.getFirstValue())) {
				templateMessage.getDatas().add(new WxMpTemplateData(wxMsgTplCommon.getFirstField(), wxMassMsgCommon.getFirstValue(), "#" + wxMsgTplCommon.getFirstColor()));
			}
		}
		// 字段1的值
		if (StringUtils.isNotBlank(wxMsgTplCommon.getKeyword1Name()) && StringUtils.isNotBlank(wxMsgTplCommon.getKeyword1Field())) {
			if (StringUtils.isNotBlank(wxMassMsgCommon.getKeyword1Value())) {
				templateMessage.getDatas()
						.add(new WxMpTemplateData(wxMsgTplCommon.getKeyword1Field(), wxMassMsgCommon.getKeyword1Value(), "#" + wxMsgTplCommon.getKeyword1Color()));
			}
		}
		// 字段2的值
		if (StringUtils.isNotBlank(wxMsgTplCommon.getKeyword2Name()) && StringUtils.isNotBlank(wxMsgTplCommon.getKeyword2Field())) {
			if (StringUtils.isNotBlank(wxMassMsgCommon.getKeyword2Value())) {
				templateMessage.getDatas()
						.add(new WxMpTemplateData(wxMsgTplCommon.getKeyword2Field(), wxMassMsgCommon.getKeyword2Value(), "#" + wxMsgTplCommon.getKeyword2Color()));
			}
		}
		// 字段3的值
		if (StringUtils.isNotBlank(wxMsgTplCommon.getKeyword3Name()) && StringUtils.isNotBlank(wxMsgTplCommon.getKeyword3Field())) {
			if (StringUtils.isNotBlank(wxMassMsgCommon.getKeyword3Value())) {
				templateMessage.getDatas()
						.add(new WxMpTemplateData(wxMsgTplCommon.getKeyword3Field(), wxMassMsgCommon.getKeyword3Value(), "#" + wxMsgTplCommon.getKeyword3Color()));
			}
		}
		// 字段4的值
		if (StringUtils.isNotBlank(wxMsgTplCommon.getKeyword4Name()) && StringUtils.isNotBlank(wxMsgTplCommon.getKeyword4Field())) {
			if (StringUtils.isNotBlank(wxMassMsgCommon.getKeyword4Value())) {
				templateMessage.getDatas()
						.add(new WxMpTemplateData(wxMsgTplCommon.getKeyword4Field(), wxMassMsgCommon.getKeyword4Value(), "#" + wxMsgTplCommon.getKeyword4Color()));
			}
		}
		// 字段5的值
		if (StringUtils.isNotBlank(wxMsgTplCommon.getKeyword5Name()) && StringUtils.isNotBlank(wxMsgTplCommon.getKeyword5Field())) {
			if (StringUtils.isNotBlank(wxMassMsgCommon.getKeyword5Value())) {
				templateMessage.getDatas()
						.add(new WxMpTemplateData(wxMsgTplCommon.getKeyword5Field(), wxMassMsgCommon.getKeyword5Value(), "#" + wxMsgTplCommon.getKeyword5Color()));
			}
		}
		// 备注字段的值
		if (StringUtils.isNotBlank(wxMsgTplCommon.getRemarkName()) && StringUtils.isNotBlank(wxMsgTplCommon.getRemarkField())) {
			if (StringUtils.isNotBlank(wxMassMsgCommon.getRemarkValue())) {
				templateMessage.getDatas().add(new WxMpTemplateData(wxMsgTplCommon.getRemarkField(), wxMassMsgCommon.getRemarkValue(), "#" + wxMsgTplCommon.getRemarkColor()));
			}
		}

		String json = null;
		// 循环发送消息
		for (WyMember member : memberList) {
			logger.info("会员id:{},发送会员:{},模板消息发送内容:{}", member.getMemberId(), member.getMemberName(), JsonMapper.toJsonString(templateMessage));
			templateMessage.setToUser(member.getOpenid());// 接受消息的用户openid
			try {
				json = weixinService.templateSend(templateMessage, wxMassMsg.getAccountId());// 发送消息
				logger.info("模板消息发送消息,返回值:{}", json);
			} catch (WxErrorException e) {
				logger.error("发送微信消息错误:{}", e);
			}

			// 每发送一条消息加1
			wxMassMsg.setCurrSendNum((wxMassMsg.getCurrSendNum() == null ? 0 : wxMassMsg.getCurrSendNum()) + 1);
			wxMassMsgService.save2(wxMassMsg);
		}
	}
}