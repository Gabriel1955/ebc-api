package com.tunyun.product.ebc.bgmanager.po;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年11月14日 上午10:15:52
 * @category com.tunyun.product.ebc.bgmanager.po
 *
 */
public class TriCategPO {

	private String cat_name;
	private String cat_alias;
	private String uid;
	private List<TriCategPO> children;
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}
	public String getCat_alias() {
		return cat_alias;
	}
	public void setCat_alias(String cat_alias) {
		this.cat_alias = cat_alias;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public List<TriCategPO> getChildren() {
		return children;
	}
	public void setChildren(List<TriCategPO> children) {
		this.children = children;
	}
	public String toString() {
		return new ToStringBuilder(this)
				.append("cat_name", this.cat_name)
				.append("cat_alias", this.cat_alias)
				.append("uid", this.uid)
				.append("children", this.children)
				.toString();
	}
}
