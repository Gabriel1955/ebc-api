
package com.tunyun.product.ebc.common.util;

/**
 * Id生成工具类
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年4月20日
 * @category com.tunyun.product.ebc.common.util
 */
public class GenerateIdUtil
{

	/**
	 * 生成ID方法 规则：取字符串Ascii码前后各五位组成十位数的Long型，如果串长度小于五位后面补0，补足十位
	 * 
	 * @param identifier
	 * @return
	 */
	public static long generateId(String identifier)
	{
		String ascii = stringToAscii(identifier);
		ascii = ascii.replaceAll(",", "");
		if (ascii.length() > 5)
		{
			String b_ascii = ascii.substring(0, 5);
			String a_ascii = ascii.substring(ascii.length() - 5);
			return Long.valueOf(b_ascii.concat(a_ascii));
		}
		else
		{
			return Long.valueOf(ascii) * (long) Math.pow(10, 10 - ascii.length());
		}
	}

	public static String stringToAscii(String value)
	{
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (i != chars.length - 1)
			{
				sbu.append((int) chars[i]).append(",");
			}
			else
			{
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}

	public static String asciiToString(String value)
	{
		StringBuffer sbu = new StringBuffer();
		String[] chars = value.split(",");
		for (int i = 0; i < chars.length; i++)
		{
			sbu.append((char) Integer.parseInt(chars[i]));
		}
		return sbu.toString();
	}

	public static void main(String[] args)
	{
		System.out.println(stringToAscii("2016c#年夏季"));
		System.out.println(generateId("2016年夏季"));
		System.out.println(stringToAscii("2016年春季"));
		System.out.println(generateId("2011年春季"));
		System.out.println(stringToAscii("2013年夏季"));
		System.out.println(generateId("2013年夏季"));
		System.out.println(asciiToString("26149"));
	}
}
