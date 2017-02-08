
package com.tunyun.product.ebc.web.mo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 菜单管理
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年2月29日
 * @category com.tunyun.product.ebc.web.mo
 */
public class MenuMgr
{

	public static Logger logger = LoggerFactory.getLogger(MenuMgr.class);
	// 存放菜单列表
	public static List<MenuMo> MenuList;
	public static Map<String, MenuMo> MenuMap;

	/**
	 * 转换成HashMap存贮
	 * 
	 * @param mlst
	 */
	public static Map<String, MenuMo> convertMap(List<MenuMo> mlst)
	{
		Map<String, MenuMo> MenuMap = new HashMap<String, MenuMo>();
		for (MenuMo mm : mlst)
		{
			MenuMap.put(String.valueOf(mm.getUid()), mm);
		}
		return MenuMap;
	}

	/**
	 * 判断是否是叶子节点
	 * 
	 * @param menuId
	 * @return
	 */
	private static int isLeaf(String menuId)
	{
		MenuMo menu = MenuMap.get(menuId);
		if (menu.isIs_leaf())
		{
			return 1;// 叶子节点
		}
		else if (menu.getP_id() == -1)
		{
			return -1;// 根节点
		}
		return 0;// 中间节点
	}

	/**
	 * 查找子节点
	 * 
	 * @param menuId
	 * @return
	 */
	private static Set<String> findChildNode(String menuId)
	{
		Set<String> childMenuLst = new HashSet<String>();
		for (MenuMo mm : MenuList)
		{
			if (mm.getP_id() == Long.parseLong(menuId))
			{
				childMenuLst.add(String.valueOf(mm.getUid()));
			}
		}
		return childMenuLst;
	}

	/**
	 * 查找父节点
	 * 
	 * @param menuId
	 * @return
	 */
	private static Set<String> findParentNode(String menuId)
	{
		Set<String> parentMenuLst = new HashSet<String>();
		MenuMo mm = MenuMap.get(menuId);
		while (null != mm && mm.getP_id() != -1)
		{
			parentMenuLst.add(String.valueOf(mm.getP_id()));
			mm = MenuMap.get(String.valueOf(mm.getP_id()));
		}
		return parentMenuLst;
	}

	/**
	 * 查找允许列表菜单id
	 * 
	 * @param menuIdLst
	 * @return
	 */
	private static Set<String> findPermitListMenuId(Set<String> menuIdLst)
	{
		Set<String> findMenuIds = new HashSet<String>();
		for (String menuId : menuIdLst)
		{
			int flag = MenuMgr.isLeaf(menuId);
			switch (flag)
			{
				case 1:
				{
					// 叶子节点，查找父节点
					findMenuIds.addAll(MenuMgr.findParentNode(menuId));
					break;
				}
				case -1:
				{
					// 根节点，查找子节点
					findMenuIds.addAll(MenuMgr.findChildNode(menuId));
					break;
				}
				case 0:
				{
					// 中间节点，首先查找子节点，然后查找父节点
					findMenuIds.addAll(MenuMgr.findParentNode(menuId));
					findMenuIds.addAll(MenuMgr.findChildNode(menuId));
					break;
				}
			}
		}
		return findMenuIds;
	}

	/**
	 * 查找禁止列表菜单id
	 * 
	 * @param menuIdLst
	 * @return
	 */
	private static Set<String> findForbidListMenuId(Set<String> menuIdLst)
	{
		Set<String> findMenuIds = new HashSet<String>();
		for (String menuId : menuIdLst)
		{
			int flag = MenuMgr.isLeaf(menuId);
			if (flag != 1)
			{
				// 查找子节点
				findMenuIds.addAll(MenuMgr.findChildNode(menuId));
			}
		}
		return findMenuIds;
	}

	/**
	 * 计算显示菜单
	 * 
	 * @param permitList
	 * @param forbidList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<MenuMo> calculateMenu(Set<String> permitList,
			Set<String> forbidList)
	{
		MenuMo menu = null;
		List<MenuMo> menus = new ArrayList<MenuMo>();
		permitList.addAll(findPermitListMenuId(permitList));
		forbidList.addAll(findForbidListMenuId(forbidList));
		// 计算允许列表和禁止列表差集
		Collection<String> collections = CollectionUtils.subtract(permitList, forbidList);
		for (Iterator it = collections.iterator(); it.hasNext();)
		{
			menu = MenuMap.get(it.next());
			if (null != menu)
			{
				menus.add(menu);
			}
		}
		return menus;
	}

	/**
	 * 返回树形菜单结构
	 * 
	 * @param nodes
	 * @return
	 */
	public static List<Map<String, Object>> generateMenuJson(List<Node> nodes)
	{
		List<Map<String, Object>> mapJsonList = new ArrayList<Map<String, Object>>();
		for (Node node : nodes)
		{
			Map<String, Object> menuMap = new HashMap<String, Object>();
			MenuMo menu = MenuMap.get(String.valueOf(node.getId()));
			menuMap.put("itemId", menu.getUid());
			menuMap.put("itemIcon", menu.getMenu_img());
			menuMap.put("itemFocusIcon", menu.getMenu_img2());
			menuMap.put("itemTitle", menu.getMenu_name());
			if (node.getCNode().size() > 0)
			{
				List<Map<String, Object>> itemMenuList = new ArrayList<Map<String, Object>>();
				for (Node itemNode : node.getCNode())
				{
					Map<String, Object> itemMenuMap = new HashMap<String, Object>();
					MenuMo itemMenu = MenuMap.get(String.valueOf(itemNode.getId()));
					itemMenuMap.put("itemId", itemMenu.getUid());
					itemMenuMap.put("itemTitle", itemMenu.getMenu_name());
					itemMenuList.add(itemMenuMap);
				}
				menuMap.put("itemMenu", itemMenuList);
			}
			mapJsonList.add(menuMap);
		}
		return mapJsonList;
	}

	/**
	 * 生成Node节点数
	 * 
	 * @param menuLst
	 */
	public static List<Node> MenuNode(List<MenuMo> menuLst)
	{
		List<Node> nodeList = new ArrayList<Node>();
		for (MenuMo m : menuLst)
		{
			if (m.getP_id() == -1)
			{
				Node node = new Node();
				node.setId(m.getUid());
				node.setSortOrder(m.getSort_order());
				node = findNode(node, menuLst);
				if (node.cNodeSize() > 1)
				{
					Collections.sort(node.getCNode());
				}
				nodeList.add(node);
			}
		}
		Collections.sort(nodeList);
		return nodeList;
	}

	/**
	 * 递归查找菜单子节点
	 * 
	 * @param pid
	 * @param menuLst
	 */
	private static Node findNode(Node node, List<MenuMo> menuLst)
	{
		for (MenuMo m : menuLst)
		{
			if (m.getP_id() == node.getId())
			{
				Node cnode = new Node();
				cnode.setId(m.getUid());
				cnode.setSortOrder(m.getSort_order());
				node.addCNode(cnode);
				findNode(cnode, menuLst);// 递归
			}
		}
		return node;
	}
}
