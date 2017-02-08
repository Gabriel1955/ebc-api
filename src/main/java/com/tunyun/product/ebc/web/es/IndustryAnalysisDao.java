
package com.tunyun.product.ebc.web.es;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.system.utils.database.PrepareSQL;
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

/**
 * 行业分析
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年1月8日
 * @category com.tunyun.product.ebc.web.es
 */
public class IndustryAnalysisDao
{

	private static final Logger logger = LoggerFactory
			.getLogger(IndustryAnalysisDao.class);
	private EsDao esDao;// 数据源
	// 行业分析-行业综合分析-汇总指标Sql
	private String collectStatisticForComplexSql = "select indu_id,sale_amount,sale_amount_chain,sale_num,sale_num_chain,chan_shop_num,chan_shop_num_chain,total_eval_num,total_eval_num_chain,prod_num,prod_num_chain from ? where indu_id=?";

	public List<EssInduYear> getCollectStatisticForComplex(String tableName, long indu_id)
	{
		PrepareSQL prepareSql = new PrepareSQL(collectStatisticForComplexSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		logger.info("getCollectStatisticForComplex SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduYear.class);
	}

	// 行业分析-行业综合分析-渠道销售动态趋势
	private String channelSalesDynamicTrendForComplexSql = "select indu_id,chan_id,chan_name,stat_time,sale_amount from ? where indu_id=? and stat_time >= ? and stat_time <= ? order by stat_time desc";

	public List<EssInduChanYear> getChannelSalesDynamicTrendForComplex(String tableName,
			long indu_id, long begin_time, long end_time)
	{
		PrepareSQL prepareSql = new PrepareSQL(channelSalesDynamicTrendForComplexSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, begin_time);
		prepareSql.setLong(4, end_time);
		logger.info("channelSalesDynamicTrendForComplexSql SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduChanYear.class);
	}

	// 行业分析-行业综合分析-渠道销售额比例+渠道用户评价
	private String channelSalesDynamicForComplexSql = "select indu_id,chan_id,chan_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio,g_eval_num,m_eval_num,b_eval_num,g_eval_num_ratio,	m_eval_num_ratio,b_eval_num_ratio from ? where indu_id=? order by sale_num desc limit ?";

	public List<EssInduChanYear> getChannelSalesDynamicForComplex(String tableName,
			long indu_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(channelSalesDynamicForComplexSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, topN);
		logger.info("getChannelSalesDynamicForComplex SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduChanYear.class);
	}

	// 行业分析-行业综合分析-热销品牌榜+品牌销售占比
	private String hotBrandForComplexSql = "select indu_id,bid,brand_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio,g_eval_num,m_eval_num,b_eval_num,g_eval_num_ratio,m_eval_num_ratio,b_eval_num_ratio from ? where indu_id = ? and bid > 0 order by sale_amount desc limit ?";

	public List<EssInduBrandYear> getHotBrandForComplex(String tableName, long indu_id,
			int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotBrandForComplexSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, topN);
		logger.info("getHotBrandForComplex SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduBrandYear.class);
	}

	// 行业分析-行业综合分析-热销品类榜+品类销售占比
	private String hotCategoryForComplexSql = "select indu_id,cid,cat_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? order by sale_amount desc limit ?";

	public List<EssInduCatYear> getHotCategoryForComplex(String tableName, long indu_id,
			int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotCategoryForComplexSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, topN);
		logger.info("getHotCategoryForComplex SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduCatYear.class);
	}

	// 行业分析-行业综合分析-热销商品榜
	private String hotCommodityForComplexSql = "select indu_id,prod_id,cid,bid,prod_name,cat_name,brand_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio,g_eval_num,m_eval_num,b_eval_num,g_eval_num_ratio,m_eval_num_ratio,b_eval_num_ratio from ? where indu_id = ? and prod_id >0 order by sale_amount desc limit ?";

	public List<EssInduProdYear> getHotCommodityForComplex(String tableName, long indu_id,
			int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotCommodityForComplexSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, topN);
		logger.info("getHotCommodityForComplex SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduProdYear.class);
	}

	// 行业分析-行业综合分析-用户评价词
	// label_polary:词性(good & bad)
	private String userEvaluationForComplexSql = "select indu_id,label,label_num from ? where indu_id = ? and label_polary = ? order by label_num desc limit ?";

	public List<EseInduYear> getUserEvaluationForComplex(String tableName, long indu_id,
			int label_polary, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(userEvaluationForComplexSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, label_polary);
		prepareSql.setLong(4, topN);
		logger.info("getUserEvaluationForComplex SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EseInduYear.class);
	}

	// 行业分析-行业综合分析-品牌、品类风向标-获取品牌、品类资讯TopN
	private String infoIdForComplexSql = "select info_id from ? where indu_ids = ? and info_dime_id in ( ? ) order by a_time desc limit ?";

	@SuppressWarnings("rawtypes")
	public List<Map> getInfoIdForComplex(String tableName, long indu_id,
			String info_dime_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(infoIdForComplexSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setStringExt(3, info_dime_id, false);
		prepareSql.setLong(4, topN);
		logger.info("getInfoIdForComplex SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), Map.class);
	}

	// 行业分析-行业综合分析-品牌、品类风向标
	private String windVaneForComplexSql = "select uid,title,read_num from ? where uid in ( ? )";

	public List<EaiInfoMonth> getWindVaneForComplex(String tableName, String info_ids)
	{
		PrepareSQL prepareSql = new PrepareSQL(windVaneForComplexSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setStringExt(2, info_ids, false);
		logger.info("getWindVaneForComplex SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EaiInfoMonth.class);
	}

	// 行业分析-行业品类分析-汇总指标-ess_indu_cat_yyyy
	private String collectStatisticForCategorySql = "select indu_id,cid,cat_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and cid = ?";

	public List<EssInduCatYear> getCollectStatisticForCategory(String tableName,
			long indu_id, long cat_id)
	{
		PrepareSQL prepareSql = new PrepareSQL(collectStatisticForCategorySql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, cat_id);
		logger.info("getCollectStatisticForCategory SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduCatYear.class);
	}

	// 行业分析-行业品类分析-品类销售趋势-ess_indu_cat_yyyy
	private String saleTrendForCategorySql = "select indu_id,cid,stat_time,sale_amount,sale_num from ? where indu_id = ? and cid = ? and stat_time >= ? and stat_time <= ? order by stat_time asc";

	public List<EssInduCatYear> getSaleTrendForCategory(String tableName, long indu_id,
			long cat_id, long begin_time, long end_time)
	{
		PrepareSQL prepareSql = new PrepareSQL(saleTrendForCategorySql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, cat_id);
		prepareSql.setLong(4, begin_time);
		prepareSql.setLong(5, end_time);
		logger.info("getSaleTrendForCategory SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduCatYear.class);
	}

	// 行业分析-行业品类分析-用户评价词-ese_indu_cat_yyyy
	private String userEvaluationForCategorySql = "select indu_id,cid,label,label_num from ? where indu_id = ? and cid = ? and label_polary = ? order by label_num desc limit ?";

	// label_polary:词性(good & bad)
	public List<EseInduCatYear> getUserEvaluationForCategory(String tableName,
			long indu_id, long cat_id, int label_polary, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(userEvaluationForCategorySql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, cat_id);
		prepareSql.setLong(4, label_polary);
		prepareSql.setLong(5, topN);
		logger.info("getUserEvaluationForCategory SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EseInduCatYear.class);
	}

	// 行业分析-品类分析-热销/滞销商品榜-ess_indu_cat_prod_top_yyyy
	private String hotCommodityForCategorySql = "select indu_id,cid,prod_id,cat_name,prod_name,sale_amount,is_fast_seller from ? where indu_id = ? and cid = ? and is_fast_seller = ? order by sale_amount ? limit ?";

	// is_fast_seller：是否畅销
	public List<EssInduCatProdTopYear> getHotCommodityForCategory(String tableName,
			long indu_id, long cat_id, int is_fast_seller, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotCommodityForCategorySql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, cat_id);
		prepareSql.setInt(4, is_fast_seller);
		prepareSql.setStringExt(5, is_fast_seller == 1 ? "desc" : "asc", false);
		prepareSql.setLong(6, topN);
		logger.info("getHotCommodityForCategory SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduCatProdTopYear.class);
	}

	// 行业分析-品类分析-热销品牌
	private String hotBrandForCategorySql = "select indu_id,bid,cid,brand_name,cat_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and cid = ? and bid >0 order by sale_amount desc limit ?";

	public List<EssInduBrandCatYear> getHotBrandForCategory(String tableName,
			long indu_id, long cat_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotBrandForCategorySql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, cat_id);
		prepareSql.setLong(4, topN);
		logger.info("getHotBrandForCategory SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduBrandCatYear.class);
	}

	// 行业分析-品类分析-渠道销售分布、渠道用户评价
	private String channelSalesDynamicForCategorySql = "select indu_id,cid,chan_id,cat_name,chan_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio,g_eval_num,m_eval_num,b_eval_num,g_eval_num_ratio,m_eval_num_ratio,b_eval_num_ratio from ? where indu_id = ? and cid = ? order by sale_num desc limit ?";

	public List<EssInduCatChanYear> getChannelSalesDynamicForCategory(String tableName,
			long indu_id, long cat_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(channelSalesDynamicForCategorySql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, cat_id);
		prepareSql.setLong(4, topN);
		logger.info("getChannelSalesDynamicForCategory SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduCatChanYear.class);
	}

	// 行业分析-品类分析-消费能力分布；
	private String spendingPowerForCategorySql = "select indu_id,cid,price_range_id,cat_name,price_range_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and cid = ? order by sale_amount desc limit ?";

	public List<EssInduCatPriceYear> getSpendingPowerForCategory(String tableName,
			long indu_id, long cat_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(spendingPowerForCategorySql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, cat_id);
		prepareSql.setLong(4, topN);
		logger.info("getSpendingPowerForCategory SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduCatPriceYear.class);
	}

	// 行业分析-渠道分析-汇总指标-ess_indu_chan_yyyy
	private String collectStatisticForChannelSql = "select indu_id,chan_id,chan_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and chan_id = ?";

	public List<EssInduChanYear> getCollectStatisticForChannel(String tableName,
			long indu_id, long chan_id)
	{
		PrepareSQL prepareSql = new PrepareSQL(collectStatisticForChannelSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_id);
		logger.info("getCollectStatisticForChannel SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduChanYear.class);
	}

	// 行业分析-渠道分析-品类销售趋势-ess_indu_chan_yyyy
	private String saleTrendForChannelSql = "select indu_id,chan_id,stat_time,sale_amount,sale_num from ? where indu_id = ? and chan_id = ? and stat_time >= ? and stat_time <= ? order by stat_time asc";

	public List<EssInduChanYear> getSaleTrendForChannel(String tableName, long indu_id,
			long chan_id, long begin_time, long end_time)
	{
		PrepareSQL prepareSql = new PrepareSQL(saleTrendForChannelSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_id);
		prepareSql.setLong(4, begin_time);
		prepareSql.setLong(5, end_time);
		logger.info("getSaleTrendForChannel SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduChanYear.class);
	}

	// 行业分析-行业渠道分析-用户评价词-ese_indu_chan_yyyy
	private String userEvaluationForChannelSql = "select indu_id,chan_id,label,chan_name,label_num from ? where indu_id = ? and chan_id = ? and label_polary = ? order by label_num desc limit ?";

	public List<EseInduChanYear> getUserEvaluationForChannel(String tableName,
			long indu_id, long chan_id, int label_polary, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(userEvaluationForChannelSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_id);
		prepareSql.setInt(4, label_polary);
		prepareSql.setInt(5, topN);
		logger.info("getUserEvaluationForChannel SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EseInduChanYear.class);
	}

	// 行业分析-渠道分析-消费能力分布-ess_indu_chan_price_yyyy
	private String spendingPowerForChannelSql = "select indu_id,chan_id,price_range_id,chan_name,price_range_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and chan_id = ? order by sale_amount desc limit ?";

	public List<EssInduChanPriceYear> getSpendingPowerForChannel(String tableName,
			long indu_id, long chan_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(spendingPowerForChannelSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_id);
		prepareSql.setLong(4, topN);
		logger.info("getSpendingPowerForChannel SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduChanPriceYear.class);
	}

	// 行业分析-渠道分析的热销店铺榜-ess_indu_shop_chan_yyyy
	private String hotShopForChannelSql = "select indu_id,chan_shop_id,chan_id,chan_shop_name,chan_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio,g_eval_num,m_eval_num,b_eval_num,g_eval_num_ratio,m_eval_num_ratio,b_eval_num_ratio from ? where indu_id = ? and chan_id = ? order by sale_num desc limit ?";

	public List<EssInduShopChanYear> getHotShopForChannel(String tableName, long indu_id,
			long chan_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotShopForChannelSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_id);
		prepareSql.setLong(4, topN);
		logger.info("getHotShopForChannel SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduShopChanYear.class);
	}

	// 行业分析-渠道分析的热销/滞销商品榜-ess_indu_chan_prod_top_yyyy
	private String hotCommodityForChannelSql = "select indu_id,chan_id,prod_id,chan_name,prod_name,sale_amount from ? where indu_id = ? and chan_id = ? and is_fast_seller = ? order by sale_amount desc limit ?";

	public List<EssInduChanProdTopYear> getHotCommodityForChannel(String tableName,
			long indu_id, long chan_id, int is_fast_seller, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotCommodityForChannelSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_id);
		prepareSql.setInt(4, is_fast_seller);
		prepareSql.setInt(5, topN);
		logger.info("getHotCommodityForChannel SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduChanProdTopYear.class);
	}

	// 行业分析-渠道分析-热销品类-ess_indu_cat_chan_yyyy
	private String hotCategoryForChannelSql = "select indu_id,cid,chan_id,cat_name,chan_name,sale_amount,	sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio,g_eval_num,m_eval_num,b_eval_num,g_eval_num_ratio,m_eval_num_ratio,b_eval_num_ratio from ? where indu_id = ? and chan_id = ? order by sale_amount desc limit ?";

	public List<EssInduCatChanYear> getHotCategoryForChannel(String tableName,
			long indu_id, long chan_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotCategoryForChannelSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_id);
		prepareSql.setLong(4, topN);
		logger.info("getHotCategoryForChannel SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduCatChanYear.class);
	}

	// 行业分析-渠道分析-热销品牌-ess_indu_brand_chan_yyyy
	private String hotBrandForChannelSql = "select indu_id,bid,chan_id,brand_name,chan_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio,g_eval_num,m_eval_num,b_eval_num,g_eval_num_ratio,m_eval_num_ratio,b_eval_num_ratio from ? where indu_id = ? and chan_id = ? and bid > 0 order by sale_amount desc limit ?";

	public List<EssInduBrandChanYear> getHotBrandForChannel(String tableName,
			long indu_id, long chan_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotBrandForChannelSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_id);
		prepareSql.setLong(4, topN);
		logger.info("getHotBrandForChannel SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduBrandChanYear.class);
	}

	public EsDao getEsDao()
	{
		return esDao;
	}

	public void setEsDao(EsDao esDao)
	{
		this.esDao = esDao;
	}
}