
package com.tunyun.product.ebc.web.es.mo.item;

/**
 * ESS行业+综合-最近30天 统计表 ess_indu_yyyy
 * 
 * @author czm
 */
public class EssInduYear
{

	// 行业ID
	private long indu_id;
	// 统计时间
	private long stat_time;
	// 销售总额
	private double sale_amount;
	// 销售总额环比
	private float sale_amount_chain;
	// 销售数量
	private long sale_num;
	// 销售数量环比
	private float sale_num_chain;
	// 店铺总数
	private long chan_shop_num;
	// 店铺总数环比
	private float chan_shop_num_chain;
	// 评价总数
	private long total_eval_num;
	// 评价总数环比
	private float total_eval_num_chain;
	// 产品总数
	private long prod_num;
	// 产品总数环比
	private float prod_num_chain;
	// 品单价
	private double prod_price;

	public long getIndu_id()
	{
		return indu_id;
	}

	public void setIndu_id(long indu_id)
	{
		this.indu_id = indu_id;
	}

	public long getStat_time()
	{
		return stat_time;
	}

	public void setStat_time(long stat_time)
	{
		this.stat_time = stat_time;
	}

	public double getSale_amount()
	{
		return sale_amount;
	}

	public void setSale_amount(double sale_amount)
	{
		this.sale_amount = sale_amount;
	}

	public float getSale_amount_chain()
	{
		return sale_amount_chain;
	}

	public void setSale_amount_chain(float sale_amount_chain)
	{
		this.sale_amount_chain = sale_amount_chain;
	}

	public long getSale_num()
	{
		return sale_num;
	}

	public void setSale_num(long sale_num)
	{
		this.sale_num = sale_num;
	}

	public float getSale_num_chain()
	{
		return sale_num_chain;
	}

	public void setSale_num_chain(float sale_num_chain)
	{
		this.sale_num_chain = sale_num_chain;
	}

	public long getChan_shop_num()
	{
		return chan_shop_num;
	}

	public void setChan_shop_num(long chan_shop_num)
	{
		this.chan_shop_num = chan_shop_num;
	}

	public float getChan_shop_num_chain()
	{
		return chan_shop_num_chain;
	}

	public void setChan_shop_num_chain(float chan_shop_num_chain)
	{
		this.chan_shop_num_chain = chan_shop_num_chain;
	}

	public long getTotal_eval_num()
	{
		return total_eval_num;
	}

	public void setTotal_eval_num(long total_eval_num)
	{
		this.total_eval_num = total_eval_num;
	}

	public float getTotal_eval_num_chain()
	{
		return total_eval_num_chain;
	}

	public void setTotal_eval_num_chain(float total_eval_num_chain)
	{
		this.total_eval_num_chain = total_eval_num_chain;
	}

	public long getProd_num()
	{
		return prod_num;
	}

	public void setProd_num(long prod_num)
	{
		this.prod_num = prod_num;
	}

	public float getProd_num_chain()
	{
		return prod_num_chain;
	}

	public void setProd_num_chain(float prod_num_chain)
	{
		this.prod_num_chain = prod_num_chain;
	}

	public double getProd_price()
	{
		return prod_price;
	}

	public void setProd_price(double prod_price)
	{
		this.prod_price = prod_price;
	}
}
