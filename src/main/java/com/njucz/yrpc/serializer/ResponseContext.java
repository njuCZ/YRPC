package com.njucz.yrpc.serializer;

import java.io.Serializable;

public class ResponseContext implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8252051695816077280L;
	private Class<?> clazz;
	private Object obj;
	
	public ResponseContext(Class<?> clazz, Object obj) {
		super();
		this.clazz = clazz;
		this.obj = obj;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
