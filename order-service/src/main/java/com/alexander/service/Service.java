package com.alexander.service;

import org.springframework.data.domain.Page;

public interface Service<T, I> {

    Page<T> findAll(int page, int size);

    T findById(I id);

    T save(T t);

    void update(I id, T t);

    void deleteById(I id);

}
