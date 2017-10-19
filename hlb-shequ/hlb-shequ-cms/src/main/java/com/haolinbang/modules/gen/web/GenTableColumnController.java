package com.haolinbang.modules.gen.web;

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
import com.haolinbang.modules.gen.entity.GenTableColumn;
import com.haolinbang.modules.gen.service.GenTableColumnService;

/**
 * 业务表字段Controller
 * 
 * @author nlp
 * @version 2016-01-31
 */
@Controller
@RequestMapping(value = "${adminPath}/gen/genTableColumn")
public class GenTableColumnController extends BaseController {

	@Autowired
	private GenTableColumnService genTableColumnService;

	@ModelAttribute
	public GenTableColumn get(@RequestParam(required = false) String id) {
		GenTableColumn entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = genTableColumnService.get(id);
		}
		if (entity == null) {
			entity = new GenTableColumn();
		}
		return entity;
	}

	@RequiresPermissions("gen:genTableColumn:view")
	@RequestMapping(value = { "list", "" })
	public String list(GenTableColumn genTableColumn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GenTableColumn> page = genTableColumnService.findPage(new Page<GenTableColumn>(request, response), genTableColumn);
		model.addAttribute("page", page);
		return "modules/gen/genTableColumnList";
	}

	@RequiresPermissions("gen:genTableColumn:view")
	@RequestMapping(value = "form")
	public String form(GenTableColumn genTableColumn, Model model) {
		model.addAttribute("genTableColumn", genTableColumn);
		return "modules/gen/genTableColumnForm";
	}

	@RequiresPermissions("gen:genTableColumn:edit")
	@RequestMapping(value = "save")
	public String save(GenTableColumn genTableColumn, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, genTableColumn)) {
			return form(genTableColumn, model);
		}
		genTableColumnService.save(genTableColumn);
		addMessage(redirectAttributes, "保存业务表字段成功");
		return "redirect:" + Global.getAdminPath() + "/gen/genTableColumn/?repage";
	}

	@RequiresPermissions("gen:genTableColumn:edit")
	@RequestMapping(value = "delete")
	public String delete(GenTableColumn genTableColumn, RedirectAttributes redirectAttributes) {
		genTableColumnService.delete(genTableColumn);
		addMessage(redirectAttributes, "删除业务表字段成功");
		return "redirect:" + Global.getAdminPath() + "/gen/genTableColumn/?repage";
	}

}