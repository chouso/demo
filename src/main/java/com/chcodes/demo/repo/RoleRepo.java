package com.chcodes.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chcodes.demo.domain.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	Role findByName(String username);
}
