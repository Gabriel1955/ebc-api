
package com.tunyun.product.ebc.bgmanager.serv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tunyun.product.ebc.common.dao.CommonDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author gaojinbao
 * @version 1.0
 * @since 2016年4月12日
 * @category com.tunyun.product.ebc.bgmanager.serv
 */
public class CategoryServ
{

	private static String categoryJson = null;
	private CommonDao commonDao;

	public void setCommonDao(CommonDao commonDao)
	{
		this.commonDao = commonDao;
	}

	public String getCategoryJson()
	{
		return null == categoryJson ? categoryJson = getCategory() : categoryJson;
	}

	/**
	 * 获取品类
	 * 
	 * @return
	 */
	private String getCategory()
	{
		Map<String, List<Map<String, Object>>> catMap = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> cagList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> categList = this.commonDao.getTriCateg();
		Map<String, Object> cmap = null;
		for (Map<String, Object> map : categList)
		{
			cmap = new HashMap<String, Object>();
			cmap.put("cid", map.get("uid"));
			cmap.put("cname", map.get("cat_name"));
			cagList.add(cmap);
		}
		catMap.put("lenovoInput", cagList);
		return JSONObject.fromObject(catMap).toString();
	}

	public static void main(String[] args)
	{
		List<Map<String, Object>> categList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("1001", "qqq");
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("1002", "aaa");
		categList.add(map1);
		categList.add(map2);
		String ss = JSONArray.fromObject(categList).toString();
		System.out.println(ss);
	}
}
