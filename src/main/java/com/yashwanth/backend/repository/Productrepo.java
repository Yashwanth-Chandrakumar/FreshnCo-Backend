package com.yashwanth.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yashwanth.backend.model.Product;

public interface Productrepo extends JpaRepository<Product, Long> {
    // Additional methods if needed
}
