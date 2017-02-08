
package com.tunyun.product.ebc.web.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.system.cache.cache.CacheFactory;
import com.common.system.cache.cache.PCache;
import com.tunyun.product.ebc.web.common.CommonUtil;
import com.tunyun.product.ebc.web.common.Constants;
import com.tunyun.product.ebc.web.dao.EbcapiDAO;
import com.tunyun.product.ebc.web.es.IndustryInfoDao;
import com.tunyun.product.ebc.web.es.mo.EaiInfoMo;
import com.tunyun.product.ebc.web.es.mo.EsiInduFashBrand30dMo;
import com.tunyun.product.ebc.web.es.mo.EsiInduLabel30dMo;
import com.tunyun.product.ebc.web.es.mo.HotBrandInfoMo;
import com.tunyun.product.ebc.web.es.table.EsTablePrefix;

import net.sf.json.JSONObject;

/**
 * 产业前沿
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2015-10-28
 * @category com.tunyun.product.ebc.web.service
 */
public class IndustryInfoServ
{

	Logger logger = LoggerFactory.getLogger(IndustryInfoServ.class);
	// 正则匹配
	private Pattern pattern = Pattern.compile(Constants.check_url,
			Pattern.CASE_INSENSITIVE);
	// es
	private IndustryInfoDao es;
	// dao
	private EbcapiDAO dao;

	public void setEs(IndustryInfoDao es)
	{
		this.es = es;
	}

	public void setDao(EbcapiDAO dao)
	{
		this.dao = dao;
	}

