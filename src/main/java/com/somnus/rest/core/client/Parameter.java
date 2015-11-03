package com.somnus.rest.core.client;

/**
 * @Description 参数接口
 * @author Somnus
 * @date 2013-4-11
 * @version 1.0
 */
public interface Parameter {

	/**
	 * @Description 获取基础地址
	 * @return
	 * @author Somnus
	 */
	String getBaseUrl();
	
	
	/**
	 * @return the readTimeout
	 */
	int getReadTimeout();
	
	/**
	 * @return the connectionTimeout
	 */
	int getConnectionTimeout();
}
