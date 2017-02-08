package com.tunyun.product.ebc.bgmanager.dao;

import java.util.List;
import java.util.Map;

public interface TriCategDao {

	public List<Map> queryTriCategs(Map param);

	public Map queryTriCateg(Map param);
}
