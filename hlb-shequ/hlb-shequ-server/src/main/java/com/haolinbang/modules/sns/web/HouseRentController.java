package com.haolinbang.modules.sns.web;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.utils.CacheUtils;
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
import com.haolinbang.modules.sns.entity.WyHouseRent;
import com.haolinbang.modules.sns.service.MemberService;
import com.haolinbang.modules.sns.service.RoomService;
import com.haolinbang.modules.sns.service.WyCommentService;
import com.haolinbang.modules.sns.service.WyDianzanRecordService;
import com.haolinbang.modules.sns.service.WyFleaMarketService;
import com.haolinbang.modules.sns.service.WyHouseRentService;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sns.util.RoomUtils;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.WxAccountService;

/**
 * 房屋租售
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/houseRent")
public class HouseRentController extends BaseController {

	@Autowired
	private WyHouseRentService wyHouseRentService;
	
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
	@RequestMapping("/publish")
	public String publish(WyHouseRent wyHouseRent, HttpServletRequest request, Model model) {
		try {
			Member member = MemberUtils.getCurrentMember();
			Room room = RoomUtils.getCurrentRoom();

			model.addAttribute("room", room);
			model.addAttribute("member", member);

			model.addAttribute("wyHouseRent", wyHouseRent);
			return "modules/sns/houseRent/houseRent";
		} catch (Exception e) {
			logger.error("房屋租售--发布出错:{}", e);
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
	public WeJson publishSave(WyHouseRent wyHouseRent, HttpServletRequest request, Model model) {
		try {
			// 添加校验
			Member member = MemberUtils.getCurrentMember();
			wyHouseRent.setMemberId(member.getMemberId());
			
			Room room = roomService.getRoom(member.getRoomId());
			if(room != null){
				wyHouseRent.setAudit(1);
				wyHouseRent.setSource(room.getSource());
				wyHouseRent.setGroupId(room.getGroupId());
				wyHouseRent.setWyid(room.getWYID().toString());
			}
			
			String appid = (String) request.getSession().getAttribute("app_appid");
			wyHouseRent.setAppid(appid);
			wyHouseRentService.save(wyHouseRent);

			return WeJson.success("保存成功");
		} catch (Exception e) {
			logger.error("房屋租售--发布出错:{}", e);
			return WeJson.fail("房屋租售发布保存出错,请稍后重试");
		}
	}

	/**
	 * 查看已发布,已审核的房屋信息列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(WyHouseRent wyHouseRent, HttpServletRequest request, Model model) {
		try {
			Member member = null;
			String dirType = "";
			String isexist = (String) request.getSession().getAttribute("isexist");
			if(StringUtils.isNotBlank(isexist) && isexist.equals("false")){
				dirType = request.getParameter("dirType");
			}
			
			if(StringUtils.isBlank(dirType)){
				member = MemberUtils.getCurrentMember();
			}else if(!dirType.equals("houseRent")){
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
				if(member == null){
					request.getSession().setAttribute("app_source", source);
					request.getSession().setAttribute("app_groupid", groupid);
				}
			}
			
			wyHouseRent.setAppid(appid);
			if(StringUtils.isBlank(wyHouseRent.getRentalType())){
				wyHouseRent.setRentalType("0");
			}

			List<WyHouseRent> wyHouseRentList = wyHouseRentService.getWyHouseRentList(wyHouseRent);
			for (WyHouseRent houseRent : wyHouseRentList) {
				String imgs = houseRent.getImgs();
				if (StringUtils.isNotBlank(imgs)) {
					String[] imgArr = imgs.split(",");
					if (imgArr != null && imgArr.length > 0) {
						houseRent.setImgList(Arrays.asList(imgArr));
					}
				}
				if(member != null){
					WyDianzanRecord wyDianzanRecord = new WyDianzanRecord();
					wyDianzanRecord.setRelationId(StringUtils.toInteger(houseRent.getId()));
					wyDianzanRecord.setBizType("house_rent");
					wyDianzanRecord.setDianzanType(WyConstants.DIANZHAN_THEME);
					wyDianzanRecord.setMemberId(member.getMemberId());
					wyDianzanRecord.setState(true);

					// 查询是否已经点赞
					WyDianzanRecord wyDianzanRecord2 = wyDianzanRecordService.getWyDianzanRecordByRelationId(wyDianzanRecord);
					if (wyDianzanRecord2 != null) {
						houseRent.setDianzhanStatus(1);
					}else{
						houseRent.setDianzhanStatus(2);
					}
				}else{
					houseRent.setDianzhanStatus(2);
				}
				//查询评论数
				List<WyComment> wyComments = wyCommentService.getAllByTypeAndId("house_rent", houseRent.getId());
				houseRent.setCommentNum(wyComments==null?0:wyComments.size());
			}

			model.addAttribute("wyHouseRentList", wyHouseRentList);
			model.addAttribute("wyHouseRent", wyHouseRent);
			if((request.getParameter("test")+"").equals("1")){
				return "modules/sns/houseRent/houseBrowse1";
			}
			return "modules/sns/houseRent/houseBrowse";
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
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request, String id, Model model) {
		try {
			String dirType = request.getParameter("dirType");
			Member member = null;
			Room room = null;
			if(StringUtils.isBlank(dirType)){
				member = MemberUtils.getCurrentMember();
				RoomUtils.getCurrentRoom();
			}else if(!dirType.equals("houseRent")){
				member = MemberUtils.getCurrentMember();
				RoomUtils.getCurrentRoom(); 
			}else{
				model.addAttribute("ignore", dirType);
			}

			model.addAttribute("room", room);
			model.addAttribute("member", member);

			WyHouseRent wyHouseRent = wyHouseRentService.get(id);

			if (wyHouseRent != null) {
				String imgs = wyHouseRent.getImgs();
				if (StringUtils.isNotBlank(imgs)) {
					String[] imgArr = imgs.split(",");
					if (imgArr != null && imgArr.length > 0) {
						wyHouseRent.setImgList(Arrays.asList(imgArr));
					}
				}
			}
			
			//根据业务类型和对应的业务表id获取该发布内容的发表评论,不是回复
			List<WyComment> comments = wyCommentService.getByTypeAndId("house_rent", wyHouseRent.getId());
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
			
			model.addAttribute("houseRent", wyHouseRent);
			model.addAttribute("comments", comments);
			return "modules/sns/houseRent/houseDetail";
		} catch (Exception e) {
			logger.error("房屋租售--发布出错:{}", e);
			return Exceptions.deal(e);
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
			return wyHouseRentService.dianzan(wyDianzanRecord);
		} catch (Exception e) {
			logger.error("点赞出错:{}", e);
			return WeJson.fail("点赞出错");
		}
	}

	/**
	 * 我的租售信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/myList")
	public String myHouseRentList(WyHouseRent wyHouseRent, HttpServletRequest request, Model model) {
		try {
			// 查询当前用户已发布的信息
			Member member = MemberUtils.getCurrentMember();
			wyHouseRent.setMemberId(member.getMemberId());
			
			String appid = (String) request.getSession().getAttribute("app_appid");
			if(StringUtils.isBlank(appid)){
				WxAccount wxAccount = new WxAccount();
				wxAccount.setSource((String) request.getSession().getAttribute("app_source"));
				wxAccount.setGroupId(StringUtils.toInteger(request.getSession().getAttribute("app_groupid")));
				appid = wxAccountService.getByWxAccount(wxAccount).get(0).getAppid();
				request.getSession().setAttribute("app_appid", appid);
			}
			
			wyHouseRent.setAppid(appid);

			List<WyHouseRent> wyHouseRentList = wyHouseRentService.getWyHouseRentList(wyHouseRent);
			for (WyHouseRent houseRent : wyHouseRentList) {
				String imgs = houseRent.getImgs();
				if (StringUtils.isNotBlank(imgs)) {
					String[] imgArr = imgs.split(",");
					if (imgArr != null && imgArr.length > 0) {
						houseRent.setImgList(Arrays.asList(imgArr));
					}
				}
				WyDianzanRecord wyDianzanRecord = new WyDianzanRecord();
				wyDianzanRecord.setRelationId(StringUtils.toInteger(houseRent.getId()));
				wyDianzanRecord.setBizType("house_rent");
				wyDianzanRecord.setDianzanType(WyConstants.DIANZHAN_THEME);
				wyDianzanRecord.setMemberId(member.getMemberId());
				wyDianzanRecord.setState(true);

				// 查询是否已经点赞
				WyDianzanRecord wyDianzanRecord2 = wyDianzanRecordService.getWyDianzanRecordByRelationId(wyDianzanRecord);
				if (wyDianzanRecord2 != null) {
					houseRent.setDianzhanStatus(1);
				}else{
					houseRent.setDianzhanStatus(2);
				}
				//查询评论数
				List<WyComment> wyComments = wyCommentService.getAllByTypeAndId("house_rent", houseRent.getId());
				houseRent.setCommentNum(wyComments==null?0:wyComments.size());
			}

			model.addAttribute("wyHouseRentList", wyHouseRentList);
			model.addAttribute("wyHouseRent", wyHouseRent);
			return "modules/sns/houseRent/myHouseRentList";
		} catch (Exception e) {
			logger.error("房屋租售--发布出错:{}", e);
			return Exceptions.deal(e);
		}
	}
	
	/*
	 * 评论提交
	 */
	@ResponseBody
	@RequestMapping("/submitComment")
	public WeJson submitComment(WyComment wyComment, HttpServletRequest request, Model model){
		try {
			Member member = MemberUtils.getCurrentMember();
			wyComment.setPublisher(member.getMemberId());
			wyComment.setBeReply(wyComment.getPublisherRelation().equals("")?false:true);
			wyComment.setAudit(true);
			
			wyCommentService.save(wyComment);
			
			if(wyComment.getBizType().equals("house_rent")){
				WyHouseRent wyHouseRent = wyHouseRentService.get(wyComment.getRelationId().toString());
				if(wyHouseRent != null){
					wyHouseRent.setCommentNum(wyHouseRent.getCommentNum()+1);
					wyHouseRentService.updates(wyHouseRent);
				}
			}
			
			if(wyComment.getBizType().equals("flea_market")){
				WyFleaMarket wyFleaMarket = wyFleaMarketService.get(wyComment.getRelationId().toString());
				if(wyFleaMarket != null){
					wyFleaMarket.setCommentNum(wyFleaMarket.getCommentNum()+1);
					wyFleaMarketService.updates(wyFleaMarket);
				}
			}
			
			Map map = new HashMap();
			String ss, ss1, ss2, ss3, ss4, ss5, ss6, ss7;
			if(wyComment.getBeReply()){
				map.put("type", 2);
				ss = "<div class='message2' onclick='fillname(this)' id='"+wyComment.getId()+"' name='"+wyComment.getPublisher()+"'>";
				ss3 = "<div class='username color'>"+member.getMemberName()+"</div>";
				Member publisherRelation = memberService.getMember(StringUtils.toInteger(wyComment.getPublisherRelation()));
				ss5 = "<div class='content'><span>回复@"+publisherRelation.getMemberName()+": </span>"+wyComment.getComment()+"</div>";
				ss7 = "</div>";
			}else{
				map.put("type", 1);
				ss = "<div class='message'><div class='message1' onclick='fillname(this)' id='"+wyComment.getId()+"' name='"+wyComment.getPublisher()+"'>";
				ss3 = "<div class='username'>"+member.getMemberName()+"</div>";
				ss5 = "<div class='content'>"+wyComment.getComment()+"</div>";
				ss7 = "</div><div class='type2comment'></div></div>";
			}
			ss1 = "<div class='message11'>";
			ss2 = "<div class='avatar1'><img src='"+member.getAvatarurl()+"' class='per1'></div>";
			ss4 = "</div>";
			ss6 = "<div class='time'>"+DateUtils.formatDateTime(wyComment.getCreateDate())+"</div>";
			
			map.put("data", ss+ss1+ss2+ss3+ss4+ss5+ss6+ss7);
			
			return WeJson.success(map);
		} catch (Exception e) {
			logger.error("房屋租售--评论提交:{}", e);
			return WeJson.fail("");
		}
	}
	
	/*
	 * 外部直接访问房屋租售方法
	 */
	@RequestMapping("/dirHouseRent")
	public String DirHouseRent(WyHouseRent wyHouseRent, HttpServletRequest request, Model model){
		
		return null;
	}
}
