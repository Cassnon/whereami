package org.mtrec.wheramitools.model.nginx;

public class Ndchost {
	private String name;
	private String ip;
	private String en;
	private String chn_hk;
	private String chn_cn;
	private String gps_long;
	private String gps_lat;
	private String port;
	public Double getDefaultzoom() {
		return defaultzoom;
	}
	public void setDefaultzoom(Double defaultzoom) {
		this.defaultzoom = defaultzoom;
	}
	private Double defaultzoom;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	public String getChn_hk() {
		return chn_hk;
	}
	public void setChn_hk(String chn_hk) {
		this.chn_hk = chn_hk;
	}
	public String getChn_cn() {
		return chn_cn;
	}
	public void setChn_cn(String chn_cn) {
		this.chn_cn = chn_cn;
	}
	public String getGps_long() {
		return gps_long;
	}
	public void setGps_long(String gps_long) {
		this.gps_long = gps_long;
	}
	public String getGps_lat() {
		return gps_lat;
	}
	public void setGps_lat(String gps_lat) {
		this.gps_lat = gps_lat;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
}
