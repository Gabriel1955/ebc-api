
package com.tunyun.product.ebc.bgmanager.statup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StatBootstrap
{

	// log
	private static final Logger log = LoggerFactory.getLogger(StatBootstrap.class);

	private void startup(long time)
	{
		String packagePath = getClass().getPackage().getName().replaceAll("\\.", "/");
		try
		{
			ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
					"classpath*:" + packagePath
							+ "/applicationContext_eai_ebc_bgmgr.xml");
//			AttributeValueAduitServ aas = ac.getBean("AttributeValueAduitServ",
//					AttributeValueAduitServ.class);
			//String attrJsonStrBody = "{ \"approvedAttr\": [ { \"vid\": 3001, \"v_alias\": [ { \"alias\": \"紫红色\" }, { \"alias\": \"红色\" }, { \"alias\": \"红色的\" }, { \"alias\": \"酒红色的\" }, { \"vid\": 3001, \"alias\": \"红白兰\", \"pid\": 9001, \"cid\": 1 } ], \"pid\": 9001, \"v_name\": \"红色\", \"cid\": 1 }, { \"vid\": 3002, \"v_alias\": [ { \"alias\": \"PPV材料\" }, { \"alias\": \"化学材质\" }, { \"alias\": \"工业化纤\" }, { \"alias\": \"工业材质\" }, { \"vid\": 3002, \"alias\": \"尼龙\", \"pid\": 9001, \"cid\": 1 }, { \"vid\": 3002, \"alias\": \"工业花扦\", \"pid\": 9001, \"cid\": 1 }, { \"vid\": 3002, \"alias\": \"纤维\", \"pid\": 9001, \"cid\": 1 } ], \"pid\": 9002, \"v_name\": \"工业材质\", \"cid\": 1 }, { \"v_alias\": [ { \"vid\": 3001, \"alias\": \"棕色\", \"pid\": 9001, \"cid\": 1 }, { \"vid\": 3001, \"alias\": \"银白\", \"pid\": 9001, \"cid\": 1 }, { \"vid\": 3001, \"alias\": \"墨绿\", \"pid\": 9001, \"cid\": 1 }, { \"vid\": 3001, \"alias\": \"橘黄\", \"pid\": 9001, \"cid\": 1 } ], \"v_name\": \"棕色\", \"vid\": 0, \"cid\": 1, \"pid\": 9001 } ], \"notApprovedAttr\": [], \"mergeRepeatAlias\": { \"v_alias\": [ { \"vid\": 3001, \"alias\": \"红色\", \"pid\": 9001, \"cid\": 1 }, { \"vid\": 3002, \"alias\": \"工业材质\", \"pid\": 9001, \"cid\": 1 } ] } }";
			//System.out.println(aas.saveAttributeValueAduitData(attrJsonStrBody));
//			String aa = aas.getAttributeValueAduit("{\"cid\":1}");
//			System.out.println(aa);
			//System.out.println(attrJsonStrBody);
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
			new StatBootstrap().startup(Long.parseLong(args[0]));
		}
		else
		{
			new StatBootstrap().startup(System.currentTimeMillis() / 1000 - 24 * 3600);
		}
	}
}
