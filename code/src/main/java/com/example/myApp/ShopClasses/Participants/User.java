package com.example.myApp.ShopClasses.Participants;

import com.example.myApp.ShopClasses.Buy.CartImpl;
import com.example.myApp.ShopClasses.Buy.Product;
import com.example.myApp.exeptions.EmptyCartException;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.List;


@Entity
public class User extends ShopParticipant {
    @Embedded    // OneToOne vs Embedded
    CartImpl myCart;// = new CartImpl();
    private String email;
    @JsonCreator
    public User(String userName, String password, String email) {
        super(userName, password);
        this.email = email;
        myCart = new CartImpl(); // Блинов с. 837
    }
    public void addToMyCart(Product chosenProduct){
        myCart.addToList(chosenProduct);
    }
    public void showTheCatalog(){

    }
    public List<Product> showMyCart() throws EmptyCartException{
        if(this.myCart.isEmpty()){
            throw new EmptyCartException();
        }
       return myCart.showAllList();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void buy(){

    }

    public CartImpl getMyCart() {
        return myCart;
    }

    public void setMyCart(CartImpl myCart) {
        this.myCart = myCart;
    }

    public User() {
        super();
    }
}
