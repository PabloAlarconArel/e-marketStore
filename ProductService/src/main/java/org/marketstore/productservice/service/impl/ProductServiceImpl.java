package org.marketstore.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marketstore.productservice.dto.request.ProductRequestDto;
import org.marketstore.productservice.dto.response.ProductResponseDto;
import org.marketstore.productservice.mapper.ProductMapper;
import org.marketstore.productservice.model.Product;
import org.marketstore.productservice.repository.ProductRepository;
import org.marketstore.productservice.service.ProductService;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public void createProduct(ProductRequestDto request){

        if(request.getSkuCode() != null &&
                productRepository.existsBySkuCode(request.getSkuCode())){
            throw new IllegalArgumentException("Ya existe un producto con el código"
                    +request.getSkuCode()
            );
        }

        Product product = productMapper.toProduct(request);
         productRepository.save(product);

        log.info("El producto {} ya fue creado exitosamente",product.getName());
    }

    @Override
    public ProductResponseDto findById(String id){

        Product product = productRepository.findById(id)
                .orElseThrow(()->
                        new ExpressionException(
                                "Producto no encontrado:"+ id
                        )
                );
        return productMapper.toResponse(product) ;
    }

    @Override
    public List<ProductResponseDto> findAll(){

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public List<ProductResponseDto> findByCategory(String category){
        return productRepository.findByCategory(category)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }
