package com.haolinbang.modules.weixin.web;

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
import com.haolinbang.modules.weixin.entity.WxMaterialImages;
import com.haolinbang.modules.weixin.service.WxMaterialImagesService;

/**
 * 图片素材对应关系表Controller
 * @author nlp
 * @version 2017-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMaterialImages")
public class WxMaterialImagesController extends BaseController {

	@Autowired
	private WxMaterialImagesService wxMaterialImagesService;
	
	@ModelAttribute
	public WxMaterialImages get(@RequestParam(required=false) String id) {
		WxMaterialImages entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMaterialImagesService.get(id);
		}
		if (entity == null){
			entity = new WxMaterialImages();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:wxMaterialImages:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMaterialImages wxMaterialImages, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMaterialImages> page = wxMaterialImagesService.findPage(new Page<WxMaterialImages>(request, response), wxMaterialImages); 
		model.addAttribute("page", page);
		return "modules/weixin/wxMaterialImagesList";
	}

	@RequiresPermissions("weixin:wxMaterialImages:view")
	@RequestMapping(value = "form")
	public String form(WxMaterialImages wxMaterialImages, Model model) {
		model.addAttribute("wxMaterialImages", wxMaterialImages);
		return "modules/weixin/wxMaterialImagesForm";
	}

	@RequiresPermissions("weixin:wxMaterialImages:edit")
	@RequestMapping(value = "save")
	public String save(WxMaterialImages wxMaterialImages, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMaterialImages)){
			return form(wxMaterialImages, model);
		}
		wxMaterialImagesService.save(wxMaterialImages);
		addMessage(redirectAttributes, "保存图片素材对应关系表成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxMaterialImages/?repage";
	}
	
	@RequiresPermissions("weixin:wxMaterialImages:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMaterialImages wxMaterialImages, RedirectAttributes redirectAttributes) {
		wxMaterialImagesService.delete(wxMaterialImages);
		addMessage(redirectAttributes, "删除图片素材对应关系表成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxMaterialImages/?repage";
	}

}