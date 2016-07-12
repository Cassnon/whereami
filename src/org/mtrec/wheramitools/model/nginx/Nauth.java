package org.mtrec.wheramitools.model.nginx;

public class Nauth {
	private String auth;
	private int role;
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public Nauth(String auth, int role) {
		super();
		this.auth = auth;
		this.role = role;
	}
	public Nauth() {
		super();
		// TODO Auto-generated constructor stub
	}
}
