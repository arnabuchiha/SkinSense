package com.arnab.skinsense;

/**
 * Created by AlphaBAT69 on 19-03-2018.
 */

public class User {
    private String username;
    private String email;
    public User(String username, String email){
        this.email=email;
        this.username=username;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
