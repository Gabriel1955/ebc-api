package com.tunyun.product.ebc.bgmanager.dao;

import com.common.system.jdbc.jpa.JPABaseDAO;

import java.util.List;
import java.util.Map;

public class TriCateDaoImpl extends JPABaseDAO implements TriCategDao {

	private final String NS = TriCateDaoImpl.class.getName();

	@Override
	public List<Map> queryTriCategs(Map param) {
		if(!param.containsKey("p_id")){
			param.put("p_id", 0);
		}
		List<Map> list = this.getSqlSessionTemplate().selectList(NS+".queryTriCategs", param);
		for(Map m:list){
			param.put("p_id", m.get("uid"));
			m.put("children", queryTriCategs(param));
		}
		return list;
	}


	public Map queryTriCateg(Map param) {
		return this.getSqlSessionTemplate().selectOne(NS+".queryTriCateg",param);
	}
}
