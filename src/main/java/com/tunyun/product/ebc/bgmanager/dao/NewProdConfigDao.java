package com.tunyun.product.ebc.bgmanager.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by shiliang on 2016/12/22.
 */
public interface NewProdConfigDao {
    List<Map> query(Map params);

    long count(Map params);

    int getMaxTypeId();


    void batchInsert(List<Map> insertList);

    void batchDelete(List<Map> deleteList);
}
