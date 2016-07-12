package org.mtrec.wheramitools.model.nginx;

public class AddNConnector {
	private Integer ver;
	private String areaId;
	private String pts;
	private String regions;
	private String id;
	//ifPath
	private Integer type;
	private String auth;
//	= "a43y[mLrLL4{j^q";
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getPts() {
		return pts;
	}
	public void setPts(String pts) {
		this.pts = pts;
	}
	public String getRegions() {
		return regions;
	}
	public void setRegions(String regions) {
		this.regions = regions;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public AddNConnector(Integer ver,String areaId, String pts,
			String regions, Integer type, String auth,String id) {
		super();
		this.ver = ver;
		this.areaId = areaId;
		this.pts = pts;
		this.regions = regions;
		this.type = type;
		this.auth = auth;
		this.id = id;
	}
	public AddNConnector() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}
