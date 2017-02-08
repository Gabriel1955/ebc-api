package com.tunyun.product.ebc.bgmanager.serv;

import com.tunyun.product.ebc.bgmanager.dao.AttrDao;
import com.tunyun.product.ebc.bgmanager.po.AliasPO;
import com.tunyun.product.ebc.bgmanager.po.AttrPO;
import com.tunyun.product.ebc.common.util.Result;
import com.tunyun.product.ebc.web.server.BaseServ;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年10月25日 下午12:31:57
 * @category com.tunyun.product.ebc.bgmanager.serv
 *
 */
public class AttrServ extends BaseServ {
	private static Logger logger = LoggerFactory.getLogger(AttrServ.class);

	private AttrDao attrDao;

	public String delAttrs(String input) {
		try {
			logger.info("delAttr params====>{}", input);
			JSONObject jsonObj = getParams(input);
			TransactionTemplate tt = new TransactionTemplate(transactionManager);
			tt.execute(new TransactionCallback<Object>() {

				@Override
				public Object doInTransaction(TransactionStatus status) {
					String params = jsonObj.getString("params");
					String[] attrvalues = params.split("##");
					List<Map> list = new ArrayList<Map>();
					Map param;
					List<Map> p_alias = new ArrayList<Map>();
					AttrPO po = null;
					for (String attrvalue : attrvalues) {
						String[] a = attrvalue.split("&&");
						param = new HashMap();
						param.put("cid", a[0]);
						param.put("pid", a[1]);
						param.put("p_name", a[2]);
						param.put("state", 0);
						param.put("startTime", jsonObj.get("startTime"));
						param.put("endTime", jsonObj.get("endTime"));
						logger.info("==={}", param);
						list.add(param);
						List<AttrPO> notApproved = attrDao.queryNotApprovedAttrs(param);
						if (notApproved.size() > 0) {
							po = notApproved.get(0);
						}
						for (AliasPO palias : po.getPalias()) {
							Map paliasMap = new HashMap();
							paliasMap.put("cid", param.get("cid"));
							paliasMap.put("pid", param.get("pid"));
							paliasMap.put("p_name", param.get("p_name"));
							paliasMap.put("state", 0);
							paliasMap.put("p_alias", palias.getName());
							paliasMap.put("addTime", new Date().getTime() / 1000L);
							p_alias.add(paliasMap);
						}
					}
					logger.info("==={}", p_alias);
					logger.info("==={}", list);
					attrDao.batchUpdateSynonym(list);
					attrDao.batchInsertPModel(p_alias);
					return null;
				}
			});
			return JSONObject.fromObject(new Result("1", "删除成功", null)).toString();
		} catch (Exception e) {
			logger.error("e===>", e);
			return JSONObject.fromObject(new Result("0", "删除失败", e.getMessage())).toString();
		}
	}

