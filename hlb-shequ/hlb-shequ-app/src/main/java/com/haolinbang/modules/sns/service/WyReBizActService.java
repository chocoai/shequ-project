package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.modules.sns.dao.WyReBizActDao;
import com.haolinbang.modules.sns.entity.WyActDef;
import com.haolinbang.modules.sns.entity.WyBizDef;
import com.haolinbang.modules.sns.entity.WyReBizAct;
import com.haolinbang.modules.sys.entity.User;
import com.haolinbang.modules.sys.utils.UserUtils;

/**
 * 工作流和实际的业务对应关系表Service
 * 
 * @author nlp
 * @version 2017-05-08
 */
@Service
@Transactional(readOnly = true)
public class WyReBizActService extends CrudService<WyReBizActDao, WyReBizAct> {

	@Autowired
	private WyReBizActDao wyReBizActDao;

	@Autowired
	private WyActDefService wyActDefService;

	@Autowired
	private WyBizDefService wyBizDefService;

	public WyReBizAct get(String id) {
		return super.get(id);
	}

	public List<WyReBizAct> findList(WyReBizAct wyReBizAct) {
		return super.findList(wyReBizAct);
	}

	public Page<WyReBizAct> findPage(Page<WyReBizAct> page, WyReBizAct wyReBizAct) {
		Page<WyReBizAct> page2 = super.findPage(page, wyReBizAct);

		return page2;
	}

	@Transactional(readOnly = false)
	public void save(WyReBizAct wyReBizAct) {
		super.save(wyReBizAct);
	}

	@Transactional(readOnly = false)
	public void delete(WyReBizAct wyReBizAct) {
		super.delete(wyReBizAct);
	}

	public List<WyReBizAct> getBizActs(WyReBizAct wyReBizAct) {
		return wyReBizActDao.getBizActs(wyReBizAct);
	}

	public WyReBizAct getWyReBizActByActIdAndBizId(String actId, String bizId) {
		return wyReBizActDao.getWyReBizActByActIdAndBizId(actId, bizId);
	}

	public WyReBizAct getWyReBizActByActId(String bizId) {
		return wyReBizActDao.getWyReBizActByActId(bizId);
	}

	public WyReBizAct getBizActByDefId(String procDefinitionId) {
		return wyReBizActDao.getBizActByDefId(procDefinitionId);
	}

	public WyReBizAct getWyReBizAct(String source, String groupId, String bizId, String actId) {
		return wyReBizActDao.getWyReBizAct(source, groupId, bizId, actId);
	}

	public WyReBizAct getDefaultWyReBizAct(String source, String groupId, String bizId) {
		return wyReBizActDao.getDefaultWyReBizAct(source, groupId, bizId);
	}

	public List<WyReBizAct> getWyReBizActByBizid(String source, String groupId, String bizId) {
		return wyReBizActDao.getWyReBizActByBizid(source, groupId, bizId);
	}

	/**
	 * 保存默认信息,使用事务处理
	 * 
	 * @param wyReBizAct
	 * @return
	 */
	@Transactional(readOnly = false)
	public WyReBizAct saveDefault(WyReBizAct wyReBizAct) {
		// 如果已经存在就更新关联关系
		String category = wyReBizAct.getCategory();
		User user = UserUtils.getUser();
		String source = user.getSource();
		WyBizDef wyBizDef = wyBizDefService.getWyBizDefByCategory(category);

		WyReBizAct selectedWyReBizAct = null;
		if (wyBizDef != null) {
			selectedWyReBizAct = this.getWyReBizAct(source, user.getParentGroupId().toString(), wyBizDef.getId(), wyReBizAct.getActId());
		}

		// 如果已经存在记录,更新对应的流程即可
		if (selectedWyReBizAct != null) {
			if (StringUtils.isNotBlank(wyReBizAct.getActId())) {
				// 更新流程id
				WyActDef wyActDef = wyActDefService.get(wyReBizAct.getActId());
				selectedWyReBizAct.setWyActDef(wyActDef);
				selectedWyReBizAct.setIsDefault(true);
				this.save(selectedWyReBizAct);
				wyReBizAct = selectedWyReBizAct;
			}
		} else {
			// 流程id不为空
			if (StringUtils.isNotBlank(wyReBizAct.getActId())) {
				WyActDef wyActDef = wyActDefService.get(wyReBizAct.getActId());
				wyReBizAct.setWyActDef(wyActDef);
			}
			// 关联业务id,通过分类进行关联
			wyReBizAct.setWyBizDef(wyBizDef);
			wyReBizAct.setSource(source);
			wyReBizAct.setGroupId(user.getParentGroupId().toString());
			wyReBizAct.setIsDefault(true);
			wyReBizAct.setId(null);
			this.save(wyReBizAct);
		}
		// 更新默认的记录条数
		List<WyReBizAct> wyReBizActList = this.getWyReBizActByBizid(source, user.getParentGroupId().toString(), wyBizDef.getId());
		if (wyReBizActList != null && !wyReBizActList.isEmpty()) {
			for (WyReBizAct reBizAct : wyReBizActList) {
				if (!wyReBizAct.getId().equals(reBizAct.getId())) {
					reBizAct.setIsDefault(false);
					this.save(reBizAct);
				}
			}
		}
		return wyReBizAct;
	}

	public WyReBizAct getBizActByBizKey(String bizKey) {

		User user = UserUtils.getUser();
		String source = user.getSource();

		// 所在部門
		WyReBizAct wyReBizAct = null;
		GroupInfo groupInfo = UserUtils.getGroupInfo(user.getGroupInfo().getGroupId().toString(), source);
		if (groupInfo != null) {
			// 查询管理处
			wyReBizAct = wyReBizActDao.getBizActByBizKey(source, groupInfo.getParentId(), bizKey);
			if (wyReBizAct == null) {
				// 如果管理处不存在,查询分公司
				GroupInfo pgroupInfo = UserUtils.getGroupInfo(groupInfo.getParentId(), source);
				wyReBizAct = wyReBizActDao.getBizActByBizKey(source, groupInfo.getParentId(), bizKey);
				if (wyReBizAct == null) {
					// 查询分公司
					if (pgroupInfo != null && StringUtils.isNotBlank(pgroupInfo.getGroupId())) {
						wyReBizAct = wyReBizActDao.getBizActByBizKey(source, pgroupInfo.getParentId(), bizKey);
						if (wyReBizAct == null) {
							// 如果分公司不存在,查询集团公司
							GroupInfo ppgroupInfo = UserUtils.getGroupInfo(pgroupInfo.getParentId(), source);
							wyReBizAct = wyReBizActDao.getBizActByBizKey(source, ppgroupInfo.getParentId(), bizKey);
							if (wyReBizAct != null) {
								return wyReBizAct;
							}
						} else {
							return wyReBizAct;
						}
					}
				} else {
					return wyReBizAct;
				}
			} else {
				return wyReBizAct;
			}
		}
		return wyReBizAct;
	}
}