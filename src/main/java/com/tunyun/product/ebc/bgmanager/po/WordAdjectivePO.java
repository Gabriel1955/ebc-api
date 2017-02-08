package com.tunyun.product.ebc.bgmanager.po;
/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年11月28日 下午4:24:36
 * @category com.tunyun.product.ebc.bgmanager.po
 *
 */
public class WordAdjectivePO {

	private long d_id;
	private String word;
	private double weight;
	private long p_id;
	private String pname;
	private long cid;
	private int is_check;
	private int check_result;
	private long check_time;
	private String comment;
	
	
	public long getD_id() {
		return d_id;
	}
	public void setD_id(long d_id) {
		this.d_id = d_id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public long getP_id() {
		return p_id;
	}
	public void setP_id(long p_id) {
		this.p_id = p_id;
	}
	public long getCid() {
		return cid;
	}
	public void setCid(long cid) {
		this.cid = cid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getIs_check() {
		return is_check;
	}
	public void setIs_check(int is_check) {
		this.is_check = is_check;
	}
	public int getCheck_result() {
		return check_result;
	}
	public void setCheck_result(int check_result) {
		this.check_result = check_result;
	}
	public long getCheck_time() {
		return check_time;
	}
	public void setCheck_time(long check_time) {
		this.check_time = check_time;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
