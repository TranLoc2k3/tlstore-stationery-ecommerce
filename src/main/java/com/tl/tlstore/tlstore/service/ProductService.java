package com.tl.tlstore.tlstore.service;

import com.tl.tlstore.tlstore.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findTopProductsByCategoryNameLike(String category, int limit);
    Product findById(Long id);
    List<Product> findAllById(Iterable<Long> ids);
    Product save(Product product);
    List<Product> findAllByCategoryIdAndNameContaining(Long categoryId, String name);
    List<Product> findTopProductsByNameContaining(String name);

}
