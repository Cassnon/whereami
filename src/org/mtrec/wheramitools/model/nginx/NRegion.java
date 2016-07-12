package org.mtrec.wheramitools.model.nginx;

public class NRegion {
	
	private Integer ver;
	private String _id;
	private String areaid;
	private Integer polyid;
	private String vertex;
	private String auth;
//	= "a43y[mLrLL4{j^q";
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaId(String areaId) {
		this.areaid = areaId;
	}
	public Integer getpolyid() {
		return polyid;
	}
	public void setpolyid(Integer polyid) {
		this.polyid = polyid;
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
	public NRegion(Integer ver, String _id, String areaid, Integer polyid,
			String vertex, String auth) {
		super();
		this.ver = ver;
		this._id = _id;
		this.areaid = areaid;
		this.polyid = polyid;
		this.vertex = vertex;
		this.auth = auth;
	}
	public NRegion() {
		super();
		// TODO Auto-generated constructor stub
	}
}
