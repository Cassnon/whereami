package org.mtrec.wheramitools.model.nginx;

public class NWFpoints {
	
	private Integer _id;
	private Integer x;
	private Integer y;
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public NWFpoints(Integer _id, Integer x, Integer y) {
		super();
		this._id = _id;
		this.x = x;
		this.y = y;
	}
	public NWFpoints() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "NWFpoints [_id=" + _id + ", x=" + x + ", y=" + y + "]";
	}


}
