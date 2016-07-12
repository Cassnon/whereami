package org.mtrec.wheramitools.controller;
import javax.servlet.http.HttpSession;
import org.mtrec.wheramitools.dao.nginx.NFacilityDao;
import org.mtrec.wheramitools.dao.nginx.NMapDao;
import org.mtrec.wheramitools.form.MapForm;
import org.mtrec.wheramitools.form.SaveAreaForm;
import org.mtrec.wheramitools.form.SaveAreaForm_p;
import org.mtrec.wheramitools.model.nginx.AddNnewImg;
import org.mtrec.wheramitools.model.nginx.NnewArea;
import org.mtrec.wheramitools.model.nginx.NnewBuilding;
import org.mtrec.wheramitools.model.nginx.Nnewsite;
import org.mtrec.wheramitools.model.nginx.NuploadBuilding;
import org.mtrec.wheramitools.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Controller	
public class MapController {
	@Autowired
	private NMapDao nmapDao;
	@Autowired
	private NFacilityDao nfacilityDao;
	
	@RequestMapping(value="/savebuilding")
	@ResponseBody 
	public String savebuilding(@RequestBody NuploadBuilding building,HttpSession session){
		
		NnewBuilding newbuilding = new NnewBuilding();
		String buildid = "";
		if(session.getAttribute("auth")==null){
			return "";
		}
		if(building.get_id().equals("")){
			newbuilding.set_id("");
			newbuilding.setImg(building.getImg());
			newbuilding.setAuth(session.getAttribute("auth").toString());
			newbuilding.setVer(1);
			newbuilding.setAreas("");
			newbuilding.setGps(building.getGps());
			newbuilding.setName(building.getName());
			newbuilding.setNorth(building.getNorth());
			newbuilding.setScale(building.getScale());
			if(building.getIfoutbuilding().equals("yes")){
				newbuilding.setIsoutdoor(1);
			}
			else 
				newbuilding.setIsoutdoor(0);
			buildid = this.nmapDao.saveBuilding(newbuilding, session);
			Nnewsite newsite =this.nfacilityDao.nloadSite(session);
			buildid = buildid.substring(1, buildid.length()-1);
			String a = newsite.getBuildings();
			if(newsite.getBuildings().equals(""))
				newsite.setBuildings(session.getAttribute("site")+"_"+buildid);
			else
				newsite.setBuildings(newsite.getBuildings()+","+session.getAttribute("site")+"_"+buildid);
			if(building.getIfoutbuilding().equals("yes")){
				if(newsite.getOutdoorbuildingid().equals("[]"))
					newsite.setOutdoorbuildingid(session.getAttribute("site")+"_"+buildid);
				else
					newsite.setOutdoorbuildingid(newsite.getOutdoorbuildingid()+","+session.getAttribute("site")+"_"+buildid);
			}
			newsite.setAuth(session.getAttribute("auth").toString());
			this.nmapDao.saveSite(newsite, session);
		}
		else{
			NnewBuilding newbuilding1 = this.nmapDao.ngetBuildByid(building.get_id(), session);
			if(!building.getImg().equals(""))
				newbuilding1.setImg(building.getImg());
			newbuilding1.setAuth(session.getAttribute("auth").toString());
			newbuilding1.setGps(building.getGps());
			newbuilding1.setName(building.getName());
			newbuilding1.setNorth(building.getNorth());
			newbuilding1.setScale(building.getScale());
			newbuilding1.setVer(newbuilding1.getVer()+1);
			newbuilding1.setIsoutdoor(newbuilding1.getIsoutdoor());
			buildid = this.nmapDao.saveBuilding(newbuilding1, session);
		}
		
		return buildid;

	}
	
	
	@RequestMapping(value="/savesite")
	@ResponseBody 
	public JsonResult savesite(@RequestBody Nnewsite site,HttpSession session){
		
		JsonResult result = new JsonResult();	
		if(session.getAttribute("auth")==null){
			
			result.setIsSuccess(0);
			return result;
			
		}
		Nnewsite newsite =this.nfacilityDao.nloadSite(session);
		if(newsite == null){
			//执行插入
			newsite = new Nnewsite();
			newsite.set_id(Integer.parseInt(session.getAttribute("id").toString()));
			newsite.setImg(site.getImg());
			newsite.setApps(site.getApps());
			newsite.setLangs(site.getLangs());
			newsite.setName(site.getName());
			newsite.setAuth(session.getAttribute("auth").toString());
			newsite.setVer(1);
			newsite.setAreas("");
			newsite.setBuildings("");
			this.nmapDao.insertSite(newsite, session);
		}
		else{
			//执行更新（有图片更新或者非图片更新）
			if (!site.getImg().equals("")){
				newsite.setImg(site.getImg());
			}
			newsite.setApps(site.getApps());
			newsite.setLangs(site.getLangs());
			newsite.setName(site.getName());
			newsite.setAuth(session.getAttribute("auth").toString());
			this.nmapDao.saveSite(newsite, session);
		}

		result.setIsSuccess(1);		
		return result;

	}
	
	
	@RequestMapping(value="/savearea")
	@ResponseBody 
	public JsonResult savearea(@RequestBody SaveAreaForm saveareaform,HttpSession session){
        JsonResult result = new JsonResult();
		if(session.getAttribute("auth")==null){
			result.setIsSuccess(0);
			return result;
		}
		else{
			if(saveareaform.get_id().equals("")){
				NnewArea area = new NnewArea();
				area.set_id("");
				area.setImg_center(saveareaform.getImg_center());
				area.setName(saveareaform.getName());
				area.setSwregions(saveareaform.getSwRegions());
				area.setAltitude(saveareaform.getAltitude());
				area.setVer(1);
				area.setAuth(session.getAttribute("auth").toString());
				area.setImgid(saveareaform.getImgid());
			    String areaid = this.nmapDao.saveArea(area, session);
			    
			    NnewBuilding building = this.nmapDao.ngetBuildByname(saveareaform.getBuilding(), session);
			    String buildingarea = building.getAreas();
			    if(buildingarea.equals("[]"))
			    	buildingarea = session.getAttribute("site")+"_"+areaid.substring(1, areaid.length()-1);
			    else
			    	buildingarea = buildingarea +","+session.getAttribute("site")+"_"+areaid.substring(1, areaid.length()-1);
			    building.setAreas(buildingarea);
			    building.setAuth(session.getAttribute("auth").toString());
			    String buildingid = this.nmapDao.saveBuilding(building, session);
			    if(!buildingid.equals(""))
			    	result.setIsSuccess(1);
			    else
			    	result.setIsSuccess(0);
			}
			else{
				NnewArea area= this.nmapDao.ngetAreaById(saveareaform.get_id(), session);
				if(!saveareaform.getImgid().equals(""))
					area.setImgid(saveareaform.getImgid());
				area.setImg_center(saveareaform.getImg_center());
				area.setName(saveareaform.getName());
				area.setSwregions(saveareaform.getSwRegions());
				NnewBuilding building = this.nmapDao.ngetBuildByname(saveareaform.getBuilding(), session);
				area.setAltitude((double)(Math.round(saveareaform.getAltitude()*building.getScale()*100)/100.0));
				area.setVer(area.getVer()+1);
				area.setAuth(session.getAttribute("auth").toString());
			    String areaid = this.nmapDao.saveArea(area, session);
			    if(!areaid.equals(""))
			    	result.setIsSuccess(1);
			    else
			    	result.setIsSuccess(0);
			}		
		}
		return result;
	}
	
