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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
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
			wyBuildingService.deleteAll();
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
				try {
					List<Property> propertyInfoList = HaolongUtils.getProperty(getProperty);
					for (Property property : propertyInfoList) {
						WyBuilding wyBuilding = new WyBuilding();
						wyBuilding.setSource(urlmap.getUrlkey());
						wyBuilding.setWyid(property.getWYID());
						wyBuilding.setProvince("");
						wyBuilding.setCity("");
						wyBuilding.setArea("");
						wyBuilding.setAddress(property.getAddress());
						wyBuilding.setWybh(property.getWYBH());
						wyBuilding.setWymc(property.getWYMC());
						wyBuildingService.save(wyBuilding);
					}
				} catch (Exception e) {
					logger.error("getContractA() : {}", e);
					e.printStackTrace();
					addMessage(redirectAttributes, "系统出错！");
					return "redirect:" + Global.getAdminPath() + "/sns/wyBuilding/?repage";
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

	@RequiresPermissions("sns:wyBuilding:view")
	@RequestMapping("/getBuildsBySource")
	public String getBuildsBySource(WyBuilding wyBuilding, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyBuilding> page = wyBuildingService.findPage(new Page<WyBuilding>(request, response), wyBuilding);
		model.addAttribute("page", page);
		return "modules/sns/wyBuildingSelect";
	}

	@ResponseBody
	@RequestMapping("/getTestJson")
	public String getTestJson() {
		String jsonStr = "{\"recordsFiltered\":57,\"data\":[{\"name\":\"于怀斌\",\"title\":\"大区经理\",\"base\":\"苏州\",\"age\":28,\"hireDate\":\"2010/11/14\",\"salary\":357650,\"DT_RowId\":27},{\"name\":\"刘伟峰\",\"title\":\"软件工程师\",\"base\":\"深圳\",\"age\":56,\"hireDate\":\"2012/06/01\",\"salary\":115000,\"DT_RowId\":49},{\"name\":\"刘勇\",\"title\":\"客户服务\",\"base\":\"北京\",\"age\":22,\"hireDate\":\"2013/03/03\",\"salary\":342000,\"DT_RowId\":12},{\"name\":\"刘晋荣\",\"title\":\"办公室主管\",\"base\":\"深圳\",\"age\":51,\"hireDate\":\"2008/12/16\",\"salary\":164500,\"DT_RowId\":37},{\"name\":\"利旭日\",\"title\":\"CEO\",\"base\":\"广州\",\"age\":47,\"hireDate\":\"2009/10/09\",\"salary\":1200000,\"DT_RowId\":25},{\"name\":\"吴书振\",\"title\":\"集成专家\",\"base\":\"南京\",\"age\":61,\"hireDate\":\"2012/12/03\",\"salary\":372000,\"DT_RowId\":6},{\"name\":\"孙凯\",\"title\":\"系统管理员\",\"base\":\"南京\",\"age\":59,\"hireDate\":\"2009/04/10\",\"salary\":237500,\"DT_RowId\":18},{\"name\":\"孙宁\",\"title\":\"秘书\",\"base\":\"深圳\",\"age\":41,\"hireDate\":\"2010/02/12\",\"salary\":109850,\"DT_RowId\":38},{\"name\":\"尚志兴\",\"title\":\"高级营销设计师\",\"base\":\"广州\",\"age\":43,\"hireDate\":\"2013/12/18\",\"salary\":313500,\"DT_RowId\":14},{\"name\":\"张宁\",\"title\":\"销售代表\",\"base\":\"深圳\",\"age\":59,\"hireDate\":\"2012/08/06\",\"salary\":137500,\"DT_RowId\":7},{\"name\":\"张忆湫\",\"title\":\"大区经理\",\"base\":\"深圳\",\"age\":36,\"hireDate\":\"2008/10/16\",\"salary\":470600,\"DT_RowId\":13},{\"name\":\"张海龙\",\"title\":\"售后支持\",\"base\":\"北京\",\"age\":46,\"hireDate\":\"2011/03/09\",\"salary\":324050,\"DT_RowId\":35},{\"name\":\"张淑杰\",\"title\":\"COO\",\"base\":\"深圳\",\"age\":48,\"hireDate\":\"2010/03/11\",\"salary\":850000,\"DT_RowId\":29},{\"name\":\"张禄\",\"title\":\"主任\",\"base\":\"南京\",\"age\":65,\"hireDate\":\"2008/09/26\",\"salary\":645750,\"DT_RowId\":41},{\"name\":\"张章\",\"title\":\"Javascript开发者\",\"base\":\"深圳\",\"age\":39,\"hireDate\":\"2009/09/15\",\"salary\":205500,\"DT_RowId\":9},{\"name\":\"张竹影\",\"title\":\"软件工程师\",\"base\":\"北京\",\"age\":23,\"hireDate\":\"2008/12/15\",\"salary\":103600,\"DT_RowId\":10},{\"name\":\"徐成业\",\"title\":\"Javascript开发者\",\"base\":\"苏州\",\"age\":29,\"hireDate\":\"2011/06/27\",\"salary\":183000,\"DT_RowId\":56},{\"name\":\"徐若琳\",\"title\":\"大区经理\",\"base\":\"北京\",\"age\":51,\"hireDate\":\"2008/11/13\",\"salary\":183000,\"DT_RowId\":55},{\"name\":\"戴向军\",\"title\":\"技术支持工程师\",\"base\":\"深圳\",\"age\":47,\"hireDate\":\"2009/07/07\",\"salary\":87500,\"DT_RowId\":46},{\"name\":\"施华军\",\"title\":\"会计\",\"base\":\"上海\",\"age\":33,\"hireDate\":\"2008/11/28\",\"salary\":162700,\"DT_RowId\":5},{\"name\":\"李岸白\",\"title\":\"客户服务\",\"base\":\"南京\",\"age\":27,\"hireDate\":\"2011/01/25\",\"salary\":112000,\"DT_RowId\":57},{\"name\":\"李霞\",\"title\":\"系统架构师\",\"base\":\"北京\",\"age\":61,\"hireDate\":\"2011/04/25\",\"salary\":320800,\"DT_RowId\":1},{\"name\":\"杜若芳\",\"title\":\"大区经理\",\"base\":\"广州\",\"age\":19,\"hireDate\":\"2010/03/17\",\"salary\":385750,\"DT_RowId\":15},{\"name\":\"杜重治\",\"title\":\"会计\",\"base\":\"上海\",\"age\":63,\"hireDate\":\"2011/07/25\",\"salary\":170750,\"DT_RowId\":2},{\"name\":\"杨乔松\",\"title\":\"营销设计师\",\"base\":\"广州\",\"age\":66,\"hireDate\":\"2012/11/27\",\"salary\":198500,\"DT_RowId\":16},{\"name\":\"杨军\",\"title\":\"团队主管\",\"base\":\"深圳\",\"age\":22,\"hireDate\":\"2008/10/26\",\"salary\":235500,\"DT_RowId\":34},{\"name\":\"杨正机\",\"title\":\"开发者\",\"base\":\"南京\",\"age\":61,\"hireDate\":\"2013/08/13\",\"salary\":985400,\"DT_RowId\":45},{\"name\":\"杨海霞\",\"title\":\"销售代表\",\"base\":\"成都\",\"age\":23,\"hireDate\":\"2010/09/20\",\"salary\":85600,\"DT_RowId\":24},{\"name\":\"林云\",\"title\":\"开发者\",\"base\":\"广州\",\"age\":53,\"hireDate\":\"2009/10/22\",\"salary\":114500,\"DT_RowId\":32},{\"name\":\"汪化言\",\"title\":\"技术支持工程师\",\"base\":\"上海\",\"age\":37,\"hireDate\":\"2009/08/19\",\"salary\":139575,\"DT_RowId\":44},{\"name\":\"沈健\",\"title\":\"软件工程师\",\"base\":\"南京\",\"age\":63,\"hireDate\":\"2010/01/14\",\"salary\":125250,\"DT_RowId\":48},{\"name\":\"沈国金\",\"title\":\"系统管理员\",\"base\":\"广州\",\"age\":21,\"hireDate\":\"2009/02/27\",\"salary\":103500,\"DT_RowId\":53},{\"name\":\"沈捷\",\"title\":\"软件工程师\",\"base\":\"广州\",\"age\":38,\"hireDate\":\"2011/05/03\",\"salary\":163500,\"DT_RowId\":43},{\"name\":\"王增凤\",\"title\":\"技术支持工程师\",\"base\":\"苏州\",\"age\":64,\"hireDate\":\"2011/02/03\",\"salary\":234500,\"DT_RowId\":42},{\"name\":\"王延芳\",\"title\":\"数据分析工程师\",\"base\":\"苏州\",\"age\":64,\"hireDate\":\"2012/02/09\",\"salary\":138575,\"DT_RowId\":47},{\"name\":\"王燕峰\",\"title\":\"开发者\",\"base\":\"深圳\",\"age\":30,\"hireDate\":\"2010/07/14\",\"salary\":86500,\"DT_RowId\":54},{\"name\":\"白莉惠\",\"title\":\"售前支持\",\"base\":\"南京\",\"age\":21,\"hireDate\":\"2011/12/12\",\"salary\":106450,\"DT_RowId\":23},{\"name\":\"纪虹羽\",\"title\":\"销售代表\",\"base\":\"南京\",\"age\":46,\"hireDate\":\"2011/12/06\",\"salary\":145600,\"DT_RowId\":51},{\"name\":\"耿静\",\"title\":\"营销设计师\",\"base\":\"深圳\",\"age\":47,\"hireDate\":\"2009/12/09\",\"salary\":85675,\"DT_RowId\":36},{\"name\":\"肖艳\",\"title\":\"大区经理\",\"base\":\"广州\",\"age\":47,\"hireDate\":\"2011/03/21\",\"salary\":356250,\"DT_RowId\":52},{\"name\":\"范永胜\",\"title\":\"开发者\",\"base\":\"北京\",\"age\":42,\"hireDate\":\"2010/12/22\",\"salary\":92575,\"DT_RowId\":26},{\"name\":\"贺光明\",\"title\":\"技术主管\",\"base\":\"南京\",\"age\":30,\"hireDate\":\"2011/09/03\",\"salary\":345000,\"DT_RowId\":21},{\"name\":\"赖祥校\",\"title\":\"软件工程师\",\"base\":\"广州\",\"age\":41,\"hireDate\":\"2012/10/13\",\"salary\":132000,\"DT_RowId\":19},{\"name\":\"赵淑娜\",\"title\":\"软件工程师\",\"base\":\"深圳\",\"age\":28,\"hireDate\":\"2011/06/07\",\"salary\":206850,\"DT_RowId\":28},{\"name\":\"邓小燕\",\"title\":\"CMO\",\"base\":\"南京\",\"age\":40,\"hireDate\":\"2009/06/25\",\"salary\":675000,\"DT_RowId\":22},{\"name\":\"邢洪锐\",\"title\":\"财务总监\",\"base\":\"深圳\",\"age\":62,\"hireDate\":\"2009/02/14\",\"salary\":452500,\"DT_RowId\":39},{\"name\":\"郑伯宁\",\"title\":\"高级JavaScript开发者\",\"base\":\"北京\",\"age\":22,\"hireDate\":\"2012/03/29\",\"salary\":433060,\"DT_RowId\":4},{\"name\":\"郭增杰\",\"title\":\"集成专家\",\"base\":\"成都\",\"age\":37,\"hireDate\":\"2011/06/02\",\"salary\":95400,\"DT_RowId\":31},{\"name\":\"郭晖\",\"title\":\"人事主管\",\"base\":\"北京\",\"age\":35,\"hireDate\":\"2012/09/26\",\"salary\":217500,\"DT_RowId\":20},{\"name\":\"郭述龙\",\"title\":\"技术作者\",\"base\":\"广州\",\"age\":27,\"hireDate\":\"2011/05/07\",\"salary\":145000,\"DT_RowId\":33},{\"name\":\"闫跃进\",\"title\":\"CEO\",\"base\":\"南京\",\"age\":64,\"hireDate\":\"2010/06/09\",\"salary\":725000,\"DT_RowId\":17},{\"name\":\"陆先生\",\"title\":\"初级Javascript开发者\",\"base\":\"北京\",\"age\":43,\"hireDate\":\"2013/02/01\",\"salary\":75650,\"DT_RowId\":50},{\"name\":\"陈云云\",\"title\":\"办公室主管\",\"base\":\"广州\",\"age\":37,\"hireDate\":\"2008/12/11\",\"salary\":136200,\"DT_RowId\":40},{\"name\":\"陈俊军\",\"title\":\"区域销售\",\"base\":\"上海\",\"age\":20,\"hireDate\":\"2011/08/15\",\"salary\":163000,\"DT_RowId\":30},{\"name\":\"陈锋\",\"title\":\"初级开发者\",\"base\":\"深圳\",\"age\":66,\"hireDate\":\"2009/01/12\",\"salary\":86000,\"DT_RowId\":3},{\"name\":\"韩庆福\",\"title\":\"办公室主管\",\"base\":\"广州\",\"age\":30,\"hireDate\":\"2008/12/19\",\"salary\":90560,\"DT_RowId\":11},{\"name\":\"马世波\",\"title\":\"集成专家\",\"base\":\"上海\",\"age\":55,\"hireDate\":\"2010/10/14\",\"salary\":327900,\"DT_RowId\":8}],\"draw\":0,\"recordsTotal\":57}";

		return jsonStr;
	}

}