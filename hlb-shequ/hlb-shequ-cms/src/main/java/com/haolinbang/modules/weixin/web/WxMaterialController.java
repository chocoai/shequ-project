package com.haolinbang.modules.weixin.web;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialCountResult;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialNewsBatchGetResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haolinbang.common.web.BaseController;

/**
 * 微信素材管理
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMaterial")
public class WxMaterialController extends BaseController {

	@Autowired
	private WxMpService wxMpService;

	/**
	 * 微信图文管理
	 * 
	 * @return
	 */
	@RequestMapping("/imageText")
	public String imageText(Model model) {
		// 调用接口获取素材信息
		try {
			// 获取素材总数
			WxMpMaterialCountResult wxMpMaterialCountResult = wxMpService.materialCount();
			//图文消息数
			int newsCount = wxMpMaterialCountResult.getNewsCount();

			// 根据类别分页获取非图文素材列表,获取图片
			WxMpMaterialFileBatchGetResult wxMpMaterialFileBatchGetResult = wxMpService.materialFileBatchGet(WxConsts.MATERIAL_IMAGE, 0, 10);

			// 根据类别分页获取图文素材列表
			WxMpMaterialNewsBatchGetResult wxMpMaterialNewsBatchGetResult = wxMpService.materialNewsBatchGet(0, 10);
			
			
			model.addAttribute("wxMpMaterialCountResult", wxMpMaterialCountResult);
			model.addAttribute("wxMpMaterialFileBatchGetResult", wxMpMaterialFileBatchGetResult);
			model.addAttribute("wxMpMaterialNewsBatchGetResult", wxMpMaterialNewsBatchGetResult);

		} catch (WxErrorException e) {
			logger.error("获取图文素材失败:{}", e);

		}

		return "modules/weixin/wxMaterial/imageText";
	}

}
