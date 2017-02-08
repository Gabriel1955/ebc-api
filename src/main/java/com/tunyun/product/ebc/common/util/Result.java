package com.tunyun.product.ebc.common.util;
/**
 * 该java的描述信息
 * @author shiliang(66614) Tel:18661205639
 * @version 1.0
 * @since 2015-3-5 下午2:54:17
 * @category com.tunyun.product.ebc.common.util
 */
public class Result {

	private String code;
	private String detail;
	private Object value;
	
	public Result(String code,String detail,Object value){
		this.code = code;
		this.detail = detail;
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
