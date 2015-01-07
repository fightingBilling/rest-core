package com.somnus.rest.core.client;

/**
 * @Description 参数接口
 * @author caobin
 * @date 2013-4-11
 * @version 1.0
 */
public interface Parameter {

	/**
	 * @Description 获取基础地址
	 * @return
	 * @author caobin
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
