package com.kpleasing.crm.ejb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class HttpHelper {
	
	private static Logger logger = Logger.getLogger(HttpHelper.class);
	
    private static final int CONNECT_TIMEOUT = 30000;
	
	private static final int SOCKET_TIMEOUT = 30000;
	
	private static final String HTTP_URI_CHARSET = "UTF-8";
	
	/** HTTP保留时间 */
	private final static int MAX_HTTP_KEEP_ALIVE = 30 * 1000;

	/** 每个路由最大连接数 */
	private final static int MAX_ROUTE_CONNECTIONS = 400;
	
	/** 最大连接数 */
	private final static int MAX_TOTAL_CONNECTIONS = 800;
	
	private static PoolingHttpClientConnectionManager connManager = null;
	
	private static CloseableHttpClient httpclient = null;
	
	static {
		HttpRequestRetryHandler myRetryHandler = customRetryHandler();
		ConnectionKeepAliveStrategy customKeepAliveHander = customKeepAliveHander();
		connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		connManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
		HttpHost localhost = new HttpHost("locahost", 80);
		connManager.setMaxPerRoute(new HttpRoute(localhost), 50);
		httpclient = HttpClients.custom().setConnectionManager(connManager).setKeepAliveStrategy(customKeepAliveHander)
		        .setRetryHandler(myRetryHandler).build();
	}
	
	/**
	 * 设置HTTP连接保留时间
	 *
	 * @return 保留策略
	 */
	private static ConnectionKeepAliveStrategy customKeepAliveHander() {
		ConnectionKeepAliveStrategy myKeepAliveHander = new ConnectionKeepAliveStrategy() {

			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				return MAX_HTTP_KEEP_ALIVE;
			}
		};
		return myKeepAliveHander;
	}

	/**
	 * 是否重试
	 *
	 * @return false，不重试
	 */
	private static HttpRequestRetryHandler customRetryHandler() {
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

			@Override
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				return false;
			}
		};
		return myRetryHandler;
	}
	
	
	/**
	 * http post request
 	 * @param url
	 * @param content
	 * @param type
	 * @return
	 */
	public static String doHttpPost(String url, String content) {
		CloseableHttpResponse response = null;
		HttpPost httpPost = new HttpPost(url);
		
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPost.setConfig(requestConfig);
		
		logger.info("post 报文信息:"+content);
		httpPost.setHeader("Content-Type","application/xml; charset=utf-8"); 
		StringEntity reqEntity = new StringEntity(content, ContentType.TEXT_HTML.withCharset("UTF-8"));
		httpPost.setEntity(reqEntity);
		
		String result = null;
		try {
			response = httpclient.execute(httpPost);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				StringBuffer sb = new StringBuffer();
				HttpEntity entity = response.getEntity();

				BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent(), HTTP_URI_CHARSET));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					sb.append(inputLine);
				}
				result = sb.toString();
				logger.info("post 結果：" + result);
				in.close();
			} else {
				HttpEntity httpEntity =  response.getEntity();
				String contentR = EntityUtils.toString(httpEntity);
				logger.info(contentR);
				logger.info("post 状态：" + response.getStatusLine().toString() + response.getStatusLine().getStatusCode());
				
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			
		}
		finally {
			httpPost.releaseConnection();
			HttpClientUtils.closeQuietly(response);
		}
		return result;
	}
}
