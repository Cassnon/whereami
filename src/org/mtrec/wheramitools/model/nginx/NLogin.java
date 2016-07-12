package org.mtrec.wheramitools.model.nginx;

public class NLogin {
	//site Id in globals/dc_hosts
	private String site;
	private String username;
	private String password;
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public NLogin(String site, String username, String password) {
		super();
		this.site = site;
		this.username = username;
		this.password = password;
	}
	public NLogin() {
		super();
		// TODO Auto-generated constructor stub
	}
	


}
