package com.elk.app.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.elk.app.model.Product;

public interface ProductRepo extends ElasticsearchRepository<Product, Long> {

}
