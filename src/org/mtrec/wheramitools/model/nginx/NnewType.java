package org.mtrec.wheramitools.model.nginx;

public class NnewType {
	private Integer ver;
	private String _id;
	private String parentid;
	private Integer displogo;
	private String logoid;
	private String details;
//	private String auth;
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
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public Integer getDisplogo() {
		return displogo;
	}
	public void setDisplogo(Integer displogo) {
		this.displogo = displogo;
	}
	public String getLogoid() {
		return logoid;
	}
	public void setLogoid(String logoid) {
		this.logoid = logoid;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public NnewType(Integer ver, String _id, String parentid, Integer displogo,
			String logoid, String details) {
		super();
		this.ver = ver;
		this._id = _id;
		this.parentid = parentid;
		this.displogo = displogo;
		this.logoid = logoid;
		this.details = details;
	}
	public NnewType() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "NType [ver=" + ver + ", _id=" + _id + ", parentId=" + parentid
				+ ", dispLogo=" + displogo + ", logoId=" + logoid
				+ ", details=" + details + "]";
	}
	
}

