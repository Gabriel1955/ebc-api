package com.tunyun.product.ebc.web.es.mo.lable;

/**
 * ESE行业+品牌-最近30天 统计表 ess_indu_brand_yyyy
 * 
 * @author czm
 *
 */
public class EseInduBrandYear {

	// 行业ID
	private long indu_id;

	// 品牌ID
	private long bid;

	// 标签
	private String label;

	// 统计时间
	private long stat_time;

	// 品牌名称
	private String brand_name;

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

	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
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

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
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
