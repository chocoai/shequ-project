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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.utils.Exceptions;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.constant.WyConstants;
import com.haolinbang.modules.sns.dto.WyRelationCandidate;
import com.haolinbang.modules.sns.dto.ZtreeDataDto;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.WyActCandidate;
import com.haolinbang.modules.sns.entity.WyInstCandidate;
import com.haolinbang.modules.sns.entity.WyOrgGroup;
import com.haolinbang.modules.sns.entity.WyOrgStaff;
import com.haolinbang.modules.sns.service.WyActCandidateService;
import com.haolinbang.modules.sns.service.WyInstCandidateService;
import com.haolinbang.modules.sns.service.WyOrgGroupService;
import com.haolinbang.modules.sns.service.WyOrgStaffService;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 候选人表或者群组表Controller
 * 
 * @author NLP
 * @version 2017-05-03
 */
@Controller
@RequestMapping(value = "/sns/wyActCandidate")
public class WyActCandidateController extends BaseController {

	@Autowired
	private WyActCandidateService wyActCandidateService;

	@Autowired
	private WyInstCandidateService wyInstCandidateService;

	@Autowired
	private WyOrgGroupService wyOrgGroupService;

	@Autowired
	private WyOrgStaffService wyOrgStaffService;

	@ModelAttribute
	public WyActCandidate get(@RequestParam(required = false) String id) {
		WyActCandidate entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wyActCandidateService.get(id);
		}
		if (entity == null) {
			entity = new WyActCandidate();
		}
		return entity;
	}

	@RequiresPermissions("sns:wyActCandidate:view")
	@RequestMapping(value = { "list", "" })
	public String list(WyActCandidate wyActCandidate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WyActCandidate> page = wyActCandidateService.findPage(new Page<WyActCandidate>(request, response), wyActCandidate);
		model.addAttribute("page", page);
		return "modules/sns/wyActCandidateList";
	}

	@RequiresPermissions("sns:wyActCandidate:view")
	@RequestMapping(value = "form")
	public String form(WyActCandidate wyActCandidate, Model model) {
		model.addAttribute("wyActCandidate", wyActCandidate);
		return "modules/sns/wyActCandidateForm";
	}

	@RequiresPermissions("sns:wyActCandidate:edit")
	@RequestMapping(value = "save")
	public String save(WyActCandidate wyActCandidate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wyActCandidate)) {
			return form(wyActCandidate, model);
		}
		wyActCandidateService.save(wyActCandidate);
		addMessage(redirectAttributes, "保存候选人表或者群组表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyActCandidate/?repage";
	}

	@RequiresPermissions("sns:wyActCandidate:edit")
	@RequestMapping(value = "delete")
	public String delete(WyActCandidate wyActCandidate, RedirectAttributes redirectAttributes) {
		wyActCandidateService.delete(wyActCandidate);
		addMessage(redirectAttributes, "删除候选人表或者群组表成功");
		return "redirect:" + Global.getAdminPath() + "/sns/wyActCandidate/?repage";
	}

	/**
	 * 保存指定的处理人
	 * 
	 * @param wyActCandidate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "specifyCandidate")
	public WeJson specifyCandidate(WyRelationCandidate wyRelationCandidate) {
		try {
			String assignee = wyRelationCandidate.getAssignee();
			if (StringUtils.isNotBlank(assignee)) {
				// 查询是对哪个节点设置处理人
				// 查询该节点是否需要指定处理人
				WyActCandidate wyActCandidate = wyActCandidateService.getWyActCandidateByDefidAndDefkeyAndType(wyRelationCandidate.getProcDefId(), wyRelationCandidate.getTaskId(),
						WyConstants.CANDIDATE_TYPE_PREVIOUS_SPECIFY);

				String[] assignees = assignee.split(",");
				if (wyActCandidate != null) {
					for (String candidate : assignees) {
						WyInstCandidate wyInstCandidate = wyInstCandidateService.getWyInstCandidateByProcInsIdAndTaskIdAndCandidate(wyRelationCandidate.getProcInsId(),
								wyActCandidate.getSpecifyTaskId(), candidate);

						if (wyInstCandidate == null) {
							wyInstCandidate = new WyInstCandidate();
						}
						wyInstCandidate.setCandidate(candidate);
						wyInstCandidate.setProcInstId(wyRelationCandidate.getProcInsId());
						wyInstCandidate.setTaskId(wyActCandidate.getTaskId());// 设置成下一个节点的taskid
						wyInstCandidate.setType(WyConstants.CANDIDATE_TYPE_PREVIOUS_SPECIFY);// 类型为上一节点指定
						wyInstCandidateService.save(wyInstCandidate);
					}
				}

			}
			return WeJson.success("保存成功");
		} catch (Exception e) {
			logger.error("指定处理人出错:{}", e);
			return WeJson.fail("保存失败");
		}
	}

	/**
	 * 委托他人办理
	 * 
	 * @param wyRelationCandidate
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/entrustOthers")
	public WeJson entrustOthers(WyRelationCandidate wyRelationCandidate) {

		return WeJson.success("");
	}

	/**
	 * 选择组织机构
	 * 
	 * @param orgId
	 * @return
	 */
	@RequestMapping("/selectOrg")
	public String selectOrg(WyRelationCandidate wyRelationCandidate, Model model) {
		Member member = MemberUtils.getCurrentMember();
		// 通过用户id获取所在部门
		if (member.getAdmintype().equals('1')) {
			model.addAttribute("group", member.getStaff().getGroupInfo());
		}
		List<ZtreeDataDto> orgTreeList = UserUtils.getOrgTreeList(member.getSource());
		model.addAttribute("wyRelationCandidate", wyRelationCandidate);
		model.addAttribute("orgTreeList", orgTreeList);

		List<ZtreeDataDto> candidateTreeList = UserUtils.getOrgTreeList(member.getSource());
		model.addAttribute("candidateTreeList", candidateTreeList);

		model.addAttribute("wyRelationCandidate", wyRelationCandidate);

		return "modules/sns/selectOrg";
	}

	/**
	 * 选择用户
	 * 
	 * @param orgId
	 * @return
	 */
	@RequestMapping("/selectUser")
	public String selectUser(WyRelationCandidate wyRelationCandidate, Model model) {
		try {
			Member member = MemberUtils.getCurrentMember();
			// 通过用户id获取所在部门
			if (member.getAdmintype().equals('1')) {
				WyOrgGroup group = wyOrgGroupService.getWyOrgGroupByStaffId(member.getMemberId().toString());
				model.addAttribute("group", group);
			}
		} catch (Exception e) {
			logger.error("获取用户信息出错:{}", e);
			return Exceptions.deal(e);
		}
		return "modules/sns/selectUser";
	}

	/**
	 * 
	 * @param orgId
	 * @return
	 */
	@RequestMapping("/delegateTaskShow")
	public String delegateTaskShow(WyRelationCandidate wyRelationCandidate, Model model) {
		ZtreeDataDto company = new ZtreeDataDto();
		company.setId("G_" + "1");
		company.setPid("G_" + "0");
		company.setName("豪龙物业公司");
		model.addAttribute("company", company);
		model.addAttribute("wyRelationCandidate", wyRelationCandidate);
		return "modules/sns/delegateTaskShow";
	}

	/**
	 * 
	 * @param orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectOrgData")
	public WeJson selectOrgData(String pid, String type) {
		List<ZtreeDataDto> list = new ArrayList<ZtreeDataDto>();
		pid = pid.replace(WyConstants.CANDIDATE_TYPE_GROUP, "");
		if (WyConstants.CANDIDATE_GROUP_GRUOP.equals(type) || WyConstants.CANDIDATE_GROUP_COMPANY.equals(type) || WyConstants.CANDIDATE_GROUP_MANAGEMENT.equals(type)) {// 集团
			List<WyOrgGroup> groupList = wyOrgGroupService.getWyOrgGroupListByPid(pid);
			for (WyOrgGroup group : groupList) {
				ZtreeDataDto dto = new ZtreeDataDto();
				dto.setId(WyConstants.CANDIDATE_TYPE_GROUP + group.getGroupId().toString());
				dto.setName(group.getGroupName());
				dto.setType(group.getGroupType().toString());
				list.add(dto);
			}
		} else if (WyConstants.CANDIDATE_GROUP_DEPARTMENT.equals(type)) {
			// 部门
			List<WyOrgStaff> wyOrgStaffList = wyOrgStaffService.getWyOrgStaffByGroupId(Integer.valueOf(pid));
			for (WyOrgStaff staff : wyOrgStaffList) {
				ZtreeDataDto dto = new ZtreeDataDto();
				dto.setId(WyConstants.CANDIDATE_TYPE_ASSIGNEE_MANAGER + staff.getGroupId().toString());
				dto.setName(staff.getStaffName());
				dto.setJobName(staff.getJob());
				list.add(dto);
			}
		}

		return WeJson.success(list);
	}

	/**
	 * 
	 * @param orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkSelectOrg")
	public WeJson checkSelectOrg(WyRelationCandidate wyRelationCandidate) {
		List<WyInstCandidate> list = wyInstCandidateService.getWyInstCandidateByProcInsIdAndTaskId(wyRelationCandidate.getProcInsId(), wyRelationCandidate.getTaskId());
		if (list != null && list.size() > 0) {
			return WeJson.success("成功");
		} else {
			return WeJson.fail("处理失败");
		}
	}

	/**
	 * 
	 * @param orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectOrgSave")
	public WeJson selectOrgSave(WyRelationCandidate wyRelationCandidate) {
		WeJson weJson = wyActCandidateService.selectOrgSave(wyRelationCandidate);
		return weJson;
	}

}