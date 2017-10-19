package com.haolinbang.modules.weixin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haolinbang.common.config.Global;

/**
 * 素材管理工具方法
 * 
 * @author Administrator
 * 
 */
public class MaterialUtil {

	private final static Logger log = LoggerFactory.getLogger(MaterialUtil.class);

	/**
	 * 上传文件
	 * 
	 * @return
	 */
	public static WxMpXmlOutMessage uploadImage(WxMpXmlMessage wxMessage, WxMpService wxMpService) {
		String realPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL + "/weixin/images/jj.png";
		File file = new File(realPath);
		String mediaId = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			try {
				WxMediaUploadResult res = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_PNG, fis);
				mediaId = res.getMediaId();
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			log.debug("FileNotFoundException={}", e.getMessage());
			e.printStackTrace();
		}

		WxMpXmlOutMessage msg = XmlMsgUtil.toImageMsg(wxMessage, mediaId);
		log.debug("IMAGE  msg={}", msg);
		return msg;
	}

}
