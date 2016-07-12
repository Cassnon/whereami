package org.mtrec.wheramitools.form;

import java.util.ArrayList;
import java.util.List;

import org.mtrec.wheramitools.model.Connector;
import org.mtrec.wheramitools.model.Region;
import org.mtrec.wheramitools.model.nginx.NWFpoints;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PointForm {
	
	private String areaCode;

	private List<NWFpoints> pointsList = new ArrayList<NWFpoints>();

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public List<NWFpoints> getPointsList() {
		return pointsList;
	}

	public void setPointsList(List<NWFpoints> pointsList) {
		this.pointsList = pointsList;
	}

	public PointForm(String areaCode, List<NWFpoints> pointsList) {
		super();
		this.areaCode = areaCode;
		this.pointsList = pointsList;
	}

	public PointForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	




	

}
