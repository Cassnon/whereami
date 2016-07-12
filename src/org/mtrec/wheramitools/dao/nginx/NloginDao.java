package org.mtrec.wheramitools.dao.nginx;

import javax.servlet.http.HttpSession;

import org.mtrec.wheramitools.model.nginx.NLogin;
import org.mtrec.wheramitools.model.nginx.NUser;
import org.mtrec.wheramitools.model.nginx.Nauth;
import org.mtrec.wheramitools.model.nginx.Nhosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Repository
public class NloginDao {
	public NloginDao() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Nauth login(String site,NUser user) {
		Nauth[] auth = null;
        String jString = "";
        String url = "https://localhost/Demo/clientAPI/auth";
		ObjectMapper mapper = new ObjectMapper();
		try {
			jString  = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        auth = HttpClient.Login(url, jString, Nauth[].class);
        if(auth == null)
        	return null;
        else
        	return auth[0];
	}

	
	public Nhosts[] getsites() {
		String urlReq = "https://localhost/Demo/clientAPI/dc_hosts";
		Nhosts[] hosts = HttpClient.doGethosts_server(urlReq, Nhosts[].class);
		return hosts;
		
	}
	
	//get the detail of the loginuser
	public Nhosts gethostdetail(HttpSession session, Integer id) {
		String urlReq = "https://localhost/Demo/clientAPI/dc_hosts/_id/"+id;
		Nhosts[] hosts = HttpClient.doGetServer_withclass(session, urlReq, Nhosts[].class);
		return hosts[0];
		
	}


}
