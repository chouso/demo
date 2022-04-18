package com.chcodes.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chcodes.demo.domain.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {
	Product findByName(String name);
}
