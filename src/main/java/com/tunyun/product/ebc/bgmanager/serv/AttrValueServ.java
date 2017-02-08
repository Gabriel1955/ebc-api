package com.tunyun.product.ebc.bgmanager.serv;

import com.tunyun.product.ebc.bgmanager.dao.AttrValueDao;
import com.tunyun.product.ebc.bgmanager.po.AliasPO;
import com.tunyun.product.ebc.bgmanager.po.AttrValuePO;
import com.tunyun.product.ebc.common.util.Result;
import com.tunyun.product.ebc.web.common.Page;
import com.tunyun.product.ebc.web.server.BaseServ;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.hadoop.yarn.webapp.view.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

/**
 * 属性及属性值服务类
 *
 * @author shiliang
 * @version 1.0
 * @category com.tunyun.product.ebc.bgmanager.serv
 * @since 2016年9月27日 下午9:02:27
 */
public class AttrValueServ extends BaseServ {

    private static Logger logger = LoggerFactory.getLogger(AttrValueServ.class);
    private AttrValueDao attrValueDao;


    /**
     * 查询属性值
     *
     * @param json
     * @return
     */
    public String queryApproveAttrValues(String json) {
        // JSON转换
        try {
            JSONObject jsonObj = getParams(json);
            logger.info("查询属性值params====>{}", jsonObj);
            List<AttrValuePO> approvedList = attrValueDao.queryApprovedAttrValues((Map) jsonObj);
            List<AttrValuePO> notApprovedList = attrValueDao.queryNotApprovedAttrValues((Map) jsonObj);
            logger.info("查询属性值 已审核.size====>{}", approvedList.size());
            logger.info("查询属性值 未审核.size====>{}", notApprovedList.size());
            Map result = new HashMap();
            result.put("查询属性值approvedAttr", approvedList);
            result.put("查询属性值notApprovedAttr", notApprovedList);
            return JSONObject.fromObject(new Result("1", "查询成功", result)).toString();
        } catch (NumberFormatException e) {
            logger.error("查询属性值 error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }
    }

    /**
     * 查询属性值详情
     *
     * @param json
     * @return
     */
    public String queryAttrValueDetails(String json) {
        // JSON转换
        try {
            JSONObject jsonObj = getParams(json);
            logger.info("查询详情params====>{}", jsonObj);
            List<Map> list = attrValueDao.queryAttrValueDetails((Map) jsonObj);
            logger.info("查询详情 list.size====>{}", list.size());
            return JSONObject.fromObject(new Result("1", "查询详情成功", list)).toString();
        } catch (NumberFormatException e) {
            logger.error("查询详情 error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询详情失败", null)).toString();
        }
    }

    /**
     * 删除属性值
     *
     * @param json
     * @return
     */
    public String delAttrValue(String json) {
        try {
            logger.info("删除,params====>{}", json);
            JSONObject jsonObj = getParams(json);
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallback<Object>() {

                @Override
                public Object doInTransaction(TransactionStatus status) {
                    String params = jsonObj.getString("params");
                    if (!"".equals(params.trim())) {
                        String[] attrvalues = params.split("##");
                        List<Map> list = new ArrayList<Map>();
                        Map param;
                        List<Map> v_alias = new ArrayList<Map>();
                        AttrValuePO po = null;
                        for (String attrvalue : attrvalues) {
                            String[] a = attrvalue.split("&&");
                            param = new HashMap();
                            param.put("cid", a[0]);
                            param.put("pid", a[1]);
                            param.put("v_name", a[2]);
                            param.put("state", 0);
                            param.put("startTime", jsonObj.get("startTime"));
                            param.put("endTime", jsonObj.get("endTime"));
                            list.add(param);
                            List<AttrValuePO> notApproved = attrValueDao.queryNotApprovedAttrValues(param);
                            if (notApproved.size() > 0) {
                                po = notApproved.get(0);
                            }
                            for (AliasPO valias : po.getValias()) {
                                Map valiasMap = new HashMap();
                                valiasMap.put("cid", param.get("cid"));
                                valiasMap.put("pid", param.get("pid"));
                                valiasMap.put("vid", valias.getId());
                                valiasMap.put("v_name", param.get("v_name"));
                                valiasMap.put("state", 0);
                                valiasMap.put("v_alias", valias.getName());
                                valiasMap.put("addTime", new Date().getTime() / 1000L);
                                v_alias.add(valiasMap);
                            }
                        }
                        logger.info("删除, list==={}", list);
                        logger.info("删除, v_alias==={}", v_alias);
                        attrValueDao.batchUpdateSynonym(list);
                        attrValueDao.batchInsertPVModel(v_alias);
                    }

                    String params1 = jsonObj.getString("params1");
                    if (!"".equals(params1.trim())) {
                        String[] attrvalues1 = params1.split("##");
                        List<Map> list1 = new ArrayList<Map>();
                        List<Map> v_alias1 = new ArrayList<Map>();
                        for (String attrvalue : attrvalues1) {
                            String[] a = attrvalue.split("&&");
                            String[] valias = a[4].split(",");
                            Map param1 = new HashMap();
                            param1.put("cid", a[0]);
                            param1.put("pid", a[1]);
                            param1.put("state", 1);
                            param1.put("vid", a[3]);
                            param1.put("v_alias", a[2]);
                            param1.put("v_name", a[2]);
                            list1.add(param1);
                            for (String v : valias) {
                                Map valiasparam1 = new HashMap();
                                valiasparam1.put("cid", a[0]);
                                valiasparam1.put("pid", a[1]);
                                valiasparam1.put("state", 0);
                                valiasparam1.put("vid", a[3]);
                                valiasparam1.put("v_alias", v);
                                valiasparam1.put("v_name", a[2]);
                                v_alias1.add(valiasparam1);
                            }
                        }
                        logger.info("删除 ,list1==={}", list1);
                        logger.info("删除,v_alias1==={}", v_alias1);

                        attrValueDao.batchDeletePV(list1);
                        // 品类与属性值关联表
                        attrValueDao.batchDeletePVModel(v_alias1);
                        attrValueDao.batchUpdateSynonym1(v_alias1);
                    }

                    return null;
                }
            });
            return JSONObject.fromObject(new Result("1", "删除成功", null)).toString();
        } catch (Exception e) {
            logger.error("删除,Exception===>", e);
            return JSONObject.fromObject(new Result("0", "删除失败", e.getMessage())).toString();
        }
    }

