
package com.tunyun.product.ebc.web.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常量
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2015年11月9日
 * @category com.tunyun.product.ebc.web.common
 */
public class Constants
{

	/**
	 * 图片url地址分隔符
	 */
	public static final char separate_2 = (char) 1;
	/**
	 * URL正则匹配表达式
	 */
	public static final String check_url = "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";

	public static void main(String[] args)
	{
		Matcher matcher = null;
		Pattern pattern = Pattern.compile(check_url, Pattern.CASE_INSENSITIVE);
		StringBuffer sb = new StringBuffer();
		sb.append("http://wifilbs.cn:2330/2,12b63921a2\\").append(separate_2);
		sb.append("http://wifilbs.cn:2330/1,0e888bfde9\\").append(separate_2);
		sb.append("23432fsdffsffs");
		if (null != sb && !"".equals(sb.toString().trim()))
		{
			System.out.println("Imgs =======" + sb.toString());
			List<String> imgArr = new ArrayList<String>();
			String[] _imgArr = sb.toString().split(String.valueOf(Constants.separate_2));
			for (String _img : _imgArr)
			{
				matcher = pattern.matcher(_img);
				if (matcher.find())
				{
					imgArr.add(_img);
				}
			}
			Object[] objArr = imgArr.toArray();
			// 打印
			for (Object img : objArr)
			{
				System.out.println("===" + img);
			}
		}
	}
}
