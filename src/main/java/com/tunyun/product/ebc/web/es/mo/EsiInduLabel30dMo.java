
package com.tunyun.product.ebc.web.es.mo;

/**
 * ESI行业+标签-最近30天 统计 表
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2015年10月30日
 * @category com.tunyun.product.ebc.web.dao.mo
 */
public class EsiInduLabel30dMo
{

	private long indu_id;// 行业ID
	private String label;// 标签
	private long stat_time;// 统计时间
	private int label_num;// 标签数量
	private int label_polary;// 标签词性
	private long info_dime_id;// 资讯纬度
	// 前端展示属性
	private String text;// 对应label
	private float weight;// 对应标签数量

	public long getIndu_id()
	{
		return indu_id;
	}

	public void setIndu_id(long indu_id)
	{
		this.indu_id = indu_id;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public long getStat_time()
	{
		return stat_time;
	}

	public void setStat_time(long stat_time)
	{
		this.stat_time = stat_time;
	}

	public int getLabel_num()
	{
		return label_num;
	}

	public void setLabel_num(int label_num)
	{
		this.label_num = label_num;
	}

	public int getLabel_polary()
	{
		return label_polary;
	}

	public void setLabel_polary(int label_polary)
	{
		this.label_polary = label_polary;
	}

	public long getInfo_dime_id()
	{
		return info_dime_id;
	}

	public void setInfo_dime_id(long info_dime_id)
	{
		this.info_dime_id = info_dime_id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public float getWeight()
	{
		return weight;
	}

	public void setWeight(float weight)
	{
		this.weight = weight;
	}
	
	/**
	 * 获取输出表格信息
	 * @return
	 */
	public String getTableStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"text\": \"").append(label).append("\",");
		sb.append("\"weight\": \"").append(label_num).append("\",");
		sb.append("\"link\": \"{href: '#', target: '_blank'}\"");
		sb.append("}");
		
		return sb.toString();
	}
}
