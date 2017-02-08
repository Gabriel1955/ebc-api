package com.tunyun.product.ebc.bgmanager.dao;

import java.util.List;
import java.util.Map;

import com.common.system.jdbc.jpa.JPABaseDAO;
import com.tunyun.product.ebc.bgmanager.po.AttrPO;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年10月25日 下午12:34:45
 * @category com.tunyun.product.ebc.bgmanager.dao
 *
 */
public class AttrDaoImpl extends JPABaseDAO implements AttrDao {
	private final String NS = AttrDaoImpl.class.getName();
	@Override
	public List<AttrPO> queryApprovedAttrs(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryApprovedAttrs", params);
	}

	@Override
	public List<AttrPO> queryNotApprovedAttrs(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryNotApprovedAttrs", params);
	}

	@Override
	public List<Map> queryAttrDetails(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryAttrDetails", params);
	}

	@Override
	public void batchUpdateSynonym(List<Map> list) {
		this.getSqlSessionTemplate().batchUpdate(NS + ".updateAttrSynonym", list);
	}

	@Override
	public void batchInsertPModel(List<Map> list) {
		this.getSqlSessionTemplate().batchInsert(NS + ".insertPModel", list);
	}

	@Override
	public long queryMaxUid(Map params) {
		long maxuid = this.getSqlSessionTemplate().selectOne(NS + ".queryMaxUid", params);
		return maxuid;
	}

	@Override
	public void batchInsertP(List<Map> list) {
		this.getSqlSessionTemplate().batchInsert(NS + ".insertP", list);
	}

	@Override
	public void batchInsertCategP(List<Map> list) {
		this.getSqlSessionTemplate().batchInsert(NS + ".insertCategP", list);
	}

	@Override
	public void batchUpdateMaxUid(List<Map> list) {
//		this.getSqlSessionTemplate().batchUpdate(NS + ".updateMaxUid", list);
	}

}
