package org.mtrec.wheramitools.dao.nginx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.mtrec.wheramitools.model.Connector;
import org.mtrec.wheramitools.model.Region;
import org.mtrec.wheramitools.model.nginx.AddNConnector;
import org.mtrec.wheramitools.model.nginx.AddNRegion;
import org.mtrec.wheramitools.model.nginx.NConnector;
import org.mtrec.wheramitools.model.nginx.NRegion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@Repository
public class NPolygonDao{

	public NPolygonDao() {
		super();
		// TODO Auto-generated constructor stub
	}


    //获得areaid 相对应的所有 region
	public List ngetPolyByAreaId (String areaId,HttpSession session){
		List result = new ArrayList<>();
		HttpClient httpClient = new HttpClient();
		String url = session.getAttribute("url").toString()+"details/"+session.getAttribute("site")+"/regions";
		NRegion[] reglist = httpClient.doGetServer_withclass(session,url,NRegion[].class);
		for(int i =0; i<reglist.length;i++){
			String temp = reglist[i].getAreaid();
			if(areaId.equals(temp)){
				result.add(reglist[i]);
			}			
		}
		return result;
	}
	
	//获得areaid 相对应的所有 area
	public List nloadconnList (String areaId,HttpSession session){
	//获得connectors里面的数据然后与areaid进行比较 获得相应的conn
		List result = new ArrayList<>();
		HttpClient httpClient = new HttpClient();
		String url = session.getAttribute("url").toString()+"/details/"+session.getAttribute("site")+"/connectors";
		NConnector[] connlist = httpClient.doGetServer_withclass(session,url,NConnector[].class);
		List resultFacilities = new ArrayList();
		for(int i =0; i<connlist.length;i++){
			String temp = connlist[i].getAreaid();
			if(areaId.equals(temp)){
				result.add(connlist[i]);
			}
		}
		return result;
	}
	
	//获得最大的region id
	public String ngetmaxregion(HttpSession session){
		String result ="";
		HttpClient httpClient = new HttpClient();
		String url = session.getAttribute("url")+"details/"+session.getAttribute("site")+"/regions";
		NRegion[] reglist = httpClient.doGetServer_withclass(session, url, NRegion[].class);
		String site = session.getAttribute("site").toString();
		int length = site.length();
		int temp = -1;
		if(reglist!=null){
			for(int i =0; i<reglist.length;i++){
				String test = reglist[i].get_id();
				test = test.substring(length+1);
				int temp1 = Integer.parseInt(test);
				if (temp1 > temp){
					temp = temp1;
					result = reglist[i].get_id();
				}
			}
		}
		else 
			result = session.getAttribute("site").toString()+"_-1";
		return result;
	}
	
	//获得最大的connector id
	public String ngetmaxconnector(HttpSession session){
		String result = "";
		HttpClient httpClient = new HttpClient();
		String url = session.getAttribute("url")+"details/"+session.getAttribute("site")+"/connectors";
		NConnector[] connlist = httpClient.doGetServer_withclass(session,url,NConnector[].class);
		String site = session.getAttribute("site").toString();
		int length = site.length();
		int temp = 0;
		if(connlist!=null){
			for(int i =0; i<connlist.length;i++){
				String test = connlist[i].get_id();
				test = test.substring(length+1);
				int temp1 = Integer.parseInt(test);
				if (temp1 > temp){
					temp = temp1;
					result = connlist[i].get_id();
				}
			}
		}
		return result;
	}
	
	//更新connector
	public Integer nupdateConn(HttpSession session,List<AddNConnector> conn){
		int result = 0;
		HttpClient httpClient = new HttpClient();
		ObjectMapper mapper = new ObjectMapper();
		String jString = "";
        Object facObject = new ObjectMapper();;
        try {
			jString  = mapper.writeValueAsString(conn);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String urlReq = session.getAttribute("url").toString()+"/modify/"+session.getAttribute("site")+"/connectors";
        result = httpClient.doPost_Server(urlReq,jString);
        return result;
	}
		
	//更新region
	public Integer nupdateRegion(HttpSession session,List<AddNRegion> region, String areaid){
		int result = 0;
		HttpClient httpClient = new HttpClient();
		ObjectMapper mapper = new ObjectMapper();
		String jString = "";
        Object facObject = new ObjectMapper();;
        try {
			jString  = mapper.writeValueAsString(region);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String urlReq = session.getAttribute("url").toString() + "/modify/"+session.getAttribute("site")+"/regions/"+areaid;
        result=httpClient.doPost_Server(urlReq,jString);
		return result;
	}

}