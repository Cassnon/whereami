package org.mtrec.wheramitools.form;

public class SaveAreaForm {
	String _id;
	String building;
	String chn_floor;
	Double altitude;
	String swRegions;
	String img_center;
	String img_amap_text;
	String name;
	String imgid;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getChn_floor() {
		return chn_floor;
	}
	public void setChn_floor(String chn_floor) {
		this.chn_floor = chn_floor;
	}
	public Double getAltitude() {
		return altitude;
	}
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}
	public String getSwRegions() {
		return swRegions;
	}
	public void setSwRegions(String swRegions) {
		this.swRegions = swRegions;
	}
	public String getImg_center() {
		return img_center;
	}
	public void setImg_center(String img_center) {
		this.img_center = img_center;
	}
	public String getImg_amap_text() {
		return img_amap_text;
	}
	public void setImg_amap_text(String img_amap_text) {
		this.img_amap_text = img_amap_text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgid() {
		return imgid;
	}
	public void setImgid(String imgid) {
		this.imgid = imgid;
	}
	public SaveAreaForm(String _id, String building, String chn_floor, Double altitude, String swRegions, String img_center,
			String img_amap_text, String name,String imgid) {
		super();
		this._id = _id;
		this.building = building;
		this.chn_floor = chn_floor;
		this.altitude = altitude;
		this.swRegions = swRegions;
		this.img_center = img_center;
		this.img_amap_text = img_amap_text;
		this.name = name;
		this.imgid = imgid;
	}
	public SaveAreaForm() {
		super();
		// TODO Auto-generated constructor stub
	}
}
