package com.tunyun.product.ebc.web.es.mo.lable;

/**
 * ESE行业+渠道-最近30天 统计 表 ese_indu_chan_yyyy
 * 
 * @author czm
 *
 */
public class EseInduChanYear {

	// 行业ID
	private long indu_id;

	// 渠道ID
	private long chan_id;

	// 标签
	private String label;

	// 统计时间
	private long stat_time;

	// 渠道名称
	private String chan_name;

	// 标签数量
	private long label_num;

	// 标签词性
	private int label_polary;

	public long getIndu_id() {
		return indu_id;
	}

	public void setIndu_id(long indu_id) {
		this.indu_id = indu_id;
	}

	public long getChan_id() {
		return chan_id;
	}

	public void setChan_id(long chan_id) {
		this.chan_id = chan_id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public long getStat_time() {
		return stat_time;
	}

	public void setStat_time(long stat_time) {
		this.stat_time = stat_time;
	}

	public String getChan_name() {
		return chan_name;
	}

	public void setChan_name(String chan_name) {
		this.chan_name = chan_name;
	}

	public long getLabel_num() {
		return label_num;
	}

	public void setLabel_num(long label_num) {
		this.label_num = label_num;
	}

	public int getLabel_polary() {
		return label_polary;
	}

	public void setLabel_polary(int label_polary) {
		this.label_polary = label_polary;
	}

}
