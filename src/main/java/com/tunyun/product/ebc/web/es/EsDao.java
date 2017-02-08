
package com.tunyun.product.ebc.web.es;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tunyun.product.ebc.web.common.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * elasticSearch查询
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2015-10-29
 * @category com.tunyun.product.ebc.web.dao
 */
public class EsDao
{

	private static final Logger logger = LoggerFactory.getLogger(EsDao.class);
	/**
	 * Http请求URL
	 */
	private String url;
	
	//httpclient
	CloseableHttpClient httpclient;
	
	public EsDao(){
		httpclient = HttpClients.createDefault();
	}

	/**
	 * 返回对象
	 * 
	 * @param sql
	 * @param classz
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public <T> List<T> search(String sql, Class<T> classz)
	{
		T t = null;
		JSONObject _jsonObj = null;
		List<T> returnList = new ArrayList<T>();
		JSONObject _jsonObject = JSONObject.fromObject(search(sql));
		Object took = _jsonObject.get("took");// 查询耗时
		Object timeout = _jsonObject.get("timed_out");// 是否超时
		Object error = _jsonObject.get("error");// 错误信息
		Object status = _jsonObject.get("status");// 状态吗
		// 查询错误
		if (null != error)
		{
			logger.error("Es search Error[{}] : [{}]", status, error);
		}
		else
		{
			// 正常返回，并且没有超时
			if ((Boolean) timeout == false)
			{
				JSONArray _jsonArray = _jsonObject.getJSONObject("hits")
						.getJSONArray("hits");
				List<Object> _objectList = (List<Object>) JSONArray
						.toCollection(_jsonArray);
				for (Object obj : _objectList)
				{
					_jsonObj = JSONObject.fromObject(obj).getJSONObject("_source");
					t = (T) JSONObject.toBean(_jsonObj, classz);
					returnList.add(t);
				}
				logger.debug("Es search Success : time[{}]!", took);
			}
			else
			{
				logger.error("Es search Error : timed_out[{}]!", took);
			}
		}
		return returnList;
	}

	/**
	 * 返回json字符串
	 * 
	 * @param sql
	 * @return
	 */
	public String search(String sql)
	{
		String result = "";
		sql = CommonUtil.encodeUri(sql);
		// HttpParams params = client.getParams();
		// params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
		// params.setParameter(CoreProtocolPNames.HTTP_ELEMENT_CHARSET, "UTF-8");
		HttpGet get = new HttpGet(url + sql);
		CloseableHttpResponse response = null;
		try
		{
			response = httpclient.execute(get);
			// Header[] heads = response.getAllHeaders();
			// logger.info("=====================");
			// // 打印所有响应头
			// for (Header h : heads)
			// {
			// logger.info("{} : {}", h.getName(), h.getValue());
			// }
			// logger.info("=====================");
			HttpEntity entity = response.getEntity();
			if (entity != null)
			{
				InputStream ins = entity.getContent();
				result = CommonUtil.convertStreamToString(ins);
			}
		}
		catch (ClientProtocolException e)
		{
			logger.error("Es search error: {}", e);
		}
		catch (IOException e)
		{
			logger.error("Es search error: {}", e);
		}
		finally{
			try{
				response.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		logger.debug("Es search data: [{}]", result);
		return result;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
