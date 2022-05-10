package com.chcodes.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chcodes.demo.domain.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	Category findByNom(String nom);
}
