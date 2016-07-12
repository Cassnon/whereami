package org.mtrec.wheramitools.controller;

import java.io.File;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.mtrec.wheramitools.dao.nginx.NFacilityDao;
import org.mtrec.wheramitools.dao.nginx.NMapDao;
import org.mtrec.wheramitools.dao.nginx.NWFpointsDao;
import org.mtrec.wheramitools.form.PolygonForm;
import org.mtrec.wheramitools.form.PointForm;
import org.mtrec.wheramitools.model.Connector;
import org.mtrec.wheramitools.model.nginx.NPoints;
import org.mtrec.wheramitools.model.nginx.NWFpoints;
import org.mtrec.wheramitools.model.nginx.GridSize;
import org.mtrec.wheramitools.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpSession;

@Controller	
public class WFpointsController {
	@Autowired
	private NFacilityDao nfacilityDao;
	@Autowired
	private NWFpointsDao pointsDao;	
	@Autowired
	private NMapDao nmapDao;
	
	
	
	//save form information into database
	@RequestMapping(value="/showpoints")
	@ResponseBody
	public List<NWFpoints> showpoints(@RequestBody String url)
	{

		
		//download the zip file of points
		try {
			
			   File mapfile = new File("/home/gary/tmp/WheramiTools/map.jpg");
			   File txtfile = new File("/home/gary/tmp/WheramiTools/pts.txt");
		       File configfile = new File("/home/gary/tmp/WheramiTools/conf.ini");
		       if(mapfile.exists()) {
		    	   mapfile.delete();
		       }
		       if(txtfile.exists()) {
		    	   txtfile.delete();
		       }
		       if(configfile.exists()) {
		    	   configfile.delete();
		       }
		       
			   URL httpurl = new URL(url);
			   
			   URLConnection conn = httpurl.openConnection();  
			   
			   conn.connect();
			   
			   BufferedInputStream is = new BufferedInputStream(conn.getInputStream());
			   FileOutputStream fos = new FileOutputStream("/home/gary/tmp/WheramiTools/template.zip");
			   			   
			   byte[] b = new byte[120400];
			   
			   int l = 0;
			   
			   while((l = is.read(b))!= -1){
			        fos.write(b, 0, l);
			   }
			   
			   fos.close();
			   is.close();
			  
			   
			   
			} catch (Exception e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			}
		File file = new File("/home/gary/tmp/WheramiTools/template.zip");
		//unzip the zip file
		try {
			unZipFiles(file,"/home/gary/tmp/WheramiTools");
			file.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//read txt file as list<NWFpoints>
		List<NWFpoints> pointList = new ArrayList<NWFpoints>();
		String textpath = "/home/gary/tmp/WheramiTools/pts.txt";
		File filename = new File(textpath); 

        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
        	for(String line=br.readLine(); line != null && line!=""; line=br.readLine()) {

						String[] strings = line.split(",");
						NWFpoints temp = new  NWFpoints();
						temp.set_id(Integer.parseInt(strings[0]));
						temp.setX(Integer.parseInt(strings[1]));
						temp.setY(Integer.parseInt(strings[2]));
						pointList.add(temp);
			}
        	br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println(pointList);
		return pointList;
	}
	
    public static void unZipFiles(File zipFile,String descDir)throws IOException{  
        File pathFile = new File(descDir);  
        if(!pathFile.exists()){  
            pathFile.mkdirs();  
        }  
        ZipFile zip = new ZipFile(zipFile);  
        for(Enumeration entries = zip.entries();entries.hasMoreElements();){  
            ZipEntry entry = (ZipEntry)entries.nextElement();  
            String zipEntryName = entry.getName();  
            InputStream in = zip.getInputStream(entry);      
            
			BufferedInputStream is = new BufferedInputStream(in);
			
			File f = new File(descDir+File.separator+zipEntryName);
			
			if(!f.exists())
				f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			   
		   byte[] b = new byte[120400];
		   
		   int l = 0;
		   
		   while((l = is.read(b))!= -1){
		        fos.write(b, 0, l);
		   }
            in.close();  
            fos.close();  
            }  
       zip.close();
    }  
    
    public static void zipFiles(File[] srcfile, File zipfile) {  
        byte[] buf = new byte[102400];  
        try {  
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));  
            for (int i = 0; i < srcfile.length; i++) {  
                FileInputStream in = new FileInputStream(srcfile[i]);  
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));  
                int len;  
                while ((len = in.read(buf)) > 0) {  
                    out.write(buf, 0, len);  
                }  
                out.closeEntry();  
                in.close();  
            }  
            out.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    @RequestMapping(value="/genpoints")
    @ResponseBody
    public String genpoints(@RequestBody GridSize gridsize, HttpSession session) throws IOException {
    	String result = "";
    	String scriptPath = "/home/gary/tmp/survey.point.generator.py";
    	String[] cmd = new String[4];
    	cmd[0] = "python";
    	cmd[1] = scriptPath;
    	cmd[2] = gridsize.area;
    	cmd[3] = String.valueOf(gridsize.gridsize);
    	Runtime rt = Runtime.getRuntime();
    	Process pr = rt.exec(cmd);
    	BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
    	String line = "";
    	while((line = bfr.readLine()) != null) {
    		result = result + line + " " ;
    	}

    	return result;
    }
    
    @RequestMapping(value="/savepoints")
	@ResponseBody
	public JsonResult savepoly(@RequestBody PointForm pointform, HttpSession session) throws IOException {
    	
    	JsonResult result = new JsonResult();
    	String areaid = pointform.getAreaCode();
    	List<NWFpoints> pointslist = pointform.getPointsList();
    	
    	for(int i = 0;i<pointslist.size();i++){
    		pointslist.get(i).set_id(pointslist.get(i).get_id());
    	}
    	 
    	File writename = new File("/home/gary/tmp/WheramiTools/pts.txt");
    	BufferedWriter bw = new BufferedWriter(new FileWriter(writename));
    	for (int i = 0; i < pointslist.size(); i++) {
    		
    		bw.write(pointslist.get(i).get_id()+",");
    		bw.write(pointslist.get(i).getX()+",");
    		bw.write(pointslist.get(i).getY()+"\n");
    	}
    	bw.flush();
    	bw.close();
    			
    	File[] srcfile  = new File[3];
    	File zipFile = new File("/var/www/html/Demo/assets/dcPkgs/"+ pointform.getAreaCode() + ".zip");
    	File txtfile = new File("/home/gary/tmp/WheramiTools/pts.txt");
    	File mapFile = new File("/home/gary/tmp/WheramiTools/map.jpg");
    	File configfile = new File("/home/gary/tmp/WheramiTools/conf.ini");
    	if (!configfile.exists()){
    		configfile.createNewFile();
    		BufferedWriter configbw = new BufferedWriter(new FileWriter(configfile));
    		configbw.write("numSamples=15\n");
    		configbw.write("24Only=1\n");
    		configbw.flush();
    		configbw.close();
    	}
    	if(!mapFile.exists()){
    		String fakeurl = this.nfacilityDao.ngetImgidByArea_server(areaid,session);
    		fakeurl = fakeurl.replace("\\", "");
    		String url = fakeurl.substring(1,fakeurl.length()-1);
    		byte[] btImg = this.nfacilityDao.getImageFromNetByUrl_server(session,url);
    		FileOutputStream fos = new FileOutputStream(mapFile);
    		fos.write(btImg);
    		fos.close();
    	}
    	srcfile[0] = configfile;
    	srcfile[1] = mapFile;
    	srcfile[2] = txtfile;
    	zipFiles(srcfile,zipFile);
    	mapFile.delete();
    	txtfile.delete();
    	configfile.delete();
    	result.setIsSuccess(1);
		return result;
    	
    }
    
    public static byte[] zipBytes(String filename, byte[] input) throws IOException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	ZipOutputStream zos = new ZipOutputStream(baos);
    	ZipEntry entry = new ZipEntry(filename);
    	entry.setSize(input.length);
    	zos.putNextEntry(entry);
    	zos.write(input);
    	zos.closeEntry();
    	zos.close();
    	return baos.toByteArray();
    }

}