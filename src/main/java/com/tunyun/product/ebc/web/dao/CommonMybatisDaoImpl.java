package com.tunyun.product.ebc.web.dao;

import com.common.system.jdbc.jpa.JPABaseDAO;

import java.util.List;
import java.util.Map;

/**
 * Created by shiliang on 2016/12/23.
 */
public class CommonMybatisDaoImpl extends JPABaseDAO implements CommonMybatisDao {
    @Override
    public List<Map> query(String NS, String selectId, Map params) {
        return this.getSqlSessionTemplate().selectList(NS + "." + selectId, params);
    }
}
