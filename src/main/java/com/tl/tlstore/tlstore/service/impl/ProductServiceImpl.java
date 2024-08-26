package com.tl.tlstore.tlstore.service.impl;

import com.tl.tlstore.tlstore.model.Product;
import com.tl.tlstore.tlstore.repository.ProductRepository;
import com.tl.tlstore.tlstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findTopProductsByCategoryNameLike(String category, int limit) {
        return productRepository.findProductByCategory_Name(category, PageRequest.of(0, limit)).getContent();
    }
    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAllById(Iterable<Long> ids) {
        return productRepository.findAllById(ids);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllByCategoryIdAndNameContaining(Long categoryId, String name) {
        return productRepository.findAllByCategoryIdAndNameContaining(categoryId, name);
    }
    @Override
    public List<Product> findTopProductsByNameContaining(String name) {
        return productRepository.findTopProductsByNameContaining(name, PageRequest.of(0, 24)).getContent();
    }
}
