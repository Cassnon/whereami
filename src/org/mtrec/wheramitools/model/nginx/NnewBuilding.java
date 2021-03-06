package org.mtrec.wheramitools.model.nginx;

public class NnewBuilding {
	private String _id;
	private Integer ver;
	private String img;
	private String name;
	private String gps;
	private String areas;
	private double scale;
	private double north;
	private String auth;
	private Integer isoutdoor;

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
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
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
	public NnewBuilding(String _id, Integer ver, String img, String name, String gps, String areas, double scale,
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
	public NnewBuilding() {
		super();
		// TODO Auto-generated constructor stub
	}
}
