package com.example.myApp.form;

import java.util.Objects;

public class UserForm {
    private String name;
    private String password;
    private String email;

    public UserForm() {
    }

    public UserForm(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserForm userForm = (UserForm) o;
        return name.equals(userForm.name) &&
                password.equals(userForm.password) &&
                email.equals(userForm.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, email);
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
