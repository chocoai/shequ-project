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
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.thridwy.haolong.bean.GetLastGroupInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.thridwy.haolong.bean.bean.UserInfo;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.entity.WyConvenienceService;
import com.haolinbang.modules.sns.service.UrlmapService;
import com.haolinbang.modules.sns.service.WyConvenienceServiceService;
import com.haolinbang.modules.sns.util.GroupUtil;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 便民服务Controller
 * 
 * @author wxc
 * @version 2017-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyConvenienceService")
public class WyConvenienceServiceController extends BaseController {

	@Autowired
	private WyConvenienceServiceService wyConvenienceServiceService;
	
	@Autowired
	private UrlmapService urlmapService;

	@ModelAttribute
	public WyConvenienceService get(@RequestParam(required = false) String id) {
		WyConvenienceService entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyConvenienceServiceService.get(id);
		}
		if (entity == null) {
			entity = new WyConvenienceService();
		}
		return entity;
	}

	/**
	 * 便民服务显示
	 * 
	 * @param wyConvenienceService
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	/*@RequiresPermissions("sns:wyConvenienceService:view")
	@RequestMapping("")
	public String index(WyConvenienceService wyConvenienceService, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (user != null) {
			wyConvenienceService.setSource(user.getSource());
			wyConvenienceService.setGroupId(user.getParentGroupId());
		}
		// 便民服务类型
		wyConvenienceService.setType(WyConstants.WX_MASS_MSG_DETAIL_CONVENIENCE_SERVICES);
		Page<WyConvenienceService> page = wyConvenienceServiceService.findPage(new Page<WyConvenienceService>(request, response), wyConvenienceService);
		model.addAttribute("page", page);

		if (page.getList() != null && page.getList().size() > 0) {
			wyConvenienceService = page.getList().get(0);
		} else {		
			wyConvenienceService.setCreateTime(new Date());
			//wyConvenienceServiceService.save(wyConvenienceService);
		}

		model.addAttribute("wyConvenienceService", wyConvenienceService);
		return "modules/sns/wyConvenienceServiceForm";
		// return "modules/sns/wyConvenienceServiceList";
	}*/
	/**
	 * 便民服务显示
	 * 
	 * @param wyConvenienceService
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sns:wyConvenienceService:view")
	@RequestMapping("/index")
	public String index(WyConvenienceService wyConvenienceService, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		String gid = request.getParameter("gid");
		String[] gids  = null;
		String source = null;
		Integer groupid = null;
		Integer grouptype = null;
		String groupInfo = "";
		if(StringUtils.isNotBlank(gid) && !gid.equals("undefined")){
			gids = gid.split("___");
			if(gids!=null && gids.length==4){
				source = gids[1];
				groupid = StringUtils.toInteger(gids[2]);
				grouptype = StringUtils.toInteger(gids[3]);
			}
		}

		if(source == null){
			source = user.getSource();
		}
		if(groupid == null){
			groupid = user.getParentGroupId();
		}
		
		if(grouptype == null){
			List<Map<String, String>> elist = null;
			try {
				elist = GroupUtil.getLastGroupInfo(source, groupid);
			} catch (IOException e) {
				logger.error("调用GetLastGroupInfo接口出错： "+e.getMessage());
			}
			
			int length = elist.size();
			if(length >= 1){
				grouptype = StringUtils.toInteger(elist.get(length-1).get("GroupType"));
				groupInfo = elist.get(length-1).get("名称");
			}else{
				return "modules/sns/wyCompanyProfileNoForm";
			}
		}else if(StringUtils.isBlank(groupInfo)){
			List<Map<String, String>> elist = null;
			try {
				elist = GroupUtil.getLastGroupInfo(source, groupid);
			} catch (IOException e) {
				logger.error("调用GetLastGroupInfo接口出错： "+e.getMessage());
			}
			int length = elist.size();
			groupInfo = elist.get(length-1).get("名称");
		}
		
		if(grouptype != 2){
			return "modules/sns/wyCompanyProfileNoForm";
		}

		wyConvenienceService.setSource(source);
		wyConvenienceService.setGroupId(groupid);
		wyConvenienceService.setGroupType(grouptype);
		
		// 便民服务类型
		wyConvenienceService.setType(WyConstants.WX_MASS_MSG_DETAIL_CONVENIENCE_SERVICES);
		Page<WyConvenienceService> page = wyConvenienceServiceService.findPage(new Page<WyConvenienceService>(request, response), wyConvenienceService);
		model.addAttribute("page", page);

		if (page.getList() != null && page.getList().size() > 0) {
			wyConvenienceService = page.getList().get(0);
		} else {
			wyConvenienceService.setCreateTime(new Date());
			wyConvenienceService.setGroupInfo(groupInfo);
			if(wyConvenienceService.getGroupType()==2){
				wyConvenienceServiceService.save(wyConvenienceService);
			}
		}
		if(StringUtils.isBlank(wyConvenienceService.getAppid())){
			wyConvenienceService.setAppid(GroupUtil.getBelongAppid(source, groupid));
		}
		if(StringUtils.isBlank(wyConvenienceService.getGroupInfo())){
			wyConvenienceService.setGroupInfo(groupInfo);
		}
		wyConvenienceService.setGroupType(grouptype);
	
		model.addAttribute("wyConvenienceService", wyConvenienceService);

		return "modules/sns/wyConvenienceServiceForm";
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
	@RequiresPermissions("sns:wyConvenienceService:view")
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
		return "modules/sns/wyConvenienceServiceList";
	}

	@RequiresPermissions("sns:wyConvenienceService:view")
	@RequestMapping(value = "form")
	public String form(WyConvenienceService wyConvenienceService, Model model) {
		model.addAttribute("wyConvenienceService", wyConvenienceService);
		return "modules/sns/wyConvenienceServiceForm";
	}

	@RequiresPermissions("sns:wyConvenienceService:edit")
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
				wyConvenienceService.setGroupId(user.getParentGroupId());
				wyConvenienceService.setType(WyConstants.WX_MASS_MSG_DETAIL_CONVENIENCE_SERVICES);
			}
		}
		wyConvenienceServiceService.save(wyConvenienceService);
		addMessage(redirectAttributes, "保存便民服务成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyConvenienceService/index?gid="+gid;
	}

	@RequiresPermissions("sns:wyConvenienceService:edit")
	@RequestMapping(value = "delete")
	public String delete(WyConvenienceService wyConvenienceService, RedirectAttributes redirectAttributes) {
		wyConvenienceServiceService.delete(wyConvenienceService);
		addMessage(redirectAttributes, "删除便民服务成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyConvenienceService/?repage";
	}
	
	@RequiresPermissions("sns:wyConvenienceService:view")
	@RequestMapping("")
	public String index1(WyConvenienceService wyConvenienceService, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/sns/wyConvenienceServiceIndex";
		// return "modules/sns/wyConvenienceServiceList";
	}

}