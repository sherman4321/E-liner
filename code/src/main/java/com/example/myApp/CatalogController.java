package com.example.myApp;


import com.example.myApp.ShopClasses.Buy.Product;
import com.example.myApp.ShopClasses.Buy.ProductType;
import com.example.myApp.ShopClasses.Buy.View;
import com.example.myApp.exeptions.ProductNotFoundException;
import com.example.myApp.service.ShopServiceImpl;
import com.example.myApp.service.TypeServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
    // Autowired надо менять на private final с конструктором
    private final com.example.myApp.service.ShopServiceImpl ShopServiceImpl; // сервисы с интерфейсами
    private final TypeServiceImpl typeServiceImpl;

    public CatalogController(com.example.myApp.service.ShopServiceImpl ShopServiceImpl, TypeServiceImpl typeServiceImpl) {
        this.ShopServiceImpl = ShopServiceImpl;
        this.typeServiceImpl = typeServiceImpl;
    }

    @JsonView(View.GetProduct.class)
    @RequestMapping(value = "/all",
            method = RequestMethod.GET, //
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Iterable<Product> getAllProducts() {
        return ShopServiceImpl.getAllProducts();
    }

    @JsonView(View.GetProduct.class)
    @RequestMapping(value = "/types/all",
            method = RequestMethod.GET, //
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Iterable<ProductType> getAllTypes() {
        return typeServiceImpl.getAllTypes();
    }

    @JsonView(View.GetProduct.class)
    @RequestMapping(value = "/types/{name}/all",
            method = RequestMethod.GET, //
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Iterable<Product> getProductsByType( @PathVariable("name") String name) {
        Collection<Product> collection = (Collection<Product>) ShopServiceImpl.getAllProducts();
        return collection.stream()
                .filter((product -> Objects.equals(product.getType().getType(), name)))
                .collect(Collectors.toSet());
    }

    @JsonView(View.GetProduct.class)
    @RequestMapping(value = "/types/all/sort",
            method = RequestMethod.GET, //
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    List<Product> getProductsSortedByCost() {
        Collection<Product> collection = (Collection<Product>) ShopServiceImpl.getAllProducts();
        List<Product> all = collection.stream().sorted(Comparator.comparing(Product::getCost)).collect(Collectors.toList());
        return all;
    }


    @JsonView(View.GetProduct.class)
    @RequestMapping(value = "/{name}",
            method = RequestMethod.GET, //
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Product readProduct(//@RequestBody Product participant,/* @PathVariable("id") Integer id)*/
                               @PathVariable("name") String name) {
        Optional<Product> product = ShopServiceImpl.readProductByName(name);
        if(!product.isPresent()) {
            throw new ProductNotFoundException();
        }
        return product.get();
    }

    @JsonView(View.GetFullType.class)
    @RequestMapping(value = "/types/{name}",
            method = RequestMethod.GET, //
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ProductType readType(//@RequestBody Product participant,/* @PathVariable("id") Integer id)*/
                                @PathVariable("name") String name) {
        Optional<ProductType> type = typeServiceImpl.readType(name);
        if(!type.isPresent()) {
            throw new ProductNotFoundException();
        }
        return type.get();
    }

}
