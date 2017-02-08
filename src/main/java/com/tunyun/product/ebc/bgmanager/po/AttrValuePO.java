package com.tunyun.product.ebc.bgmanager.po;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年9月28日 下午2:35:48
 * @category com.tunyun.product.ebc.bgmanager.po
 *
 */
public class AttrValuePO {

	private String cid;
	private String pid;
	private String vid;
	private String vname;
	private String state;
	private long addTime;
	private List<AliasPO> valias;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getAddTime() {
		return addTime;
	}
	public void setAddTime(long addTime) {
		this.addTime = addTime;
	}
	public List<AliasPO> getValias() {
		return valias;
	}
	public void setValias(List<AliasPO> valias) {
		this.valias = valias;
	}
	public String toString() {
		return new ToStringBuilder(this)
				.append("cid", this.cid)
				.append("pid", this.pid)
				.append("vid", this.vid)
				.append("vname", this.vname)
				.append("state", this.state)
				.append("addTime", this.addTime)
				.append("valias", this.valias)
				.toString();
	}
}
