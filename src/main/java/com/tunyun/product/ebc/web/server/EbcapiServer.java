
package com.tunyun.product.ebc.web.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.common.system.cache.cache.CacheFactory;
import com.common.system.cache.cache.PCache;
import com.tunyun.product.ebc.web.common.CommonUtil;
import com.tunyun.product.ebc.web.common.Constants;
import com.tunyun.product.ebc.web.dao.EbcapiDAO;
import com.tunyun.product.ebc.web.es.IndustryInfoDao;
import com.tunyun.product.ebc.web.es.mo.EaiInfoMo;
import com.tunyun.product.ebc.web.es.mo.EsiHotBrandMO;
import com.tunyun.product.ebc.web.es.mo.EsiHotCatMO;
import com.tunyun.product.ebc.web.es.mo.EsiInduLabel30dMo;
import com.tunyun.product.ebc.web.es.mo.EsiPolicyApprMO;
import com.tunyun.product.ebc.web.es.mo.EsiSentBrandMO;
import com.tunyun.product.ebc.web.es.mo.EsiSentCatMO;
import com.tunyun.product.ebc.web.es.mo.EsiTopicApprMO;
import com.tunyun.product.ebc.web.es.table.EsTablePrefix;

import net.sf.json.JSONObject;

/**
 * 接口业务处理类
 * 
 * @author czm
 */
public class EbcapiServer
{

	// es
	private IndustryInfoDao es;
	// dao
	private EbcapiDAO dao;
	// 正则匹配
	private Pattern pattern = Pattern.compile(Constants.check_url,
			Pattern.CASE_INSENSITIVE);

	public void setEs(IndustryInfoDao es)
	{
		this.es = es;
	}

	public void setDao(EbcapiDAO dao)
	{
		this.dao = dao;
	}

