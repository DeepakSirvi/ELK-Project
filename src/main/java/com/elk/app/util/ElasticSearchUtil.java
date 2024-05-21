package com.elk.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import lombok.val;

public class ElasticSearchUtil {

//	This return the query to us which we pass to search method of elastic repo 
	public static Supplier<Query> supplier() {
		Supplier<Query> supplier = () -> Query.of(q -> q.matchAll(matchAllQuery()));
		return supplier;
	};

	public static MatchAllQuery matchAllQuery() {
		val matchAllQuery = new MatchAllQuery.Builder();
		return matchAllQuery.build();
	}
	
//	--------------------------------------------------------------


	public static Supplier<Query> supplierWithNameField(String fieldValue) {
		Supplier<Query> supplier = () -> Query.of(q -> q.match(matchQueryWithNameField(fieldValue)));
		return supplier;
	}
	

//	To build match query and supply to Supplier for standard way which return the main query
//	Supplier purpose This is a functional interface and can therefore be used as the assignment target for a lambda expression or method reference
	public static MatchQuery matchQueryWithNameField(String fieldValue) {
		val matchQuery = new MatchQuery.Builder();
		return matchQuery.field("name").query(fieldValue).build(); // only change filed name
	}
	
//	 -----------------------------------------------------------------------------

	
	 public static Supplier<Query> supplierQueryForBoolQuery(String productName, Integer qty){
	        Supplier<Query> supplier = ()->Query.of(q->q.bool(boolQuery(productName,qty)));
	        return supplier;
	    }
	 
	 
	  public static BoolQuery boolQuery(String productName, Integer qty){

	        val boolQuery  = new BoolQuery.Builder();
	        return boolQuery.filter(termQuery(productName)).must(matchQuery(qty)).build();
	     }
	  
	  public static List<Query> termQuery(String productName){
	        final List<Query> terms = new ArrayList<>();
	        val termQuery = new TermQuery.Builder();
	        terms.add(Query.of(q->q.term(termQuery.field("name").value(productName).build())));
	        return terms;
	      }

	    public static List<Query> matchQuery(Integer qty){
	        final List<Query> matches = new ArrayList<>();
	        val matchQuery = new MatchQuery.Builder();
	        matches.add(Query.of(q->q.match(matchQuery.field("quantity").query(qty).build())));
	        return matches;
	    }
//------------------------------------------------------------------------
	    
	    public static Supplier<Query> createSupplierQuery(String approximateProductName){
	        Supplier<Query> supplier = ()->Query.of(q->q.fuzzy(createFuzzyQuery(approximateProductName)));
	        return  supplier;
	    }


	    public static FuzzyQuery createFuzzyQuery(String approximateProductName){
	        val fuzzyQuery  = new FuzzyQuery.Builder();
	        return  fuzzyQuery.field("name").value(approximateProductName).build();

	    }
	    
//	    -------------------------------------------------
	    
	    public static Supplier<Query> createSupplierAutoSuggest(String partialProductName){

	        Supplier<Query> supplier = ()->Query.of(q->q.match(createAutoSuggestMatchQuery(partialProductName)));
	    return  supplier;
	    }
	    public static MatchQuery createAutoSuggestMatchQuery(String partialProductName){
	        val autoSuggestQuery = new MatchQuery.Builder();
	        return  autoSuggestQuery.field("description").query(partialProductName).analyzer("standard").build();

	    }
//	    auto suggest with multi search
	    
//	    public static Supplier<Query> createSupplierAutoSuggest(String partialProductName){
//
//	        Supplier<Query> supplier = ()->Query.of(q->q.multiMatch(createAutoSuggestMatchQuery(partialProductName)));
//	    return  supplier;
//	    }
//	    public static MultiMatchQuery createAutoSuggestMatchQuery(String partialProductName){
//	        val autoSuggestQuery = new MultiMatchQuery.Builder();
//	        List<String> list = new ArrayList<>();
//	        list.add("description");
//	        list.add("name");
//	        return  autoSuggestQuery.fields(list).query(partialProductName).analyzer("standard").build();
//	        
//
//	    }
	    
	    
//     ---------------------------------------------------------
	    
	    public static Supplier<Query>  supplierQueryForMultiMatchQuery(String key , List<String> fields){

	         Supplier<Query> supplier = ()->Query.of(q->q.multiMatch(multiMatchQuery(key, fields)));
	     return supplier;
	     }
	     public static MultiMatchQuery multiMatchQuery(String key , List<String> fields ){

	         val multimatch = new MultiMatchQuery.Builder();
	       return   multimatch.query(key).fields(fields).build();
	     }
	     
//	     this is a code  to create filter and analyser for auto search which we have to do in start of index creation we have to run this in kibana dev tool
//	     PUT products
//	     {
//	       "settings": {
//	         "analysis": {
//	           "filter": {
//	             "ngram_filter":{
//	               "type": "edge_ngram",
//	               "min_gram":1,
//	               "max_gram":6
//	             }
//	           },
//	           "analyzer": {
//	             "ngram_analyzer":{
//	               "type": "custom",
//	               "tokenizer": "standard",
//	               "filter":[
//	                 "lowercase",
//	                 "ngram_filter"]
//	             }
//	           }
//	         }
//	       }
//	     }
//
//	     PUT product/_mapping
//	     {
//	       "properties":{
//	         "name":{
//	           "type":"text",
//	           "analyser":"ngram_analyser"
//	         }
//	       }
//	     }
	    
}
