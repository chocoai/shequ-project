package com.haolinbang.modules.sns.web;

import java.util.Arrays;
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
import com.haolinbang.modules.sns.entity.WyFleaMarket;
import com.haolinbang.modules.sns.entity.WyHouseRent;
import com.haolinbang.modules.sns.service.WyHouseRentService;
import com.haolinbang.modules.sys.entity.AppUserScope;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 房屋租售Controller
 * 
 * @author nlp
 * @version 2017-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyHouseRent")
public class WyHouseRentController extends BaseController {

	@Autowired
	private WyHouseRentService wyHouseRentService;

	@ModelAttribute
	public WyHouseRent get(@RequestParam(required = false) String id) {
		WyHouseRent entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyHouseRentService.get(id);
		}
		if (entity == null) {
			entity = new WyHouseRent();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyHouseRent:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyHouseRent wyHouseRent, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*User user = UserUtils.getUser();
		if(user!=null){
			wyHouseRent.setSource(user.getSource());
			wyHouseRent.setGroupId(user.getParentGroupId());
		}*/
		String wymc = request.getParameter("wymc");
		List<AppUserScope> list = UserUtils.getEmployeeOfWuye();
		if(list!=null && list.size()>0){
			if(StringUtils.isNotBlank(wymc)){
				String[] wymcs = wymc.split("_");
				wyHouseRent.setSource(wymcs[0]);
				wyHouseRent.setWyid(wymcs[1]);
			}else{
				wyHouseRent.setSource(list.get(0).getSource());
				String wyids = list.get(0).getGroupId();
				for(int i=1; i<list.size(); i++){
					wyids += ","+list.get(i).getGroupId();
				}
				wyHouseRent.setWyids(wyids);
			}
		}
		Page<WyHouseRent> page = wyHouseRentService.findPage(new Page<WyHouseRent>(request, response), wyHouseRent);
		if(page.getList()!=null){
			for(WyHouseRent whr : page.getList()){
				String imgs = whr.getImgs();
				if (StringUtils.isNotBlank(imgs)) {
					String[] imgArr = imgs.split(",");
					if (imgArr != null && imgArr.length > 0) {
						whr.setImgList(Arrays.asList(imgArr));
					}
				}
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		model.addAttribute("source", wyHouseRent.getSource());
		model.addAttribute("wyid", wyHouseRent.getWyid());
		return "modules/sns/wyHouseRentList";
	}

	@RequiresPermissions("sns:wyHouseRent:view")
	@RequestMapping(value = "form")
	public String form(WyHouseRent wyHouseRent, Model model) {
		model.addAttribute("wyHouseRent", wyHouseRent);
		return "modules/sns/wyHouseRentForm";
	}

	@RequiresPermissions("sns:wyHouseRent:edit")
	@RequestMapping(value = "save")
	public String save(WyHouseRent wyHouseRent, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyHouseRent)) {
			return form(wyHouseRent, model);
		}
		wyHouseRentService.save(wyHouseRent);
		addMessage(redirectAttributes, "保存房屋租售成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyHouseRent/?repage";
	}

	@RequiresPermissions("sns:wyHouseRent:edit")
	@RequestMapping(value = "delete")
	public String delete(WyHouseRent wyHouseRent, RedirectAttributes redirectAttributes) {
		wyHouseRentService.delete(wyHouseRent);
		addMessage(redirectAttributes, "删除房屋租售成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyHouseRent/?repage";
	}

	@RequiresPermissions("sns:wyHouseRent:audit")
	@RequestMapping(value = "audit")
	public String audit(String id, RedirectAttributes redirectAttributes) {
		boolean b = wyHouseRentService.audit(id);
		if (b) {
			addMessage(redirectAttributes, "审核房屋租售信息成功");
		} else {
			addMessage(redirectAttributes, "审核房屋租售信息失败");
		}
		return "redirect:" + Global.getAdminPath() + "/sns/wyHouseRent/?repage";
	}
	
	
	

}