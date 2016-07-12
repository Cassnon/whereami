package org.mtrec.wheramitools.model.nginx;

public class AddNImg {
	private Integer ver;
	private String type;
	private String imPath;
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
	public String getImPath() {
		return imPath;
	}
	public void setImPath(String imPath) {
		this.imPath = imPath;
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


	public AddNImg(Integer ver, String type, String imPath,
			String tag, String auth) {
		super();
		this.ver = ver;
		this.type = type;
		this.imPath = imPath;
		this.tag = tag;
		this.auth = auth;
	}
	public AddNImg() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
