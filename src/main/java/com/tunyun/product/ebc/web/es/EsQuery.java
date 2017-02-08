package com.tunyun.product.ebc.web.es;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;

public class EsQuery {

	// 参数
	private String cluster = "es-cluster";
	private String server = "cloud-1.novalocal";
	private String port = "9300";
	private Client client;

	public void init() {
		try {
			Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", cluster)
					.build();

			client = TransportClient.builder().settings(settings).build().addTransportAddress(
					new InetSocketTransportAddress(InetAddress.getByName(server), Integer.parseInt(port)));
		} catch (Exception e) {
		}
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void close() {
		client.close();
	}

	/**
	 * 查询es数据
	 * 
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param qb
	 *            查询条件
	 * @param fetchSize
	 *            获取记录数
	 * @param order
	 *            排序字段
	 * @return
	 */
	public List<String> query(String index, String type, QueryBuilder qb, int fetchSize, String order) {
		SearchResponse response = client.prepareSearch(index).setTypes(type).setQuery(qb).setSize(fetchSize)
				.setHighlighterOrder(order).execute().actionGet();

		SearchHits hits = response.getHits();
		List<String> ret = new ArrayList<String>();
		for (int i = 0; i < hits.getHits().length; i++) {
			ret.add(hits.getHits()[i].getSourceAsString());
		}

		return ret;
	}

}
