package com.haolinbang.modules.weixin.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageInterceptor;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialNewsBatchGetResult;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialUploadNewsImgResult;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.common.utils.FileUtils;
import com.haolinbang.common.utils.HttpUtils;
import com.haolinbang.common.utils.QRCodeUtil;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.entity.WxMaterialImages;
import com.haolinbang.modules.weixin.entity.WxMessage;
import com.haolinbang.modules.weixin.entity.WxNewsArticle;
import com.haolinbang.modules.weixin.service.WxMaterialImagesService;
import com.haolinbang.modules.weixin.service.impl.receive.event.ClickHandler;
import com.haolinbang.modules.weixin.service.impl.receive.event.LocationEventHandler;
import com.haolinbang.modules.weixin.service.impl.receive.event.ScanHandler;
import com.haolinbang.modules.weixin.service.impl.receive.event.SubscribeHandler;
import com.haolinbang.modules.weixin.service.impl.receive.event.UnsubscribeHandler;
import com.haolinbang.modules.weixin.service.impl.receive.event.ViewHandler;
import com.haolinbang.modules.weixin.service.impl.receive.normal.ImageHandler;
import com.haolinbang.modules.weixin.service.impl.receive.normal.LinkHandler;
import com.haolinbang.modules.weixin.service.impl.receive.normal.LocationHandler;
import com.haolinbang.modules.weixin.service.impl.receive.normal.MassSendJobFinishHandler;
import com.haolinbang.modules.weixin.service.impl.receive.normal.ShortvideoHandler;
import com.haolinbang.modules.weixin.service.impl.receive.normal.TemplateMsgSendJobFinishHandler;
import com.haolinbang.modules.weixin.service.impl.receive.normal.TextHandler;
import com.haolinbang.modules.weixin.service.impl.receive.normal.VideoHandler;
import com.haolinbang.modules.weixin.service.impl.receive.normal.VoiceHandler;
import com.haolinbang.modules.weixin.service.inter.WeixinService;
import com.haolinbang.modules.weixin.service.inter.WxAccountService;

@SuppressWarnings("deprecation")
@Service
public class WeixinServiceImpl implements WeixinService {
	private Logger log = LoggerFactory.getLogger(WeixinServiceImpl.class);

	@Resource(name = TextHandler.SERVICE_ID)
	private WxMpMessageHandler textHandler;

	@Resource(name = ImageHandler.SERVICE_ID)
	private WxMpMessageHandler imageHandler;

	@Resource(name = LinkHandler.SERVICE_ID)
	private WxMpMessageHandler linkHandler;

	@Resource(name = LocationHandler.SERVICE_ID)
	private WxMpMessageHandler locationHandler;

	@Resource(name = ShortvideoHandler.SERVICE_ID)
	private WxMpMessageHandler shortvideoHandler;

	@Resource(name = VideoHandler.SERVICE_ID)
	private WxMpMessageHandler videoHandler;

	@Resource(name = VoiceHandler.SERVICE_ID)
	private WxMpMessageHandler voiceHandler;

	@Resource(name = SubscribeHandler.SERVICE_ID)
	private WxMpMessageHandler subscribeHandler;

	@Resource(name = UnsubscribeHandler.SERVICE_ID)
	private WxMpMessageHandler unsubscribeHandler;

	@Resource(name = LocationEventHandler.SERVICE_ID)
	private WxMpMessageHandler locationEventHandler;

	@Resource(name = ScanHandler.SERVICE_ID)
	private WxMpMessageHandler scanHandler;

	@Resource(name = ClickHandler.SERVICE_ID)
	private WxMpMessageHandler clickHandler;

	@Resource(name = ViewHandler.SERVICE_ID)
	private WxMpMessageHandler viewHandler;

	@Resource(name = MassSendJobFinishHandler.SERVICE_ID)
	private WxMpMessageHandler massSendJobFinishHandler;

	@Resource(name = TemplateMsgSendJobFinishHandler.SERVICE_ID)
	private WxMpMessageHandler templateMsgSendJobFinishHandler;

	@Autowired
	private WxAccountService wxAccountService;

	@Autowired
	private WxMessageService WxMessageService;

	// 利用拦截器的上下文传递变量
	private WxMpMessageInterceptor context;
	private WxMpXmlMessage inMessage;

	@Autowired
	private WxMaterialImagesService wxMaterialImagesService;

	@Autowired
	private WxMpService wxMpService;

	private WxMpMessageRouter wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
	private WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
	private WxAccount wxAccount;
	private HttpServletRequest request;
	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;

