package org.mtrec.wheramitools.controller;

import javax.servlet.http.HttpSession;

import org.mtrec.wheramitools.dao.nginx.NadminDao;
import org.mtrec.wheramitools.model.nginx.NUser;
import org.mtrec.wheramitools.model.nginx.Ndchost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller	
public class AdminController {
	@Autowired
	private NadminDao nadminDao;
	
	@RequestMapping(value="/savedchost")
	public @ResponseBody String savedchost(@RequestBody Ndchost dchost,HttpSession session){
		String result = nadminDao.savedchost(dchost, session);
		return result;
	}
	
	@RequestMapping(value="/saveuser")
	public @ResponseBody String saveuser(@RequestBody NUser user,HttpSession session){
		String result = nadminDao.saveuser(user, session);
		return result;
	}
}
