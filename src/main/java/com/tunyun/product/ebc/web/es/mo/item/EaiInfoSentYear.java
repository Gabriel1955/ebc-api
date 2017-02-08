
package com.tunyun.product.ebc.web.es.mo.item;

/**
 * @author gaojinbao
 * @version 1.0
 * @since 2016年1月26日
 * @category com.tunyun.product.ebc.web.es.mo.item
 */
public class EaiInfoSentYear
{

	// 资讯ID
	private long info_id;
	// 归属行业ID列表
	private String indu_ids;
	// 爬虫时间
	private long c_time;
	// 分析时间
	private long a_time;
	// 舆论名称
	private String sent_name;
	// 舆论极性
	private int sent_polary;
	// 资讯纬度
	private int info_dime_id;

	public long getInfo_id()
	{
		return info_id;
	}

	public void setInfo_id(long info_id)
	{
		this.info_id = info_id;
	}

	public String getIndu_ids()
	{
		return indu_ids;
	}

	public void setIndu_ids(String indu_ids)
	{
		this.indu_ids = indu_ids;
	}

	public long getC_time()
	{
		return c_time;
	}

	public void setC_time(long c_time)
	{
		this.c_time = c_time;
	}

	public long getA_time()
	{
		return a_time;
	}

	public void setA_time(long a_time)
	{
		this.a_time = a_time;
	}

	public String getSent_name()
	{
		return sent_name;
	}

	public void setSent_name(String sent_name)
	{
		this.sent_name = sent_name;
	}

	public int getSent_polary()
	{
		return sent_polary;
	}

	public void setSent_polary(int sent_polary)
	{
		this.sent_polary = sent_polary;
	}

	public int getInfo_dime_id()
	{
		return info_dime_id;
	}

	public void setInfo_dime_id(int info_dime_id)
	{
		this.info_dime_id = info_dime_id;
	}
}
