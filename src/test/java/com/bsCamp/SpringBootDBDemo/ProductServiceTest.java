package com.bsCamp.SpringBootDBDemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bsCamp.SpringBootDBDemo.entity.Product;
import com.bsCamp.SpringBootDBDemo.repository.ProductRepository;
import com.bsCamp.SpringBootDBDemo.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@Mock
	ProductRepository productRepository;
	
	@InjectMocks
	ProductServiceImpl productServiceImpl;
	
	Product product = new Product();
	Product product2 = new Product();
	List<Product> products = new ArrayList<Product>();
	
	@BeforeEach
	private void setup() {
		product.setP_id(1);
		product.setPname("coffee");
		product.setQuantity(500);
		product.setUnit_price(400);
		product2.setP_id(2);
		product2.setPname("noodle");
		product2.setQuantity(400);
		product2.setUnit_price(500);
		products.add(product);
		products.add(product2);
	}
	
	@DisplayName("Test for save Product in service layer")
	@Test
	void givenSaveProduct_sendProductData_thenReturnProductObj() {
		given(productRepository.save(product)).willReturn(product);
		Product savedProduct = productServiceImpl.save(product);
		assertThat(savedProduct).isNotNull();
	}
	
	@DisplayName("Test for All Product in service layer")
	@Test
	void givenGetProduct_thenReturnProductwithList() {
		given(productRepository.findAll()).willReturn(products);
		List<Product> products = productServiceImpl.getProducts();
		assertThat(products).isNotNull();
		assertThat(products).contains(product);
		assertThat(products.size()).isEqualTo(2);
	}
	
	@DisplayName("Test for get Product by Id in service layer")
	@Test
	void givenGetProduct_sendID_thenReturnProductwithID() {
		given(productRepository.findById(1)).willReturn(Optional.of(product));
		Product productWithID = productServiceImpl.getProduct(1);
		assertThat(productWithID.getP_id()).isEqualTo(1);
		assertThat(productWithID.getPname()).isEqualTo(product.getPname());
		assertThat(productWithID.getQuantity()).isGreaterThan(300);
	}
	
	@DisplayName("Test for delete Product by Id in service layer")
	@Test
	void givenDeleteProduct_sendID_thenNull() {
		willDoNothing().given(productRepository).deleteById(1);		
		productServiceImpl.deleteProduct(1);
		verify(productRepository, times(1)).deleteById(1);

	}
	
	@DisplayName("Test for update Product by Id in service layer")
	@Test
	void givenUpdateProduct_sendProduct_thenReturnUpdatedProductObj() {
		given(productRepository.findById(product.getP_id())).willReturn(Optional.of(product));
		given(productRepository.save(product)).willReturn(product2);
		Product updatedProduct = productServiceImpl.updateProduct(product);
		assertThat(updatedProduct.getPname()).isEqualTo(product2.getPname());
		assertThat(updatedProduct.getUnit_price()).isEqualTo(product2.getUnit_price());
		assertThat(updatedProduct.getQuantity()).isEqualTo(product2.getQuantity());
	}

}
