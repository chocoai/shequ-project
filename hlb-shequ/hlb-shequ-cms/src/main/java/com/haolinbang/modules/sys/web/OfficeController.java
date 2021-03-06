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
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.service.UrlmapService;
import com.haolinbang.modules.sys.entity.Office;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.service.OfficeService;
import com.haolinbang.modules.sys.utils.DictUtils;
import com.haolinbang.modules.sys.utils.UserUtils;

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
	private UrlmapService urlmapService;

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
		String gid = null;
		if (StringUtils.isNotBlank(id)) {
			Map<String, String> map = UserUtils.parseGroupids(gid);
			source = map.get("source");
			type = map.get("type");
			gid = map.get("gid");
		}

		List<Office> list = null;
		if ("g".equals(type)) {
			list = UserUtils.getSubGroupList(gid, source);
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
	@RequestMapping(value = "/authTreeData")
	public List<Map<String, Object>> authTreeData(String source) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		List<Urlmap> urlmapList = urlmapService.getUrlmapBySource(source);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

		// 默认查询第一条
		if (urlmapList != null && !urlmapList.isEmpty()) {
			Urlmap url = urlmapList.get(0);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", "g___" + url.getUrlkey() + "___" + url.getId());
			map.put("pId", "-1");
			map.put("name", url.getUrlname() + "(" + url.getUrlkey() + ")");
			map.put("type", "-1");// 最上面一层,类型为0
			map.put("isParent", true);// 是父节点
			map.put("urlkey", url.getUrlkey());
			mapList.add(map);

		}
		// for (Urlmap url : urlmapList) {
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("id", "g___" + url.getUrlkey() + "___" + url.getId());
		// map.put("pId", "-1");
		// map.put("name", url.getUrlname() + "(" + url.getUrlkey() + ")");
		// map.put("type", "-1");// 最上面一层,类型为0
		// map.put("isParent", true);// 是父节点
		// map.put("urlkey", url.getUrlkey());
		// mapList.add(map);
		// }

		return mapList;
	}

	/**
	 * 获取机构JSON数据，通过source查询公司
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
	@RequestMapping(value = "/wyTreeData")
	public List<Map<String, Object>> wyTreeData(String source, String name) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		List<Urlmap> urlmapList = urlmapService.getUrlmapBySource(source);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

		for (Urlmap url : urlmapList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", "s___" + url.getUrlkey() + "___" + url.getId());// source开头
			map.put("pId", "-1");
			map.put("name", url.getUrlname() + "(" + url.getUrlkey() + ")");
			map.put("type", "-1");// 最上面一层,类型为0
			map.put("isParent", true);// 是父节点
			map.put("urlkey", url.getUrlkey());
			mapList.add(map);
		}

		return mapList;
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "/searchOrg")
	public String searchOrg(Urlmap urlmap, String source, Model model, HttpServletRequest request, HttpServletResponse response) {
		Page<Urlmap> page = urlmapService.findPage(new Page<Urlmap>(request, response), urlmap);
		model.addAttribute("urlmap", urlmap);
		model.addAttribute("page", page);
		return "modules/sys/officeSearch";
	}

	/**
	 * 获取机构JSON数据。调用接口获取组织机构,各个分支机构的组织机构
	 * 
	 * @param extId
	 *            排除的ID
	 * @param type
	 *            类型（0：平台；1：公司；2：部门/小组/其它；3：用户）
	 * @param grade
	 *            显示级别
	 * @param gtype
	 *            获取哪种类型的
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/subTree")
	public List<Map<String, Object>> subTree(String pId, String id, String type, String urlkey, String gtype) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		// 分类进行查询
		if ("-1".equals(type)) {
			List<GroupInfo> list = null;
			List<GroupInfo> list1 = new ArrayList<GroupInfo>();
			list = UserUtils.getAllGetGroupInfo(urlkey);
			if (list != null && list.size() > 0) {
				for (GroupInfo groupInfo : list) {
					String groupType = groupInfo.getGroupType();
					String parentID = groupInfo.getParentId();
					// 查询组织机构类型
					if ("1".equals(gtype)) {
						if (groupType.equals("0") || (groupType.equals("3") && parentID.equals("1"))) {
							list1.add(groupInfo);
						}
					} else if ("2".equals(gtype)) {
						if ("0".equals(groupType) || "1".equals(groupType) || "2".equals(groupType)) {
							list1.add(groupInfo);
						}
					} else {
						list1.add(groupInfo);
					}
				}
			}

			logger.info("返回的调用组织结构：" + list1);

			for (GroupInfo info : list1) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", "g___" + urlkey + "___" + info.getGroupId());// 组织结构的用g开头
				map.put("pId", "g___" + urlkey + "___" + info.getParentId());
				map.put("name", info.getName());
				map.put("type", info.getGroupType());// 最上面一层,类型为0

				mapList.add(map);
			}
		} else if ("0".equals(type)) {

		}
		return mapList;
	}
}
