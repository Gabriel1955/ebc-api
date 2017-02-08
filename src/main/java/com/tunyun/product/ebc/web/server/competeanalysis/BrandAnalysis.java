
package com.tunyun.product.ebc.web.server.competeanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tunyun.product.ebc.web.common.CommonUtil;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBradProdTopYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBrandCatYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBrandChanYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBrandPriceYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBrandYear;
import com.tunyun.product.ebc.web.es.mo.lable.EseInduBrandYear;

/**
 * 竞争分析-品牌分析
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年1月19日
 * @category com.tunyun.product.ebc.web.server.competeanalysis
 */
public class BrandAnalysis
{

	Logger logger = LoggerFactory.getLogger(BrandAnalysis.class);

	// 竞争分析-竞争品牌分析-综合指标-ess_indu_brand_yyyy
	public List<Map<String, Object>> getCollectStatistic(EssInduBrandYear eic)
	{
		int unit = 10000;
		List<Map<String, Object>> jList = new ArrayList<Map<String, Object>>();
		Map<String, Object> jMap1 = new HashMap<String, Object>();
		if (eic.getSale_amount() > unit)
		{
			jMap1.put("indicator_name", "30天销售额(万)");
			jMap1.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eic.getSale_amount() / unit));
		}
		else
		{
			jMap1.put("indicator_name", "30天销售额");
			jMap1.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eic.getSale_amount()));
		}
		jMap1.put("indicator_hb",
				CommonUtil.DecimalFormat("##.0", eic.getSale_amount_chain()) + '%');
		jMap1.put("indicator_hb_trend", eic.getSale_amount_chain() > 0 ? 1 : 0);
		jMap1.put("border_right", 0);
		jList.add(jMap1);
		Map<String, Object> jMap2 = new HashMap<String, Object>();
		if (eic.getSale_num() > unit)
		{
			jMap2.put("indicator_name", "30天销售量(万)");
			jMap2.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eic.getSale_num() / unit));
		}
		else
		{
			jMap2.put("indicator_name", "30天销售量");
			jMap2.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eic.getSale_num()));
		}
		jMap2.put("indicator_hb", CommonUtil.DecimalFormat("##.0", 0) + '%');
		jMap2.put("indicator_hb_trend", 0);
		jMap2.put("border_right", 0);
		jList.add(jMap2);
		Map<String, Object> jMap3 = new HashMap<String, Object>();
		jMap3.put("indicator_name", "行业占比");
		jMap3.put("indicator_value",
				CommonUtil.DecimalFormat("##.0", eic.getAmount_indu_ratio()));
		jMap3.put("indicator_hb", CommonUtil.DecimalFormat("##.0", 0) + '%');
		jMap3.put("indicator_hb_trend", 0);
		jMap3.put("border_right", 1);
		jList.add(jMap3);
		Map<String, Object> jMap4 = new HashMap<String, Object>();
		if (eic.getChan_shop_num() > unit)
		{
			jMap4.put("indicator_name", "店铺总数(万)");
			jMap4.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eic.getChan_shop_num() / unit));
		}
		else
		{
			jMap4.put("indicator_name", "店铺总数");
			jMap4.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eic.getChan_shop_num()));
		}
		jMap4.put("indicator_hb", CommonUtil.DecimalFormat("##.0", 0) + '%');
		jMap4.put("indicator_hb_trend", 0);
		jMap4.put("border_right", 0);
		jList.add(jMap4);
		Map<String, Object> jMap5 = new HashMap<String, Object>();
		if (eic.getProd_num() > unit)
		{
			jMap5.put("indicator_name", "商品总数(万)");
			jMap5.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eic.getProd_num() / unit));
		}
		else
		{
			jMap5.put("indicator_name", "商品总数");
			jMap5.put("indicator_value",
					CommonUtil.DecimalFormat("#,###", eic.getProd_num()));
		}
		jMap5.put("indicator_hb", CommonUtil.DecimalFormat("##.0", 0) + '%');
		jMap5.put("indicator_hb_trend", 0);
		jMap5.put("border_right", 0);
		jList.add(jMap5);
		return jList;
	}

	// 竞争分析-竞争品牌分析-品牌销售趋势-ess_indu_brand_yyyy
	public Map<String, List<Object>> getSalesTrend(List<EssInduBrandYear> list)
	{
		List<Object> statimeList = new ArrayList<Object>();
		List<Object> saleNumList = new ArrayList<Object>();
		List<Object> saleAmountList = new ArrayList<Object>();
		for (EssInduBrandYear eic : list)
		{
			// 统计时间
			String stat_time = CommonUtil.format("MM-dd", eic.getStat_time() * 1000L);
			// 按统计时间去除重复数据
			if (!statimeList.contains(stat_time))
			{
				statimeList.add(stat_time);
				saleNumList.add(eic.getSale_num());
				saleAmountList.add(eic.getSale_amount());
			}
		}
		// 组装json对象
		Map<String, List<Object>> JMap = new HashMap<String, List<Object>>();
		List<Object> JSeriesList = new ArrayList<Object>();
		Map<String, Object> JSeriesMap1 = new HashMap<String, Object>();
		JSeriesMap1.put("name", "30天销售额(万元)");
		JSeriesMap1.put("data", saleAmountList);
		Map<String, Object> JSeriesMap2 = new HashMap<String, Object>();
		JSeriesMap2.put("name", "30天销售量(笔)");
		JSeriesMap2.put("data", saleNumList);
		JSeriesList.add(JSeriesMap1);
		JSeriesList.add(JSeriesMap2);
		JMap.put("series", JSeriesList);
		JMap.put("categories", statimeList);
		return JMap;
	}

	// 竞争分析-竞争品牌分析-渠道销售分布&销售额比例&渠道用户评价-ess_indu_brand_chan_yyyy
	public List<List<Object>> getChanSalesDistributed(List<EssInduBrandChanYear> list)
	{
		List<List<Object>> jList = new ArrayList<List<Object>>();
		List<Object> JItemList = null;
		int No = 1;
		for (EssInduBrandChanYear ecc : list)
		{
			JItemList = new ArrayList<Object>();
			JItemList.add(No++);
			JItemList.add(ecc.getChan_name());
			JItemList.add(CommonUtil.DecimalFormat("#,###.00", ecc.getSale_amount()));
			StringBuffer itemStr = new StringBuffer();
			itemStr.append("[");
			itemStr.append("\"")
					.append(CommonUtil.DecimalFormat("##.00", ecc.getSale_amount_chain()))
					.append("%\",").append(ecc.getSale_amount_chain() > 0 ? 1 : 0)
					.append("]");
			JItemList.add(itemStr.toString());
			JItemList.add(
					CommonUtil.DecimalFormat("##.0", ecc.getAmount_indu_ratio()) + "%");
			jList.add(JItemList);
		}
		return jList;
	}

	public List<String> getChannelSalesRatio(List<EssInduBrandChanYear> list)
	{
		List<String> jList = new ArrayList<String>();
		StringBuffer JItem = new StringBuffer();
		for (EssInduBrandChanYear eic : list)
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

	public List<Map<String, Object>> getChannelEvaluate(List<EssInduBrandChanYear> list)
	{
		List<Map<String, Object>> jList = new ArrayList<Map<String, Object>>();
		Map<String, Object> JItem = null;
		for (EssInduBrandChanYear eic : list)
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

	// 竞争分析-竞争品牌分析-热销品类&销售占比-ess_indu_brand_cat_yyyy
	public List<List<Object>> getHotCategoryBrandTop(List<EssInduBrandCatYear> list)
	{
		List<List<Object>> jList = new ArrayList<List<Object>>();
		List<Object> JItemList = null;
		int No = 1;
		for (EssInduBrandCatYear eib : list)
		{
			JItemList = new ArrayList<Object>();
			JItemList.add(No++);
			JItemList.add(eib.getCat_name());
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

	public List<String> getHotSalesCategoryRatio(List<EssInduBrandCatYear> list)
	{
		List<String> jList = new ArrayList<String>();
		StringBuffer JItem = new StringBuffer();
		for (EssInduBrandCatYear eib : list)
		{
			JItem.delete(0, JItem.length());
			JItem.append("[");
			JItem.append("\"").append(eib.getCat_name()).append("\",")
					.append(eib.getAmount_indu_ratio());
			JItem.append("]");
			jList.add(JItem.toString());
		}
		return jList;
	}

	// 竞争分析-竞争品牌分析-消费能力分布&销售占比-ess_indu_brand_price_yyyy
	public List<List<Object>> getConsumptionPowerTop(List<EssInduBrandPriceYear> list)
	{
		List<List<Object>> jList = new ArrayList<List<Object>>();
		List<Object> JItemList = null;
		int No = 1;
		for (EssInduBrandPriceYear eicp : list)
		{
			JItemList = new ArrayList<Object>();
			JItemList.add(No++);
			JItemList.add(eicp.getPrice_range_name());
			JItemList.add(CommonUtil.DecimalFormat("#,###.00", eicp.getSale_amount()));
			StringBuffer itemStr = new StringBuffer();
			itemStr.append("[");
			itemStr.append("\"")
					.append(CommonUtil.DecimalFormat("##.00",
							eicp.getSale_amount_chain()))
					.append("%\",").append(eicp.getSale_amount_chain() > 0 ? 1 : 0)
					.append("]");
			JItemList.add(itemStr.toString());
			JItemList.add(
					CommonUtil.DecimalFormat("##.0", eicp.getAmount_indu_ratio()) + "%");
			jList.add(JItemList);
		}
		return jList;
	}

	public List<String> getConsumptionPowerTopRatio(List<EssInduBrandPriceYear> list)
	{
		List<String> jList = new ArrayList<String>();
		StringBuffer JItem = new StringBuffer();
		for (EssInduBrandPriceYear eicp : list)
		{
			JItem.delete(0, JItem.length());
			JItem.append("[");
			JItem.append("\"").append(eicp.getPrice_range_name()).append("\",")
					.append(eicp.getSale_amount());
			JItem.append("]");
			jList.add(JItem.toString());
		}
		return jList;
	}

	// 竞争分析-竞争品牌分析-热销/滞销商品-ess_indu_brad_prod_top_yyyy
	public List<Map<String, Object>> getHotAndUnsalableTop(
			List<EssInduBradProdTopYear> list)
	{
		List<Map<String, Object>> JList = new ArrayList<Map<String, Object>>();
		for (EssInduBradProdTopYear eicpt : list)
		{
			Map<String, Object> JMap = new HashMap<String, Object>();
			JMap.put("href", "#");
			JMap.put("brand", eicpt.getBrand_name());
			JMap.put("model", eicpt.getProd_name());
			JMap.put("sales_volume",
					CommonUtil.DecimalFormat("##.0", eicpt.getSale_amount()));
			JList.add(JMap);
		}
		return JList;
	}

	// 竞争分析-竞争品牌分析-用户评价词-ese_indu_brand_yyyy
	public List<Map<String, Object>> getUserEvaluation(List<EseInduBrandYear> list)
	{
		List<Map<String, Object>> jList = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> JItem = null;
		for (EseInduBrandYear eic : list)
		{
			JItem = new HashMap<String, Object>();
			JItem.put("label", eic.getLabel());
			JItem.put("number", eic.getLabel_num());
			jList.add(JItem);
		}
		return jList;
	}
}
