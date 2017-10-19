package com.haolinbang.modules.sns.web;

import java.util.List;

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
import com.haolinbang.modules.sns.entity.WyComplain;
import com.haolinbang.modules.sns.service.WyComplainService;
import com.haolinbang.modules.sys.entity.AppUserScope;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 物业投诉表Controller
 * 
 * @author wxc
 * @version 2017-07-04
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyComplain")
public class WyComplainController extends BaseController {

	@Autowired
	private WyComplainService wyComplainService;

	@ModelAttribute
	public WyComplain get(@RequestParam(required = false) String id) {
		WyComplain entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyComplainService.get(id);
		}
		if (entity == null) {
			entity = new WyComplain();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyComplain:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyComplain wyComplain, HttpServletRequest request, HttpServletResponse response, Model model) {
		String wymc = request.getParameter("wymc");
		List<AppUserScope> list = UserUtils.getEmployeeOfWuye();
		if(list!=null && list.size()>0){
			if(StringUtils.isNotBlank(wymc)){
				String[] wymcs = wymc.split("_");
				wyComplain.setSource(wymcs[0]);
				wyComplain.setWyid(wymcs[1]);
			}else{
				wyComplain.setSource(list.get(0).getSource());
				String wyids = list.get(0).getGroupId();
				for(int i=1; i<list.size(); i++){
					wyids += ","+list.get(i).getGroupId();
				}
				wyComplain.setWyids(wyids);
			}
		}

		Page<WyComplain> page = wyComplainService.findPage(new Page<WyComplain>(request, response), wyComplain);
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		model.addAttribute("source", wyComplain.getSource());
		model.addAttribute("wyid", wyComplain.getWyid());
		return "modules/sns/wyComplainList";
	}

	@RequiresPermissions("sns:wyComplain:view")
	@RequestMapping(value = "form")
	public String form(WyComplain wyComplain, Model model) {
		model.addAttribute("wyComplain", wyComplain);
		return "modules/sns/wyComplainForm";
	}

	@RequiresPermissions("sns:wyComplain:edit")
	@RequestMapping(value = "save")
	public String save(WyComplain wyComplain, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyComplain)) {
			return form(wyComplain, model);
		}
		wyComplainService.save(wyComplain);
		addMessage(redirectAttributes, "保存物业投诉表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyComplain/?repage";
	}

	@RequiresPermissions("sns:wyComplain:edit")
	@RequestMapping(value = "delete")
	public String delete(WyComplain wyComplain, RedirectAttributes redirectAttributes) {
		wyComplain = wyComplainService.get(wyComplain);
		String content = wyComplain.getContent();
		if (StringUtils.isNotBlank(content) && content.length() > 10) {
			content = content.substring(0, 10) + "...";
		}
		wyComplainService.delete(wyComplain);

		addMessage(redirectAttributes, "删除【<font color='red'>" + content + "</font>】成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyComplain/?repage";
	}

}