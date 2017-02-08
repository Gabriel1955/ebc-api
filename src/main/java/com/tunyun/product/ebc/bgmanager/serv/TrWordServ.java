package com.tunyun.product.ebc.bgmanager.serv;

import java.util.*;

import com.tunyun.product.ebc.common.util.RemoteShellTool;
import com.tunyun.product.ebc.web.common.Page;
import com.tunyun.product.ebc.web.server.BaseServ;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.tunyun.product.ebc.bgmanager.dao.TrWordDao;
import com.tunyun.product.ebc.bgmanager.po.WordFeaturePO;
import com.tunyun.product.ebc.common.util.Result;

import net.sf.json.JSONObject;

/**
 * @author shiliang
 * @version 1.0
 * @category com.tunyun.product.ebc.bgmanager.serv
 * @since 2016年11月28日 下午3:28:44
 */
public class TrWordServ extends BaseServ {
    private static Logger logger = LoggerFactory.getLogger(TrWordServ.class);
    private TrWordDao trWordDao;

    public TrWordDao getTrWordDao() {
        return trWordDao;
    }

    public void setTrWordDao(TrWordDao trWordDao) {
        this.trWordDao = trWordDao;
    }

    public String queryWordFeatures(String input) {
        try {
            JSONObject jsonObj = getParams(input);
            logger.info("查询特征词====>{}", jsonObj);
            List<WordFeaturePO> list = trWordDao.queryWordFeaturesByPage((Map) jsonObj);
            Page page = new Page(jsonObj.getInt("pageSize"), jsonObj.getInt("pageNo"), trWordDao.countWordFeatures(jsonObj), list);
            logger.info("查询特征词====>{}", page);
            return JSONObject.fromObject(new Result("1", "查询成功", page)).toString();
        } catch (NumberFormatException e) {
            logger.error("查询特征词 error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }
    }

    public String saveWordFeatures(String input) {
        try {
            logger.info("审核 params====>{}", input);
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallback<Object>() {

                @Override
                public Object doInTransaction(TransactionStatus status) {
                    JSONObject jsonObj = getParams(input);
                    String params = jsonObj.getString("params");
                    if (!"".equals(params.trim())) {
                        String[] wordFeatures = params.split("##");
                        List<Map> list = new ArrayList<Map>();
                        Map param;
                        for (String wordFeature : wordFeatures) {
                            String[] a = wordFeature.split("&&");
                            param = new HashMap();
                            param.put("n_id", Long.parseLong(a[0]));
                            param.put("pid", Long.parseLong(a[1]));
                            param.put("check_result", Long.parseLong(a[2]));
                            param.put("check_time", new Date().getTime() / 1000L);
                            param.put("is_check", 1);
                            list.add(param);
                        }
                        logger.info("审核==={}", list);
                        trWordDao.batchUpdateWordFeatures(list);
                    }

                    return null;
                }
            });
            return JSONObject.fromObject(new Result("1", "提交成功", null)).toString();
        } catch (Exception e) {
            logger.error("审核 Exception===>", e);
            return JSONObject.fromObject(new Result("0", "提交失败", e.getMessage())).toString();
        }
    }

    public String delWordFeatures(String input) {
        try {
            logger.info("删除 params====>{}", input);
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallback<Object>() {

                @Override
                public Object doInTransaction(TransactionStatus status) {
                    JSONObject jsonObj = getParams(input);
                    String params = jsonObj.getString("params");
                    if (!"".equals(params.trim())) {
                        String[] wordFeatures = params.split("##");
                        List<Map> list = new ArrayList<Map>();
                        Map param;
                        for (String wordFeature : wordFeatures) {
                            String[] a = wordFeature.split("&&");
                            param = new HashMap();
                            param.put("n_id", Long.parseLong(a[0]));
                            param.put("pid", -1);
                            param.put("check_result", 0);
                            param.put("check_time", 0);
                            param.put("is_check", 0);
                            list.add(param);
                        }
                        logger.info("删除==={}", list);
                        trWordDao.batchUpdateWordFeatures(list);
                    }
                    return null;
                }
            });
            return JSONObject.fromObject(new Result("1", "提交成功", null)).toString();
        } catch (Exception e) {
            logger.error("审核 Exception===>", e);
            return JSONObject.fromObject(new Result("0", "提交失败", e.getMessage())).toString();
        }
    }


    public String updateWordFeatures(String input) {
        try {
            logger.info("修改 params====>{}", input);
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallback<Object>() {
                @Override
                public Object doInTransaction(TransactionStatus status) {
                    JSONObject jsonObj = getParams(input);
                    String params = jsonObj.getString("params");
                    if (!"".equals(params.trim())) {
                        String[] wordFeatures = params.split("##");
                        List<Map> list = new ArrayList<Map>();
                        Map param;
                        for (String wordFeature : wordFeatures) {
                            String[] a = wordFeature.split("&&");
                            param = new HashMap();
                            param.put("n_id", Long.parseLong(a[0]));
                            param.put("pid", Long.parseLong(a[1]));
                            param.put("check_result", Long.parseLong(a[2]));
                            param.put("check_time", new Date().getTime() / 1000L);
                            list.add(param);
                        }
                        logger.info("修改==={}", list);
                        trWordDao.batchUpdateWordFeatures(list);
                    }
                    return null;
                }
            });
            return JSONObject.fromObject(new Result("1", "修改成功", null)).toString();
        } catch (Exception e) {
            logger.error("修改 Exception===>", e);
            return JSONObject.fromObject(new Result("0", "修改失败", e.getMessage())).toString();
        }
    }

    public String shell(String input) {
        JSONObject jsonObj = getParams(input);
        JSONArray params = jsonObj.getJSONArray("params");
        Iterator<Object> it = params.iterator();
        String paramStr = " ";
        while (it.hasNext()) {
            JSONObject ob = (JSONObject) it.next();
            paramStr += ob.getString("value") + " ";
        }
        logger.info("执行参数：" + paramStr);
        RemoteShellTool tool = new RemoteShellTool("crawler-1", "crawler",
                "*KIEifa2fsf9(*LEFAVCXDefoiod", "utf-8");
        tool.exec("/home/crawler/spark_test/" + jsonObj.getString("id") + paramStr);
        return JSONObject.fromObject(new Result("1", "提交成功", null)).toString();
    }
}
