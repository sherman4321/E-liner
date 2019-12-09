package com.example.myApp;

import com.example.myApp.ShopClasses.Participants.Enum.ParticipantRoleEnum;
import com.example.myApp.ShopClasses.Participants.ShopParticipant;
import com.example.myApp.ShopClasses.Participants.User;
import com.example.myApp.exeptions.SuchUserAlreadyExistException;
import com.example.myApp.form.UserForm;
import com.example.myApp.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
//
//@EnableAuthorizationServer
//@EnableResourceServer
@Controller
public class MainController {
    @Autowired
    private ServiceImpl ServiceImpl;

//    @RequestMapping(value = "/registration", // create user
//            method = RequestMethod.POST, //
//            produces = { MediaType.APPLICATION_JSON_VALUE})
//    @ResponseStatus(HttpStatus.CREATED)
//    public User addNewParticipant (@RequestBody @Valid  User newUser){
//
//        if(ServiceImpl.isExist(newUser.getUserName())){
//            throw new SuchUserAlreadyExistException();
//        }
//        newUser.setRole(Collections.singleton(ParticipantRoleEnum.ROLE_USER));
//        newUser.setActive(true);
//        ServiceImpl.creatUser(newUser);
//        return newUser;
//    }
    @RequestMapping(value = { "/", "/main" }, method = RequestMethod.GET)
    public String mainPage(Model model) {
        //model.addAttribute("message", "Hi");
        return "mainPage";
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login(Model model) {
        //model.addAttribute("message", "Hi");
        return "login";
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.POST)
    public String login(Model model, @ModelAttribute("userForm") UserForm userForm) {
        //userForm.getName();
        return "redirect:/hello";
    }

    @RequestMapping(value = { "/registration" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {
//        UserForm userForm = new UserForm();
//        model.addAttribute("userForm", userForm);
        return "registration";
    }

    @RequestMapping(value = "/registration", // create user
            method = RequestMethod.POST, //
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public String addNewParticipant (Model model, @ModelAttribute("userForm") UserForm userForm){

        if(ServiceImpl.isExist(userForm.getName())){
            throw new SuchUserAlreadyExistException();
        }
        User newUser = new User(userForm.getName(), userForm.getPassword(), userForm.getEmail());
        newUser.setRole(Collections.singleton(ParticipantRoleEnum.ROLE_USER));
        newUser.setActive(true);
        ServiceImpl.creatUser(newUser);
        return "mainPage";
    }

    @RequestMapping(value = "/users/{id}", // read  нужно прятать пароль
            method = RequestMethod.GET, //
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ShopParticipant getUser(@PathVariable("id") Integer id) {
        return ServiceImpl.readUserById(id);
    }

}
