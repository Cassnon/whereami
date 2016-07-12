package org.mtrec.wheramitools.dao.nginx;

import java.io.ByteArrayOutputStream;  
import java.io.InputStream;  

import javax.servlet.http.HttpSession;

import org.mtrec.wheramitools.model.nginx.AddNnewImg;
import org.mtrec.wheramitools.model.nginx.NnewArea;
import org.mtrec.wheramitools.model.nginx.NnewBuilding;
import org.mtrec.wheramitools.model.nginx.Nnewsite;
import org.springframework.stereotype.Repository;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@Repository
public class NFacilityDao{

	public NFacilityDao() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    String accessKeyID = "AKIAITXC4PMVIRMDWBHQ";  
    String secretKey = "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";  
    String bucketName = "mtrec.lbs.resources"; 
    

    public void getFileFromS3(String path,HttpSession session){
    	
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyID, secretKey);
        AmazonS3 S3Client = new AmazonS3Client(credentials);
        S3Object object = S3Client.getObject(new GetObjectRequest(bucketName, session.getAttribute("site").toString()+"/"+path));
    	
    }

	
	public Nnewsite nloadSite(HttpSession session){
		HttpClient httpclient = new HttpClient();
		String url = session.getAttribute("url").toString()+"getSiteInfo/"+session.getAttribute("id");
		Nnewsite[] sitetable = httpclient.doGetServer_withclass(session, url, Nnewsite[].class);
		if (sitetable == null)
			return null;
		else
			return sitetable[0];
	}

	
	public NnewBuilding ngetBuildNameByAreaid(String areaid,HttpSession session){
		String url = session.getAttribute("url").toString()+"getAllBuildings/"+session.getAttribute("id");
		NnewBuilding[] buildingtable = HttpClient.doGetServer_withclass(session, url, NnewBuilding[].class);
		NnewBuilding resBuilding = null;
		for(int i= 0;i<buildingtable.length;i++){
			String root1[];
			root1 = buildingtable[i].getAreas().split(",");
			for(int j = 0;j<root1.length;j++){
				if(areaid.equals(root1[j])){
					resBuilding = buildingtable[i];
					return resBuilding;
				}
			}
		}
		return resBuilding;	
	}
	
	public NnewArea[] nloadAreaList(HttpSession session){

		String url = session.getAttribute("url").toString()+"getAllAreas/"+session.getAttribute("id");
		NnewArea[] areatable = HttpClient.doGetServer_withclass(session,url,NnewArea[].class);
		return areatable;
	}
	
	public NnewBuilding[] nloadNnewBuilding(HttpSession session){
        String url = session.getAttribute("url").toString()+"getAllBuildings/"+session.getAttribute("id");
		NnewBuilding[] buildingtable = HttpClient.doGetServer_withclass(session, url, NnewBuilding[].class);
        if (buildingtable == null)
        	return null;
        else
        	return buildingtable;
	}
	
	public NnewArea ngetAreaById(String string,HttpSession session){
		String url = session.getAttribute("url").toString()+"getAllareas/"+session.getAttribute("id");
		NnewArea[] area = HttpClient.doGetServer_withclass(session, url, NnewArea[].class);
        NnewArea a = new NnewArea();
		for(int i=0; i< area.length; i++){
			if (area[i].get_id().equals(string)){
				a = area[i];
				break;
			}
		}
		return a;
	}
	
	public String ngetImgidByArea_server (String areaId,HttpSession session){
		
		String urlReq = session.getAttribute("url")+"getImages/areas/"+areaId;
		String result= HttpClient.doGetServer_withoutclass(session,urlReq);

		return result;
	}
	
	public String ngetImgidBySite_server(HttpSession session){
		
		String urlReq = session.getAttribute("url").toString()+"getImages/sites/"+session.getAttribute("id");
		String result= HttpClient.doGetServer_withoutclass(session,urlReq);

		return result;
	}
	public String ngetImgidByBuilding_server (String BuildingId,HttpSession session){
		
		String urlReq = session.getAttribute("url").toString()+"getImages/buildings/"+BuildingId;
		String result= HttpClient.doGetServer_withoutclass(session,urlReq);

		return result;
	}
	
	public String trynsaveImgwithT(AddNnewImg img, byte[] imgbyte,HttpSession session){
		String uploadresult = "";
		String imgtabelresult = "";
		img.setAuth(session.getAttribute("auth").toString());
		//String url= "https://143.89.144.46/svnsmartwifi/branch/Demo/index.php/clientAPI/image/filename/";
		String url= session.getAttribute("url").toString()+"image/filename/";
		String temp = url +img.getTag();
		try {
			
			uploadresult = HttpClient.dotryPostImg(temp, imgbyte,session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		String jString = "";
        Object imgObject = img;
        try {
			jString  = mapper.writeValueAsString(imgObject);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(uploadresult.length()>0){
        	
        	//imgtabelresult = HttpClient.doPost_Server(urlReq, jString)
        	//String urlreq = "https://143.89.144.46/svnsmartwifi/branch/Demo/clientAPI/saveInfo/"+session.getAttribute("site")+"/imgs";
        	String urlreq = session.getAttribute("url").toString()+"saveInfo/"+session.getAttribute("site")+"/imgs";
        	imgtabelresult = HttpClient.doPost_AServer(urlreq, jString);
        }
		
        return imgtabelresult;
	}
	

	
	public String changeurl(String fakeurl){
		int j=0;
		String url="";
		String[] tempurl;
		tempurl =fakeurl.split("/");
		for (j= 0; j< tempurl.length-1; j++){
			if(j == 0)
				url=url+tempurl[0].substring(1, tempurl[0].length()-1)+"/";
			else 
				url=url+tempurl[j].substring(0, tempurl[j].length()-1)+"/";
				
		}
		url= url+tempurl[j].substring(0, tempurl[j].length()-1);
		return url;
	}

	
	public byte[] getImageFromNetByUrl(HttpSession session,String strUrl){ 
    	byte[] btImg = null;
    	btImg  = HttpClient.getImage_server(session,strUrl);
    	
        return btImg;  
    } 
	
    public byte[] getImageFromNetByUrl_server(HttpSession session,String strUrl){ 
    	byte[] btImg = null;
    	btImg  = HttpClient.getImage_server(session,strUrl);
    	
        return btImg;  
    }  
    
    public byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    }
	
}