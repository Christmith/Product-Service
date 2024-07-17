package com.codecraftlabs.productservice;

import com.codecraftlabs.productservice.Repository.ProductRepository;
import com.codecraftlabs.productservice.dto.ProductRequest;
import com.codecraftlabs.productservice.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.4");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@AfterEach
	void tearDown() {
		productRepository.deleteAll();
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
			.contentType(MediaType.APPLICATION_JSON)
			.content(productRequestString))
			.andExpect(status().isCreated());

        Assertions.assertEquals(1, productRepository.findAll().size());
	}

	@Test
	void shouldFetchAllProducts() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequsetString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
			.contentType(MediaType.APPLICATION_JSON)
			.content(productRequsetString))
			.andExpect(status().isCreated());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	@Test
	void shouldFetchProductById() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequsetString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
		    .contentType(MediaType.APPLICATION_JSON)
			.content(productRequsetString))
			.andExpect(status().isCreated());

		String productId = productRepository.findAll().get(0).getProductId();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/products/"+ productId)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		Assertions.assertTrue(productRepository.findById(productId).isPresent());
	}

	@Test
	void shouldUpdateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
			.contentType(MediaType.APPLICATION_JSON)
			.content(productRequestString))
			.andExpect(status().isCreated());

		ProductRequest updatedProductRequest = getUpdateProductRequest();
		String updatedProductRequestString = objectMapper.writeValueAsString(updatedProductRequest);

		String productId = productRepository.findAll().get(0).getProductId();
		mockMvc.perform(MockMvcRequestBuilders.put("/api/products/"+ productId)
			.contentType(MediaType.APPLICATION_JSON)
			.content(updatedProductRequestString))
			.andExpect(status().isOk());

		Product updatedProduct =productRepository.findById(productId).get();

		Assertions.assertEquals("updated name", updatedProduct.getProductName());
		Assertions.assertEquals("updated description",updatedProduct.getProductDescription());
		Assertions.assertEquals(BigDecimal.valueOf(400000), updatedProduct.getProductPrice());
	}

	@Test
	void shouldDeleteProduct() throws Exception{
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
			.contentType(MediaType.APPLICATION_JSON)
			.content(productRequestString))
			.andExpect(status().isCreated());

		String productId = productRepository.findAll().get(0).getProductId();
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/"+ productId)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());

		Assertions.assertFalse(productRepository.existsById(productId));
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
			.productName("iPhone 14 Pro")
			.productDescription("This is also a smart phone")
			.productPrice(BigDecimal.valueOf(300000))
			.build();
	}

	private ProductRequest getUpdateProductRequest() {
		return ProductRequest.builder()
			.productName("updated name")
			.productDescription("updated description")
			.productPrice(BigDecimal.valueOf(400000))
			.build();
	}

}
