
package com.tunyun.product.ebc.web.server.induanalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tunyun.product.ebc.web.common.CommonUtil;
import com.tunyun.product.ebc.web.es.mo.item.EaiInfoMonth;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBrandYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduCatYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduChanYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduProdYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduYear;
import com.tunyun.product.ebc.web.es.mo.lable.EseInduYear;
import com.tunyun.product.ebc.web.server.comparator.CompEssInduChanYear;

/**
 * 行业分析-综合分析
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年1月15日
 * @category com.tunyun.product.ebc.web.server.induanalysis
 */
public class ComplexAnalysis
{

	Logger logger = LoggerFactory.getLogger(ComplexAnalysis.class);

	// 行业分析-行业综合分析-渠道销售动态趋势
	public Map<String, List<Object>> getChannelSalesDyna(List<EssInduChanYear> list)
	{
		logger.info("{}", list);
		Map<Object, List<String>> groupByStatime = new HashMap<Object, List<String>>();
		Map<Object, List<EssInduChanYear>> groupByChanIdMap = new HashMap<Object, List<EssInduChanYear>>();
		// 按照渠道进行聚类
		for (EssInduChanYear eic : list)
		{
			// 统计时间
			String stat_time = CommonUtil.format("MM-dd", eic.getStat_time() * 1000L);
			if (groupByChanIdMap.get(eic.getChan_id()) != null)
			{
				// 保证渠道一天只有一天记录
				if (!groupByStatime.get(eic.getChan_id()).contains(stat_time))
				{
					groupByStatime.get(eic.getChan_id()).add(stat_time);
					groupByChanIdMap.get(eic.getChan_id()).add(eic);
				}
			}
			else
			{
				// groupByStatime
				List<String> statimeList = new ArrayList<String>();
				statimeList.add(stat_time);
				groupByStatime.put(eic.getChan_id(), statimeList);
				// groupByChanIdMap
				List<EssInduChanYear> itemList = new ArrayList<EssInduChanYear>();
				itemList.add(eic);
				groupByChanIdMap.put(eic.getChan_id(), itemList);
			}
		}
		// 组装series-json对象
		Map<String, List<Object>> JMap = new HashMap<String, List<Object>>();
		List<Object> JSeriesList = new ArrayList<Object>();
		for (Entry<Object, List<EssInduChanYear>> entry : groupByChanIdMap.entrySet())
		{
			Map<String, Object> JSeriesMap = new HashMap<String, Object>();
			List<Double> JSeriesDataList = new ArrayList<Double>();
			List<EssInduChanYear> eicList = entry.getValue();
			Collections.sort(eicList, new CompEssInduChanYear());// 排序
			for (int i = 0; i < eicList.size(); i++)
			{
				if (i == 0)
				{
					JSeriesMap.put("name", eicList.get(i).getChan_name());
				}
				JSeriesDataList.add(eicList.get(i).getSale_amount());
			}
			JSeriesMap.put("data", JSeriesDataList);
			JSeriesList.add(JSeriesMap);
		}
		JMap.put("series", JSeriesList);
		//// 组装categories-json对象
		List<Object> JCategoriesList = new ArrayList<Object>();
		for (Entry<Object, List<String>> entry : groupByStatime.entrySet())
		{
			List<String> statimeList = entry.getValue();
			Collections.sort(statimeList);// 排序
			for (String item : statimeList)
			{
				JCategoriesList.add(item);
			}
			break;
		}
		JMap.put("categories", JCategoriesList);
		return JMap;
	}

	// 行业分析-行业综合分析-品类/品牌风向标
	public List<Map<String, Object>> getBrandAndCatVane(List<EaiInfoMonth> list)
	{
		List<Map<String, Object>> jList = new ArrayList<Map<String, Object>>();
		Map<String, Object> jMap = null;
		for (EaiInfoMonth eim : list)
		{
			jMap = new HashMap<String, Object>();
			jMap.put("uid", eim.getUid());
			jMap.put("title", eim.getTitle());
			jMap.put("read_num", eim.getRead_num());
			jList.add(jMap);
		}
		return jList;
	}

	// 行业分析-行业综合分析-渠道销售动态+渠道销售额+渠道用户评价
	public List<String> getChannelSalesRatio(List<EssInduChanYear> list)
	{
		List<String> jList = new ArrayList<String>();
		StringBuffer JItem = new StringBuffer();
		for (EssInduChanYear eic : list)
		{
			JItem.delete(0, JItem.length());
			JItem.append("[");
			JItem.append("\"").append(eic.getChan_name()).append("\",")
					.append(eic.getSale_amount());
			JItem.append("]");
			jList.add(JItem.toString());
		}
		return jList;
	}

