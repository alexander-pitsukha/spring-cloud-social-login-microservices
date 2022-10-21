package com.alexander.converter;

import com.alexander.dto.ProductDto;
import com.alexander.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoToProductConverter implements Converter<ProductDto, Product> {

    @Override
    public Product convert(ProductDto source) {
        Product product = new Product();
        product.setName(source.getName());
        product.setVersion(source.getVersion());
        product.setEdition(source.getEdition());
        product.setDescription(source.getDescription());
        product.setValidFrom(source.getValidFrom());
        return product;
    }

}
