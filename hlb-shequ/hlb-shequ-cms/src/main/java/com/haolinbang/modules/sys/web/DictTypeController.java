package com.haolinbang.modules.sys.web;

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
import com.haolinbang.modules.sys.entity.Dict;
import com.haolinbang.modules.sys.entity.DictType;
import com.haolinbang.modules.sys.service.DictService;
import com.haolinbang.modules.sys.service.DictTypeService;

/**
 * 字典分类Controller
 * @author nlp
 * @version 2017-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dictType")
public class DictTypeController extends BaseController {

	@Autowired
	private DictTypeService dictTypeService;
	
	@Autowired
	private DictService dictService;
	
	@ModelAttribute
	public DictType get(@RequestParam(required=false) String id) {
		DictType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dictTypeService.get(id);
		}
		if (entity == null){
			entity = new DictType();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = {"list", ""})
	public String list(DictType dictType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DictType> page = dictTypeService.findPage(new Page<DictType>(request, response), dictType); 
		model.addAttribute("page", page);
		return "modules/sys/dictTypeList";
	}

	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "form")
	public String form(DictType dictType, Model model) {
		model.addAttribute("dictType", dictType);
		return "modules/sys/dictTypeForm";
	}

	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "save")
	public String save(DictType dictType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dictType)){
			return form(dictType, model);
		}
		boolean flag = false;
		String value = "";
		if(!dictType.getIsNewRecord()){
			DictType old_dictType = dictTypeService.get(dictType.getId());
			if(old_dictType != null && !old_dictType.getValue().equals(dictType.getValue())){
				flag = true;
				value = old_dictType.getValue();
			}
		}
		dictTypeService.save(dictType);
		if(flag){
			Dict dict = new Dict();
			dict.setType(value);
			List<Dict> typeList = dictService.getByType(dict);
			for(Dict dc : typeList){
				dc.setType(dictType.getValue());
				dictService.updateType(dc);
			}
		}
		addMessage(redirectAttributes, "保存字典分类成功");
		return "redirect:"+Global.getAdminPath()+"/sys/dictType/?repage";
	}
	
	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "delete")
	public String delete(DictType dictType, RedirectAttributes redirectAttributes) {
		dictTypeService.delete(dictType);
		addMessage(redirectAttributes, "删除字典分类成功");
		return "redirect:"+Global.getAdminPath()+"/sys/dictType/?repage";
	}

}