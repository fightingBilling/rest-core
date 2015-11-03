package com.somnus.rest.core.client;

import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

import com.somnus.rest.core.client.proxy.ResourceJsonSSLInvocationHandler;

/**
 * @Description 
 * @author Somnus
 * @date 2014-8-14
 * @version 1.0
 */
public class RESTfulJsonSSLClientFactory {
	 /**
     * 代理 cache
     */
    private static ConcurrentHashMap proxyCache=new ConcurrentHashMap();

	/**
	 * @Description 创建客户端
	 * @param interfaceType 接口类型
	 * @param baseUrl 基础url
	 * @return
	 * @author Somnus
	 */
	public static<T> T createClient(Class<T> interfaceType, String baseUrl){
		ParameterBuilder builder = ParameterBuilder.getInstance();
		builder.setBaseUrl(baseUrl);
		return interfaceType.cast(proxyClient(interfaceType, builder.build()));
	}
	
	/**
	 * @Description 客户端代理
	 * @param interfaceType 接口类型
	 * @param param 参数
	 * @return
	 * @author Somnus
	 */
	private static <C> C proxyClient(Class<C> interfaceType, Parameter param){
		Class<?>[] interfaces = new Class<?>[]{interfaceType};
        Object proxy=proxyCache.get(interfaceType);
        if(proxy==null){
            proxy = Proxy.newProxyInstance(
                    RESTfulJsonSSLClientFactory.class.getClassLoader(),
                    interfaces,
                    new ResourceJsonSSLInvocationHandler(param)
            );
            proxyCache.putIfAbsent(interfaceType,proxy);
        }
		return interfaceType.cast(proxy);
	}
}
