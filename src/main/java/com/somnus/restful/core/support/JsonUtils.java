package com.somnus.restful.core.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * JsonUtils
 *
 * @author levis
 * @version 1.0 13-9-29
 */
public class JsonUtils {

    private static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";

    private static ObjectMapper objectMapper;

    public static String toString(Object obj){
        if(objectMapper==null){
            objectMapper= getObjectMapper();
        }
        try{
            return objectMapper.writeValueAsString(obj);
        }catch (Exception ex){
           throw new RuntimeException(ex);
        }
    }

    private synchronized static ObjectMapper  getObjectMapper(){
        ObjectMapper mapper=new ObjectMapper();
        //serialization
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        //deSerialization
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        //date
        mapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        return mapper;
    }

}
