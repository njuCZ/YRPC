package com.njucz.yrpc.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.njucz.yrpc.config.SystemProperties;
import com.njucz.yrpc.serializer.RequestContext;
import com.njucz.yrpc.serializer.RequestContextSerializer;
import com.njucz.yrpc.serializer.ResponseContext;
import com.njucz.yrpc.serializer.ResponseContextSerializer;

public class TcpClient {
	
	private String ip;
	private int port;
	private Socket client;
	private static TcpClient tcpClient;
	
	private TcpClient(String ip, int port){
		this.ip = ip;
		this.port = port;
		init();
	}
	
	private void init(){
		try {
			client = new Socket(ip,port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public synchronized static TcpClient getInstance(){
		if(tcpClient == null){
			tcpClient = new TcpClient(SystemProperties.getIpAddress(), SystemProperties.getPort());
		}
		return tcpClient;
	}
	
	public void sendMsg(RequestContext requestContext){
		
		try {
			OutputStream outputStream =client.getOutputStream();
			RequestContextSerializer.encode(requestContext, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ResponseContext readInputStream() throws IOException{
		ResponseContext responseContext = null;
		try{
			InputStream inputStream = client.getInputStream();
			responseContext = ResponseContextSerializer.decode(inputStream);
		}catch(Exception e){
			e.printStackTrace();
		}
		return responseContext;
	}

}
