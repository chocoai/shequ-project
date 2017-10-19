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
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.entity.WyFleaMarket;
import com.haolinbang.modules.sns.service.WyFleaMarketService;
import com.haolinbang.modules.sys.entity.AppUserScope;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 跳蚤市场Controller
 * 
 * @author nlp
 * @version 2017-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyFleaMarket")
public class WyFleaMarketController extends BaseController {

	@Autowired
	private WyFleaMarketService wyFleaMarketService;

	@ModelAttribute
	public WyFleaMarket get(@RequestParam(required = false) String id) {
		WyFleaMarket entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyFleaMarketService.get(id);
		}
		if (entity == null) {
			entity = new WyFleaMarket();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyFleaMarket:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyFleaMarket wyFleaMarket, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*User user = UserUtils.getUser();
		if(user!=null){
			wyFleaMarket.setSource(user.getSource());
			wyFleaMarket.setGroupId(user.getParentGroupId());
		}*/
		String wymc = request.getParameter("wymc");
		List<AppUserScope> list = UserUtils.getEmployeeOfWuye();
		if(list!=null && list.size()>0){
			if(StringUtils.isNotBlank(wymc)){
				String[] wymcs = wymc.split("_");
				wyFleaMarket.setSource(wymcs[0]);
				wyFleaMarket.setWyid(wymcs[1]);
			}else{
				wyFleaMarket.setSource(list.get(0).getSource());
				String wyids = list.get(0).getGroupId();
				for(int i=1; i<list.size(); i++){
					wyids += ","+list.get(i).getGroupId();
				}
				wyFleaMarket.setWyids(wyids);
			}
		}
		Page<WyFleaMarket> page = wyFleaMarketService.findPage(new Page<WyFleaMarket>(request, response), wyFleaMarket);
		if(page.getList()!=null){
			for(WyFleaMarket wfm : page.getList()){
				String imgs = wfm.getImgs();
				if (StringUtils.isNotBlank(imgs)) {
					String[] imgArr = imgs.split(",");
					if (imgArr != null && imgArr.length > 0) {
						wfm.setImgList(Arrays.asList(imgArr));
					}
				}
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		model.addAttribute("source", wyFleaMarket.getSource());
		model.addAttribute("wyid", wyFleaMarket.getWyid());
		return "modules/sns/wyFleaMarketList";
	}

	@RequiresPermissions("sns:wyFleaMarket:view")
	@RequestMapping(value = "form")
	public String form(WyFleaMarket wyFleaMarket, Model model) {
		model.addAttribute("wyFleaMarket", wyFleaMarket);
		return "modules/sns/wyFleaMarketForm";
	}

	@RequiresPermissions("sns:wyFleaMarket:edit")
	@RequestMapping(value = "save")
	public String save(WyFleaMarket wyFleaMarket, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyFleaMarket)) {
			return form(wyFleaMarket, model);
		}
		wyFleaMarketService.save(wyFleaMarket);
		addMessage(redirectAttributes, "保存跳蚤市场成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyFleaMarket/?repage";
	}

	@RequiresPermissions("sns:wyFleaMarket:edit")
	@RequestMapping(value = "delete")
	public String delete(WyFleaMarket wyFleaMarket, RedirectAttributes redirectAttributes) {
		wyFleaMarketService.delete(wyFleaMarket);
		addMessage(redirectAttributes, "删除跳蚤市场成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyFleaMarket/?repage";
	}

	/**
	 * 审核跳蚤市场发布信息
	 * 
	 * @param wyHouseRent
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sns:wyFleaMarket:audit")
	@RequestMapping(value = "audit")
	public String audit(String id, RedirectAttributes redirectAttributes) {
		boolean b = wyFleaMarketService.audit(id);
		if (b) {
			addMessage(redirectAttributes, "审核房屋租售信息成功");
		} else {
			addMessage(redirectAttributes, "审核房屋租售信息失败");
		}
		return "redirect:" + Global.getAdminPath() + "/sns/wyFleaMarket/?repage";
	}

}