package com.example.apod.model;

import com.example.apod.R;

import java.util.Date;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(){}

    public User(String firstName, String lastName, String email, String password) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = hashPassword(password);
    }

    public String hashPassword(String password) {
        return(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
    }

    public boolean isPasswordCorrect(String password, String stored_hash) {

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), stored_hash);

        return(result.verified);
    }

    public int getId() { return id; }

    public void setId(int id){ this.id = id; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
