package com.tunyun.product.ebc.web.es.mo;

import java.text.DecimalFormat;

/**
 * 政策舆情信息
 * 
 * @author czm
 *
 */
public class EsiPolicyApprMO {

	// 行业ID
	private Long indu_id;

	// 政策分类
	private String sent_name;

	// 统计时间
	private long stat_time;

	// 关注度
	private float atte_degree;

	// 褒贬度
	private float appr_degree;

	// 正面
	private long g_eval_num;

	// 中性
	private long m_eval_num;

	// 负面
	private long b_eval_num;

	// 百分比
	private float percent;

	// 总数
	private float total;

	// 序号
	private int index;

	public Long getIndu_id() {
		return indu_id;
	}

	public void setIndu_id(Long indu_id) {
		this.indu_id = indu_id;
	}

	public String getSent_name() {
		return sent_name;
	}

	public void setSent_name(String sent_name) {
		this.sent_name = sent_name;
	}

	public long getStat_time() {
		return stat_time;
	}

	public void setStat_time(long stat_time) {
		this.stat_time = stat_time;
	}

	public float getAtte_degree() {
		return atte_degree;
	}

	public void setAtte_degree(float atte_degree) {
		this.atte_degree = atte_degree;
	}

	public float getAppr_degree() {
		return appr_degree;
	}

	public void setAppr_degree(float appr_degree) {
		this.appr_degree = appr_degree;
	}

	public long getG_eval_num() {
		return g_eval_num;
	}

	public void setG_eval_num(long g_eval_num) {
		this.g_eval_num = g_eval_num;
	}

	public long getM_eval_num() {
		return m_eval_num;
	}

	public void setM_eval_num(long m_eval_num) {
		this.m_eval_num = m_eval_num;
	}

	public long getB_eval_num() {
		return b_eval_num;
	}

	public void setB_eval_num(long b_eval_num) {
		this.b_eval_num = b_eval_num;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;

		// 计算百分比
		DecimalFormat df = new DecimalFormat(".##");
		this.percent = Float.parseFloat(df.format(atte_degree * 100 / total));
	}

	/**
	 * 获取表格信息
	 * 
	 * @return
	 */
	public String getTableStr() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(indu_id).append(",");
		sb.append(index).append(",");
		sb.append("\"").append(sent_name).append("\",");
		sb.append("\"").append(atte_degree).append("\",");
		sb.append("\"").append(g_eval_num).append("\",");
		sb.append("\"").append(m_eval_num).append("\",");
		sb.append("\"").append(b_eval_num).append("\"");
		sb.append("]");

		return sb.toString();
	}

	/**
	 * 获取图表信息
	 * 
	 * @return
	 */
	public String getChartStr() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("\"").append(sent_name).append("\",");
		sb.append(percent);
		sb.append("]");

		return sb.toString();
	}
}
