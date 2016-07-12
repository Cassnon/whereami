package org.mtrec.wheramitools.model.nginx;

public class NBuilding {
	private Integer _id;
	private Integer ver;
	private Integer img;
	private String name;
	private String gps;
	private String areas;
	private double scale;
	private double north;
	private String auth;
	private Integer isoutdoor;
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
	
	public Integer getImg() {
		return img;
	}
	public void setImg(Integer img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public String getAreas() {
		return areas;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		this.scale = scale;
	}
	public double getNorth() {
		return north;
	}
	public void setNorth(double north) {
		this.north = north;
	}
	
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public Integer getIsoutdoor() {
		return isoutdoor;
	}
	public void setIsoutdoor(Integer isoutdoor) {
		this.isoutdoor = isoutdoor;
	}
	public NBuilding(Integer _id, Integer ver, Integer img, String name, String gps, String areas, double scale,
			double north, String auth, Integer isoutdoor) {
		super();
		this._id = _id;
		this.ver = ver;
		this.img = img;
		this.name = name;
		this.gps = gps;
		this.areas = areas;
		this.scale = scale;
		this.north = north;
		this.auth = auth;
		this.isoutdoor = isoutdoor;
	}
	public NBuilding() {
		super();
		// TODO Auto-generated constructor stub
	}
}
