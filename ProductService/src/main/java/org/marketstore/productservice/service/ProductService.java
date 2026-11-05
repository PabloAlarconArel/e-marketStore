package org.marketstore.productservice.service;

import org.marketstore.productservice.dto.request.ProductRequestDto;
import org.marketstore.productservice.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {

    void createProduct(ProductRequestDto request);

    ProductResponseDto findById(String id);

    List<ProductResponseDto> findAll();

    List<ProductResponseDto> findByCategory(String category);
