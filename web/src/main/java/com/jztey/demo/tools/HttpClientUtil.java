package com.jztey.demo.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/* 
 * 利用HttpClient进行post请求的工具类 
 */

public class HttpClientUtil {
	private static final String APPLICATION_JSON = "application/json";
	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	public static String doPost(String url, Map<String, Object> map, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
			
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * httpclient的 POST方法
	 * 
	 * @author ys 2016-05-26
	 * @param url
	 * @param json
	 *            json参数值
	 * @return
	 * @throws Exception
	 */
	public static String httpPostWithJSON(String url, String json) throws Exception {
		String result = null;
		// 将JSON进行UTF-8编码,以便传输中文
		// String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);

		String encoderJson = json;

		// DefaultHttpClient httpClient = new DefaultHttpClient();
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

		StringEntity se = new StringEntity(encoderJson, "UTF-8");// 解决乱码问题
		se.setContentType(CONTENT_TYPE_TEXT_JSON);
		// se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
		// APPLICATION_JSON));
		httpPost.setEntity(se);
		httpClient = new SSLClient();
		HttpResponse response = httpClient.execute(httpPost);
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				result = EntityUtils.toString(resEntity, "UTF-8");
			}
		}
		return result;

	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String httpGet(String url) throws Exception {
		BufferedReader in = null;

		String content = null;
		try {
			// 定义HttpClient
			// HttpClient client = new DefaultHttpClient();
		//	CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpClient client  = new SSLClient();
			// 实例化HTTP方法
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			content = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (in != null) {
				try {
					in.close();// 最后要关闭BufferedReader
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}

	public static String http998jkPost(String url, Map<String, Object> map, String token, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			httpPost.setHeader("Authorization", token);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * 需要头加token 的访问
	 * 
	 * @param url
	 * @return wq
	 * @throws Exception
	 */
	public static String http998jkGet(String url, String token) throws Exception {
		BufferedReader in = null;

		String content = null;
		try {
			// 定义HttpClient
			// HttpClient client = new DefaultHttpClient();
			CloseableHttpClient client = HttpClients.createDefault();
			// 实例化HTTP方法
			HttpGet request = new HttpGet();
			request.setHeader("Authorization", token);
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			content = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (in != null) {
				try {
					in.close();// 最后要关闭BufferedReader
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}

	public static String httpPUT(String url, String json) throws Exception {

//		URL url = new URL(urlStr);
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setRequestMethod("PUT");
//		String paramStr = paras;// prepareParamPut(paramMap);//
//								// prepareParam(paramMap);
//		conn.setDoInput(true);
//		conn.setDoOutput(true);
//		OutputStream os = conn.getOutputStream();
//		os.write(paramStr.toString().getBytes("utf-8"));
//		os.close();
//
//		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//		String line;
//		String result = "";
//		while ((line = br.readLine()) != null) {
//			result += line;
//		}
//		System.out.println(result);
//		br.close();
//		return result;

		String result = null;
		// 将JSON进行UTF-8编码,以便传输中文
		// String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);

		String encoderJson = json;

		// DefaultHttpClient httpClient = new DefaultHttpClient();
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpPut httpPut = new HttpPut(url);
		httpPut.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

		StringEntity se = new StringEntity(encoderJson, "UTF-8");// 解决乱码问题
		se.setContentType(CONTENT_TYPE_TEXT_JSON);
		// se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
		// APPLICATION_JSON));
		httpPut.setEntity(se);
		httpClient = new SSLClient();
		HttpResponse response = httpClient.execute(httpPut);
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				result = EntityUtils.toString(resEntity, "UTF-8");
			}
		}
		return result;

		
	}

	/**
	 * 处理PUT方法的参数
	 * 
	 * @author ys 2016-05-21
	 * @param paramMap
	 * @return
	 */
	public static String prepareParamPut(Map<String, Object> paramMap) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(paramMap);
	}

	public static String httpDelete(String url) throws Exception {

		BufferedReader in = null;

		String content = null;
		try {
			// 定义HttpClient
			// HttpClient client = new DefaultHttpClient();
			CloseableHttpClient client = HttpClients.createDefault();
			// 实例化HTTP方法
			HttpDelete delete = new HttpDelete(url);

			HttpResponse response = client.execute(delete);

			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			content = sb.toString();
		} finally {
			if (in != null) {
				try {
					in.close();// 最后要关闭BufferedReader
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return content;

	}

}