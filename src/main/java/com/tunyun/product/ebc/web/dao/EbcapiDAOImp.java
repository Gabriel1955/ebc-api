
package com.tunyun.product.ebc.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.common.system.utils.database.PrepareSQL;
import com.tunyun.product.ebc.web.mo.AreaMo;
import com.tunyun.product.ebc.web.mo.MenuMo;
import com.tunyun.product.ebc.web.mo.RoleMo;

/**
 * 数据库查询
 * 
 * @author czm
 */
public class EbcapiDAOImp implements EbcapiDAO
{

	// log
	// private Logger log = LoggerFactory.getLogger(EbcapiDAOImp.class);
	// jdbc
	private JdbcTemplate jdbc;

	public void setDs(DataSource ds)
	{
		jdbc = new JdbcTemplate(ds);
	}

	/**
	 * 查询所有维度
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAllDime()
	{
		return jdbc.queryForList("select uid,dime_ids,type_1 from tr_info_dime_ids");
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getUser(String userAccout, String pwd)
	{
		String sql = "select ta.uid,ta.tenant_id,ta.user_name,tb.value_1 from ta_user ta left  join ta_user_ext1 tb on ta.uid = tb.uid where user_name = ? and user_pwd = ?";
		PrepareSQL prepareSql = new PrepareSQL(sql);
		prepareSql.setString(1, userAccout);
		prepareSql.setString(2, pwd);
		try
		{
			return jdbc.queryForMap(prepareSql.getSQL());
		}
		catch (EmptyResultDataAccessException e)
		{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMo> getUserRole(long userId)
	{
		String sql = "select tb.uid,tb.tenant_id,tb.role_name,tb.role_desc,tb.permit_list,tb.forbid_list from ta_user_role ta left join ta_role tb on ta.role_id = tb.uid where ta.user_id = ?";
		PrepareSQL prepareSql = new PrepareSQL(sql);
		prepareSql.setLong(1, userId);
		List<RoleMo> roleList = jdbc.query(prepareSql.getSQL(), new RowMapper()
		{

			@Override
			public RoleMo mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				RoleMo rm = new RoleMo();
				rm.setUid(rs.getLong("uid"));
				rm.setTenant_id(rs.getLong("tenant_id"));
				rm.setRole_name(rs.getString("role_name"));
				rm.setRole_desc("role_desc");
				rm.setPermit_list(rs.getString("permit_list"));
				rm.setForbid_list(rs.getString("forbid_list"));
				return rm;
			}
		});
		return roleList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AreaMo> getUserArea(long userId)
	{
		String sql = "select tb.uid,tb.tenant_id,tb.area_name,tb.area_desc,tb.permit_list,tb.forbid_list from ta_user_area ta left join ta_area tb on ta.area_id = tb.uid where ta.user_id = ? ";
		PrepareSQL prepareSql = new PrepareSQL(sql);
		prepareSql.setLong(1, userId);
		List<AreaMo> areaList = jdbc.query(prepareSql.getSQL(), new RowMapper()
		{

			@Override
			public AreaMo mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				AreaMo am = new AreaMo();
				am.setUid(rs.getLong("uid"));
				am.setTenant_id(rs.getLong("tenant_id"));
				am.setArea_name(rs.getString("area_name"));
				am.setArea_desc(rs.getString("area_desc"));
				am.setPermit_list(rs.getString("permit_list"));
				am.setForbid_list(rs.getString("forbid_list"));
				return am;
			}
		});
		return areaList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuMo> getMenu()
	{
		String sql = "select uid,state,menu_type_id,menu_name,menu_img,menu_img2,is_leaf,p_id,menu_tree_id,menu_level,sort_order from ta_menu";
		List<MenuMo> menuList = jdbc.query(sql, new RowMapper()
		{

			@Override
			public MenuMo mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				MenuMo menu = new MenuMo();
				menu.setUid(rs.getLong("uid"));
				menu.setState(rs.getInt("state"));
				menu.setMenu_type_id(rs.getInt("menu_type_id"));
				menu.setMenu_name(rs.getString("menu_name"));
				menu.setMenu_img(rs.getString("menu_img"));
				menu.setMenu_img2(rs.getString("menu_img2"));
				menu.setIs_leaf(rs.getBoolean("is_leaf"));
				menu.setP_id(rs.getLong("p_id"));
				menu.setMenu_tree_id(rs.getString("menu_tree_id"));
				menu.setMenu_level(rs.getInt("menu_level"));
				menu.setSort_order(rs.getInt("sort_order"));
				return menu;
			}
		});
		return menuList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getIndustryById(String indu_ids)
	{
		String sql = "select uid,name,indu_desc from tr_indu where uid in(?)";
		PrepareSQL prepareSql = new PrepareSQL(sql);
		prepareSql.setStringExt(1, indu_ids, false);
		return jdbc.queryForList(prepareSql.getSQL());
	}

	@Override
	public int updateUserPasswd(String userAccout, String oldpasswd, String newpasswd)
	{
		String sql = "update ta_user set user_pwd = ? where user_name = ? and user_pwd = ?";
		PrepareSQL prepareSql = new PrepareSQL(sql);
		prepareSql.setString(1, newpasswd);
		prepareSql.setString(2, userAccout);
		prepareSql.setString(3, oldpasswd);
		return jdbc.update(prepareSql.getSQL());
	}
}
