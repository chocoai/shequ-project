package com.haolinbang.modules.sns.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WxMassMsg;
import com.haolinbang.modules.sns.service.RoomService;
import com.haolinbang.modules.sns.service.WxMassMsgService;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sns.util.RoomUtils;

@Controller
@RequestMapping("/wxMassMsg")
public class WxMassMsgController {
	
	@Autowired
	private WxMassMsgService wxMassMsgService;
	
	@Autowired
	private RoomService roomService;
	
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request){
		Member member = MemberUtils.getCurrentMember();
		Room room = RoomUtils.getCurrentRoom();
		if(room==null || room.equals("")){
			room = roomService.getRoom(member.getRoomId());
		}
		List<WxMassMsg> wmms = wxMassMsgService.getWxMassMsgList(room.getSource(), room.getGroupId().toString(), room.getWYID().toString(), room.getLYID().toString(), member.getMemberId().toString());
		
		model.addAttribute("wmms", wmms);
		
		return "modules/sns/wxMassMsg";
	}
	
	/*@RequestMapping("/index2")
	public String index2(Model model, HttpServletRequest request){
		Member member = MemberUtils.getCurrentMember();
		Room room = RoomUtils.getCurrentRoom();
		if(room==null || room.equals("")){
			room = roomService.getRoom(member.getRoomId());
		}
		List<WxMassMsg> wmms = wxMassMsgService.getWxMassMsgList2(room.getSource(), room.getGroupId().toString(), member.getMemberId().toString());
		
		model.addAttribute("wmms", wmms);
		
		return "modules/sns/wxMassMsg";
	}*/
}
