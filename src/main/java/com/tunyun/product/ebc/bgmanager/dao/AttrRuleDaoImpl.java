package com.tunyun.product.ebc.bgmanager.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.system.jdbc.jpa.JPABaseDAO;
import com.tunyun.product.ebc.bgmanager.po.AttrRulePO;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年11月4日 下午3:31:10
 * @category com.tunyun.product.ebc.bgmanager.dao
 *
 */
public class AttrRuleDaoImpl extends JPABaseDAO implements AttrRuleDao {
	private final String NS = AttrRuleDaoImpl.class.getName();
	private static Logger logger = LoggerFactory.getLogger(AttrRuleDaoImpl.class);

	@Override
	public List<AttrRulePO> queryAttrRules(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryAttrRules", params);
	}

	@Override
	public void batchDelAttrRules(List<Map> dellist) {
		this.getSqlSessionTemplate().batchDelete(NS + ".deleteAttrRule", dellist);
	}

	@Override
	public void batchInsertAttrRules(List<Map> insertList) {
		this.getSqlSessionTemplate().batchInsert(NS + ".insertAttrRule", insertList);

	}

	@Override
	public List<Map> queryTestDatas(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryTestDatas", params);
	}

	@Override
	public long countTestDatas(Map params) {
		return this.getSqlSessionTemplate().selectOne(NS + ".countTestDatas", params);
	}

	@Override
	public List<Map> configQueryAttrRules(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".configTestQuery", params);
	}

	@Override
	public List<Map> regtestQueryAttrRules(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".regtestQuery", params);
	}

	@Override
	public List<Map> queryTrModelTests(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryTrModelTests", params);
	}

	@Override
	public void batchUpdateTrModelTest(List<Map> updateList) {
		this.getSqlSessionTemplate().batchUpdate(NS + ".updateTrModelTest", updateList);
	}

	@Override
	public void batchInsertTrModelTest(List<Map> insertList) {
		this.getSqlSessionTemplate().batchInsert(NS + ".insertTrModelTest", insertList);
		
	}

	@Override
	public long countTrValueAuditDatas(Map params) {
		return this.getSqlSessionTemplate().selectOne(NS + ".countTrValueAuditDatas", params);
	}

	@Override
	public List<Map> queryTrValueAuditDatas(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryTrValueAuditDatas", params);
	}

	@Override
	public List<Map> queryAttrs(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryAttrs", params);
	}
}
