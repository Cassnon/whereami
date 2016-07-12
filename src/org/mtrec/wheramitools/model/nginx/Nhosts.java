package org.mtrec.wheramitools.model.nginx;

public class Nhosts {
	private Integer _id;
	private Integer port;
	private String gps_loc;
	private String ip;
	private String functions;
	private String details;
	private String name;
	private Integer declare_ver;
	private Double defaultzoom;
	
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getGps_loc() {
		return gps_loc;
	}
	public void setGps_loc(String gps_loc) {
		this.gps_loc = gps_loc;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getFunctions() {
		return functions;
	}
	public void setFunctions(String functions) {
		this.functions = functions;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDeclare_ver() {
		return declare_ver;
	}
	public void setDeclare_ver(Integer declare_ver) {
		this.declare_ver = declare_ver;
	}
	public Nhosts(Integer _id, Integer port, String gps_loc, String ip,
			String functions, String details, String name, Integer declare_ver, Double defaultzoom) {
		super();
		this._id = _id;
		this.port = port;
		this.gps_loc = gps_loc;
		this.ip = ip;
		this.functions = functions;
		this.details = details;
		this.name = name;
		this.declare_ver = declare_ver;
		this.defaultzoom = defaultzoom;
	}
	public Nhosts() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Double getDefaultzoom() {
		return defaultzoom;
	}
	public void setDefaultzoom(Double defaultzoom) {
		this.defaultzoom = defaultzoom;
	}
}
