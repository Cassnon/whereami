package org.mtrec.wheramitools.model.nginx;

public class AddNFacility {

	private Integer ver;
	private String logoPt;
	private String exitPts;
	private String addr;
	private Integer logoId;
	private Integer typeId;
	private String entryPts;
	private String details;
	private String zone;
	private Integer areaId;
	private String stat;
	private String name;
	private String auth;
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getLogoPt() {
		return logoPt;
	}
	public void setLogoPt(String logoPt) {
		this.logoPt = logoPt;
	}
	public String getExitPts() {
		return exitPts;
	}
	public void setExitPts(String exitPts) {
		this.exitPts = exitPts;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Integer getLogoId() {
		return logoId;
	}
	public void setLogoId(Integer logoId) {
		this.logoId = logoId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getEntryPts() {
		return entryPts;
	}
	public void setEntryPts(String entryPts) {
		this.entryPts = entryPts;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public AddNFacility(Integer ver, String logoPt, String exitPts,
			String addr, Integer logoId, Integer typeId, String entryPts,
			String details, String zone, Integer areaId, String stat,
			String name, String auth) {
		super();
		this.ver = ver;
		this.logoPt = logoPt;
		this.exitPts = exitPts;
		this.addr = addr;
		this.logoId = logoId;
		this.typeId = typeId;
		this.entryPts = entryPts;
		this.details = details;
		this.zone = zone;
		this.areaId = areaId;
		this.stat = stat;
		this.name = name;
		this.auth = auth;
	}
	public AddNFacility() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
