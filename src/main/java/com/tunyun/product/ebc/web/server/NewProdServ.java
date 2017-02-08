package com.tunyun.product.ebc.web.server;

import com.tunyun.product.ebc.common.util.Result;
import com.tunyun.product.ebc.web.common.Page;
import com.tunyun.product.ebc.web.dao.NewProdDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by shiliang on 2016/12/13.
 */
public class NewProdServ extends BaseServ {
    private static Logger logger = LoggerFactory.getLogger(NewProdServ.class);

    private NewProdDao newProdDao;

    public String queryTrConfigTypes(String input){
            JSONObject jsonObj = getParams(input);
            logger.info("查询====>{}", jsonObj);
            List<Map> list = newProdDao.queryTrConfigTypes((Map) jsonObj);
            logger.info("查询====>{}", list);
            return JSONArray.fromObject(list).toString();
    }
    public String queryTimeList(String input){
        JSONObject jsonObj = getParams(input);
        logger.info("查询====>{}", jsonObj);
        List<Map> list = newProdDao.queryTimeList((Map) jsonObj);
        logger.info("查询====>{}", list);
        return JSONArray.fromObject(list).toString();
    }

    public String queryProgrammes(String input) {
        try {
            JSONObject jsonObj = getParams(input);
            logger.info("查询====>{}", jsonObj);
            List<Map> list = newProdDao.queryProgrammesByPage((Map) jsonObj);
            Page page = new Page(jsonObj.getInt("pageSize"), jsonObj.getInt("pageNo"), newProdDao.countProgrammes(jsonObj), list);
            logger.info("查询====>{}", page);
            return JSONObject.fromObject(new Result("1", "查询成功", page)).toString();
        } catch (NumberFormatException e) {
            logger.error("查询 error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }
    }
    public String queryProgrammeAttrs(String input) {
        try {
            JSONObject jsonObj = getParams(input);
            logger.info("查询====>{}", jsonObj);
            List<Map> list = newProdDao.queryProgrammeAttrsByPage((Map) jsonObj);
            Page page = new Page(jsonObj.getInt("pageSize"), jsonObj.getInt("pageNo"), newProdDao.countProgrammeAttrs(jsonObj), list);
            logger.info("查询====>{}", page);
            return JSONObject.fromObject(new Result("1", "查询成功", page)).toString();
        } catch (NumberFormatException e) {
            logger.error("查询 error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }
    }

    public String queryProgrammeTypes(String input) {
        try {
            JSONObject jsonObj = getParams(input);
            logger.info("查询====>{}", jsonObj);
            List<Map> list = newProdDao.queryProgrammeTypesByPage((Map) jsonObj);
            Page page = new Page(jsonObj.getInt("pageSize"), jsonObj.getInt("pageNo"), newProdDao.countProgrammeTypes(jsonObj), list);
            logger.info("查询====>{}", page);
            return JSONObject.fromObject(new Result("1", "查询成功",page)).toString();
        } catch (NumberFormatException e) {
            logger.error("查询 error:{}", e);
            return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
        }
    }

    public NewProdDao getNewProdDao() {
        return newProdDao;
    }

    public void setNewProdDao(NewProdDao newProdDao) {
        this.newProdDao = newProdDao;
    }
}
