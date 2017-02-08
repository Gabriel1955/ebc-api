package com.tunyun.product.ebc.web.server;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by shiliang on 2016/12/23.
 */
public class CommonReqServ extends BaseServ {
    public String queryTrIndu(String inputStr) {
        JSONObject params = getParams(inputStr);
        List<Map> list = getCommonMybatisDao().query("com.tunyun.product.ebc.web.dao.Common","queryTrIndu",params);
        return JSONArray.fromObject(list).toString();
    }

    public String queryCatesByIndu(String inputStr) {
        JSONObject params = getParams(inputStr);
        List<Map> list = getCommonMybatisDao().query("com.tunyun.product.ebc.web.dao.Common","queryCatesByIndu",params);
        return JSONArray.fromObject(list).toString();
    }
}
