package com.chcodes.demo.service;

import java.util.List;

import com.chcodes.demo.domain.AppUser;
import com.chcodes.demo.domain.Role;

public interface UserService {

	AppUser saveUser(AppUser appUser);

	Role saveRole(Role role);

	void addRoleToUser(String username, String roleName);

	AppUser getUser(String username);

	List<AppUser> getUsers();


	public AppUser get(Long id);
	
	public AppUser  updateUser(Long id, AppUser appUser) ;
}
