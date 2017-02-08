
package com.tunyun.product.ebc.web.es.mo;

import java.util.List;

import net.sf.json.JSONArray;

/**
 * EAI-资讯表
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2015年10月30日
 * @category com.tunyun.product.ebc.web.dao.mo
 */
public class EaiInfoMo
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

	public int getImg_num()
	{
		return img_num;
	}

	public void setImg_num(int img_num)
	{
		this.img_num = img_num;
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

	public long getInfo_dime_id()
	{
		return info_dime_id;
	}

	public void setInfo_dime_id(long info_dime_id)
	{
		this.info_dime_id = info_dime_id;
	}

	public String getCollect_time_str()
	{
		return collect_time_str;
	}

	public void setCollect_time_str(String collect_time_str)
	{
		this.collect_time_str = collect_time_str;
	}

	public String getInfo_seed_str()
	{
		return info_seed_str;
	}

	public void setInfo_seed_str(String info_seed_str)
	{
		this.info_seed_str = info_seed_str;
	}

	public Object[] getImg_arr()
	{
		return img_arr;
	}

	public void setImg_arr(Object[] img_arr)
	{
		this.img_arr = img_arr;
	}

	public String getInfo_dime_str()
	{
		return info_dime_str;
	}

	public void setInfo_dime_str(String info_dime_str)
	{
		this.info_dime_str = info_dime_str;
	}

	/**
	 * 获取表格信息
	 * 
	 * @return
	 */
	public String getTableStr()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"title\": \"").append(title).append("\",");
		sb.append("\"uid\": \"").append(uid).append("\",");
		sb.append("\"read_num\": ").append(read_num);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 获取国内外趋势
	 * 
	 * @return
	 */
	public String getFashionTrend()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"uid\": \"").append(uid).append("\",");
		sb.append("\"title\": \"").append(title).append("\",");
		sb.append("\"read_num\": ").append(read_num);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 生成资讯信息
	 * 
	 * @return
	 */
	public String getEaiInfo()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"uid\": \"").append(uid).append("\",");
		sb.append("\"title\": \"").append(title).append("\",");
		sb.append("\"info_seed_str\": \"").append(info_seed_str).append("\",");
		sb.append("\"collect_time_str\": \"").append(collect_time_str).append("\",");
		sb.append("\"read_num\": \"").append(read_num).append("\",");
		sb.append("\"label\": \"").append(label).append("\",");
		sb.append("\"summary\": \"").append(summary).append("\",");
		sb.append("\"img_num\": ").append(img_num);
		/////
		//sb.append(",");
		//sb.append("\"img_arr\":[").append("\"http://wifilbs.cn:2330/5,325de20b3f62\",\"http://wifilbs.cn:2330/5,325de20b3f62\"]");
		if (img_num > 0)
		{
			sb.append(",");
			sb.append("\"img_arr\": [");
			if (null != img_arr)
			{
				for (int i = 0; i < img_arr.length; i++)
				{
					if (i > 0)
					{
						sb.append(",");
					}
					sb.append("\"");
					sb.append(img_arr[i].toString());
					sb.append("\"");
				}
			}
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}

	public static void main(String[] args)
	{
		// String json = "{ srcs: [ ../images/comp/tunyun.list2/u21.jpg,
		// ../images/comp/tunyun.list2/u21.jpg, ../images/comp/tunyun.list2/u21.jpg,
		// ../images/comp/tunyun.list2/u21.jpg ] }";
		// JSONObject jObject = JSONObject.fromObject(json);
		// JSONArray jArray = JSONArray.fromObject(jObject.get("srcs"));
		// String[] strs = (String[]) JSONArray.toArray(jArray, String.class);
		// System.out.println(strs);
		// // 互转
		// jArray = JSONArray.fromObject(strs);
		// System.out.println(jArray.toString());
		String[] arr = new String[2];
		arr[0] = "1111";
		arr[1] = "2222";
		JSONArray jObject = JSONArray.fromObject(arr);
		System.out.println(jObject.toString());
	}
}
