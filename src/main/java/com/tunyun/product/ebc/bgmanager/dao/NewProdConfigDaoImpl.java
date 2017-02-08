package com.tunyun.product.ebc.bgmanager.dao;

import com.common.system.jdbc.jpa.JPABaseDAO;

import java.util.List;
import java.util.Map;

/**
 * Created by shiliang on 2016/12/22.
 */
public class NewProdConfigDaoImpl extends JPABaseDAO implements NewProdConfigDao {

    private final String NS = NewProdConfigDaoImpl.class.getName();

    @Override
    public List<Map> query(Map params) {
        return this.getSqlSessionTemplate().selectList(NS+".query",params);
    }

    @Override
    public long count(Map params) {
        return this.getSqlSessionTemplate().selectOne(NS + ".count", params);
    }

    @Override
    public int getMaxTypeId() {
        return this.getSqlSessionTemplate().selectOne(NS + ".getMaxTypeId");
    }

    @Override
    public void batchInsert(List<Map> insertList) {
        this.getSqlSessionTemplate().batchInsert(NS+".insert",insertList);
    }

    @Override
    public void batchDelete(List<Map> deleteList) {
        this.getSqlSessionTemplate().batchDelete(NS+".delete",deleteList);
    }
}
