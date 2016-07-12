package org.mtrec.wheramitools.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

public class AreaConnector {

	@Id 
	private Integer _id;
	private Boolean bidirectional;
	private Boolean obstacle_free;
	private Boolean autoPath;
	private List<Integer> fromPt;
	private List<Integer> toPt;
	private int fromRegion;
	private int toRegion;
	private int fromArea;
	private int toArea;
	private Date lastModified;
	

	 
//	 public static void setObjectId(ObjectId id){
//		  setObjectId(id);
//		  }
	 
	public Integer get_id() {
		return _id;
	}

	public void set_id(Integer _id) {
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


	public List<Integer> getFromPt() {
		return fromPt;
	}


	public void setFromPt(List<Integer> fromPt) {
		this.fromPt = fromPt;
	}


	public List<Integer> getToPt() {
		return toPt;
	}


	public void setToPt(List<Integer> toPt) {
		this.toPt = toPt;
	}


	public int getFromRegion() {
		return fromRegion;
	}


	public void setFromRegion(int fromRegion) {
		this.fromRegion = fromRegion;
	}


	public int getToRegion() {
		return toRegion;
	}


	public void setToRegion(int toRegion) {
		this.toRegion = toRegion;
	}


	public int getFromArea() {
		return fromArea;
	}


	public void setFromArea(int fromArea) {
		this.fromArea = fromArea;
	}


	public int getToArea() {
		return toArea;
	}


	public void setToArea(int toArea) {
		this.toArea = toArea;
	}


	public Date getLastModified() {
		return lastModified;
	}


	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}


	
	public AreaConnector(Boolean bidirectional, Boolean obstacle_free,
			Boolean autoPath, List<Integer> fromPt, List<Integer> toPt,
			int fromRegion, int toRegion, int fromArea, int toArea,
			Date lastModified) {
		super();
		this.bidirectional = bidirectional;
		this.obstacle_free = obstacle_free;
		this.autoPath = autoPath;
		this.fromPt = fromPt;
		this.toPt = toPt;
		this.fromRegion = fromRegion;
		this.toRegion = toRegion;
		this.fromArea = fromArea;
		this.toArea = toArea;
		this.lastModified = lastModified;
	}


	public AreaConnector() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
