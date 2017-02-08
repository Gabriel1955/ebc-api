package com.tunyun.product.ebc.web.dao;

import com.common.system.jdbc.jpa.JPABaseDAO;

import java.util.List;
import java.util.Map;

/**
 * Created by shiliang on 2016/12/13.
 */
public class NewProdDaoImpl extends JPABaseDAO implements NewProdDao{
    private final String NS = NewProdDaoImpl.class.getName();

    @Override
    public long countProgrammes(Map params) {
        return this.getSqlSessionTemplate().selectOne(NS + ".countProgrammes", params);
    }

    @Override
    public List<Map> queryProgrammesByPage(Map params) {
        return this.getSqlSessionTemplate().selectList(NS + ".queryProgrammesByPage", params);
    }

    @Override
    public List<Map> queryTrConfigTypes(Map params) {
        return this.getSqlSessionTemplate().selectList(NS + ".queryTrConfigTypes", params);
    }

    @Override
    public long countProgrammeAttrs(Map params) {
        return this.getSqlSessionTemplate().selectOne(NS + ".countProgrammeAttrs", params);
    }

    @Override
    public List<Map> queryProgrammeAttrsByPage(Map params) {
        return this.getSqlSessionTemplate().selectList(NS + ".queryProgrammeAttrsByPage", params);
    }

    @Override
    public List<Map> queryTimeList(Map params) {
        return this.getSqlSessionTemplate().selectList(NS + ".queryTimeList", params);
    }

    @Override
    public long countProgrammeTypes(Map params) {
        return this.getSqlSessionTemplate().selectOne(NS + ".countProgrammeTypes", params);
    }

    @Override
    public List<Map> queryProgrammeTypesByPage(Map params) {
        return this.getSqlSessionTemplate().selectList(NS + ".queryProgrammeTypesByPage", params);
    }
}
