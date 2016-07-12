package org.mtrec.wheramitools.model.nginx;

public class NSite {
	
	private Integer ver;
	private String apps;
	private Integer _id;
	private String img;
	private String areas;
	private String langs;
	private String buildings;
	private String name;
	private String outdoorbuildingid;
	private String auth;
//	= "a43y[mLrLL4{j^q";
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getApps() {
		return apps;
	}
	public void setApps(String apps) {
		this.apps = apps;
	}
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getAreas() {
		return areas;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public String getLangs() {
		return langs;
	}
	public void setLangs(String langs) {
		this.langs = langs;
	}
	public String getBuildings() {
		return buildings;
	}
	public void setBuildings(String buildings) {
		this.buildings = buildings;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	public String getOutdoorBuildingId() {
		return outdoorbuildingid;
	}
	public void setOutdoorBuildingId(String outdoorbuildingid) {
		this.outdoorbuildingid = outdoorbuildingid;
	}
	public NSite(Integer ver, String apps, Integer _id, String img,
			String areas, String langs, String buildings, String name,
			String outdoorbuildingid, String auth) {
		super();
		this.ver = ver;
		this.apps = apps;
		this._id = _id;
		this.img = img;
		this.areas = areas;
		this.langs = langs;
		this.buildings = buildings;
		this.name = name;
		this.outdoorbuildingid = outdoorbuildingid;
		this.auth = auth;
	}
	public NSite() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	

}
