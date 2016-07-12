package org.mtrec.wheramitools.dao.nginx;

import javax.servlet.http.HttpSession;

import org.mtrec.wheramitools.model.nginx.NUser;
import org.mtrec.wheramitools.model.nginx.Ndchost;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class NadminDao {
	public NadminDao() {
		super();
	}
	
	public String savedchost(Ndchost dchost,HttpSession session){
		ObjectMapper mapper = new ObjectMapper();
		String jString = "";
        try {
			jString  = mapper.writeValueAsString(dchost);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //String url = session.getAttribute("url").toString()+"/wheramitools";
        //String url = "https://eek123.ust.hk:443/Demo/ClientAPI/wheramitools";
        String url = "https://localhost/Demo/ClientAPI/wheramitools";
        String result = HttpClient.doPost_Server_save(url, jString);
		return result;
	}
	
	public String saveuser(NUser user,HttpSession session){
		ObjectMapper mapper = new ObjectMapper();
		String jString = "";
        try {
			jString  = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //String url = session.getAttribute("url").toString()+"/wheramitools";
        //String url = "https://eek123.ust.hk:443/Demo/ClientAPI/login";
        String url = "https://localhost/Demo/ClientAPI/login";
        String result = HttpClient.doPost_Server_save(url, jString);
		return result;
	}
}
