package com.tl.tlstore.tlstore.service;

import com.tl.tlstore.tlstore.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);

    Category save(Category category);
}
