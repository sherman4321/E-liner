package com.example.myApp;

import com.example.myApp.ShopClasses.Buy.Product;
import com.example.myApp.ShopClasses.Buy.ProductType;
import com.example.myApp.ShopClasses.Buy.View;
import com.example.myApp.exeptions.SuchUserAlreadyExistException;
import com.example.myApp.service.ShopServiceImpl;
import com.example.myApp.service.TypeServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired  // Autowired надо менять на private final с конструктором
    private ShopServiceImpl ShopServiceImpl;
    @Autowired
    private TypeServiceImpl typeServiceImpl;

    @JsonView(View.GetProduct.class)
    @RequestMapping(value = "/new", // create product
            method = RequestMethod.POST, //
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Product addNewProduct(@RequestBody @Valid Product newProduct) {
        if (ShopServiceImpl.isExist(newProduct.getName())) {
            throw new SuchUserAlreadyExistException();
        }
        String typeName = newProduct.getType().getType();
        Optional<ProductType> type = typeServiceImpl.readType(typeName);
        if(type.isPresent()){
            newProduct.setType(type.get());
            ShopServiceImpl.createProduct(newProduct);
            return newProduct;
        }
        else{
            ShopServiceImpl.createProduct(newProduct);
            return newProduct;
        }
    }

    //@JsonView(View.GetType.class)
    @RequestMapping(value = "/types/new", // create product
            method = RequestMethod.POST, //
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ProductType addNewType(@RequestBody @Valid ProductType newProductType) {
        if (ShopServiceImpl.isExist(newProductType.getType())) {
            throw new SuchUserAlreadyExistException();
        }
        typeServiceImpl.createProductType(newProductType);
            return newProductType;
    }

}
