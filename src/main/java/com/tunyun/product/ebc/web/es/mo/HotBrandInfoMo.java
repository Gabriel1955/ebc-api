
package com.tunyun.product.ebc.web.es.mo;

/**
 * 热门品牌资讯
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2015年11月3日
 * @category com.tunyun.product.ebc.web.dao.mo
 */
public class HotBrandInfoMo
{

	private long bid;// 品牌ID
	private long info_id;// 资讯ID
	private long indu_id;// 资讯ID
	private long uid;// 资讯ID
	private long title_num;// 关注度
	private String titleName;// 品牌名称
	private String icon;// 品牌logo
	private String summary;// 摘要

	public long getBid()
	{
		return bid;
	}

	public void setBid(long bid)
	{
		this.bid = bid;
	}

	public long getInfo_id()
	{
		return info_id;
	}

	public void setInfo_id(long info_id)
	{
		this.info_id = info_id;
	}

	public long getTitle_num()
	{
		return title_num;
	}

	public void setTitle_num(long title_num)
	{
		this.title_num = title_num;
	}

	public String getTitleName()
	{
		return titleName;
	}

	public void setTitleName(String titleName)
	{
		this.titleName = titleName;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public long getUid() {
		return info_id;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
	
	public long getIndu_id() {
		return indu_id;
	}

	public void setIndu_id(long indu_id) {
		this.indu_id = indu_id;
	}

	/**
	 * 获取品牌详情
	 * @return
	 */
	public String getBrandInfoStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"uid\": \"").append(uid).append("\",");
		sb.append("\"indu_id\": \"").append(indu_id).append("\",");
		sb.append("\"icon\": \"").append(icon).append("\",");
		sb.append("\"titleName\": \"").append(titleName).append("\",");
		sb.append("\"title_num\": \"").append(title_num).append("\",");
		sb.append("\"summary\": \"").append(summary).append("\"");
		sb.append("}");
		
		return sb.toString();
	}

	public static void main(String[] args)
	{
		System.out.println(System.currentTimeMillis());
	}
}
