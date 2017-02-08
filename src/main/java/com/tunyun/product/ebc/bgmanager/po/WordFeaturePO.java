package com.tunyun.product.ebc.bgmanager.po;

import java.util.List;

/**
 * 
 * @author shiliang
 * @version 1.0
 * @since 2016年11月28日 下午4:14:39
 * @category com.tunyun.product.ebc.bgmanager.po
 *
 */
public class WordFeaturePO {

	private long n_id;
	private String word;
	private double weight;
	private long cid;
	private List<WordAdjectivePO> wordAdjectives;

	public long getN_id() {
		return n_id;
	}

	public void setN_id(long n_id) {
		this.n_id = n_id;
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


	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public List<WordAdjectivePO> getWordAdjectives() {
		return wordAdjectives;
	}

	public void setWordAdjectives(List<WordAdjectivePO> wordAdjectives) {
		this.wordAdjectives = wordAdjectives;
	}

	
	
	
	
}
