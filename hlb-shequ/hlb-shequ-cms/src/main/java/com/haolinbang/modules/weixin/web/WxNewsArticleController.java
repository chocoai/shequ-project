package com.haolinbang.modules.weixin.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.entity.WxNewsArticle;
import com.haolinbang.modules.weixin.service.WxNewsArticleService;
import com.haolinbang.modules.weixin.service.inter.WeixinService;
import com.haolinbang.modules.weixin.service.inter.WxAccountService;

/**
 * 微信图文消息Controller
 * 
 * @author nlp
 * @version 2017-07-11
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxNewsArticle")
public class WxNewsArticleController extends BaseController {

	@Autowired
	private WxNewsArticleService wxNewsArticleService;

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private WxAccountService wxAccountService;

	@ModelAttribute
	public WxNewsArticle get(@RequestParam(required = false) String id) {
		WxNewsArticle entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxNewsArticleService.get(id);
		}
		if (entity == null) {
			entity = new WxNewsArticle();
		}
		return entity;
	}

	@RequiresPermissions("weixin:wxNewsArticle:view")
	@RequestMapping(value = { "list", "" })
	public String list(WxNewsArticle wxNewsArticle, HttpServletRequest request, HttpServletResponse response, Model model) {
		WxAccount wxAccount = wxAccountService.getDefaultWxAccount();
		wxNewsArticle.setAccountId(wxAccount.getId());
		Page<WxNewsArticle> page = wxNewsArticleService.findPage(new Page<WxNewsArticle>(request, response), wxNewsArticle);
		model.addAttribute("page", page);
		return "modules/weixin/wxNewsArticleList";
	}

	@RequiresPermissions("weixin:wxNewsArticle:view")
	@RequestMapping(value = "form")
	public String form(WxNewsArticle wxNewsArticle, Model model) {
		wxNewsArticle = weixinService.addUrl(wxNewsArticle);
		model.addAttribute("wxNewsArticle", wxNewsArticle);
		return "modules/weixin/wxNewsArticleForm";
	}

	@RequiresPermissions("weixin:wxNewsArticle:edit")
	@RequestMapping(value = "save")
	public String save(WxNewsArticle wxNewsArticle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxNewsArticle)) {
			return form(wxNewsArticle, model);
		}

		WxAccount wxAccount = wxAccountService.getDefaultWxAccount();
		wxNewsArticle.setAccountId(wxAccount.getId());

		wxNewsArticleService.save(wxNewsArticle);
		addMessage(redirectAttributes, "保存微信图文消息成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxNewsArticle/?repage";
	}

	@RequiresPermissions("weixin:wxNewsArticle:edit")
	@RequestMapping(value = "delete")
	public String delete(WxNewsArticle wxNewsArticle, RedirectAttributes redirectAttributes) {
		wxNewsArticleService.delete(wxNewsArticle);
		addMessage(redirectAttributes, "删除微信图文消息成功");
		return "redirect:" + Global.getAdminPath() + "/weixin/wxNewsArticle/?repage";
	}

	@RequestMapping("/wxImagesSelect")
	public String wxImagesSelect(Model model) {
		// 获取图片资料
		try {
			List<WxMaterialFileBatchGetNewsItem> images = weixinService.getWxImages(0, 20);
			model.addAttribute("images", images);
		} catch (Exception e) {
			logger.error("获取图片资料出错");
		}
		return "modules/weixin/wxImagesSelect";
	}

	/**
	 * 上传封面图片资料
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/wxImagesUpload")
	public WeJson wxImagesUpload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
		try {
			WxMediaUploadResult uploadMediaRes = weixinService.wxImagesUpload(file.getInputStream(), file.getOriginalFilename());
			return WeJson.success(uploadMediaRes);
		} catch (Exception e) {
			logger.error("上传图片资料出错:{}", e);
			return WeJson.fail("上传图片资料出错");
		}
	}

	/**
	 * 上传图文消息内部的图片
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/wxNewsImagesUpload")
	public WeJson wxNewsImagesUpload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
		try {
			Map<String, String> map = weixinService.wxNewsImagesUpload(file.getInputStream(), file.getOriginalFilename());
			return WeJson.success(map);
		} catch (Exception e) {
			logger.error("上传图片资料出错:{}", e);
			return WeJson.fail("上传图片资料出错");
		}
	}

}