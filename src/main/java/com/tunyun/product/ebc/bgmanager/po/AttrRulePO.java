package com.tunyun.product.ebc.bgmanager.po;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年11月2日 下午3:20:29
 * @category com.tunyun.product.ebc.bgmanager.po
 *
 */
public class AttrRulePO {

	private String cid;
	private String chan_id;
	private String p_name;
	private List<RulePO> preRules;
	private List<RulePO> afterRules;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getChan_id() {
		return chan_id;
	}
	public void setChan_id(String chan_id) {
		this.chan_id = chan_id;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public List<RulePO> getPreRules() {
		return preRules;
	}
	public void setPreRules(List<RulePO> preRules) {
		this.preRules = preRules;
	}
	public List<RulePO> getAfterRules() {
		return afterRules;
	}
	public void setAfterRules(List<RulePO> afterRules) {
		this.afterRules = afterRules;
	}
	public String toString() {
		return new ToStringBuilder(this).append("cid", this.cid).append("chan_id", this.chan_id)
				.append("p_name", this.p_name).append("preRules", this.preRules)
				.append("afterRules", this.afterRules).toString();
	}
}
