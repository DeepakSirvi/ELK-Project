package com.elk.app.service;

import com.elk.app.model.Product;

public interface ProductService {

	public Iterable<Product> getProducts();
	
	public Product save(Product product);
	
	public Product updateProduct(Product product);
	
	public void deleteProduct(Long id);
}
