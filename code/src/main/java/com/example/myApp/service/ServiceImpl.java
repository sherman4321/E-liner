package com.example.myApp.service;

import com.example.myApp.ShopClasses.Participants.ShopParticipant;
import com.example.myApp.ShopClasses.Participants.User;
import com.example.myApp.exeptions.EntityNotFoundException;
import com.example.myApp.repository.ParticipantCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ServiceImpl implements AbstractService, UserDetailsService {
    private final ParticipantCrudRepository repository;

    @Autowired
    public ServiceImpl(ParticipantCrudRepository repository){
        this.repository = repository;
    }

    public ShopParticipant creatUser(ShopParticipant account) {
        repository.save(account);
        return account;
    }

    public ShopParticipant readUserById(Integer id) {
       return repository.findById(id)
               .orElseThrow(EntityNotFoundException::new);
    }

    public Optional<ShopParticipant> readUserByName(String userName) throws EntityNotFoundException {
        Optional<ShopParticipant> n;
        n = repository.findByUserName(userName);
        if(!n.isPresent()){
            throw new EntityNotFoundException();
        }
        return n;
    }

    public boolean isExist(String userName){
        Optional<ShopParticipant> n;
        n = repository.findByUserName(userName);
        return n.isPresent();
    }

@Transactional
    public User updateUser(User account) {
        repository.save(account);
        return account;
    }

    public void deleteUser(Integer id) throws EntityNotFoundException{
        repository.deleteById(id);
    }
//
    public Iterable<ShopParticipant> getAllUsers(){
        return repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws EntityNotFoundException {
        Optional<ShopParticipant> participant = repository.findByUserName(userName);
        if(!participant.isPresent()){
            throw new EntityNotFoundException();
        }
        return participant.get();
    }
//
//    public List<Product> showCart(String userName) {
//        ShopParticipant currentUser = UserMap.get(userName);
//        return currentUser.showMyCart();
//    }
}
