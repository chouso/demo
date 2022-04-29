package com.chcodes.demo.controller;

import java.io.IOException;
import java.net.URI;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.chcodes.demo.domain.AppUser;
import com.chcodes.demo.domain.Role;
import com.chcodes.demo.service.UserService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<AppUser>> getUsers() {
		return ResponseEntity.ok().body(userService.getUsers());
	}

	@PostMapping("/user/save")
	public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
		return ResponseEntity.created(uri).body(userService.saveUser(user));
	}

	@PostMapping("/role/save")
	public ResponseEntity<Role> saveUser(@RequestBody Role role) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}

	@PostMapping("/role/addtouser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
		userService.addRoleToUser(form.username, form.roleName);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
		 String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		  if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	        	 //we are going to send the token after we have authenticated successfully
	        	 //steps login successful , we get the token and then send another request with that token
	        	 // so whener we send the request with the token , we are going to put the word "Bearer " and that means 
	        	 //that whoever is sending the request passing in this token , once we verify that the token is valid , you don't have to do anything
	        	 // give this person all the permission and no further check
	        	 try {
	        		 String refresh_token = authorizationHeader.substring("Bearer ".length());
	        		 Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
	        		 //verify that the token is valid
	        		 JWTVerifier verifier = JWT.require(algorithm).build();
	        		 DecodedJWT decodedJWT = verifier.verify(refresh_token);
					String username = decodedJWT.getSubject();
					//load the user 
					AppUser user = userService.getUser(username);
					
					
					String access_token = JWT.create()
							.withSubject(user.getUserName())
							.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
							.withIssuer(request.getRequestURI().toString())
							.withClaim("roles",user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
							.sign(algorithm);

					
					//here to get tokens in body in response
					Map<String,String> tokens = new HashMap<>();
					tokens.put("access_token", access_token);
					tokens.put("refresh_token", refresh_token);
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), tokens);
					} catch (Exception e) {
						response.setHeader("error", e.getMessage());
						//response.sendError(HttpStatus.FORBIDDEN.value());
						response.setStatus(HttpStatus.FORBIDDEN.value());
						Map<String,String> error = new HashMap<>();
						error.put("error_msg", e.getMessage());
						response.setContentType(MediaType.APPLICATION_JSON_VALUE);
						new ObjectMapper().writeValue(response.getOutputStream(), error);
					}
				} else {
					throw new RuntimeException("Refresh token is missing");
				}
	}
	@Data
	class RoleToUserForm {
		private String username;
		private String roleName;
	}
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}
	@GetMapping("/mod")
	@PreAuthorize("hasRole('MANAGER')")
	public String moderatorAccess() {
		return "MANAGER Board.";
	}
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
