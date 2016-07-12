package org.mtrec.wheramitools.model.nginx;

public class AddNRegion {
	
	private String id;
	private Integer ver;
	private String areaId;
	private Integer polyId;
	private String vertex;
	private String auth;
//	= "a43y[mLrLL4{j^q";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public Integer getPolyId() {
		return polyId;
	}
	public void setPolyId(Integer polyId) {
		this.polyId = polyId;
	}
	public String getVertex() {
		return vertex;
	}
	public void setVertex(String vertex) {
		this.vertex = vertex;
	}
	
	
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public AddNRegion(Integer ver, String areaId, Integer polyId,
			String vertex, String auth) {
		super();
		this.ver = ver;
		this.areaId = areaId;
		this.polyId = polyId;
		this.vertex = vertex;
		this.auth = auth;
	}
	public AddNRegion() {
		super();
		// TODO Auto-generated constructor stub
	}
}
