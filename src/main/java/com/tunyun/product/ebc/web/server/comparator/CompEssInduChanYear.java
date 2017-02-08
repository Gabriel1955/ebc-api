
package com.tunyun.product.ebc.web.server.comparator;

import java.util.Comparator;

import com.tunyun.product.ebc.web.es.mo.item.EssInduChanYear;

/**
 * @author gaojinbao
 * @version 1.0
 * @since 2016年1月15日
 * @category com.tunyun.product.ebc.web.server.comparator
 */
public class CompEssInduChanYear implements Comparator<EssInduChanYear>
{

	@Override
	public int compare(EssInduChanYear o1, EssInduChanYear o2)
	{
		if (o1.getStat_time() > o2.getStat_time())
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
}
