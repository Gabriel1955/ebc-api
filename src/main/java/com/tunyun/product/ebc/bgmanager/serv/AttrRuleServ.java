package com.tunyun.product.ebc.bgmanager.serv;

import com.tunyun.product.ebc.bgmanager.dao.AttrRuleDao;
import com.tunyun.product.ebc.bgmanager.po.AttrRulePO;
import com.tunyun.product.ebc.common.util.Result;
import com.tunyun.product.ebc.web.common.Page;
import com.tunyun.product.ebc.web.server.BaseServ;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

/**
 * @author shiliang
 * @version 1.0
 * @category com.tunyun.product.ebc.bgmanager.serv
 * @since 2016年11月4日 下午3:28:51
 */
public class AttrRuleServ extends BaseServ {

    private static Logger logger = LoggerFactory.getLogger(AttrRuleServ.class);
    private AttrRuleDao attrRuleDao;

    public String queryAttrRules(String input) {
        JSONObject jsonObj = JSONObject.fromObject(input);
        try {
            List<AttrRulePO> list = attrRuleDao.queryAttrRules(jsonObj);
            return JSONObject.fromObject(new Result("1", "查询成功", list)).toString();
        } catch (Exception e) {
            logger.error("queryApproveAttrs error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }

    }

    public String saveAttrRules(String input) {
        try {
            JSONObject jsonObj = JSONObject.fromObject(input);
            logger.info("=================>save:{}", jsonObj);
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallback<Object>() {
                @Override
                public Object doInTransaction(TransactionStatus status) {
                    List<Map> dellist = new ArrayList<Map>();
                    dellist.add(jsonObj);
                    logger.info("=================>delete:{}", dellist);
                    attrRuleDao.batchDelAttrRules(dellist);
                    List<Map> insertList = new ArrayList<Map>();
                    JSONArray preList = jsonObj.getJSONArray("preRules");
                    Iterator<Object> it = preList.iterator();
                    int index = 0;
                    while (it.hasNext()) {
                        JSONObject ob = (JSONObject) it.next();
                        if (ob.has("expr") && !"".equals(ob.getString("expr"))) {
                            Map param = new HashMap();
                            param.put("cid", jsonObj.getString("cid"));
                            param.put("chan_id", jsonObj.getString("chan_id"));
                            param.put("p_name", jsonObj.getString("p_name"));
                            param.put("expr", ob.has("expr") ? ob.getString("expr") : "");
                            param.put("replace_str", ob.has("replace_str") ? ob.getString("replace_str") : "");
                            param.put("index", index);
                            param.put("type", 1);
                            insertList.add(param);
                            index++;
                        }
                    }

                    JSONArray afterList = jsonObj.getJSONArray("afterRules");
                    it = afterList.iterator();
                    index = 0;
                    while (it.hasNext()) {
                        JSONObject ob = (JSONObject) it.next();
                        if (ob.has("expr") && !"".equals(ob.getString("expr"))) {
                            Map param = new HashMap();
                            param.put("cid", jsonObj.getString("cid"));
                            param.put("chan_id", jsonObj.getString("chan_id"));
                            param.put("p_name", jsonObj.getString("p_name"));
                            param.put("expr", ob.has("expr") ? ob.getString("expr") : "");
                            param.put("replace_str", ob.has("replace_str") ? ob.getString("replace_str") : "");
                            param.put("index", index);
                            param.put("type", 2);
                            insertList.add(param);
                            index++;
                        }
                    }
                    logger.info("=================>insert:{}", insertList);
                    attrRuleDao.batchInsertAttrRules(insertList);
                    return null;
                }
            });
            return JSONObject.fromObject(new Result("1", "保存成功", null)).toString();
        } catch (Exception e) {
            logger.error("saveAttrRules error:{}", e);
            return JSONObject.fromObject(new Result("0", "保存失败", null)).toString();
        }
    }

    public String clearAttrRules(String input) {
        try {
            JSONObject jsonObj = JSONObject.fromObject(input);
            logger.info("=================>clear:{}", jsonObj);
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallback<Object>() {
                @Override
                public Object doInTransaction(TransactionStatus status) {
                    List<Map> dellist = new ArrayList<Map>();
                    dellist.add(jsonObj);
                    logger.info("=================>delete:{}", dellist);
                    attrRuleDao.batchDelAttrRules(dellist);
                    return null;
                }
            });
            return JSONObject.fromObject(new Result("1", "清空成功", null)).toString();
        } catch (Exception e) {
            logger.error("clearAttrRules error:{}", e);
            return JSONObject.fromObject(new Result("0", "清空失败", null)).toString();
        }
    }

