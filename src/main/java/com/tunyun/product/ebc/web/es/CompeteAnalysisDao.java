
package com.tunyun.product.ebc.web.es;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.system.utils.database.PrepareSQL;
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

/**
 * 竞争分析
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年1月19日
 * @category com.tunyun.product.ebc.web.es
 */
public class CompeteAnalysisDao
{

	private static final Logger logger = LoggerFactory
			.getLogger(CompeteAnalysisDao.class);
	private EsDao esDao;// 数据源
	// 竞争分析-竞争品牌分析-综合指标-ess_indu_brand_yyyy
	private String collectStatisticForBrandSql = "select indu_id,bid,brand_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and bid = ?";

	public List<EssInduBrandYear> getCollectStatisticForBrand(String tableName,
			long indu_id, long bid)
	{
		PrepareSQL prepareSql = new PrepareSQL(collectStatisticForBrandSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, bid);
		logger.info("getCollectStatisticForBrand SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduBrandYear.class);
	}

	// 竞争分析-竞争品牌分析-品牌销售趋势-ess_indu_brand_yyyy
	private String salesTrendForBrandSql = "select indu_id,bid,stat_time,brand_name,sale_amount,sale_num from ? where indu_id = ? and bid = ? and stat_time >= ? and stat_time <= ? order by stat_time asc";

	public List<EssInduBrandYear> getSalesTrendForBrand(String tableName, long indu_id,
			long bid, long begin_time, long end_time)
	{
		PrepareSQL prepareSql = new PrepareSQL(salesTrendForBrandSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, bid);
		prepareSql.setLong(4, begin_time);
		prepareSql.setLong(5, end_time);
		logger.info("getSalesTrendForBrand SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduBrandYear.class);
	}

	// 竞争分析-竞争品牌分析-渠道销售分布&销售额比例&渠道用户评价-ess_indu_brand_chan_yyyy
	private String channelSalesDynamicForBrandSql = "select indu_id,bid,chan_id,brand_name,chan_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio,g_eval_num,m_eval_num,b_eval_num,g_eval_num_ratio,m_eval_num_ratio,b_eval_num_ratio from ? where indu_id = ? and bid = ? order by sale_amount desc limit ?";

	public List<EssInduBrandChanYear> getChannelSalesDynamicForBrand(String tableName,
			long indu_id, long bid, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(channelSalesDynamicForBrandSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, bid);
		prepareSql.setInt(4, topN);
		logger.info("getChannelSalesDynamicForBrand SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduBrandChanYear.class);
	}

	// 竞争分析-竞争品牌分析-热销品类&销售占比-ess_indu_brand_cat_yyyy
	private String hotCategoryForBrandSql = "select indu_id,bid,cid,brand_name,cat_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,	prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and bid = ? order by sale_amount desc limit ?";

	public List<EssInduBrandCatYear> getHotCategoryForBrand(String tableName,
			long indu_id, long bid, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotCategoryForBrandSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, bid);
		prepareSql.setInt(4, topN);
		logger.info("getHotCategoryForBrand SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduBrandCatYear.class);
	}

	// 竞争分析-竞争品牌分析-消费能力分布&销售占比-ess_indu_brand_price_yyyy
	private String spendingPowerForBrandSql = "select indu_id,bid,price_range_id,brand_name,price_range_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and bid = ? order by sale_amount desc limit ?";

	public List<EssInduBrandPriceYear> getSpendingPowerForBrand(String tableName,
			long indu_id, long bid, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(spendingPowerForBrandSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, bid);
		prepareSql.setLong(4, topN);
		logger.info("getSpendingPowerForBrand SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduBrandPriceYear.class);
	}

	// 竞争分析-竞争品牌分析-热销/滞销商品-ess_indu_brad_prod_top_yyyy
	private String hotAndUnHotCommodityForBrandSql = "select indu_id,bid,prod_id,brand_name,prod_name,sale_amount from ? where indu_id = ? and bid = ? and is_fast_seller = ? order by sale_amount desc limit ?";

	public List<EssInduBradProdTopYear> getHotAndUnHotCommodityForBrand(String tableName,
			long indu_id, long bid, int is_fast_seller, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotAndUnHotCommodityForBrandSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, bid);
		prepareSql.setInt(4, is_fast_seller);
		prepareSql.setLong(5, topN);
		logger.info("getHotAndUnHotCommodityForBrand SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduBradProdTopYear.class);
	}

	// 竞争分析-竞争品牌分析-用户评价词-ese_indu_brand_yyyy
	private String userEvaluationForBrandSql = "select indu_id,	bid,label,brand_name,label_num from ? where indu_id = ? and bid = ? and label_polary = ? order by label_num desc limit ?";

	public List<EseInduBrandYear> getUserEvaluationForBrand(String tableName,
			long indu_id, long bid, int label_polary, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(userEvaluationForBrandSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, bid);
		prepareSql.setInt(4, label_polary);
		prepareSql.setLong(5, topN);
		logger.info("getUserEvaluationForBrand SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EseInduBrandYear.class);
	}

	// 竞争分析-竞争店铺分析-综合指标-ess_indu_shop_yyyy
	private String collectStatisticForShopSql = "select indu_id,chan_shop_id,shop_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and chan_shop_id = ?";

	public List<EssInduShopYear> getCollectStatisticForShop(String tableName,
			long indu_id, long chan_shop_id)
	{
		PrepareSQL prepareSql = new PrepareSQL(collectStatisticForShopSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_shop_id);
		logger.info("getCollectStatisticForShop SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduShopYear.class);
	}

	// 竞争分析-竞争店铺分析-店铺销售趋势-ess_indu_shop_yyyy
	private String salesTrendForShopSql = "select indu_id,chan_shop_id,stat_time,shop_name,sale_amount,sale_num from ? where indu_id = ? and chan_shop_id = ? and stat_time >= ? and stat_time <= ? order by stat_time asc";

	public List<EssInduShopYear> getSalesTrendForShop(String tableName, long indu_id,
			long chan_shop_id, long begin_time, long end_time)
	{
		PrepareSQL prepareSql = new PrepareSQL(salesTrendForShopSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_shop_id);
		prepareSql.setLong(4, begin_time);
		prepareSql.setLong(5, end_time);
		logger.info("getSalesTrendForShop SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduShopYear.class);
	}

	// 竞争分析-竞争店铺分析-热销品类&销售占比-ess_indu_shop_cat_yyyy
	private String hotCategoryForShopSql = "select indu_id,chan_id,chan_shop_id,cid,chan_shop_name,cat_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,	prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and chan_shop_id = ? order by sale_amount desc limit ?";

	public List<EssInduShopCatYear> getHotCategoryForShop(String tableName, long indu_id,
			long chan_shop_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotCategoryForShopSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_shop_id);
		prepareSql.setInt(4, topN);
		logger.info("getHotCategoryForShop SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduShopCatYear.class);
	}

	// 竞争分析-竞争店铺分析-热销品牌&销售占比-ess_indu_shop_brand_yyyy
	private String hotBrandForShopSql = "select indu_id,chan_id,chan_shop_id,bid,chan_shop_name,brand_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and chan_shop_id = ? and bid > 0 order by sale_amount limit ?";

	public List<EssInduShopBrandYear> getHotBrandForShop(String tableName, long indu_id,
			long chan_shop_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotBrandForShopSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_shop_id);
		prepareSql.setInt(4, topN);
		logger.info("getHotBrandForShop SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduShopBrandYear.class);
	}

	// 竞争分析-竞争店铺分析-消费能力分布&销售占比-ess_indu_shop_price_yyyy
	private String spendingPowerForShopSql = "select indu_id,chan_id,chan_shop_id,price_range_id,chan_shop_name,price_range_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and chan_shop_id = ? order by sale_amount desc limit ?";

	public List<EssInduShopPriceYear> getSpendingPowerForShop(String tableName,
			long indu_id, long chan_shop_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(spendingPowerForShopSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_shop_id);
		prepareSql.setLong(4, topN);
		logger.info("getSpendingPowerForShop SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduShopPriceYear.class);
	}

	// 竞争分析-竞争店铺分析-热销/滞销商品-ess_indu_shop_prod_top_yyyy
	private String hotAndUnHotCommodityForShopSql = "select indu_id,chan_id,chan_shop_id,prod_id,chan_shop_name,prod_name,sale_amount from ? where indu_id = ? and chan_shop_id = ? and is_fast_seller = ? order by sale_amount desc limit ?";

	public List<EssInduShopProdTopYear> getHotAndUnHotCommodityForShop(String tableName,
			long indu_id, long chan_shop_id, int is_fast_seller, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotAndUnHotCommodityForShopSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_shop_id);
		prepareSql.setInt(4, is_fast_seller);
		prepareSql.setLong(5, topN);
		logger.info("getHotAndUnHotCommodityForShop SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduShopProdTopYear.class);
	}

	// 竞争分析-竞争店铺分析-用户评价词-ese_indu_shop_yyyy
	private String userEvaluationForShopSql = "select indu_id,chan_shop_id,label,chan_shop_name,label_num from ? where indu_id = ? and chan_shop_id = ? and label_polary = ? order by label_num desc limit ?";

	public List<EseInduShopYear> getUserEvaluationForShop(String tableName, long indu_id,
			long chan_shop_id, int label_polary, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(userEvaluationForShopSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, chan_shop_id);
		prepareSql.setInt(4, label_polary);
		prepareSql.setLong(5, topN);
		logger.info("getUserEvaluationForShop SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EseInduShopYear.class);
	}

	// 竞争分析-竞争单品分析-综合指标-ess_indu_prod_yyyy
	private String collectStatisticForProdSql = "select indu_id,prod_id,cid,bid,prod_name,cat_name,brand_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio from ? where indu_id = ? and prod_id = ?";

	public List<EssInduProdYear> getCollectStatisticForProd(String tableName,
			long indu_id, long prod_id)
	{
		PrepareSQL prepareSql = new PrepareSQL(collectStatisticForProdSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, prod_id);
		logger.info("getCollectStatisticForProd SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduProdYear.class);
	}

	// 竞争分析-竞争单品分析-单品销售趋势-ess_indu_prod_yyyy
	private String salesTrendForProdSql = "select indu_id,prod_id,stat_time,prod_name,sale_amount,sale_num from ? where indu_id = ? and prod_id = ? and stat_time >= ? and stat_time <= ? order by stat_time asc";

	public List<EssInduProdYear> getSalesTrendForProd(String tableName, long indu_id,
			long prod_id, long begin_time, long end_time)
	{
		PrepareSQL prepareSql = new PrepareSQL(salesTrendForProdSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, prod_id);
		prepareSql.setLong(4, begin_time);
		prepareSql.setLong(5, end_time);
		logger.info("getSalesTrendForProd SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduProdYear.class);
	}

	// 竞争分析-竞争单品分析-渠道销售分布&销售额比例&渠道用户评价-ess_indu_prod_chan_yyyy
	private String channelSalesDynamicForProdSql = "select indu_id,prod_id,chan_id,prod_name,chan_name,sale_amount,sale_num,chan_shop_num,total_eval_num,prod_num,prod_price,sale_amount_chain,amount_indu_ratio,g_eval_num,m_eval_num,b_eval_num,g_eval_num_ratio,m_eval_num_ratio,b_eval_num_ratio from ? where indu_id = ? and prod_id = ? order by sale_amount desc limit ?";

	public List<EssInduProdChanYear> getChannelSalesDynamicForProd(String tableName,
			long indu_id, long prod_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(channelSalesDynamicForProdSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, prod_id);
		prepareSql.setInt(4, topN);
		logger.info("getChannelSalesDynamicForProd SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduProdChanYear.class);
	}

	// 竞争分析-竞争单品分析-热卖店铺-ess_indu_shop_prod_top_yyyy
	private String hotShopForProdSql = "select indu_id,chan_id,chan_shop_id,prod_id,chan_shop_name,prod_name,sale_amount from ? where indu_id = ? and prod_id = ? and chan_shop_id > 0 order by sale_amount desc limit ?";

	public List<EssInduShopProdTopYear> getHotShopForProd(String tableName, long indu_id,
			long prod_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotShopForProdSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, prod_id);
		prepareSql.setInt(4, topN);
		logger.info("getHotShopForProd SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EssInduShopProdTopYear.class);
	}

	// 竞争分析-竞争单品分析-用户评价词-ese_indu_prod_yyyy
	private String userEvaluationForProdSql = "select indu_id,prod_id,label,prod_name,label_num from ? where indu_id = ? and prod_id = ? and label_polary = ? order by label_num desc limit ?";

	public List<EseInduProdYear> getUserEvaluationForProd(String tableName, long indu_id,
			long prod_id, int label_polary, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(userEvaluationForProdSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, prod_id);
		prepareSql.setInt(4, label_polary);
		prepareSql.setLong(5, topN);
		logger.info("getUserEvaluationForProd SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EseInduProdYear.class);
	}

	public void setEsDao(EsDao esDao)
	{
		this.esDao = esDao;
	}
}
