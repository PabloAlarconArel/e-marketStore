package org.marketstore.productservice.repository;

import org.marketstore.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product,String> {

    Optional<Product> findBySkuCode (String skuCode);

    boolean existsBySkuCode(String skuCode);

    List<Product>findByCategory(String category);
}
