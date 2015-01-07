package com.somnus.rest.core.client;

public class ParameterBuilder {
	
	private DefaultParameter parameter = new DefaultParameter();

	private ParameterBuilder(){}
	
	/**
	 * @Description 构建参数Builder对象
	 * @return
	 * @author caobin
	 */
	public static ParameterBuilder getInstance(){
		return new ParameterBuilder();
	}

	/**
	 * @param baseUrl the baseUrl to set
	 */
	public ParameterBuilder setBaseUrl(String baseUrl) {
		parameter.setBaseUrl(baseUrl);
		return this;
	}
	
	/**
	 * @Description 设置连接超时
	 * @param connectionTimeout
	 * @return
	 * @author caobin
	 */
	public ParameterBuilder setConnectionTimeout(int connectionTimeout){
		parameter.setConnectionTimeout(connectionTimeout);
		return this;
	}
	
	/**
	 * @Description 设置读取超时
	 * @param readTimeout
	 * @return
	 * @author caobin
	 */
	public ParameterBuilder setReadTimeout(int readTimeout){
		parameter.setReadTimeout(readTimeout);
		return this;
	}
	
	public Parameter build(){
		return parameter;
	}
	
	class DefaultParameter implements Parameter{		
		private String baseUrl;
		
		private int connectionTimeout;
		
		private int readTimeout;
		
		public DefaultParameter(){
			connectionTimeout = 30000;
			readTimeout = 30000;
		}

		@Override
		public String getBaseUrl() {
			return baseUrl;
		}

		/**
		 * @param baseUrl the baseUrl to set
		 */
		public void setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
		}

		/**
		 * @return the connectionTimeout
		 */
		@Override
		public int getConnectionTimeout() {
			return connectionTimeout;
		}

		/**
		 * @param connectionTimeout the connectionTimeout to set
		 */
		public void setConnectionTimeout(int connectionTimeout) {
			this.connectionTimeout = connectionTimeout;
		}

		/**
		 * @return the readTimeout
		 */
		@Override
		public int getReadTimeout() {
			return readTimeout;
		}

		/**
		 * @param readTimeout the readTimeout to set
		 */
		public void setReadTimeout(int readTimeout) {
			this.readTimeout = readTimeout;
		}	
		
		
	}
}
