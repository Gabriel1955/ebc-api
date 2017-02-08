package com.tunyun.product.ebc.web.es.mo.lable;

/**
 * 分析-产品各店铺评价标签天表 ea_prod_eval_label_d
 * 
 * @author czm
 *
 */
public class EaProdEvalLabelD {

	// 产品ID
	private long prod_id;

	// 渠道ID
	private long chan_id;

	// 店铺ID
	private long chan_shop_id;

	// 标签
	private String label;

	// 标签数量
	private long label_num;

	// 标签词性
	private int label_polar;

	// 统计时间
	private long stat_time;

	public long getProd_id() {
		return prod_id;
	}

	public void setProd_id(long prod_id) {
		this.prod_id = prod_id;
	}

	public long getChan_id() {
		return chan_id;
	}

	public void setChan_id(long chan_id) {
		this.chan_id = chan_id;
	}

	public long getChan_shop_id() {
		return chan_shop_id;
	}

	public void setChan_shop_id(long chan_shop_id) {
		this.chan_shop_id = chan_shop_id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public long getLabel_num() {
		return label_num;
	}

	public void setLabel_num(long label_num) {
		this.label_num = label_num;
	}

	public int getLabel_polar() {
		return label_polar;
	}

	public void setLabel_polar(int label_polar) {
		this.label_polar = label_polar;
	}

	public long getStat_time() {
		return stat_time;
	}

	public void setStat_time(long stat_time) {
		this.stat_time = stat_time;
	}

}
