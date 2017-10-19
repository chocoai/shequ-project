package com.haolinbang.modules.sns.web;

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
import com.haolinbang.common.web.BaseController;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sns.entity.WyFleaMarket;
import com.haolinbang.modules.sns.service.WyFleaMarketService;

/**
 * 跳蚤市场Controller
 * @author nlp
 * @version 2017-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyFleaMarket")
public class WyFleaMarketController extends BaseController {

	@Autowired
	private WyFleaMarketService wyFleaMarketService;
	
	@ModelAttribute
	public WyFleaMarket get(@RequestParam(required=false) String id) {
		WyFleaMarket entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyFleaMarketService.get(id);
		}
		if (entity == null){
			entity = new WyFleaMarket();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyFleaMarket:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyFleaMarket wyFleaMarket, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyFleaMarket> page = wyFleaMarketService.findPage(new Page<WyFleaMarket>(request, response), wyFleaMarket); 
		model.addAttribute("page", page);
		return "modules/sns/wyFleaMarketList";
	}

	@RequiresPermissions("sns:wyFleaMarket:view")
	@RequestMapping(value = "form")
	public String form(WyFleaMarket wyFleaMarket, Model model) {
		model.addAttribute("wyFleaMarket", wyFleaMarket);
		return "modules/sns/wyFleaMarketForm";
	}

	@RequiresPermissions("sns:wyFleaMarket:edit")
	@RequestMapping(value = "save")
	public String save(WyFleaMarket wyFleaMarket, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyFleaMarket)){
			return form(wyFleaMarket, model);
		}
		wyFleaMarketService.save(wyFleaMarket);
		addMessage(redirectAttributes, "保存跳蚤市场成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyFleaMarket/?repage";
	}
	
	@RequiresPermissions("sns:wyFleaMarket:edit")
	@RequestMapping(value = "delete")
	public String delete(WyFleaMarket wyFleaMarket, RedirectAttributes redirectAttributes) {
		wyFleaMarketService.delete(wyFleaMarket);
		addMessage(redirectAttributes, "删除跳蚤市场成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyFleaMarket/?repage";
	}

}