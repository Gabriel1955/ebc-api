
package com.tunyun.product.ebc.ebcapi;

import net.sf.json.JSONObject;

/**
 * @author gaojinbao
 * @version 1.0
 * @since 2016年1月14日
 * @category com.tunyun.product.ebc.ebcapi
 */
public class JsonTest
{

	public static void main(String[] args)
	{
		TestJson j = new TestJson();
		j.setId(691937097346584576L);
		System.out.println(JSONObject.fromObject(j).toString());
	}
}
