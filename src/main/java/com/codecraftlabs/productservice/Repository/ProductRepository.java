package com.codecraftlabs.productservice.Repository;

import com.codecraftlabs.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository <Product, String>{
}
