package com.njucz.yrpc.server;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import com.njucz.yrpc.SystemProperties;
import com.njucz.yrpc.serializer.RequestContext;
import com.njucz.yrpc.serializer.RequestContextSerializer;

public class TcpServer {
	
	private ServerSocket server;
	private static TcpServer tcpServer;
	
	private static HashMap<String,Object> objects =new HashMap<String,Object>();
	
	static{
		String objClassList = SystemProperties.getProperties("server.objects");
		String[] list = objClassList.split(",");
		for( String objClass : list){
			Object obj;
			try {
				obj = Class.forName(objClass).newInstance();
				Class[] interfaces= obj.getClass().getInterfaces();
				for(int i =0;i<interfaces.length;i++){
					objects.put(interfaces[i].getName(), obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized static TcpServer getInstance() throws Exception{
		if(tcpServer == null){
			tcpServer = new TcpServer();
		}
		return tcpServer;
	}
	
	
	private TcpServer() throws Exception{
		try {
			server = new ServerSocket(SystemProperties.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true)
		{
			Socket socket = server.accept();
			InputStream inputStream = socket.getInputStream();
			RequestContext requestContext = RequestContextSerializer.decode(inputStream);
			
			Object obj = objects.get(requestContext.getClassName());
			
			Class clazz = obj.getClass();
			
			String[] parameterTypeStrs = requestContext.getParameterTypes();
			Class<?>[] parameterTypes = new Class<?>[parameterTypeStrs.length];
			for(int i = 0 ; i < parameterTypeStrs.length; i ++){
				parameterTypes[i] = getClass(parameterTypeStrs[i]);
			}
			Method func = clazz.getMethod(requestContext.getFuncName(), parameterTypes);
			Object result= func.invoke(obj, requestContext.getParameter());
			
			System.out.println(result);
		}
	}
	
	public static void main(String[] args){
		try {
			TcpServer.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Class<?> getClass(String str) throws ClassNotFoundException{
		if(str.equals("int")){
			return int.class;
		}
		return Class.forName(str);
		
	}
}
