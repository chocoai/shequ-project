package com.haolinbang.modules.sns.web;

import java.util.Date;

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
import com.haolinbang.modules.sns.entity.WyConvenienceService;
import com.haolinbang.modules.sns.service.WyConvenienceServiceService;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 便民服务Controller
 * @author wxc
 * @version 2017-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyConvenienceService")
public class WyConvenienceServiceController extends BaseController {

	@Autowired
	private WyConvenienceServiceService wyConvenienceServiceService;
	
	@ModelAttribute
	public WyConvenienceService get(@RequestParam(required=false) String id) {
		WyConvenienceService entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyConvenienceServiceService.get(id);
		}
		if (entity == null){
			entity = new WyConvenienceService();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyConvenienceService:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyConvenienceService wyConvenienceService, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyConvenienceService> page = wyConvenienceServiceService.findPage(new Page<WyConvenienceService>(request, response), wyConvenienceService); 
		model.addAttribute("page", page);

		if(page.getList()!=null && page.getList().size()>0){
			wyConvenienceService = page.getList().get(0);
		}else{
			wyConvenienceService = new WyConvenienceService();
			wyConvenienceService.setCreateTime(new Date());
			wyConvenienceServiceService.save(wyConvenienceService);
		}
		
		model.addAttribute("wyConvenienceService", wyConvenienceService);
		return "modules/sns/wyConvenienceServiceForm";
		//return "modules/sns/wyConvenienceServiceList";
	}

	@RequiresPermissions("sns:wyConvenienceService:view")
	@RequestMapping(value = "form")
	public String form(WyConvenienceService wyConvenienceService, Model model) {
		model.addAttribute("wyConvenienceService", wyConvenienceService);
		return "modules/sns/wyConvenienceServiceForm";
	}

	@RequiresPermissions("sns:wyConvenienceService:edit")
	@RequestMapping(value = "save")
	public String save(WyConvenienceService wyConvenienceService, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyConvenienceService)){
			return form(wyConvenienceService, model);
		}
		if(wyConvenienceService.getServiceId()!=null){
			wyConvenienceService.setId(wyConvenienceService.getServiceId().toString());
		}else{
			User user = UserUtils.getUser();
			if(user!=null){
				wyConvenienceService.setSource(user.getSource());
				wyConvenienceService.setGroupId(user.getGroupId());
			}
		}
		wyConvenienceServiceService.save(wyConvenienceService);
		addMessage(redirectAttributes, "保存便民服务成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyConvenienceService/?repage";
	}
	
	@RequiresPermissions("sns:wyConvenienceService:edit")
	@RequestMapping(value = "delete")
	public String delete(WyConvenienceService wyConvenienceService, RedirectAttributes redirectAttributes) {
		wyConvenienceServiceService.delete(wyConvenienceService);
		addMessage(redirectAttributes, "删除便民服务成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyConvenienceService/?repage";
	}
	
	

}