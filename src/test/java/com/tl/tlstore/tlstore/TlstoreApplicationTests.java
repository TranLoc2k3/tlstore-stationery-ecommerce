package com.tl.tlstore.tlstore;

import com.tl.tlstore.tlstore.model.Order;
import com.tl.tlstore.tlstore.model.Product;
import com.tl.tlstore.tlstore.repository.OrderRepository;
import com.tl.tlstore.tlstore.repository.ProductRepository;
import com.tl.tlstore.tlstore.repository.UserRepository;
import com.tl.tlstore.tlstore.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class TlstoreApplicationTests {

	@Autowired
	private ProductService productService;
	@Autowired
	private OrderRepository orderRepository;

	@Test
	void contextLoads() {

	}
}