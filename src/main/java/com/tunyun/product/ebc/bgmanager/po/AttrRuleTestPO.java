package com.tunyun.product.ebc.bgmanager.po;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年11月10日 上午9:50:40
 * @category com.tunyun.product.ebc.bgmanager.po
 *
 */
public class AttrRuleTestPO {
	
	
	private String p_name;
	private String p_value;
	private List<OriginalPO> originals;
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getP_value() {
		return p_value;
	}
	public void setP_value(String p_value) {
		this.p_value = p_value;
	}
	public List<OriginalPO> getOriginals() {
		return originals;
	}
	public void setOriginals(List<OriginalPO> originals) {
		this.originals = originals;
	}
	public String toString() {
		return new ToStringBuilder(this)
				.append("p_name", this.p_name)
				.append("p_value", this.p_value)
				.append("originals", this.originals)
				.toString();
	}
	

}
