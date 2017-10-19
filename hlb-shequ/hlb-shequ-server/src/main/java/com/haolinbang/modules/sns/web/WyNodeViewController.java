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
import com.haolinbang.modules.sns.entity.WyNodeView;
import com.haolinbang.modules.sns.service.WyNodeViewService;

/**
 * 节点对应的页面显示Controller
 * @author nlp
 * @version 2017-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyNodeView")
public class WyNodeViewController extends BaseController {

	@Autowired
	private WyNodeViewService wyNodeViewService;
	
	@ModelAttribute
	public WyNodeView get(@RequestParam(required=false) String id) {
		WyNodeView entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyNodeViewService.get(id);
		}
		if (entity == null){
			entity = new WyNodeView();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyNodeView:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyNodeView wyNodeView, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyNodeView> page = wyNodeViewService.findPage(new Page<WyNodeView>(request, response), wyNodeView); 
		model.addAttribute("page", page);
		return "modules/sns/wyNodeViewList";
	}

	@RequiresPermissions("sns:wyNodeView:view")
	@RequestMapping(value = "form")
	public String form(WyNodeView wyNodeView, Model model) {
		model.addAttribute("wyNodeView", wyNodeView);
		return "modules/sns/wyNodeViewForm";
	}

	@RequiresPermissions("sns:wyNodeView:edit")
	@RequestMapping(value = "save")
	public String save(WyNodeView wyNodeView, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyNodeView)){
			return form(wyNodeView, model);
		}
		wyNodeViewService.save(wyNodeView);
		addMessage(redirectAttributes, "保存节点对应的页面显示成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyNodeView/?repage";
	}
	
	@RequiresPermissions("sns:wyNodeView:edit")
	@RequestMapping(value = "delete")
	public String delete(WyNodeView wyNodeView, RedirectAttributes redirectAttributes) {
		wyNodeViewService.delete(wyNodeView);
		addMessage(redirectAttributes, "删除节点对应的页面显示成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyNodeView/?repage";
	}

}