package com.njucz.yrpc.serializer;

import java.io.Serializable;

public class RequestContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4577226717216501841L;
	
	private String className;
	private String funcName;
	private String[] parameterTypes;
	private Object[] parameter;
	
	public RequestContext(String className, String funcName,
			String[] parameterTypes, Object[] parameter) {
		super();
		this.className = className;
		this.funcName = funcName;
		this.parameterTypes = parameterTypes;
		this.parameter = parameter;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(String[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getParameter() {
		return parameter;
	}

	public void setParameter(Object[] parameter) {
		this.parameter = parameter;
	}
	
}
