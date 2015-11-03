package com.somnus.rest.core.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.somnus.rest.core.client.Parameter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

public class ResourceJsonSSLInvocationHandler implements InvocationHandler {
	private Parameter parameter;

	public ResourceJsonSSLInvocationHandler(Parameter parameter) {
		this.parameter = parameter;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String path = method.getDeclaringClass().getName();
		WebResource webResource = getSSLClient().resource(
				parameter.getBaseUrl().concat(path).concat("/")
						.concat(method.getName()));
		ClientResponse response = webResource.entity(args[0],
				MediaType.APPLICATION_JSON).post(ClientResponse.class);
		if (response.getStatus() == Status.OK.getStatusCode()) {
			return response.getEntity(method.getReturnType());
		} else if (response.getStatus() == Status.NO_CONTENT.getStatusCode()) {
			return null;
		} else {
			throw new RuntimeException(String.format(
					"\n\tStatus:%s\n\tError Message:%s", response.getStatus(),
					response.getEntity(String.class)));
		}
	}

	/**
	 * @Description 获取RESTful客户端(ssl)
	 * @return
	 * @author Somnus
	 * @throws Exception 
	 */
	private Client getSSLClient() throws Exception {
		if (client == null) {
			synchronized(ResourceJsonSSLInvocationHandler.class) {
				if (client == null) {
					ClientConfig config = new DefaultClientConfig();
					config.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, parameter.getConnectionTimeout());
					config.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, parameter.getReadTimeout());
					config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(getHostnameVerifier(), getSSLContext()));
					client = Client.create(config);
				}
			}
		}
		return client;
	}

	private HostnameVerifier getHostnameVerifier() {
		return new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession sslSession) {
				return true;
			}
		};
	}
	
	private SSLContext getSSLContext() throws Exception{		
		System.setProperty("jsse.enableSNIExtension", "false");
        TrustManager[] certs = new TrustManager[]
        {
            new X509TrustManager(){
                @Override
                public X509Certificate[] getAcceptedIssuers(){
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException{
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException{
                }
            }
        };
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, certs, new SecureRandom()) ;
		return context;
	}

	private volatile Client client;
}
