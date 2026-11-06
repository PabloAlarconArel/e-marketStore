package org.marketstore.productservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.marketstore.productservice.dto.request.ProductRequestDto;
import org.marketstore.productservice.dto.response.ApiResponse;
import org.marketstore.productservice.dto.response.ProductResponseDto;
import org.marketstore.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor

public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity <String> createProduct (@Valid @RequestBody ProductRequestDto request){
        productService.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Producto creado correctamente");
    }

    @GetMapping
    public List<ProductResponseDto> findAll(){
         return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> findById (@PathVariable String id){

        ProductResponseDto product = productService.findById(id);
        ApiResponse<ProductResponseDto> response =
                new ApiResponse<>(
                        201,
                        "Producto encontrado exitosamente",
                        product
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("code/{code}")
    public ResponseEntity<ProductResponseDto> findBySkuCode(@PathVariable String skuCode){
        return ResponseEntity.ok(productService.findBySkuCode(skuCode));
    }
    @GetMapping("category/{category}")
    public ResponseEntity<List<ProductResponseDto>> findByCategory(@PathVariable String category){
        return ResponseEntity.ok(productService.findByCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update( @PathVariable String id, @RequestBody ProductRequestDto request){
        ProductResponseDto updateProduct = productService.update(id,request);

        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        productService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Producto borrado correctamente");
    }



}
