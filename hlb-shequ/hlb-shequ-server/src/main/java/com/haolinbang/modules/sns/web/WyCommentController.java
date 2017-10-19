package com.haolinbang.modules.sns.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.entity.WyComment;
import com.haolinbang.modules.sns.service.WyCommentService;

/**
 * 评论和回复表Controller
 * 
 * @author nlp
 * @version 2017-07-18
 */
@Controller
@RequestMapping(value = "/sns/wyComment")
public class WyCommentController extends BaseController {

	@Autowired
	private WyCommentService wyCommentService;

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

	@RequestMapping(value = { "list", "" })
	public String list(WyComment wyComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyComment> page = wyCommentService.findPage(new Page<WyComment>(request, response), wyComment);
		model.addAttribute("page", page);
		return "modules/sns/wyCommentList";
	}

	@RequestMapping(value = "form")
	public String form(WyComment wyComment, Model model) {
		model.addAttribute("wyComment", wyComment);
		return "modules/sns/wyCommentForm";
	}

	@RequestMapping(value = "save")
	public String save(WyComment wyComment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyComment)) {
			return form(wyComment, model);
		}
		wyCommentService.save(wyComment);
		addMessage(redirectAttributes, "保存评论和回复表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyComment/?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(WyComment wyComment, RedirectAttributes redirectAttributes) {
		wyCommentService.delete(wyComment);
		addMessage(redirectAttributes, "删除评论和回复表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyComment/?repage";
	}

	/**
	 * 发表评论
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/publish")
	public WeJson publish(WyComment wyComment) {
		try {
			boolean b = wyCommentService.publish(wyComment);

			if (b) {
				return WeJson.success("发表成功");
			} else {
				return WeJson.fail("发表失败");
			}
		} catch (Exception e) {
			logger.error("发表评论出错:{}", e);
			return WeJson.fail("发表评论出错");
		}
	}

}