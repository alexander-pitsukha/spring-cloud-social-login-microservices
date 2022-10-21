package com.alexander.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.alexander.model.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
