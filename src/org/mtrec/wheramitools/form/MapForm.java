package org.mtrec.wheramitools.form;

import java.util.Date;

import org.mtrec.wheramitools.model.Area;
import org.mtrec.wheramitools.model.nginx.NArea;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class MapForm extends NArea{

	@Transient
	private String building;	
	@Transient
	private String floor;
	@Transient 
	private CommonsMultipartFile uploadmap;
	@Transient 
	private CommonsMultipartFile uploaddeclare;
	public CommonsMultipartFile getUploaddeclare() {
		return uploaddeclare;
	}
	public void setUploaddeclare(CommonsMultipartFile uploaddeclare) {
		this.uploaddeclare = uploaddeclare;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public CommonsMultipartFile getUploadmap() {
		return uploadmap;
	}
	public void setUploadmap(CommonsMultipartFile uploadmap) {
		this.uploadmap = uploadmap;
	}



}
