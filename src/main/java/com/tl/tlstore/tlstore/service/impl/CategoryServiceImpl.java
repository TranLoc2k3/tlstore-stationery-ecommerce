package com.tl.tlstore.tlstore.service.impl;

import com.tl.tlstore.tlstore.model.Category;
import com.tl.tlstore.tlstore.repository.CategorieRepository;
import com.tl.tlstore.tlstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategorieRepository categorieRepository;
    @Override
    public List<Category> findAll() {
        return categorieRepository.findAll();
    }
    @Override
    public Optional<Category> findById(Long id) {
        return categorieRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categorieRepository.save(category);
    }

}
