package com.chcodes.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chcodes.demo.domain.AppUser;
import com.chcodes.demo.domain.Role;
import com.chcodes.demo.repo.RoleRepo;
import com.chcodes.demo.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepo.findByUserName(username);
		if (user == null) {
			LOGGER.error("User not found in the database");
			throw new UsernameNotFoundException("User not found in the database");
		} else {
			LOGGER.info("User found in the database: {} ", username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				authorities);
	}

	@Override
	public AppUser saveUser(AppUser appUser) {
		LOGGER.info("saving new user to the database");
		// to encode the password and save it encoded in db 
		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		return userRepo.save(appUser);
	}

	@Override
	public Role saveRole(Role role) {
		LOGGER.info("saving new role to the database");
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		LOGGER.info("adding role to user");
		AppUser user = userRepo.findByUserName(username);
		Role role = roleRepo.findByName(roleName);
		user.getRoles().add(role);
	}

	@Override
	public AppUser getUser(String username) {
		LOGGER.info("fetching user");
		return userRepo.findByUserName(username);
	}

	@Override
	public List<AppUser> getUsers() {
		LOGGER.info("fetching all users");
		return userRepo.findAll();
	}

}
