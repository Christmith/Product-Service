package com.codecraftlabs.productservice.service;

import com.codecraftlabs.productservice.Repository.ProductRepository;
import com.codecraftlabs.productservice.dto.ProductRequest;
import com.codecraftlabs.productservice.dto.ProductResponse;
import com.codecraftlabs.productservice.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    // Add Product
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
            .productName(productRequest.getProductName())
            .productDescription(productRequest.getProductDescription())
            .productPrice(productRequest.getProductPrice())
            .build();

        productRepository.save(product);
        log.info("Product {} is created.", product.getProductId());
    }

    // Get All Products
    public List<ProductResponse> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    // Get Product by ID
    public ProductResponse findProductById(String productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found"));
        return mapToProductResponse(product);
    }

    // Update Product
    public void updateProduct(String productId, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(productId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found"));

        Product updatedProduct = Product.builder()
            .productId(existingProduct.getProductId())
            .productName(productRequest.getProductName())
            .productDescription(productRequest.getProductDescription())
            .productPrice(productRequest.getProductPrice())
            .build();

        productRepository.save(updatedProduct);
        log.info("Product {} is updated.", productId);
    }

    // Delete Product
    public void deleteProduct(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResponseStatusException(NOT_FOUND, "Product not found");
        }

        productRepository.deleteById(productId);
        log.info("Product {} is deleted.", productId);
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
            .productId(product.getProductId())
            .productName(product.getProductName())
            .productDescription(product.getProductDescription())
            .productPrice(product.getProductPrice())
            .build();
    }
}
