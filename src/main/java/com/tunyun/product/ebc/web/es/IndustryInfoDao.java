
package com.tunyun.product.ebc.web.es;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.system.utils.database.PrepareSQL;
import com.tunyun.product.ebc.web.es.mo.EaiInfoMo;
import com.tunyun.product.ebc.web.es.mo.EsiInduFashBrand30dMo;
import com.tunyun.product.ebc.web.es.mo.EsiInduLabel30dMo;
import com.tunyun.product.ebc.web.es.mo.EsiPolicyApprMO;
import com.tunyun.product.ebc.web.es.mo.EsiSentBrandMO;
import com.tunyun.product.ebc.web.es.mo.EsiSentCatMO;
import com.tunyun.product.ebc.web.es.mo.EsiTopicApprMO;

/**
 * 产业前沿
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2015-10-28
 * @category com.tunyun.product.ebc.web.dao
 */
/**
 * @author gaojinbao
 * @version 1.0
 * @since 2016年1月26日
 * @category com.tunyun.product.ebc.web.es
 */
public class IndustryInfoDao
{

	private static final Logger logger = LoggerFactory.getLogger(IndustryInfoDao.class);
	// 潮流资讯表表前缀
	public String eai_info_yyyymm = "eai_info_";
	// 潮流资讯标签表前缀
	public String esi_indu_label_30d_yyyy = "esi_indu_label_30d_";
	// ESI行业+潮流热门品牌-最近30天 统计 表
	public String esi_indu_fash_brand_30d_yyyy = "esi_indu_fash_brand_30d_";
	// 资讯品牌表
	public String eai_info_brand_yyyy = "eai_info_brand_";
	// 资讯品类表
	public String eai_info_cat_yyyy = "eai_info_cat_";
	// ESI行业+舆论热门品牌-最近30天 统计 表
	public String esi_indu_sent_brand_yyyy = "esi_indu_sent_brand_";
	// ESI行业+舆论热门品类-最近30天 统计 表
	public String esi_indu_sent_cat_yyyy = "esi_indu_sent_cat_";
	// ESI行业+舆论政策褒贬度-最近30天 统计 表
	public String esi_indu_policy_appr_30d_yyyy = "esi_indu_policy_appr_30d_";
	// ESI行业+舆论话题褒贬度-最近30天 统计 表
	public String esi_indu_topic_appr_30d_yyyy = "esi_indu_topic_appr_30d_";
	// 产业资讯摘要列表 eai_info_yyyymm
	private String industryInfoSql = "select uid,indu_ids,info_dime_id,title,read_num,c_time,a_time,img_num,imgs,info_seed_id,summary,label from ? where indu_ids = ? and info_dime_id in (?) and a_time >= ? and a_time <= ? order by c_time desc limit ?";
	private String industryInfoSql2 = "select uid,indu_ids,info_dime_id,title,read_num,c_time,a_time,img_num,imgs,info_seed_id,summary,label from ? where uid in (?) and a_time >= ? and a_time <= ? order by c_time desc limit ?";
	// 国内外流行趋势
	// private String popTrendSql = "select title,read_num from ? where indu_id
	// = ? and info_dime_id = ? order by label_cloud_dist limit ?";
	// 标签云 esi_indu_label_30d_yyyy
	private String labelCloudSql = "select label,label_num from ? where indu_id = ? and info_dime_id in (?) order by label_num desc limit ?";
	// 热门品牌 esi_indu_fash_brand_30d_yyyy
	private String hotBrandIdSql = "select bid,atte_degree from ? where indu_id = ? order by atte_degree desc limit ?";
	// 热门品牌 eai_info_brand_yyyy
	private String hotBrandInfoIdSql = "select bid,info_id from ? where indu_ids = ? and info_dime_id in (?) and bid = ? order by c_time desc limit 1";
	// 热门品类 eai_info_cat_yyyy
	private String hotCatInfoIdSql = "select cid,info_id from ? where indu_ids = ? and info_dime_id in (?) and cid = ? order by c_time desc limit 1";
	// 热门品类 eai_info_yyyymm
	private String hotEaiInfoSql = "select uid,title,read_num from ? where indu_ids = ? and uid = ?";
	// 产业资讯摘要 eai_info_yyyymm
	private String industryInfoDetailSql = "select uid,indu_ids,info_dime_id,title,read_num,c_time,a_time,info_seed_id,orig_cont from ? where indu_ids = ? and uid = ?";
	private EsDao esDao;
	// 行业政策舆情 esi_indu_policy_appr_30d_yyyy
	private String esi_indu_policy_appr_30d = "select indu_id,sent_name,stat_time,atte_degree,appr_degree,g_eval_num,m_eval_num,b_eval_num from ? where indu_id=? order by atte_degree desc limit ?";
	// 行业话题舆情 esi_indu_topic_appr_30d_yyyy
	private String esi_indu_topic_appr_30d = "select indu_id,label,stat_time,atte_degree,appr_degree,g_eval_num,m_eval_num,b_eval_num from ? where indu_id=? order by atte_degree desc limit ?";
	// 舆论热门品牌 esi_indu_sent_brand_yyyy
	private String esi_indu_sent_brand = "select indu_id,bid,stat_time,brand_name,atte_degree,g_eval_num,m_eval_num,b_eval_num from ? where indu_id=? order by atte_degree desc limit ?";
	// 舆论热门品类 esi_indu_sent_cat_yyyy
	private String esi_indu_sent_cat = "select indu_id,cid,stat_time,cat_name,atte_degree,g_eval_num,m_eval_num,b_eval_num from ? where indu_id=? order by atte_degree desc limit ?";

