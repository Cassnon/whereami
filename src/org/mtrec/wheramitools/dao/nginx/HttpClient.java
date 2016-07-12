package org.mtrec.wheramitools.dao.nginx;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.AbstractVerifier;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.io.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpSession;
    public class HttpClient {
    public static KeyStore ks_eek123;

    static {    
    	// jks of mtrec
    	try {
    		ks_eek123 = KeyStore.getInstance(KeyStore.getDefaultType());
			String rootPath=Thread.currentThread().getContextClassLoader().getResource("/").getPath();
	    	InputStream in = new FileInputStream(new File(rootPath + "/eek123_ust_hk.jks"));
	    	try {
	    		ks_eek123.load(in, "garyitf".toCharArray());
	    	} finally {
	    	    in.close();
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	public HttpClient() {
		super();

	}
	
	public static byte[] getImage_server(HttpSession session,String strUrl){
	   
	   org.apache.http.client.HttpClient httpClient = HCBuilder_eek123();
	   HttpGet getMethod = new HttpGet(strUrl);
	   
		   	byte[] btImg = null;
		   	
		   	try{
					HttpResponse response = httpClient.execute(getMethod);
					if(response.getStatusLine().getStatusCode() != 200){
						
						throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
					}
					
					HttpEntity httpEntity = response.getEntity();
													
					btImg = IOUtils.toByteArray(httpEntity.getContent());
		    }  
		   	catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       return btImg;  
		}
	    
	    public static byte[] readInputStream(InputStream inStream) throws Exception{  
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        byte[] buffer = new byte[1024];  
	        int len = 0;  
	        while( (len=inStream.read(buffer)) != -1 ){  
	            outStream.write(buffer, 0, len);  
	        }  
	        inStream.close();  
	        return outStream.toByteArray();  
	    }
	    		
		//get host data
		public static <T> T doGethosts_server(String urlReq,Class<T> dataModelCls){
			
			org.apache.http.client.HttpClient httpClient = HCBuilder_eek123();
			HttpGet getMethod = new HttpGet(urlReq);
			T result = null;
			try {
				HttpResponse response = httpClient.execute(getMethod);
				if(response.getStatusLine().getStatusCode() != 200){
					
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
	 		
				HttpEntity httpEntity = response.getEntity();
				
				String jsonEntity = EntityUtils.toString(httpEntity, HTTP.UTF_8);//.toString(httpEntity);
				
				System.out.println(jsonEntity);
				
				ObjectMapper objectMapper = new ObjectMapper();
				
				result=objectMapper.readValue(jsonEntity, dataModelCls);
				
				System.out.println(result);
							
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
				
		}
			
		//get the data from API withclass
		public static <T> T doGetServer_withclass(HttpSession session,String urlReq,Class<T> dataModelCls){
			
			org.apache.http.client.HttpClient httpClient = HCBuilder_eek123();
	
			HttpGet getMethod = new HttpGet(urlReq);
			String jString = "{\"auth\":\""+session.getAttribute("auth").toString()+"\"}";
	
			//getMethod.setHeader("Content-Type", "application/json");
			T result = null;
			try {
				HttpResponse response = httpClient.execute(getMethod);
				if(response.getStatusLine().getStatusCode() == 404){
					//找不到的话返回空
					result = null;
					//throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
				else{
					HttpEntity httpEntity = response.getEntity();
					
					String jsonEntity = EntityUtils.toString(httpEntity, HTTP.UTF_8);//.toString(httpEntity);
					
					System.out.println(jsonEntity);
					
					if(jsonEntity.equals("")){
						//如果找不到 返回空
						result = null;
					}
					else{
						//如果找到了 则进行类型转化
						ObjectMapper objectMapper = new ObjectMapper();
						result=objectMapper.readValue(jsonEntity, dataModelCls);
						System.out.println(result);
					}
				}
							
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;			
		}
	    
	public static String doGetServer_withoutclass(HttpSession session,String urlReq){
		
		org.apache.http.client.HttpClient httpClient = HCBuilder_eek123();

		HttpGet getMethod = new HttpGet(urlReq);
		String jString = "{\"auth\":\""+session.getAttribute("auth").toString()+"\"}";
		String result ="";
		try {
			HttpResponse response = httpClient.execute(getMethod);
			if(response.getStatusLine().getStatusCode() != 200){
				
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
 		
			HttpEntity httpEntity = response.getEntity();
			
			String jsonEntity = EntityUtils.toString(httpEntity, HTTP.UTF_8);//.toString(httpEntity);
			
			System.out.println(jsonEntity);
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			result=jsonEntity.toString();
			
			System.out.println(result);
						
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
		
	public static Integer doPost_Server(String urlReq,String jString){
		String result ="";
		org.apache.http.client.HttpClient httpClient = HCBuilder_eek123();
		HttpPost postMethod = new HttpPost(urlReq);
		postMethod.setHeader("Content-Type", "application/json");
		try {
			postMethod.setEntity(new StringEntity(jString,"utf-8"));
			HttpResponse httpResponse=httpClient.execute(postMethod);		
	        if(httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200)  
	        {  
	            HttpEntity httpEntity = httpResponse.getEntity();  
				result = EntityUtils.toString(httpEntity);
				System.out.println(result);
	        }
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result.equals("\"Succeeded!\""))
		return 1;
		else
		return 0;
	}
    
	public static String doPost_AServer(String urlReq,String jString){
		String result ="";
		org.apache.http.client.HttpClient httpClient = HCBuilder_eek123();
		HttpPost postMethod = new HttpPost(urlReq);
		postMethod.setHeader("Content-Type", "application/json");
		try {
			postMethod.setEntity(new StringEntity(jString,"utf-8"));
			HttpResponse httpResponse=httpClient.execute(postMethod);		
	        if(httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200)  
	        {  
	            HttpEntity httpEntity = httpResponse.getEntity();  
				result = EntityUtils.toString(httpEntity);
				System.out.println(result);
	        }
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static String doPost_Server_save(String urlReq,String jString){
		String result ="";
		org.apache.http.client.HttpClient httpClient = HCBuilder_eek123();
		HttpPost postMethod = new HttpPost(urlReq);
		postMethod.setHeader("Content-Type", "application/json");
		try {
			postMethod.setEntity(new StringEntity(jString,"utf-8"));
			HttpResponse httpResponse=httpClient.execute(postMethod);		
	        if(httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200)  
	        {  
	            HttpEntity httpEntity = httpResponse.getEntity();  
				result = EntityUtils.toString(httpEntity);
				System.out.println(result);
	        }
	        else if (httpResponse.getStatusLine().getStatusCode() == 404){
	        	result = "Cannot find the Method";
	        }
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static <T> T Login(String urlReq,String jString,Class<T> dataModelCls){
		T result = null;
		org.apache.http.client.HttpClient httpClient = HCBuilder_eek123();
		HttpPost postMethod = new HttpPost(urlReq);
		postMethod.setHeader("Content-Type", "application/json");
		try {
			postMethod.setEntity(new StringEntity(jString,"utf-8"));
			HttpResponse httpResponse=httpClient.execute(postMethod);		
	        if(httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200)  
	        {  
	            HttpEntity httpEntity = httpResponse.getEntity();  
				
				String jsonEntity = EntityUtils.toString(httpEntity, HTTP.UTF_8);//.toString(httpEntity);
				
				System.out.println(jsonEntity);
				
				jsonEntity = "["+jsonEntity +"]";
				
				ObjectMapper objectMapper = new ObjectMapper();
				
				result=objectMapper.readValue(jsonEntity, dataModelCls);
				
				System.out.println(result);
							
			} 
	     }catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return result;
	}
	
	
	public static String dotryPostImg(String urlReq,byte[] imgbytes,HttpSession session){
		
		String result ="";
		if(session.getAttribute("auth")==null){
			
			result = "noauth";
			
			return result;
			
		}
		org.apache.http.client.HttpClient httpClient = HCBuilder_eek123();;
		HttpPost postMethod = new HttpPost();
		try {

			URIBuilder uriBuilder = new URIBuilder(urlReq);
			uriBuilder.addParameter("auth", session.getAttribute("auth").toString());
			URI uri =uriBuilder.build();

			postMethod.setURI(uri);
			
			ByteArrayEntity entity=new ByteArrayEntity(imgbytes);
			postMethod.setEntity(entity);
			HttpResponse httpResponse=httpClient.execute(postMethod);		
	        if(httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200)  
	        {  
	            HttpEntity httpEntity = httpResponse.getEntity();  
				result = EntityUtils.toString(httpEntity);
	        }
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.print(result);
		return result;	
	}
	
	//The HttpClient connect to server eek123.ust.hk
	static org.apache.http.client.HttpClient HCBuilder_eek123(){

	    	javax.net.ssl.SSLContext sslContext = null;
	    	X509HostnameVerifier verifier = null;
			try {

		    	sslContext = SSLContexts.custom()
		    	        .useTLS()
		    	        .loadTrustMaterial(ks_eek123)
		    	        .build();
		    	verifier = new AbstractVerifier() {

		    	    @Override
		    	    public void verify(final String host, final String[] cns, final String[] subjectAlts) throws SSLException {
		    	    }

		    	};
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			org.apache.http.client.HttpClient httpclient = HttpClients.custom()
	    	        .setSslcontext(sslContext)
	    	        .setHostnameVerifier(verifier)
	    	    .build();

			return httpclient;
	}
    
}
