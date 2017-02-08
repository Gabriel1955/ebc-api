
package com.tunyun.product.ebc.web.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tunyun.product.ebc.web.common.CommonUtil;
import com.tunyun.product.ebc.web.dao.EbcapiDAO;
import com.tunyun.product.ebc.web.es.EsDao;
import com.tunyun.product.ebc.web.es.IndustryAnalysisDao;
import com.tunyun.product.ebc.web.es.mo.item.EaiInfoMonth;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBrandCatYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBrandChanYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduBrandYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduCatChanYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduCatPriceYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduCatProdTopYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduCatYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduChanPriceYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduChanProdTopYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduChanYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduProdYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduShopChanYear;
import com.tunyun.product.ebc.web.es.mo.item.EssInduYear;
import com.tunyun.product.ebc.web.es.mo.lable.EseInduCatYear;
import com.tunyun.product.ebc.web.es.mo.lable.EseInduChanYear;
import com.tunyun.product.ebc.web.es.mo.lable.EseInduYear;
import com.tunyun.product.ebc.web.es.table.EsTablePrefix;
import com.tunyun.product.ebc.web.server.induanalysis.CategoryAnalysis;
import com.tunyun.product.ebc.web.server.induanalysis.ChannelAnalysis;
import com.tunyun.product.ebc.web.server.induanalysis.ComplexAnalysis;

import net.sf.json.JSONObject;

/**
 * 行业分析
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年1月11日
 * @category com.tunyun.product.ebc.web.server
 */
public class IndustryAnalysisServ
{

	Logger logger = LoggerFactory.getLogger(IndustryAnalysisServ.class);
	// DB
	private EbcapiDAO ebcDao;
	// Es
	private IndustryAnalysisDao induAnanlysisDao;
	// 综合分析
	private ComplexAnalysis comAnalysis;

