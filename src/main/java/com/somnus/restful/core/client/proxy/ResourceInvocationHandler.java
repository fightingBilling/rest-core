package com.somnus.restful.core.client.proxy;

import static com.somnus.restful.core.ProtobufMediaType.APPLICATION_X_PROTOBUF;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.google.protobuf.Message;
import com.somnus.restful.core.client.Parameter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;

/**
 * @Description 
 * @author caobin
 * @date 2013-4-8
 * @version 1.0
 */
public class ResourceInvocationHandler implements InvocationHandler {

	private Parameter parameter;
	
	public ResourceInvocationHandler(Parameter parameter){
		this.parameter = parameter;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Message message = Message.class.cast(args[0]);

		Object head = MethodUtils.invokeMethod(message, "getHead", null);
		String trancode = String.class.cast(MethodUtils.invokeMethod(head, "getTranCode", null));
		
		WebResource webResource = getClient().resource(parameter.getBaseUrl().concat(trancode));
		ClientResponse response = webResource.entity(message, APPLICATION_X_PROTOBUF).post(ClientResponse.class);
		if(response.getStatus() == Status.OK.getStatusCode()){
			return response.getEntity(method.getReturnType());
		}else{			
			throw new RuntimeException(String.format("\n\tStatus:%s\n\tError Message:%s", response.getStatus(), response.getEntity(String.class)));
		}
	}
	
	private Client getClient() throws Exception {
		if (client == null) {
			synchronized(ResourceJsonSSLInvocationHandler.class) {
				if (client == null) {
					//ClientConfig config = new DefaultClientConfig();
					//config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(getHostnameVerifier(), getSSLContext()));
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
