package com.somnus.rest.core.spi.provider;

import static com.somnus.rest.core.ProtobufMediaType.APPLICATION_X_PROTOBUF;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

/**
 * @Description 基于Google Protocol Buffer的Provider
 * @author Somnus
 * @date 2013-4-8
 * @version 1.0
 */
@Provider
@Consumes(APPLICATION_X_PROTOBUF)
@Produces(APPLICATION_X_PROTOBUF)
public class ProtobufMessageProvider 
		implements MessageBodyReader<Message>, MessageBodyWriter<Message> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return Message.class.isAssignableFrom(type);
	}

	@Override
	public long getSize(Message message, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return message.getSerializedSize();
	}

	@Override
	public void writeTo(Message message, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		entityStream.write(message.toByteArray());
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return mediaType.toString().equals(APPLICATION_X_PROTOBUF); 
	}

	@Override
	public Message readFrom(Class<Message> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {		
		try {
			Builder builder = Builder.class.cast(MethodUtils.invokeStaticMethod(type, "newBuilder", null));
			return builder.mergeFrom(entityStream).build();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
