package com.haolinbang.modules.sns.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.modules.sns.dao.WyReBizActDao;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyReBizAct;
import com.haolinbang.modules.sns.util.RoomUtils;
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

	public WyReBizAct get(String id) {
		return super.get(id);
	}

	public List<WyReBizAct> findList(WyReBizAct wyReBizAct) {
		return super.findList(wyReBizAct);
	}

	public Page<WyReBizAct> findPage(Page<WyReBizAct> page, WyReBizAct wyReBizAct) {
		return super.findPage(page, wyReBizAct);
	}

	@Transactional(readOnly = false)
	public void save(WyReBizAct wyReBizAct) {
		super.save(wyReBizAct);
	}

	@Transactional(readOnly = false)
	public void delete(WyReBizAct wyReBizAct) {
		super.delete(wyReBizAct);
	}

	public List<WyReBizAct> getBizActs() {
		return wyReBizActDao.getBizActs();
	}

	@Transactional(readOnly = false)
	public WyReBizAct getBizActByBizKey(String bizKey) {
		Room room = RoomUtils.getCurrentRoom();
		String source = room.getSource();
		String groupId = room.getGroupId().toString();
		// 所在部門
		WyReBizAct wyReBizAct = null;
		GroupInfo groupInfo = UserUtils.getGroup(groupId, source);
		if (groupInfo != null) {
			// 查询管理处
			wyReBizAct = wyReBizActDao.getBizActByBizKey(source, groupInfo.getParentId(), bizKey);
			if (wyReBizAct == null) {
				// 如果管理处不存在,查询分公司
				GroupInfo pgroupInfo = UserUtils.getGroup(groupInfo.getParentId(), source);
				wyReBizAct = wyReBizActDao.getBizActByBizKey(source, groupInfo.getParentId(), bizKey);
				if (wyReBizAct == null) {
					// 查询分公司
					if (pgroupInfo != null && StringUtils.isNotBlank(pgroupInfo.getGroupId())) {
						wyReBizAct = wyReBizActDao.getBizActByBizKey(source, pgroupInfo.getParentId(), bizKey);
						if (wyReBizAct == null) {
							// 如果分公司不存在,查询集团公司
							GroupInfo ppgroupInfo = UserUtils.getGroup(pgroupInfo.getParentId(), source);
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