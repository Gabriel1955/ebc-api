
package com.tunyun.product.ebc.web.mo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 构造菜单节点
 * 
 * @author gaojinbao
 * @version 1.0
 * @since 2016年2月29日
 * @category com.tunyun.product.ebc.web.mo
 */
public class Node implements Comparable<Node>
{

	private long id;// 节点id
	private int sortOrder;// 排序规则
	private List<Node> childNode = new ArrayList<Node>();// 子节点列表

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public List<Node> getCNode()
	{
		return this.childNode;
	}

	public void addCNode(Node node)
	{
		childNode.add(node);
	}

	public int cNodeSize()
	{
		return childNode.size();
	}

	public int getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(int sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	@Override
	public int compareTo(Node o)
	{
		if (this.getSortOrder() < o.getSortOrder())
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}

	public static void main(String[] args)
	{
		List<Node> nodes = new ArrayList<Node>();
		Node node1 = new Node();
		node1.setId(1001);
		node1.setSortOrder(1);
		Node node2 = new Node();
		node2.setId(1003);
		node2.setSortOrder(3);
		Node node3 = new Node();
		node3.setId(1004);
		node3.setSortOrder(4);
		Node node4 = new Node();
		node4.setId(1002);
		node4.setSortOrder(2);
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		nodes.add(node4);
		Collections.sort(nodes);
		for (Node n : nodes)
		{
			System.out.println(n.getId());
		}
	}
}
