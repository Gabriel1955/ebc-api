package com.tunyun.product.ebc.web.server;

import com.tunyun.product.ebc.common.dao.CommonDao;
import com.tunyun.product.ebc.web.common.CommonUtil;
import com.tunyun.product.ebc.web.dao.CommonMybatisDao;
import net.sf.json.JSONObject;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shiliang
 * @version 1.0
 * @category com.tunyun.product.ebc.bgmanager.serv
 * @since 2016年11月29日 下午1:57:46
 */
public class BaseServ {
    private static long DAY_INTERVAL_SECONDS = 60 * 60 * 24L;
    protected PlatformTransactionManager transactionManager;

    private CommonMybatisDao commonMybatisDao;

//    protected Map getResult() {
//        Map result = new HashMap();
//        result.put("pages", pages);
//        result.put("total", total);
//        result.put("list", list);
//        return result;
//    }

    protected JSONObject getParams(String json) {
        JSONObject params = JSONObject.fromObject(json);
        if (!params.has("startTime")) {
            String yesterday = CommonUtil.format("yyyyMMdd", -1);
            long add_time = CommonUtil.formatDataTotimestampForSecond(yesterday, "yyyyMMdd");
            params.put("startTime", add_time);
            params.put("endTime", add_time + DAY_INTERVAL_SECONDS);
        } else {
            params.put("endTime", Long.parseLong(String.valueOf(params.get("startTime"))) + DAY_INTERVAL_SECONDS);
        }
        if (!params.has("pageNo")) {
            params.put("pageNo", 1);
        }
        if (!params.has("pageSize")) {
            params.put("pageSize", 20);
        }
        if (params.has("keyword") && !"".equals(params.getString("keyword"))) {
            params.put("keyword", "%" + params.getString("keyword") + "%");
        }
        if (params.has("pageNo") && params.has("pageSize")) {
            params.put("limit", params.getInt("pageSize"));
            params.put("offset", (params.getInt("pageNo") - 1) * params.getInt("pageSize"));
        }
        return params;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public CommonMybatisDao getCommonMybatisDao() {
        return commonMybatisDao;
    }

    public void setCommonMybatisDao(CommonMybatisDao commonMybatisDao) {
        this.commonMybatisDao = commonMybatisDao;
    }
}