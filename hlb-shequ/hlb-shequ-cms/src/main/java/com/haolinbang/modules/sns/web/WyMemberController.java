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
import com.haolinbang.modules.sns.entity.WyMember;
import com.haolinbang.modules.sns.service.WyMemberService;

/**
 * 会员信息Controller
 * @author nlp
 * @version 2017-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyMember")
public class WyMemberController extends BaseController {

	@Autowired
	private WyMemberService wyMemberService;
	
	@ModelAttribute
	public WyMember get(@RequestParam(required=false) Integer memberId) {
		WyMember entity = null;
		if (StringUtils.isNotBlank(""+memberId)){
			entity = wyMemberService.get(""+memberId);
		}
		if (entity == null){
			entity = new WyMember();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyMember:view")
	@RequestMapping(value = {"list", ""})
	public String list(WyMember wyMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyMember> page = wyMemberService.findPage(new Page<WyMember>(request, response), wyMember); 
		model.addAttribute("page", page);
		return "modules/sns/wyMemberList";
	}

	@RequiresPermissions("sns:wyMember:view")
	@RequestMapping(value = "form")
	public String form(WyMember wyMember, Model model, String type) {//默认调用get方法
		model.addAttribute("wyMember", wyMember);
		model.addAttribute("type", type);
		return "modules/sns/wyMemberForm";
	}

	@RequiresPermissions("sns:wyMember:edit")
	@RequestMapping(value = "save")
	public String save(WyMember wyMember, Model model, RedirectAttributes redirectAttributes, String type) {
		if (!beanValidator(model, wyMember)){
			return form(wyMember, model, type);
		}
		if(type!=null && type.equals("edit")){
			wyMember.setId(wyMember.getMemberId().toString());
		}
		wyMemberService.save(wyMember);
		addMessage(redirectAttributes, "保存会员信息成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyMember/?repage";
	}
	
	@RequiresPermissions("sns:wyMember:edit")
	@RequestMapping(value = "delete")
	public String delete(WyMember wyMember, RedirectAttributes redirectAttributes) {
		wyMemberService.delete(wyMember);
		addMessage(redirectAttributes, "删除会员信息成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyMember/?repage";
	}

}