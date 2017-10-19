package com.haolinbang.modules.sns.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.entity.WyConvenienceService;
import com.haolinbang.modules.sns.service.WyConvenienceServiceService;
import com.haolinbang.modules.sns.util.GroupUtil;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.inter.WxAccountService;

/**
 * 便民服务Controller
 * 
 * @author wxc
 * @version 2017-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyCompanyProfile")
public class WyCompanyProfileController extends BaseController {

	@Autowired
	private WyConvenienceServiceService wyConvenienceServiceService;
	
	@Autowired
	private WxAccountService wxAccountService;

	@ModelAttribute
	public WyConvenienceService get(@RequestParam(required = false) String serviceId) {
		WyConvenienceService entity = null;
		if (StringUtils.isNotBlank(serviceId)) {
			entity = wyConvenienceServiceService.get(serviceId);
		}
		if (entity == null) {
			entity = new WyConvenienceService();
		}
		return entity;
	}

	/**
	 * 公司简介显示
	 * 
	 * @param wyConvenienceService
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sns:wyCompanyProfile:view")
	@RequestMapping("/index")
	public String index(WyConvenienceService wyConvenienceService, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		String gid = request.getParameter("gid");
		String[] gids  = null;
		String source = null;
		Integer groupid = null;
		if(StringUtils.isNotBlank(gid) && !gid.equals("undefined")){
			gids = gid.split("___");
			if(gids!=null && gids.length==3){
				source = gids[1];
				groupid = StringUtils.toInteger(gids[2]);
			}
		}
		
		if(source == null){
			source = user.getSource();
		}
		if(groupid == null){
			groupid = user.getParentGroupId();
		}

		WxAccount wxAccount = new WxAccount();
		wxAccount.setSource(source);
		wxAccount.setGroupId(groupid);
		List<WxAccount> wxAccounts = wxAccountService.findList(wxAccount);
		wxAccount = wxAccounts.size()>0?wxAccounts.get(0):null;
		try{
			if(wxAccount == null){
				throw new Exception("查询不到对应的公众号");
			}else{
				wyConvenienceService.setSource(source);
				wyConvenienceService.setGroupId(groupid);
				wyConvenienceService.setWxAccountId(wxAccount.getId());
				wyConvenienceService.setAppid(wxAccount.getAppid());
			}
		}catch(Exception e){
			logger.error(e.toString());
			return "modules/sns/wyCompanyProfileNoForm";
		}
		
		//公司介绍类型
		wyConvenienceService.setType(WyConstants.WX_MASS_MSG_DETAIL_COMPANYPROFILE);
		Page<WyConvenienceService> page = wyConvenienceServiceService.findPage(new Page<WyConvenienceService>(request, response), wyConvenienceService);
		model.addAttribute("page", page);

		if (page.getList() != null && page.getList().size() > 0) {
			wyConvenienceService = page.getList().get(0);
			if(StringUtils.isBlank(wyConvenienceService.getAppid())){
				wyConvenienceService.setAppid(wxAccount.getAppid());
			}
		} else {		
			wyConvenienceService.setCreateTime(new Date());
			wyConvenienceServiceService.save(wyConvenienceService);
		}
		
		if(StringUtils.isBlank(wyConvenienceService.getGroupInfo())){
			List<Map<String, String>> elist = null;
			try {
				elist = GroupUtil.getLastGroupInfo(source, groupid);
			} catch (IOException e) {
				logger.error("调用GetLastGroupInfo接口出错： "+e.getMessage());
			}
			int length = elist.size();
			wyConvenienceService.setGroupInfo(elist.get(length-1).get("名称"));
		}

		model.addAttribute("wyConvenienceService", wyConvenienceService);
		return "modules/sns/wyCompanyProfileForm";
		// return "modules/sns/wyConvenienceServiceList";
	}

	/**
	 * 公告，通告详情列表
	 * 
	 * @param wyConvenienceService
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sns:wyCompanyProfile:view")
	@RequestMapping("/list")
	public String list(WyConvenienceService wyConvenienceService, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (user != null) {
			wyConvenienceService.setSource(user.getSource());
			wyConvenienceService.setGroupId(user.getParentGroupId());
		}
		Page<WyConvenienceService> page = wyConvenienceServiceService.findPage(new Page<WyConvenienceService>(request, response), wyConvenienceService);
		model.addAttribute("page", page);
		model.addAttribute("wyConvenienceService", wyConvenienceService);
		return "modules/sns/wyCompanyProfileList";
	}

	@RequiresPermissions("sns:wyCompanyProfile:view")
	@RequestMapping(value = "form")
	public String form(WyConvenienceService wyConvenienceService, Model model) {
		model.addAttribute("wyConvenienceService", wyConvenienceService);
		return "modules/sns/wyCompanyProfileForm";
	}

	@RequiresPermissions("sns:wyCompanyProfile:edit")
	@RequestMapping(value = "save")
	public String save(WyConvenienceService wyConvenienceService, Model model, RedirectAttributes redirectAttributes, String gid) {
		if (!beanValidator(model, wyConvenienceService)) {
			return form(wyConvenienceService, model);
		}
		if (wyConvenienceService.getServiceId() != null) {
			wyConvenienceService.setId(wyConvenienceService.getServiceId().toString());
		} else {
			User user = UserUtils.getUser();
			if (user != null) {
				wyConvenienceService.setSource(user.getSource());
				//wyConvenienceService.setGroupId(user.getParentGroupId());
			}
			wyConvenienceService.setType(WyConstants.WX_MASS_MSG_DETAIL_COMPANYPROFILE);
		}
		wyConvenienceServiceService.save(wyConvenienceService);
		addMessage(redirectAttributes, "保存公司简介成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyCompanyProfile/index?gid="+gid;
	}

	@RequiresPermissions("sns:wyCompanyProfile:edit")
	@RequestMapping(value = "delete")
	public String delete(WyConvenienceService wyConvenienceService, RedirectAttributes redirectAttributes) {
		wyConvenienceServiceService.delete(wyConvenienceService);
		addMessage(redirectAttributes, "删除公司简介成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyCompanyProfile/?repage";
	}
	
	@RequiresPermissions("sns:wyCompanyProfile:view")
	@RequestMapping("")
	public String index1(WyConvenienceService wyConvenienceService, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/sns/wyCompanyProfileIndex";
		// return "modules/sns/wyConvenienceServiceList";
	}
}