package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.dao.WyHouseRentDao;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.WyDianzanRecord;
import com.haolinbang.modules.sns.entity.WyHouseRent;
import com.haolinbang.modules.sns.util.MemberUtils;

/**
 * 房屋租售Service
 * 
 * @author nlp
 * @version 2017-07-07
 */
@Service
@Transactional(readOnly = true)
public class WyHouseRentService extends CrudService<WyHouseRentDao, WyHouseRent> {

	@Autowired
	private WyHouseRentDao wyHouseRentDao;

	@Autowired
	private WyDianzanRecordService wyDianzanRecordService;

	public WyHouseRent get(String id) {
		return super.get(id);
	}

	public List<WyHouseRent> findList(WyHouseRent wyHouseRent) {
		return super.findList(wyHouseRent);
	}

	public Page<WyHouseRent> findPage(Page<WyHouseRent> page, WyHouseRent wyHouseRent) {
		return super.findPage(page, wyHouseRent);
	}

	@Transactional(readOnly = false)
	public void save(WyHouseRent wyHouseRent) {
		super.save(wyHouseRent);
	}

	@Transactional(readOnly = false)
	public void delete(WyHouseRent wyHouseRent) {
		super.delete(wyHouseRent);
	}

	/**
	 * 点赞,用户未点赞，新建点赞记录；已经点赞的，取消点赞状态
	 * 
	 * @param wyDianzanRecord
	 * @return
	 */
	@Transactional(readOnly = false)
	public WeJson dianzan(WyDianzanRecord wyDianzanRecord) {
		if (wyDianzanRecord.getRelationId() == null || wyDianzanRecord.getRelationId() == 0) {
			return WeJson.fail("关联的id为空");
		}

		Member member = MemberUtils.getCurrentMember();

		wyDianzanRecord.setBizType("house_rent");
		wyDianzanRecord.setDianzanType("1");
		wyDianzanRecord.setMemberId(member.getMemberId());

		// 查询是否已经点赞
		WyDianzanRecord wyDianzanRecord2 = wyDianzanRecordService.getWyDianzanRecordByRelationId(wyDianzanRecord);
		WyHouseRent wyHouseRent = this.get(wyDianzanRecord.getRelationId().toString());
		if (wyDianzanRecord2 != null) {
			// 如果已经点赞，改为不点赞，否则改为已经点赞
			if (wyDianzanRecord2.getState() != null && wyDianzanRecord2.getState()) {
				wyDianzanRecord2.setState(false);

				// 将点赞数量减1
				int num = wyHouseRent.getDianzanNum() == null ? 0 : wyHouseRent.getDianzanNum();
				num = (num <= 0) ? 0 : (num - 1);

				wyHouseRent.setDianzanNum(num);
			} else {
				wyDianzanRecord2.setState(true);

				// 将点赞数量加1
				int num = wyHouseRent.getDianzanNum() == null ? 0 : wyHouseRent.getDianzanNum();
				num += 1;
				wyHouseRent.setDianzanNum(num);
			}
			wyDianzanRecordService.save(wyDianzanRecord2);
		} else {
			// 新建点赞记录
			wyDianzanRecord.setState(true);
			wyDianzanRecordService.save(wyDianzanRecord);

			int num = wyHouseRent.getDianzanNum() == null ? 0 : wyHouseRent.getDianzanNum();
			num += 1;
			wyHouseRent.setDianzanNum(num);
		}
		this.save(wyHouseRent);
		return WeJson.success(wyHouseRent.getDianzanNum());
	}

	public WyDianzanRecord getWyDianzanRecordByRelationId(WyDianzanRecord wyDianzanRecord) {
		return wyHouseRentDao.getWyDianzanRecordByRelationId(wyDianzanRecord);
	}

	public List<WyHouseRent> getWyHouseRentList(WyHouseRent wyHouseRent) {
		return wyHouseRentDao.getWyHouseRentList(wyHouseRent);
	}

	@Transactional(readOnly = false)
	public void updates(WyHouseRent wyHouseRent) {
		wyHouseRentDao.updates(wyHouseRent);
	}

}