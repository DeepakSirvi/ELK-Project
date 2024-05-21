package com.elk.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elk.app.model.Product;
import com.elk.app.service.ElasticSearchService;
import com.elk.app.service.ProductService;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

@RequestMapping("/product")
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ElasticSearchService elasticSearchService;

	@PostMapping
	public ResponseEntity<?> saveProduct(@RequestBody Product product) {
		return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> getProducts() {
		return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>("Deleted successfully", HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> updateProduct(@RequestBody Product product) {
		return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.CREATED);
	}

	@GetMapping("/matchAll")
	public ResponseEntity<String> matchAll() throws IOException {
		SearchResponse<Map> searchResponse = elasticSearchService.matchAllServices();
		return new ResponseEntity<String>(searchResponse.hits().hits().toString(), HttpStatus.OK);
	}

	@GetMapping("/matchAllProducts")
	public ResponseEntity<?> matchAllProducts() throws IOException {
		SearchResponse<Product> searchResponse = elasticSearchService.matchAllProductsServices();
		List<Hit<Product>> listOfHits = searchResponse.hits().hits();
		List<Product> listOfProducts = new ArrayList<>();
		for (Hit<Product> hit : listOfHits) {
			listOfProducts.add(hit.source());
		}
		return new ResponseEntity<List<Product>>(listOfProducts, HttpStatus.OK);
	}

	@GetMapping("/matchAllProducts/{fieldValue}")
	public ResponseEntity<?> matchAllProductsWithName(@PathVariable String fieldValue) throws IOException {
		SearchResponse<Product> searchResponse = elasticSearchService.matchProductsWithName(fieldValue);
		System.out.println(searchResponse.hits().hits().toString());

		List<Hit<Product>> listOfHits = searchResponse.hits().hits();
		List<Product> listOfProducts = new ArrayList<>();
		for (Hit<Product> hit : listOfHits) {
			listOfProducts.add(hit.source());
		}
		return new ResponseEntity<List<Product>>(listOfProducts, HttpStatus.OK);
	}

	@GetMapping("/boolQuery/{productName}/{qty}")
	public ResponseEntity<?> boolQuery(@PathVariable String productName, @PathVariable Integer qty) throws IOException {
		SearchResponse<Product> searchResponse = elasticSearchService.boolQueryImpl(productName, qty);
		System.out.println(searchResponse.hits().hits().toString());

		List<Hit<Product>> listOfHits = searchResponse.hits().hits();
		List<Product> listOfProducts = new ArrayList<>();
		for (Hit<Product> hit : listOfHits) {
			listOfProducts.add(hit.source());
		}
		return new ResponseEntity<List<Product>>(listOfProducts, HttpStatus.OK);
	}

	@GetMapping("/fuzzySearch/{approximateProductName}")
	public ResponseEntity<?> fuzzySearch(@PathVariable String approximateProductName) throws IOException {
		SearchResponse<Product> searchResponse = elasticSearchService.fuzzySearch(approximateProductName);
		List<Hit<Product>> hitList = searchResponse.hits().hits();
		List<Product> productList = new ArrayList<>();
		for (Hit<Product> hit : hitList) {
			productList.add(hit.source());
		}
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("/autoSuggest/{partialProductName}")
	public ResponseEntity<?> autoSuggestProductSearch(@PathVariable String partialProductName) throws IOException {
		SearchResponse<Product> searchResponse = elasticSearchService.autoSuggestProduct(partialProductName);
		List<Hit<Product>> hitList = searchResponse.hits().hits();
		List<String> listOfProductNames = new ArrayList<>();
		for (Hit<Product> hit : hitList) {
			listOfProductNames.add(hit.source().getName());
		}
		return new ResponseEntity<List<String>>(listOfProductNames, HttpStatus.OK);
	}
	
	 @GetMapping("/multiMatch/{key}")
	    public ResponseEntity<?> multiMatch(@PathVariable String key , @RequestParam(value = "fields",  defaultValue = "name") String[] fields
	  ) throws IOException {
	        
	        List<String>  fieldsList = new ArrayList<String>();
	        fieldsList.toArray(fields);
	         SearchResponse<Product>  searchResponse =
	        		 elasticSearchService.multiMatch(key,fieldsList);
	        List<Hit<Product>> listOfHits = searchResponse.hits().hits();
	        List<Product>  listOfUsers = new ArrayList <>();

	          for(Hit<Product>  hit : listOfHits){
	              listOfUsers.add(hit.source());
	          }
	  		return new ResponseEntity<List<Product>>(listOfUsers, HttpStatus.OK);
	     }

}
