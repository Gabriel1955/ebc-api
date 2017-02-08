
package com.tunyun.product.ebc.bgmanager.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.system.jdbc.jpa.JPABaseDAO;
import com.google.common.base.Strings;
import com.tunyun.product.ebc.common.util.ToolsUtil;

/**
 * @author gaojinbao
 * @version 1.0
 * @since 2016年5月10日
 * @category com.tunyun.product.ebc.bgmanager.dao
 */
public class GoodsDetailDaoImpl extends JPABaseDAO implements GoodsDetailDao
{
	private static Logger logger = LoggerFactory.getLogger(GoodsDetailDaoImpl.class);
	private final String NS = GoodsDetailDaoImpl.class.getName();
	@Override
	public List<Map<String, Object>> getAttrValueByAttrid(Map<String,String> param) {
		return this.getSqlSessionTemplate().selectList(NS+".getAttrValueByAttrid", param);
	}
	@Override
	public Map<String, Object> getAttrValues(Map<String, String> map) {
		Map<String, Object> newMap = new LinkedHashMap<String, Object>();
		Map param = new HashMap<String,String>();
		Map<String, String> resultMap = ToolsUtil.sortMapByKey(map);	//按Key进行排序
		logger.info("getAttrValues=={}", resultMap);
		try{
			for (Entry<String, String> entry : resultMap.entrySet()) {
				String attrId = entry.getKey();
				String attrValueIds = String.valueOf(entry.getValue());
				if (!Strings.isNullOrEmpty(attrId) && !Strings.isNullOrEmpty(attrValueIds)) {
					param.put("attrId", attrId);
					param.put("attrValueIds", attrValueIds.split(","));
					List<Map<String, Object>> list = getAttrValueByAttrid(param);
					if (null != list && list.size() > 0) {
						attrId = String.valueOf(list.get(0).get("p_name"));
						attrValueIds = spellAttrValueName(list);
						newMap.put(attrId, attrValueIds);
					}
				}
			}
		}catch(Exception e){
			logger.error("getAttrValues=={}", e);
		}
		return newMap;
	}
	private String spellAttrValueName(List<Map<String, Object>> list) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(list.get(i).get("v_name"));
		}
		return sb.toString();
	}
}
