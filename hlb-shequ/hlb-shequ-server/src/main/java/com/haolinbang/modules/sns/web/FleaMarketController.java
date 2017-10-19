package com.haolinbang.modules.sns.web;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.utils.DateUtils;
import com.haolinbang.common.utils.Exceptions;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyComment;
import com.haolinbang.modules.sns.entity.WyDianzanRecord;
import com.haolinbang.modules.sns.entity.WyFleaMarket;
import com.haolinbang.modules.sns.service.MemberService;
import com.haolinbang.modules.sns.service.RoomService;
import com.haolinbang.modules.sns.service.WyCommentService;
import com.haolinbang.modules.sns.service.WyDianzanRecordService;
import com.haolinbang.modules.sns.service.WyFleaMarketService;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sns.util.RoomUtils;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.WxAccountService;

/**
 * 跳蚤市场
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/fleaMarket")
public class FleaMarketController extends BaseController {

	@Autowired
	private WyFleaMarketService wyFleaMarketService;
	
	@Autowired
	private WyDianzanRecordService wyDianzanRecordService;
	
	@Autowired
	private WyCommentService wyCommentService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private WxAccountService wxAccountService;

	/**
	 * 发布
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(WyFleaMarket wyFleaMarket, HttpServletRequest request, Model model) {
		try {
			Member member = null;
			String dirType = "";
			String isexist = (String) request.getSession().getAttribute("isexist");
			
			if(StringUtils.isNotBlank(isexist) && isexist.equals("false")){
				dirType = request.getParameter("dirType");
			}
			
			if(StringUtils.isBlank(dirType)){
				member = MemberUtils.getCurrentMember();
			}else if(!dirType.equals("fleaMarket")){
				member = MemberUtils.getCurrentMember();
			}else{
				model.addAttribute("ignore", dirType);
			}
			
			String appid = (String) request.getSession().getAttribute("app_appid");
			String source = (String) request.getSession().getAttribute("app_source");
			Integer groupid = StringUtils.toInteger(request.getSession().getAttribute("app_groupid"));
			if(StringUtils.isBlank(appid)){
				WxAccount wxAccount = new WxAccount();
				wxAccount.setSource(source);
				wxAccount.setGroupId(groupid);
				appid = wxAccountService.getByWxAccount(wxAccount).get(0).getAppid();
				request.getSession().setAttribute("app_appid", appid);
			}
			
			wyFleaMarket.setAppid(appid);
			/*wyFleaMarket.setSource(source);
			wyFleaMarket.setGroupId(groupid);*/
			
			List<WyFleaMarket> wyFleaMarketList = wyFleaMarketService.findList(wyFleaMarket);
			for (WyFleaMarket fleaMarket : wyFleaMarketList) {
				String imgs = fleaMarket.getImgs();
				if (StringUtils.isNotBlank(imgs)) {
					String[] imgArr = imgs.split(",");
					if (imgArr != null && imgArr.length > 0) {
						fleaMarket.setImgList(Arrays.asList(imgArr));
					}
				}
				if(member != null){
					WyDianzanRecord wyDianzanRecord = new WyDianzanRecord();
					wyDianzanRecord.setRelationId(StringUtils.toInteger(fleaMarket.getId()));
					wyDianzanRecord.setBizType("flea_market");
					wyDianzanRecord.setDianzanType(WyConstants.DIANZHAN_THEME);
					wyDianzanRecord.setMemberId(member.getMemberId());
					wyDianzanRecord.setState(true);
					
					// 查询是否已经点赞
					WyDianzanRecord wyDianzanRecord2 = wyDianzanRecordService.getWyDianzanRecordByRelationId(wyDianzanRecord);
					if (wyDianzanRecord2 != null) {
						fleaMarket.setDianzhanStatus(1);
					}else{
						fleaMarket.setDianzhanStatus(2);
					}
				}else{
					fleaMarket.setDianzhanStatus(2);
				}
				//查询评论数
				List<WyComment> wyComments = wyCommentService.getAllByTypeAndId("flea_market", fleaMarket.getId());
				fleaMarket.setCommentNum(wyComments==null?0:wyComments.size());
			}
			

			model.addAttribute("wyFleaMarketList", wyFleaMarketList);
			model.addAttribute("wyFleaMarket", wyFleaMarket);

			return "modules/sns/fleaMarket/fleaMarketBrowse";
		} catch (Exception e) {
			logger.error("跳蚤市场--发布出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 发布
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/publish")
	public String publish(WyFleaMarket wyFleaMarket, HttpServletRequest request, Model model) {
		try {
			Member member = MemberUtils.getCurrentMember();
			model.addAttribute("member", member);
			model.addAttribute("wyFleaMarket", wyFleaMarket);

			Room room = RoomUtils.getCurrentRoom();
			model.addAttribute("room", room);

			return "modules/sns/fleaMarket/publish";
		} catch (Exception e) {
			logger.error("跳蚤市场--发布出错:{}", e);
			return "redirect:/index";
		}
	}

	/**
	 * 发布保存
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/publishSave")
	public WeJson publishSave(WyFleaMarket wyFleaMarket, HttpServletRequest request, Model model) {
		try {
			Member member = MemberUtils.getCurrentMember();
			wyFleaMarket.setMemberId(member.getMemberId());
			
			Room room = roomService.getRoom(member.getRoomId());
			if(room != null){
				wyFleaMarket.setAudit(1);
				wyFleaMarket.setSource(room.getSource());
				wyFleaMarket.setGroupId(room.getGroupId());
				wyFleaMarket.setWyid(room.getWYID().toString());
			}
			
			String appid = (String) request.getSession().getAttribute("app_appid");
			wyFleaMarket.setAppid(appid);
			wyFleaMarketService.save(wyFleaMarket);

			return WeJson.success("保存成功");
		} catch (Exception e) {
			logger.error("跳蚤市场--发布出错:{}", e);
			return WeJson.fail("跳蚤市场保存出错,请稍后重试");
		}
	}

	/**
	 * 点赞
	 * 
	 * @param wyDianzanRecord
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/dianzan")
	public WeJson dianzan(WyDianzanRecord wyDianzanRecord) {
		try {
			return wyFleaMarketService.dianzan(wyDianzanRecord);
		} catch (Exception e) {
			logger.error("点赞出错:{}", e);
			return WeJson.fail("点赞出错");
		}
	}

	/**
	 * 发布
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request, String id, Model model) {
		try {
			String dirType = request.getParameter("dirType");
			Member member = null;
			Room room = null;
			if(StringUtils.isBlank(dirType)){
				member = MemberUtils.getCurrentMember();
				RoomUtils.getCurrentRoom();
			}else if(!dirType.equals("fleaMarket")){
				member = MemberUtils.getCurrentMember();
				RoomUtils.getCurrentRoom(); 
			}else{
				model.addAttribute("ignore", dirType);
			}

			model.addAttribute("room", room);
			model.addAttribute("member", member);

			WyFleaMarket wyFleaMarket = wyFleaMarketService.get(id);

			if (wyFleaMarket != null) {
				String imgs = wyFleaMarket.getImgs();
				if (StringUtils.isNotBlank(imgs)) {
					String[] imgArr = imgs.split(",");
					if (imgArr != null && imgArr.length > 0) {
						wyFleaMarket.setImgList(Arrays.asList(imgArr));
					}
				}
			}
			
			//根据业务类型和对应的业务表id获取该发布内容的发表评论,不是回复
			List<WyComment> comments = wyCommentService.getByTypeAndId("flea_market", wyFleaMarket.getId());
			if(comments!=null && comments.size()>0){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(WyComment wc : comments){
					Member publisher = memberService.getMember(wc.getPublisher());
					wc.setPublisherName(publisher.getMemberName());
					wc.setPublisherAvatar(publisher.getAvatarurl());
					wc.setCreateTime(DateUtils.formatDateTime(wc.getCreateDate()));
					List<WyComment> comments1 = wyCommentService.getByCommentId(wc.getId());
					if(comments1!=null && comments1.size()>0){
						for(WyComment wc1 : comments1){
							publisher = memberService.getMember(wc1.getPublisher());
							Member publisherRelation = memberService.getMember(StringUtils.toInteger(wc1.getPublisherRelation()));
							wc1.setPublisherName(publisher.getMemberName());
							wc1.setPublisherAvatar(publisher.getAvatarurl());
							wc1.setPublisherRelationName(publisherRelation.getMemberName());
							wc1.setCreateTime(DateUtils.formatDateTime(wc1.getCreateDate()));
						}
					}
					wc.setComments(comments1);
				}
			}

			model.addAttribute("fleaMarket", wyFleaMarket);
			model.addAttribute("comments", comments);
			return "modules/sns/fleaMarket/detail";
		} catch (Exception e) {
			logger.error("房屋租售--发布出错:{}", e);
			return Exceptions.deal(e);
		}
	}

	/**
	 * 发布
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/myFleaMarketList")
	public String myFleaMarketList(WyFleaMarket wyFleaMarket, HttpServletRequest request, Model model) {
		try {
			//查询当前用户已发布的信息
			Member member = MemberUtils.getCurrentMember();
			wyFleaMarket.setMemberId(member.getMemberId());	
			
			String appid = (String) request.getSession().getAttribute("app_appid");
			if(StringUtils.isBlank(appid)){
				WxAccount wxAccount = new WxAccount();
				wxAccount.setSource((String) request.getSession().getAttribute("app_source"));
				wxAccount.setGroupId(StringUtils.toInteger(request.getSession().getAttribute("app_groupid")));
				appid = wxAccountService.getByWxAccount(wxAccount).get(0).getAppid();
				request.getSession().setAttribute("app_appid", appid);
			}
			wyFleaMarket.setAppid(appid);

			List<WyFleaMarket> wyFleaMarketList = wyFleaMarketService.findList(wyFleaMarket);
			for (WyFleaMarket fleaMarket : wyFleaMarketList) {
				String imgs = fleaMarket.getImgs();
				if (StringUtils.isNotBlank(imgs)) {
					String[] imgArr = imgs.split(",");
					if (imgArr != null && imgArr.length > 0) {
						fleaMarket.setImgList(Arrays.asList(imgArr));
					}
				}
				WyDianzanRecord wyDianzanRecord = new WyDianzanRecord();
				wyDianzanRecord.setRelationId(StringUtils.toInteger(fleaMarket.getId()));
				wyDianzanRecord.setBizType("house_rent");
				wyDianzanRecord.setDianzanType(WyConstants.DIANZHAN_THEME);
				wyDianzanRecord.setMemberId(member.getMemberId());
				wyDianzanRecord.setState(true);
				
				// 查询是否已经点赞
				WyDianzanRecord wyDianzanRecord2 = wyDianzanRecordService.getWyDianzanRecordByRelationId(wyDianzanRecord);
				if (wyDianzanRecord2 != null) {
					fleaMarket.setDianzhanStatus(1);
				}else{
					fleaMarket.setDianzhanStatus(2);
				}
				//查询评论数
				List<WyComment> wyComments = wyCommentService.getAllByTypeAndId("flea_market", fleaMarket.getId());
				fleaMarket.setCommentNum(wyComments==null?0:wyComments.size());
			}

			model.addAttribute("wyFleaMarketList", wyFleaMarketList);
			model.addAttribute("wyFleaMarket", wyFleaMarket);

			return "modules/sns/fleaMarket/myFleaMarketList";
		} catch (Exception e) {
			logger.error("跳蚤市场--发布出错:{}", e);
			return Exceptions.deal(e);
		}
	}

}
