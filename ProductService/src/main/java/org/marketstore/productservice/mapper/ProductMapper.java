package org.marketstore.productservice.mapper;

import org.mapstruct.Mapper;
import org.marketstore.productservice.dto.request.ProductRequestDto;
import org.marketstore.productservice.dto.response.ProductResponseDto;
import org.marketstore.productservice.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductRequestDto product);

    ProductResponseDto toResponse(Product product);
}
