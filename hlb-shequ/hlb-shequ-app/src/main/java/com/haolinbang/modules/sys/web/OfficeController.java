package com.haolinbang.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.LyBuilding;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.dto.ZtreeDataDto;
import com.haolinbang.modules.sns.entity.WyBuilding;
import com.haolinbang.modules.sns.entity.WyMember;
import com.haolinbang.modules.sns.service.WyMemberService;
import com.haolinbang.modules.sys.entity.AppUserScope;
import com.haolinbang.modules.sys.entity.Office;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.service.OfficeService;
import com.haolinbang.modules.sys.utils.DictUtils;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.inter.WxAccountService;

/**
 * 机构Controller,通过接口获取组织机构信息
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private WxAccountService wxAccountService;

	@Autowired
	private WyMemberService wyMemberService;

	@ModelAttribute("office")
	public Office get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return officeService.get(id);
		} else {
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping("")
	public String index(Office office, Model model) {

		// model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}

	/**
	 * 选择代办人员部门
	 * 
	 * @param office
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = { "selectHandlerIndex" })
	public String selectHandlerIndex(Office office, Model model) {

		return "modules/sys/selectHandlerOfficeIndex";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = { "/list" })
	public String list(Office office, Model model) {
		model.addAttribute("list", officeService.findList(office));
		return "modules/sys/officeList";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping("/list2")
	public String list2(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		String source = null;
		String type = null;
		if (id.contains("___")) {
			String[] arr = id.split("___");
			if (arr != null && arr.length == 3) {
				type = arr[0];
				source = arr[1];
				id = arr[2];
			}
		}
		List<Office> list = null;
		if ("g".equals(type)) {
			list = UserUtils.getSubOfficeList(id, source);
		}

		Office office = new Office();
		office.setId(id);
		model.addAttribute("list", list);
		model.addAttribute("office", office);
		return "modules/sys/officeList";
	}

	/**
	 * 获取部门信息
	 * 
	 * @param office
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = { "/selectHandlerOfficeList" })
	public String selectHandlerOfficeList(Office office, Model model) {
		model.addAttribute("list", officeService.findList(office));
		return "modules/sys/selectHandlerOfficeList";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "/form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent() == null || office.getParent().getId() == null) {
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea() == null) {
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId()) && office.getParent() != null) {
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i = 0; i < list.size(); i++) {
				Office e = list.get(i);
				if (e.getParent() != null && e.getParent().getId() != null && e.getParent().getId().equals(office.getParent().getId())) {
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size + 1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		return "modules/sys/officeForm";
	}

	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "/save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)) {
			return form(office, model);
		}
		officeService.save(office);

		if (office.getChildDeptList() != null) {
			Office childOffice = null;
			for (String id : office.getChildDeptList()) {
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade()) + 1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}

		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		return "redirect:" + adminPath + "/sys/office/list?id=" + id + "&parentIds=" + office.getParentIds();
	}

	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "/delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
		// if (Office.isRoot(id)){
		// addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
		// }else{
		officeService.delete(office);
		addMessage(redirectAttributes, "删除机构成功");
		// }
		return "redirect:" + adminPath + "/sys/office/list?id=" + office.getParentId() + "&parentIds=" + office.getParentIds();
	}

	/**
	 * 获取机构JSON数据。
	 * 
	 * @param extId
	 *            排除的ID
	 * @param type
	 *            类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade
	 *            显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String type,
			@RequestParam(required = false) Long grade, @RequestParam(required = false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i = 0; i < list.size(); i++) {
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue())) && Global.YES.equals(e.getUseable())) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)) {
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}

	/**
	 * 获取机构JSON数据。
	 * 
	 * @param extId
	 *            排除的ID
	 * @param type
	 *            类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade
	 *            显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/selectHandlerTreeData")
	public List<Map<String, Object>> selectHandlerTreeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String type,
			@RequestParam(required = false) Long grade, @RequestParam(required = false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i = 0; i < list.size(); i++) {
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue())) && Global.YES.equals(e.getUseable())) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)) {
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}

	/**
	 * 获取机构JSON数据。调用接口获取组织机构,最外层的公司结构
	 * 
	 * @param extId
	 *            排除的ID
	 * @param type
	 *            类型（0：平台；1：公司；2：部门/小组/其它；3：用户）
	 * @param grade
	 *            显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/treeData2")
	public List<Map<String, Object>> treeData2() {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		String urlkey = UserUtils.getUser().getSource();
		List<GroupInfo> list = UserUtils.getUserGroupInfo(urlkey);
		for (GroupInfo groupInfo : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", "g___" + urlkey + "___" + groupInfo.getGroupId());
			map.put("pId", "g___" + urlkey + "___" + groupInfo.getParentId());
			map.put("name", groupInfo.getName());
			map.put("type", groupInfo.getGroupType());// 最上面一层,类型为0

			mapList.add(map);
		}
		return mapList;
	}
	
	/*
	 * 获取不包括物业的组织机构，并显示配有公众号的组织机构
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/treeData3")
	public List<Map<String, Object>> treeData3() {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		String urlkey = UserUtils.getUser().getSource();
		List<GroupInfo> list = UserUtils.getUserGroupInfo(urlkey);
		for (GroupInfo groupInfo : list) {
			if(!groupInfo.getGroupType().equals("3") && !groupInfo.getGroupType().equals("4")){
				
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", "g___" + urlkey + "___" + groupInfo.getGroupId());
				map.put("pId", "g___" + urlkey + "___" + groupInfo.getParentId());
				if(checkWxAccount(urlkey, StringUtils.toInteger(groupInfo.getGroupId()))){
					map.put("name", groupInfo.getName()+"(有公众号)");
				}else{
					map.put("name", groupInfo.getName());
				}
				/*map.put("name", groupInfo.getName());*/
				map.put("type", groupInfo.getGroupType());// 最上面一层,类型为0

				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/*
	 * 获取不包括物业的组织机构，并显示拥有业务操作权限的组织机构
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/treeData31")
	public List<Map<String, Object>> treeData31() {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		String urlkey = UserUtils.getUser().getSource();
		List<GroupInfo> list = UserUtils.getUserGroupInfo(urlkey);
		for (GroupInfo groupInfo : list) {
			if(!groupInfo.getGroupType().equals("3") && !groupInfo.getGroupType().equals("4")){
				
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", "g___" + urlkey + "___" + groupInfo.getGroupId());
				map.put("pId", "g___" + urlkey + "___" + groupInfo.getParentId());
				if(checkWxAccount(urlkey, StringUtils.toInteger(groupInfo.getGroupId()))){
					map.put("name", groupInfo.getName()+"(有公众号)");
				}else{
					map.put("name", groupInfo.getName());
				}
				/*map.put("name", groupInfo.getName());*/
				map.put("type", groupInfo.getGroupType());// 最上面一层,类型为0

				mapList.add(map);
			}
		}
		return mapList;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/treeData32")
	public List<Map<String, Object>> treeData32(HttpServletRequest request) {
		List<Map<String, Object>> mapList;
		mapList = (List<Map<String, Object>>) request.getSession().getAttribute("treeData32");
		if(mapList!=null && mapList.size()>0){
			return mapList;
		}else{
			mapList = new ArrayList<Map<String, Object>>();
		}
		List<String> groupids = UserUtils.getEmployeeOfGroups();
		String urlkey = UserUtils.getUser().getSource();
		List<GroupInfo> list = UserUtils.getUserGroupInfo(urlkey);
		for (GroupInfo groupInfo : list) {
			if(!groupInfo.getGroupType().equals("3") && !groupInfo.getGroupType().equals("4")){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", "g___" + urlkey + "___" + groupInfo.getGroupId());
				map.put("pId", "g___" + urlkey + "___" + groupInfo.getParentId());
				String name = groupInfo.getName();
				for(String ss : groupids){
					if(groupInfo.getGroupId().equals(ss)){
						name += "(可操作)";
					}
				}
				map.put("name", name);
				map.put("type", groupInfo.getGroupType());// 最上面一层,类型为0

				mapList.add(map);
			}
		}
		request.getSession().setAttribute("treeData32", mapList);
		return mapList;
	}
	
	/**
	 * 不包含部门
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/treeData4")
	public List<Map<String, Object>> treeData4() {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		String urlkey = UserUtils.getUser().getSource();
		List<GroupInfo> list = UserUtils.getUserGroupInfo(urlkey);
		for (GroupInfo groupInfo : list) {
			if(!groupInfo.getGroupType().equals("3") && !groupInfo.getGroupType().equals("4")){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", "g___" + urlkey + "___" + groupInfo.getGroupId());
				map.put("pId", "g___" + urlkey + "___" + groupInfo.getParentId());
				map.put("name", groupInfo.getName());
				map.put("type", groupInfo.getGroupType());// 最上面一层,类型为0

				mapList.add(map);
			}
		}
		return mapList;
	}

	/**
	 * 获取机构JSON数据。调用接口获取组织机构,各个分支机构的组织机构
	 * 
	 * @param extId
	 *            排除的ID
	 * @param type
	 *            类型（-1：source；0：平台；1：公司；2：部门/小组/其它；3：用户）
	 * @param grade
	 *            显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/subTree")
	public List<Map<String, Object>> subTree(String pId, String id, String type, String urlkey) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		// 分类进行查询
		if ("-1".equals(type)) {
			List<GroupInfo> list = UserUtils.getUserGroupInfo(urlkey);
			for (GroupInfo groupInfo : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", "g___" + urlkey + "___" + groupInfo.getGroupId());
				map.put("pId", "g___" + urlkey + "___" + groupInfo.getParentId());
				map.put("name", groupInfo.getName());
				map.put("type", groupInfo.getGroupType());// 最上面一层,类型为0

				mapList.add(map);
			}
		} else if ("0".equals(type)) {

		}
		return mapList;
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/subTree1")
	public List<Map<String, Object>> subTree1(String pId, String id, String type, String urlkey) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		// 分类进行查询
		if ("-1".equals(type)) {
			List<GroupInfo> list = UserUtils.getUserGroupInfo(urlkey);
			for (GroupInfo groupInfo : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", "g___" + urlkey + "___" + groupInfo.getGroupId());
				map.put("pId", "g___" + urlkey + "___" + groupInfo.getParentId());
				map.put("name", groupInfo.getName());
				map.put("type", groupInfo.getGroupType());// 最上面一层,类型为0

				mapList.add(map);
			}
		} else if ("0".equals(type)) {

		}
		return mapList;
	}

	/**
	 * 通过source和groupid获取物业树结构
	 * 
	 * @param source
	 * @param groupId
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/getWYTree")
	public List<Map<String, Object>> getWYTree(String source, String accountId) {
		WxAccount wxAccount = wxAccountService.get(accountId);

		// 选择物业
		List<ZtreeDataDto> wyTreeList = UserUtils.getOrgWYTreeListSub(source, wxAccount.getGroupId().toString());
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (ZtreeDataDto dto : wyTreeList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", dto.getId());
			map.put("pId", dto.getPid());
			map.put("name", dto.getName());
			map.put("type", dto.getType());// 最上面一层,类型为0
			map.put("chkDisabled", dto.isChkDisabled());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 获取物业信息出错
	 * 
	 * @param source
	 * @param accountId
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/getWYBuildingList")
	public WeJson getWYBuildingList(String source, String accountId) {
		try {
			WxAccount wxAccount = wxAccountService.get(accountId);
			List<WyBuilding> buildingListAll = UserUtils.getWYListByGroupid(source, wxAccount.getGroupId().toString());

			return WeJson.success(buildingListAll);
		} catch (Exception e) {
			return WeJson.fail("获取物业信息出错");
		}
	}

	/**
	 * 获取楼栋列表
	 * 
	 * @param source
	 * @param accountId
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/getLouYuList")
	public WeJson getLouYuList(String source, String wyid) {
		try {

			List<LyBuilding> list = UserUtils.getLouYuList(source, wyid);
			return WeJson.success(list);
		} catch (Exception e) {
			logger.error("获取物业信息出错:{}", e);
			return WeJson.fail("获取物业信息出错");
		}
	}

	/**
	 * 通过楼栋获取会员信息
	 * 
	 * @param source
	 * @param lyid
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/getLyMemberList")
	public WeJson getLyMemberList(String source, String lyid, String memberName) {
		try {
			List<WyMember> list = wyMemberService.getMemberListByLyid(source, lyid, memberName);

			return WeJson.success(list);
		} catch (Exception e) {
			logger.error("获取物业信息出错:{}", e);
			return WeJson.fail("获取物业信息出错");
		}
	}
	
	public boolean checkWxAccount(String source, Integer groupid){
		WxAccount wxAccount = new WxAccount();
		wxAccount.setSource(source);
		wxAccount.setGroupId(groupid);
		List<WxAccount> wxAccounts = wxAccountService.findList(wxAccount);
		if(wxAccounts.size() > 0){
			return true;
		}
		return false;
	}
	
	public boolean checkBusiness(){
		
		
		return true;
	}
}
