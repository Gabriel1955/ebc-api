package com.tunyun.product.ebc.web.dao;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by shiliang on 2016/12/13.
 */
public interface NewProdDao {
    long countProgrammes(Map params);

    List<Map> queryProgrammesByPage(Map params);

    List<Map> queryTrConfigTypes(Map params);

    long countProgrammeAttrs(Map params);

    List<Map> queryProgrammeAttrsByPage(Map params);

    List<Map> queryTimeList(Map params);

    long countProgrammeTypes(Map params);

    List<Map> queryProgrammeTypesByPage(Map params);
}
