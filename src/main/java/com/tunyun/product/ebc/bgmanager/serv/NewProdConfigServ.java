package com.tunyun.product.ebc.bgmanager.serv;

import com.tunyun.product.ebc.bgmanager.dao.NewProdConfigDao;
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
 * Created by shiliang on 2016/12/22.
 */
public class NewProdConfigServ extends BaseServ {
    private static Logger logger = LoggerFactory.getLogger(NewProdConfigServ.class);

    private NewProdConfigDao newProdConfigDao;

    public String query(String input) {
        try {
            JSONObject jsonObj = getParams(input);
            logger.info("查询====>{}", jsonObj);
            Page page = new Page(jsonObj.getInt("pageSize"), jsonObj.getInt("pageNo"), newProdConfigDao.count((Map) jsonObj), newProdConfigDao.query((Map) jsonObj));
            logger.info("查询====>{}", page);
            return JSONObject.fromObject(new Result("1", "查询成功", page)).toString();
        } catch (Exception e) {
            logger.error("查询 error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }
    }

    public NewProdConfigDao getNewProdConfigDao() {
        return newProdConfigDao;
    }

    public void setNewProdConfigDao(NewProdConfigDao newProdConfigDao) {
        this.newProdConfigDao = newProdConfigDao;
    }

    public String save(String input) {
        try {
            JSONArray jsonArray = JSONArray.fromObject(input);
            logger.info("=================>save:{}", jsonArray);
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallback<Object>() {
                @Override
                public Object doInTransaction(TransactionStatus status) {
                    List<Map> insertList = new ArrayList<Map>();
                    List<Map> deleteList = new ArrayList<Map>();
                    Iterator<Object> it = jsonArray.iterator();
                    int maxTypeId=newProdConfigDao.getMaxTypeId();
                    while (it.hasNext()) {
                        JSONObject ob = (JSONObject) it.next();
                        Map param = new HashMap();
                        if(ob.has("type_id")){
                            if(!ob.getBoolean("selected")){
                                param.put("type_id", ob.getInt("type_id"));
                                deleteList.add(param);
                            }
                        }else{
                            param.put("cid", ob.getString("cid"));
                            param.put("p_id", ob.getString("pid"));
                            param.put("v_id", ob.getString("vid"));
                            param.put("type_id", ++maxTypeId);
                            insertList.add(param);
                        }
                    }
                    logger.info("=================>insert:{}", insertList);
                    if(insertList.size()>0){
                        newProdConfigDao.batchInsert(insertList);
                    }

                    if(deleteList.size()>0){
                        newProdConfigDao.batchDelete(deleteList);
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
}
