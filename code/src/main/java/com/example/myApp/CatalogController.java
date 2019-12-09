package com.example.myApp;


import com.example.myApp.ShopClasses.Buy.Product;
import com.example.myApp.ShopClasses.Buy.ProductType;
import com.example.myApp.ShopClasses.Buy.View;
import com.example.myApp.ShopClasses.Participants.User;
import com.example.myApp.exeptions.ProductNotFoundException;
import com.example.myApp.service.ServiceImpl;
import com.example.myApp.service.ShopServiceImpl;
import com.example.myApp.service.TypeServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
    // Autowired надо менять на private final с конструктором
    private final ShopServiceImpl shopServiceImpl; // сервисы с интерфейсами
    private final TypeServiceImpl typeServiceImpl;
    private final ServiceImpl service;

    public CatalogController(ShopServiceImpl shopServiceImpl, TypeServiceImpl typeServiceImpl, ServiceImpl service) {
        this.shopServiceImpl = shopServiceImpl;
        this.typeServiceImpl = typeServiceImpl;
        this.service = service;
    }

    // @JsonView(View.GetProduct.class)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllProducts(@ModelAttribute("userForm") ArrayList<Product> products, Model model) {
        products = (ArrayList<Product>) shopServiceImpl.getAllProducts();
        model.addAttribute("products", products);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String current = authentication.getName();
        return "catalog";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String getCart(@ModelAttribute("userForm") ArrayList<Product> products, Model model) {
        products = (ArrayList<Product>) shopServiceImpl.getAllProducts();
        model.addAttribute("products", products);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String current = authentication.getName();
        User user = (User)service.readUserByName(current).get();
        List<Product> cart =  user.getMyCart().getUserCart().getList();
        model.addAttribute("cart", cart);
        return "cart";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public String addToCart(@ModelAttribute("product") Product product, Model model){
        //ModelAndView cart = new ModelAndView((productName));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String current = authentication.getName();
        User user = (User)service.readUserByName(current).get();
        Product productSQL =  shopServiceImpl.readProductByName(product.getName()).get();
        int index = user.getMyCart().getUserCart().getList().indexOf(productSQL);
        if(index == -1) {
            Product temp = shopServiceImpl.readProductByName(product.getName()).get();
            temp.setNumber(1);
            user.getMyCart().getUserCart().addToList(temp);
        }
        else{
            Product temp = user.getMyCart().getUserCart().getList().remove(index);
            temp.setNumber(temp.getNumber() + 1);
            user.getMyCart().getUserCart().getList().add(temp);
        }
        service.updateUser(user);
        return "redirect:/catalog/cart";
    }

    @RequestMapping(value = "/cart/remove", method = RequestMethod.POST)
    public String removeFromCart(@ModelAttribute("product") Product product, Model model){
        //ModelAndView cart = new ModelAndView((productName));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String current = authentication.getName();
        User user = (User)service.readUserByName(current).get();
        user.getMyCart().getUserCart().deleteFromList(product);
        //user.getMyCart().getUserCart().addToList(shopServiceImpl.readProductByName(product.getName()).get());
        service.updateUser(user);
        return "redirect:/catalog/cart";
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
        Collection<Product> collection = (Collection<Product>) shopServiceImpl.getAllProducts();
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
        Collection<Product> collection = (Collection<Product>) shopServiceImpl.getAllProducts();
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
        Optional<Product> product = shopServiceImpl.readProductByName(name);
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
