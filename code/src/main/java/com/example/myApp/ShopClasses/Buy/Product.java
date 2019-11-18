package com.example.myApp.ShopClasses.Buy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Embeddable
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonView(View.GetProduct.class)
    private int productId;
    @JsonView(View.GetProduct.class)
    private String name;
    @JsonView(View.GetProduct.class)
    private float cost;
    @JsonView(View.GetProduct.class)
    private int number;
    @JsonView(View.GetProduct.class)
    @ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonManagedReference
    private ProductType type;


    public Product() {
    }

    @JsonCreator
    public Product(String name, float cost, int number, ProductType type) {
        this.name = name;
        this.cost = cost;
        this.number = number;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public float getCost() {
        return cost;
    }

    public int getNumber() { return number;}

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getName().equals(product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
