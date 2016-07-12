package org.mtrec.wheramitools.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="region_tbl")
public class Region {
	@Override
	public String toString() {
		return "Region [areaId=" + areaId + ",id="+id+ ", polyId=" + polyId
				+ ", lastModified=" + lastModified + ", connectedPolys="
				+ connectedPolys + ", vertexes=" + vertexes + "]";
	}

	private String areaId;
	private int polyId;
	private String id;
	private Date lastModified;
	private List<Integer> connectedPolys;
	private List<Integer> vertexes;
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
	public int getPolyId() {
		return polyId;
	}
	public void setPolyId(int polyId) {
		this.polyId = polyId;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public List<Integer> getConnectedPolys() {
		return connectedPolys;
	}
	public void setConnectedPolys(List<Integer> connectedPolys) {
		this.connectedPolys = connectedPolys;
	}

	public List<Integer> getVertexes() {
		return vertexes;
	}
	public void setVertexes(List<Integer> vertexes) {
		this.vertexes = vertexes;
	}

	public Region() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
