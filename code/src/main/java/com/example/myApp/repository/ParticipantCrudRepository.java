package com.example.myApp.repository;

import com.example.myApp.ShopClasses.Participants.Admin;
import com.example.myApp.ShopClasses.Participants.ShopParticipant;
//import com.example.myApp.ShopClasses.Participants.User;
//import com.example.myApp.ShopClasses.Participants.User;
import com.example.myApp.ShopClasses.Participants.User;
import com.example.myApp.ShopClasses.Participants.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ParticipantCrudRepository extends CrudRepository<ShopParticipant, Integer> {
    Optional<ShopParticipant> findByUserName(String userName);   //  ??????? MappedSuperclass не ищет в бд
    //Optional<Admin> findByAdminName(String userName);
    //Optional<Vendor> findByVendorName(String userName);
//    ShopParticipant creatUser(ShopParticipant account);
//    ShopParticipant readUser(String userName);
//    ShopParticipant updateUser(ShopParticipant account);
//    void deleteUser(String userName);
}
