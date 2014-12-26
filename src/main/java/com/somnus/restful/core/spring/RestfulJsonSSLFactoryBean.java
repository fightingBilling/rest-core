package com.somnus.restful.core.spring;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.somnus.restful.core.client.RESTfulJsonSSLClientFactory;

public class RestfulJsonSSLFactoryBean implements FactoryBean<Object>,
		InitializingBean {

	 private Object serviceProxy;

	    private Class<?> serviceInterface;

	    private String serviceUrl;

	    @Override
	    public Object getObject() throws Exception {
	        return serviceProxy;
	    }

	    @Override
	    public Class<?> getObjectType() {
	        return getServiceInterface();
	    }

	    @Override
	    public boolean isSingleton() {
	        return true;
	    }

	    public Class<?> getServiceInterface() {
	        return serviceInterface;
	    }

	    public void setServiceInterface(Class<?> serviceInterface) {
	        this.serviceInterface = serviceInterface;
	    }

	    public String getServiceUrl() {
	        return serviceUrl;
	    }

	    public void setServiceUrl(String serviceUrl) {
	        this.serviceUrl = serviceUrl;
	    }

	    @Override
	    public void afterPropertiesSet() throws Exception {
	        if(serviceInterface==null){
	            throw new IllegalArgumentException("serviceInterface must set value !");
	        }
	        if(serviceUrl==null){
	            throw new IllegalArgumentException("serviceUrl must set value !");
	        }
	        this.serviceProxy = RESTfulJsonSSLClientFactory.createClient(getServiceInterface(), getServiceUrl());
	    }

}
