package com.alexander.service.impl;

import com.alexander.dto.ProductDto;
import com.alexander.repository.ProductRepository;
import com.alexander.service.ProductService;
import com.alexander.exception.ResourceNotFoundException;
import com.alexander.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.alexander.config.AppConstants.ID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final DefaultConversionService defaultConversionService;

    @Override
    public Page<ProductDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> defaultConversionService.convert(product, ProductDto.class));
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", ID, id));
        return defaultConversionService.convert(product, ProductDto.class);
    }

    @Transactional
    @Override
    public ProductDto save(ProductDto productDto) {
        Product product = Objects.requireNonNull(defaultConversionService.convert(productDto, Product.class));
        return defaultConversionService.convert(productRepository.save(product), ProductDto.class);
    }

    @Transactional
    @Override
    public void update(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", ID, id));
        product.setName(productDto.getName());
        product.setVersion(productDto.getVersion());
        product.setEdition(productDto.getEdition());
        product.setDescription(productDto.getDescription());
        product.setValidFrom(productDto.getValidFrom());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
