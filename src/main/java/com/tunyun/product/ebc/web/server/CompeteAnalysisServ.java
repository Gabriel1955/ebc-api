
package com.tunyun.product.ebc.web.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tunyun.product.ebc.web.common.CommonUtil;
import com.tunyun.product.ebc.web.es.CompeteAnalysisDao;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBradProdTopYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBrandCatYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBrandChanYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBrandPriceYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBrandYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduProdChanYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduProdYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduShopBrandYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduShopCatYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduShopPriceYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduShopProdTopYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduShopYear;
import com.tunyun.product.ebc.web.es.mo.lable.EseInduBrandYear;
import com.tunyun.product.ebc.web.es.mo.lable.EseInduProdYear;
import com.tunyun.product.ebc.web.es.mo.lable.EseInduShopYear;
import com.tunyun.product.ebc.web.es.table.EsTablePrefix;
import com.tunyun.product.ebc.web.server.competeanalysis.BrandAnalysis;
import com.tunyun.product.ebc.web.server.competeanalysis.ProdAnalysis;
import com.tunyun.product.ebc.web.server.competeanalysis.ShopAnalysis;

import net.sf.json.JSONObject;

/**
 * 竞争分析
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年1月19日
 * @category com.tunyun.product.ebc.web.server
 */
public class CompeteAnalysisServ
{

	Logger logger = LoggerFactory.getLogger(CompeteAnalysisServ.class);
	// Es-DAO
	private CompeteAnalysisDao competeAnalysisDao;
	// 竞争品牌
	private BrandAnalysis brandAnalysis;

	public String brandAnalysis(String param)
	{
		int topN = 10;
		String year = CommonUtil.format("yyyy", -1);// 前推一天，年
		String yesterday = CommonUtil.format("yyyyMMdd", -1);// 前推一天，日期
		String today = CommonUtil.format("yyyyMMdd", 0);// 当前日期
		String thirtyBeforeDay = CommonUtil.format("yyyyMMdd", -30);// 前推30天，日期
		long end_time = CommonUtil.formatDataTotimestampForSecond(today, "yyyyMMdd");// 开始时间
		long begin_time = CommonUtil.formatDataTotimestampForSecond(thirtyBeforeDay,
				"yyyyMMdd");// 结束时间
		JSONObject _jsonObject = JSONObject.fromObject(param);
		long indu_id = CommonUtil.getJsonParam(_jsonObject, "indu_id");// 行业id
		long bid = CommonUtil.getJsonParam(_jsonObject, "bid");// 品牌id
		Map<String, Object> jMap = new HashMap<String, Object>();
		// 统计指标
		List<EssInduBrandYear> cs_list = competeAnalysisDao
				.getCollectStatisticForBrand(EsTablePrefix.ess_indu_brand_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, bid);
		if (cs_list.size() > 0)
		{
			jMap.put("BrandIndicator", brandAnalysis.getCollectStatistic(cs_list.get(0)));
		}
		// 销售趋势
		List<EssInduBrandYear> st_list = competeAnalysisDao.getSalesTrendForBrand(
				EsTablePrefix.ess_indu_brand_yyyy + year, indu_id, bid, begin_time,
				end_time);
		jMap.put("SalesTrend", brandAnalysis.getSalesTrend(st_list));
		// 渠道销售分布&销售额比例&渠道用户评价
		List<EssInduBrandChanYear> csd_list = competeAnalysisDao
				.getChannelSalesDynamicForBrand(
						EsTablePrefix.ess_indu_brand_chan_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, bid, topN);
		jMap.put("ChannelEvaluate", brandAnalysis.getChannelEvaluate(csd_list));
		jMap.put("ChanSalesDistributed", brandAnalysis.getChanSalesDistributed(csd_list));
		jMap.put("ChanSalesDistributedRatio",
				brandAnalysis.getChannelSalesRatio(csd_list));
		// 热销品类&销售占比
		List<EssInduBrandCatYear> hc_list = competeAnalysisDao
				.getHotCategoryForBrand(
						EsTablePrefix.ess_indu_brand_cat_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, bid, topN);
		jMap.put("HotCatSalesTop", brandAnalysis.getHotCategoryBrandTop(hc_list));
		jMap.put("HotCatSalesTopRatio", brandAnalysis.getHotSalesCategoryRatio(hc_list));
		// 消费能力分布&销售占比
		List<EssInduBrandPriceYear> sp_list = competeAnalysisDao
				.getSpendingPowerForBrand(
						EsTablePrefix.ess_indu_brand_price_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, bid, topN);
		jMap.put("ConsumptionPowerTop", brandAnalysis.getConsumptionPowerTop(sp_list));
		jMap.put("ConsumptionPowerTopRatio",
				brandAnalysis.getConsumptionPowerTopRatio(sp_list));
		// 热销&滞销商品
		List<EssInduBradProdTopYear> hotc_list = competeAnalysisDao
				.getHotAndUnHotCommodityForBrand(
						EsTablePrefix.ess_indu_brad_prod_top_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, bid, 1, topN);
		List<EssInduBradProdTopYear> unhotc_list = competeAnalysisDao
				.getHotAndUnHotCommodityForBrand(
						EsTablePrefix.ess_indu_brad_prod_top_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, bid, 0, topN);
		jMap.put("HotSales", brandAnalysis.getHotAndUnsalableTop(hotc_list));
		jMap.put("Unsalable", brandAnalysis.getHotAndUnsalableTop(unhotc_list));
		// 用户评价词
		List<EseInduBrandYear> gue_list = competeAnalysisDao
				.getUserEvaluationForBrand(EsTablePrefix.ese_indu_brand_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, bid, 1, topN);
		List<EseInduBrandYear> bue_list = competeAnalysisDao
				.getUserEvaluationForBrand(EsTablePrefix.ese_indu_brand_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, bid, 0, topN);
		jMap.put("GoodReputation", brandAnalysis.getUserEvaluation(gue_list));
		jMap.put("BadReputation", brandAnalysis.getUserEvaluation(bue_list));
		String jsonStr = JSONObject.fromObject(jMap).toString();
		logger.info("<竞争分析-竞争品牌>:{}", jsonStr);
		return jsonStr;
	}

