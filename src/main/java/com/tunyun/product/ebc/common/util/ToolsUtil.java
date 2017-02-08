package com.tunyun.product.ebc.common.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MD5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tunyun.product.ebc.bgmanager.serv.ProductServ;

public class ToolsUtil {
	private static Logger logger = LoggerFactory.getLogger(ProductServ.class);
	public static String getValue(Result rs,String family, String key) {
		return Bytes.toString(rs.getValue(Bytes.toBytes(family), Bytes.toBytes(key)));
	}
	public static Map<String, String> getValue(Result rs, String family) {
		Map<String, String> map = new HashMap<String, String>();
		try{
			NavigableMap<byte[], byte[]> navigableMap = rs.getFamilyMap(family.getBytes());
			if (null != navigableMap.entrySet()) {
				for (Entry<byte[], byte[]> entry : navigableMap.entrySet()) {
					map.put(Bytes.toString(entry.getKey()),Bytes.toString(entry.getValue()));
				}
			}
		}catch(Exception e){
			logger.error("getValue:{}", e);
		}
		logger.info("getValue:{}", map);
		return map;
	}
	
	/**
	 * 使用 Map按key进行排序
	 * @param map
	 * @return
	 */
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, String> sortMap = new TreeMap<String, String>(
				new MapKeyComparator());
		sortMap.putAll(map);
		return sortMap;
	}

	/**
	 * md5hash算法
	 *
	 * @param key
	 * @return
	 */
	public static String md5Hash(String key) {
		byte[] b = Bytes.add(MD5Hash.getMD5AsHex(Bytes.toBytes(key)).substring(0, 8).getBytes(), Bytes.toBytes(key));
		return new String(b);
	}

	public static void main(String[] args) {
		String rowKey = md5Hash("3#10430871993");
		System.out.println("rowKey:"+rowKey);

	}
}
class MapKeyComparator implements Comparator<String>{
	@Override
	public int compare(String str1, String str2) {
		return Integer.parseInt(str1) - Integer.parseInt(str2);
	}
}