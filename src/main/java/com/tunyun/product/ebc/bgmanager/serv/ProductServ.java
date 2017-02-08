package com.tunyun.product.ebc.bgmanager.serv;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Strings;
import com.tunyun.product.ebc.bgmanager.dao.GoodsDetailDao;
import com.tunyun.product.ebc.bgmanager.dao.TriCategDao;
import com.tunyun.product.ebc.common.util.ToolsUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 产品服务类
 * @author shiliang
 * @version 1.0
 * @since 2016年9月21日 下午9:02:27
 * @category com.tunyun.product.ebc.bgmanager.serv
 *
 */
public class ProductServ implements InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(ProductServ.class);
	private static Configuration configuration;
	private String zkQuorum;
	private String zkPort;
	private String hbMaster;
	private TriCategDao triCategDao;
	private GoodsDetailDao goodsDetailDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (null == configuration) {
			if (!Strings.isNullOrEmpty(zkQuorum)) {
				configuration = HBaseConfiguration.create();
				configuration.set("hbase.zookeeper.quorum", zkQuorum);
				configuration.set("hbase.zookeeper.property.clientPort",
						Strings.isNullOrEmpty(zkPort) ? "2181" : zkPort);
				if (!Strings.isNullOrEmpty(hbMaster)) {
					configuration.set("hbase.master", hbMaster);
				}
			} else {
				logger.error("hbase profile error!");
			}
		}
	}

	public void setZkQuorum(String zkQuorum) {
		this.zkQuorum = zkQuorum;
	}

	public void setZkPort(String zkPort) {
		this.zkPort = zkPort;
	}

	public void setHbMaster(String hbMaster) {
		this.hbMaster = hbMaster;
	}

	public TriCategDao getTriCategDao() {
		return triCategDao;
	}

	public void setTriCategDao(TriCategDao triCategDao) {
		this.triCategDao = triCategDao;
	}

	public String searachCategs(String input) {
		JSONObject jsonObj = JSONObject.fromObject(input);
		logger.info("[searachCategs]{}", jsonObj);
		String result = JSONArray.fromObject(triCategDao.queryTriCategs((Map) jsonObj)).toString();
		logger.info("[searachCategs]{}", result);
		return result;
	}

	public String searachProduct(String input) {
		JSONObject jsonObj = JSONObject.fromObject(input);
		String uid = String.valueOf(jsonObj.get("uid"));
		String prodid = String.valueOf(jsonObj.get("prodid"));
		Map<String, Object> map = new HashMap<String, Object>();
		if (!Strings.isNullOrEmpty(uid) && !Strings.isNullOrEmpty(uid) && !Strings.isNullOrEmpty(prodid)
				&& !Strings.isNullOrEmpty(prodid)) {
			String rowKey = prodid;
			HTable table = null;
			try {
				table = new HTable(configuration, "prod_static_" + uid);
				Get get = new Get(rowKey.getBytes());
				Result rs = table.get(get);
				logger.info("searachProduct:{}", rs);
				map.put("cf1", this.getCf1(rs, rowKey));
				map.put("cf2", getCf2(ToolsUtil.getValue(rs, "cf2")));
				// map.put("cf3", this.getCf3(rs, rowKey));
				map.put("cf3", ToolsUtil.getValue(rs, "cf3"));
			} catch (IOException e) {
				logger.error("hbase get error {}", e);
			} finally {
				try {
					if (null != table) {
						table.close();
					}
				} catch (IOException e) {
					logger.error("hbase table close error {}", e);
				}
			}
		}
		String jsonStr = JSONObject.fromObject(map).toString();
		logger.info("product=={}", jsonStr);
		return jsonStr;
	}

	public Map<String, Object> getCf1(Result rs, String rowKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("名称", ToolsUtil.getValue(rs, "cf1", "title"));
		map.put("价格", ToolsUtil.getValue(rs, "cf1", "price"));
		return map;
	}

	public Map<String, Object> getCf2(Map<String, String> map) {
		return goodsDetailDao.getAttrValues(map);
	}

	public Map<String, Object> getCf3(Result rs, String rowKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", ToolsUtil.getValue(rs, "cf3", "item"));
		return map;
	}

	public GoodsDetailDao getGoodsDetailDao() {
		return goodsDetailDao;
	}

	public void setGoodsDetailDao(GoodsDetailDao goodsDetailDao) {
		this.goodsDetailDao = goodsDetailDao;
	}

}
