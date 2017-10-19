package com.haolinbang.modules.sns.dao;

import com.haolinbang.common.persistence.CrudDao;
import com.haolinbang.common.persistence.annotation.MyBatisDao;
import com.haolinbang.modules.sns.entity.WyInstCandidate;

/**
 * 运行期间待办人DAO接口
 * @author nlp
 * @version 2017-06-05
 */
@MyBatisDao
public interface WyInstCandidateDao extends CrudDao<WyInstCandidate> {
	
}