
package com.tunyun.product.ebc.bgmanager.dao;

import java.util.List;
import java.util.Map;

/**
 * @author gaojinbao
 * @version 1.0
 * @since 2016年5月10日
 * @category com.tunyun.product.ebc.bgmanager.dao
 */
public interface GoodsDetailDao
{

	List<Map<String, Object>> getAttrValueByAttrid(Map<String,String> param);

	Map<String, Object> getAttrValues(Map<String, String> map);
}
