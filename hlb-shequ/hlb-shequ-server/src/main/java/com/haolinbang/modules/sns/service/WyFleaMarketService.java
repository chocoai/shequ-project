package com.haolinbang.modules.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.service.CrudService;
import com.haolinbang.modules.sns.dao.WyFleaMarketDao;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.WyDianzanRecord;
import com.haolinbang.modules.sns.entity.WyFleaMarket;
import com.haolinbang.modules.sns.util.MemberUtils;

/**
 * 跳蚤市场Service
 * 
 * @author nlp
 * @version 2017-07-07
 */
@Service
@Transactional(readOnly = true)
public class WyFleaMarketService extends CrudService<WyFleaMarketDao, WyFleaMarket> {
	
	@Autowired
	private WyFleaMarketDao wyFleaMarketDao;

	@Autowired
	private WyDianzanRecordService wyDianzanRecordService;

	public WyFleaMarket get(String id) {
		return super.get(id);
	}

	public List<WyFleaMarket> findList(WyFleaMarket wyFleaMarket) {
		return super.findList(wyFleaMarket);
	}

	public Page<WyFleaMarket> findPage(Page<WyFleaMarket> page, WyFleaMarket wyFleaMarket) {
		return super.findPage(page, wyFleaMarket);
	}

	@Transactional(readOnly = false)
	public void save(WyFleaMarket wyFleaMarket) {
		super.save(wyFleaMarket);
	}

	@Transactional(readOnly = false)
	public void delete(WyFleaMarket wyFleaMarket) {
		super.delete(wyFleaMarket);
	}

	
	@Transactional(readOnly = false)
	public WeJson dianzan(WyDianzanRecord wyDianzanRecord) {

		if (wyDianzanRecord.getRelationId() == null || wyDianzanRecord.getRelationId() == 0) {
			return WeJson.fail("关联的id为空");
		}

		Member member = MemberUtils.getCurrentMember();

		wyDianzanRecord.setBizType("flea_market");
		wyDianzanRecord.setDianzanType("1");
		wyDianzanRecord.setMemberId(member.getMemberId());

		// 查询是否已经点赞
		WyDianzanRecord wyDianzanRecord2 = wyDianzanRecordService.getWyDianzanRecordByRelationId(wyDianzanRecord);
		WyFleaMarket wyFleaMarket = this.get(wyDianzanRecord.getRelationId().toString());
		if (wyDianzanRecord2 != null) {
			// 如果已经点赞，改为不点赞，否则改为已经点赞
			if (wyDianzanRecord2.getState() != null && wyDianzanRecord2.getState()) {
				wyDianzanRecord2.setState(false);

				// 将点赞数量减1
				int num = wyFleaMarket.getDianzanNum() == null ? 0 : wyFleaMarket.getDianzanNum();
				num = (num <= 0) ? 0 : (num - 1);

				wyFleaMarket.setDianzanNum(num);
			} else {
				wyDianzanRecord2.setState(true);

				// 将点赞数量加1
				int num = wyFleaMarket.getDianzanNum() == null ? 0 : wyFleaMarket.getDianzanNum();
				num += 1;
				wyFleaMarket.setDianzanNum(num);
			}
			wyDianzanRecordService.save(wyDianzanRecord2);
		} else {
			// 新建点赞记录
			wyDianzanRecord.setState(true);
			wyDianzanRecordService.save(wyDianzanRecord);

			int num = wyFleaMarket.getDianzanNum() == null ? 0 : wyFleaMarket.getDianzanNum();
			num += 1;
			wyFleaMarket.setDianzanNum(num);
		}
		this.save(wyFleaMarket);
		return WeJson.success(wyFleaMarket.getDianzanNum());

	}

	@Transactional(readOnly = false)
	public void updates(WyFleaMarket wyFleaMarket) {
		wyFleaMarketDao.updates(wyFleaMarket);
	}

}