	public List<Map<String, Object>> getChannelEvaluate(List<EssInduChanYear> list)
	{
		List<Map<String, Object>> jList = new ArrayList<Map<String, Object>>();
		Map<String, Object> JItem = null;
		for (EssInduChanYear eic : list)
		{
			JItem = new HashMap<String, Object>();
			JItem.put("ebName", eic.getChan_name());
			JItem.put("evaluateNum",
					CommonUtil.DecimalFormat("#,###", eic.getTotal_eval_num()));
			JItem.put("goodsEv",
					CommonUtil.DecimalFormat("##", eic.getG_eval_num_ratio()) + "%");
			JItem.put("neutralEv",
					CommonUtil.DecimalFormat("##", eic.getM_eval_num_ratio()) + "%");
			JItem.put("badEv",
					CommonUtil.DecimalFormat("##", eic.getB_eval_num_ratio()) + "%");
			jList.add(JItem);
		}
		return jList;
	}

	// 行业分析-行业综合分析-热销品牌榜+品牌销售占比
	public List<List<Object>> getHotSalesBrandTop(List<EssInduBrandYear> list)
	{
		List<List<Object>> jList = new ArrayList<List<Object>>();
		List<Object> JItemList = null;
		int No = 1;
		for (EssInduBrandYear eib : list)
		{
			JItemList = new ArrayList<Object>();
			JItemList.add(No++);
			JItemList.add(eib.getBrand_name());
			JItemList.add(CommonUtil.DecimalFormat("#,###.00", eib.getSale_amount()));
			StringBuffer itemStr = new StringBuffer();
			itemStr.append("[");
			itemStr.append("\"")
					.append(CommonUtil.DecimalFormat("##.00", eib.getSale_amount_chain()))
					.append("%\",").append(eib.getSale_amount_chain() > 0 ? 1 : 0)
					.append("]");
			JItemList.add(itemStr.toString());
			JItemList.add(
					CommonUtil.DecimalFormat("##.0", eib.getAmount_indu_ratio()) + "%");
			jList.add(JItemList);
		}
		return jList;
	}

	public List<String> getHotSalesBrandRatio(List<EssInduBrandYear> list)
	{
		List<String> jList = new ArrayList<String>();
		StringBuffer JItem = new StringBuffer();
		for (EssInduBrandYear eib : list)
		{
			JItem.delete(0, JItem.length());
			JItem.append("[");
			JItem.append("\"").append(eib.getBrand_name()).append("\",")
					.append(eib.getAmount_indu_ratio());
			JItem.append("]");
			jList.add(JItem.toString());
		}
		return jList;
	}

	// 行业分析-行业综合分析-热销品类榜+品类销售占比
	public List<List<Object>> getHotSalesCategoryTop(List<EssInduCatYear> list)
	{
		List<List<Object>> jList = new ArrayList<List<Object>>();
		List<Object> JItemList = null;
		int No = 1;
		for (EssInduCatYear eic : list)
		{
			JItemList = new ArrayList<Object>();
			JItemList.add(No++);
			JItemList.add(eic.getCat_name());
			JItemList.add(CommonUtil.DecimalFormat("#,###.00", eic.getSale_amount()));
			StringBuffer itemStr = new StringBuffer();
			itemStr.append("[");
			itemStr.append("\"")
					.append(CommonUtil.DecimalFormat("##.00", eic.getSale_amount_chain()))
					.append("%\",").append(eic.getSale_amount_chain() > 0 ? 1 : 0)
					.append("]");
			JItemList.add(itemStr.toString());
			JItemList.add(
					CommonUtil.DecimalFormat("##.0", eic.getAmount_indu_ratio()) + "%");
			jList.add(JItemList);
		}
		return jList;
	}

	public List<String> getHotSalesCategoryRatio(List<EssInduCatYear> list)
	{
		List<String> jList = new ArrayList<String>();
		StringBuffer JItem = new StringBuffer();
		for (EssInduCatYear eic : list)
		{
			JItem.delete(0, JItem.length());
			JItem.append("[");
			JItem.append("\"").append(eic.getCat_name()).append("\",")
					.append(eic.getAmount_indu_ratio());
			JItem.append("]");
			jList.add(JItem.toString());
		}
		return jList;
	}

	// 行业分析-行业综合分析-热销商品榜
	public List<List<Object>> getHotSalesGoodsTop(List<EssInduProdYear> list)
	{
		List<List<Object>> jList = new ArrayList<List<Object>>();
		List<Object> JItemList = null;
		int No = 1;
		for (EssInduProdYear eip : list)
		{
			JItemList = new ArrayList<Object>();
			JItemList.add(No++);
			JItemList.add(eip.getProd_name());
			JItemList.add(CommonUtil.DecimalFormat("#,###.00", eip.getSale_amount()));
			StringBuffer itemStr = new StringBuffer();
			itemStr.append("[");
			itemStr.append("\"")
					.append(CommonUtil.DecimalFormat("##.00", eip.getSale_amount_chain()))
					.append("%\",").append(eip.getSale_amount_chain() > 0 ? 1 : 0)
					.append("]");
			JItemList.add(itemStr.toString());
			JItemList.add(CommonUtil.DecimalFormat("#,###", eip.getSale_num()));
			JItemList.add(eip.getCat_name());
			JItemList.add(eip.getBrand_name());
			jList.add(JItemList);
		}
		return jList;
	}

