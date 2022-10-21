package com.alexander.converter;

import com.alexander.dto.ProductDto;
import com.alexander.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductDtoConverter implements Converter<Product, ProductDto> {

    @Override
    public ProductDto convert(Product source) {
        ProductDto productDto = new ProductDto();
        productDto.setId(source.getId());
        productDto.setName(source.getName());
        productDto.setVersion(source.getVersion());
        productDto.setEdition(source.getEdition());
        productDto.setDescription(source.getDescription());
        productDto.setValidFrom(source.getValidFrom());
        return productDto;
    }

}
