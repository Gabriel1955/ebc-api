
package com.tunyun.product.ebc.web.es.mo;

/**
 * ESI行业+标签-最近30天 统计 表
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2015年11月3日
 * @category com.tunyun.product.ebc.web.dao.mo
 */
public class EsiInduFashBrand30dMo
{

	private long indu_id;// 行业ID
	private long bid;// 品牌ID
	private long stat_time;// 统计时间
	private String brand_name;// 品牌名称
	private float atte_degree;// 关注度
	private float appr_degree;// 褒贬度
	private long g_eval_num;// 正面
	private long m_eval_num;// 中性
	private long b_eval_num;// 负面

	public long getIndu_id()
	{
		return indu_id;
	}

	public void setIndu_id(long indu_id)
	{
		this.indu_id = indu_id;
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

	public String getBrand_name()
	{
		return brand_name;
	}

	public void setBrand_name(String brand_name)
	{
		this.brand_name = brand_name;
	}

	public float getAtte_degree()
	{
		return atte_degree;
	}

	public void setAtte_degree(float atte_degree)
	{
		this.atte_degree = atte_degree;
	}

	public float getAppr_degree()
	{
		return appr_degree;
	}

	public void setAppr_degree(float appr_degree)
	{
		this.appr_degree = appr_degree;
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
