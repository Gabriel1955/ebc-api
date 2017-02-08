
package com.tunyun.product.ebc.web.es.mo;

/**
 * @author gaojinbao
 * @version 1.0
 * @since 2015年10月29日
 * @category com.tunyun.product.ebc.web.dao.mo
 */
public class Product
{

	private long uuid;
	private String name;
	private String title;
	private long channel_id;
	private long cid;
	private long bid;
	private int shop_id;

	public long getUuid()
	{
		return uuid;
	}

	public void setUuid(long uuid)
	{
		this.uuid = uuid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public long getChannel_id()
	{
		return channel_id;
	}

	public void setChannel_id(long channel_id)
	{
		this.channel_id = channel_id;
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

	public int getShop_id()
	{
		return shop_id;
	}

	public void setShop_id(int shop_id)
	{
		this.shop_id = shop_id;
	}
}
