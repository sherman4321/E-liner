package com.example.myApp.ShopClasses.Buy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProductType {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonView(View.GetType.class)
    private int id;
    @JsonView(View.GetType.class)
    private String type;

//    @JsonView(View.GetFullType.class)
//    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="types")
//    private Set<Subcategory> subcategories = new HashSet<Subcategory>();

   @JsonView(View.GetFullType.class)
    @OneToMany(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST}, mappedBy="type")
   @JsonBackReference
    private Set<Product> products = new HashSet<Product>();

    public ProductType() {
    }

    @JsonCreator
    public ProductType(String type) {
        this.type = type;

        //subcategories = new HashSet<Subcategory>();
       //products = new HashSet<Product>();
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public Set<Subcategory> getSubcategories() {
//        return subcategories;
//    }
//
//    public void setSubcategories(Set<Subcategory> subcategories) {
//        this.subcategories = subcategories;
//    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void removeProduct(Product product){
        products.remove(product);
    }

//    public void addSubcategory(Subcategory subcategory){
//        subcategories.add(subcategory);
//    }
//
//    public void removeSubcategory(Subcategory subcategory){
//        subcategories.remove(subcategory);
//    }
}
