package org.mtrec.wheramitools.dao.nginx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.apache.http.client.methods.HttpPost;

@Repository
public class NWFpointsDao {
	
	public NWFpointsDao() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	@Value(value="${uploadzip}") 
	private String uploadzipurl;


//	@Autowired
//	private NFacilityDao facDao;

	
	public void uploadzip(String tag, byte[] zipbyte, String filename,String contentType){
		
		HttpClient httpClient = new HttpClient();
		
		HttpPost post = new HttpPost(uploadzipurl+tag);
		
		
//		HttpClient httpClient = new HttpClient();
//		
//		String temp= uploadzipurl+tag;
//		
//		String fileresult = httpClient.doPostImg(temp, zipbyte,filename,contentType);
		
	}
	
	
}