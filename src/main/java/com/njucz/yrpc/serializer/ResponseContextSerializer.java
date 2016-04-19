package com.njucz.yrpc.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ResponseContextSerializer {
	
	public static String encode(ResponseContext responseContext,OutputStream outputStream){
		StringBuilder sb = new StringBuilder();
		byte[] data;
		try {
			data = SerializeUtils.serialize(responseContext);
			int dataLength = data.length;
			System.out.println("send length:" + dataLength);
			outputStream.write(int2byteArray(dataLength));
			outputStream.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static ResponseContext decode(InputStream inputStream){
		try {
			byte[] size = new byte[4];
			inputStream.read(size);
			int dataLength = byteArray2int(size);
			System.out.println("receive length:" + dataLength);
			byte[] data = new byte[dataLength];
			inputStream.read(data);
			ResponseContext responseContext = (ResponseContext)SerializeUtils.deserialize(data);
			return responseContext;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static int byteArray2int(byte[] b){  
	    byte[] a = new byte[4];  
	    int i = a.length - 1,j = b.length - 1;  
	    for (; i >= 0 ; i--,j--) {//从b的尾部(即int值的低位)开始copy数据  
	        if(j >= 0)  
	            a[i] = b[j];  
	        else  
	            a[i] = 0;//如果b.length不足4,则将高位补0  
	    }  
	    int v0 = (a[0] & 0xff) << 24;//&0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位  
	    int v1 = (a[1] & 0xff) << 16;  
	    int v2 = (a[2] & 0xff) << 8;  
	    int v3 = (a[3] & 0xff) ;  
	    return v0 + v1 + v2 + v3;  
	}
	
	private static byte[] int2byteArray(int num) {  
	    byte[] result = new byte[4];  
	    result[0] = (byte)(num >>> 24);//取最高8位放到0下标  
	    result[1] = (byte)(num >>> 16);//取次高8为放到1下标  
	    result[2] = (byte)(num >>> 8); //取次低8位放到2下标   
	    result[3] = (byte)(num );      //取最低8位放到3下标  
	    return result;  
	}
}
