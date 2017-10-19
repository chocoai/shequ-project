package com.haolinbang.modules.sns.web;

import java.util.ArrayList;
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
import com.haolinbang.modules.sys.entity.AppUserScope;
import com.haolinbang.modules.sys.utils.UserUtils;

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
		String wymc = request.getParameter("wymc");	
		String bizType = request.getParameter("bizType");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		if(StringUtils.isBlank(beginDate) && StringUtils.isBlank(endDate)){
			wyComment.setBeginDate(null);
			wyComment.setEndDate(null);
		}else if(StringUtils.isNotBlank(beginDate) && StringUtils.isBlank(endDate)){
			wyComment.setBeginDate(beginDate+" 00:00:00");
			wyComment.setEndDate(null);
		}else if(StringUtils.isBlank(beginDate) && StringUtils.isNotBlank(endDate)){
			wyComment.setBeginDate(null);
			wyComment.setEndDate(endDate+" 23:59:59");
		}else {
			wyComment.setBeginDate(beginDate+" 00:00:00");
			wyComment.setEndDate(endDate+" 23:59:59");
		}
		if(StringUtils.isNotBlank(bizType)){
			wyComment.setBizType(bizType);
		}
		List<AppUserScope> list = UserUtils.getEmployeeOfWuye();
		if(list!=null && list.size()>0){
			if(StringUtils.isNotBlank(wymc) && wymc.split("_").length==2){
				String[] wymcs = wymc.split("_");
				wyComment.setSource(wymcs[0]);
				wyComment.setWyid(wymcs[1]);
			}else{
				wyComment.setSource(list.get(0).getSource());
				String wyids = list.get(0).getGroupId();
				for(int i=1; i<list.size(); i++){
					wyids += ","+list.get(i).getGroupId();
				}
				wyComment.setWyids(wyids);
			}
		}
		Page<WyComment> page = wyCommentService.findPage(new Page<WyComment>(request, response), wyComment);
		// 查询业务类型
		WyBizDef wyBizDef = new WyBizDef();
		List<WyBizDef> wyBizDefList = wyBizDefService.findList(wyBizDef);
		model.addAttribute("wyBizDefList", wyBizDefList);

		/*for (WyComment wyComment2 : page.getList()) {
			String bizType = wyComment2.getBizType();
			for (WyBizDef wyBizDef2 : wyBizDefList) {
				if (wyBizDef2.getKey().equals(bizType)) {
					wyComment2.setBizType(wyBizDef2.getName());
					break;
				}
			}
		}*/

		model.addAttribute("page", page);
		model.addAttribute("list", list);
		model.addAttribute("source", wyComment.getSource());
		model.addAttribute("wyid", wyComment.getWyid());
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
	public String delete(WyComment wyComment, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		wyCommentService.delete(wyComment);
		addMessage(redirectAttributes, "删除评论和回复表成功");
		String id = request.getParameter("did");
		if(StringUtils.isNotBlank(id)){
			String relationName = request.getParameter("drelationName");
			String title = request.getParameter("dtitle");
			String bizType = request.getParameter("dbizType");
			String wymc = request.getParameter("dwymc");
			return "redirect:" + Global.getAdminPath() + "/sns/wyComment/detail?id="+id+
					"&relationName="+relationName+"&title="+title+"&bizType="+bizType+"&wymc="+wymc;		
		}
		return "redirect:" + Global.getAdminPath() + "/sns/wyComment/?repage";
	}

	@RequiresPermissions("sns:wyComment:audit")
	@RequestMapping(value = "audit")
	public String audit(WyComment wyComment, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		boolean b;
		if(wyComment.getAudit()){
			b = wyCommentService.audit(wyComment.getId());
		}else{
			b = wyCommentService.audit1(wyComment.getId());
		}
		
		if (b) {
			addMessage(redirectAttributes, "审核成功");
		} else {
			addMessage(redirectAttributes, "审核系统出错");
		}
		String id = request.getParameter("did");
		if(StringUtils.isNotBlank(id)){
			String relationName = request.getParameter("drelationName");
			String title = request.getParameter("dtitle");
			String bizType = request.getParameter("dbizType");
			String wymc = request.getParameter("dwymc");
			return "redirect:" + Global.getAdminPath() + "/sns/wyComment/detail?id="+id+
					"&relationName="+relationName+"&title="+title+"&bizType="+bizType+"&wymc="+wymc;		
		}
		return "redirect:" + Global.getAdminPath() + "/sns/wyComment/?bizType="+wyComment.getBizType();
	}
	
	@RequiresPermissions("sns:wyComment:view")
	@RequestMapping(value = "detail")
	public String detail(WyComment wyComment, Model model, HttpServletRequest request, HttpServletResponse response) {
		//List<WyComment> wyComments = new ArrayList<WyComment>();
		//wyComments = wyCommentService.getDetailByCommentId(wyComment);
		wyComment.setTitle(request.getParameter("title"));
		WyComment wyComment1 = new WyComment();
		wyComment1.setRelationId(StringUtils.toInteger(wyComment.getId()));
		Page<WyComment> page = wyCommentService.findPage(new Page<WyComment>(request, response), wyComment1);
		model.addAttribute("page", page);
		model.addAttribute("wyComment", wyComment);
		return "modules/sns/wyCommentDetailList";
	}
}