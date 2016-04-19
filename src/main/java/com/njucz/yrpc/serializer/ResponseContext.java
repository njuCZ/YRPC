package com.njucz.yrpc.serializer;

import java.io.Serializable;

public class ResponseContext implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8252051695816077280L;
	
	private String requestId;
	private Throwable error;
	private Object obj;
	
	public boolean isError(){
		return error != null;
	}
	
	public String getRequestId() {
		return requestId;
	}
	
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public Throwable getError() {
		return error;
	}
	
	public void setError(Throwable error) {
		this.error = error;
	}
	
	public Object getObj() {
		return obj;
	}
	
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
