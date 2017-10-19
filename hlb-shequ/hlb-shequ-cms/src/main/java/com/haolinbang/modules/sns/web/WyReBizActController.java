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
import com.haolinbang.modules.act.service.ActProcessService;
import com.haolinbang.modules.sns.entity.WyActDef;
import com.haolinbang.modules.sns.entity.WyBizDef;
import com.haolinbang.modules.sns.entity.WyReBizAct;
import com.haolinbang.modules.sns.service.WyActDefService;
import com.haolinbang.modules.sns.service.WyBizDefService;
import com.haolinbang.modules.sns.service.WyReBizActService;

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
	private WyBizDefService wyBizDefService;

	@Autowired
	private ActProcessService actProcessService;

	@Autowired
	private WyActDefService wyActDefService;

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
		Page<WyReBizAct> page = wyReBizActService.findPage(new Page<WyReBizAct>(request, response), wyReBizAct);
		model.addAttribute("page", page);

		List<WyReBizAct> list = wyReBizActService.getBizActs(wyReBizAct);
		model.addAttribute("list", list);
		
		

		return "modules/sns/wyReBizActList";
	}

	@RequiresPermissions("sns:wyReBizAct:view")
	@RequestMapping(value = "form")
	public String form(WyReBizAct wyReBizAct, Model model) {

		// 获取能够使用的流程和业务流程
		List<WyBizDef> wyBizDefList = wyBizDefService.findList(new WyBizDef());
		for (WyBizDef wyBizDef : wyBizDefList) {
			wyBizDef.setShowName(wyBizDef.getName() + "（" + wyBizDef.getKey() + "）");
		}

		model.addAttribute("wyBizDefList", wyBizDefList);
		model.addAttribute("wyReBizAct", wyReBizAct);

		return "modules/sns/wyReBizActForm";
	}

	@RequiresPermissions("sns:wyReBizAct:edit")
	@RequestMapping(value = "save")
	public String save(WyReBizAct wyReBizAct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyReBizAct)) {
			return form(wyReBizAct, model);
		}
		// 如果已经存在就更新关联关系
		WyReBizAct wyReBizAct2 = wyReBizActService.getWyReBizActByActId(wyReBizAct.getBizId());
		if (wyReBizAct2 != null) {
			wyReBizAct.setId(wyReBizAct2.getId());
		}
		wyReBizActService.save(wyReBizAct);
		addMessage(redirectAttributes, "保存工作流和实际的业务对应关系表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyReBizAct/?repage";
	}

	@RequiresPermissions("sns:wyReBizAct:edit")
	@RequestMapping(value = "delete")
	public String delete(WyReBizAct wyReBizAct, RedirectAttributes redirectAttributes) {
		wyReBizActService.delete(wyReBizAct);
		addMessage(redirectAttributes, "删除工作流和实际的业务对应关系表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyReBizAct/?repage";
	}

	@RequiresPermissions("sns:wyReBizAct:view")
	@ResponseBody
	@RequestMapping("/getActByType")
	public WeJson getActByType(String catagory, HttpServletRequest request, HttpServletResponse response) {
		List<WyActDef> actList = wyActDefService.getWyActDefListByCatagory(catagory);

		return WeJson.success(actList);
	}

}