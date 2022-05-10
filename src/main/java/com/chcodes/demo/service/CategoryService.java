package com.chcodes.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chcodes.demo.domain.Category;
import com.chcodes.demo.repo.CategoryRepo;

@Service
public class CategoryService {
	@Autowired
	CategoryRepo categoryRepo;

	public void saveCategory(Category c) {
		categoryRepo.save(c);
	}

	public void deleteCategory(int id) {
		categoryRepo.deleteById(id);
	}

	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}

	public Category getCategory(int id) {
		return categoryRepo.findById(id).get();
	}
}
