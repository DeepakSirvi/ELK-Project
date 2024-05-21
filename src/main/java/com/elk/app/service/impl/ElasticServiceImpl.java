package com.elk.app.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elk.app.model.Product;
import com.elk.app.service.ElasticSearchService;
import com.elk.app.util.ElasticSearchUtil;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@Service
public class ElasticServiceImpl implements ElasticSearchService{


	    @Autowired
	    private ElasticsearchClient  elasticsearchClient;

	    public SearchResponse<Map> matchAllServices() throws IOException {
	        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
	       SearchResponse<Map> searchResponse = elasticsearchClient.search(s->s.query(supplier.get()),Map.class);
	        System.out.println("elasticsearch query is "+supplier.get().toString());
	        return searchResponse;
	    }
	    
	    //matchAllProducts video content

	    public SearchResponse<Product> matchAllProductsServices() throws IOException {
	        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
	        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("product").query(supplier.get()),Product.class);
	        System.out.println("elasticsearch query is "+supplier.get().toString());
	        return searchResponse;
	    }

	    //matchProductWithName

	    public SearchResponse<Product> matchProductsWithName(String fieldValue) throws IOException {
	        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithNameField(fieldValue);
	        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("product").query(supplier.get()),Product.class);
	        System.out.println("elasticsearch query is "+supplier.get().toString());
	        return searchResponse;
	    }
	    
	    public SearchResponse<Product> boolQueryImpl(String productName, Integer qty) throws IOException {
	        Supplier<Query> supplier  = ElasticSearchUtil.supplierQueryForBoolQuery(productName, qty);
	        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("product").query(supplier.get()),Product.class);
	        System.out.println("elasticsearch query is "+supplier.get().toString());
	        return searchResponse;
	    }
	    
	    public SearchResponse<Product> fuzzySearch(String approximateProductName) throws IOException {
	        Supplier<Query>  supplier = ElasticSearchUtil.createSupplierQuery(approximateProductName);
	       SearchResponse<Product> response = elasticsearchClient
	               .search(s->s.index("product").query(supplier.get()),Product.class);
	        System.out.println("elasticsearch supplier fuzzy query "+supplier.get().toString());
	        return response;
	    }
	    
	    public SearchResponse<Product> autoSuggestProduct(String partialProductName) throws IOException {

	        Supplier<Query> supplier = ElasticSearchUtil.createSupplierAutoSuggest(partialProductName);
	       SearchResponse<Product> searchResponse  = elasticsearchClient
	                .search(s->s.index("product").query(supplier.get()), Product.class);
	        System.out.println(" elasticsearch auto suggestion query"+supplier.get().toString());
	        return searchResponse;
	    }
	    
	    public SearchResponse<Product> multiMatch(String key , List<String> fields ) throws IOException {
	        Supplier<Query> supplier  = ElasticSearchUtil.supplierQueryForMultiMatchQuery(key,fields);
	        SearchResponse<Product> searchResponse =
	                elasticsearchClient.search(s->s.index("product").query(supplier.get()), Product.class);
	        System.out.println("es query" +supplier.get().toString());
	        return searchResponse;
	    }


}
