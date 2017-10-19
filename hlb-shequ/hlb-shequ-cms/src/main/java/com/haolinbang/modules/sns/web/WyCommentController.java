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
import com.haolinbang.modules.sns.entity.WyBizDef;
import com.haolinbang.modules.sns.entity.WyComment;
import com.haolinbang.modules.sns.service.WyBizDefService;
import com.haolinbang.modules.sns.service.WyCommentService;

/**
 * 评论和回复表Controller
 * 
 * @author nlp
 * @version 2017-07-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyComment")
public class WyCommentController extends BaseController {

	@Autowired
	private WyCommentService wyCommentService;

	@Autowired
	private WyBizDefService wyBizDefService;

	@ModelAttribute
	public WyComment get(@RequestParam(required = false) String id) {
		WyComment entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyCommentService.get(id);
		}
		if (entity == null) {
			entity = new WyComment();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyComment:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyComment wyComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyComment> page = wyCommentService.findPage(new Page<WyComment>(request, response), wyComment);
		// 查询业务类型
		WyBizDef wyBizDef = new WyBizDef();
		List<WyBizDef> wyBizDefList = wyBizDefService.findList(wyBizDef);
		model.addAttribute("wyBizDefList", wyBizDefList);

		for (WyComment wyComment2 : page.getList()) {
			String bizType = wyComment2.getBizType();
			for (WyBizDef wyBizDef2 : wyBizDefList) {
//				if (wyBizDef2.getKey().equals(bizType)) {
//					wyComment2.setBizType(wyBizDef2.getName());
//					break;
//				}
			}
		}

		model.addAttribute("page", page);
		return "modules/sns/wyCommentList";
	}

	@RequiresPermissions("sns:wyComment:view")
	@RequestMapping(value = "form")
	public String form(WyComment wyComment, Model model) {
		WyBizDef wyBizDef = new WyBizDef();
		List<WyBizDef> wyBizDefList = wyBizDefService.findList(wyBizDef);
		model.addAttribute("wyBizDefList", wyBizDefList);

		model.addAttribute("wyComment", wyComment);
		return "modules/sns/wyCommentForm";
	}

	@RequiresPermissions("sns:wyComment:edit")
	@RequestMapping(value = "save")
	public String save(WyComment wyComment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyComment)) {
			return form(wyComment, model);
		}
		wyCommentService.save(wyComment);
		addMessage(redirectAttributes, "保存评论和回复表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyComment/?repage";
	}

	@RequiresPermissions("sns:wyComment:edit")
	@RequestMapping(value = "delete")
	public String delete(WyComment wyComment, RedirectAttributes redirectAttributes) {
		wyCommentService.delete(wyComment);
		addMessage(redirectAttributes, "删除评论和回复表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyComment/?repage";
	}

	@RequiresPermissions("sns:wyComment:audit")
	@RequestMapping(value = "audit")
	public String audit(String id, RedirectAttributes redirectAttributes) {
		boolean b = wyCommentService.audit(id);
		if (b) {
			addMessage(redirectAttributes, "删除评论和回复表成功");
		} else {
			addMessage(redirectAttributes, "删除评论和回复表成功");
		}
		return "redirect:" + Global.getAdminPath() + "/sns/wyComment/?repage";
	}
}