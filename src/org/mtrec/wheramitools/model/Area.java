package org.mtrec.wheramitools.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="site_tbl")
public class Area {
	@Id
	private int _id;
	private String site;
	private String chn_site;
	private Date updateTs;
	private String floor;
	private String chn_floor;
	private String building;
	private String chn_building;
	private int imgId;
	
	
	
	public int get_id() {
		return _id;
	}



	public void set_id(int _id) {
		this._id = _id;
	}



	public String getSite() {
		return site;
	}



	public void setSite(String site) {
		this.site = site;
	}



	public String getChn_site() {
		return chn_site;
	}



	public void setChn_site(String chn_site) {
		this.chn_site = chn_site;
	}



	public Date getUpdateTs() {
		return updateTs;
	}



	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}



	public String getFloor() {
		return floor;
	}



	public void setFloor(String floor) {
		this.floor = floor;
	}



	public String getChn_floor() {
		return chn_floor;
	}



	public void setChn_floor(String chn_floor) {
		this.chn_floor = chn_floor;
	}



	public String getBuilding() {
		return building;
	}



	public void setBuilding(String building) {
		this.building = building;
	}



	public String getChn_building() {
		return chn_building;
	}



	public void setChn_building(String chn_building) {
		this.chn_building = chn_building;
	}



	public int getImgId() {
		return imgId;
	}



	public void setImgId(int imgId) {
		this.imgId = imgId;
	}



	public Area(int _id, String site, String chn_site, Date updateTs,
			String floor, String chn_floor, String building,
			String chn_building, int imgId) {
		super();
		this._id = _id;
		this.site = site;
		this.chn_site = chn_site;
		this.updateTs = updateTs;
		this.floor = floor;
		this.chn_floor = chn_floor;
		this.building = building;
		this.chn_building = chn_building;
		this.imgId = imgId;
	}



	public Area() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
