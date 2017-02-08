package com.tunyun.product.ebc.bgmanager.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.system.jdbc.jpa.JPABaseDAO;
import com.tunyun.product.ebc.bgmanager.po.AliasPO;
import com.tunyun.product.ebc.bgmanager.po.AttrValuePO;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年9月28日 下午1:57:33
 * @category com.tunyun.product.ebc.bgmanager.dao
 *
 */
public class AttrValueDaoImpl extends JPABaseDAO implements AttrValueDao {
	private static Logger logger = LoggerFactory.getLogger(AttrValueDaoImpl.class);
	private final String NS = AttrValueDaoImpl.class.getName();
	// private static final long PREFIX_ID = 1000000L;

	@Override
	public List<AttrValuePO> queryApprovedAttrValues(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryApprovedAttrValuesByPage", params);
	}

	@Override
	public List<AttrValuePO> queryNotApprovedAttrValues(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryNotApprovedAttrValuesByPage", params);
	}
	
	@Override
	public List<Map> queryAttrValueDetails(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryAttrValueDetails", params);
	}

	@Override
	public void batchUpdateSynonym(List<Map> list) {
		this.getSqlSessionTemplate().batchUpdate(NS + ".updateSynonym", list);
	}

	@Override
	public void batchInsertPVModel(List<Map> list) {
		this.getSqlSessionTemplate().batchInsert(NS + ".insertPVModel", list);
	}

	@Override
	public long queryMaxVid(Map param) {
		long maxvid = this.getSqlSessionTemplate().selectOne(NS + ".queryMaxVid", param);
		return maxvid;
	}

	@Override
	public void batchInsertCategPV(List<Map> list) {
		this.getSqlSessionTemplate().batchInsert(NS + ".insertCategPV", list);
	}

	@Override
	public void batchInsertPV(List<Map> list) {
		this.getSqlSessionTemplate().batchInsert(NS + ".insertPV", list);
	}

	@Override
	public void batchUpdateMaxVid(List<Map> list) {
		this.getSqlSessionTemplate().batchUpdate(NS + ".updateMaxVid", list);
	}

	@Override
	public List<Map> queryAttrs(Map param) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryAttrs", param);
	}

	@Override
	public void batchDeletePV(List<Map> list) {
		this.getSqlSessionTemplate().batchDelete(NS + ".deletePV", list);
		
	}

	@Override
	public void batchDeletePVModel(List<Map> list) {
		this.getSqlSessionTemplate().batchDelete(NS + ".deletePVModel", list);
		
	}

	@Override
	public void batchUpdateSynonym1(List<Map> list) {
		this.getSqlSessionTemplate().batchUpdate(NS + ".updateSynonym1", list);
	}

	@Override
	public long countApprovedAttrValues(Map params) {
		return this.getSqlSessionTemplate().selectOne(NS + ".countApprovedAttrValues", params);
	}

	@Override
	public long countNotApprovedAttrValues(Map params) {
		return this.getSqlSessionTemplate().selectOne(NS + ".countNotApprovedAttrValues", params);
	}

	@Override
	public List<Map> queryPV(Map params) {
		return this.getSqlSessionTemplate().selectList(NS + ".queryPV", params);
	}
}
