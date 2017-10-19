package com.haolinbang.modules.weixin.service.inter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.modules.weixin.entity.WxNewsArticle;

public interface WeixinService {

	public String deal(HttpServletRequest request, String token);

	public WeJson mass(HttpServletRequest request);

	public List<WxMaterialFileBatchGetNewsItem> getWxImages(int i, int j) throws WxErrorException, Exception;

	public WxNewsArticle addUrl(WxNewsArticle wxNewsArticle);

	public WxMediaUploadResult wxImagesUpload(String filename, String accountId) throws Exception;

	public List<WxMaterialNewsBatchGetNewsItem> getNewsList(int i, int j) throws WxErrorException;

	public String getBase64StrByMediaId(String thumbMediaId) throws Exception;

	public List<WxMpMassSendResult> sendMassNews(WxMpMassNews news, String accountId) throws WxErrorException;

	public WxMediaUploadResult wxImagesUpload(InputStream inputStream, String filename, String accountId) throws Exception;

	public Map<String, String> wxNewsImagesUpload(InputStream inputStream, String originalFilename) throws WxErrorException, IOException, RuntimeException;

	public String templateSend(WxMpTemplateMessage templateMessage, String accountId) throws WxErrorException;

}
