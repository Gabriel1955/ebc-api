package com.tunyun.product.ebc.web.es.mo.lable;

/**
 * ESE行业+单品-最近30天 统计表 ese_indu_prod_yyyy
 * 
 * @author czm
 *
 */
public class EseInduProdYear {

	// 行业ID
	private long indu_id;

	// 产品ID
	private long prod_id;

	// 标签
	private String label;

	// 统计时间
	private long stat_time;

	// 产品名称
	private String prod_name;

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

	public long getProd_id() {
		return prod_id;
	}

	public void setProd_id(long prod_id) {
		this.prod_id = prod_id;
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

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
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