    /**
     * /home/op/process/ebc/ResStandard nohup detailTest.sh 50200000 品牌
     * 1478481324 &
     * "/home/op/process/ebc/ResStandard/detailTest.sh " + jsonObj.getString("cid") + " "
     * + jsonObj.getString("p_name") + " " + jsonObj.getLong("time")+ " " + jsonObj.getInt("chan_id")
     *
     * @param input
     * @return
     */
    public String runShell(String input) {
        try {
            JSONObject jsonObj = JSONObject.fromObject(input);
            logger.info("=================>runShell:{}", jsonObj);
            Runtime.getRuntime().exec(jsonObj.getString("shell"));
            return JSONObject.fromObject(new Result("1", "脚本调用成功", null)).toString();
        } catch (Exception e) {
            logger.error("runShell error:{}", e);
            return JSONObject.fromObject(new Result("0", "脚本调用失败", null)).toString();
        }
    }

    public String testQueryAttrRules(String input) {
        try {
            JSONObject jsonObj = getParams(input);
            logger.info("=================>testQueryAttrRules:{}", jsonObj);
            if (!"".equals(jsonObj.getString("p_value"))) {
                jsonObj.put("p_value", "%" + jsonObj.getString("p_value") + "%");
            }
            long total = attrRuleDao.countTestDatas(jsonObj);
            List<Map> list = attrRuleDao.queryTestDatas(jsonObj);
            Page page = new Page(jsonObj.getInt("pageSize"), jsonObj.getInt("pageNo"), total, list);
            logger.info("查询测试数据===>{}", page);
            return JSONObject.fromObject(new Result("1", "查询成功", page)).toString();
        } catch (Exception e) {
            logger.error("testQueryAttrRules error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }
    }


    public String configQueryAttrRules(String input) {
        JSONObject jsonObj = JSONObject.fromObject(input);
        try {
            List<Map> list = attrRuleDao.configQueryAttrRules(jsonObj);
            return JSONObject.fromObject(new Result("1", "查询成功", list)).toString();
        } catch (Exception e) {
            logger.error("configQueryAttrRules error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }
    }

    public String configSaveAttrRules(String input) {
        // TODO Auto-generated method stub
        try {
            JSONArray jsonArray = JSONArray.fromObject(input);
            logger.info("=================>save:{}", jsonArray);
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallback<Object>() {
                @Override
                public Object doInTransaction(TransactionStatus status) {
                    List<Map> updateList = new ArrayList<Map>();
                    List<Map> insertList = new ArrayList<Map>();
                    Iterator<Object> it = jsonArray.iterator();
                    while (it.hasNext()) {
                        JSONObject ob = (JSONObject) it.next();
                        List<Map> trModelTests = attrRuleDao.queryTrModelTests(ob);
                        if (trModelTests != null && trModelTests.size() > 0) {
                            updateList.add(ob);
                        } else {
                            insertList.add(ob);
                        }
                    }
                    if (updateList.size() > 0) {
                        attrRuleDao.batchUpdateTrModelTest(updateList);
                    }
                    if (insertList.size() > 0) {
                        attrRuleDao.batchInsertTrModelTest(insertList);
                    }

                    return null;
                }
            });
            return JSONObject.fromObject(new Result("1", "保存成功", null)).toString();
        } catch (Exception e) {
            logger.error("saveAttrRules error:{}", e);
            return JSONObject.fromObject(new Result("0", "保存失败", null)).toString();
        }
    }

    public String regtestQueryAttrRules(String input) {
        try {
            JSONObject jsonObj = getParams(input);
            logger.info("=================>regtestQueryAttrRules:{}", jsonObj);
            if (!"".equals(jsonObj.getString("p_value"))) {
                jsonObj.put("p_value", "%" + jsonObj.getString("p_value") + "%");
            }
            long total = attrRuleDao.countTrValueAuditDatas(jsonObj);
            List<Map> list = attrRuleDao.queryTrValueAuditDatas(jsonObj);
            Page page = new Page(jsonObj.getInt("pageSize"), jsonObj.getInt("pageNo"), total, list);
            logger.info("查询数据===>{}", page);
            return JSONObject.fromObject(new Result("1", "查询成功", page)).toString();
        } catch (Exception e) {
            logger.error("regtestQueryAttrRules error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }
    }

    public String queryAttrs(String input) {
        JSONObject jsonObj = JSONObject.fromObject(input);
        try {
            List<Map> list = attrRuleDao.queryAttrs(jsonObj);
            return JSONObject.fromObject(new Result("1", "查询成功", list)).toString();
        } catch (Exception e) {
            logger.error("configQueryAttrRules error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }
    }


    public AttrRuleDao getAttrRuleDao() {
        return attrRuleDao;
    }

    public void setAttrRuleDao(AttrRuleDao attrRuleDao) {
        this.attrRuleDao = attrRuleDao;
    }

}
