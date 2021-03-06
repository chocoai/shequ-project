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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.WyDianzanRecord;
import com.haolinbang.modules.sns.service.WyDianzanRecordService;
import com.haolinbang.modules.sns.util.MemberUtils;

/**
 * 点赞记录Controller
 * 
 * @author nlp
 * @version 2017-07-20
 */
@Controller
@RequestMapping(value = "/sns/wyDianzanRecord")
public class WyDianzanRecordController extends BaseController {

	@Autowired
	private WyDianzanRecordService wyDianzanRecordService;

	@ModelAttribute
	public WyDianzanRecord get(@RequestParam(required = false) String id) {
		WyDianzanRecord entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyDianzanRecordService.get(id);
		}
		if (entity == null) {
			entity = new WyDianzanRecord();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(WyDianzanRecord wyDianzanRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyDianzanRecord> page = wyDianzanRecordService.findPage(new Page<WyDianzanRecord>(request, response), wyDianzanRecord);
		model.addAttribute("page", page);
		return "modules/sns/wyDianzanRecordList";
	}

	@RequestMapping(value = "form")
	public String form(WyDianzanRecord wyDianzanRecord, Model model) {
		model.addAttribute("wyDianzanRecord", wyDianzanRecord);
		return "modules/sns/wyDianzanRecordForm";
	}

	@RequestMapping(value = "save")
	public String save(WyDianzanRecord wyDianzanRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyDianzanRecord)) {
			return form(wyDianzanRecord, model);
		}
		wyDianzanRecordService.save(wyDianzanRecord);
		addMessage(redirectAttributes, "保存点赞记录成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyDianzanRecord/?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(WyDianzanRecord wyDianzanRecord, RedirectAttributes redirectAttributes) {
		wyDianzanRecordService.delete(wyDianzanRecord);
		addMessage(redirectAttributes, "删除点赞记录成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyDianzanRecord/?repage";
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
	public WeJson dianzan(WyDianzanRecord wyDianzanRecord, Model model) {
		try {
			Member member = MemberUtils.getCurrentMember();
			wyDianzanRecord.setMemberId(member.getMemberId());
			boolean b = wyDianzanRecordService.dianzan(wyDianzanRecord);

			return WeJson.success("点赞成功");
		} catch (Exception e) {

			return WeJson.fail("点赞出错");
		}
	}

}