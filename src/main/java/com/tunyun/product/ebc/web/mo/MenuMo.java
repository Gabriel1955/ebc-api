
package com.tunyun.product.ebc.web.mo;

/**
 * 菜单表
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年2月29日
 * @category com.tunyun.product.ebc.web.mo
 */
public class MenuMo implements Cloneable
{

	private long uid;// 菜单ID
	private int state;// 状态
	private long add_time;// 增加时间
	private long upd_time;// 最近更新时间
	private long del_time;// 删除时间
	private int menu_type_id;// 菜单类型ID
	private String menu_name;// 菜单名称
	private String menu_img;// 图片
	private String menu_img2;// 图片2
	private String meun_desc;// 描述
	private String meun_url;// url
	private boolean is_leaf;// 是否叶子节点
	private long p_id;// 父ID
	private String menu_tree_id;// 菜单路径ID
	private int menu_level;// 菜单级别
	private int sort_order;// 顺序

	public long getUid()
	{
		return uid;
	}

	public void setUid(long uid)
	{
		this.uid = uid;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
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

	public int getMenu_type_id()
	{
		return menu_type_id;
	}

	public void setMenu_type_id(int menu_type_id)
	{
		this.menu_type_id = menu_type_id;
	}

	public String getMenu_name()
	{
		return menu_name;
	}

	public void setMenu_name(String menu_name)
	{
		this.menu_name = menu_name;
	}

	public String getMenu_img()
	{
		return menu_img;
	}

	public void setMenu_img(String menu_img)
	{
		this.menu_img = menu_img;
	}

	public String getMenu_img2()
	{
		return menu_img2;
	}

	public void setMenu_img2(String menu_img2)
	{
		this.menu_img2 = menu_img2;
	}

	public String getMeun_desc()
	{
		return meun_desc;
	}

	public void setMeun_desc(String meun_desc)
	{
		this.meun_desc = meun_desc;
	}

	public String getMeun_url()
	{
		return meun_url;
	}

	public void setMeun_url(String meun_url)
	{
		this.meun_url = meun_url;
	}

	public boolean isIs_leaf()
	{
		return is_leaf;
	}

	public void setIs_leaf(boolean is_leaf)
	{
		this.is_leaf = is_leaf;
	}

	public long getP_id()
	{
		return p_id;
	}

	public void setP_id(long p_id)
	{
		this.p_id = p_id;
	}

	public String getMenu_tree_id()
	{
		return menu_tree_id;
	}

	public void setMenu_tree_id(String menu_tree_id)
	{
		this.menu_tree_id = menu_tree_id;
	}

	public int getMenu_level()
	{
		return menu_level;
	}

	public void setMenu_level(int menu_level)
	{
		this.menu_level = menu_level;
	}

	public int getSort_order()
	{
		return sort_order;
	}

	public void setSort_order(int sort_order)
	{
		this.sort_order = sort_order;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
