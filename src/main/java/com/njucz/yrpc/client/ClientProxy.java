package com.njucz.yrpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import test.Divide;
import test.Plus;

import com.njucz.yrpc.serializer.RequestContext;
import com.njucz.yrpc.serializer.ResponseContext;

public class ClientProxy implements InvocationHandler{
	
	Class<?> interfaceClass;
	
	public <T> T proxy(Class<T> interfaceClass){
		this.interfaceClass = interfaceClass;
		return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, this);
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Class<?>[] clazzs = method.getParameterTypes();
		String[] parameters = new String[clazzs.length];
		for(int i = 0 ; i < clazzs.length ; i ++){
			parameters[i] = clazzs[i].getName();
		}
		RequestContext requestContext = new RequestContext();
		requestContext.setRequestId(UUID.randomUUID().toString());
		requestContext.setClassName(interfaceClass.getName());
		requestContext.setFuncName(method.getName());
		requestContext.setParameterTypes(parameters);
		requestContext.setParameter(args);

		TcpClient tcpClient = TcpClient.getInstance();
		tcpClient.sendMsg(requestContext);
		System.out.println("send obj success");
		ResponseContext responseContext = tcpClient.readInputStream();
		if(responseContext.isError()){
			throw responseContext.getError();
		}
		return responseContext.getObj();
	}
	
	public static void main(String[] args){
		Plus plus = new ClientProxy().proxy(Plus.class);
		int result = plus.add(1, 2);
		System.out.println("receive result:"+result);
		
		Divide divide = new ClientProxy().proxy(Divide.class);
//		int result = 0;
		try {
			result = divide.divide(90, 8);
			System.out.println("receive result:"+result);
			result = divide.divide(90, 0);
			System.out.println("receive result:"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
