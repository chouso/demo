package com.chcodes.demo.filter;

import static java.util.Arrays.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
	// filter any request coming in and determine if the user has access to the
	// aplication or not
	// this method will intercept every request that come to th app
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// check if it's login , user is just trying to log in=> not doing anyThing
		if (request.getServletPath().equals("/api/login")) {
			//let the request continue
			filterChain.doFilter(request, response);
		} else {
         String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
         if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        	 //we are going to send the token after we have authenticated successfully
        	 //steps login successful , we get the token and then send another request with that token
        	 // so whener we send the request with the token , we are going to put the word "Bearer " and that means 
        	 //that whoever is sending the request passing in this token , once we verify that the token is valid , you don't have to do anything
        	 // give this person all the permission and no further check
        	 try {
        		 String token = authorizationHeader.substring("Bearer ".length());
        		 Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        		 //verify that the token is valid
        		 JWTVerifier verifier = JWT.require(algorithm).build();
        		 DecodedJWT decodedJWT = verifier.verify(token);
				String username = decodedJWT.getSubject();
				// here we will convert roles to something that extends GrantedAuthority, that what spring boot is expecting as roles of user
				String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
				 stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
				// here we tell spring security, hey this is the user , his name , his roles, so spring will look at the user and role
				//and determine what resource they can access
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
				//let the request continue
				filterChain.doFilter(request, response);
				} catch (Exception e) {
					logger.error("Error logging in " + e.getMessage(), e);
					response.setHeader("error", e.getMessage());
					//response.sendError(HttpStatus.FORBIDDEN.value());
					response.setStatus(HttpStatus.FORBIDDEN.value());
					Map<String,String> error = new HashMap<>();
					error.put("error_msg", e.getMessage());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

}