	// 竞争店铺
	private ShopAnalysis shopAnalysis;

	public String shopAnalysis(String param)
	{
		int topN = 10;
		String year = CommonUtil.format("yyyy", -1);// 前推一天，年
		String yesterday = CommonUtil.format("yyyyMMdd", -1);// 前推一天，日期
		String today = CommonUtil.format("yyyyMMdd", 0);// 当前日期
		String thirtyBeforeDay = CommonUtil.format("yyyyMMdd", -30);// 前推30天，日期
		long end_time = CommonUtil.formatDataTotimestampForSecond(today, "yyyyMMdd");// 开始时间
		long begin_time = CommonUtil.formatDataTotimestampForSecond(thirtyBeforeDay,
				"yyyyMMdd");// 结束时间
		JSONObject _jsonObject = JSONObject.fromObject(param);
		long indu_id = CommonUtil.getJsonParam(_jsonObject, "indu_id");// 行业id
		long chan_shop_id = CommonUtil.getJsonParam(_jsonObject, "chan_shop_id");// 店铺id
		Map<String, Object> jMap = new HashMap<String, Object>();
		// 统计指标
		List<EssInduShopYear> cs_list = competeAnalysisDao
				.getCollectStatisticForShop(EsTablePrefix.ess_indu_shop_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, chan_shop_id);
		if (cs_list.size() > 0)
		{
			jMap.put("ShopIndicator", shopAnalysis.getCollectStatistic(cs_list.get(0)));
		}
		// 销售趋势
		List<EssInduShopYear> st_list = competeAnalysisDao.getSalesTrendForShop(
				EsTablePrefix.ess_indu_shop_yyyy + year, indu_id, chan_shop_id,
				begin_time, end_time);
		jMap.put("SalesTrend", shopAnalysis.getSalesTrend(st_list));
		// 热销品类&销售占比
		List<EssInduShopCatYear> hc_list = competeAnalysisDao
				.getHotCategoryForShop(
						EsTablePrefix.ess_indu_shop_cat_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, chan_shop_id, topN);
		jMap.put("HotCatSalesTop", shopAnalysis.getHotCategoryBrandTop(hc_list));
		jMap.put("HotCatSalesTopRatio", shopAnalysis.getHotSalesCategoryRatio(hc_list));
		// 热销品牌&销售占比
		List<EssInduShopBrandYear> hb_list = competeAnalysisDao
				.getHotBrandForShop(
						EsTablePrefix.ess_indu_shop_brand_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, chan_shop_id, topN);
		jMap.put("HotBrandSalesTop", shopAnalysis.getHotSalesBrandTop(hb_list));
		jMap.put("HotBrandSalesTopRatio", shopAnalysis.getHotSalesBrandRatio(hb_list));
		// 消费能力分布&销售占比
		List<EssInduShopPriceYear> sp_list = competeAnalysisDao
				.getSpendingPowerForShop(
						EsTablePrefix.ess_indu_shop_price_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, chan_shop_id, topN);
		jMap.put("ConsumptionPowerTop", shopAnalysis.getConsumptionPowerTop(sp_list));
		jMap.put("ConsumptionPowerTopRatio",
				shopAnalysis.getConsumptionPowerTopRatio(sp_list));
		// 热销&滞销商品
		List<EssInduShopProdTopYear> hotc_list = competeAnalysisDao
				.getHotAndUnHotCommodityForShop(
						EsTablePrefix.ess_indu_shop_prod_top_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, chan_shop_id, 1, topN);
		List<EssInduShopProdTopYear> unhotc_list = competeAnalysisDao
				.getHotAndUnHotCommodityForShop(
						EsTablePrefix.ess_indu_shop_prod_top_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, chan_shop_id, 0, topN);
		jMap.put("HotSales", shopAnalysis.getHotAndUnsalableTop(hotc_list));
		jMap.put("Unsalable", shopAnalysis.getHotAndUnsalableTop(unhotc_list));
		// 用户评价词
		List<EseInduShopYear> gue_list = competeAnalysisDao.getUserEvaluationForShop(
				EsTablePrefix.ese_indu_shop_yyyy + year + EsTablePrefix.separator
						+ yesterday,
				indu_id, chan_shop_id, 1, topN);
		List<EseInduShopYear> bue_list = competeAnalysisDao.getUserEvaluationForShop(
				EsTablePrefix.ese_indu_shop_yyyy + year + EsTablePrefix.separator
						+ yesterday,
				indu_id, chan_shop_id, 0, topN);
		jMap.put("GoodReputation", shopAnalysis.getUserEvaluation(gue_list));
		jMap.put("BadReputation", shopAnalysis.getUserEvaluation(bue_list));
		String jsonStr = JSONObject.fromObject(jMap).toString();
		logger.info("<竞争分析-竞争店铺>:{}", jsonStr);
		return jsonStr;
	}

