package org.mtrec.wheramitools.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="connector_tbl")
public class Connector {
	
	private String areaId;
	private Date lastModified;
	private String polyId1;
	private String polyId2;
	private Integer type;
	private String id;
	private List<Integer> connectors;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public String getPolyId1() {
		return polyId1;
	}
	public void setPolyId1(String polyId1) {
		this.polyId1 = polyId1;
	}
	public String getPolyId2() {
		return polyId2;
	}
	public void setPolyId2(String polyId2) {
		this.polyId2 = polyId2;
	}
	public List<Integer> getConnectors() {
		return connectors;
	}
	public void setConnectors(List<Integer> connectors) {
		this.connectors = connectors;
	}
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Connector(String areaId, Date lastModified, String id, String polyId1, String polyId2,
			Integer type, List<Integer> connectors) {
		super();
		this.areaId = areaId;
		this.lastModified = lastModified;
		this.id = id;
		this.polyId1 = polyId1;
		this.polyId2 = polyId2;
		this.type = type;
		this.connectors = connectors;
	}
	public Connector() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
