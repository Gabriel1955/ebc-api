
package com.tunyun.product.ebc.web.server.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.tunyun.product.ebc.web.common.CommonUtil;
import com.tunyun.product.ebc.web.dao.EbcapiDAO;
import com.tunyun.product.ebc.web.mo.AreaMo;
import com.tunyun.product.ebc.web.mo.MenuMgr;
import com.tunyun.product.ebc.web.mo.MenuMo;
import com.tunyun.product.ebc.web.mo.Node;
import com.tunyun.product.ebc.web.mo.RoleMo;

import net.sf.json.JSONObject;

/**
 * 系统操作
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年2月26日
 * @category com.tunyun.product.ebc.web.server.system
 */
public class SystemServ implements InitializingBean
{

	Logger logger = LoggerFactory.getLogger(SystemServ.class);
	// DB
	private EbcapiDAO ebcDao;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		// 初始化菜单管理类
		MenuMgr.MenuList = ebcDao.getMenu();
		MenuMgr.MenuMap = MenuMgr.convertMap(MenuMgr.MenuList);
		// 测试
		// String userJson = "{\"user_account\":\"gjb\",\"pwd\":\"ebc!^*\"}";
		// String userJson2 = "{\"uid\":100002}";
		// LoginAuthentication(userJson);
		// accessPermission(userJson2);
	}

	/**
	 * 用户密码修改
	 * 
	 * @param param:{'user_account':'gaojinbao';'pwd':'pwd'}
	 * @return
	 */
	public String changePasswd(String param)
	{
		JSONObject _jsonObject = JSONObject.fromObject(param);
		String userAccout = CommonUtil.getJsonParamForString(_jsonObject, "user_account");// 账户
		String oldpasswd = CommonUtil.getJsonParamForString(_jsonObject, "oldpasswd");// 旧密码
		String newpasswd = CommonUtil.getJsonParamForString(_jsonObject, "newpasswd");// 新密码
		int result = ebcDao.updateUserPasswd(userAccout, oldpasswd, newpasswd);
		return result >= 1 ? "{\"StatusCode\":200}" : "{\"StatusCode\":404}";
	}

	/**
	 * 登录验证
	 * 
	 * @param param:{'user_account':'gaojinbao';'pwd':'pwd'}
	 * @return
	 */
	public String loginAuthentication(String param)
	{
		logger.info("======================");
		JSONObject _jsonObject = JSONObject.fromObject(param);
		String userAccout = CommonUtil.getJsonParamForString(_jsonObject, "user_account");// 账户
		String pwd = CommonUtil.getJsonParamForString(_jsonObject, "pwd");// 密码
		Map<String, Object> userMap = ebcDao.getUser(userAccout, pwd);
		logger.info("###############################");
		if (null == userMap)
		{
			// 404：表示用户不存在或者用户名密码错误
			return "{\"StatusCode\":404}";
		}
		// 200：表示验证成功
		userMap.put("StatusCode", 200);
		String jsonStr = JSONObject.fromObject(userMap).toString();
		logger.info("LoginAuthentication:{}", jsonStr);
		return jsonStr;
	}

	/**
	 * 获取用户行业数据域
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<Map<String, Object>> getUserArea(long userId)
	{
		List<Map<String, Object>> areaJson = new ArrayList<Map<String, Object>>();
		List<AreaMo> areaList = ebcDao.getUserArea(userId);
		if (null == areaList || areaList.size() == 0)
		{
			logger.error("user id:{} not found area......", userId);
			return areaJson;
		}
		Set<String> permitList = new HashSet<String>();// 允许列表
		Set<String> forbidList = new HashSet<String>();// 禁止列表
		for (AreaMo am : areaList)
		{
			if (CollectionUtils.isNotEmpty(am.getPermitList()))
			{
				permitList.addAll(am.getPermitList());// 取允许列表并集
			}
			if (CollectionUtils.isNotEmpty(am.getForbidList()))
			{
				forbidList.addAll(am.getForbidList());// 取禁止列表并集
			}
		}
		permitList.removeAll(forbidList);// 计算差集
		if (CollectionUtils.isNotEmpty(permitList))
		{
			StringBuffer induIds = new StringBuffer();
			int index = 0;
			for (Iterator it = permitList.iterator(); it.hasNext();)
			{
				if (index++ > 0)
				{
					induIds.append(",");
				}
				induIds.append(it.next());
			}
			List<Map<String, Object>> industryInfoList = ebcDao
					.getIndustryById(induIds.toString());
			if (CollectionUtils.isNotEmpty(industryInfoList))
			{
				for (Map<String, Object> tmpMap : industryInfoList)
				{
					Map<String, Object> InduInfoMap = new HashMap<String, Object>();
					InduInfoMap.put("induId", tmpMap.get("uid"));
					InduInfoMap.put("induName", tmpMap.get("indu_desc"));
					areaJson.add(InduInfoMap);
				}
			}
		}
		return areaJson;
	}

	/**
	 * 获取用户菜单
	 * 
	 * @param userId
	 * @return
	 */
	private List<Map<String, Object>> getUserMenu(long userId)
	{
		List<RoleMo> roleList = ebcDao.getUserRole(userId);
		if (null == roleList || roleList.size() == 0)
		{
			logger.error("user id:{} not found role......", userId);
			return new ArrayList<Map<String, Object>>();
		}
		Set<String> permitList = new HashSet<String>();// 允许列表
		Set<String> forbidList = new HashSet<String>();// 禁止列表
		for (RoleMo rm : roleList)
		{
			if (CollectionUtils.isNotEmpty(rm.getPermitList()))
			{
				permitList.addAll(rm.getPermitList());// 取允许列表并集
			}
			if (CollectionUtils.isNotEmpty(rm.getForbidList()))
			{
				forbidList.addAll(rm.getForbidList());// 取禁止列表并集
			}
		}
		logger.debug("====允许列表并集====");
		for (String s : permitList)
		{
			logger.debug("{} ", s);
		}
		logger.debug("====禁止列表并集====");
		for (String s : forbidList)
		{
			logger.debug("{} ", s);
		}
		// 禁止和允许列表间取差集
		forbidList.removeAll(permitList);
		logger.debug("====禁止列表差集====");
		for (String s : forbidList)
		{
			logger.debug("{} ", s);
		}
		// 计算显示菜单
		List<MenuMo> menus = MenuMgr.calculateMenu(permitList, forbidList);
		logger.debug("====计算显示菜单====");
		for (MenuMo m : menus)
		{
			logger.debug("{}", m.getUid());
		}
		// 生成菜单树
		List<Node> nodes = MenuMgr.MenuNode(menus);
		logger.debug("====生成菜单树====");
		for (Node n : nodes)
		{
			logger.debug("{}->{} ", n.getId(), n.getCNode().size());
		}
		// 返回菜单json
		return MenuMgr.generateMenuJson(nodes);
	}

	/**
	 * 获取用户访问权限
	 * 
	 * @param param
	 * @return
	 */
	public String accessPermission(String param)
	{
		Map<String, List<Map<String, Object>>> apMapJson = new HashMap<String, List<Map<String, Object>>>();
		JSONObject _jsonObject = JSONObject.fromObject(param);
		long userId = CommonUtil.getJsonParam(_jsonObject, "uid");// 用户ID
		// 返回菜单json
		List<Map<String, Object>> menuJson = getUserMenu(userId);
		List<Map<String, Object>> areaJson = getUserArea(userId);
		apMapJson.put("induInfo", areaJson);
		apMapJson.put("menuInfo", menuJson);
		String jsonStr = JSONObject.fromObject(apMapJson).toString();
		return jsonStr;
	}

	public void setEbcDao(EbcapiDAO ebcDao)
	{
		this.ebcDao = ebcDao;
	}
}
