package com.tunyun.product.ebc.bgmanager.dao;

import java.util.List;
import java.util.Map;

import com.common.system.jdbc.jpa.JPABaseDAO;
import com.tunyun.product.ebc.bgmanager.po.WordFeaturePO;

/**
 * @author shiliang
 * @version 1.0
 * @category com.tunyun.product.ebc.bgmanager.dao
 * @since 2016年11月28日 下午3:28:23
 */
public class TrWordDaoImpl extends JPABaseDAO implements TrWordDao {
    private final String NS = TrWordDaoImpl.class.getName();

    @Override
    public long countWordFeatures(Map params) {
        return this.getSqlSessionTemplate().selectOne(NS + ".countWordFeatures", params);
    }

    @Override
    public List<WordFeaturePO> queryWordFeaturesByPage(Map params) {
        return this.getSqlSessionTemplate().selectList(NS + ".queryWordFeaturesByPage", params);
    }

    @Override
    public void batchUpdateWordFeatures(List<Map> list) {
        this.getSqlSessionTemplate().batchUpdate(NS + ".updateWordFeature", list);
    }

}
