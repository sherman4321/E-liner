package com.example.myApp.ShopClasses.Participants.Enum;

import org.springframework.security.core.GrantedAuthority;

public enum ParticipantRoleEnum implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_VENDOR;

    ParticipantRoleEnum() {
    };

    @Override
    public String getAuthority() {
        return name();
    }

}
