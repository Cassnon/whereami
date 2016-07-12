package org.mtrec.wheramitools.model.nginx;

public class NConnector {
	private Integer ver;
	private String _id;
	private String Areaid;
	private String pts;
	private String regions;
	//ifPath
	private Integer type;
	private String auth;
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
		return Areaid;
	}
	public void setAreaid(String Areaid) {
		this.Areaid = Areaid;
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
	public NConnector(Integer ver, String _id, String Areaid, String pts,
			String regions, Integer type, String auth) {
		super();
		this.ver = ver;
		this._id = _id;
		this.Areaid = Areaid;
		this.pts = pts;
		this.regions = regions;
		this.type = type;
		this.auth = auth;
	}
	public NConnector() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "NConnector [ver=" + ver + ", _id=" + _id + ", Areaid=" + Areaid
				+ ", pts=" + pts + ", regions=" + regions + ", type=" + type
				+ ", auth=" + auth + "]";
	}
	

}