	public String mergeAttrs(String input) {
		try {
			logger.info("mergeAttrs params====>{}", input);
			JSONObject jsonObj = getParams(input);
			TransactionTemplate tt = new TransactionTemplate(transactionManager);
			tt.execute(new TransactionCallback<Object>() {

				@Override
				public Object doInTransaction(TransactionStatus status) {
					String params = jsonObj.getString("params");
					String approved = jsonObj.getString("approved");
					String pname = jsonObj.getString("pname");
					String[] attrvalues = params.split("##");
					Map param;
					// 已审核合并
					if (!"".equals(approved.trim())) {
						List<Map> list = new ArrayList<Map>();
						String[] a;
						String[] b = approved.split("&&");
						List<Map> p_alias = new ArrayList<Map>();
						AttrPO po = null;
						for (String attrvalue : attrvalues) {
							a = attrvalue.split("&&");
							param = new HashMap();
							param.put("cid", a[0]);
							param.put("pid", a[1]);
							param.put("p_name", a[2]);
							param.put("state", 1);
							param.put("startTime", jsonObj.get("startTime"));
							param.put("endTime", jsonObj.get("endTime"));
							list.add(param);
							logger.info("==={}", param);
							List<AttrPO> notApproved = attrDao.queryNotApprovedAttrs(param);
							if (notApproved.size() > 0) {
								po = notApproved.get(0);
							}
							for (AliasPO palias : po.getPalias()) {
								Map paliasMap = new HashMap();
								paliasMap.put("cid", b[0]);
								paliasMap.put("pid", b[1]);
								paliasMap.put("state", 1);
								paliasMap.put("p_alias", palias.getName());
								paliasMap.put("p_name", b[2]);
								paliasMap.put("addTime", new Date().getTime() / 1000L);
								paliasMap.put("startTime", jsonObj.get("startTime"));
								paliasMap.put("endTime", jsonObj.get("endTime"));
								p_alias.add(paliasMap);
							}
						}
						logger.info("mergeAttrs addAttr==={}", list);
						logger.info("mergeAttrs addAttr==={}", p_alias);
						attrDao.batchInsertPModel(p_alias);
						attrDao.batchUpdateSynonym(list);
					} else {// 未审核合并
						List<Map> list = new ArrayList<Map>();
						for (String attrvalue : attrvalues) {
							param = new HashMap();
							String[] a = attrvalue.split("&&");
							param.put("cid", a[0]);
							param.put("pid", a[1]);
							param.put("p_name", a[2]);
							param.put("newpname", pname);
							param.put("startTime", jsonObj.get("startTime"));
							param.put("endTime", jsonObj.get("endTime"));
							logger.info("mergeAttrp==={}", param);
							list.add(param);
						}
						attrDao.batchUpdateSynonym(list);
					}

					return null;
				}
			});
			Map result = new HashMap();
			result.put("approvedAttr", attrDao.queryApprovedAttrs((Map) jsonObj));
			result.put("notApprovedAttr", attrDao.queryNotApprovedAttrs((Map) jsonObj));
			return JSONObject.fromObject(new Result("1", "合并成功", result)).toString();
		} catch (Exception e) {
			logger.error("e===>", e.getMessage());
			return JSONObject.fromObject(new Result("0", "合并失败", e.getMessage())).toString();
		}
	}

	public String splitAttrs(String input) {
		try {
			logger.info("splitAttrs params====>{}", input);
			JSONObject jsonObj = getParams(input);
			TransactionTemplate tt = new TransactionTemplate(transactionManager);
			tt.execute(new TransactionCallback<Object>() {

				@Override
				public Object doInTransaction(TransactionStatus status) {
					String params = jsonObj.getString("params");
					String[] attrvalues = params.split("##");
					List<Map> list = new ArrayList<Map>();
					Map param;
					for (String attrvalue : attrvalues) {
						param = new HashMap();
						String[] a = attrvalue.split("&&");
						param.put("cid", a[0]);
						param.put("pid", a[1]);
						param.put("p_alias", a[2]);
						param.put("newpname", a[2]);
						param.put("startTime", jsonObj.get("startTime"));
						param.put("endTime", jsonObj.get("endTime"));
						logger.info("splitAttrs==={}", param);
						list.add(param);
					}
					attrDao.batchUpdateSynonym(list);
					return null;
				}
			});
			return JSONObject.fromObject(new Result("1", "拆分成功", attrDao.queryNotApprovedAttrs((Map) jsonObj)))
					.toString();
		} catch (Exception e) {
			logger.error("e===>", e.getMessage());
			return JSONObject.fromObject(new Result("0", "拆分失败", e.getMessage())).toString();
		}
	}

