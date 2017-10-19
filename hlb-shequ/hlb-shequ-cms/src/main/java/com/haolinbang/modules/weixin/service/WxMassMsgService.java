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

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.common.utils.Encodes;
import com.haolinbang.modules.weixin.dao.WxMassMsgDao;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.entity.WxMassMsg;
import com.haolinbang.modules.weixin.entity.WxNewsArticle;
import com.haolinbang.modules.weixin.service.inter.WeixinService;
import com.haolinbang.modules.weixin.service.inter.WxAccountService;

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
	private WxAccountService wxAccountService;

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
		try {
			// 进行群发内容的控制
			WxMpMassNews news = new WxMpMassNews();
			String[] idArr = wxMassMsg.getNewsId().split(",");
			for (String id : idArr) {
				WxNewsArticle wxNewsArticle = wxNewsArticleService.get(id);
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

			List<WxMpMassSendResult> massResult = weixinService.sendMassNews(news);
			if (massResult != null && !massResult.isEmpty()) {
				wxMassMsg.setMsgId(massResult.get(0).getMsgId());
			}

			wxMassMsg.setNewsArticleId(wxMassMsg.getNewsId());
			// 设置公众号账号id
			WxAccount wxAccount = wxAccountService.getDefaultWxAccount();
			wxMassMsg.setAccount_id(wxAccount.getId());

			wxMassMsg.setToUsers("all");
			super.save(wxMassMsg);
		} catch (WxErrorException e) {
			logger.error("发送图文消息出错,{}", e);
		}
	}

	@Transactional(readOnly = false)
	public void delete(WxMassMsg wxMassMsg) {
		super.delete(wxMassMsg);
	}

	public WxMassMsg getWxMassMsgByMsgid(Long msgId) {
		return wxMassMsgDao.getWxMassMsgByMsgid(msgId);
	}

}