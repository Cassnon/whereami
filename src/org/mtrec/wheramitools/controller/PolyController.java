package org.mtrec.wheramitools.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.mtrec.wheramitools.dao.nginx.NFacilityDao;
import org.mtrec.wheramitools.dao.nginx.NPolygonDao;
import org.mtrec.wheramitools.form.PolygonForm;
import org.mtrec.wheramitools.form.PolygonVForm;
import org.mtrec.wheramitools.model.Region;
import org.mtrec.wheramitools.model.nginx.AddNRegion;
import org.mtrec.wheramitools.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller	
public class PolyController {
	

	@Autowired
	private NPolygonDao npolygonDao;
	@Autowired
	private NFacilityDao nfacDao;
	
	
	@RequestMapping(value="/loadPolygon")
	public @ResponseBody List loadPolygon(@RequestBody String areaid,HttpSession session){
		List result= null;
		result = this.npolygonDao.ngetPolyByAreaId(areaid,session);
		return result;
	}
		
	@RequestMapping(value="/loadconn")
	public @ResponseBody List loadconn(@RequestBody String areaid,HttpSession session){
		List result= null;
		result = this.npolygonDao.nloadconnList(areaid,session);			
		return result;
	}
	
	@RequestMapping(value="/getmaxregionid")
	public @ResponseBody String getmaxregionid(HttpSession session){
		String result;
		result = this.npolygonDao.ngetmaxregion(session);
		return result;
	}
    
	@RequestMapping(value="/getmaxconnectorid")
	public @ResponseBody String getmaxconnectorid(HttpSession session){
		String result;
		result = this.npolygonDao.ngetmaxconnector(session);
		return result;
	}
	
	@RequestMapping(value="/savepoly")
	public @ResponseBody JsonResult savepoly(@RequestBody PolygonForm polygonform,HttpSession session){
	
		JsonResult result = new JsonResult();
		int result1 = 0;
		
		if((session).getAttribute("auth")==null){
			
			result.setIsSuccess(0);
		}
		else{
			//将当前region的信息update到后台
			List<Region> regionlist = polygonform.getRegionList();
			List<AddNRegion> addnregion = new ArrayList<AddNRegion>();
			String areaid = regionlist.get(0).getAreaId(); 
			for(int i=0;i<regionlist.size();i++)
			{
				Region region = regionlist.get(i);
				AddNRegion toSaveRegion= new AddNRegion();
				//检验 如果上传的是空白的话 选择清空吧
			    String temp = "\""+region.getId()+"\"";
				if(!temp.equals("\"null\"")){
					String vertexString = region.getVertexes().toString();
					toSaveRegion.setId(region.getId());
					toSaveRegion.setAreaId(region.getAreaId());
					toSaveRegion.setPolyId(region.getPolyId());
					toSaveRegion.setVertex(vertexString);
					toSaveRegion.setVer(0);
					toSaveRegion.setAuth(session.getAttribute("auth").toString());
					addnregion.add(toSaveRegion);
				}
			}
			result1 = this.npolygonDao.nupdateRegion(session,addnregion,areaid);

			if (result1 ==1)
			result.setIsSuccess(1);
			else
			result.setIsSuccess(0);
		}
		return result;
	}
	
	
	@RequestMapping(value="/resort")
	public @ResponseBody List resort(@RequestBody PolygonVForm sa){
		
		List result= null;
		
		scan_anticlockwise(sa.getVertexes());
		
		result = sa.getVertexes();
			
		return result;

	}
	

	public static class Point{
		private Integer x;
		private Integer y;
		public Integer getX() {
			return x;
		}
		public void setX(Integer x) {
			this.x = x;
		}
		public Integer getY() {
			return y;
		}
		public void setY(Integer y) {
			this.y = y;
		}
		
	}
	
		
	double multiply(Point begPnt,Point endPnt,Point nextPnt)   
	{   
	    return((nextPnt.x-begPnt.x)*(endPnt.y-begPnt.y) - (endPnt.x-begPnt.x)*(nextPnt.y-begPnt.y));   
	} 
	double distance(Point pnt1, Point pnt2)  
	{  
	    return Math.sqrt( (pnt2.x-pnt1.x) * (pnt2.x-pnt1.x) + (pnt2.y-pnt1.y) * (pnt2.y-pnt1.y) );  
	}  
	

	//void scan_anticlockwise(POINT PointSet[],int n) 
	private void scan_anticlockwise(List<Integer> vertexes) 
	{ 
 
		
		List<Point> pointSet=new ArrayList<Point>();
		for(int i = 0;i<vertexes.size();i++)
		{
			if(i%2== 1)
			{
				Point point = new Point();
				point.setX(vertexes.get(i-1));
				point.setY(vertexes.get(i));
				pointSet.add(point);
			}
		}
	 int i,j,k=0; 
	 Point tmp; 
	
	 for(i=1;i<pointSet.size();i++) 
	  if ( pointSet.get(i).y<pointSet.get(k).y || (pointSet.get(i).y==pointSet.get(k).y) && (pointSet.get(i).x<pointSet.get(k).x) ) 
	   k=i; 
	 tmp=pointSet.get(0); 
	 pointSet.set(0, pointSet.get(k)); 
	 pointSet.set(k, tmp); 
	 for (i=1;i<pointSet.size()-1;i++) 
	 { 
	  k=i; 
	  for (j=i+1;j<pointSet.size();j++) 
	   if ( multiply(pointSet.get(j),pointSet.get(k),pointSet.get(0))>0 ||  
	    (multiply(pointSet.get(j),pointSet.get(k),pointSet.get(0))==0) && 
	    distance(pointSet.get(0),pointSet.get(j))<distance(pointSet.get(0),pointSet.get(k))
	      ) 
	    k=j; 
	  tmp=pointSet.get(i); 
	  pointSet.set(i, pointSet.get(k));
	  pointSet.set(k, tmp);
	 }
	 vertexes.clear();
	 for(int h= 0;h<pointSet.size();h++)
	 {
		 vertexes.add(pointSet.get(h).getX());
		 vertexes.add(pointSet.get(h).getY());
	 }

	}
		
}