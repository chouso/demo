package com.chcodes.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chcodes.demo.domain.Command;

public interface CommandRepo extends JpaRepository<Command, Integer> {

	@Query("select c from Command c where c.user.id=:x")
	List<Command> findByUser(@Param("x") int id);
}
