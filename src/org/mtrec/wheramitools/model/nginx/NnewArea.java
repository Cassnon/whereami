package org.mtrec.wheramitools.model.nginx;

public class NnewArea {
	private String _id;
	private Integer ver;
	private String imgid;
	private String name;
	private String img_center;
	private String swregions;
	private double altitude;
	private String auth;
//	= "a43y[mLrLL4{j^q";
	
	public NnewArea(String _id, Integer ver, String imgId, String name,
			String img_center, String swRegions, double altitude, String auth) {
		super();
		this._id = _id;
		this.ver = ver;
		this.imgid = imgId;
		this.name = name;
		this.img_center = img_center;
		this.swregions = swRegions;
		this.altitude = altitude;
		this.auth = auth;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getImgid() {
		return imgid;
	}
	public void setImgid(String imgid) {
		this.imgid = imgid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg_center() {
		return img_center;
	}
	public void setImg_center(String img_center) {
		this.img_center = img_center;
	}
	public String getSwregions() {
		return swregions;
	}
	public void setSwregions(String swregions) {
		this.swregions = swregions;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public NnewArea() {
		super();
		// TODO Auto-generated constructor stub
	}



	

}
