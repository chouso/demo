package com.chcodes.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chcodes.demo.domain.AppUser;

public interface UserRepo extends JpaRepository<AppUser, Integer> {
	AppUser findByUserName(String username);

	AppUser getById(Long id);
}
