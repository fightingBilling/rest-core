package com.somnus.restful.core.spi.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

/**
 * CustomJacksonJsonProvider
 *
 * @author levis
 * @version 1.0 13-12-3
 */
@Provider
@Produces("application/json")
@Consumes("application/json")
public class CustomJacksonJsonProvider extends JacksonJsonProvider {

    private static volatile boolean flag=false;

    private static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";

    @Override
    public void writeTo(Object value, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException {
        ObjectMapper mapper = locateMapper(type, mediaType);
        if(!flag){
            setDeFaultFeature(mapper);
        }
        super.writeTo(value, type, genericType, annotations, mediaType, httpHeaders, entityStream);
    }

    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException {
        ObjectMapper mapper = locateMapper(type, mediaType);
        if(!flag){
            setDeFaultFeature(mapper);
        }
        return super.readFrom(type,genericType,annotations,mediaType,httpHeaders,entityStream);
    }

    private synchronized void  setDeFaultFeature(ObjectMapper mapper){
        //serialization
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        //deSerialization
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        //date
        //mapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
    }
}
