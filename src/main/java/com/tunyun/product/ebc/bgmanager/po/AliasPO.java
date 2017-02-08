package com.tunyun.product.ebc.bgmanager.po;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年9月28日 下午2:38:20
 * @category com.tunyun.product.ebc.bgmanager.po
 *
 */
public class AliasPO {

	private String uid;
	private String id;
	private String name;
	private List<Map> details;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map> getDetails() {
		return details;
	}

	public void setDetails(List<Map> details) {
		this.details = details;
	}

	public String toString() {
		return new ToStringBuilder(this).append("uid", this.uid).append("id", this.id)
				.append("name", this.name).append("details", this.details).toString();
	}

}
