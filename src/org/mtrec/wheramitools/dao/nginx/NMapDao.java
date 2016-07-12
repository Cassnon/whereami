package org.mtrec.wheramitools.dao.nginx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.mtrec.wheramitools.model.nginx.AddNBuilding;
import org.mtrec.wheramitools.model.nginx.Auth;
import org.mtrec.wheramitools.model.nginx.NnewArea;
import org.mtrec.wheramitools.model.nginx.NnewBuilding;
import org.mtrec.wheramitools.model.nginx.Nnewsite;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class NMapDao {
	public NMapDao() {
		super();
	}

	public String saveBuilding(NnewBuilding building,HttpSession session){
		AddNBuilding addbuilding = new AddNBuilding();
		String url = "";
		if(building.get_id() == ""){
			addbuilding.setAreas("[]");
			addbuilding.setAuth(building.getAuth());
			addbuilding.setGps(building.getGps());
			addbuilding.setImg(building.getImg());
			addbuilding.setName(building.getName());
			addbuilding.setVer(1);
			addbuilding.setNorth(building.getNorth());
			addbuilding.setScale(building.getScale());
			addbuilding.setIsoutdoor(building.getIsoutdoor());
			url = session.getAttribute("url").toString()+"/saveInfo/"+session.getAttribute("site").toString()+"/buildings/";
		}
		else{
			addbuilding.setAreas(building.getAreas());
			addbuilding.setAuth(building.getAuth());
			addbuilding.setGps(building.getGps());
			addbuilding.setImg(building.getImg());
			addbuilding.setName(building.getName());
			addbuilding.setVer(building.getVer()+1);
			addbuilding.setNorth(building.getNorth());
			addbuilding.setScale(building.getScale());
			addbuilding.setIsoutdoor(building.getIsoutdoor());
			url = session.getAttribute("url").toString()+"/saveInfo/"+session.getAttribute("site").toString()+"/buildings/"+building.get_id();
		}
		HttpClient httpClient = new HttpClient();
		ObjectMapper mapper = new ObjectMapper();
		String jString = "";
        try {
			jString  = mapper.writeValueAsString(addbuilding);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        String result = HttpClient.doPost_Server_save(url, jString);
        return result;

	}
	
	public void saveSite(Nnewsite site,HttpSession session){

		HttpClient httpClient = new HttpClient();
		ObjectMapper mapper = new ObjectMapper();
		String jString = "";
        try {
			jString  = mapper.writeValueAsString(site);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String url = session.getAttribute("url").toString()+"saveInfo/"+session.getAttribute("site").toString()+"/sites/"+session.getAttribute("id");
        String result = HttpClient.doPost_Server_save(url, jString);
	}
	
	public void insertSite(Nnewsite site,HttpSession session){

		HttpClient httpClient = new HttpClient();
		ObjectMapper mapper = new ObjectMapper();
		String jString = "";
        try {
			jString  = mapper.writeValueAsString(site);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String url = session.getAttribute("url").toString()+"saveInfo/"+session.getAttribute("site").toString()+"/sites/";
        String result = HttpClient.doPost_Server_save(url, jString);
	}
	
	public String saveArea(NnewArea area,HttpSession session){
		String url = "";
		NnewArea toSaveArea=new NnewArea();
		toSaveArea.set_id(area.get_id());
		toSaveArea.setAltitude(area.getAltitude());
		toSaveArea.setImgid(area.getImgid());
		toSaveArea.setName(area.getName());
		toSaveArea.setSwregions(area.getSwregions());
		toSaveArea.setVer(area.getVer());
		toSaveArea.setImg_center(area.getImg_center());
		toSaveArea.setAuth(area.getAuth());
		HttpClient httpClient = new HttpClient();
		ObjectMapper mapper = new ObjectMapper();
		String jString = "";
        try {
			jString  = mapper.writeValueAsString(toSaveArea);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(area.get_id().equals(""))
        	url = session.getAttribute("url").toString()+"/saveInfo/"+session.getAttribute("site").toString()+"/areas/";
        else
        	url = session.getAttribute("url").toString()+"/saveInfo/"+session.getAttribute("site").toString()+"/areas/"+area.get_id();
        String result = HttpClient.doPost_Server_save(url, jString);
        return result;
	}
	
	public String delBuildingById(String buildingid,HttpSession session) {
		
		HttpClient http = new HttpClient();
		Auth auth = new Auth();
		auth.setAuth(session.getAttribute("auth").toString());
		String jString = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			jString  = mapper.writeValueAsString(auth);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = session.getAttribute("url").toString()+"/delete/buildings/"+buildingid;
        String delresult = HttpClient.doPost_Server_save(url, jString);
		return delresult;
	}
	
	
	public String delAreaById(String areaid,HttpSession session) {
		
		HttpClient http = new HttpClient();
		Auth auth = new Auth();
		auth.setAuth(session.getAttribute("auth").toString());
		String jString = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			jString  = mapper.writeValueAsString(auth);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url =session.getAttribute("url").toString()+"delete/areas/"+areaid;
		String delresult = HttpClient.doPost_Server_save(url, jString);
		return delresult;
		
	}
	
	public String delImgById(String imgid,HttpSession session) {
		
		HttpClient http = new HttpClient();
		Auth auth = new Auth();
		auth.setAuth(session.getAttribute("auth").toString());
		String jString = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			jString  = mapper.writeValueAsString(auth);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = session.getAttribute("url").toString()+"/delete/imgs/"+imgid;
        String delresult = HttpClient.doPost_Server_save(url, jString);
		return delresult;
		
	}
	public NnewBuilding ngetBuildByname(String buildString,HttpSession session){
		NnewBuilding resulBuilding = new NnewBuilding();
		HttpClient httpClient = new HttpClient();
		String url = session.getAttribute("url").toString()+"/getAllBuildings/"+session.getAttribute("id");
		NnewBuilding[] buildings = HttpClient.doGetServer_withclass(session, url, NnewBuilding[].class);

		for(int i =0;i<buildings.length;i++){
			 
	        String name = buildings[i].getName();
			
			ObjectMapper mapper = new ObjectMapper(); 

		    JsonNode root = null;
			try {
				root = mapper.readTree(name);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		    JsonNode data = root.get(0).path("en");
		    
		    String temp = "\""+buildString+"\"";
		    
			if(temp.equals(data.toString())){
				resulBuilding = buildings[i];
				return resulBuilding;
			}
		}
		return resulBuilding;
	}
	
	public NnewBuilding ngetBuildByid(String id,HttpSession session){
		NnewBuilding resulBuilding = new NnewBuilding();
		HttpClient httpClient = new HttpClient();
		String url = session.getAttribute("url").toString()+"/getAllBuildings/"+session.getAttribute("id");
		NnewBuilding[] buildings = HttpClient.doGetServer_withclass(session, url, NnewBuilding[].class);
		for(int i =0;i<buildings.length;i++){
			 
	        String id1 = buildings[i].get_id();	    
			if(id1.equals(id)){
				resulBuilding = buildings[i];
				return resulBuilding;
			}
		}
		return resulBuilding;
	}
	
	public List<Integer> toAreaList(String areastring) {
		
		List<Integer> arealist = new ArrayList<Integer>();
		ObjectMapper mapper = new ObjectMapper(); 
		
	    JsonNode root = null;
		try {
			root = mapper.readTree(areastring);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		for(int a= 0;a<root.size();a++){
			arealist.add(Integer.parseInt(root.get(a).toString()));
		}
				
		return arealist;
	}
	
	public NnewArea ngetAreaById(String id,HttpSession session) {
		NnewArea resultArea = null;
		String url = session.getAttribute("url").toString()+"/getAllAreas/"+session.getAttribute("id");
		NnewArea[] area = HttpClient.doGetServer_withclass(session, url, NnewArea[].class);
		for(int i=0;i< area.length; i++){
			if(area[i].get_id().equals(id)){
				resultArea = area[i];
				break;
			}
		}
		return resultArea;
	}
	
	public NnewArea getAreaIdByname(String Buildingname,String floorid,HttpSession session){
		
		NnewArea area = new NnewArea();
		
		String url = session.getAttribute("url").toString()+"/getAllAreas/"+session.getAttribute("id");
		NnewArea[] areaidList = HttpClient.doGetServer_withclass(session,url,NnewArea[].class);
		
		
		for(int i=0;i<areaidList.length;i++){
			
			String areaid = areaidList[i].get_id();
			if(areaid.equals(floorid))
			{
				area = areaidList[i];
				break;
			}
		}
		return area;
	}
	
}