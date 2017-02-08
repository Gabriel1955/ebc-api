package com.tunyun.product.ebc.bgmanager.dao;

import java.util.List;
import java.util.Map;

import com.tunyun.product.ebc.bgmanager.po.AttrRulePO;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年11月4日 下午3:30:32
 * @category com.tunyun.product.ebc.bgmanager.dao
 *
 */
public interface AttrRuleDao {

	List<AttrRulePO> queryAttrRules(Map params);

	void batchDelAttrRules(List<Map> dellist);

	void batchInsertAttrRules(List<Map> insertList);

	List<Map> queryTestDatas(Map params);

	long countTestDatas(Map params);

	List<Map> configQueryAttrRules(Map params);

	List<Map> regtestQueryAttrRules(Map params);

	List<Map> queryTrModelTests(Map params);

	void batchUpdateTrModelTest(List<Map> updateList);

	void batchInsertTrModelTest(List<Map> insertList);

	long countTrValueAuditDatas(Map params);

	List<Map> queryTrValueAuditDatas(Map params);

	List<Map> queryAttrs(Map params);

}
