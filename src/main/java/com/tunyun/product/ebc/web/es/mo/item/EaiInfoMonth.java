
package com.tunyun.product.ebc.web.es.mo.item;

import java.util.List;

/**
 * EAI-资讯表 eai_info_yyyymm
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2015年10月30日
 * @category com.tunyun.product.ebc.web.dao.mo
 */
public class EaiInfoMonth
{

	private long uid;
	private String title;// 标题
	private String orig_keyword;// 原始关键字
	private String orig_summary;// 原摘要
	private String orig_cont;// 原内容
	private String orig_label;// 原标签
	private int read_num;// 阅读数量
	private String url;
	private long c_time;// 时间
	private long a_time;// 时间
	private String collect_time_str;// yyyy-mm-dd hh:mm:ss
	private int img_num;// 图片数量
	private String images;// 图片Url地址列表
	private String imgs;// 图片Url地址列表
	private Object[] img_arr;// 图片地址列表
	private List<String> indu_ids;// 行业ID
	private long info_seed_id;// 来源ID
	private String info_seed_str;// 来源
	private String summary;// 分析摘要
	private String keyword;// 提取关键字
	private String label;// 提取标签
	private long info_dime_id;// 资讯纬度
	private String info_dime_str;//// 资讯纬度

	public long getUid()
	{
		return uid;
	}

	public void setUid(long uid)
	{
		this.uid = uid;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getOrig_keyword()
	{
		return orig_keyword;
	}

	public void setOrig_keyword(String orig_keyword)
	{
		this.orig_keyword = orig_keyword;
	}

	public String getOrig_summary()
	{
		return orig_summary;
	}

	public void setOrig_summary(String orig_summary)
	{
		this.orig_summary = orig_summary;
	}

	public String getOrig_cont()
	{
		return orig_cont;
	}

	public void setOrig_cont(String orig_cont)
	{
		this.orig_cont = orig_cont;
	}

	public String getOrig_label()
	{
		return orig_label;
	}

	public void setOrig_label(String orig_label)
	{
		this.orig_label = orig_label;
	}

	public int getRead_num()
	{
		return read_num;
	}

	public void setRead_num(int read_num)
	{
		this.read_num = read_num;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
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

	public String getCollect_time_str()
	{
		return collect_time_str;
	}

	public void setCollect_time_str(String collect_time_str)
	{
		this.collect_time_str = collect_time_str;
	}

	public int getImg_num()
	{
		return img_num;
	}

	public void setImg_num(int img_num)
	{
		this.img_num = img_num;
	}

	public String getImages()
	{
		return images;
	}

	public void setImages(String images)
	{
		this.images = images;
	}

	public String getImgs()
	{
		return imgs;
	}

	public void setImgs(String imgs)
	{
		this.imgs = imgs;
	}

	public Object[] getImg_arr()
	{
		return img_arr;
	}

	public void setImg_arr(Object[] img_arr)
	{
		this.img_arr = img_arr;
	}

	public List<String> getIndu_ids()
	{
		return indu_ids;
	}

	public void setIndu_ids(List<String> indu_ids)
	{
		this.indu_ids = indu_ids;
	}

	public long getInfo_seed_id()
	{
		return info_seed_id;
	}

	public void setInfo_seed_id(long info_seed_id)
	{
		this.info_seed_id = info_seed_id;
	}

	public String getInfo_seed_str()
	{
		return info_seed_str;
	}

	public void setInfo_seed_str(String info_seed_str)
	{
		this.info_seed_str = info_seed_str;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public long getInfo_dime_id()
	{
		return info_dime_id;
	}

	public void setInfo_dime_id(long info_dime_id)
	{
		this.info_dime_id = info_dime_id;
	}

	public String getInfo_dime_str()
	{
		return info_dime_str;
	}

	public void setInfo_dime_str(String info_dime_str)
	{
		this.info_dime_str = info_dime_str;
	}
}
