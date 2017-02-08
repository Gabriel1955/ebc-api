package com.tunyun.product.ebc.web.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by shiliang on 2016/12/23.
 */
public interface CommonMybatisDao {
    List<Map> query(String NS, String selectId, Map params);
}
