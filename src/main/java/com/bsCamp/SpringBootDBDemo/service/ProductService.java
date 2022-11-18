package com.bsCamp.SpringBootDBDemo.service;

import java.util.List;

import com.bsCamp.SpringBootDBDemo.entity.Product;

public interface ProductService {

	//crud
	
	public Product save(Product product);
	
	public List<Product> getProducts();
	
	public Product getProduct(int id);
	
	public Product updateProduct(Product product);
	
	public Product deleteProduct(int id);
	
	public List<Product> getProductwithQty(int qty);
}
