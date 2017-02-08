package com.tunyun.product.ebc.bgmanager.po;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年11月10日 上午10:06:42
 * @category com.tunyun.product.ebc.bgmanager.po
 *
 */
public class OriginalPO {

	private String original;

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}
	public String toString() {
		return new ToStringBuilder(this)
				.append("original", this.original)
				.toString();
	}
	
}
