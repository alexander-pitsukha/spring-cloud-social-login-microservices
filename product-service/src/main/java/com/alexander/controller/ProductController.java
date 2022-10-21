package com.alexander.controller;

import com.alexander.dto.ApiResponse;
import com.alexander.dto.ProductDto;
import com.alexander.service.ProductService;
import com.alexander.config.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(productService.findAll(page, size));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveProduct(@RequestBody @Validated ProductDto productDto) {
        productService.save(productDto);
        return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody @Validated ProductDto productDto) {
        productService.update(id, productDto);
        return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
    }

}
