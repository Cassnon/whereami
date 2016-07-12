package org.mtrec.wheramitools.form;

import org.mtrec.wheramitools.model.nginx.NSite;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class SiteForm extends NSite{

	@Transient 
	private CommonsMultipartFile uploadmap_smap;

	public CommonsMultipartFile getUploadmap_smap() {
		return uploadmap_smap;
	}

	public void setUploadmap_smap(CommonsMultipartFile uploadmap_smap) {
		this.uploadmap_smap = uploadmap_smap;
	}

	public SiteForm(Integer ver, String apps, Integer _id, String img,
			String areas, String langs, String buildings, String name,
			String outdoorbuildingId, String auth,
			CommonsMultipartFile uploadmap_smap) {
		super(ver, apps, _id, img, areas, langs, buildings, name,
				outdoorbuildingId, auth);
		this.uploadmap_smap = uploadmap_smap;
	}

	public SiteForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SiteForm(Integer ver, String apps, Integer _id, String img,
			String areas, String langs, String buildings, String name,
			String outdoorbuildingId, String auth) {
		super(ver, apps, _id, img, areas, langs, buildings, name, outdoorbuildingId,
				auth);
		// TODO Auto-generated constructor stub
	}	
}