	/**
	 * 获取行业资讯详情
	 * 
	 * @return
	 */
	public String industryDynaDetail(String param)
	{
		// 解析参数
		long indu_id = 1;
		long info_id = 1;
		JSONObject _jsonObject = JSONObject.fromObject(param);
		indu_id = CommonUtil.getJsonParam(_jsonObject, "indu_id");
		info_id = CommonUtil.getJsonParam(_jsonObject, "info_id");
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		EaiInfoMo eaiInfoDetail = getIndustryInfoDetail(indu_id, info_id);
		String eaiInfoDetailJson = JSONObject.fromObject(eaiInfoDetail).toString();
		logger.debug("eaiInfoDetailJson : {}", eaiInfoDetailJson);
		sb.append("\"DynmaicDetail\":").append(eaiInfoDetailJson).append(",");
		sb.append("\"GuessLikeDynamic\":").append("[]").append(",");
		sb.append("\"HaveReadDynamic\":").append("[]").append(",");
		sb.append("\"SameThemeDynamic\":").append("[]");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 获取行业资讯统计信息
	 * 
	 * @return
	 */
	public String industryDyna(String param)
	{
		// 解析参数
		long indu_id = 1;
		JSONObject _jsonObject = JSONObject.fromObject(param);
		indu_id = CommonUtil.getJsonParam(_jsonObject, "indu_id");
		// 查询维度信息
		List<Map<String, Object>> dataList = dao.getAllDime();
		Map<String, String> idMap = new HashMap<String, String>();
		for (Map<String, Object> m : dataList)
		{
			String uid = String.valueOf(m.get("uid"));
			String ids = String.valueOf(m.get("dime_ids"));
			idMap.put(uid, ids);
		}
		/**
		 * 资讯维度为分层级的维度例如：资讯潮流-10是国内资讯-11和国外资讯-12的父ID
		 */
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		// 潮流资讯
		List<EaiInfoMo> industryInfo = getIndustryInfo(indu_id, idMap.get("10"), 10);
		sb.append("\"article_summary\":").append(eaiInfoStr(industryInfo));
		sb.append(",");
		// 国内外趋势
		List<EaiInfoMo> inlandPopTrend = getPopTrendList(indu_id, "11", 5);
		sb.append("\"inland_fashion_trend\":").append(fashionTrendStr(inlandPopTrend));
		sb.append(",");
		List<EaiInfoMo> foreignPopTrend = getPopTrendList(indu_id, "12", 5);
		sb.append("\"foreign_fashion_trend\":").append(fashionTrendStr(foreignPopTrend));
		sb.append(",");
		// 热门品牌资讯
		List<HotBrandInfoMo> hotBrandInfo = getHotBrandInfo(indu_id, idMap.get("10"), 5);
		sb.append("\"brand_hot_dynamic\":").append(brandInfoStr(hotBrandInfo));
		sb.append(",");
		// 流行标签云
		List<EsiInduLabel30dMo> popLabelCloud = getLabelCloudList(indu_id,
				idMap.get("10"), 20);
		sb.append("\"PopLable\":").append(lableCloudStr(popLabelCloud));
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 生成json
	 * 
	 * @param eaiList
	 * @return
	 */
	private String lableCloudStr(List<EsiInduLabel30dMo> list)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++)
		{
			if (i > 0)
			{
				sb.append(",");
			}
			sb.append(list.get(i).getTableStr());
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 生成json
	 * 
	 * @param eaiList
	 * @return
	 */
	private String eaiInfoStr(List<EaiInfoMo> eaiList)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < eaiList.size(); i++)
		{
			if (i > 0)
			{
				sb.append(",");
			}
			sb.append(eaiList.get(i).getEaiInfo());
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 生成json
	 * 
	 * @param eaiList
	 * @return
	 */
	private String fashionTrendStr(List<EaiInfoMo> eaiList)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < eaiList.size(); i++)
		{
			if (i > 0)
			{
				sb.append(",");
			}
			sb.append(eaiList.get(i).getFashionTrend());
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 生成json
	 * 
	 * @param eaiList
	 * @return
	 */
	private String brandInfoStr(List<HotBrandInfoMo> list)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++)
		{
			if (i > 0)
			{
				sb.append(",");
			}
			sb.append(list.get(i).getBrandInfoStr());
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 获取热门品牌最新资讯
	 * 
	 * @param indu_id
	 * @param info_dime_id
	 * @param topN
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<HotBrandInfoMo> getHotBrandInfo(long indu_id, String info_dime_ids,
			int topN)
	{
		String esi_indu_fash_brand_30d_yyyy_table = EsTablePrefix.esi_indu_fash_brand_30d_yyyy
				+ CommonUtil.format("yyyy", -1) + "/" + CommonUtil.format("yyyyMMdd", -1);
		String eai_info_brand_yyyy_table = EsTablePrefix.eai_info_brand_yyyy
				+ CommonUtil.format("yyyy", -1) + "/" + CommonUtil.format("yyyyMMdd", -1);
		List<EsiInduFashBrand30dMo> esInduBrandList = es
				.getHotBrandId(esi_indu_fash_brand_30d_yyyy_table, indu_id, topN);
		// 创建热门品牌资讯对象
		HotBrandInfoMo hotBrandInfo = null;
		List<HotBrandInfoMo> hotBrandInfoList = new ArrayList<HotBrandInfoMo>();
		// 初始化缓存
		PCache mc = CacheFactory.PCache();
		Map<String, Object> entry = null;
		List<Map> hotBrandInfoIdList = null;
		for (EsiInduFashBrand30dMo esInduBrand : esInduBrandList)
		{
			hotBrandInfo = new HotBrandInfoMo();
			hotBrandInfo.setBid(esInduBrand.getBid());
			hotBrandInfo.setTitle_num(Math.round(esInduBrand.getAtte_degree()));
			hotBrandInfo.setIndu_id(indu_id);
			entry = (Map) mc.hget("tri_brand_id", String.valueOf(hotBrandInfo.getBid()),
					Map.class);
			if (null != entry)
			{
				hotBrandInfo.setIcon(String.valueOf(entry.get("brand_log")));
				hotBrandInfo.setTitleName(String.valueOf(entry.get("brand_name")));
			}
			// 获取资讯id
			hotBrandInfoIdList = es.getHotBrandInfoId(eai_info_brand_yyyy_table, indu_id,
					info_dime_ids, hotBrandInfo.getBid());
			for (Map tempMap : hotBrandInfoIdList)
			{
				// 将资讯id加入到bean
				hotBrandInfo
						.setInfo_id(Long.valueOf(String.valueOf(tempMap.get("info_id"))));
				List<Map> infoList = es.getHotEaiInfo(EsTablePrefix.eai_info, indu_id,
						hotBrandInfo.getInfo_id());
				for (Map map : infoList)
				{
					// 将资讯加入到bean
					String _title = (String) map.get("title");
					hotBrandInfo.setSummary(_title);
				}
			}
			hotBrandInfoList.add(hotBrandInfo);
		}
		return hotBrandInfoList;
	}

	private List<EsiInduLabel30dMo> getLabelCloudList(long indu_id, String info_dime_ids,
			int topN)
	{
		String tableName = EsTablePrefix.esi_indu_label_30d_yyyy
				+ CommonUtil.format("yyyy", -1) + "/" + CommonUtil.format("yyyyMMdd", -1);
		logger.debug("tableName is [{}]", tableName);
		List<EsiInduLabel30dMo> list = es.getLabelCloudList(tableName, indu_id,
				info_dime_ids, topN);
		for (EsiInduLabel30dMo info : list)
		{
			info.setText(info.getLabel());
			info.setWeight(info.getLabel_num() / 100);
		}
		return list;
	}

	private List<EaiInfoMo> getPopTrendList(long indu_id, String info_dime_ids, int topN)
	{
		String today = CommonUtil.format("yyyyMMdd", 0);// 当前日期
		String thirtyBeforeDay = CommonUtil.format("yyyyMMdd", -30);// 前推30天，日期
		long end_time = CommonUtil.formatDataTotimestampForSecond(today, "yyyyMMdd");// 开始时间
		long begin_time = CommonUtil.formatDataTotimestampForSecond(thirtyBeforeDay,
				"yyyyMMdd");// 结束时间
		List<EaiInfoMo> list = es.getInduInfoList(EsTablePrefix.eai_info, indu_id,
				info_dime_ids, begin_time, end_time, topN);
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<EaiInfoMo> getIndustryInfo(long indu_id, String info_dime_ids, int topN)
	{
		String today = CommonUtil.format("yyyyMMdd", 0);// 当前日期
		String thirtyBeforeDay = CommonUtil.format("yyyyMMdd", -30);// 前推30天，日期
		long end_time = CommonUtil.formatDataTotimestampForSecond(today, "yyyyMMdd");// 开始时间
		long begin_time = CommonUtil.formatDataTotimestampForSecond(thirtyBeforeDay,
				"yyyyMMdd");// 结束时间
		List<EaiInfoMo> list = es.getInduInfoList(EsTablePrefix.eai_info, indu_id,
				info_dime_ids, begin_time, end_time, topN);
		// 初始化缓存
		PCache mc = CacheFactory.PCache();
		Map<String, Object> entry = null;
		Matcher matcher = null;
		for (EaiInfoMo info : list)
		{
			// 设置时间
			info.setCollect_time_str(
					CommonUtil.format("yyyy-MM-dd HH:mm:ss", info.getC_time() * 1000L));
			if (null != info.getImgs() && !"".equals(info.getImgs().trim()))
			{
				List<String> imgArr = new ArrayList<String>();
				String[] _imgArr = info.getImgs()
						.split(String.valueOf(Constants.separate_2));
				for (String _img : _imgArr)
				{
					matcher = pattern.matcher(_img);
					if (matcher.find())
					{
						imgArr.add(_img);
					}
				}
				info.setImg_arr(imgArr.toArray());
			}
			// 通过缓存查询来源
			entry = (Map) mc.hget("tr_info_seed", String.valueOf(info.getInfo_seed_id()),
					Map.class);
			if (null != entry)
			{
				info.setInfo_seed_str(String.valueOf(entry.get("name")));
			}
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private EaiInfoMo getIndustryInfoDetail(long indu_id, long info_id)
	{
		List<EaiInfoMo> list = es.getInduInfoDetail(EsTablePrefix.eai_info, indu_id,
				info_id);
		// 初始化缓存
		PCache mc = CacheFactory.PCache();
		EaiInfoMo info = null;
		if (list.size() != 0)
		{
			info = list.get(0);
			// 设置时间
			info.setCollect_time_str(
					CommonUtil.format("yyyy-MM-dd HH:mm:ss", info.getC_time() * 1000L));
			// 通过缓存查询来源
			Map<String, Object> tr_info_seed_map = (Map) mc.hget("tr_info_seed",
					String.valueOf(info.getInfo_seed_id()), Map.class);
			if (null != tr_info_seed_map)
			{
				info.setInfo_seed_str(String.valueOf(tr_info_seed_map.get("name")));
			}
			// 通过缓存查询维度分类
			Map<String, Object> tr_info_dime_map = (Map) mc.hget("tr_info_dime",
					String.valueOf(info.getInfo_dime_id()), Map.class);
			if (null != tr_info_dime_map)
			{
				info.setInfo_dime_str(String.valueOf(tr_info_dime_map.get("name")));
			}
		}
		return info;
	}

	public static void main(String[] args)
	{
		String today = CommonUtil.format("yyyyMMdd", 0);// 当前日期
		String thirtyBeforeDay = CommonUtil.format("yyyyMMdd", -30);// 前推30天，日期
		long end_time = CommonUtil.formatDataTotimestampForSecond(today, "yyyyMMdd");// 开始时间
		long begin_time = CommonUtil.formatDataTotimestampForSecond(thirtyBeforeDay,
				"yyyyMMdd");// 结束时间
		System.out.println(begin_time);
		System.out.println(end_time);
	}
}