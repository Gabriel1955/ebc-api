
package com.tunyun.product.ebc.bgmanager.serv;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
import com.tunyun.product.ebc.common.util.ToolsUtil;

import net.sf.json.JSONObject;

/**
 * 商品详情展示
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年5月10日
 * @category com.tunyun.product.ebc.bgmanager.serv
 */
public class GoodsDetailServ implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(GoodsDetailServ.class);
	private static Configuration configuration;
	private String zkQuorum;
	private String zkPort;
	private String hbMaster;
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
	/**
	 * 获取详情
	 * 
	 * @param json
	 * @return
	 */
	public String goodsDetail(String json) {
		// JSON转换
		JSONObject jsonObj = JSONObject.fromObject(json);
		String goodsId = String.valueOf(jsonObj.get("gid"));
		String chanId = String.valueOf(jsonObj.get("chanid"));
		Map<String, Object> map = new HashMap<String, Object>();
		if (!Strings.isNullOrEmpty(goodsId) && !Strings.isNullOrEmpty(chanId)) {
			String rowKey = ToolsUtil.md5Hash(chanId + "#" + goodsId);
			logger.info("rowKey:{}",rowKey);
			HTable table = null;
			try {
				table = new HTable(configuration, "tp_item_static");
				Get get = new Get(rowKey.getBytes());
				Result rs = table.get(get);
				logger.info("searchGoods:{}", rs);
				map.put("cf1", ToolsUtil.getValue(rs, "cf1"));
				map.put("cf2", ToolsUtil.getValue(rs, "cf2"));
				map.put("cf3", ToolsUtil.getValue(rs, "cf3"));
				map.put("cf4", getCf4(ToolsUtil.getValue(rs, "cf4")));
				map.put("cf5", ToolsUtil.getValue(rs, "cf5"));
				map.put("cf6", this.getCf6(rs, rowKey));
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
		logger.info("goodsDetail=={}", jsonStr);
		return jsonStr;
	}

	private Map<String, Object> getCf4(Map<String, String> map) {
		logger.info("getCf4=={}", map);
		return goodsDetailDao.getAttrValues(map);
	}

	public Map<String, Object> getCf6(Result rs, String rowKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("产品ID", ToolsUtil.getValue(rs, "cf6", "prod_id"));
		return map;
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

	public void setGoodsDetailDao(GoodsDetailDao goodsDetailDao) {
		this.goodsDetailDao = goodsDetailDao;
	}

	public static void main(String[] args) {
//		String sss = "{\"gid\":10182355707,\"chanid\":1}";
//		JSONObject jsonObj = JSONObject.fromObject(sss);
//		String goodsId = String.valueOf(jsonObj.get("gid"));
//		String chanId = String.valueOf(jsonObj.get("chanid"));
//		System.out.println(goodsId + "++" + chanId);
		
//		Map<String, String> map = new TreeMap<String, String>();
//		map.put("101", "kfc");
//		map.put("103", "wnba");
//		map.put("102", "nba");
//		map.put("20", "cba");
//
//		Map<String, String> resultMap = ToolsUtil.sortMapByKey(map);	//按Key进行排序
//
//		for (Map.Entry<String, String> entry : resultMap.entrySet()) {
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		}

		String rowKey = ToolsUtil.md5Hash("3#10430871993");
		System.out.println("rowKey:"+rowKey);
		
	}
}
