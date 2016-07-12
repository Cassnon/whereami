package org.mtrec.wheramitools.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	public static void setCookie(HttpRequestBase request,String cookie){
   
		request.setHeader("Set-Cookie", cookie);  
		
	}
	
	private static HttpParams configHttpParams(){
		 HttpParams httpParameters = new BasicHttpParams();   
		 HttpConnectionParams.setConnectionTimeout(httpParameters, 10*1000); 
		 HttpConnectionParams.setSoTimeout(httpParameters, 10*1000);   
		 HttpConnectionParams.setSocketBufferSize(httpParameters, 8192);
		 return httpParameters;
	}
	
	public static String doGet(String urlReq)
	{
    	String result= "";
    	HttpGet httpRequst = new HttpGet(urlReq);

    	try {
			HttpResponse httpResponse = new DefaultHttpClient(configHttpParams()).execute(httpRequst);
		    if(httpResponse.getStatusLine().getStatusCode() == 200)
		    {
		    	HttpEntity httpEntity = httpResponse.getEntity();
		    	result = EntityUtils.toString(httpEntity);
		    	result.replaceAll("\r", "");  
		    }
		    else 
		    	httpRequst.abort();
    	} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		return result;
	}
	
	public static HttpResponse doPostReturnResponse(String urlReq, Header[] headers,Map<String, String> params) {
		HttpResponse result = null;  
        HttpPost httpRequst = new HttpPost(urlReq);//锟斤拷锟斤拷HttpPost锟斤拷锟斤拷        
     
        if(headers != null && headers.length > 0) {
        	for(Header h:headers) {
        		httpRequst.addHeader(h);
        	}
        }
        
        List <NameValuePair> paramsTemp = new ArrayList<NameValuePair>();  
        if(params != null && params.size() > 0) {
	        for(Map.Entry<String, String> entry : params.entrySet()){
	        	paramsTemp.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	        } 
        }
        
        try {  
        	if(paramsTemp.size() > 0) {
        		httpRequst.setEntity(new UrlEncodedFormEntity(paramsTemp));
        	}
        	HttpClient httpClient = new DefaultHttpClient(configHttpParams());
            
            result = httpClient.execute(httpRequst);  
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        catch (ClientProtocolException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return result;  
	}
	
	public static HttpResponse doPostReturnResponse(String urlReq, Header[] headers,String params) {
		HttpResponse result = null;  
        HttpPost httpRequst = new HttpPost(urlReq);        
               
        if(headers != null && headers.length > 0) {
        	for(Header h:headers) {
        		httpRequst.addHeader(h);
        	}
        }        
        
        try {  
        	if(params != null && params.length() > 0) {
                StringEntity stringEntity = new StringEntity(params,"utf-8");
                httpRequst.setEntity(stringEntity);
        	}
        	HttpClient httpClient = new DefaultHttpClient(configHttpParams());
            result = httpClient.execute(httpRequst);  
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        catch (ClientProtocolException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return result;  
	}
	
	/*
	 * 	
	 */
	public static String doPost(String urlReq,Header[] headers,Map<String, String> params)  
    {  
		String result = "";
		
        HttpResponse httpResponse = doPostReturnResponse(urlReq, headers, params);
            
        if(httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200)  
        {  
            HttpEntity httpEntity = httpResponse.getEntity();  
            try {
				result = EntityUtils.toString(httpEntity);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        }
        
        return result;
    }
	
	public static String doPost(String urlReq,Header[] headers,String params)  
    {  
		String result = "";
		
        HttpResponse httpResponse = doPostReturnResponse(urlReq, headers, params);
            
        if(httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200)  
        {  
            HttpEntity httpEntity = httpResponse.getEntity();  
            try {
				result = EntityUtils.toString(httpEntity);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }
        
        return result;
    }
	
	
	
	/**
     * 
     *   <FORM METHOD=POST ACTION="http://192.168.1.101:8083/upload/servlet/UploadServlet" enctype="multipart/form-data">
            <INPUT TYPE="text" NAME="name">
            <INPUT TYPE="text" NAME="id">
            <input type="file" name="imagefile"/>
            <input type="file" name="zip"/>
         </FORM>
     * @param urlReq 
     * @param params 
     * @param file 
     */
    public static boolean doPostFile(String urlReq, Map<String, String> params, FormFile[] files) throws Exception{     
        final String BOUNDARY = "---------------------------7da2137580612";
        final String endline = "--" + BOUNDARY + "--\r\n";
        
        int fileDataLength = 0;
        for(FormFile uploadFile : files){
            StringBuilder fileExplain = new StringBuilder();
             fileExplain.append("--");
             fileExplain.append(BOUNDARY);
             fileExplain.append("\r\n");
             fileExplain.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
             fileExplain.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
             fileExplain.append("\r\n");
             fileDataLength += fileExplain.length();
            if(uploadFile.getInStream()!=null){
                fileDataLength += uploadFile.getFile().length();
             }else{
                 fileDataLength += uploadFile.getData().length;
             }
        }
        StringBuilder textEntity = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            textEntity.append("--");
            textEntity.append(BOUNDARY);
            textEntity.append("\r\n");
            textEntity.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
            textEntity.append(entry.getValue());
            textEntity.append("\r\n");
        }
        int dataLength = textEntity.toString().getBytes().length + fileDataLength +  endline.getBytes().length;
        
        URL url = new URL(urlReq);
        int port = url.getPort()==-1 ? 80 : url.getPort();
        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);    
        //socket.getSendBufferSize()
        OutputStream outStream = socket.getOutputStream();
       
        String requestmethod = "POST "+ url.getPath()+" HTTP/1.1\r\n";
        outStream.write(requestmethod.getBytes());
        String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
        outStream.write(accept.getBytes());
        String language = "Accept-Language: zh-CN\r\n";
        outStream.write(language.getBytes());
        String contenttype = "Content-Type: multipart/form-data; boundary="+ BOUNDARY+ "\r\n";
        outStream.write(contenttype.getBytes());
        String contentlength = "Content-Length: "+ dataLength + "\r\n";
        outStream.write(contentlength.getBytes());
        String alive = "Connection: Keep-Alive\r\n";
        outStream.write(alive.getBytes());
        String host = "Host: "+ url.getHost() +":"+ port +"\r\n";
        outStream.write(host.getBytes());
        outStream.write("\r\n".getBytes());
        outStream.write(textEntity.toString().getBytes());           
        for(FormFile uploadFile : files){
            StringBuilder fileEntity = new StringBuilder();
             fileEntity.append("--");
             fileEntity.append(BOUNDARY);
             fileEntity.append("\r\n");
             fileEntity.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
             fileEntity.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
             outStream.write(fileEntity.toString().getBytes());
             if(uploadFile.getInStream()!=null){
                 byte[] buffer = new byte[1024];
                 int len = 0;
                 Long lengthUploaded = 0L;
                 while((len = uploadFile.getInStream().read(buffer, 0, 1024))!=-1){
                     outStream.write(buffer, 0, len);
                     lengthUploaded += len;
                 }
                 uploadFile.getInStream().close();
             }else{
                 outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);
             }
             outStream.write("\r\n".getBytes());
        }
        outStream.write(endline.getBytes());
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        if(reader.readLine().indexOf("200")==-1){
            return false;
        }
        outStream.flush();
        outStream.close();
        reader.close();
        socket.close();
        return true;
    }
    
    /**
     * 
     * @param urlReq 
     * @param params 
     * @param file 
     */
    public static boolean doPostFile(String urlReq, Map<String, String> params, FormFile file) throws Exception{
       return doPostFile(urlReq, params, new FormFile[]{file});
    }
    
    public static String doPostJson(String urlReq,String jsonForm) throws Exception {
    	    	
    	String result = "";
    	
    	try {

        	HttpClient httpClient = new DefaultHttpClient();
        	
        	HttpPost post = new HttpPost(urlReq);
        	
        	post.addHeader("accept", "application/json");

        	if(jsonForm == null || jsonForm.trim().length()<=0){
            
        		jsonForm = "{}";
        	}
        	
        	StringEntity stringEntity = new StringEntity(jsonForm, ContentType.APPLICATION_JSON);
            
    		post.setEntity(stringEntity);
    		
			HttpResponse httpResponse = httpClient.execute(post);
			
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				
				throw new RuntimeException("Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			}
			
			HttpEntity httpEntity = httpResponse.getEntity();  

			result = EntityUtils.toString(httpEntity);
			
		} catch (Exception e) {

			throw e;
		}
    	
    	return result;
    }
}