	/**
	 * 钻取详情
	 * 
	 * @param param
	 * @return
	 */
	public String getPolicySentimentDrillDetail(String param)
	{
		JSONObject _jsonObject = JSONObject.fromObject(param);
		long indu_id = CommonUtil.getJsonParam(_jsonObject, "indu_id");
		long info_id = CommonUtil.getJsonParam(_jsonObject, "info_id");
		Map<String, EaiInfoMo> eimap = new HashMap<String, EaiInfoMo>();
		EaiInfoMo ei = getIndustryInfoDetail(indu_id, info_id);
		eimap.put("DrillDetail", null != ei ? ei : new EaiInfoMo());
		String jsonStr = JSONObject.fromObject(eimap).toString();
		return jsonStr;
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

	/**
	 * 行业政策舆情页面钻取详情
	 * 
	 * @param param
	 * @return
	 */
	public String getPolicySentimentDrill(String param)
	{
		// 解析参数
		JSONObject _jsonObject = JSONObject.fromObject(param);
		long indu_id = CommonUtil.getJsonParam(_jsonObject, "indu_id");// 舆情政策名称
		String itemName = CommonUtil.getJsonParamForString(_jsonObject, "itemName");// 舆情政策名称
		String polar = CommonUtil.getJsonParamForString(_jsonObject, "polar");// 舆情政策极性
		String year = CommonUtil.format("yyyy", -1);// 当前年
		String today = CommonUtil.format("yyyyMMdd", 0);// 当前日期
		String thirtyBeforeDay = CommonUtil.format("yyyyMMdd", -30);// 前推30天，日期
		long end_time = CommonUtil.formatDataTotimestampForSecond(today, "yyyyMMdd");// 开始时间
		long begin_time = CommonUtil.formatDataTotimestampForSecond(thirtyBeforeDay,
				"yyyyMMdd");// 结束时间
		// 获取EAI-资讯舆论表
		List<Map> infoIdLst = es.getEaiInfoSent(EsTablePrefix.eai_info_sent, indu_id,
				itemName, polar, begin_time, end_time);
		StringBuffer sb = new StringBuffer();
		if (infoIdLst.size() > 0)
		{
			StringBuffer infoIdStr = new StringBuffer();
			for (int i = 0; i < infoIdLst.size(); i++)
			{
				if (i > 0)
				{
					infoIdStr.append(",");
				}
				infoIdStr.append(infoIdLst.get(i).get("info_id"));
			}
			// 根据资讯id查找资讯表
			List<EaiInfoMo> eiList = es.getInduInfoListById(EsTablePrefix.eai_info,
					infoIdStr.toString(), begin_time, end_time, 50);
			// 初始化缓存
			PCache mc = CacheFactory.PCache();
			Map<String, Object> entry = null;
			Matcher matcher = null;
			for (EaiInfoMo info : eiList)
			{
				// 设置时间
				info.setCollect_time_str(CommonUtil.format("yyyy-MM-dd HH:mm:ss",
						info.getC_time() * 1000L));
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
				entry = (Map) mc.hget("tr_info_seed",
						String.valueOf(info.getInfo_seed_id()), Map.class);
				if (null != entry)
				{
					info.setInfo_seed_str(String.valueOf(entry.get("name")));
				}
			}
			// 组装json
			sb.append("{");
			sb.append("\"sentiment_summary\":").append(eaiInfoStr(eiList));
			sb.append(",");
			sb.append("\"sentiment_list\":").append(eaiInfoStr2(eiList));
			sb.append("}");
		}
		else
		{
			sb.append("{");
			sb.append("\"sentiment_summary\":[]");
			sb.append(",");
			sb.append("\"sentiment_list\":[]");
			sb.append("}");
		}
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
			if (i < 10)
			{
				if (i > 0)
				{
					sb.append(",");
				}
				sb.append(eaiList.get(i).getEaiInfo());
			}
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
	private String eaiInfoStr2(List<EaiInfoMo> eaiList)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < eaiList.size(); i++)
		{
			if (i > 10)
			{
				if (i > 11)
				{
					sb.append(",");
				}
				sb.append(eaiList.get(i).getFashionTrend());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 获取网络舆情分析页面信息
	 * 
	 * @param param
	 * @return
	 */
	public String getNetworkAppr2(String param)
	{
		// 解析参数
		long indu_id = 0;
		JSONObject _jsonObject = JSONObject.fromObject(param);
		indu_id = CommonUtil.getJsonParam(_jsonObject, "indu_id");
		String yyyy = CommonUtil.format("yyyy", -1);
		String yyyymmdd = CommonUtil.format("yyyyMMdd", -1);
		String yyyy_mm = CommonUtil.format("yyyy-MM", -1);
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		// 查询行业政策舆情
		List<EsiPolicyApprMO> EsiPolicyApprList = es.getEsiPolicyAppr(
				es.esi_indu_policy_appr_30d_yyyy + yyyy + "/" + yyyymmdd, indu_id, 10);
		// 查询行业话题舆情
		List<EsiTopicApprMO> EsiTopicApprList = es.getEsiTopicAppr(
				es.esi_indu_topic_appr_30d_yyyy + yyyy + "/" + yyyymmdd, indu_id, 10);
		// 查询热门品牌舆情
		List<EsiSentBrandMO> esiSentBrandList = es.getEsiSentBrand(
				es.esi_indu_sent_brand_yyyy + yyyy + "/" + yyyymmdd, indu_id, 10);
		// 查询热门品类舆情
		List<EsiSentCatMO> esiSentCatList = es.getEsiSentCat(
				es.esi_indu_sent_cat_yyyy + yyyy + "/" + yyyymmdd, indu_id, 10);
		// 行业舆情数据
		float ploicyTotal = 0f;
		for (EsiPolicyApprMO plicyMo : EsiPolicyApprList)
		{
			ploicyTotal += plicyMo.getAtte_degree();
		}
		for (EsiPolicyApprMO plicyMo : EsiPolicyApprList)
		{
			plicyMo.setTotal(ploicyTotal);
		}
		sb.append(createPolicyApprTable(EsiPolicyApprList));
		sb.append(",");
		sb.append(createPolicyApprChart(EsiPolicyApprList, yyyy_mm));
		sb.append(",");
		// 行业话题数据
		sb.append(createTopicApprChart(EsiTopicApprList, yyyy_mm));
		sb.append(",");
		// 热门品牌数据
		float brandTotal = 0f;
		for (EsiSentBrandMO brandMo : esiSentBrandList)
		{
			brandTotal += brandMo.getAtte_degree();
		}
		for (EsiSentBrandMO brandMo : esiSentBrandList)
		{
			brandMo.setTotal(brandTotal);
		}
		sb.append(createSentBrandTable(esiSentBrandList));
		sb.append(",");
		sb.append(createSentBrandChart(esiSentBrandList, yyyy_mm));
		sb.append(",");
		// 热门品类数据
		float catTotal = 0f;
		for (EsiSentCatMO catMo : esiSentCatList)
		{
			catTotal += catMo.getAtte_degree();
		}
		for (EsiSentCatMO catMo : esiSentCatList)
		{
			catMo.setTotal(catTotal);
		}
		sb.append(createSentCatTable(esiSentCatList));
		sb.append(",");
		sb.append(createSentCatChart(esiSentCatList, yyyy_mm));
		sb.append(",");
		// 查询行业话题标签
		List<EsiInduLabel30dMo> EsiInduLabel30dList = es.getLabelCloudList(
				es.esi_indu_label_30d_yyyy + yyyy + "/" + yyyymmdd, indu_id, "22", 10);
		sb.append(createLabelCloud(EsiInduLabel30dList));
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 获取网络舆情分析页面信息
	 * 
	 * @param param
	 * @return
	 */
	public String getNetworkAppr(String param)
	{
		// 解析参数
		long indu_id = 0;
		JSONObject _jsonObject = JSONObject.fromObject(param);
		indu_id = CommonUtil.getJsonParam(_jsonObject, "indu_id");
		String yyyy = CommonUtil.format("yyyy", -1);
		String yyyy_mm = CommonUtil.format("yyyy-MM", -1);
		String yyyymmdd = CommonUtil.format("yyyyMMdd", -1);
		String today = CommonUtil.format("yyyyMMdd", 0);// 当前日期
		String thirtyBeforeDay = CommonUtil.format("yyyyMMdd", -30);// 前推30天，日期
		long end_time = CommonUtil.formatDataTotimestampForSecond(today, "yyyyMMdd");// 开始时间
		long begin_time = CommonUtil.formatDataTotimestampForSecond(thirtyBeforeDay,
				"yyyyMMdd");// 结束时间
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		// 查询热门品牌舆情
		List<EsiSentBrandMO> esiSentBrandList = es.getEsiSentBrand(
				es.esi_indu_sent_brand_yyyy + yyyy + "/" + yyyymmdd, indu_id, 10);
		// 查询热门品类舆情
		List<EsiSentCatMO> esiSentCatList = es.getEsiSentCat(
				es.esi_indu_sent_cat_yyyy + yyyy + "/" + yyyymmdd, indu_id, 10);
		// 查询热门政策动态
		List<EaiInfoMo> EaiInfoList = es.getInduInfoList(EsTablePrefix.eai_info, indu_id,
				"21", begin_time, end_time, 10);
		sb.append(createPolicyApprList(EaiInfoList));
		sb.append(",");
		// 查询热门品牌动态
		List<EsiHotBrandMO> EsiHotBrandList = getHotBrandStat(indu_id, "20,21,22",
				esiSentBrandList);
		sb.append(createSentBrandList(EsiHotBrandList));
		sb.append(",");
		// 查询热门品类动态
		List<EsiHotCatMO> EsiHotCatList = getHotCatStat(indu_id, "20,21,22",
				esiSentCatList);
		sb.append(createSentCatList(EsiHotCatList));
		sb.append(",");
		// 查询行业政策舆情
		List<EsiPolicyApprMO> EsiPolicyApprList = es.getEsiPolicyAppr(
				es.esi_indu_policy_appr_30d_yyyy + yyyy + "/" + yyyymmdd, indu_id, 10);
		// 查询行业话题舆情
		List<EsiTopicApprMO> EsiTopicApprList = es.getEsiTopicAppr(
				es.esi_indu_topic_appr_30d_yyyy + yyyy + "/" + yyyymmdd, indu_id, 10);
		// 行业舆情数据
		float ploicyTotal = 0f;
		for (EsiPolicyApprMO plicyMo : EsiPolicyApprList)
		{
			ploicyTotal += plicyMo.getAtte_degree();
		}
		for (EsiPolicyApprMO plicyMo : EsiPolicyApprList)
		{
			plicyMo.setTotal(ploicyTotal);
		}
		sb.append(createPolicyApprTable(EsiPolicyApprList));
		sb.append(",");
		sb.append(createPolicyApprChart(EsiPolicyApprList, yyyy_mm));
		sb.append(",");
		// 行业话题数据
		sb.append(createTopicApprChart(EsiTopicApprList, yyyy_mm));
		sb.append(",");
		// 热门品牌数据
		float brandTotal = 0f;
		for (EsiSentBrandMO brandMo : esiSentBrandList)
		{
			brandTotal += brandMo.getAtte_degree();
		}
		for (EsiSentBrandMO brandMo : esiSentBrandList)
		{
			brandMo.setTotal(brandTotal);
		}
		sb.append(createSentBrandTable(esiSentBrandList));
		sb.append(",");
		sb.append(createSentBrandChart(esiSentBrandList, yyyy_mm));
		sb.append(",");
		// 热门品类数据
		float catTotal = 0f;
		for (EsiSentCatMO catMo : esiSentCatList)
		{
			catTotal += catMo.getAtte_degree();
		}
		for (EsiSentCatMO catMo : esiSentCatList)
		{
			catMo.setTotal(catTotal);
		}
		sb.append(createSentCatTable(esiSentCatList));
		sb.append(",");
		sb.append(createSentCatChart(esiSentCatList, yyyy_mm));
		sb.append(",");
		// 查询行业话题标签
		List<EsiInduLabel30dMo> EsiInduLabel30dList = es.getLabelCloudList(
				es.esi_indu_label_30d_yyyy + yyyy + "/" + yyyymmdd, indu_id, "22", 10);
		sb.append(createLabelCloud(EsiInduLabel30dList));
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 获取热门品牌最新资讯
	 * 
	 * @param indu_id
	 * @param info_dime_id
	 * @param esiSentBrandList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	private List<EsiHotBrandMO> getHotBrandStat(long indu_id, String info_dime_ids,
			List<EsiSentBrandMO> esiSentBrandList)
	{
		String yyyy = CommonUtil.format("yyyy", -1);
		String yyyymm = CommonUtil.format("yyyyMM", -1);
		String eai_info_brand_yyyy_table = es.eai_info_brand_yyyy + yyyy;
		String eai_info_yyyymm_table = es.eai_info_yyyymm + yyyymm;
		// 创建热门品牌资讯对象
		EsiHotBrandMO hotBrandInfo = null;
		List<EsiHotBrandMO> hotBrandInfoList = new ArrayList<EsiHotBrandMO>();
		// 初始化缓存
		List<Map> hotBrandInfoIdList = null;
		String _title = null;
		long readNum = 0;
		for (EsiSentBrandMO esiSentBrand : esiSentBrandList)
		{
			hotBrandInfo = new EsiHotBrandMO();
			hotBrandInfo.setBid(esiSentBrand.getBid());
			// 获取资讯id
			hotBrandInfoIdList = es.getHotBrandInfoId(eai_info_brand_yyyy_table, indu_id,
					info_dime_ids, hotBrandInfo.getBid());
			for (Map tempMap : hotBrandInfoIdList)
			{
				// 将资讯id加入到bean
				hotBrandInfo
						.setInfo_id(Long.valueOf(String.valueOf(tempMap.get("info_id"))));
				List<Map> infoList = es.getHotEaiInfo(eai_info_yyyymm_table, indu_id,
						hotBrandInfo.getInfo_id());
				for (Map map : infoList)
				{
					// 将资讯加入到bean
					_title = String.valueOf(map.get("title"));
					readNum = Long.valueOf(String.valueOf(map.get("read_num")));
					hotBrandInfo.setTitle(_title);
					hotBrandInfo.setReadNum(readNum);
				}
			}
			if (hotBrandInfo.getTitle() != null)
			{
				hotBrandInfoList.add(hotBrandInfo);
			}
		}
		return hotBrandInfoList;
	}

	/**
	 * 获取热门品类最新资讯
	 * 
	 * @param indu_id
	 * @param info_dime_id
	 * @param esiSentCatList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	private List<EsiHotCatMO> getHotCatStat(long indu_id, String info_dime_id,
			List<EsiSentCatMO> esiSentCatList)
	{
		String yyyy = CommonUtil.format("yyyy", -1);
		String yyyymm = CommonUtil.format("yyyyMM", -1);
		String eai_info_cat_yyyy_table = es.eai_info_cat_yyyy + yyyy;
		String eai_info_yyyymm_table = es.eai_info_yyyymm + yyyymm;
		// 创建热门品牌资讯对象
		EsiHotCatMO hotCatInfo = null;
		List<EsiHotCatMO> hotCatInfoList = new ArrayList<EsiHotCatMO>();
		// 初始化缓存
		List<Map> hotCatInfoIdList = null;
		String _title = null;
		long readNum = 0;
		for (EsiSentCatMO esiSentCat : esiSentCatList)
		{
			hotCatInfo = new EsiHotCatMO();
			hotCatInfo.setCid(esiSentCat.getCid());
			// 获取资讯id
			hotCatInfoIdList = es.getHotCatInfoId(eai_info_cat_yyyy_table, indu_id,
					info_dime_id, hotCatInfo.getCid());
			for (Map tempMap : hotCatInfoIdList)
			{
				// 将资讯id加入到bean
				hotCatInfo
						.setInfo_id(Long.valueOf(String.valueOf(tempMap.get("info_id"))));
				List<Map> infoList = es.getHotEaiInfo(eai_info_yyyymm_table, indu_id,
						hotCatInfo.getInfo_id());
				for (Map map : infoList)
				{
					// 将资讯加入到bean
					_title = String.valueOf(map.get("title"));
					readNum = Long.valueOf(String.valueOf(map.get("read_num")));
					hotCatInfo.setTitle(_title);
					hotCatInfo.setReadNum(readNum);
				}
			}
			if (hotCatInfo.getTitle() != null)
			{
				hotCatInfoList.add(hotCatInfo);
			}
		}
		return hotCatInfoList;
	}

	/**
	 * 生成行业舆情表格
	 * 
	 * @param EsiPolicyApprList
	 * @return
	 */
	private String createPolicyApprTable(List<EsiPolicyApprMO> EsiPolicyApprList)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\"InduPolicy\":[");
		EsiPolicyApprMO mo;
		if (EsiPolicyApprList.size() > 0)
		{
			for (int i = 0; i < EsiPolicyApprList.size(); i++)
			{
				if (i > 0)
				{
					sb.append(",");
				}
				mo = EsiPolicyApprList.get(i);
				mo.setIndex(i + 1);
				sb.append(mo.getTableStr());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 生成行业品牌表格
	 * 
	 * @param EsiPolicyApprList
	 * @return
	 */
	private String createSentBrandTable(List<EsiSentBrandMO> esiSentBrandList)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\"HotBrandSet\":[");
		EsiSentBrandMO mo;
		if (esiSentBrandList.size() > 0)
		{
			for (int i = 0; i < esiSentBrandList.size(); i++)
			{
				if (i > 0)
				{
					sb.append(",");
				}
				mo = esiSentBrandList.get(i);
				mo.setIndex(i + 1);
				sb.append(mo.getTableStr());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 生成行业品类表格
	 * 
	 * @param EsiPolicyApprList
	 * @return
	 */
	private String createSentCatTable(List<EsiSentCatMO> esiSentCatList)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\"HotCategorySet\":[");
		EsiSentCatMO mo;
		if (esiSentCatList.size() > 0)
		{
			for (int i = 0; i < esiSentCatList.size(); i++)
			{
				if (i > 0)
				{
					sb.append(",");
				}
				mo = esiSentCatList.get(i);
				mo.setIndex(i + 1);
				sb.append(mo.getTableStr());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 生成行业舆情图形
	 * 
	 * @param EsiPolicyApprList
	 * @return
	 */
	private String createPolicyApprChart(List<EsiPolicyApprMO> EsiPolicyApprList,
			String yyyy_mm)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\"InduPolicyRatio\":[");
		if (EsiPolicyApprList.size() > 0)
		{
			for (int i = 0; i < EsiPolicyApprList.size(); i++)
			{
				if (i > 0)
				{
					sb.append(",");
				}
				sb.append(EsiPolicyApprList.get(i).getChartStr());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 生成行业话题图形
	 * 
	 * @param EsiPolicyApprList
	 * @return
	 */
	private String createTopicApprChart(List<EsiTopicApprMO> EsiTopicApprList,
			String yyyy_mm)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\"InduTopicSet\":{");
		StringBuffer categories = new StringBuffer();
		StringBuffer data0 = new StringBuffer();
		StringBuffer data1 = new StringBuffer();
		EsiTopicApprMO mo;
		if (EsiTopicApprList.size() > 0)
		{
			for (int i = 0; i < EsiTopicApprList.size(); i++)
			{
				if (i > 0)
				{
					categories.append(",");
					data0.append(",");
					data1.append(",");
				}
				mo = EsiTopicApprList.get(i);
				categories.append("\"").append(mo.getLabel()).append("\"");
				data0.append(mo.getAtte_degree());
				data1.append(mo.getAppr_degree());
			}
		}
		sb.append("\"categories\":[").append(categories.toString()).append("],");
		sb.append("\"series\":[{\"name\":\"热情指数\",");
		sb.append("\"data\":[").append(data0.toString()).append("]},");
		sb.append("{\"name\":\"舆论表情\",");
		sb.append("\"data\":[").append(data1.toString()).append("]}]");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 生成行业品牌图形
	 * 
	 * @param EsiPolicyApprList
	 * @return
	 */
	private String createSentBrandChart(List<EsiSentBrandMO> esiSentBrandList,
			String yyyy_mm)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\"HotBrandSetRatio\":[");
		if (esiSentBrandList.size() > 0)
		{
			for (int i = 0; i < esiSentBrandList.size(); i++)
			{
				if (i > 0)
				{
					sb.append(",");
				}
				sb.append(esiSentBrandList.get(i).getChartStr());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 生成行业品类图形
	 * 
	 * @param EsiPolicyApprList
	 * @return
	 */
	private String createSentCatChart(List<EsiSentCatMO> esiSentCatList, String yyyy_mm)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\"HotCategorySetRatio\":[");
		if (esiSentCatList.size() > 0)
		{
			for (int i = 0; i < esiSentCatList.size(); i++)
			{
				if (i > 0)
				{
					sb.append(",");
				}
				sb.append(esiSentCatList.get(i).getChartStr());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 生成热门政策动态
	 * 
	 * @return
	 */
	private String createPolicyApprList(List<EaiInfoMo> EaiInfoList)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\"HotPolicyDynamic\":[");
		if (EaiInfoList.size() > 0)
		{
			for (int i = 0; i < EaiInfoList.size(); i++)
			{
				if (i > 0)
				{
					sb.append(",");
				}
				sb.append(EaiInfoList.get(i).getTableStr());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 生成热门政策动态
	 * 
	 * @return
	 */
	private String createSentBrandList(List<EsiHotBrandMO> EsiHotBrandList)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\"HotBrandDynamic\":[");
		if (EsiHotBrandList.size() > 0)
		{
			for (int i = 0; i < EsiHotBrandList.size(); i++)
			{
				if (i > 0)
				{
					sb.append(",");
				}
				sb.append(EsiHotBrandList.get(i).getTableStr());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 生成热门政策动态
	 * 
	 * @return
	 */
	private String createSentCatList(List<EsiHotCatMO> EsiHotCatList)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\"HotCategoryDynamic\":[");
		if (EsiHotCatList.size() > 0)
		{
			for (int i = 0; i < EsiHotCatList.size(); i++)
			{
				if (i > 0)
				{
					sb.append(",");
				}
				sb.append(EsiHotCatList.get(i).getTableStr());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 生成热门政策动态
	 * 
	 * @return
	 */
	private String createLabelCloud(List<EsiInduLabel30dMo> EsiInduLabel30dList)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\"InduTopicLable\":[");
		if (EsiInduLabel30dList.size() > 0)
		{
			for (int i = 0; i < EsiInduLabel30dList.size(); i++)
			{
				if (i > 0)
				{
					sb.append(",");
				}
				sb.append(EsiInduLabel30dList.get(i).getTableStr());
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
