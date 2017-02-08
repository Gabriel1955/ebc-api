package com.tunyun.product.ebc.web.es.mo.item;

/**
 * ESS行业+综合+产品-最近30天 统计表 ess_indu_prod_yyyy
 * 
 * @author czm
 *
 */
public class EssInduProdYear {

	// 行业ID
	private long indu_id;

	// 产品ID
	private long prod_id;

	// 品类ID
	private long cid;

	// 品牌ID
	private long bid;

	// 统计时间
	private long stat_time;

	// 产品名称
	private String prod_name;

	// 品类名称
	private String cat_name;

	// 品牌名称
	private String brand_name;

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

	// 好评总数
	private long g_eval_num;

	// 中评总数
	private long m_eval_num;

	// 差评总数
	private long b_eval_num;

	// 好评占比
	private float g_eval_num_ratio;

	// 中评占比
	private float m_eval_num_ratio;

	// 差评占比
	private float b_eval_num_ratio;

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

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
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

	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
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

	public long getG_eval_num() {
		return g_eval_num;
	}

	public void setG_eval_num(long g_eval_num) {
		this.g_eval_num = g_eval_num;
	}

	public long getM_eval_num() {
		return m_eval_num;
	}

	public void setM_eval_num(long m_eval_num) {
		this.m_eval_num = m_eval_num;
	}

	public long getB_eval_num() {
		return b_eval_num;
	}

	public void setB_eval_num(long b_eval_num) {
		this.b_eval_num = b_eval_num;
	}

	public float getG_eval_num_ratio() {
		return g_eval_num_ratio;
	}

	public void setG_eval_num_ratio(float g_eval_num_ratio) {
		this.g_eval_num_ratio = g_eval_num_ratio;
	}

	public float getM_eval_num_ratio() {
		return m_eval_num_ratio;
	}

	public void setM_eval_num_ratio(float m_eval_num_ratio) {
		this.m_eval_num_ratio = m_eval_num_ratio;
	}

	public float getB_eval_num_ratio() {
		return b_eval_num_ratio;
	}

	public void setB_eval_num_ratio(float b_eval_num_ratio) {
		this.b_eval_num_ratio = b_eval_num_ratio;
	}

}
