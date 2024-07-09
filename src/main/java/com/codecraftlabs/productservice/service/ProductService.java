package com.codecraftlabs.productservice.service;

import com.codecraftlabs.productservice.Repository.ProductRepository;
import com.codecraftlabs.productservice.dto.ProductRequest;
import com.codecraftlabs.productservice.dto.ProductResponse;
import com.codecraftlabs.productservice.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    //Add Product
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getProductDescription())
                .productPrice(productRequest.getProductPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is created.", product.getProductId());
    }

    //Get Product by ID
    public Product findProductById(String productId){
        return productRepository.findById(productId).get();
    }

    //Get All Products
    public List<ProductResponse> findAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .build();
    }

    //Update Product
    public Product updateProduct(Product product){
        product.setProductId(product.getProductId());
        return productRepository.save(product);
    }

    //Delete Product
    public void deleteProduct(String productId){
        productRepository.deleteById(productId);
    }
}
