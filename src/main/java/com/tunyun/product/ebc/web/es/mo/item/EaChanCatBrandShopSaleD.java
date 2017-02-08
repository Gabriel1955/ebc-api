
package com.tunyun.product.ebc.web.es.mo.item;

/**
 * 分析-渠道-品类-品牌-店铺销售天表-最近30天 ea_chan_cat_brand_shop_sale_d
 * 
 * @author czm
 */
public class EaChanCatBrandShopSaleD
{

	// 渠道ID
	private long chan_id;
	// 店铺ID
	private long chan_shop_id;
	// 品类ID
	private long cid;
	// 品牌ID
	private long bid;
	// 统计时间
	private long stat_time;
	// 销售数量
	private long sale_num;
	// 销售总额
	private double sale_amount;
	// 评价销售总额
	private double eval_sale_amount;
	// 评价总数
	private long total_eval_num;
	// 好评总数
	private long g_eval_num;
	// 中评总数
	private long m_eval_num;
	// 差评总数
	private long b_eval_num;

	public long getChan_id()
	{
		return chan_id;
	}

	public void setChan_id(long chan_id)
	{
		this.chan_id = chan_id;
	}

	public long getChan_shop_id()
	{
		return chan_shop_id;
	}

	public void setChan_shop_id(long chan_shop_id)
	{
		this.chan_shop_id = chan_shop_id;
	}

	public long getCid()
	{
		return cid;
	}

	public void setCid(long cid)
	{
		this.cid = cid;
	}

	public long getBid()
	{
		return bid;
	}

	public void setBid(long bid)
	{
		this.bid = bid;
	}

	public long getStat_time()
	{
		return stat_time;
	}

	public void setStat_time(long stat_time)
	{
		this.stat_time = stat_time;
	}

	public long getSale_num()
	{
		return sale_num;
	}

	public void setSale_num(long sale_num)
	{
		this.sale_num = sale_num;
	}

	public double getSale_amount()
	{
		return sale_amount;
	}

	public void setSale_amount(double sale_amount)
	{
		this.sale_amount = sale_amount;
	}

	public double getEval_sale_amount()
	{
		return eval_sale_amount;
	}

	public void setEval_sale_amount(double eval_sale_amount)
	{
		this.eval_sale_amount = eval_sale_amount;
	}

	public long getTotal_eval_num()
	{
		return total_eval_num;
	}

	public void setTotal_eval_num(long total_eval_num)
	{
		this.total_eval_num = total_eval_num;
	}

	public long getG_eval_num()
	{
		return g_eval_num;
	}

	public void setG_eval_num(long g_eval_num)
	{
		this.g_eval_num = g_eval_num;
	}

	public long getM_eval_num()
	{
		return m_eval_num;
	}

	public void setM_eval_num(long m_eval_num)
	{
		this.m_eval_num = m_eval_num;
	}

	public long getB_eval_num()
	{
		return b_eval_num;
	}

	public void setB_eval_num(long b_eval_num)
	{
		this.b_eval_num = b_eval_num;
	}
}
