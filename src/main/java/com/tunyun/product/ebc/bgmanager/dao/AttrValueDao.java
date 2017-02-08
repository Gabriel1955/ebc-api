package com.tunyun.product.ebc.bgmanager.dao;

import java.util.List;
import java.util.Map;

import com.tunyun.product.ebc.bgmanager.po.AttrValuePO;

import net.sf.json.JSONObject;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年9月28日 下午1:57:02
 * @category com.tunyun.product.ebc.bgmanager.dao
 *
 */
public interface AttrValueDao {

	public abstract List<AttrValuePO> queryApprovedAttrValues(Map params);

	public abstract List<AttrValuePO> queryNotApprovedAttrValues(Map params);
	
	public abstract List<Map> queryAttrValueDetails(Map params);
	
	public abstract void batchUpdateSynonym(List<Map> list);

	public abstract void batchInsertPVModel(List<Map> list);

	public abstract long queryMaxVid(Map param);

	public abstract void batchInsertCategPV(List<Map> list);

	public abstract void batchInsertPV(List<Map> list);

	public abstract void batchUpdateMaxVid(List<Map> list);

	public abstract List<Map> queryAttrs(Map param);

	public abstract void batchDeletePV(List<Map> list);

	public abstract void batchDeletePVModel(List<Map> list);

	public abstract void batchUpdateSynonym1(List<Map> list);

	public abstract long countApprovedAttrValues(Map params);

	public abstract long countNotApprovedAttrValues(Map params);


	public abstract List<Map> queryPV(Map param);
}