	public String addAttrs(String input) {
		try {
			logger.info("addAttrs params====>{}", input);
			JSONObject jsonObj = getParams(input);
			String params = jsonObj.getString("params");
			String[] attrvalues = params.split("##");
			TransactionTemplate tt = new TransactionTemplate(transactionManager);
			tt.execute(new TransactionCallback<Object>() {
				@Override
				public Object doInTransaction(TransactionStatus status) {

					List<Map> list = new ArrayList<Map>();
					List<Map> p_alias = new ArrayList<Map>();
					long maxuid = attrDao.queryMaxUid(jsonObj);
					logger.info("addAttrValue maxvid====>{}", maxuid);
					for (String attrvalue : attrvalues) {
						String[] a = attrvalue.split("&&");
						String[] palias = a[3].split(",");
						Map param = new HashMap();
						param.put("cid", a[0]);
						param.put("state", 1);
						param.put("pid", ++maxuid);
						param.put("p_alias", a[2]);
						param.put("p_name", a[2]);
						param.put("addTime", new Date().getTime() / 1000L);
						param.put("startTime", jsonObj.get("startTime"));
						param.put("endTime", jsonObj.get("endTime"));
						list.add(param);
						for (String v : palias) {
							Map paliasparam = new HashMap();
							paliasparam.put("cid", a[0]);
							paliasparam.put("state", 1);
							paliasparam.put("pid", maxuid);
							paliasparam.put("p_alias", v);
							paliasparam.put("p_name", a[2]);
							paliasparam.put("addTime", new Date().getTime() / 1000L);
							paliasparam.put("startTime", jsonObj.get("startTime"));
							paliasparam.put("endTime", jsonObj.get("endTime"));
							p_alias.add(paliasparam);
						}
					}
					logger.info("addAttr==={}", list);
					// 属性值表
					attrDao.batchInsertP(list);
					// 品类与属性值关联表
					List<Map> cpvlist = new ArrayList<Map>();
					Map e = new HashMap();
					e.put("cid", jsonObj.getString("cid"));
					e.put("pid", maxuid);
					cpvlist.add(e);
					attrDao.batchInsertCategP(cpvlist);
					attrDao.batchInsertPModel(p_alias);
					attrDao.batchUpdateSynonym(p_alias);
					return null;
				}
			});
			return JSONObject.fromObject(new Result("1", "审核成功", attrDao.queryApprovedAttrs((Map) jsonObj))).toString();
		} catch (Exception e) {
			logger.error("e===>", e.getMessage());
			return JSONObject.fromObject(new Result("0", "审核失败", e.getMessage())).toString();
		}
	}

	public String queryAttrsDetails(String input) {
		// JSON转换
		try {
			JSONObject jsonObj = getParams(input);
			logger.info("params====>{}", jsonObj);
			List<Map> list = attrDao.queryAttrDetails((Map) jsonObj);
			logger.info("queryAttrsDetails list.size====>{}", list.size());
			return JSONObject.fromObject(new Result("1", "查询详情成功", list)).toString();
		} catch (NumberFormatException e) {
			logger.error("queryAttrsDetails error:{}", e);
			return JSONObject.fromObject(new Result("0", "查询详情失败", null)).toString();
		}
	}

	public String queryApproveAttrs(String input) {
		try {
			JSONObject jsonObj = getParams(input);
			logger.info("params====>{}", jsonObj);
			List<AttrPO> approvedList = attrDao.queryApprovedAttrs((Map) jsonObj);
			List<AttrPO> notApprovedList = attrDao.queryNotApprovedAttrs((Map) jsonObj);
			logger.info("approvedList.size====>{}", approvedList.size());
			logger.info("notApprovedList.size====>{}", notApprovedList.size());
			Map result = new HashMap();
			result.put("approvedAttr", approvedList);
			result.put("notApprovedAttr", notApprovedList);
			return JSONObject.fromObject(new Result("1", "查询成功", result)).toString();
		} catch (Exception e) {
			logger.error("queryApproveAttrs error:{}", e);
			return JSONObject.fromObject(new Result("0", "查询失败", null)).toString();
		}
	}

	public AttrDao getAttrDao() {
		return attrDao;
	}

	public void setAttrDao(AttrDao attrDao) {
		this.attrDao = attrDao;
	}


}
