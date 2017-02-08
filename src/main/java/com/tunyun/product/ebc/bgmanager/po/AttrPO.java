package com.tunyun.product.ebc.bgmanager.po;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年10月25日 上午9:46:06
 * @category com.tunyun.product.ebc.bgmanager.po
 *
 */
public class AttrPO {
	private String cid;
	private String pid;
	private String pname;
	private String state;
	private long addTime;
	private long updTime;// 最近更新时间
	private long delTime;// 删除时间
	private List<AliasPO> palias;
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
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
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
	public long getUpdTime() {
		return updTime;
	}
	public void setUpdTime(long updTime) {
		this.updTime = updTime;
	}
	public long getDelTime() {
		return delTime;
	}
	public void setDelTime(long delTime) {
		this.delTime = delTime;
	}
	public List<AliasPO> getPalias() {
		return palias;
	}
	public void setPalias(List<AliasPO> palias) {
		this.palias = palias;
	}
	public String toString() {
		return new ToStringBuilder(this).append("cid", this.cid).append("pid", this.pid)
				.append("pname", this.pname).append("state", this.state)
				.append("state", this.state)
				.append("addTime", this.addTime)
				.append("updTime", this.updTime)
				.append("delTime", this.delTime)
				.append("palias", this.palias).toString();
	}
}