	/**
	 * 处理微信消息
	 */
	@Override
	public String deal(HttpServletRequest request, String token) {		
		signature = request.getParameter("signature");
		timestamp = request.getParameter("timestamp");
		nonce = request.getParameter("nonce");
		echostr = request.getParameter("echostr");
		this.request = request;
		// 查找微信公众号
		wxAccount = wxAccountService.findWxAccountByToken(token);
		if (wxAccount == null) {
			return "please add your weixin account.";
		}
		config.setAppId(wxAccount.getAppid()); // 设置微信公众号的appid
		config.setSecret(wxAccount.getAppsecret()); // 设置微信公众号的app
		config.setToken(token); // 设置微信公众号的token
		config.setAesKey(wxAccount.getEncodingAESKey()); // 设置微信公众号的EncodingAESKey
		wxMpService.setWxMpConfigStorage(config);
		if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
			// 消息签名不正确，说明不是公众平台发过来的消息
			return "Check Signature failed.";
		}
		if (StringUtils.isNotBlank(echostr)) {
			// 说明是一个仅仅用来验证的请求，回显echostr
			return echostr;
		}
		// 获取加密类型
		String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ? "raw" : request.getParameter("encrypt_type");
		log.debug("encryptType={}", encryptType);
		String respStr = null;
		if ("raw".equals(encryptType)) {
			// 明文传输的消息
			respStr = dealRawType();
		} else if ("aes".equals(encryptType)) {
			// 是aes加密的消息
			respStr = dealAesType();
		} else {
			respStr = "error encryptType.";
		}
		log.debug("response str=\n{}", respStr);
		return respStr;
	}

	/**
	 * 处理明文消息
	 */
	private String dealRawType() {
		try {			
			inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
			// 将对应的微信传进来的信息持久化到数据库
			WxMessage wxMessage = new WxMessage();
			BeanUtils.copyProperties(wxMessage, inMessage);
			WxMessageService.save(wxMessage);

			log.info("inMessage={}", inMessage);
		} catch (IOException e) {
			log.error("IOException={}", e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error("IOException={}", e.getMessage());
			e.printStackTrace();
		}
		// 设置上下文中用到的变量
		setContext();
		// 处理消息路由配置
		buildRouter();
		// 组装返回的消息
		WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
		log.debug("outMessage=\n{}", outMessage);
		return outMessage.toXml();
	}

	/**
	 * 处理加密信息
	 */
	private String dealAesType() {		
		// 获取参数
		String msgSignature = request.getParameter("msg_signature");
		// 解密过程
		try {
			inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), config, timestamp, nonce, msgSignature);
		} catch (IOException e) {
			log.error("IOException={}", e.getMessage());
			e.printStackTrace();
		}
		// 设置上下文中用到的变量
		setContext();
		// 处理消息路由
		buildRouter();
		WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
		return outMessage.toEncryptedXml(config);
	}

	/**
	 * 处理消息路由配置
	 */
	private void buildRouter() {
		setDefaultAccount();
		// 1.文本消息进行处理
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_TEXT).interceptor(context).handler(textHandler).end();
		// 2.图片消息
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_IMAGE).interceptor(context).handler(imageHandler).end();
		// 3.语音消息
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_VOICE).interceptor(context).handler(voiceHandler).end();
		// 4.视频消息
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_VIDEO).interceptor(context).handler(videoHandler).end();
		// 5.小视频消息
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_SHORT_VIDEO).interceptor(context).handler(shortvideoHandler).end();
		// 6.地理位置消息
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_LOCATION).interceptor(context).handler(locationHandler).end();
		// 7.链接消息
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_LINK).interceptor(context).handler(linkHandler).end();
		// 8.关注消息
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE).interceptor(context).handler(subscribeHandler).end();
		// 9.取消关注事件
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_UNSUBSCRIBE).interceptor(context).handler(unsubscribeHandler).end();
		// 11.用户已关注时的事件推送
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SCAN).interceptor(context).handler(scanHandler).end();
		// 12.上报地理位置事件
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_LOCATION).interceptor(context).handler(locationEventHandler).end();
		// 13.自定义菜单事件
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_CLICK).interceptor(context).handler(clickHandler).end();
		// 14.点击菜单跳转链接时的事件推送
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_VIEW).interceptor(context).handler(viewHandler).end();
		// 15.群发消息结束事件
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_MASS_SEND_JOB_FINISH).interceptor(context).handler(massSendJobFinishHandler).end();
		// 16.模板消息发送结束通知事件
		wxMpMessageRouter.rule().msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_TEMPLATESENDJOBFINISH).interceptor(context).handler(templateMsgSendJobFinishHandler).end();

	}

	/**
	 * 设置上下文用到的变量
	 */
	public void setContext() {
		context = new WxMpMessageInterceptor() {
			@Override
			public boolean intercept(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
				context.put("wxAccount", wxAccount);
				return true;
			}
		};
	}

	/**
	 * 设置账号信息
	 */
	private void setDefaultAccount() {
		WxAccount wxAccount = wxAccountService.getDefaultWxAccount();
		config.setAppId(wxAccount.getAppid()); // 设置微信公众号的appid
		config.setSecret(wxAccount.getAppsecret()); // 设置微信公众号的app
		config.setToken(wxAccount.getToken()); // 设置微信公众号的token
		config.setAesKey(wxAccount.getEncodingAESKey()); // 设置微信公众号的EncodingAESKey
		wxMpService.setWxMpConfigStorage(config);
	}

	@Override
	public WeJson mass(HttpServletRequest request) {
		FileInputStream inputStream = null;
		try {
			setDefaultAccount();
			inputStream = new FileInputStream(new File("D:\\images\\1.jpg"));

			// 注意，2.0.0版本以上，请在wxMpService后面加上.getMaterialService()
			// 先上传图文消息里需要的图片
			WxMediaUploadResult uploadMediaRes = null;

			uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);

			WxMpMassNews news = new WxMpMassNews();
			WxMpMassNews.WxMpMassNewsArticle article1 = new WxMpMassNews.WxMpMassNewsArticle();
			article1.setTitle("标题1");
			article1.setContent("内容1");
			article1.setThumbMediaId(uploadMediaRes.getMediaId());
			news.addArticle(article1);

			WxMpMassNews.WxMpMassNewsArticle article2 = new WxMpMassNews.WxMpMassNewsArticle();
			article2.setTitle("标题2");
			article2.setContent("内容2");
			article2.setThumbMediaId(uploadMediaRes.getMediaId());
			article2.setShowCoverPic(true);
			article2.setAuthor("作者2");
			article2.setContentSourceUrl("www.baidu.com");
			article2.setDigest("摘要2");
			news.addArticle(article2);

			WxMpMassUploadResult massUploadResult = wxMpService.massNewsUpload(news);

			WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
			massMessage.setMsgType(WxConsts.MASS_MSG_NEWS);
			massMessage.setMediaId(massUploadResult.getMediaId());

			massMessage.getToUsers().add("o3v4BuEXevFBJhmpPAXnNsFBWFrw");
			massMessage.getToUsers().add("o3v4BuCTPFFAdzDF-rCi2kTtOEcc");

			WxMpMassSendResult massResult = wxMpService.massOpenIdsMessageSend(massMessage);

			return WeJson.success(massResult);

		} catch (Exception e) {
			log.error("{}", e);
			return WeJson.fail("处理失败");
		}
	}

	/**
	 * 获取图片文件
	 * 
	 * @throws Exception
	 */
	@Override
	public List<WxMaterialFileBatchGetNewsItem> getWxImages(int i, int j) throws Exception {
		setDefaultAccount();
		WxMpMaterialFileBatchGetResult wxMpMaterialFileBatchGetResult = wxMpService.materialFileBatchGet(WxConsts.MATERIAL_IMAGE, i, j);

		List<WxMaterialFileBatchGetNewsItem> items = wxMpMaterialFileBatchGetResult.getItems();
		for (WxMaterialFileBatchGetNewsItem item : items) {
			if (StringUtils.isNotBlank(item.getMediaId())) {
				// 单独缓存将文件缓存起来
				String str = CacheUtils.get("_WX_MATERIAL_IMAGE_", item.getMediaId());
				if (StringUtils.isNotBlank(str)) {
					item.setUrl(str);
				} else {
					InputStream is = wxMpService.materialImageOrVoiceDownload(item.getMediaId());
					str = QRCodeUtil.encode2ImgBase64(is);
					// 缓存base64编码
					CacheUtils.put("_WX_MATERIAL_IMAGE_", item.getMediaId(), str);
					item.setUrl(str);
				}
			}
		}
		return items;
	}

	public WxNewsArticle addUrl(WxNewsArticle wxNewsArticle) {
		setDefaultAccount();
		if (StringUtils.isNotBlank(wxNewsArticle.getThumbMediaId())) {
			// 单独y9ongyige缓存将文件缓存起来
			String str = CacheUtils.get("_WX_MATERIAL_IMAGE_", wxNewsArticle.getThumbMediaId());
			if (StringUtils.isNotBlank(str)) {
				wxNewsArticle.setUrl(str);
			} else {
				InputStream is;
				try {
					is = wxMpService.materialImageOrVoiceDownload(wxNewsArticle.getThumbMediaId());
					str = QRCodeUtil.encode2ImgBase64(is);
				} catch (Exception e) {
					log.error("{}", e);
				}
				// 缓存图片文件
				CacheUtils.put("_WX_MATERIAL_IMAGE_", wxNewsArticle.getThumbMediaId(), str);
				wxNewsArticle.setUrl(str);
			}
		}
		return wxNewsArticle;
	}

	@Override
	public WxMediaUploadResult wxImagesUpload(String filename) throws Exception {
		setDefaultAccount();
		FileInputStream inputStream = new FileInputStream(filename);

		WxMediaUploadResult uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
		// 将文件加到缓存中

		String str = QRCodeUtil.encode2ImgBase64(inputStream);
		String mediaId = uploadMediaRes.getMediaId();
		uploadMediaRes.setThumbMediaId(str);

		// 缓存base64编码
		CacheUtils.put("_WX_MATERIAL_IMAGE_", mediaId, str);
		return uploadMediaRes;
	}

	/**
	 * 获取新闻消息
	 * @throws WxErrorException 
	 */
	@Override
	public List<WxMaterialNewsBatchGetNewsItem> getNewsList(int i, int j) throws WxErrorException {
		// 设置默认账号
		setDefaultAccount();
		WxMpMaterialNewsBatchGetResult wxMpMaterialNewsBatchGetResult = wxMpService.materialNewsBatchGet(0, 20);
		List<WxMaterialNewsBatchGetNewsItem> newsList = wxMpMaterialNewsBatchGetResult.getItems();

		return newsList;
	}

	@Override
	public String getBase64StrByMediaId(String thumbMediaId) throws Exception {
		setDefaultAccount();
		String str = CacheUtils.get("_WX_MATERIAL_IMAGE_", thumbMediaId);
		if (StringUtils.isNotBlank(str)) {
			return str;
		} else {
			InputStream is = wxMpService.materialImageOrVoiceDownload(thumbMediaId);
			str = QRCodeUtil.encode2ImgBase64(is);
			CacheUtils.put("_WX_MATERIAL_IMAGE_", thumbMediaId, str);
			return str;
		}
	}

	@Override
	public List<WxMpMassSendResult> sendMassNews(WxMpMassNews news) throws WxErrorException {
		setDefaultAccount();
		WxMpMassUploadResult massUploadResult = wxMpService.massNewsUpload(news);
		List<WxMpMassSendResult> wxMpMassSendResultList = new ArrayList<WxMpMassSendResult>();
		// List<WxMpGroup> groupList = wxMpService.groupGet();
		// for (WxMpGroup group : groupList) {
		// WxMpMassGroupMessage gmsg = new WxMpMassGroupMessage();
		// gmsg.setGroupId(group.getId());
		// gmsg.setMsgtype(WxConsts.MASS_MSG_NEWS);
		// gmsg.setMediaId(massUploadResult.getMediaId());
		// WxMpMassSendResult massResult =
		// wxMpService.massGroupMessageSend(gmsg);
		// wxMpMassSendResultList.add(massResult);
		// }

		WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
		massMessage.setMsgType(WxConsts.MASS_MSG_NEWS);
		massMessage.setMediaId(massUploadResult.getMediaId());
		
		//小吴测试公众号 
//		massMessage.getToUsers().add("oNAfivnDmDeg1vMDmousiDgoWR1Y");
//		massMessage.getToUsers().add("oNAfivpn3C8bRVnQSlKvOYtJg2fA");
		
		massMessage.getToUsers().add("onSoKsw83AQbc9-keaxoxABoAqZA");
		massMessage.getToUsers().add("onSoKs-nZdMcrHZeUMPMU_AYi1Qs");
		massMessage.getToUsers().add("onSoKs-2oi5X13gThqBQQfyHiAiM");
		
		WxMpMassSendResult massResult = wxMpService.massOpenIdsMessageSend(massMessage);
		wxMpMassSendResultList.add(massResult);

		return wxMpMassSendResultList;
	}

	@Override
	public WxMediaUploadResult wxImagesUpload(InputStream inputStream, String filename) throws Exception {
		setDefaultAccount();

		// 通过后缀获取文件名类型
		if (com.haolinbang.common.utils.StringUtils.isBlank(filename) || !filename.contains(".")) {
			throw new RuntimeException("不支持的文件类型,请重新选择");
		}
		String type = filename.substring(filename.lastIndexOf(".") + 1);
		if (WxConsts.FILE_JPG.equalsIgnoreCase(type) || WxConsts.FILE_JPG2.equalsIgnoreCase(type)) {
			type = WxConsts.FILE_JPG;
		} else if (WxConsts.FILE_PNG.equalsIgnoreCase(type)) {
			type = WxConsts.FILE_PNG;
		} else {
			throw new RuntimeException("不支持的文件类型,请重新选择");
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		IOUtils.copy(inputStream, bos);

		InputStream is1 = new ByteArrayInputStream(bos.toByteArray());
		InputStream is2 = new ByteArrayInputStream(bos.toByteArray());

		WxMediaUploadResult uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, type, is1);

		Map<String, String> map = getLocalPath();
		// 下载文件到本地
		// InputStream is =
		// wxMpService.materialImageOrVoiceDownload(uploadMediaRes.getMediaId());
		FileUtils.saveFile(is2, map.get("amrPath"), map.get("imageName"));
		// 获取服务器访问地址
		String url = map.get("imgUrl");

		// WxAccount wxAccount = wxAccountService.getDefaultWxAccount();
		// // 将本地文件和远程文件做一个映射,保存起来对应关系
		// WxMaterialImages wxMaterialImages = new WxMaterialImages();
		// wxMaterialImages.setAccountId(wxAccount.getId());
		// wxMaterialImages.setLocalUrl(url);
		// wxMaterialImages.setRemoteUrl(uploadMediaRes.getMediaId());
		// wxMaterialImages.setType("1");
		// wxMaterialImagesService.save(wxMaterialImages);

		uploadMediaRes.setThumbMediaId(url);

		return uploadMediaRes;
	}

	/**
	 * 上传微信文件
	 * @throws WxErrorException 
	 */
	@Override
	public Map<String, String> wxNewsImagesUpload(InputStream inputStream, String filename) throws IOException, RuntimeException, WxErrorException {
		setDefaultAccount();

		if (com.haolinbang.common.utils.StringUtils.isBlank(filename) || !filename.contains(".")) {
			throw new RuntimeException("不支持的文件类型,请重新选择");
		}
		String type = filename.substring(filename.lastIndexOf(".") + 1);
		if (WxConsts.FILE_JPG.equalsIgnoreCase(type) || WxConsts.FILE_JPG2.equalsIgnoreCase(type)) {
			type = WxConsts.FILE_JPG;
		} else if (WxConsts.FILE_PNG.equalsIgnoreCase(type)) {
			type = WxConsts.FILE_PNG;
		} else {
			throw new RuntimeException("不支持的文件类型,请重新选择");
		}
		// 将图文消息进行上传
		WxMpMaterialUploadNewsImgResult result = wxMpService.uploadImg(type, inputStream);

		// 将图片下载到本地,用于本地预览显示

		String url = downloadPic2Local(result.getUrl());
		Map<String, String> map = new HashMap<String, String>();
		map.put("url", url);

		WxAccount wxAccount = wxAccountService.getDefaultWxAccount();
		// 将本地文件和远程文件做一个映射,保存起来对应关系
		WxMaterialImages wxMaterialImages = new WxMaterialImages();
		wxMaterialImages.setAccountId(wxAccount.getId());
		wxMaterialImages.setLocalUrl(url);
		wxMaterialImages.setRemoteUrl(result.getUrl());
		wxMaterialImages.setType("1");
		wxMaterialImagesService.save(wxMaterialImages);

		return map;
	}

	/**
	 * 下载文件到本地
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String downloadPic2Local(String url) throws ClientProtocolException, IOException {

		Map<String, String> map = getLocalPath();
		boolean b = HttpUtils.executeDownloadFile(url, map.get("amrPath"), map.get("imageName"));
		if (b) {
			// 实际的保存路径
			return map.get("imgUrl");
		}
		return null;
	}

	/**
	 * 组装文件保存路径和本地服务器的对外访问地址规则
	 * 
	 * @return
	 */
	private Map<String, String> getLocalPath() {
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		SimpleDateFormat time = new SimpleDateFormat("HHmmssSSSS");
		Date date = new Date();
		String slash = File.separator;
		String imageName = "newsImages" + time.format(date) + ((int) Math.random() * 100) + ".jpg";
		String urlPath = "newsImages" + slash + year.format(date) + slash + month.format(date) + slash + day.format(date) + slash;
		String amrPath = Global.getUserfilesBaseDir() + urlPath;

		String imgUrl = (Global.getConfig("server.project.images") + File.separator + urlPath).replaceAll("\\\\", "/") + imageName;

		Map<String, String> map = new HashMap<String, String>();
		map.put("imageName", imageName);
		map.put("urlPath", urlPath);
		map.put("amrPath", amrPath);
		map.put("imgUrl", imgUrl);

		return map;
	}

}
