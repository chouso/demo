package com.chcodes.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chcodes.demo.domain.UserInformations;
import com.chcodes.demo.repo.UserInformationsRepo;

@Service
public class UserInformationsService {
	@Autowired
	UserInformationsRepo userInfoRepo;

	public UserInformations saveUserInfos(UserInformations c) {
		return userInfoRepo.save(c);
	}

	public String deleteUserInfos(int id) {
		userInfoRepo.deleteById(id);
		return "user infos removed !! " + id;
	}

	public List<UserInformations> getAllUserInfos() {
		return userInfoRepo.findAll();
	}

	public UserInformations getUserInfos(int id) {
		return userInfoRepo.findById(id).get();
	}

	public UserInformations updateUserInformations(UserInformations u) {
		UserInformations existingUserInfos = userInfoRepo.findById(u.getIdInformations()).orElse(null);
		existingUserInfos.setAddress(u.getAddress());
		existingUserInfos.setCity(u.getCity());
		existingUserInfos.setEmail(u.getEmail());
		return userInfoRepo.save(existingUserInfos);
	}
}
