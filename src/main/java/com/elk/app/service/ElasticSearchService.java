package com.elk.app.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.elk.app.model.Product;

import co.elastic.clients.elasticsearch.core.SearchResponse;

public interface ElasticSearchService {

	public SearchResponse<Map> matchAllServices() throws IOException;

	// matchAllProducts video content
	public SearchResponse<Product> matchAllProductsServices() throws IOException;

	// matchProductWithName
	public SearchResponse<Product> matchProductsWithName(String fieldValue) throws IOException;

	public SearchResponse<Product> boolQueryImpl(String productName, Integer qty) throws IOException;
	
	  public SearchResponse<Product> fuzzySearch(String approximateProductName) throws IOException;
	  
	  public SearchResponse<Product> autoSuggestProduct(String partialProductName) throws IOException;

	  public SearchResponse<Product> multiMatch(String key , List<String> fields ) throws IOException;
}