	public String comAnalysis(String param)
	{
		int topN = 10;
		String year = CommonUtil.format("yyyy", -1);// 前推一天，年
		String lastmonth = CommonUtil.format("yyyyMM", -1);// 前推一天，月份
		String yesterday = CommonUtil.format("yyyyMMdd", -1);// 前推一天，日期
		String today = CommonUtil.format("yyyyMMdd", 0);// 当前日期
		String thirtyBeforeDay = CommonUtil.format("yyyyMMdd", -30);// 前推30天，日期
		String fiveBeforeDay = CommonUtil.format("yyyyMMdd", -5);// 前推5天，日期
		long end_time = CommonUtil.formatDataTotimestampForSecond(today, "yyyyMMdd");// 开始时间
		long begin_time_30 = CommonUtil.formatDataTotimestampForSecond(thirtyBeforeDay,
				"yyyyMMdd");// 结束时间
		long begin_time_5 = CommonUtil.formatDataTotimestampForSecond(fiveBeforeDay,
				"yyyyMMdd");// 结束时间
		// 查询维度信息
		List<Map<String, Object>> dimeList = ebcDao.getAllDime();
		Map<String, String> dimeMap = new HashMap<String, String>();
		for (Map<String, Object> m : dimeList)
		{
			String uid = String.valueOf(m.get("uid"));
			String ids = String.valueOf(m.get("dime_ids"));
			dimeMap.put(uid, ids);
		}
		JSONObject _jsonObject = JSONObject.fromObject(param);
		long indu_id = CommonUtil.getJsonParam(_jsonObject, "indu_id");
		Map<String, Object> jMap = new HashMap<String, Object>();
		// 渠道
		List<EssInduChanYear> eicList = induAnanlysisDao
				.getChannelSalesDynamicForComplex(EsTablePrefix.ess_indu_chan_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, topN);
		// 渠道用户评价
		jMap.put("ChannelEvaluate", comAnalysis.getChannelEvaluate(eicList));
		// 渠道销售动态
		jMap.put("ChannelSalesRatio", comAnalysis.getChannelSalesRatio(eicList));
		// 渠道销售趋势
		List<EssInduChanYear> chanSaleList = induAnanlysisDao
				.getChannelSalesDynamicTrendForComplex(
						EsTablePrefix.ess_indu_chan_yyyy + year, indu_id, begin_time_5,
						end_time);
		jMap.put("ChannelSalesDyna", comAnalysis.getChannelSalesDyna(chanSaleList));
		// 品牌风险标
		List<Map> brandInfoIdList = induAnanlysisDao.getInfoIdForComplex(
				EsTablePrefix.eai_info_brand_yyyy + year + EsTablePrefix.separator
						+ yesterday,
				indu_id, dimeMap.get("10"), topN);
		if (brandInfoIdList.size() > 0)
		{
			List<EaiInfoMonth> brandEimList = induAnanlysisDao
					.getWindVaneForComplex(
							EsTablePrefix.eai_info_yyyymm + lastmonth
									+ EsTablePrefix.separator + yesterday,
							this.spliceInfoId(brandInfoIdList));
			jMap.put("Brand_vane", comAnalysis.getBrandAndCatVane(brandEimList));
		}
		else
		{
			jMap.put("Brand_vane", new ArrayList());
		}
		// 品类风向标
		List<Map> catInfoIdList = induAnanlysisDao.getInfoIdForComplex(
				EsTablePrefix.eai_info_cat_yyyy + year + EsTablePrefix.separator
						+ yesterday,
				indu_id, dimeMap.get("10"), topN);
		if (catInfoIdList.size() > 0)
		{
			List<EaiInfoMonth> catEimList = induAnanlysisDao
					.getWindVaneForComplex(
							EsTablePrefix.eai_info_yyyymm + lastmonth
									+ EsTablePrefix.separator + yesterday,
							this.spliceInfoId(catInfoIdList));
			jMap.put("Category_vane", comAnalysis.getBrandAndCatVane(catEimList));
		}
		else
		{
			jMap.put("Category_vane", new ArrayList());
		}
		// 热门销售品牌
		List<EssInduBrandYear> eibList = induAnanlysisDao
				.getHotBrandForComplex(EsTablePrefix.ess_indu_brand_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, topN);
		jMap.put("HotSalesBrandTop", comAnalysis.getHotSalesBrandTop(eibList));
		jMap.put("HotSalesBrandRatio", comAnalysis.getHotSalesBrandRatio(eibList));
		// 热门销售品类
		List<EssInduCatYear> eicatList = induAnanlysisDao
				.getHotCategoryForComplex(EsTablePrefix.ess_indu_cat_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, topN);
		jMap.put("HotSalesCategoryTop", comAnalysis.getHotSalesCategoryTop(eicatList));
		jMap.put("HotSalesCategoryRatio",
				comAnalysis.getHotSalesCategoryRatio(eicatList));
		// 热门销售商品
		List<EssInduProdYear> eipList = induAnanlysisDao
				.getHotCommodityForComplex(EsTablePrefix.ess_indu_prod_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, topN);
		jMap.put("HotSalesGoodsTop", comAnalysis.getHotSalesGoodsTop(eipList));
		// 销售指标
		List<EssInduYear> eiyList = induAnanlysisDao.getCollectStatisticForComplex(
				EsTablePrefix.ess_indu_yyyy + year + EsTablePrefix.separator + yesterday,
				indu_id);
		if (eiyList.size() > 0)
		{
			jMap.put("ComprehensiveIndicator",
					comAnalysis.getComprehensiveIndicator(eiyList.get(0)));
		}
		// 用户评价词
		List<EseInduYear> goodReputationList = induAnanlysisDao
				.getUserEvaluationForComplex(EsTablePrefix.ese_indu_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, 1, topN);
		jMap.put("GoodReputation", comAnalysis.getUserEvaluation(goodReputationList));
		List<EseInduYear> badReputationList = induAnanlysisDao
				.getUserEvaluationForComplex(EsTablePrefix.ese_indu_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, 0, topN);
		jMap.put("BadReputation", comAnalysis.getUserEvaluation(badReputationList));
		String jsonStr = JSONObject.fromObject(jMap).toString();
		logger.info("<行业分析-综合分析>:{}", jsonStr);
		return jsonStr;
	}