	@RequestMapping(value="/saveaimg")
	@ResponseBody 
	public String saveaimg(SaveAreaForm_p saveareaform_p,HttpSession session){
        String saveimmgresult = "";
		if(session.getAttribute("auth")!=null){
			CommonsMultipartFile fileSrc = saveareaform_p.getImg_amap();
			byte[] bytes = fileSrc.getBytes();

			AddNnewImg img = new AddNnewImg();
					
			img.setImpath("tmp/"+fileSrc.getOriginalFilename());						
			img.setTag(fileSrc.getOriginalFilename());
			img.setVer(0);
			img.setType(fileSrc.getContentType().toString());

			if(bytes.length > 0){
				saveimmgresult = this.nfacilityDao.trynsaveImgwithT(img,bytes,session);
			}
			else{
				saveimmgresult = "";
			}
		}
		String img_name = session.getAttribute("site")+"_"+saveimmgresult.substring(1, saveimmgresult.length()-1);
		return img_name;
	}
		
	@RequestMapping(value="/loadSite")
	@ResponseBody 
	public Nnewsite loadSite(HttpSession session){
		Nnewsite result =this.nfacilityDao.nloadSite(session);
		if(result == null){
			Nnewsite a = new Nnewsite();
			a.set_id(-1);
			return a;
		}
		else
			return result;
	}

	
	@RequestMapping(value="/getbuilding")
	@ResponseBody 
	public NnewBuilding getbuilding(@RequestBody String buildingname,HttpSession session){
		NnewBuilding result =this.nmapDao.ngetBuildByname(buildingname,session);
		return result;
	}
	
