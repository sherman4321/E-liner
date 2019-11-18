package com.example.myApp.repository;

import com.example.myApp.ShopClasses.Buy.Product;
import com.example.myApp.ShopClasses.Buy.ProductType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductCrudRepository extends CrudRepository<Product, Integer> {
    Optional<Product> findByName(String name);
}
