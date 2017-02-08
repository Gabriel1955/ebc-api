
package com.tunyun.product.ebc.bgmanager.serv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tunyun.product.ebc.common.dao.CommonDao;

import net.sf.json.JSONArray;

/**
 * 获取渠道
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年4月14日
 * @category com.tunyun.product.ebc.bgmanager.serv
 */
public class ChannelServ
{

	private static Logger logger = LoggerFactory.getLogger(ChannelServ.class);
	private static Map<Long, String> channelMap = null;
	private CommonDao commonDao;

	public void setCommonDao(CommonDao commonDao)
	{
		this.commonDao = commonDao;
	}

	public String getChannelJson()
	{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<Long, String> map = getChannelMap();
		if (null != map)
		{
			for (Entry<Long, String> entry : map.entrySet())
			{
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("chanid", entry.getKey());
				m.put("chaname", entry.getValue());
				list.add(m);
			}
			String json = JSONArray.fromObject(list).toString();
			logger.info("getChannelJson:{}", json);
			return json;
		}
		return "";
	}

	public Map<Long, String> getChannelMap()
	{
		return null == channelMap ? channelMap = getChannel() : channelMap;
	}

	private Map<Long, String> getChannel()
	{
		Map<Long, String> chanMap = new HashMap<Long, String>();
		List<Map<String, Object>> chanList = commonDao.geTrTenantChan();
		for (Map<String, Object> map : chanList)
		{
			long chan_id = Long.valueOf(String.valueOf(map.get("chan_id")));
			String chan_name = (String) map.get("chan_name");
			chanMap.put(chan_id, chan_name);
		}
		return chanMap;
	}
}
