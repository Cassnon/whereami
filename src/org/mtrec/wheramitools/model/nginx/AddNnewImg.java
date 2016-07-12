package org.mtrec.wheramitools.model.nginx;

public class AddNnewImg {
	private Integer ver;
	private String type;
	private String impath;
	private String tag;
	private String auth;
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImpath() {
		return impath;
	}
	public void setImpath(String impath) {
		this.impath = impath;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public AddNnewImg(Integer ver, String type, String impath, String tag, String auth) {
		super();
		this.ver = ver;
		this.type = type;
		this.impath = impath;
		this.tag = tag;
		this.auth = auth;
	}
	public AddNnewImg() {
		super();
	}
}
