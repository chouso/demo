package com.chcodes.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chcodes.demo.domain.UserInformations;

public interface UserInformationsRepo extends JpaRepository<UserInformations, Integer> {
	UserInformations findByCity(String city);
}
