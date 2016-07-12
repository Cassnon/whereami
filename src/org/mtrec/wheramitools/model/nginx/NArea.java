package org.mtrec.wheramitools.model.nginx;

public class NArea {
	private Integer _id;
	private Integer ver;
	private Integer imgId;
	private String name;
	private String img_center;
	private String swRegions;
	private double altitude;
	private String auth;
//	= "a43y[mLrLL4{j^q";
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getImgId() {
		return imgId;
	}
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}
	public String getSwRegions() {
		return swRegions;
	}
	public void setSwRegions(String swRegions) {
		this.swRegions = swRegions;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	
	public String getImg_center() {
		return img_center;
	}
	public void setImg_center(String img_center) {
		this.img_center = img_center;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public NArea(Integer _id, Integer ver, Integer imgId, String name,
			String img_center, String swRegions, double altitude, String auth) {
		super();
		this._id = _id;
		this.ver = ver;
		this.imgId = imgId;
		this.name = name;
		this.img_center = img_center;
		this.swRegions = swRegions;
		this.altitude = altitude;
		this.auth = auth;
	}
	public NArea() {
		super();
		// TODO Auto-generated constructor stub
	}



	

}
