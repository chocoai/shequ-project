package com.haolinbang.modules.sns.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.haolinbang.common.thridwy.haolong.bean.GetProperty;
import com.haolinbang.common.thridwy.haolong.bean.bean.Property;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.entity.WyBuilding;
import com.haolinbang.modules.sns.service.UrlmapService;
import com.haolinbang.modules.sns.service.WyBuildingService;

/**
 * 楼盘表Controller
 * 
 * @author wxc
 * @version 2017-07-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyBuilding")
public class WyBuildingController extends BaseController {

	@Autowired
	private WyBuildingService wyBuildingService;

	@Autowired
	private UrlmapService urlmapService;

	@ModelAttribute
	public WyBuilding get(@RequestParam(required = false) String id) {
		WyBuilding entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyBuildingService.get(id);
		}
		if (entity == null) {
			entity = new WyBuilding();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyBuilding:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyBuilding wyBuilding, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyBuilding> page = wyBuildingService.findPage(new Page<WyBuilding>(request, response), wyBuilding);
		model.addAttribute("page", page);
		return "modules/sns/wyBuildingList";
	}

	@RequiresPermissions("sns:wyBuilding:view")
	@RequestMapping(value = "form")
	public String form(WyBuilding wyBuilding, Model model) {
		model.addAttribute("wyBuilding", wyBuilding);
		return "modules/sns/wyBuildingForm";
	}

	@RequiresPermissions("sns:wyBuilding:edit")
	@RequestMapping(value = "save")
	public String save(WyBuilding wyBuilding, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyBuilding)) {
			return form(wyBuilding, model);
		}
		wyBuildingService.save(wyBuilding);
		addMessage(redirectAttributes, "保存楼盘表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyBuilding/?repage";
	}

	@RequiresPermissions("sns:wyBuilding:edit")
	@RequestMapping(value = "delete")
	public String delete(WyBuilding wyBuilding, RedirectAttributes redirectAttributes) {
		wyBuildingService.delete(wyBuilding);
		addMessage(redirectAttributes, "删除楼盘表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyBuilding/?repage";
	}

	@RequiresPermissions("sns:wyBuilding:edit")
	@RequestMapping(value = "update")
	public String update(HttpServletRequest request, RedirectAttributes redirectAttributes, String source, String wyid) {
		Date date = new Date();
		System.out.println(date);
		String type = "更新";
		List<Urlmap> urlmapList = new ArrayList<Urlmap>();
		Urlmap urlmaptemp = new Urlmap();
		urlmaptemp.setStatus("1");
		if (StringUtils.isNotBlank(source)) {
			type = "添加";
			urlmaptemp.setUrlkey(source);
		} else {
			/*wyBuildingService.deleteAll();*/
		}
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		urlmapList = urlmapService.getUrlmap1(urlmaptemp);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		if (urlmapList != null && urlmapList.size() > 0) {
			for (Urlmap urlmap : urlmapList) {
				GetProperty getProperty = new GetProperty();
				getProperty.setUrlString(urlmap.getUrlstring());
				getProperty.setSoapActionString(urlmap.getSoapactionstring());
				getProperty.setSecretkey(urlmap.getSecretkey());
				getProperty.setWYID("");
				if (StringUtils.isNotBlank(wyid)) {
					getProperty.setWYID(wyid);
				}
				List<Property> propertyInfoList = null;
				try {
					propertyInfoList = HaolongUtils.getProperty(getProperty);
				}catch (Exception e) {}
					
				if(propertyInfoList != null){
					for (Property property : propertyInfoList) {
						WyBuilding wyBuilding = new WyBuilding();
						wyBuilding.setSource(urlmap.getUrlkey());
						wyBuilding.setWyid(property.getWYID());
						
						WyBuilding wb = wyBuildingService.getBySourceAndWyid(wyBuilding);
						wyBuilding.setProvince("");
						wyBuilding.setCity("");
						wyBuilding.setArea("");
						wyBuilding.setAddress(property.getAddress());
						wyBuilding.setWybh(property.getWYBH());
						wyBuilding.setWymc(property.getWYMC());
						wyBuilding.setGroupId(property.getGroupId());
						if(wb != null){
							wyBuildingService.update(wyBuilding);
						}else{
							wyBuildingService.save(wyBuilding);
						}
					}
				}
				
				
			}
			addMessage(redirectAttributes, type + "成功！");
		} else {
			addMessage(redirectAttributes, type + "失败！");
		}

		date = new Date();
		System.out.println(date);
		return "redirect:" + Global.getAdminPath() + "/sns/wyBuilding/?repage";
	}

}