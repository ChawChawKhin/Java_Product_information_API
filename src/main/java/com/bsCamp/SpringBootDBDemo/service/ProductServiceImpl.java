package com.bsCamp.SpringBootDBDemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsCamp.SpringBootDBDemo.entity.Product;
import com.bsCamp.SpringBootDBDemo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Override
	public Product save(Product product) {
		Product newProduct = productRepository.save(product);
		return newProduct;
	}

	@Override
	public List<Product> getProducts() {
		List<Product> products = productRepository.findAll();
		return products;
	}

	@Override
	public Product getProduct(int id) {
		Product product = productRepository.findById(id).orElse(null);
		return product;
	}

	@Override
	public Product updateProduct(Product product) {
		Product original = productRepository.findById(product.getP_id()).orElse(null);
		//Product original = getProduct(product.getP_id());
		if(original != null) {
			original.setPname(product.getPname());
			original.setQuantity(product.getQuantity());
			original.setUnit_price(product.getUnit_price());
			original = productRepository.save(original);
		}
		return original;
	}

	@Override
	public Product deleteProduct(int id) {
		productRepository.deleteById(id);
		return null;
	}

	@Override
	public List<Product> getProductwithQty(int qty) {
		// TODO Auto-generated method stub
		return productRepository.getOver100Qty(qty);
	}
	
	

}
