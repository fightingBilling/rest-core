package com.somnus.rest.core.support;

import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.google.protobuf.GeneratedMessage.Builder;

/**
 * @Description 自动填充头部字段
 * @author Somnus
 * @date 2013-4-8
 * @version 1.0
 */
public class HeadMerger {
	
	/**
	 * @Description 对Head.Builder进行部分字段的设值
	 * @param builder
	 * @author Somnus
	 */
	public static void merge(Builder<?> builder){
		try {
			MethodUtils.invokeMethod(builder, "setMsgType", new Object[]{"0000001"});
			MethodUtils.invokeMethod(builder, "setFrontNo", new Object[]{hostName});		
			MethodUtils.invokeMethod(builder, "setFrontTime", new Object[]{DateFormatUtils.format(new Date(), datePattern)});
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private static String datePattern = "yyyy-MM-dd HH:mm:ss";
	private static String hostName = null;
	
	static{	
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}	
	}
}
