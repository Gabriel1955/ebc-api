
package com.tunyun.product.ebc.web.es.mo;

/**
 * EAI-资讯品牌表
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2015年11月3日
 * @category com.tunyun.product.ebc.web.dao.mo
 */
public class EaiInfoBrand
{

	private long info_id;
	private long indu_id;// 行业ID
	private long stat_time;// 统计时间
	private long bid;// 品牌ID
	private String brand_name;// 品牌名称
	private long info_dime_id;// 资讯纬度

	public long getInfo_id()
	{
		return info_id;
	}

	public void setInfo_id(long info_id)
	{
		this.info_id = info_id;
	}

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

	public long getBid()
	{
		return bid;
	}

	public void setBid(long bid)
	{
		this.bid = bid;
	}

	public String getBrand_name()
	{
		return brand_name;
	}

	public void setBrand_name(String brand_name)
	{
		this.brand_name = brand_name;
	}

	public long getInfo_dime_id()
	{
		return info_dime_id;
	}

	public void setInfo_dime_id(long info_dime_id)
	{
		this.info_dime_id = info_dime_id;
	}
}
