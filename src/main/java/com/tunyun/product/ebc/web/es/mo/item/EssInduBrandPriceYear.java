package com.tunyun.product.ebc.web.es.mo.item;

/**
 * ESS行业+品牌+消费能力-最近30天综合 统计表 ess_indu_brand_price_yyyy
 * 
 * @author czm
 *
 */
public class EssInduBrandPriceYear {

	// 行业ID
	private long indu_id;

	// 品牌ID
	private long bid;

	// 价格区间ID
	private long price_range_id;

	// 统计时间
	private long stat_time;

	// 品牌名称
	private String brand_name;

	// 价格区间名称
	private String price_range_name;

	// 销售总额
	private double sale_amount;

	// 销售数量
	private long sale_num;

	// 店铺总数
	private long chan_shop_num;

	// 评价总数
	private long total_eval_num;

	// 产品总数
	private long prod_num;

	// 品单价
	private double prod_price;

	// 销售总额环比
	private float sale_amount_chain;

	// 销售总额行业占比
	private float amount_indu_ratio;

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

	public long getPrice_range_id() {
		return price_range_id;
	}

	public void setPrice_range_id(long price_range_id) {
		this.price_range_id = price_range_id;
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

	public String getPrice_range_name() {
		return price_range_name;
	}

	public void setPrice_range_name(String price_range_name) {
		this.price_range_name = price_range_name;
	}

	public double getSale_amount() {
		return sale_amount;
	}

	public void setSale_amount(double sale_amount) {
		this.sale_amount = sale_amount;
	}

	public long getSale_num() {
		return sale_num;
	}

	public void setSale_num(long sale_num) {
		this.sale_num = sale_num;
	}

	public long getChan_shop_num() {
		return chan_shop_num;
	}

	public void setChan_shop_num(long chan_shop_num) {
		this.chan_shop_num = chan_shop_num;
	}

	public long getTotal_eval_num() {
		return total_eval_num;
	}

	public void setTotal_eval_num(long total_eval_num) {
		this.total_eval_num = total_eval_num;
	}

	public long getProd_num() {
		return prod_num;
	}

	public void setProd_num(long prod_num) {
		this.prod_num = prod_num;
	}

	public double getProd_price() {
		return prod_price;
	}

	public void setProd_price(double prod_price) {
		this.prod_price = prod_price;
	}

	public float getSale_amount_chain() {
		return sale_amount_chain;
	}

	public void setSale_amount_chain(float sale_amount_chain) {
		this.sale_amount_chain = sale_amount_chain;
	}

	public float getAmount_indu_ratio() {
		return amount_indu_ratio;
	}

	public void setAmount_indu_ratio(float amount_indu_ratio) {
		this.amount_indu_ratio = amount_indu_ratio;
	}

}
