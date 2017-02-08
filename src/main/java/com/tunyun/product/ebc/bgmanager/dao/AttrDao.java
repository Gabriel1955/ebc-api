package com.tunyun.product.ebc.bgmanager.dao;

import java.util.List;
import java.util.Map;

import com.tunyun.product.ebc.bgmanager.po.AttrPO;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年10月25日 下午12:33:09
 * @category com.tunyun.product.ebc.bgmanager.dao
 *
 */
public interface AttrDao {

	public abstract List<AttrPO> queryApprovedAttrs(Map params);

	public abstract List<AttrPO> queryNotApprovedAttrs(Map params);

	public abstract List<Map> queryAttrDetails(Map params);

	public abstract void batchUpdateSynonym(List<Map> list);

	public abstract void batchInsertPModel(List<Map> list);

	public abstract long queryMaxUid(Map params);

	public abstract void batchInsertP(List<Map> list);

	public abstract void batchInsertCategP(List<Map> list);

	public abstract void batchUpdateMaxUid(List<Map> list);

}
