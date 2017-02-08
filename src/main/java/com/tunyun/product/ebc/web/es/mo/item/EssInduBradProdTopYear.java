package com.tunyun.product.ebc.web.es.mo.item;

/**
 * ESS行业+品牌+产品-销量TOP最近30天综合 统计表   ess_indu_brad_prod_top_yyyy 
 * @author czm
 *
 */
public class EssInduBradProdTopYear {

	//行业ID
	private long indu_id;
	
	//品牌ID
	private long bid;
	
	//产品ID
	private long prod_id;
	
	//统计时间
	private long stat_time;
	
	//品牌名称
	private String brand_name;
	
	//产品名称
	private String prod_name;
	
	//销售总额
	private double sale_amount;
	
	//是否畅销
	private boolean is_fast_seller;

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

	public long getProd_id() {
		return prod_id;
	}

	public void setProd_id(long prod_id) {
		this.prod_id = prod_id;
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

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public double getSale_amount() {
		return sale_amount;
	}

	public void setSale_amount(double sale_amount) {
		this.sale_amount = sale_amount;
	}

	public boolean isIs_fast_seller() {
		return is_fast_seller;
	}

	public void setIs_fast_seller(boolean is_fast_seller) {
		this.is_fast_seller = is_fast_seller;
	}
	
}
