package com.tunyun.product.ebc.bgmanager.dao;

import java.util.List;
import java.util.Map;

import com.tunyun.product.ebc.bgmanager.po.WordFeaturePO;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年11月28日 下午3:27:58
 * @category com.tunyun.product.ebc.bgmanager.dao
 *
 */
public interface TrWordDao {

	long countWordFeatures(Map params);

	List<WordFeaturePO> queryWordFeaturesByPage(Map params);

	void batchUpdateWordFeatures(List<Map> list);

}
