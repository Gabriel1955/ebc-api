
package com.tunyun.product.ebc.common.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author gaojinbao
 * @version 1.0
 * @since 2016年4月12日
 * @category com.tunyun.product.ebc.common.dao
 */
public class CommonDaoImpl implements CommonDao
{

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource)
	{
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private static String getTriCategSql = "select uid,cat_name from tri_categ where state = 1";
	private static String geTrTenantChanSql = "select chan_id,chan_name from tr_tenant_chan where state = 1";

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getTriCateg()
	{
		return this.jdbcTemplate.queryForList(getTriCategSql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> geTrTenantChan()
	{
		return this.jdbcTemplate.queryForList(geTrTenantChanSql);
	}
}
