package com.haolinbang.modules.sns.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.service.RoomService;

/**
 * 房屋信息Controller
 * @author wxc
 * @version 2017-06-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/room")
public class RoomController extends BaseController {

	@Autowired
	private RoomService roomService;
	
	@ModelAttribute
	public Room get(@RequestParam(required=false) String roomid) {
		Room entity = null;
		if (StringUtils.isNotBlank(roomid)){
			entity = roomService.get(roomid);
		}
		if (entity == null){
			entity = new Room();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:room:view")
	@RequestMapping(value = {"list", ""})
	public String list(Room room, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Room> page = roomService.findPage(new Page<Room>(request, response), room); 
		model.addAttribute("page", page);
		return "modules/sns/roomList";
	}

	@RequiresPermissions("sns:room:view")
	@RequestMapping(value = "form")
	public String form(Room room, Model model, String type) {
		model.addAttribute("room", room);
		model.addAttribute("type", type);
		return "modules/sns/roomForm";
	}

	@RequiresPermissions("sns:room:edit")
	@RequestMapping(value = "save")
	public String save(Room room, Model model, RedirectAttributes redirectAttributes, String type) {
		if (!beanValidator(model, room)){
			return form(room, model, type);
		}
		if(type!=null && type.equals("edit")){
			room.setId(room.getRoomid().toString());
		}
		roomService.save(room);
		addMessage(redirectAttributes, "保存房屋信息成功");
		return "redirect:"+Global.getAdminPath()+"/sns/room/?repage";
	}
	
	@RequiresPermissions("sns:room:edit")
	@RequestMapping(value = "delete")
	public String delete(Room room, RedirectAttributes redirectAttributes) {
		roomService.delete(room);
		addMessage(redirectAttributes, "删除房屋信息成功");
		return "redirect:"+Global.getAdminPath()+"/sns/room/?repage";
	}

}