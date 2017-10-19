package com.haolinbang.modules.weixin.service;

import java.util.Iterator;
import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.common.utils.Encodes;
import com.haolinbang.modules.weixin.dao.WxMassMsgDao;
import com.haolinbang.modules.weixin.entity.WxMassMsg;
import com.haolinbang.modules.weixin.entity.WxNewsArticle;
import com.haolinbang.modules.weixin.service.inter.WeixinService;

/**
 * 群发消息记录表Service
 * 
 * @author nlp
 * @version 2017-07-11
 */
@Service
@Transactional(readOnly = true)
public class WxMassMsgService extends CrudService<WxMassMsgDao, WxMassMsg> {

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private WxNewsArticleService wxNewsArticleService;

	@Autowired
	private WxMaterialImagesService wxMaterialImagesService;

	@Autowired
	private WxMassMsgDao wxMassMsgDao;

	public WxMassMsg get(String id) {
		return super.get(id);
	}

	public List<WxMassMsg> findList(WxMassMsg wxMassMsg) {
		return super.findList(wxMassMsg);
	}

	public Page<WxMassMsg> findPage(Page<WxMassMsg> page, WxMassMsg wxMassMsg) {
		return super.findPage(page, wxMassMsg);
	}

	/**
	 * 发送群发消息,并且保存群发消息
	 */
	@Transactional(readOnly = false)
	public void save(WxMassMsg wxMassMsg) {
		wxMassMsg.setToUsers("all");
		super.save(wxMassMsg);
	}

	@Transactional(readOnly = false)
	public void save2(WxMassMsg wxMassMsg) {
		super.save(wxMassMsg);
	}

	@Transactional(readOnly = false)
	public void delete(WxMassMsg wxMassMsg) {
		super.delete(wxMassMsg);
	}

	public WxMassMsg getWxMassMsgByMsgid(Long msgId) {
		return wxMassMsgDao.getWxMassMsgByMsgid(msgId);
	}

	/**
	 * 发送微信消息
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public WeJson sendNewsMsg(String id) {
		WxMassMsg wxMassMsg = wxMassMsgDao.get(id);
		String newsId = wxMassMsg.getNewsArticleId();
		WxMpMassNews news = new WxMpMassNews();
		if (StringUtils.isNotBlank(newsId)) {
			String[] arr = newsId.split(",");
			for (String newid : arr) {

				WxNewsArticle wxNewsArticle = wxNewsArticleService.get(newid);
				WxMpMassNews.WxMpMassNewsArticle article2 = new WxMpMassNews.WxMpMassNewsArticle();
				article2.setTitle(wxNewsArticle.getTitle());
				article2.setThumbMediaId(wxNewsArticle.getThumbMediaId());
				article2.setShowCoverPic(wxNewsArticle.getShowCoverPic());
				article2.setAuthor(wxNewsArticle.getAuthor());
				article2.setContentSourceUrl(wxNewsArticle.getContentSourceUrl());
				article2.setDigest(wxNewsArticle.getDigest());

				// 替换文本中的图片文件地址
				String content = Encodes.unescapeHtml(wxNewsArticle.getContent());
				// 解析dom节点,替换节点中的src地址
				Document doc = Jsoup.parseBodyFragment(content);
				Element body = doc.body();
				Elements elements = body.getElementsByTag("img");

				// 获取迭代器
				Iterator<Element> it = elements.iterator();
				while (it.hasNext()) {
					Element element = (Element) it.next();
					String localUrl = element.attr("src");
					if (StringUtils.isNotBlank(localUrl)) {
						// 查找实际的腾讯地址
						String url = wxMaterialImagesService.getWxImgUrlByLocalUrl(localUrl);
						if (StringUtils.isNotBlank(url)) {
							// 替换节点中的链接地址
							element.attr("src", url);
						}
					}
				}
				article2.setContent(doc.html());
				news.addArticle(article2);
			}
		}

		try {
			List<WxMpMassSendResult> massResults = weixinService.sendMassNews(news, wxMassMsg.getAccountId());
			String ids = "";
			for (WxMpMassSendResult result : massResults) {
				if (StringUtils.isNotBlank(result.getMsgId())) {
					ids += "," + result.getMsgId();
				}
			}
			if (StringUtils.isNotBlank(ids)) {
				wxMassMsg.setMsgId(ids);
				super.save(wxMassMsg);
			}

			// 发送完成后，更新发送信息

		} catch (WxErrorException e) {
			logger.error("发送出现错误：{}", e);
		}

		return null;
	}
}