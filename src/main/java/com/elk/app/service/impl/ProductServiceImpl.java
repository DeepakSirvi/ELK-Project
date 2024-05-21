package com.elk.app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elk.app.model.Product;
import com.elk.app.repo.ProductRepo;
import com.elk.app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepo productRepo;
	
	public Iterable<Product> getProducts(){
		return productRepo.findAll();
	}
	
	public Product save(Product product){
		return productRepo.save(product);
	}
	
	public Product updateProduct(Product product){
		Product oldProduct = productRepo.findById(product.getId()).get();
		oldProduct.setPrice(product.getPrice());
		return productRepo.save(oldProduct);
	}
	
	public void deleteProduct(Long id){
		  productRepo.deleteById(id);
	}
}
