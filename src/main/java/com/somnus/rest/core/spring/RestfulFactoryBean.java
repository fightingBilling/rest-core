package com.somnus.rest.core.spring;

import com.somnus.rest.core.client.RESTfulClientFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * RestfulFactoryBean
 *
 * @author levis
 * @version 1.0 13-6-4
 */
public class RestfulFactoryBean implements FactoryBean<Object>, InitializingBean {

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
        this.serviceProxy = RESTfulClientFactory.createClient(getServiceInterface(), getServiceUrl());
    }
}