	/**
	 * 获取热门品牌ID
	 * 
	 * @param tableName
	 * @param indu_id
	 * @param topN
	 * @return
	 */
	public List<EsiInduFashBrand30dMo> getHotBrandId(String tableName, long indu_id,
			int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotBrandIdSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, topN);
		logger.info("getHotBrandId SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EsiInduFashBrand30dMo.class);
	}

	/**
	 * 获取品牌热门资讯ID
	 * 
	 * @param tableName
	 * @param indu_id
	 * @param info_dime_id
	 * @param bidStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getHotBrandInfoId(String tableName, long indu_id,
			String info_dime_id, long bid)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotBrandInfoIdSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setStringExt(3, info_dime_id, false);
		prepareSql.setLong(4, bid);
		logger.info("getHotBrandInfoId SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), Map.class);
	}

	/**
	 * 获取品类热门资讯ID
	 * 
	 * @param tableName
	 * @param indu_id
	 * @param info_dime_id
	 * @param bidStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getHotCatInfoId(String tableName, long indu_id, String info_dime_id,
			long bid)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotCatInfoIdSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setStringExt(3, info_dime_id, false);
		prepareSql.setLong(4, bid);
		logger.info("getHotCatInfoId SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), Map.class);
	}

	/**
	 * 获取品牌热门资讯
	 * 
	 * @param tableName
	 * @param indu_id
	 * @param info_id_str
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getHotEaiInfo(String tableName, long indu_id, long info_id)
	{
		PrepareSQL prepareSql = new PrepareSQL(hotEaiInfoSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, info_id);
		logger.info("getHotBrandInfo SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), Map.class);
	}

	/**
	 * 获取产业流行标签云
	 * 
	 * @param tableName
	 * @param indu_id
	 * @param info_dime_id
	 * @return
	 */
	public List<EsiInduLabel30dMo> getLabelCloudList(String tableName, long indu_id,
			String info_dime_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(labelCloudSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setStringExt(3, info_dime_id, false);
		prepareSql.setInt(4, topN);
		logger.info("getLabelCloudList SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EsiInduLabel30dMo.class);
	}

	/**
	 * 获取产业资讯列表
	 * 
	 * @param tableName
	 * @param indu_id
	 * @param info_dime_id
	 * @param topN
	 * @return
	 */
	public List<EaiInfoMo> getInduInfoList(String tableName, long indu_id,
			String info_dime_id, long begin_time, long end_time, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(industryInfoSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setStringExt(3, info_dime_id, false);
		prepareSql.setLong(4, begin_time);
		prepareSql.setLong(5, end_time);
		prepareSql.setInt(6, topN);
		logger.info("getInduInfoList SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EaiInfoMo.class);
	}

	/**
	 * 获取产业资讯列表
	 * 
	 * @param tableName
	 * @param indu_id
	 * @param info_dime_id
	 * @param topN
	 * @return
	 */
	public List<EaiInfoMo> getInduInfoListById(String tableName, String infoIds,
			long begin_time, long end_time, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(industryInfoSql2);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setStringExt(2, infoIds, false);
		prepareSql.setLong(3, begin_time);
		prepareSql.setLong(4, end_time);
		prepareSql.setInt(5, topN);
		logger.info("getInduInfoList SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EaiInfoMo.class);
	}

	/**
	 * 获取指定产业资讯
	 * 
	 * @param sqlStr
	 * @param tableName
	 * @param indu_id
	 * @param info_dime_id
	 * @param topN
	 * @return
	 */
	public List<EaiInfoMo> getInduInfoDetail(String tableName, long indu_id, long info_id)
	{
		PrepareSQL prepareSql = new PrepareSQL(industryInfoDetailSql);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, info_id);
		logger.info("getInduInfoDetail SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EaiInfoMo.class);
	}

	/**
	 * 获取舆论政策统计信息
	 * 
	 * @param sqlStr
	 * @param tableName
	 * @param indu_id
	 * @param topN
	 * @return
	 */
	public List<EsiPolicyApprMO> getEsiPolicyAppr(String tableName, long indu_id,
			int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(esi_indu_policy_appr_30d);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, topN);
		logger.info("getEsiPolicyAppr SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EsiPolicyApprMO.class);
	}

	/**
	 * 获取舆论话题统计信息
	 * 
	 * @param sqlStr
	 * @param tableName
	 * @param indu_id
	 * @param topN
	 * @return
	 */
	public List<EsiTopicApprMO> getEsiTopicAppr(String tableName, long indu_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(esi_indu_topic_appr_30d);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, topN);
		logger.info("getEsiTopicAppr SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EsiTopicApprMO.class);
	}

	/**
	 * 获取舆论热门品牌统计信息
	 * 
	 * @param sqlStr
	 * @param tableName
	 * @param indu_id
	 * @param topN
	 * @return
	 */
	public List<EsiSentBrandMO> getEsiSentBrand(String tableName, long indu_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(esi_indu_sent_brand);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, topN);
		logger.info("getEsiSentBrand SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EsiSentBrandMO.class);
	}

	/**
	 * 获取舆论热门品类统计信息
	 * 
	 * @param sqlStr
	 * @param tableName
	 * @param indu_id
	 * @param topN
	 * @return
	 */
	public List<EsiSentCatMO> getEsiSentCat(String tableName, long indu_id, int topN)
	{
		PrepareSQL prepareSql = new PrepareSQL(esi_indu_sent_cat);
		prepareSql.setStringExt(1, tableName, false);
		prepareSql.setLong(2, indu_id);
		prepareSql.setLong(3, topN);
		logger.info("getEsiSentCat SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), EsiSentCatMO.class);
	}

	/**
	 * 获取EAI-资讯舆论表
	 * 
	 * @param tableName
	 * @param policyName
	 * @param polar
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getEaiInfoSent(String tableName, long indu_id, String policyName,
			String polar, long begin_time, long end_time)
	{
		PrepareSQL prepareSql = null;
		if (null == polar)
		{
			prepareSql = new PrepareSQL(
					"select info_id from ? where indu_ids = ? and sent_name = ? and a_time >= ? and a_time <= ? ");
			prepareSql.setStringExt(1, tableName, false);
			prepareSql.setLong(2, indu_id);
			prepareSql.setString(3, policyName);
			prepareSql.setLong(4, begin_time);
			prepareSql.setLong(5, end_time);
		}
		else
		{
			prepareSql = new PrepareSQL(
					"select info_id from ? where indu_ids = ? and sent_name = ? and sent_polary = ? and a_time >= ? and a_time <= ? ");
			prepareSql.setStringExt(1, tableName, false);
			prepareSql.setLong(2, indu_id);
			prepareSql.setString(3, policyName);
			prepareSql.setStringExt(4, polar, false);
			prepareSql.setLong(5, begin_time);
			prepareSql.setLong(6, end_time);
		}
		logger.info("getEaiInfoSent SQL: {}", prepareSql.getSQL());
		return esDao.search(prepareSql.getSQL(), Map.class);
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