	// 品类分析
	private CategoryAnalysis catAnalysis;

	public String catAnalysis(String param)
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
		long cat_id = CommonUtil.getJsonParam(_jsonObject, "cat_id");// 品类id
		Map<String, Object> jMap = new HashMap<String, Object>();
		// 统计指标
		List<EssInduCatYear> cs_list = induAnanlysisDao
				.getCollectStatisticForCategory(EsTablePrefix.ess_indu_cat_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, cat_id);
		if (cs_list.size() > 0)
		{
			jMap.put("CateAnyIndicator",
					catAnalysis.getComprehensiveIndicator(cs_list.get(0)));
		}
		// 销售趋势
		List<EssInduCatYear> st_list = induAnanlysisDao.getSaleTrendForCategory(
				EsTablePrefix.ess_indu_cat_yyyy + year, indu_id, cat_id, begin_time,
				end_time);
		jMap.put("ChanSalesTrend", catAnalysis.getChanSalesTrend(st_list));
		// 渠道销售分布&销售额比例&渠道用户评价
		List<EssInduCatChanYear> csd_list = induAnanlysisDao
				.getChannelSalesDynamicForCategory(
						EsTablePrefix.ess_indu_cat_chan_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, cat_id, topN);
		jMap.put("ChannelEvaluate", catAnalysis.getChannelEvaluate(csd_list));
		jMap.put("ChanSalesDistributed", catAnalysis.getChanSalesDistributed(csd_list));
		jMap.put("ChanSalesDistributedRatio", catAnalysis.getChannelSalesRatio(csd_list));
		// 热销品牌&销售占比
		List<EssInduBrandCatYear> hc_list = induAnanlysisDao
				.getHotBrandForCategory(
						EsTablePrefix.ess_indu_brand_cat_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, cat_id, topN);
		jMap.put("HotBrandSalesTop", catAnalysis.getHotSalesBrandTop(hc_list));
		jMap.put("HotBrandSalesTopRatio", catAnalysis.getHotSalesBrandRatio(hc_list));
		// 消费能力分布&销售占比
		List<EssInduCatPriceYear> sp_list = induAnanlysisDao
				.getSpendingPowerForCategory(
						EsTablePrefix.ess_indu_cat_price_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, cat_id, topN);
		jMap.put("ConsumptionPowerTop", catAnalysis.getConsumptionPowerTop(sp_list));
		jMap.put("ConsumptionPowerTopRatio",
				catAnalysis.getConsumptionPowerTopRatio(sp_list));
		// 热销&滞销商品
		List<EssInduCatProdTopYear> hotc_list = induAnanlysisDao
				.getHotCommodityForCategory(
						EsTablePrefix.ess_indu_cat_prod_top_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, cat_id, 1, topN);
		List<EssInduCatProdTopYear> unhotc_list = induAnanlysisDao
				.getHotCommodityForCategory(
						EsTablePrefix.ess_indu_cat_prod_top_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, cat_id, 0, topN);
		jMap.put("HotSales", catAnalysis.getHotAndUnsalableTop(hotc_list));
		jMap.put("Unsalable", catAnalysis.getHotAndUnsalableTop(unhotc_list));
		// 用户评价词
		List<EseInduCatYear> gue_list = induAnanlysisDao
				.getUserEvaluationForCategory(EsTablePrefix.ese_indu_brand_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, cat_id, 1, topN);
		List<EseInduCatYear> bue_list = induAnanlysisDao
				.getUserEvaluationForCategory(EsTablePrefix.ese_indu_brand_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, cat_id, 0, topN);
		jMap.put("GoodReputation", catAnalysis.getUserEvaluation(gue_list));
		jMap.put("BadReputation", catAnalysis.getUserEvaluation(bue_list));
		String jsonStr = JSONObject.fromObject(jMap).toString();
		logger.info("<行业分析-行业品类>:{}", jsonStr);
		return jsonStr;
	}

	// 渠道分析
	private ChannelAnalysis chanAnalysis;

	public String chanAnalysis(String param)
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
		long chan_id = CommonUtil.getJsonParam(_jsonObject, "chan_id");// 渠道id
		Map<String, Object> jMap = new HashMap<String, Object>();
		// 统计指标
		List<EssInduChanYear> cs_list = induAnanlysisDao
				.getCollectStatisticForChannel(EsTablePrefix.ess_indu_chan_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, chan_id);
		if (cs_list.size() > 0)
		{
			jMap.put("ChanAnyIndicator",
					chanAnalysis.getChanAnyIndicator(cs_list.get(0)));
		}
		// 销售趋势
		List<EssInduChanYear> st_list = induAnanlysisDao.getSaleTrendForChannel(
				EsTablePrefix.ess_indu_chan_yyyy + year, indu_id, chan_id, begin_time,
				end_time);
		jMap.put("ChanSalesTrend", chanAnalysis.getChanSalesTrend(st_list));
		// 热销品类&销售占比
		List<EssInduCatChanYear> hc_list = induAnanlysisDao
				.getHotCategoryForChannel(
						EsTablePrefix.ess_indu_cat_chan_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, chan_id, topN);
		jMap.put("HotCatSalesTop", chanAnalysis.getHotCategoryBrandTop(hc_list));
		jMap.put("HotCatSalesTopRatio", chanAnalysis.getHotSalesCategoryRatio(hc_list));
		// 热销品牌&销售占比
		List<EssInduBrandChanYear> hb_list = induAnanlysisDao
				.getHotBrandForChannel(
						EsTablePrefix.ess_indu_brand_chan_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, chan_id, topN);
		jMap.put("HotBrandSalesTop", chanAnalysis.getHotSalesBrandTop(hb_list));
		jMap.put("HotBrandSalesTopRatio", chanAnalysis.getHotSalesBrandRatio(hb_list));
		// 消费能力分布&销售占比
		List<EssInduChanPriceYear> sp_list = induAnanlysisDao
				.getSpendingPowerForChannel(
						EsTablePrefix.ess_indu_chan_price_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, chan_id, topN);
		jMap.put("ConsumptionPowerTop", chanAnalysis.getConsumptionPowerTop(sp_list));
		jMap.put("ConsumptionPowerTopRatio",
				chanAnalysis.getConsumptionPowerTopRatio(sp_list));
		// 热销店铺榜
		List<EssInduShopChanYear> hs_list = induAnanlysisDao
				.getHotShopForChannel(
						EsTablePrefix.ess_indu_shop_chan_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, chan_id, topN);
		jMap.put("HotShopTop", chanAnalysis.getHotShopTop(hs_list));
		// 热销&滞销商品
		List<EssInduChanProdTopYear> hotc_list = induAnanlysisDao
				.getHotCommodityForChannel(
						EsTablePrefix.ess_indu_chan_prod_top_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, chan_id, 1, topN);
		List<EssInduChanProdTopYear> unhotc_list = induAnanlysisDao
				.getHotCommodityForChannel(
						EsTablePrefix.ess_indu_chan_prod_top_yyyy + year
								+ EsTablePrefix.separator + yesterday,
						indu_id, chan_id, 0, topN);
		jMap.put("HotSales", chanAnalysis.getHotAndUnsalableTop(hotc_list));
		jMap.put("Unsalable", chanAnalysis.getHotAndUnsalableTop(unhotc_list));
		// 用户评价词
		List<EseInduChanYear> gue_list = induAnanlysisDao
				.getUserEvaluationForChannel(EsTablePrefix.ese_indu_chan_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, chan_id, 1, topN);
		List<EseInduChanYear> bue_list = induAnanlysisDao
				.getUserEvaluationForChannel(EsTablePrefix.ese_indu_chan_yyyy + year
						+ EsTablePrefix.separator + yesterday, indu_id, chan_id, 0, topN);
		jMap.put("GoodReputation", chanAnalysis.getUserEvaluation(gue_list));
		jMap.put("BadReputation", chanAnalysis.getUserEvaluation(bue_list));
		String jsonStr = JSONObject.fromObject(jMap).toString();
		logger.info("<行业分析-行业渠道>:{}", jsonStr);
		return jsonStr;
	}

	// 拼接infoid
	@SuppressWarnings("rawtypes")
	private String spliceInfoId(List<Map> list)
	{
		// 拼接
		StringBuffer spliceStr = new StringBuffer();
		for (int i = 0, len = list.size(); i < len; i++)
		{
			spliceStr.append(list.get(i).get("info_id"));
			if (i < len - 1)
			{
				spliceStr.append(',');
			}
		}
		return spliceStr.toString();
	}

	public void setComAnalysis(ComplexAnalysis comAnalysis)
	{
		this.comAnalysis = comAnalysis;
	}

	public void setCatAnalysis(CategoryAnalysis catAnalysis)
	{
		this.catAnalysis = catAnalysis;
	}

	public void setChanAnalysis(ChannelAnalysis chanAnalysis)
	{
		this.chanAnalysis = chanAnalysis;
	}

	public void setEbcDao(EbcapiDAO ebcDao)
	{
		this.ebcDao = ebcDao;
	}

	public void setInduAnanlysisDao(IndustryAnalysisDao induAnanlysisDao)
	{
		this.induAnanlysisDao = induAnanlysisDao;
	}

	public static void main(String[] args)
	{
		ComplexAnalysis complex = new ComplexAnalysis();
		IndustryAnalysisDao induDao = new IndustryAnalysisDao();
		EsDao induAnanlysisDao = new EsDao();
		induAnanlysisDao.setUrl("http://wifilbs.cn:2325/_sql?sql=");
		induDao.setEsDao(induAnanlysisDao);
		// List<EssInduChanYear> list =
		// induDao.getChannelSalesDynamicForComplex("ess_indu_chan_2016/20160111", 1, 10);
		// System.out.println(JSONObject.fromObject(induServ.getChannelSalesRatio(list)).toString());
		// System.out.println(JSONObject.fromObject(induServ.getChannelEvaluate(list)).toString());
		// List<EssInduBrandYear> list =
		// induDao.getHotBrandForComplex("ess_indu_brand_2016/20160111",1,10);
		// System.out.println(JSONObject.fromObject(induServ.getHotSalesBrandTop(list)).toString());
		// System.out.println(JSONObject.fromObject(induServ.getHotSalesBrandRatio(list)).toString());
		// List<EssInduCatYear> list =
		// induDao.getHotCategoryForComplex("ess_indu_cat_2016/20160111", 1, 10);
		// System.out.println(JSONObject.fromObject(induServ.getHotSalesCategoryTop(list)).toString());
		// System.out.println(JSONObject.fromObject(induServ.getHotSalesCategoryRatio(list)).toString());
		// List<EssInduProdYear> list =
		// induDao.getHotCommodityForComplex("ess_indu_prod_2016/20160111", 1, 10);
		// System.out.println(JSONObject.fromObject(induServ.getHotSalesGoodsTop(list)).toString());
		String year = CommonUtil.format("yyyy", -1);
		String currentDay = CommonUtil.format("yyyyMMdd", 0);
		String thirtyBeforeDay = CommonUtil.format("yyyyMMdd", -30);
		long end_time = CommonUtil.formatDataTotimestampForSecond(currentDay, "yyyyMMdd");
		long begin_time = CommonUtil.formatDataTotimestampForSecond(thirtyBeforeDay,
				"yyyyMMdd");
		List<EssInduChanYear> chanSaleList = induDao
				.getChannelSalesDynamicTrendForComplex(
						EsTablePrefix.ess_indu_chan_yyyy + year, 1, begin_time, end_time);
		System.out.println(JSONObject
				.fromObject(complex.getChannelSalesDyna(chanSaleList)).toString());
	}
}
