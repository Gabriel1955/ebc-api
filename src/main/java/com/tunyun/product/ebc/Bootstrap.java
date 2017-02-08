
package com.tunyun.product.ebc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动应用
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年4月11日
 * @category com.tunyun.product.ebc
 */
public class Bootstrap
{

	// log
	private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

	private void startup(long time)
	{
		try
		{
			new ClassPathXmlApplicationContext(
					"classpath:/applicationContext_public.xml");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("spring init Exception: " + e.getMessage(), e);
			System.exit(1);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		if (args != null && args.length > 0)
		{
			new Bootstrap().startup(Long.parseLong(args[0]));
		}
		else
		{
			new Bootstrap().startup(System.currentTimeMillis() / 1000 - 24 * 3600);
		}
	}
}
