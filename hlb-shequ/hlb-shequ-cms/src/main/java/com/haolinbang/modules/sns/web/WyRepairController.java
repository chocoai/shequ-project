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
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyRepair;
import com.haolinbang.modules.sns.service.RoomService;
import com.haolinbang.modules.sns.service.WyRepairService;

/**
 * 物业报修Controller
 * 
 * @author nlp
 * @version 2017-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyRepair")
public class WyRepairController extends BaseController {

	@Autowired
	private WyRepairService wyRepairService;
	
	@Autowired
	private RoomService roomService;
	
	@ModelAttribute
	public WyRepair get(@RequestParam(required = false) String id) {
		WyRepair entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyRepairService.get(id);			
			wyRepairService.buildAct(entity);			
		}
		if (entity == null) {
			entity = new WyRepair();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyRepair:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyRepair wyRepair, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyRepair> page = wyRepairService.findPage(new Page<WyRepair>(request, response), wyRepair);
		for(WyRepair wr : page.getList()){
			Room room = roomService.get(wr.getRoomId().toString());
			if(room != null){
				wr.setRoom(room.getWyname()+room.getLyname()+room.getRoomno());
			}
		}
		model.addAttribute("page", page);
		return "modules/sns/wyRepairList";
	}

	@RequiresPermissions("sns:wyRepair:view")
	@RequestMapping(value = "form")
	public String form(WyRepair wyRepair, Model model) {
		model.addAttribute("wyRepair", wyRepair);
		return "modules/sns/wyRepairForm";
	}

	@RequiresPermissions("sns:wyRepair:edit")
	@RequestMapping(value = "save")
	public String save(WyRepair wyRepair, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyRepair)) {
			return form(wyRepair, model);
		}
		wyRepairService.save(wyRepair);
		addMessage(redirectAttributes, "保存物业报修成功");
		// return "redirect:"+Global.getAdminPath()+"/sns/wyRepair/?repage";
		return "redirect:" + adminPath + "/act/task/todo/";
	}

	@RequiresPermissions("sns:wyRepair:edit")
	@RequestMapping(value = "delete")
	public String delete(WyRepair wyRepair, RedirectAttributes redirectAttributes) {
		wyRepairService.delete(wyRepair);
		addMessage(redirectAttributes, "删除物业报修成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyRepair/?repage";
	}

}