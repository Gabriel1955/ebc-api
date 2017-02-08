
package com.tunyun.product.ebc.web.dao;

import java.util.List;
import java.util.Map;

import com.tunyun.product.ebc.web.mo.AreaMo;
import com.tunyun.product.ebc.web.mo.MenuMo;
import com.tunyun.product.ebc.web.mo.RoleMo;

/**
 * 数据库查询
 * 
 * @author czm
 */
public interface EbcapiDAO
{

	/**
	 * 查询所有维度
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getAllDime();

	/**
	 * 修改密码
	 * 
	 * @param userAccout
	 * @param oldpasswd
	 * @param newpasswd
	 * @return
	 */
	public int updateUserPasswd(String userAccout, String oldpasswd, String newpasswd);

	/**
	 * 查询用户
	 * 
	 * @param userAccout
	 * @param pwd
	 * @return
	 */
	public Map<String, Object> getUser(String userAccout, String pwd);

	/**
	 * 查询用户角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<RoleMo> getUserRole(long userId);

	/**
	 * 查询用户数据域
	 * 
	 * @param userId
	 * @return
	 */
	public List<AreaMo> getUserArea(long userId);

	/**
	 * 查询菜单
	 * 
	 * @return
	 */
	public List<MenuMo> getMenu();

	/**
	 * 查询行业
	 * 
	 * @param indu_ids
	 * @return
	 */
	public List<Map<String, Object>> getIndustryById(String indu_ids);
}
