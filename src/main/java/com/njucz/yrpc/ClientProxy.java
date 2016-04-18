package com.njucz.yrpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import test.Plus;

import com.njucz.yrpc.client.TcpClient;
import com.njucz.yrpc.serializer.RequestContext;

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
		RequestContext requestContext = new RequestContext(interfaceClass.getName(), method.getName(), parameters, args);
		TcpClient tcpClient = TcpClient.getInstance();
		tcpClient.sendMsg(requestContext);
//		String data = RequestContextSerializer.encode(requestContext);
//		System.out.println(data);
//		tcpClient.sendMsg(data);
		System.out.println("send obj success");
//		return method.invoke(proxy, args);
		return null;
	}
	
	public static void main(String[] args){
		Plus plus = new ClientProxy().proxy(Plus.class);
		plus.add(1, 2);
	}
}
