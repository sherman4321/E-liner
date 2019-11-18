package com.example.myApp.repository;

import com.example.myApp.ShopClasses.Buy.ProductType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductTypeCrudRepository extends CrudRepository<ProductType,Integer> {
    Optional<ProductType> findByType(String type);
}
