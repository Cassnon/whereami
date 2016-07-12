package org.mtrec.wheramitools.model.nginx;

public class NPoints {
	
	private Integer x;
	private Integer y;
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
	public NPoints(Integer x, Integer y) {
		super();
		this.x = x;
		this.y = y;
	}
	public NPoints() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "NPoints [x=" + x + ", y=" + y + "]";
	}
	
	

}
