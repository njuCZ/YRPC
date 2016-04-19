package com.njucz.yrpc.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemProperties {
	
	private static Properties properties = new Properties();
	
	static{
		try{
			InputStream in = SystemProperties.class.getResourceAsStream("/config.properties");
			properties.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getIpAddress(){
		return properties.getProperty("tcp.ip");
	}
	
	public static int getPort(){
		String port = properties.getProperty("tcp.port");
		return Integer.parseInt(port);
	}
	
	public static String getProperties(String key){
		return properties.getProperty(key);
	}
}