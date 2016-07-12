package org.mtrec.wheramitools.form;

import java.util.ArrayList;
import java.util.List;

import org.mtrec.wheramitools.model.Connector;
import org.mtrec.wheramitools.model.Region;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PolygonForm {

	private List<Region> regionList = new ArrayList<Region>();

	public List<Region> getRegionList() {
		return regionList;
	}
	public void setRegionList(List<Region> regionList) {
		this.regionList = regionList;
	}
	public PolygonForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "PolygonForm [regionList="+ regionList + "]";
	}
}
