
package com.tunyun.product.ebc.common.dao;

import java.util.List;
import java.util.Map;

/**
 * 通用Dao
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年4月12日
 * @category com.tunyun.product.ebc.common.dao
 */
public interface CommonDao
{

	/**
	 * 获取品类表
	 * 
	 * @return
	 */
	List<Map<String, Object>> getTriCateg();

	/**
	 * 获取渠道表
	 * 
	 * @return
	 */
	List<Map<String, Object>> geTrTenantChan();
}
