//package com.example.myApp.ShopClasses.Buy;
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//public class Subcategory {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private int id;
//    private String name;
//    private CategoryEnum category;
//    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<ProductType> types;
//
//    public Subcategory() {
//    }
//
//    public Subcategory(String name, CategoryEnum category, Set<ProductType> types) {
//        this.name = name;
//        this.category = category;
//        this.types = types;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public CategoryEnum getCategory() {
//        return category;
//    }
//
//    public void setCategory(CategoryEnum category) {
//        this.category = category;
//    }
//
//    public Set<ProductType> getTypes() {
//        return types;
//    }
//
//    public void setTypes(Set<ProductType> types) {
//        this.types = types;
//    }
//
//    public void addType(ProductType type){
//        types.add(type);
//    }
//
//    public void removeType(ProductType type){
//        types.remove(type);
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//}
