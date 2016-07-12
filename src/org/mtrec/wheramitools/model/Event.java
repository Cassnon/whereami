package org.mtrec.wheramitools.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="event_tbl")
public class Event {
	private int areaId;
	private List<Integer> location;
	private String title;
	private String address;;
	private String description;
	private String stime;
	private String etime;
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public List<Integer> getLocation() {
		return location;
	}
	public void setLocation(List<Integer> location) {
		this.location = location;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public Event(int areaId, List<Integer> location, String title,
			String address, String description, String stime, String etime) {
		super();
		this.areaId = areaId;
		this.location = location;
		this.title = title;
		this.address = address;
		this.description = description;
		this.stime = stime;
		this.etime = etime;
	}
	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}



}