	// 行业分析-行业综合分析-汇总统计Sql
	public List<Map<String, Object>> getComprehensiveIndicator(EssInduYear eiy)
	{
		int unit = 10000;
		List<Map<String, Object>> jList = new ArrayList<Map<String, Object>>();
		Map<String, Object> jMap1 = new HashMap<String, Object>();
		if (eiy.getSale_amount() > unit)
		{
			jMap1.put("indicator_name", "30天销售额(万元)");
			jMap1.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eiy.getSale_amount() / unit));
		}
		else
		{
			jMap1.put("indicator_name", "30天销售额");
			jMap1.put("indicator_value", CommonUtil.DecimalFormat("#,###",
					Math.round(eiy.getSale_amount())));
		}
		jMap1.put("indicator_hb",
				CommonUtil.DecimalFormat("##.0", eiy.getSale_amount_chain()) + '%');
		jMap1.put("indicator_hb_trend", eiy.getSale_amount_chain() > 0 ? 1 : 0);
		jMap1.put("border_right", 0);
		jList.add(jMap1);
		Map<String, Object> jMap2 = new HashMap<String, Object>();
		if (eiy.getSale_num() > unit)
		{
			jMap2.put("indicator_name", "30天销售量(万)");
			jMap2.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eiy.getSale_num() / unit));
		}
		else
		{
			jMap2.put("indicator_name", "30天销售量");
			jMap2.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eiy.getSale_num()));
		}
		jMap2.put("indicator_hb",
				CommonUtil.DecimalFormat("##.0", eiy.getSale_num_chain()) + '%');
		jMap2.put("indicator_hb_trend", eiy.getSale_num_chain() > 0 ? 1 : 0);
		jMap2.put("border_right", 1);
		jList.add(jMap2);
		Map<String, Object> jMap3 = new HashMap<String, Object>();
		jMap3.put("indicator_name", "店铺总数");
		jMap3.put("indicator_value",
				CommonUtil.DecimalFormat("#,###", eiy.getChan_shop_num()));
		jMap3.put("indicator_hb",
				CommonUtil.DecimalFormat("##.0", eiy.getChan_shop_num_chain()) + '%');
		jMap3.put("indicator_hb_trend", eiy.getChan_shop_num_chain() > 0 ? 1 : 0);
		jMap3.put("border_right", 0);
		jList.add(jMap3);
		Map<String, Object> jMap4 = new HashMap<String, Object>();
		if (eiy.getTotal_eval_num() > unit)
		{
			jMap4.put("indicator_name", "累计评价数(万)");
			jMap4.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eiy.getTotal_eval_num() / unit));
		}
		else
		{
			jMap4.put("indicator_name", "累计评价数");
			jMap4.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eiy.getTotal_eval_num()));
		}
		jMap4.put("indicator_hb",
				CommonUtil.DecimalFormat("##.0", eiy.getTotal_eval_num_chain()) + '%');
		jMap4.put("indicator_hb_trend", eiy.getTotal_eval_num_chain() > 0 ? 1 : 0);
		jMap4.put("border_right", 0);
		jList.add(jMap4);
		Map<String, Object> jMap5 = new HashMap<String, Object>();
		if (eiy.getProd_num() > unit)
		{
			jMap5.put("indicator_name", "商品总数(万)");
			jMap5.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eiy.getProd_num() / unit));
		}
		else
		{
			jMap5.put("indicator_name", "商品总数");
			jMap5.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eiy.getProd_num()));
		}
		jMap5.put("indicator_hb",
				CommonUtil.DecimalFormat("##.0", eiy.getProd_num_chain()) + '%');
		jMap5.put("indicator_hb_trend", eiy.getProd_num_chain() > 0 ? 1 : 0);
		jMap5.put("border_right", 0);
		jList.add(jMap5);
		return jList;
	}

	// 行业分析-行业综合分析-用户评价词
	public List<Map<String, Object>> getUserEvaluation(List<EseInduYear> list)
	{
		List<Map<String, Object>> jList = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> JItem = null;
		for (EseInduYear eiy : list)
		{
			JItem = new HashMap<String, Object>();
			JItem.put("label", eiy.getLabel());
			JItem.put("number", eiy.getLabel_num());
			jList.add(JItem);
		}
		return jList;
	}
}
