package org.mtrec.wheramitools.form;

import org.mtrec.wheramitools.form.SaveAreaForm;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class SaveAreaForm_p extends SaveAreaForm{
	
	@Transient 
	private CommonsMultipartFile img_amap;

	public CommonsMultipartFile getImg_amap() {
		return img_amap;
	}

	public void setImg_amap(CommonsMultipartFile img_amap) {
		this.img_amap = img_amap;
	}

	public SaveAreaForm_p(String _id, String building, String chn_floor, Double altitude, String swRegions,
			String img_center, String img_amap_text, String name, String imgid, CommonsMultipartFile img_amap) {
		super(_id, building, chn_floor, altitude, swRegions, img_center, img_amap_text, name, imgid);
		this.img_amap = img_amap;
	}

	public SaveAreaForm_p() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SaveAreaForm_p(String _id, String building, String chn_floor, Double altitude, String swRegions,
			String img_center, String img_amap_text, String name, String imgid) {
		super(_id, building, chn_floor, altitude, swRegions, img_center, img_amap_text, name, imgid);
		// TODO Auto-generated constructor stub
	}

	
	
}
