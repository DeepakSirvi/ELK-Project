package com.elk.app.model;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "product")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

	private Long id;
	private String name;
	private String description;
	private Integer price;
	private Integer quantity;
	
}
