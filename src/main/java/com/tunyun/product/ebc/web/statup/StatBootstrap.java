package com.tunyun.product.ebc.web.statup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StatBootstrap {

	// log
	private static final Logger log = LoggerFactory.getLogger(StatBootstrap.class);

	private void startup(long time) {
		String packagePath = getClass().getPackage().getName().replaceAll("\\.", "/");
		try {
			ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
					"classpath*:" + packagePath + "/applicationContext_eai_ebc_web.xml");

		} catch (Exception e) {
			e.printStackTrace();
			log.error("spring init Exception: " + e.getMessage(), e);
			System.exit(1);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			new StatBootstrap().startup(Long.parseLong(args[0]));
		} else {
			new StatBootstrap().startup(System.currentTimeMillis() / 1000 - 24 * 3600);
		}
	}

}
