package com.mcp.server.repository;

import com.mcp.server.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStockGreaterThan(Integer stock);
    List<Product> findByStockGreaterThanAndCategoryContainingIgnoreCase(Integer stock, String category);
}
