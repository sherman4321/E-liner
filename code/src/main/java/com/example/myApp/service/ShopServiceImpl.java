package com.example.myApp.service;

import com.example.myApp.ShopClasses.Buy.Product;
import com.example.myApp.ShopClasses.Buy.ProductType;
import com.example.myApp.exeptions.ProductNotFoundException;
import com.example.myApp.repository.ProductCrudRepository;
import com.example.myApp.repository.ProductTypeCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ShopServiceImpl implements AbstractService {
    private final ProductCrudRepository productRepository;
   // private final ProductTypeCrudRepository typeRepository;

    @Autowired
    public ShopServiceImpl(ProductCrudRepository productRepository){
        this.productRepository = productRepository;
       // this.typeRepository = typeRepository;
    }

    public Product createProduct (Product newProduct){
        productRepository.save(newProduct);
        return newProduct;
    }



    public Optional<Product> readProductById(Integer id) throws ProductNotFoundException {
        Optional<Product> n;
        n = productRepository.findById(id);
        if(!n.isPresent()){
            throw new ProductNotFoundException();
        }
        return n;
    }

    public Optional<Product> readProductByName(String userName) throws ProductNotFoundException {
        Optional<Product> n;
        n = productRepository.findByName(userName);
        if(!n.isPresent()){
            throw new ProductNotFoundException();
        }
        return n;
    }



    public boolean isExist(String userName){
        Optional<Product> n;
        n = productRepository.findByName(userName);
        return n.isPresent();
    }

    @Transactional
    public Product updateProduct(Product account) {
        productRepository.save(account);
        return account;
    }

    public void deleteUser(Integer id) throws ProductNotFoundException{
        productRepository.deleteById(id);
    }
    //
    public Iterable<Product> getAllProducts(){
        return productRepository.findAll();
    }

}
