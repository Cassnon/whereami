package org.mtrec.wheramitools.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

public class NewAreaConnector {

	@Id 
	private String _id;
	private Boolean bidirectional;
	private Boolean obstacle_free;
	private Boolean autoPath;
	private List<String> frompt;
	private List<String> topt;
	private String fromregion;
	private String toregion;
	private String fromarea;
	private String toarea;
	private Date lastmodified;
    
	public String get_id() {
		return _id;
	}


	public void set_id(String _id) {
		this._id = _id;
	}


	public Boolean getBidirectional() {
		return bidirectional;
	}


	public void setBidirectional(Boolean bidirectional) {
		this.bidirectional = bidirectional;
	}


	public Boolean getObstacle_free() {
		return obstacle_free;
	}


	public void setObstacle_free(Boolean obstacle_free) {
		this.obstacle_free = obstacle_free;
	}


	public Boolean getAutoPath() {
		return autoPath;
	}


	public void setAutoPath(Boolean autoPath) {
		this.autoPath = autoPath;
	}


	public List<String> getFrompt() {
		return frompt;
	}


	public void setFrompt(List<String> frompt) {
		this.frompt = frompt;
	}


	public List<String> getTopt() {
		return topt;
	}


	public void setTopt(List<String> topt) {
		this.topt = topt;
	}


	public String getFromregion() {
		return fromregion;
	}


	public void setFromregion(String fromregion) {
		this.fromregion = fromregion;
	}


	public String getToregion() {
		return toregion;
	}


	public void setToregion(String toregion) {
		this.toregion = toregion;
	}


	public String getFromarea() {
		return fromarea;
	}


	public void setFromarea(String fromarea) {
		this.fromarea = fromarea;
	}


	public String getToarea() {
		return toarea;
	}


	public void setToarea(String toarea) {
		this.toarea = toarea;
	}


	public Date getLastmodified() {
		return lastmodified;
	}


	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}


	public NewAreaConnector(Boolean bidirectional, Boolean obstacle_free,
			Boolean autoPath, List<String> frompt, List<String> topt,
			String fromregion, String toregion,String fromarea, String toarea,
			Date lastmodified) {
		super();
		this.bidirectional = bidirectional;
		this.obstacle_free = obstacle_free;
		this.autoPath = autoPath;
		this.frompt = frompt;
		this.topt = topt;
		this.fromregion = fromregion;
		this.toregion = toregion;
		this.fromarea = fromarea;
		this.toarea = toarea;
		this.lastmodified = lastmodified;
	}


	public NewAreaConnector() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
