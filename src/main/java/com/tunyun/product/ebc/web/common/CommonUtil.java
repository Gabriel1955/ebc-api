
package com.tunyun.product.ebc.web.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * @author gaojinbao
 * @version 1.0
 * @since 2015-10-29
 * @category com.tunyun.product.ebc.web
 */
public class CommonUtil
{

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * 解析返回数值
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String convertStreamToString(InputStream is)
	{
		String line = null;
		StringBuilder sb = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is, "UTF-8"));
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		}
		catch (IOException e)
		{
			logger.error("return search error : {}", e);
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				logger.error("return search error : {}", e.getMessage());
			}
		}
		logger.debug("return search data : [{}]", sb.toString());
		return sb.toString();
	}

	/**
	 * 实现和javascript encodeUri 相同的效果
	 * 
	 * @param source
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeUri(String source)
	{
		String temp = null;
		try
		{
			temp = URLEncoder.encode(source, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			logger.debug("URL Encode error : [{}]", e);
		}
		return temp.replaceAll("\\+", "%20");
	}

	/**
	 * 获取指定格式化时间
	 * 
	 * @param pattern
	 *            指定格式化
	 * @param amount
	 *            指定特定天数+1代表以后一天，-1代表前一天
	 * @return
	 */
	public static String format(String pattern, int amount)
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, amount);
		return new SimpleDateFormat(pattern).format(cal.getTime());
	}

	/**
	 * 将制定日期格式字符串装换成时间戳（单位：秒）
	 * 
	 * @param formatData
	 * @param pattern
	 * @return
	 */
	public static long formatDataTotimestampForSecond(String formatData, String pattern)
	{
		long timestamp = 0L;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try
		{
			timestamp = sdf.parse(formatData).getTime();
		}
		catch (ParseException e)
		{
			logger.info("format turn fail,parseException:{}", e);
		}
		return timestamp / 1000;
	}

	/**
	 * 获取指定格式化时间
	 * 
	 * @param pattern
	 *            指定格式化
	 * @param timeMillis
	 *            指定时间毫秒数
	 * @return
	 */
	public static String format(String pattern, long timeMillis)
	{
		return new SimpleDateFormat(pattern).format(new Date(timeMillis));
	}

	/**
	 * 从json中获取参数
	 * 
	 * @param _jsonObject
	 * @param name
	 * @return
	 */
	public static Long getJsonParam(JSONObject _jsonObject, String name)
	{
		Long ret = 0l;
		Object str = _jsonObject.get(name);
		if (str != null && !"".equals(String.valueOf(str))
				&& !"null".equalsIgnoreCase(String.valueOf(str)))
		{
			ret = Long.parseLong(String.valueOf(str));
		}
		return ret;
	}

	/**
	 * 从json中获取参数
	 * 
	 * @param _jsonObject
	 * @param name
	 * @return
	 */
	public static String getJsonParamForString(JSONObject _jsonObject, String name)
	{
		String str = null;
		Object obj = _jsonObject.get(name);
		if (obj != null && !"".equals(String.valueOf(obj))
				&& !"null".equalsIgnoreCase(String.valueOf(obj)))
		{
			str = String.valueOf(obj);
		}
		return str;
	}

	/**
	 * 数字格式化
	 */
	public static Map<String, DecimalFormat> dfmap = new HashMap<String, DecimalFormat>();

	static
	{
		dfmap.put("##", new DecimalFormat("##"));
		dfmap.put("##.0", new DecimalFormat("##.0"));
		dfmap.put("##.00", new DecimalFormat("##.00"));
		dfmap.put("#,###", new DecimalFormat("#,###"));
		dfmap.put("#,###.00", new DecimalFormat("#,###.00"));
	}

	public static String DecimalFormat(String pattern, Object data)
	{
		return dfmap.get(pattern) == null ? String.valueOf(data)
				: dfmap.get(pattern).format(data);
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException
	{
		// Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE, -1);
		// System.out.println(cal.getTime().getTime());
		// String yesterday = new SimpleDateFormat("yyyyMMdd ").format(cal.getTime());
		// System.out.println(yesterday);
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// System.out.println(sdf.parse("20160112").getTime());
		// System.out.println(10000000758L / 1000);
		// 格式化
		//double d = 0.012845493;
		//System.out.println(CommonUtil.DecimalFormat("##.00", d));
		System.out.println(CommonUtil.encodeUri("SELECT * FROM ess_indu_prod_chan_2016/20160627"));
	}
}