	@RequestMapping(value="/getarea")
	@ResponseBody 
	public NnewArea getarea(@RequestBody MapForm mapform,HttpSession session){
		NnewArea result =this.nmapDao.getAreaIdByname(mapform.getBuilding(),mapform.getFloor(),session);
		return result;
	}
	
	
	@RequestMapping(value="/delBuilding")
	@ResponseBody 
	public JsonResult delBuilding(@RequestBody String buildingid,HttpSession session){
		JsonResult result = new JsonResult();	
		
 		if(session.getAttribute("auth")==null){
			
			result.setIsSuccess(0);
			return result;			
		}
 		else{
 			NnewBuilding building = this.nmapDao.ngetBuildByid(buildingid, session);
 			String[] areas =building.getAreas().split(",");
 			String isareadelete="";
 			for(int i = 0; i< areas.length; i++){
 				NnewArea area = new NnewArea();
 				if(!areas[i].equals("[]")){
 					area = this.nmapDao.ngetAreaById(areas[i], session);
 					isareadelete = this.nmapDao.delAreaById(areas[i], session);
	 				if(!isareadelete.equals("")){
	 					
	 					if(isareadelete.substring(1,isareadelete.length()-1).equals("Succeeded!")){
	 						String isdelimg = this.nmapDao.delImgById(area.getImgid(), session);
	 					}
	 				}
 				}
 			}
 			Nnewsite newsite =this.nfacilityDao.nloadSite(session);
			String buildings[] = newsite.getBuildings().split(",");
			String buildid = "";
			String outbuilding = newsite.getOutdoorbuildingid();
			String outbuildings[] = null; 
			if(outbuilding == null){
				outbuildings = new String[0];
			}
			else{
				outbuildings = outbuilding.split(",");
			}
			String outbuildid = "";

			for(int j=0; j< buildings.length; j++){
				if(!buildings[j].equals(buildingid)){
					if (buildid.equals("")){
						buildid = buildid + buildings[j];
					}
					else{
						buildid = buildid +"," + buildings[j];
					}
				}
			}
			if(buildid.equals(""))
				buildid = "";
			
				for(int j=0; j< outbuildings.length; j++){
					if(!outbuildings[j].equals(buildingid)){
						if (outbuildid.equals("")){
							outbuildid = outbuildid + buildings[j];
						}
						else{
							outbuildid = outbuildid +"," + buildings[j];
						}
					}
				}
				
				if(outbuildid.equals(""))
					outbuildid = "[]";
				
				newsite.setBuildings(buildid);
				newsite.setOutdoorbuildingid(outbuildid);
				newsite.setAuth(session.getAttribute("auth").toString());
				this.nmapDao.saveSite(newsite, session);
	 			String isbuildingdelete = this.nmapDao.delBuildingById(buildingid, session);
	 			if(!isbuildingdelete.equals("")){
						
						if(isbuildingdelete.substring(1,isbuildingdelete.length()-1).equals("Succeeded!")){
							String isdelimg = this.nmapDao.delImgById(building.getImg(), session);
							result.setIsSuccess(1);
						}
	 			}
 		}
		return result;
	}
	
	
	@RequestMapping(value="/delArea")
	@ResponseBody 
	public JsonResult delArea(@RequestBody String areaid,HttpSession session){
		
		JsonResult result = new JsonResult();	
		
		if(session.getAttribute("auth")==null){
			
			result.setIsSuccess(0);
			return result;			
		}
		
		NnewArea area = new NnewArea();
		
		area = this.nfacilityDao.ngetAreaById(areaid, session);
		
		String isareadelete = this.nmapDao.delAreaById(areaid, session);
		if(!isareadelete.equals("")){
		
			if(isareadelete.substring(1,isareadelete.length()-1).equals("Succeeded!")){
				String isdelimg = this.nmapDao.delImgById(area.getImgid(), session);
				if(!isdelimg.equals("")){
					if(isdelimg.substring(1,isdelimg.length()-1).equals("Succeeded!")){
						NnewBuilding building = this.nfacilityDao.ngetBuildNameByAreaid(areaid,session);
						if(building != null){
							String[] area_id = building.getAreas().split(",");
							String areaString = "";
							for(int i = 0; i< area_id.length; i++){
								if(!area_id[i].equals(areaid)){
									if (areaString.equals("")){
										areaString = areaString +area_id[i];
									}
									else{
										areaString = areaString +"," +area_id[i];
									}
								}
							}
							if(areaString.equals("")) {
								areaString = "[]";
							}
							building.setAreas(areaString);
							building.setAuth(session.getAttribute("auth").toString());
							this.nmapDao.saveBuilding(building, session);
						}
					}
				}
			}
		}
		result.setIsSuccess(1);		
		return result;
	}
	
}