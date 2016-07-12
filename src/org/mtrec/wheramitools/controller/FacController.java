package org.mtrec.wheramitools.controller;

import javax.servlet.http.HttpSession;
import org.mtrec.wheramitools.dao.nginx.NFacilityDao;
import org.mtrec.wheramitools.dao.nginx.NMapDao;
import org.mtrec.wheramitools.model.nginx.NnewArea;
import org.mtrec.wheramitools.model.nginx.NnewBuilding;
import org.mtrec.wheramitools.model.nginx.NImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import sun.misc.BASE64Encoder;

@Controller	
public class FacController {
	

	@Autowired
	private NFacilityDao nfacilityDao;
	@Autowired
	private NMapDao nMapDao;
	
	@RequestMapping(value="/loadAreaList")
	public @ResponseBody NnewArea[] loadAreaList (HttpSession session){
		
		NnewArea[] result = this.nfacilityDao.nloadAreaList(session);
			
		return result;

	}
	
	@RequestMapping(value="/loadBuilding")
	public @ResponseBody NnewBuilding[] loadBuilding(HttpSession session){
		
		NnewBuilding[] result = this.nfacilityDao.nloadNnewBuilding(session);
		if (result == null)
			return new NnewBuilding[0];
		else
			return result;

	}
			
	@RequestMapping(value="/showmap")
	public @ResponseBody String showmap(@RequestBody String areaid,HttpSession session){
				
		NImg[] result= null;
		String url="";
		
		//去掉转义符号并且去掉双引号
		String fakeurl = this.nfacilityDao.ngetImgidByArea_server(areaid,session);
		fakeurl = fakeurl.replace("\\", "");
		url = fakeurl.substring(1,fakeurl.length()-1);
		
		
		byte[] btImg = this.nfacilityDao.getImageFromNetByUrl_server(session,url);
		
        BASE64Encoder encode = new BASE64Encoder();
        String base64 = encode.encode(btImg);
        
		return base64;
        
	}
	
	@RequestMapping(value="/showmap_building")
	public @ResponseBody String showmap_building(@RequestBody String buildingid,HttpSession session){
				
		NImg[] result= null;
		String url="";
		//获得对应areaid的地图的url 带有转义符号
		String fakeurl = this.nfacilityDao.ngetImgidByBuilding_server(buildingid,session);
		fakeurl = fakeurl.replace("\\", "");
		url = fakeurl.substring(1,fakeurl.length()-1);
		
		byte[] btImg = this.nfacilityDao.getImageFromNetByUrl_server(session,url);
		
        BASE64Encoder encode = new BASE64Encoder();
        String base64 = encode.encode(btImg);
        
		return base64;
        
	}
    
	@RequestMapping(value="/showmap_site")
	public @ResponseBody String showmap_site(HttpSession session){
				
		NImg[] result= null;
		String url="";
		
		//获得对应areaid的地图的url 带有转义符号
		String fakeurl = this.nfacilityDao.ngetImgidBySite_server(session);
		fakeurl = fakeurl.replace("\\", "");
		url = fakeurl.substring(1,fakeurl.length()-1);
		
		byte[] btImg = this.nfacilityDao.getImageFromNetByUrl_server(session,url);
		
        BASE64Encoder encode = new BASE64Encoder();
        String base64 = encode.encode(btImg);
        
		return base64;
        
	}
	
}