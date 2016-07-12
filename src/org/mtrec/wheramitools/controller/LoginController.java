package org.mtrec.wheramitools.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mtrec.wheramitools.dao.nginx.NloginDao;
import org.mtrec.wheramitools.model.nginx.NLogin;
import org.mtrec.wheramitools.model.nginx.NUser;
import org.mtrec.wheramitools.model.nginx.Nauth;
import org.mtrec.wheramitools.model.nginx.Nhosts;
import org.mtrec.wheramitools.response.JsonResult;
import org.mtrec.wheramitools.response.RoleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller	
public class LoginController {
	@Autowired
	private NloginDao nloginDao;

	@RequestMapping(value="/loginaction")
	@ResponseBody
	public JsonResult loginaction(@RequestBody NLogin user,HttpServletRequest request)
	{
		JsonResult result = new JsonResult();
		HttpSession session = request.getSession();
		session.setAttribute("auth", "");
		Nhosts site= this.nloginDao.gethostdetail(session,Integer.parseInt(user.getSite()));
		NUser nuser = new NUser();
		nuser.setPassword(user.getPassword());
		nuser.setUsername(user.getUsername());
		Nauth nauth = null;
		nauth = this.nloginDao.login(site.getName(),nuser);
		if(nauth != null){
			session.setAttribute("auth", nauth.getAuth().toString());
			session.setAttribute("role", String.valueOf((nauth.getRole())));
			session.setAttribute("url", "https://"+site.getIp()+":"+site.getPort()+"/Demo/clientAPI/");
			session.setAttribute("site", site.getName());
			session.setAttribute("id",site.get_id());
			result.setIsSuccess(1);
		}
		else{
			result.setIsSuccess(0);
		}
		return result;
	}
	
	//get host detail
	@RequestMapping(value="/gethosts")
	@ResponseBody
	public Nhosts[] gethosts(HttpServletRequest request)
	{
		Nhosts[] nhosts= this.nloginDao.getsites();
		return nhosts;
	}
	
	//check if login
	@RequestMapping(value="/checklogin")
	@ResponseBody
	public RoleResult checklogin(HttpSession session){
		RoleResult result = new RoleResult();			
		if(session.getAttribute("auth") == null)
			result.setRole(-1);
		else if(Integer.parseInt(session.getAttribute("role").toString()) == 1)
			result.setRole(1);
		else
			result.setRole(0);
		return result;
	}
	
	//check user
	@RequestMapping(value="/checkuser")
	@ResponseBody
	public String checkuser(HttpSession session){
		if(session.getAttribute("auth").toString()!= null){
			return "1";
		}
		else
			return "0";
	}
	
	//logout
	@RequestMapping(value="/logout")
	@ResponseBody
	public JsonResult logout(HttpSession session){
		session.removeAttribute("auth");
		session.removeAttribute("url");
		session.removeAttribute("nocacheurl");
		session.removeAttribute("site");
		session.removeAttribute("functions");
		JsonResult result = new JsonResult();
		result.setIsSuccess(1);			
		return result;
	}
	
}