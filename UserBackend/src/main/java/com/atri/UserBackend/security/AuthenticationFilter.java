package com.atri.UserBackend.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.atri.UserBackend.entity.LoginRequestModel;
import com.atri.UserBackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private UserService usrService;
	private Environment env;
	
	public AuthenticationFilter(UserService usrService, Environment env, AuthenticationManager am) {
		this.usrService = usrService;
		this.env = env;
		super.setAuthenticationManager(am);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		LoginRequestModel creds;
		try {
//			readInputStream(request.getInputStream());
			creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String username = ((User)auth.getPrincipal()).getUsername();
		com.atri.UserBackend.entity.User user = usrService.getUserByEmail(username);
		
		String token = Jwts.builder()
				.setSubject(String.valueOf(user.getId()))
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
				.compact();
		response.addHeader("token", token);
		response.addHeader("user_id", String.valueOf(user.getId()));

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(new ObjectMapper().writeValueAsString(user));
        out.flush();
	}
	
	private String readInputStream(ServletInputStream is) throws IOException {
		InputStream inputStream = is; 
		BufferedReader bufferedReader = null;
        StringBuilder stringBuilder=new StringBuilder();
        if (inputStream != null) {  
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  

            char[] charBuffer = new char[128];  
            int bytesRead = -1;  
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {  
                stringBuilder.append(charBuffer, 0, bytesRead);  
            }  
        } else {  
            stringBuilder.append("");  
        } 
        return stringBuilder.toString();
	}
}
