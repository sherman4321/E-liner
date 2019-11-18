package com.example.myApp;

import com.example.myApp.ShopClasses.Participants.Admin;
import com.example.myApp.ShopClasses.Participants.Enum.ParticipantRoleEnum;
import com.example.myApp.ShopClasses.Participants.ShopParticipant;
import com.example.myApp.ShopClasses.Participants.User;
import com.example.myApp.ShopClasses.Participants.Vendor;
import com.example.myApp.exeptions.EntityNotFoundException;
import com.example.myApp.exeptions.SuchUserAlreadyExistException;
import com.example.myApp.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/admins")
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//@PreAuthorize("#oauth2.hasScope('openid') and hasAuthority('ROLE_ADMIN')")
public class AdminController {
    @Autowired  // Autowired надо менять на private final с конструктором
    private ServiceImpl ServiceImpl;
    @GetMapping(path="/all") // get all
    public @ResponseBody
    Iterable<ShopParticipant> getAllUsers(){
        return ServiceImpl.getAllUsers();
    }

    @RequestMapping(value = "/users/{id}", // delete
            method = RequestMethod.DELETE, //
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser( @PathVariable("id") Integer id) {
        ServiceImpl.deleteUser(id);
    }

    @RequestMapping(value = "/users/{username}", // update
            method = RequestMethod.PUT, //
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public User updateUser(@RequestBody User participant,/* @PathVariable("id") Integer id)*/
                           @PathVariable("username") String username) {
        Optional<ShopParticipant> participantOld = ServiceImpl.readUserByName(participant.getUserName());
        if(!participantOld.isPresent()) {
            throw new EntityNotFoundException();
        }
        int id = participantOld.get().getAccountId();
        participant.setAccountId(id);
        return ServiceImpl.updateUser(participant);
    }

    @RequestMapping(value = "/users/vendor", // create vendor
            method = RequestMethod.POST, //
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Vendor addNewParticipant (@RequestBody @Valid Vendor newVendor){
        if(ServiceImpl.isExist(newVendor.getUserName())){
            throw new SuchUserAlreadyExistException();
        }
        newVendor.setRole(Collections.singleton(ParticipantRoleEnum.ROLE_VENDOR));
        newVendor.setActive(true);
        ServiceImpl.creatUser(newVendor);
        return newVendor;
    }

    @RequestMapping(value = "/users/admin", // create admin
            method = RequestMethod.POST, //
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Admin addNewParticipant (@RequestBody @Valid Admin newAdmin){
        if(ServiceImpl.isExist(newAdmin.getUserName())){
            throw new SuchUserAlreadyExistException();
        }
        newAdmin.setRole(Collections.singleton(ParticipantRoleEnum.ROLE_ADMIN));
        newAdmin.setActive(true);
        ServiceImpl.creatUser(newAdmin);
        return newAdmin;
    }
}
