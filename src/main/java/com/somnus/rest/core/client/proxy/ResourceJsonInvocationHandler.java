package com.somnus.rest.core.client.proxy;

import com.somnus.rest.core.client.Parameter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description 
 * @author caobin
 * @date 2013-4-8
 * @version 1.0
 */
public class ResourceJsonInvocationHandler implements InvocationHandler {

	private Parameter parameter;

	public ResourceJsonInvocationHandler(Parameter parameter){
		this.parameter = parameter;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String path=method.getDeclaringClass().getName();
        WebResource webResource = getClient().resource(parameter.getBaseUrl().concat(path).concat("/").concat(method.getName()));
		ClientResponse response = webResource.entity(args[0], MediaType.APPLICATION_JSON).post(ClientResponse.class);
		if(response.getStatus() == Status.OK.getStatusCode()){
			return response.getEntity(method.getReturnType());
        }else if(response.getStatus() == Status.NO_CONTENT.getStatusCode()){
            return null;
		}else{			
			throw new RuntimeException(String.format("\n\tStatus:%s\n\tError Message:%s", response.getStatus(), response.getEntity(String.class)));
		}
	}
	
	/**
	 * @Description 获取RESTful客户端
	 * @return
	 * @author caobin
	 */
	private Client getClient(){
		if(client == null){
			synchronized (ResourceJsonInvocationHandler.class) {
				if(client == null){
					//ClientConfig config = new DefaultClientConfig();
					//config.getClasses().add(ProtobufMessageProvider.class);
					//by SPI
					client = Client.create();
					client.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, parameter.getConnectionTimeout());
					client.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, parameter.getReadTimeout());
				}
			}
		}
		return client;
	}
	
	private volatile Client client;
}
