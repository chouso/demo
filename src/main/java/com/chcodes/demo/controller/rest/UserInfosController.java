package com.chcodes.demo.controller.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.chcodes.demo.domain.UserInformations;
import com.chcodes.demo.service.UserInformationsService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/userinfos")
@RequiredArgsConstructor
public class UserInfosController {

	@Autowired
	private UserInformationsService service;

	@PostMapping("/add")
	public ResponseEntity<UserInformations> addUserInformations(@RequestBody UserInformations UserInformations) {
		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/userinfos/add").toUriString());
		return ResponseEntity.created(uri).body(service.saveUserInfos(UserInformations));
	}

	@GetMapping("")
	public List<UserInformations> findAllUserInformationss() {
		return service.getAllUserInfos();
	}

	@GetMapping("/UserInformationsById/{id}")
	public UserInformations findUserInformationsById(@PathVariable int id) {
		return service.getUserInfos(id);
	}

	@PutMapping("/update")
	public UserInformations updateUserInformations(@RequestBody UserInformations UserInformations) {
		return service.updateUserInformations(UserInformations);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUserInformations(@PathVariable int id) {
		return service.deleteUserInfos(id);
	}
}
