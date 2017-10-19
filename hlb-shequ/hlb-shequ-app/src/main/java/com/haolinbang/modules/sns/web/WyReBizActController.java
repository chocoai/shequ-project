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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.entity.WyActDef;
import com.haolinbang.modules.sns.entity.WyApprove;
import com.haolinbang.modules.sns.entity.WyBizDef;
import com.haolinbang.modules.sns.entity.WyReBizAct;
import com.haolinbang.modules.sns.service.WyActDefService;
import com.haolinbang.modules.sns.service.WyApproveService;
import com.haolinbang.modules.sns.service.WyBizDefService;
import com.haolinbang.modules.sns.service.WyReBizActService;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 工作流和实际的业务对应关系表Controller 流程引用
 * 
 * @author nlp
 * @version 2017-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyReBizAct")
public class WyReBizActController extends BaseController {

	@Autowired
	private WyReBizActService wyReBizActService;

	@Autowired
	private WyActDefService wyActDefService;

	@Autowired
	private WyBizDefService wyBizDefService;

	@Autowired
	private WyApproveService wyApproveService;

	@ModelAttribute
	public WyReBizAct get(@RequestParam(required = false) String id) {
		WyReBizAct entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyReBizActService.get(id);
		}
		if (entity == null) {
			entity = new WyReBizAct();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyReBizAct:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyReBizAct wyReBizAct, HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = UserUtils.getUser();
		String source = user.getSource();
		wyReBizAct.setGroupId(user.getParentGroupId().toString());
		wyReBizAct.setSource(source);

		// Page<WyReBizAct> page = wyReBizActService.findPage(new
		// Page<WyReBizAct>(request, response), wyReBizAct);
		// model.addAttribute("page", page);

		// 业务分类
		String category = wyReBizAct.getCategory();
		// 获取能够使用的流程和业务流程
		List<WyBizDef> wyBizDefList = wyBizDefService.findList(new WyBizDef());
		for (WyBizDef wyBizDef : wyBizDefList) {
			wyBizDef.setShowName(wyBizDef.getName() + "（" + wyBizDef.getKey() + "）");
		}
		if (StringUtils.isBlank(category) && wyBizDefList != null && !wyBizDefList.isEmpty()) {
			category = wyBizDefList.get(0).getKey();
		}
		model.addAttribute("wyBizDefList", wyBizDefList);

		List<WyActDef> actList = wyActDefService.getWyActDefListByCatagory(wyReBizAct.getCategory());
		// 设置分类名称
		for (WyActDef act : actList) {
			for (WyBizDef biz : wyBizDefList) {
				if (act.getCategory().equals(biz.getKey())) {
					act.setCategory(biz.getName());
				}
			}
		}
		model.addAttribute("actList", actList);

		WyBizDef wyBizDef = wyBizDefService.getWyBizDefByCategory(category);
		if (wyBizDef != null) {
			WyReBizAct selectedWyReBizAct = wyReBizActService.getDefaultWyReBizAct(source, user.getParentGroupId().toString(), wyBizDef.getId());
			model.addAttribute("selectedWyReBizAct", selectedWyReBizAct);
		}

		model.addAttribute("wyReBizAct", wyReBizAct);
		return "modules/sns/wyReBizActList";
	}

	@RequiresPermissions("sns:wyReBizAct:view")
	@RequestMapping(value = "form")
	public String form(WyReBizAct wyReBizAct, Model model) {

		// 获取能够使用的流程和业务流程
		List<WyActDef> wyActDefList = wyActDefService.findList(new WyActDef());
		List<WyBizDef> wyBizDefList = wyBizDefService.findList(new WyBizDef());

		for (WyActDef wyActDef : wyActDefList) {
			wyActDef.setShowName(wyActDef.getName() + ":" + wyActDef.getProcDefId());
		}

		for (WyBizDef wyBizDef : wyBizDefList) {
			wyBizDef.setShowName(wyBizDef.getName() + ":" + wyBizDef.getKey());
		}

		model.addAttribute("wyActDefList", wyActDefList);
		model.addAttribute("wyBizDefList", wyBizDefList);
		model.addAttribute("wyReBizAct", wyReBizAct);

		return "modules/sns/wyReBizActForm";
	}

	@RequiresPermissions("sns:wyReBizAct:edit")
	@ResponseBody
	@RequestMapping(value = "save")
	public WeJson save(WyReBizAct wyReBizAct, Model model, RedirectAttributes redirectAttributes) {
		try {
			WyReBizAct wyReBizAct2 = wyReBizActService.saveDefault(wyReBizAct);

			return WeJson.success(wyReBizAct2);
		} catch (Exception e) {
			logger.error("流程引用保存出错,{}", e);
			return WeJson.fail("流程引用保存出错");
		}
	}

	@RequiresPermissions("sns:wyReBizAct:edit")
	@RequestMapping(value = "delete")
	public String delete(WyReBizAct wyReBizAct, RedirectAttributes redirectAttributes) {
		wyReBizActService.delete(wyReBizAct);
		addMessage(redirectAttributes, "删除工作流和实际的业务对应关系表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyReBizAct/?repage";
	}

	/**
	 * 显示需要设置的流程
	 * 
	 * @param wyReBizAct
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sns:wyReBizAct:view")
	@RequestMapping("/setting")
	public String setting(WyReBizAct wyReBizAct, HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = UserUtils.getUser();
		wyReBizAct.setGroupId(user.getParentGroupId().toString());
		wyReBizAct.setSource(user.getSource());
		String category = wyReBizAct.getCategory();
		// 获取能够使用的流程和业务流程
		List<WyBizDef> wyBizDefList = wyBizDefService.findList(new WyBizDef());
		if (StringUtils.isBlank(category) && wyBizDefList != null && !wyBizDefList.isEmpty()) {
			category = wyBizDefList.get(0).getKey();
			wyReBizAct.setCategory(category);
		}
		model.addAttribute("wyBizDefList", wyBizDefList);

		Page<WyReBizAct> page = wyReBizActService.findPage(new Page<WyReBizAct>(request, response), wyReBizAct);

		// 设置分类名称
		for (WyReBizAct act : page.getList()) {
			for (WyBizDef biz : wyBizDefList) {
				if (act.getWyActDef().getCategory().equals(biz.getKey())) {
					act.setCategory(biz.getName());
				}
			}
			// 查询是否修改
			List<WyApprove> wyApproveList = wyApproveService.getWyApproveByProcDefId(act.getWyActDef().getProcDefId());
			if (wyApproveList == null || wyApproveList.isEmpty() || wyApproveList.size() <= 0) {
				act.setEditable(true);
			}
		}

		model.addAttribute("page", page);

		return "modules/sns/wyReBizActSetting";
	}
}