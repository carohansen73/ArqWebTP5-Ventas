package com.example.demo.utils;

import java.net.URI;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TokenClienteHandler {
	private  String token = null;
	@Autowired
	private  RestTemplate restTemplate;
	
	public String getToken() {
		if(token == null) {
			String body = "{\"username\":\"admin\",\"password\":\"admin\"}";
			RequestEntity request = RequestEntity
					.post(URI.create("http://localhost:8081/auth/login"))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.ALL)
					.body(body);
			ParameterizedTypeReference<HashMap<String,String>> myBean =
			 new ParameterizedTypeReference<HashMap<String,String>>() {};
			ResponseEntity<HashMap<String,String>> response = restTemplate.exchange(request, myBean);
			token = response.getBody().get("token");
			System.out.println("token:" + token);	
		}
		return token;
	}
}
