package com.example.myApp.service;

import com.example.myApp.ShopClasses.Buy.ProductType;
import com.example.myApp.exeptions.ProductNotFoundException;
import com.example.myApp.repository.ProductTypeCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeServiceImpl implements AbstractService {
    private final ProductTypeCrudRepository typeRepository;

    @Autowired
    public TypeServiceImpl(ProductTypeCrudRepository typeRepository){
        this.typeRepository = typeRepository;
    }

    public Optional<ProductType> readType(String type) throws ProductNotFoundException {
        Optional<ProductType> n = typeRepository.findByType(type);
        if(!n.isPresent()){
            throw new ProductNotFoundException();
        }
        return n;
    }

    public ProductType createProductType (ProductType newProductType){
        typeRepository.save(newProductType);
        return newProductType;
    }

    public Iterable<ProductType> getAllTypes(){
        return typeRepository.findAll();
    }
}
