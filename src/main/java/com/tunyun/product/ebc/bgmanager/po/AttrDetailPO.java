package com.tunyun.product.ebc.bgmanager.po;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年11月28日 下午5:28:03
 * @category com.tunyun.product.ebc.bgmanager.po
 *
 */
public class AttrDetailPO {

	private int v_num;
	private int chan_id;
	private String chan_name;
	private String items;

	public int getV_num() {
		return v_num;
	}

	public void setV_num(int v_num) {
		this.v_num = v_num;
	}

	public int getChan_id() {
		return chan_id;
	}

	public void setChan_id(int chan_id) {
		this.chan_id = chan_id;
	}

	public String getChan_name() {
		return chan_name;
	}

	public void setChan_name(String chan_name) {
		this.chan_name = chan_name;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String toString() {
		return new ToStringBuilder(this).append("v_num", this.v_num).append("chan_id", this.chan_id)
				.append("chan_name", this.chan_name).append("items", this.items).toString();
	}
}
