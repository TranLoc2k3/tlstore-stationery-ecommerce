package com.tl.tlstore.tlstore.repository;

import com.tl.tlstore.tlstore.model.Category;
import com.tl.tlstore.tlstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends JpaRepository<Category, Long> {
}