    /**
     * 合并属性值
     *
     * @param json
     * @return
     */
    public String mergeAttrValue(String json) {
        try {
            logger.info("合并, params====>{}", json);
            JSONObject jsonObj = getParams(json);
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallback<Object>() {

                @Override
                public Object doInTransaction(TransactionStatus status) {
                    String params = jsonObj.getString("params");
                    String approved = jsonObj.getString("approved");
                    String vname = jsonObj.getString("vname");
                    String[] attrvalues = params.split("##");
                    Map param;
                    // 已审核合并
                    if (!"".equals(approved.trim())) {
                        List<Map> list = new ArrayList<Map>();
                        String[] a;
                        String[] b = approved.split("&&");
                        List<Map> v_alias = new ArrayList<Map>();
                        AttrValuePO po = null;
                        for (String attrvalue : attrvalues) {
                            a = attrvalue.split("&&");
                            param = new HashMap();
                            param.put("cid", a[0]);
                            param.put("pid", a[1]);
                            param.put("v_name", a[2]);
                            param.put("state", 1);
                            param.put("startTime", jsonObj.get("startTime"));
                            param.put("endTime", jsonObj.get("endTime"));
                            list.add(param);
                            List<AttrValuePO> notApproved = attrValueDao.queryNotApprovedAttrValues(param);
                            if (notApproved.size() > 0) {
                                po = notApproved.get(0);
                            }
                            for (AliasPO valias : po.getValias()) {
                                Map valiasMap = new HashMap();
                                valiasMap.put("cid", b[0]);
                                valiasMap.put("pid", b[1]);
                                valiasMap.put("state", 1);
                                valiasMap.put("vid", b[3]);
                                valiasMap.put("v_alias", valias.getName());
                                valiasMap.put("v_name", b[2]);
                                valiasMap.put("addTime", new Date().getTime() / 1000L);
                                valiasMap.put("startTime", jsonObj.get("startTime"));
                                valiasMap.put("endTime", jsonObj.get("endTime"));
                                v_alias.add(valiasMap);
                            }
                        }
                        logger.info("合并,list==={}", list);
                        logger.info("合并,v_alias==={}", v_alias);
                        attrValueDao.batchInsertPVModel(v_alias);
                        attrValueDao.batchUpdateSynonym(list);
                    } else {// 未审核合并
                        List<Map> list = new ArrayList<Map>();
                        AttrValuePO po = null;
                        for (String attrvalue : attrvalues) {
                            param = new HashMap();
                            String[] a = attrvalue.split("&&");
                            param.put("cid", a[0]);
                            param.put("pid", a[1]);
                            param.put("v_name", vname);
                            param.put("startTime", jsonObj.get("startTime"));
                            param.put("endTime", jsonObj.get("endTime"));

                            List<AttrValuePO> notApproved = attrValueDao.queryNotApprovedAttrValues(param);
                            if (notApproved.size() > 0) {
                                po = notApproved.get(0);
                                param.put("state", po.getState());
                            } else {
                                param.put("state", -3);
                            }
                            param.put("v_name", a[2]);
                            param.put("newvname", vname);
                            param.put("startTime", jsonObj.get("startTime"));
                            param.put("endTime", jsonObj.get("endTime"));
                            list.add(param);
                        }
                        logger.info("合并,{}", list);
                        attrValueDao.batchUpdateSynonym(list);
                    }

                    return null;
                }
            });
            return JSONObject.fromObject(new Result("1", "合并成功", null)).toString();
        } catch (Exception e) {
            logger.error("合并,Exception=>", e.getMessage());
            return JSONObject.fromObject(new Result("0", "合并失败", e.getMessage())).toString();
        }
    }

    /**
     * 拆分属性值
     *
     * @param json
     * @return
     */
    public String splitAttrValue(String json) {
        try {
            logger.info("拆分, params====>{}", json);
            JSONObject jsonObj = getParams(json);
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallback<Object>() {

                @Override
                public Object doInTransaction(TransactionStatus status) {
                    String params = jsonObj.getString("params");
                    String[] attrvalues = params.split("##");
                    List<Map> list = new ArrayList<Map>();
                    Map param;
                    for (String attrvalue : attrvalues) {
                        String[] a = attrvalue.split("&&");
                        param = new HashMap();
                        param.put("cid", a[0]);
                        param.put("pid", a[1]);
                        param.put("v_name", a[2]);
                        List<AttrValuePO> approved = attrValueDao.queryApprovedAttrValues(param);
                        if (approved.size() > 0) {
                            param.put("state", -1);
                        } else {
                            param.put("state", -3);
                        }
                        param.remove("v_name");
                        param.put("v_alias", a[2]);
                        param.put("newvname", a[2]);
                        param.put("startTime", jsonObj.get("startTime"));
                        param.put("endTime", jsonObj.get("endTime"));
                        list.add(param);
                    }
                    logger.info("拆分,{}", list);
                    attrValueDao.batchUpdateSynonym(list);
                    return null;
                }
            });
            return JSONObject
                    .fromObject(new Result("1", "拆分成功", null))
                    .toString();
        } catch (Exception e) {
            logger.error("拆分,Exception=>", e.getMessage());
            return JSONObject.fromObject(new Result("0", "拆分失败", e.getMessage())).toString();
        }
    }

    /**
     * 审核
     *
     * @param json
     * @return
     */
    public String addAttrValue(String json) {
        try {
            logger.info("审核, params====>{}", json);
            JSONObject jsonObj = getParams(json);

            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallback<Object>() {
                @Override
                public Object doInTransaction(TransactionStatus status) {
                    String params = jsonObj.getString("params");
                    if (!"".equals(params.trim())) {
                        String[] attrvalues = params.split("##");
                        List<Map> list = new ArrayList<Map>();
                        List<Map> v_alias = new ArrayList<Map>();
                        long maxvid = attrValueDao.queryMaxVid(jsonObj);
                        logger.info("审核, maxvid====>{}", maxvid);
                        for (String attrvalue : attrvalues) {

                            String[] a = attrvalue.split("&&");
                            String[] valias = a[3].split(",");
                            Map param = new HashMap();
                            param.put("cid", a[0]);
                            param.put("pid", a[1]);
                            param.put("state", 1);
                            param.put("v_alias", a[2]);
                            param.put("v_name", a[2]);
                            List<Map> pvlist = attrValueDao.queryPV(param);
                            if (pvlist.size() <= 0) {
                                param.put("vid", ++maxvid);
                                param.put("addTime", new Date().getTime() / 1000L);
                                param.put("startTime", jsonObj.get("startTime"));
                                param.put("endTime", jsonObj.get("endTime"));
                                list.add(param);
                                // 品类与属性值关联表
                                List<Map> cpvlist = new ArrayList<Map>();
                                Map e = new HashMap();
                                e.put("cid", jsonObj.getString("cid"));
                                e.put("pid", jsonObj.getString("pid"));
                                e.put("vid", maxvid);
                                cpvlist.add(e);
                                attrValueDao.batchInsertCategPV(cpvlist);
                                attrValueDao.batchUpdateMaxVid(cpvlist);
                            }
                            for (String v : valias) {
                                Map valiasparam = new HashMap();
                                valiasparam.put("cid", a[0]);
                                valiasparam.put("pid", a[1]);
                                valiasparam.put("state", 1);
                                valiasparam.put("vid", maxvid);
                                valiasparam.put("v_alias", v);
                                valiasparam.put("v_name", a[2]);
                                valiasparam.put("addTime", new Date().getTime() / 1000L);
                                valiasparam.put("startTime", jsonObj.get("startTime"));
                                valiasparam.put("endTime", jsonObj.get("endTime"));
                                v_alias.add(valiasparam);
                            }
                        }
                        logger.info("审核,state=-3==={}", list);
                        // 属性值表
                        attrValueDao.batchInsertPV(list);
                        attrValueDao.batchInsertPVModel(v_alias);
                        attrValueDao.batchUpdateSynonym(v_alias);
                    }

                    String params1 = jsonObj.getString("params1");
                    if (!"".equals(params1.trim())) {
                        String[] attrvalues = params1.split("##");
                        List<Map> list = new ArrayList<Map>();
                        List<Map> v_alias = new ArrayList<Map>();
                        AttrValuePO po = null;
                        for (String attrvalue : attrvalues) {
                            String[] a = attrvalue.split("&&");
                            String[] valias = a[3].split(",");
                            Map param = new HashMap();
                            a = attrvalue.split("&&");
                            param = new HashMap();
                            param.put("cid", a[0]);
                            param.put("pid", a[1]);
                            param.put("v_name", a[2]);
                            param.put("state", 1);
                            param.put("startTime", jsonObj.get("startTime"));
                            param.put("endTime", jsonObj.get("endTime"));
                            list.add(param);
                            List<AttrValuePO> approved = attrValueDao.queryApprovedAttrValues(param);
                            if (approved.size() > 0) {
                                po = approved.get(0);
                                for (String v : valias) {
                                    boolean isExsit = false;
                                    for (AliasPO vv : po.getValias()) {
                                        if (vv.getName().equals(v)) {
                                            isExsit = true;
                                        }
                                    }
                                    if (!isExsit) {
                                        Map valiasMap = new HashMap();
                                        valiasMap.put("cid", a[0]);
                                        valiasMap.put("pid", a[1]);
                                        valiasMap.put("state", 1);
                                        valiasMap.put("vid", po.getVid());
                                        valiasMap.put("v_alias", v);
                                        valiasMap.put("v_name", po.getVname());
                                        valiasMap.put("addTime", new Date().getTime() / 1000L);
                                        valiasMap.put("startTime", jsonObj.get("startTime"));
                                        valiasMap.put("endTime", jsonObj.get("endTime"));
                                        v_alias.add(valiasMap);
                                    }
                                }
                            }
                        }
                        logger.info("审核,state=-1==={}", list);
                        logger.info("审核,state=-1==={}", v_alias);
                        attrValueDao.batchInsertPVModel(v_alias);
                        attrValueDao.batchUpdateSynonym(list);
                    }

                    return null;
                }
            });
            return JSONObject.fromObject(new Result("1", "审核成功", null))
                    .toString();
        } catch (Exception e) {
            logger.error("审核,Exception===>", e.getMessage());
            return JSONObject.fromObject(new Result("0", "审核失败", e.getMessage())).toString();
        }
    }

    /**
     * 查询属性
     *
     * @param input
     * @return
     */
    public String queryAttrs(String input) {
        // JSON转换
        JSONObject jsonObj = JSONObject.fromObject(input);
        String jsonStr = JSONArray.fromObject(attrValueDao.queryAttrs(jsonObj)).toString();
        return jsonStr;
    }


    public AttrValueDao getAttrValueDao() {
        return attrValueDao;
    }

    public void setAttrValueDao(AttrValueDao attrValueDao) {
        this.attrValueDao = attrValueDao;
    }

    public String queryApprovedAttrValues(String input) {
        try {
            JSONObject jsonObj = getParams(input);
            logger.info("查询已审核属性值====>{}", jsonObj);
            Page page = new Page(jsonObj.getInt("pageSize"), jsonObj.getInt("pageNo"), attrValueDao.countApprovedAttrValues(jsonObj), attrValueDao.queryApprovedAttrValues((Map) jsonObj));
            logger.info("查询已审核属性值====>{}", page);
            return JSONObject.fromObject(new Result("1", "查询成功", page)).toString();
        } catch (NumberFormatException e) {
            logger.error("查询已审核属性值  error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }
    }

    public String queryNotApprovedAttrValues(String input) {
        try {
            JSONObject jsonObj = getParams(input);
            logger.info("查询未审核属性值====>{}", jsonObj);
            List<AttrValuePO> list = attrValueDao.queryNotApprovedAttrValues((Map) jsonObj);
            for (AttrValuePO po : list) {
                List<AliasPO> valiasList = po.getValias();
                for (AliasPO valias : valiasList) {
                    jsonObj.put("v_alias", valias.getName());
                    valias.setDetails(attrValueDao.queryAttrValueDetails((Map) jsonObj));
                }
            }
            Page page = new Page(jsonObj.getInt("pageSize"), jsonObj.getInt("pageNo"), attrValueDao.countNotApprovedAttrValues(jsonObj), list);
            logger.info("查询未审核属性值====>{}",page);
            return JSONObject.fromObject(new Result("1", "查询成功", page)).toString();
        } catch (NumberFormatException e) {
            logger.error("查询未审核属性值 error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }
    }
}