	// 竞争单品
	private ProdAnalysis prodAnalysis;

	public String prodAnalysis(String param)
	{
		int topN = 10;
		String year = CommonUtil.format("yyyy", -1);// 前推一天，年
		String yesterday = CommonUtil.format("yyyyMMdd", -1);// 前推一天，日期
		String today = CommonUtil.format("yyyyMMdd", 0);// 当前日期
		String thirtyBeforeDay = CommonUtil.format("yyyyMMdd", -30);// 前推30天，日期
		long end_time = CommonUtil.formatDataTotimestampForSecond(today, "yyyyMMdd");// 开始时间
		long begin_time = CommonUtil.formatDataTotimestampForSecond(thirtyBeforeDay,
				"yyyyMMdd");// 结束时间
		JSONObject _jsonObject = JSONObject.fromObject(param);
		long indu_id = CommonUtil.getJsonParam(_jsonObject, "indu_id");// 行业id
		long prod_id = CommonUtil.getJsonParam(_jsonObject, "prod_id");// 产品id
		Map<String, Object> jMap = new HashMap<String, Object>();
		// 统计指标
		List<EssInduProdYear> cs_list = competeAnalysisDao
				.getCollectStatisticForProd(EsTablePrefix.ess_indu_prod_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, prod_id);
		if (cs_list.size() > 0)
		{
			jMap.put("SingleIndicator", prodAnalysis.getCollectStatistic(cs_list.get(0)));
		}
		// 销售趋势
		List<EssInduProdYear> st_list = competeAnalysisDao.getSalesTrendForProd(
				EsTablePrefix.ess_indu_prod_yyyy + year, indu_id, prod_id, begin_time,
				end_time);
		jMap.put("SalesTrend", prodAnalysis.getSalesTrend(st_list));
		// 渠道销售分布&销售额比例&渠道用户评价
		List<EssInduProdChanYear> csd_list = competeAnalysisDao
				.getChannelSalesDynamicForProd(
						EsTablePrefix.ess_indu_prod_chan_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, prod_id, topN);
		jMap.put("ChannelEvaluate", prodAnalysis.getChannelEvaluate(csd_list));
		jMap.put("ChanSalesDistributed", prodAnalysis.getChanSalesDistributed(csd_list));
		jMap.put("ChanSalesDistributedRatio",
				prodAnalysis.getChannelSalesRatio(csd_list));
		// 热销店铺&销售占比
		List<EssInduShopProdTopYear> hc_list = competeAnalysisDao
				.getHotShopForProd(
						EsTablePrefix.ess_indu_shop_prod_top_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, prod_id, topN);
		jMap.put("HotShopSalesTop", prodAnalysis.getHotShopTop(hc_list));
		jMap.put("HotShopSalesTopRatio", prodAnalysis.getHotShopTopRatio(hc_list));
		// 用户评价词
		List<EseInduProdYear> gue_list = competeAnalysisDao
				.getUserEvaluationForProd(EsTablePrefix.ese_indu_prod_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, prod_id, 1, topN);
		List<EseInduProdYear> bue_list = competeAnalysisDao
				.getUserEvaluationForProd(EsTablePrefix.ese_indu_prod_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, prod_id, 0, topN);
		jMap.put("GoodReputation", prodAnalysis.getUserEvaluation(gue_list));
		jMap.put("BadReputation", prodAnalysis.getUserEvaluation(bue_list));
		String jsonStr = JSONObject.fromObject(jMap).toString();
		logger.info("<竞争分析-竞争单品>:{}", jsonStr);
		return jsonStr;
	}

	public void setCompeteAnalysisDao(CompeteAnalysisDao competeAnalysisDao)
	{
		this.competeAnalysisDao = competeAnalysisDao;
	}

	public void setBrandAnalysis(BrandAnalysis brandAnalysis)
	{
		this.brandAnalysis = brandAnalysis;
	}

	public void setShopAnalysis(ShopAnalysis shopAnalysis)
	{
		this.shopAnalysis = shopAnalysis;
	}

	public void setProdAnalysis(ProdAnalysis prodAnalysis)
	{
		this.prodAnalysis = prodAnalysis;
	}
}
