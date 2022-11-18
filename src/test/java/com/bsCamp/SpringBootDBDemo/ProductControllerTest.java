package com.bsCamp.SpringBootDBDemo;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bsCamp.SpringBootDBDemo.entity.Product;
import com.bsCamp.SpringBootDBDemo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;


@WebMvcTest
public class ProductControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ProductService productService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	List<Product> products =  new ArrayList<Product>();
	
	Product product1 = new Product();
	Product product2 = new Product();
	
	@BeforeEach
	void setup() {
		product1.setP_id(1);
		product1.setPname("coffee");
		product1.setQuantity(500);
		product1.setUnit_price(400);
		
		product2.setP_id(2);
		product2.setPname("noodle");
		product2.setQuantity(400);
		product2.setUnit_price(500);
		
		products.add(product1);
		products.add(product2);
	}
	
	@DisplayName("Test for fetching all products in controller")
	@Test
	public void givenToGetAllProduct_thenReturnListAllProduct() throws Exception {
		given(productService.getProducts()).willReturn(products);
		ResultActions response = mockMvc.perform(get("/product/get/products"));
		response
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(products.size())))
			.andExpect(jsonPath("$[0].p_id", is(product1.getP_id())));
	}
	
	@DisplayName("Test for product by ID in controller")
	@Test
	public void givenToGetProduct_sendID_thenReturnListProductWithID() throws Exception {
		given(productService.getProduct(product1.getP_id())).willReturn(product1);
		ResultActions response = mockMvc.perform(get("/product/get/product/{id}", product1.getP_id()));
		response
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.p_id", is(product1.getP_id())))
			.andExpect(jsonPath("$.pname", is(product1.getPname())));
	}
	
	@DisplayName("Test for saving product in controller")
	@Test
	public void giveSaveProduct_SendProductData_thenReturnProductObject() throws Exception{
		given(productService.save(
				any(Product.class)
				)).willAnswer((invocation)->invocation.getArgument(0));
		ResultActions response = mockMvc.perform(
				post("/product/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product1))
				);
		
		response
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.p_id", is(product1.getP_id())))
			.andExpect(jsonPath("$.pname", is(product1.getPname())));
	}
	
	@DisplayName("Test for updating product in controller")
	@Test
	public void giveUpdateProduct_SendProduct_thenReturnUpdatedProductObject() throws Exception{
		given(productService.getProduct(product1.getP_id())).willReturn(product1);
		given(productService.updateProduct(
				any(Product.class)
				)).willAnswer((invocation)->invocation.getArgument(0));
		ResultActions updatedResponse = mockMvc.perform(
				put("/product//update/{id}",product1.getP_id())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product2))
				);
		updatedResponse
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.p_id", is(product2.getP_id())))
			.andExpect(jsonPath("$.pname", is(product2.getPname())));;	
	}
	
	@DisplayName("Test for deleting product in controller")
	@Test
	public void giveDeleteProduct_SendID_thenNull() throws Exception{
		//willDoNothing().given(productService).deleteProduct(product1.getP_id());
		ResultActions deletedResponse = mockMvc.perform(
				delete("/product/delete/{id}",product1.getP_id())		
				);
		deletedResponse
			.andExpect(status().isOk());
		
	}
	
	
	
	
}
