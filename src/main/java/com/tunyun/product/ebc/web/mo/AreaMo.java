
package com.tunyun.product.ebc.web.mo;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

/**
 * 数据域Mo
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年3月1日
 * @category com.tunyun.product.ebc.web.mo
 */
public class AreaMo
{

	public static final String comma = ",";// 分隔符
	private long uid;// role_id
	private long state;// 状态
	private long add_time;// 增加时间
	private long upd_time;// 最近更新时间
	private long del_time;// 删除时间
	private long tenant_id;// 租户ID
	private String area_name;// 名称
	private String area_desc;// 描述
	private int type_id;// 类型
	private String permit_list;// 允许列表
	private Set<String> permitList = new HashSet<String>();// 允许列表
	private String forbid_list;// 禁止列表
	private Set<String> forbidList = new HashSet<String>();// 禁止列表

	public long getUid()
	{
		return uid;
	}

	public void setUid(long uid)
	{
		this.uid = uid;
	}

	public long getState()
	{
		return state;
	}

	public void setState(long state)
	{
		this.state = state;
	}

	public long getAdd_time()
	{
		return add_time;
	}

	public void setAdd_time(long add_time)
	{
		this.add_time = add_time;
	}

	public long getUpd_time()
	{
		return upd_time;
	}

	public void setUpd_time(long upd_time)
	{
		this.upd_time = upd_time;
	}

	public long getDel_time()
	{
		return del_time;
	}

	public void setDel_time(long del_time)
	{
		this.del_time = del_time;
	}

	public long getTenant_id()
	{
		return tenant_id;
	}

	public void setTenant_id(long tenant_id)
	{
		this.tenant_id = tenant_id;
	}

	public String getArea_name()
	{
		return area_name;
	}

	public void setArea_name(String area_name)
	{
		this.area_name = area_name;
	}

	public String getArea_desc()
	{
		return area_desc;
	}

	public void setArea_desc(String area_desc)
	{
		this.area_desc = area_desc;
	}

	public int getType_id()
	{
		return type_id;
	}

	public void setType_id(int type_id)
	{
		this.type_id = type_id;
	}

	public String getPermit_list()
	{
		return permit_list;
	}

	public void setPermit_list(String permit_list)
	{
		this.permit_list = permit_list;
		if (null != this.permit_list && !"".equals(this.permit_list))
		{
			CollectionUtils.addAll(this.permitList, this.permit_list.split(comma));
		}
	}

	public Set<String> getPermitList()
	{
		return permitList;
	}

	public void setPermitList(Set<String> permitList)
	{
		this.permitList = permitList;
	}

	public String getForbid_list()
	{
		return forbid_list;
	}

	public void setForbid_list(String forbid_list)
	{
		this.forbid_list = forbid_list;
		if (null != this.forbid_list && !"".equals(this.forbid_list))
		{
			CollectionUtils.addAll(this.forbidList, this.forbid_list.split(comma));
		}
	}

	public Set<String> getForbidList()
	{
		return forbidList;
	}

	public void setForbidList(Set<String> forbidList)
	{
		this.forbidList = forbidList;
	}
}
