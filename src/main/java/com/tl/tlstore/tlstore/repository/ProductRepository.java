package com.tl.tlstore.tlstore.repository;

import com.tl.tlstore.tlstore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.category.name = ?1")
    Page<Product> findProductByCategory_Name(String category, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.name LIKE %:name%")
    List<Product> findAllByCategoryIdAndNameContaining(Long categoryId, String name);
    List<Product> findAllById(Iterable<Long> ids);
    Page<Product> findTopProductsByNameContaining(String name, Pageable pageable);


}