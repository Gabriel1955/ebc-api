package com.tunyun.product.ebc.bgmanager.po;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年11月2日 下午3:20:45
 * @category com.tunyun.product.ebc.bgmanager.po
 *
 */
public class RulePO {

	private String expr;
	private String type;
	private String replace_str;
	private int index;

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReplace_str() {
		return replace_str;
	}

	public void setReplace_str(String replace_str) {
		this.replace_str = replace_str;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	public String toString() {
		return new ToStringBuilder(this)
				.append("expr", this.expr)
				.append("type", this.type)
				.append("replace_str", this.replace_str)
				.append("index", this.index)
				.toString();
	}
	

}
