package com.timesoft.kaitoo.common;

public class ResponseCommon extends ResponseParameter{
	
	private Object result;
	private Boolean flag;
	private String information;
	private Object exceptoin;
	
	public ResponseCommon() {}
	
	public ResponseCommon(Boolean flag) {
		if(flag) {
			this.flag = Boolean.TRUE;
			this.information = SUCCESS;
		} else {
			this.flag = Boolean.FALSE;
			this.information = FAILED;
		}
	}
	
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public Object getExceptoin() {
		return exceptoin;
	}
	public void setExceptoin(Object exceptoin) {
		this.exceptoin = exceptoin;
	}
}
