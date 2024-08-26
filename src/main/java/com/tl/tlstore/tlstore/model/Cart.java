package com.tl.tlstore.tlstore.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Long, Integer> products = new HashMap<>();

    public Cart() {
    }

    public Map<Long, Integer> getProducts() {
        return products;
    }
    public void addProduct(Long productId, int quantity) {
        if (products.containsKey(productId)) {
            products.put(productId, products.get(productId) + quantity);
        } else {
            products.put(productId, quantity);
        }
    }
    public void updateQuantity(Long productId, int quantity) {
        if (quantity == 0) {
            products.remove(productId);
            return;
        }
        if (products.containsKey(productId)) {
            products.put(productId, quantity);
        }
    }
}
