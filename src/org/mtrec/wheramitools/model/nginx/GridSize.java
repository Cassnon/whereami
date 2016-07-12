package org.mtrec.wheramitools.model.nginx;

public class GridSize {
	public String area;
	public int gridsize;
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getGridsize() {
		return gridsize;
	}
	public void setGridsize(int gridsize) {
		this.gridsize = gridsize;
	}
	public GridSize(String area, int gridsize) {
		super();
		this.area = area;
		this.gridsize = gridsize;
	}
	public GridSize() {
		super();
		// TODO Auto-generated constructor stub
	}
}
