package com.codecraftlabs.productservice.controller;

import com.codecraftlabs.productservice.dto.ProductRequest;
import com.codecraftlabs.productservice.dto.ProductResponse;
import com.codecraftlabs.productservice.model.Product;
import com.codecraftlabs.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class productController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProduct(@PathVariable String productId){
        return productService.findProductById(productId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Product modifyProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String productId){
        productService.deleteProduct(productId);
    }

}
